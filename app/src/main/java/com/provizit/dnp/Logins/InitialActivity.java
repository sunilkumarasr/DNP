package com.provizit.dnp.Logins;
import static android.view.View.GONE;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationSet;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.provizit.dnp.AESUtil;
import com.provizit.dnp.Activities.PrivacyPolicyActivity;
import com.provizit.dnp.Config.ConnectionReceiver;
import com.provizit.dnp.Config.ProgressLoader;
import com.provizit.dnp.Config.ViewController;
import com.provizit.dnp.Conversions;
import com.provizit.dnp.MVVM.ApiViewModel;
import com.provizit.dnp.MVVM.RequestModels.OtpSendEmaiModelRequest;
import com.provizit.dnp.R;
import com.provizit.dnp.Services.DataManger;
import com.provizit.dnp.databinding.ActivityInitialBinding;

import org.json.JSONException;
import org.json.JSONObject;

public class InitialActivity extends AppCompatActivity {
    private static final String TAG = "InitialActivity";
    ActivityInitialBinding binding;
    BroadcastReceiver broadcastReceiver;
    int otp;
    SharedPreferences.Editor editor1;
    AlertDialog.Builder builder;
    AESUtil aesUtil;
    //app update
    private static final int RC_APP_UPDATE = 100;
    ApiViewModel apiViewModel;
    //contacts List
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    String email = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        ViewController.barPrimaryColor(InitialActivity.this);
        binding = ActivityInitialBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent iin = getIntent();
        Bundle b = iin.getExtras();
        if (b != null) {
            email = (String) b.get("email");
        }

        binding.editEmail.setText(email.trim());
        SharedPreferences sharedPreferences1 = InitialActivity.this.getSharedPreferences("EGEMSS_DATA", MODE_PRIVATE);
        builder = new AlertDialog.Builder(InitialActivity.this);
        aesUtil = new AESUtil(InitialActivity.this);

        // Request permission to read contacts
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS},
                    PERMISSIONS_REQUEST_READ_CONTACTS);
        }

//        //internet connection
        broadcastReceiver = new ConnectionReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (isConnecteds(context)) {
                    binding.relativeInternet.getRoot().setVisibility(GONE);
                    binding.relativeUi.setVisibility(View.VISIBLE);
                } else {
                    binding.relativeInternet.getRoot().setVisibility(View.VISIBLE);
                    binding.relativeUi.setVisibility(GONE);
                }
            }
        };
        registoreNetWorkBroadcast();
        editor1 = sharedPreferences1.edit();
        apiViewModel = new ViewModelProvider(InitialActivity.this).get(ApiViewModel.class);

        binding.editEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.emailTextinput.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        binding.checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                binding.checkbox.setChecked(binding.checkbox.isChecked());
            } else {
                binding.checkbox.setChecked(false);
            }
        });

        binding.linearPrivacy.setOnClickListener(v -> {
            AnimationSet animation = Conversions.animation();
            v.startAnimation(animation);
            Intent i = new Intent(getApplicationContext(), PrivacyPolicyActivity.class);
            overridePendingTransition(R.anim.enter, R.anim.exit);
            startActivity(i);
        });

        binding.forgotPassword.setOnClickListener(v -> {
            AnimationSet animation = Conversions.animation();
            v.startAnimation(animation);
            Intent i = new Intent(getApplicationContext(), ForgotActivity.class);
            overridePendingTransition(R.anim.enter, R.anim.exit);
            startActivity(i);
        });

        binding.next.setOnClickListener(v -> {
            ViewController.hideKeyboard(InitialActivity.this);
//                STCPROAA02

            if (binding.editEmail.getText().toString().equalsIgnoreCase("")) {
                binding.emailTextinput.setErrorEnabled(true);
                binding.emailTextinput.setError("Enter email");
            } else if (binding.editPassword.getText().toString().equalsIgnoreCase("")) {
                binding.passwordSt.setErrorEnabled(true);
                binding.passwordSt.setError("Enter password");
            } else {
                if (binding.checkbox.isChecked()) {
                    if (ViewController.isEmailValid(binding.editEmail.getText().toString())) {

                        JsonObject gsonObject = new JsonObject();
                        JSONObject jsonObj_ = new JSONObject();
                        try {
                            jsonObj_.put("id", "");
                            jsonObj_.put("mverify", 0);
                            jsonObj_.put("type", "email");
                            jsonObj_.put("val", binding.editEmail.getText().toString().trim());
                            String encryptPWD = DataManger.Pwd_encrypt(getApplicationContext(), binding.editPassword.getText().toString(), binding.editEmail.getText().toString().trim());
                            jsonObj_.put("password", encryptPWD);
                            editor1.putString("password", encryptPWD);
                            editor1.commit();
                            editor1.apply();
                            JsonParser jsonParser = new JsonParser();
                            gsonObject = (JsonObject) jsonParser.parse(jsonObj_.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        apiViewModel.appuserlogin(getApplicationContext(), gsonObject);

                        ProgressLoader.show(InitialActivity.this);
                        binding.relativeUi.setEnabled(false);
                    } else {
                        binding.emailTextinput.setErrorEnabled(true);
                        binding.emailTextinput.setError("Invalid Email");
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please Agree & Continue", Toast.LENGTH_SHORT).show();
                }
            }
        });

        apiViewModel.getappuserloginResponse().observe(this, response -> {
            binding.relativeUi.setEnabled(true);
            if (response != null) {
                Integer statuscode = response.getResult();
                Integer successcode = 200, failurecode = 201, not_verified = 404, internet_verified = 500;
                if (statuscode.equals(failurecode)) {
                    ProgressLoader.hide();
                    builder.setMessage("Presently, this app is accessible by only the enterprise users of PROVIZIT.\n" +
                                    "\n" +
                                    "We couldn’t find you as an enterprise user.\n" +
                                    "\n" +
                                    "You may contact your organization or write to info@provizit.com for more information.")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                } else if (statuscode.equals(internet_verified)) {
                    ProgressLoader.hide();
                } else if (statuscode.equals(not_verified)) {
                    ProgressLoader.hide();
                } else if (statuscode.equals(successcode)) {
                    otp = Conversions.getNDigitRandomNumber(4);
                    editor1.putString("ProvizitOtp", otp + "");
                    editor1.putString("ProvizitEmail", binding.editEmail.getText().toString().trim());
                    editor1.commit();
                    editor1.apply();
                    OtpSendEmaiModelRequest otpSendEmaiModelRequest = new OtpSendEmaiModelRequest(binding.editEmail.getText().toString().trim(), binding.editEmail.getText().toString().trim(), aesUtil.encrypt(otp + "", "egems_2013_grms_2017_provizit_2020"), aesUtil.encrypt(otp + "", binding.editEmail.getText().toString().trim()));
                    apiViewModel.otpsendemail(getApplicationContext(), otpSendEmaiModelRequest);
                    binding.relativeUi.setEnabled(false);
                }
            } else {
                ProgressLoader.hide();
            }
        });

        apiViewModel.getcheckSetupResponse().observe(this, response -> {
            binding.relativeUi.setEnabled(true);
            if (response != null) {
                Integer statuscode = response.getResult();
                Integer successcode = 200, failurecode = 201, not_verified = 404, internet_verified = 500;
                if (statuscode.equals(failurecode)) {
                    ProgressLoader.hide();
                    builder.setMessage("Presently, this app is accessible by only the enterprise users of PROVIZIT.\n" +
                                    "\n" +
                                    "We couldn’t find you as an enterprise user.\n" +
                                    "\n" +
                                    "You may contact your organization or write to info@provizit.com for more information.")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                } else if (statuscode.equals(internet_verified)) {
                    ProgressLoader.hide();
                } else if (statuscode.equals(not_verified)) {
                    ProgressLoader.hide();
                } else if (statuscode.equals(successcode)) {
                    otp = Conversions.getNDigitRandomNumber(4);
                    editor1.putString("ProvizitOtp", otp + "");
                    editor1.putString("ProvizitEmail", binding.editEmail.getText().toString().trim());
                    editor1.commit();
                    editor1.apply();
                    OtpSendEmaiModelRequest otpSendEmaiModelRequest = new OtpSendEmaiModelRequest(binding.editEmail.getText().toString().trim(), binding.editEmail.getText().toString().trim(), aesUtil.encrypt(otp + "", "egems_2013_grms_2017_provizit_2020"), aesUtil.encrypt(otp + "", binding.editEmail.getText().toString().trim()));
                    apiViewModel.otpsendemail(getApplicationContext(), otpSendEmaiModelRequest);
                    binding.relativeUi.setEnabled(false);
                }
            } else {
                ProgressLoader.hide();
            }
        });

        apiViewModel.getotpsendemailResponse().observe(this, response -> {
            ProgressLoader.hide();
            binding.relativeUi.setEnabled(true);
            if (response != null) {
                Intent intent = new Intent(InitialActivity.this, OtpActivity.class);
                intent.putExtra("activity_type", "");
                overridePendingTransition(R.anim.enter, R.anim.exit);
                startActivity(intent);
            }
        });

        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(10000);
                } catch (Exception e) {
                } finally {
                    ProgressLoader.hide();
                }
            }
        };
        t.start();

    }

    protected void registoreNetWorkBroadcast() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, read contacts
            } else {
                // Permission denied
            }
        }
    }
}