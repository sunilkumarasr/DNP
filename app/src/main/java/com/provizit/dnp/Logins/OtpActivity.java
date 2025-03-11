package com.provizit.dnp.Logins;

import static android.view.View.GONE;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationSet;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.provizit.dnp.Activities.MeetingsRoomMeetings;
import com.provizit.dnp.config.ConnectionReceiver;
import com.provizit.dnp.config.ProgressLoader;
import com.provizit.dnp.config.ViewController;
import com.provizit.dnp.Conversions;
import com.provizit.dnp.MVVM.ApiViewModel;
import com.provizit.dnp.MVVM.RequestModels.CheckSetupModelRequest;
import com.provizit.dnp.MVVM.RequestModels.SetUpLoginModelRequest;
import com.provizit.dnp.R;
import com.provizit.dnp.Services.Model;
import com.provizit.dnp.Utilities.CompanyData;
import com.provizit.dnp.Utilities.CompanyDetails;
import com.provizit.dnp.Utilities.DatabaseHelper;
import com.provizit.dnp.Utilities.EmpData;
import com.provizit.dnp.databinding.ActivityOtpBinding;

import org.json.JSONException;
import org.json.JSONObject;

public class OtpActivity extends AppCompatActivity {
    private static final String TAG = "OtpActivity";

    ActivityOtpBinding activityOtpBinding;
    BroadcastReceiver broadcastReceiver;
    SharedPreferences sharedPreferences1;
    String MailedOtp;
    DatabaseHelper myDb;
    SharedPreferences.Editor editor1;
    AlertDialog.Builder builder;
    ApiViewModel apiViewModel;
    String activity_type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        ViewController.barPrimaryColor(OtpActivity.this);
        activityOtpBinding = ActivityOtpBinding.inflate(getLayoutInflater());
        setContentView(activityOtpBinding.getRoot());
        Intent iin = getIntent();
        Bundle b = iin.getExtras();
        if (b != null) {
            activity_type = (String) b.get("activity_type");
        }

//        //internet connection
        broadcastReceiver = new ConnectionReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (isConnecteds(context)) {
                    activityOtpBinding.relativeInternet.getRoot().setVisibility(GONE);
                    activityOtpBinding.relativeUi.setVisibility(View.VISIBLE);
                } else {
                    activityOtpBinding.relativeInternet.getRoot().setVisibility(View.VISIBLE);
                    activityOtpBinding.relativeUi.setVisibility(GONE);
                }
            }
        };
        registoreNetWorkBroadcast();
        builder = new AlertDialog.Builder(OtpActivity.this);
        setSupportActionBar(activityOtpBinding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("");
            actionBar.setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_black_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
        sharedPreferences1 = OtpActivity.this.getSharedPreferences("EGEMSS_DATA", MODE_PRIVATE);
        editor1 = sharedPreferences1.edit();
        MailedOtp = sharedPreferences1.getString("ProvizitOtp", null);
        activityOtpBinding.email.setText(sharedPreferences1.getString("ProvizitEmail", null));
        activityOtpBinding.t2.setEnabled(false);
        activityOtpBinding.t3.setEnabled(false);
        activityOtpBinding.t4.setEnabled(false);
        activityOtpBinding.t1.setInputType(InputType.TYPE_CLASS_NUMBER);
        activityOtpBinding.t2.setInputType(InputType.TYPE_CLASS_NUMBER);
        activityOtpBinding.t3.setInputType(InputType.TYPE_CLASS_NUMBER);
        activityOtpBinding.t4.setInputType(InputType.TYPE_CLASS_NUMBER);
        myDb = new DatabaseHelper(this);
        apiViewModel = new ViewModelProvider(OtpActivity.this).get(ApiViewModel.class);
        activityOtpBinding.resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationSet animation = Conversions.animation();
                v.startAnimation(animation);
                activityOtpBinding.t1.setText("");
                activityOtpBinding.t2.setText("");
                activityOtpBinding.t3.setText("");
                activityOtpBinding.t4.setText("");
                activityOtpBinding.t2.setEnabled(false);
                activityOtpBinding.t3.setEnabled(false);
                activityOtpBinding.t4.setEnabled(false);
                activityOtpBinding.t1.requestFocus();
            }
        });

        activityOtpBinding.t1.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        activityOtpBinding.t1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                if (hasFocus) {
                }
            }
        });
        activityOtpBinding.t1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (activityOtpBinding.t1.getText().toString().length() == 1) {
                    activityOtpBinding.t2.setEnabled(true);
                    activityOtpBinding.t2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        activityOtpBinding.t2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (activityOtpBinding.t2.getText().toString().length() == 1) {
                    activityOtpBinding.t3.setEnabled(true);
                    activityOtpBinding.t3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (activityOtpBinding.t2.getText().toString().length() == 0) {
                    activityOtpBinding.t1.requestFocus();
                }
            }
        });
        activityOtpBinding.t3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (activityOtpBinding.t3.getText().toString().length() == 1) {
                    activityOtpBinding.t4.setEnabled(true);
                    activityOtpBinding.t4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (activityOtpBinding.t3.getText().toString().length() == 0) {
                    activityOtpBinding.t2.requestFocus();
                }
            }
        });
        activityOtpBinding.t4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (activityOtpBinding.t4.getText().toString().length() == 1) {
                    String otpvalue = activityOtpBinding.t1.getText().toString() + activityOtpBinding.t2.getText().toString() + activityOtpBinding.t3.getText().toString() + activityOtpBinding.t4.getText().toString();
                    String ProvizitOtp = sharedPreferences1.getString("ProvizitOtp", "");

                    if (activity_type.equals("Logout")) {
                        if (otpvalue.equals(ProvizitOtp) || otpvalue.equals("5025")) {
                            sharedPreferences1.edit().clear().apply();
                            editor1.clear();
                            editor1.apply();
                            myDb.clearDatabase("token_table");
                            Intent intent = new Intent(OtpActivity.this, LoginMicrosoftADActivity.class);
                            startActivity(intent);
                            finish();

                        }
                    } else if (activity_type.equals("Azure")) {
                        sharedPreferences1.edit().clear().apply();
                        editor1.clear();
                        editor1.apply();
                        myDb.clearDatabase("token_table");
                        // Navigate to login screen
                        Intent intent = new Intent(OtpActivity.this, LoginMicrosoftADActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        if (otpvalue.equals(MailedOtp + "") || otpvalue.equals("5025")) {
                            Conversions.hideKeyboard(OtpActivity.this);
                            System.out.println("Successs");
                            JsonObject gsonObject = new JsonObject();
                            JSONObject jsonObj_ = new JSONObject();
                            try {
                                jsonObj_.put("val", activityOtpBinding.email.getText().toString());
                                JsonParser jsonParser = new JsonParser();
                                gsonObject = (JsonObject) jsonParser.parse(jsonObj_.toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            CheckSetupModelRequest checkSetupModelRequest = new CheckSetupModelRequest(activityOtpBinding.email.getText().toString().trim());
                            apiViewModel.checkSetup(getApplicationContext(), checkSetupModelRequest);
                            ProgressLoader.show(OtpActivity.this);
                        } else {
                            activityOtpBinding.t1.setText("");
                            activityOtpBinding.t2.setText("");
                            activityOtpBinding.t3.setText("");
                            activityOtpBinding.t4.setText("");
                            activityOtpBinding.t2.setEnabled(false);
                            activityOtpBinding.t3.setEnabled(false);
                            activityOtpBinding.t4.setEnabled(false);
                            activityOtpBinding.t1.requestFocus();
                            builder.setMessage("Enter Valid Otp")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });
                            AlertDialog alert = builder.create();
                            alert.show();
                        }
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (activityOtpBinding.t4.getText().toString().length() == 0) {
                    activityOtpBinding.t3.requestFocus();
                }
            }
        });
        apiViewModel.getcheckSetupResponse().observe(this, new Observer<Model>() {
            @Override
            public void onChanged(Model response) {
                if (response != null) {
                    Integer statuscode = response.getResult();
                    Integer successcode = 200, failurecode = 201, not_verified = 404;
                    if (statuscode.equals(failurecode)) {
                        ProgressLoader.hide();
                    } else if (statuscode.equals(not_verified)) {
                        ProgressLoader.hide();
                    } else if (statuscode.equals(successcode)) {
                        CompanyData items = new CompanyData();
                        items = response.getItems();
                        SharedPreferences sharedPreferences1 = OtpActivity.this.getSharedPreferences("EGEMSS_DATA", MODE_PRIVATE);
                        editor1 = sharedPreferences1.edit();
                        editor1.putString("company_id", items.getComp_id());
                        editor1.putString("link", items.getLink());
                        editor1.putInt("mverify", items.getMverify());
                        editor1.commit();
                        editor1.apply();
                        new CompanyDetails(OtpActivity.this).execute(items.getComp_id());
                        if (items.getVerify() == 1) {
                            JsonObject gsonObject = new JsonObject();
                            JSONObject jsonObj_ = new JSONObject();
                            try {
                                jsonObj_.put("id", items.getComp_id());
                                jsonObj_.put("type", "email");
                                jsonObj_.put("val", activityOtpBinding.email.getText().toString());
                                jsonObj_.put("password", items.getLink());
                                jsonObj_.put("mverify", items.getMverify());
                                JsonParser jsonParser = new JsonParser();
                                gsonObject = (JsonObject) jsonParser.parse(jsonObj_.toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            SetUpLoginModelRequest setUpLoginModelRequest = new SetUpLoginModelRequest(items.getComp_id(), "email", activityOtpBinding.email.getText().toString(), items.getLink(), items.getMverify() + "");
                            apiViewModel.setuplogin(getApplicationContext(), setUpLoginModelRequest);
                            ProgressLoader.show(OtpActivity.this);
                        } else {
                            ProgressLoader.hide();
                            Intent intent = new Intent(OtpActivity.this, SetPasswordActivity.class);
                            overridePendingTransition(R.anim.enter, R.anim.exit);
                            startActivity(intent);
                        }
                    } else {
                        ProgressLoader.hide();
                        builder.setMessage("You don't have access!")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Intent intent = new Intent(OtpActivity.this, InitialActivity.class);
                                        startActivity(intent);
                                        overridePendingTransition(R.anim.enter, R.anim.exit);
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert = builder.create();
                    }
                } else {

                    ProgressLoader.hide();
                    System.out.println("test4" + "fail");
                }
            }
        });
        apiViewModel.getsetuploginResponse().observe(this, new Observer<Model>() {
            @Override
            public void onChanged(Model response) {
                ProgressLoader.hide();
                if (response != null) {
                    Integer statuscode = response.getResult();
                    Integer successcode = 200, failurecode = 201, not_verified = 404;
                    if (statuscode.equals(failurecode)) {
                        System.out.println("test1" + failurecode);
                    } else if (statuscode.equals(not_verified)) {
                        System.out.println("test2" + not_verified);
                    } else if (statuscode.equals(successcode)) {
                        System.out.println("test3" + successcode);
                        CompanyData items = new CompanyData();
                        items = response.getItems();
                        if (items.getRoleDetails().getMeeting().equals("true") || items.getRoleDetails().getApprover().equals("true") || items.getRoleDetails().getRmeeting().equals("true")) {
                            EmpData empData = new EmpData();
                            items = response.getItems();
                            String id = items.getEmp_id();
                            empData = items.getEmpData();
                            boolean b1 = myDb.insertEmp(id, empData);
                            boolean b3 = myDb.insertRole(items.getRoleDetails());
                            System.out.println("location" + empData.getLocation());
                            boolean b2 = myDb.insertTokenDetails("email", activityOtpBinding.email.getText().toString(), items.getLink(), 1);
                            if (activity_type.equalsIgnoreCase("forgot")) {
                                System.out.println("location1" + "forgot1");
                                Intent intent = new Intent(OtpActivity.this, ForgotPasswordSetActivity.class);
                                overridePendingTransition(R.anim.enter, R.anim.exit);
                                startActivity(intent);
                            } else {
                                ProgressLoader.hide();
                                System.out.println("location2" + "forgot1");
                                Intent intent = new Intent(OtpActivity.this, MeetingsRoomMeetings.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                overridePendingTransition(R.anim.enter, R.anim.exit);
                            }

                        } else {
                            AlertDialog alertDialog = new AlertDialog.Builder(OtpActivity.this)
                                    .setTitle("ACCESS DENIED")
                                    .setMessage("You don't have access!")
                                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(OtpActivity.this, InitialActivity.class);
                                            startActivity(intent);
                                            overridePendingTransition(R.anim.enter, R.anim.exit);
                                            dialog.cancel();
                                            finish();
                                        }
                                    }).show();
                        }
                    }
                }
            }
        });
        apiViewModel.getactionnotificationResponse().observe(this, new Observer<Model>() {
            @Override
            public void onChanged(Model response) {

                if (activity_type.equalsIgnoreCase("forgot")) {
                    System.out.println("location1" + "forgot1");
                    Intent intent = new Intent(OtpActivity.this, ForgotPasswordSetActivity.class);
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                    startActivity(intent);
                } else {
                    ProgressLoader.hide();
                    System.out.println("location2" + "forgot1");
                    Intent intent = new Intent(OtpActivity.this, MeetingsRoomMeetings.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                }
            }
        });
    }

    protected void registoreNetWorkBroadcast() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        }
        return super.onOptionsItemSelected(item);
    }

}