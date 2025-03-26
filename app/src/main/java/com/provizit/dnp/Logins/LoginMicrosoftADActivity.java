package com.provizit.dnp.Logins;

import static android.view.View.GONE;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.microsoft.identity.client.AuthenticationCallback;
import com.microsoft.identity.client.IAccount;
import com.microsoft.identity.client.IAuthenticationResult;
import com.microsoft.identity.client.IPublicClientApplication;
import com.microsoft.identity.client.PublicClientApplication;
import com.microsoft.identity.client.exception.MsalException;
import com.provizit.dnp.AESUtil;
import com.provizit.dnp.Activities.MeetingsRoomMeetings;
import com.provizit.dnp.config.ConnectionReceiver;
import com.provizit.dnp.config.Preferences;
import com.provizit.dnp.config.ProgressLoader;
import com.provizit.dnp.config.ViewController;
import com.provizit.dnp.Conversions;
import com.provizit.dnp.MVVM.ApiViewModel;
import com.provizit.dnp.MVVM.RequestModels.CheckSetupModelRequest;
import com.provizit.dnp.MVVM.RequestModels.OtpSendEmaiModelRequest;
import com.provizit.dnp.R;
import com.provizit.dnp.Services.DataManger;
import com.provizit.dnp.Utilities.AzureaddetailsModel;
import com.provizit.dnp.Utilities.CompanyData;
import com.provizit.dnp.Utilities.CompanyDetails;
import com.provizit.dnp.Utilities.DatabaseHelper;
import com.provizit.dnp.Utilities.EmpData;
import com.provizit.dnp.databinding.ActivityLoginMicrosoftAdactivityBinding;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginMicrosoftADActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityLoginMicrosoftAdactivityBinding binding;
    BroadcastReceiver broadcastReceiver;
    AlertDialog.Builder builder;
    DatabaseHelper myDb;
    SharedPreferences.Editor editor1;
    int otp;
    AESUtil aesUtil;
    ApiViewModel apiViewModel;
    TextInputLayout email_textinput;
    EditText edit_email;
    Button next;
    String AUTHORITY = "https://login.microsoftonline.com/c6416dc9-4961-4429-a1bc-1c1bfee7f846";
    String REDIRECT_URI = "msauth://com.provizit.dnp/RNy5oraIA7QxAEY9MB%2FZ5j%2FwWgo%3D";
    String CLIENT_ID = "3e4a6142-7057-4e08-a278-114688ab51ef";
    String Company_ID = "";
    AzureaddetailsModel azuredetailsmodel;
    String username = "";
    String LoginType = "";
    String company_id = "";
    Boolean Azure_status;
    String comp_id = "";
    SharedPreferences sharedPreferences1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        ViewController.barPrimaryColor(LoginMicrosoftADActivity.this);
        binding = ActivityLoginMicrosoftAdactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setContentView(R.layout.activity_login_microsoft_adactivity);
        Preferences.saveStringValue(getApplicationContext(), Preferences.LOGINCHECK, "Login");
        sharedPreferences1 = LoginMicrosoftADActivity.this.getSharedPreferences("EGEMSS_DATA", MODE_PRIVATE);
        comp_id = sharedPreferences1.getString("company_id", "");
        editor1 = sharedPreferences1.edit();
        myDb = new DatabaseHelper(this);
        builder = new AlertDialog.Builder(LoginMicrosoftADActivity.this);
        aesUtil = new AESUtil(LoginMicrosoftADActivity.this);
        apiViewModel = new ViewModelProvider(LoginMicrosoftADActivity.this).get(ApiViewModel.class);
        email_textinput = findViewById(R.id.email_textinput);
        edit_email = findViewById(R.id.edit_email);
        next = findViewById(R.id.next);

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

        apiViewModel.getcheckSetupResponse().observe(this, response -> {
            if (response != null) {
                Integer statuscode = response.getResult();
                Integer successcode = 200, failurecode = 201, not_verified = 404, internet_verified = 500;
                if (statuscode.equals(failurecode)) {
                    ProgressLoader.hide();
                    builder.setMessage("Presently, this app is accessible by only the enterprise users of PROVIZIT.\n" + "\n" + "We couldn’t find you as an enterprise user.\n" + "\n" + "You may contact your organization or write to info@provizit.com for more information.").setCancelable(false).setPositiveButton("OK", (dialog, id) -> dialog.cancel());
                    AlertDialog alert = builder.create();
                    alert.show();
                } else if (statuscode.equals(internet_verified)) {
                    ProgressLoader.hide();
                } else if (statuscode.equals(not_verified)) {
                    ProgressLoader.hide();
                } else if (statuscode.equals(successcode)) {
                    CompanyData items = new CompanyData();
                    items = response.getItems();
                    company_id = items.getCid();
                    SharedPreferences sharedPreferences11 = LoginMicrosoftADActivity.this.getSharedPreferences("EGEMSS_DATA", MODE_PRIVATE);
                    editor1 = sharedPreferences11.edit();
                    editor1.putString("company_id", company_id);
                    editor1.commit();
                    editor1.apply();
                    if (items.isAzure()) {
                        ProgressLoader.hide();
                        LoginType = "Azure";
                        microsoft_ad(items.getClientid(), items.getTenantid(), items.getClientsecret());
                        getazureaddetails(comp_id);
                    } else if (items.isTwofa()) {
                        ProgressLoader.hide();
                        Intent intent = new Intent(LoginMicrosoftADActivity.this, InitialActivity.class);
                        intent.putExtra("email", edit_email.getText().toString());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        overridePendingTransition(R.anim.enter, R.anim.exit);
                    } else {
                        //otp screen
                        otp_send();
                        LoginType = "OTP";
                    }
                }
            } else {
                ProgressLoader.hide();
            }
        });

        apiViewModel.getuserADloginResponse().observe(this, response -> {
            if (response != null) {
                CompanyData items = new CompanyData();
                items = response.getItems();
                if (items != null && items.getRoleDetails() != null) {
                    if (items.getRoleDetails().getMeeting().equals("true") || items.getRoleDetails().getApprover().equals("true") || items.getRoleDetails().getRmeeting().equals("true")) {
                        SharedPreferences sharedPreferences11 = LoginMicrosoftADActivity.this.getSharedPreferences("EGEMSS_DATA", MODE_PRIVATE);
                        editor1 = sharedPreferences11.edit();
                        editor1.putString("link", items.getLink());
                        editor1.putInt("mverify", items.getMverify());
                        editor1.commit();
                        editor1.apply();
                        try {
                            if (LoginType.equalsIgnoreCase("Azure")) {
                                new CompanyDetails(LoginMicrosoftADActivity.this).execute(items.getComp_id());
                                if (items.getVerify() == 1) {
                                    EmpData empData = new EmpData();
                                    items = response.getItems();
                                    String id = items.getEmp_id();
                                    empData = items.getEmpData();
                                    boolean b1 = myDb.insertEmp(id, empData);
                                    boolean b3 = myDb.insertRole(items.getRoleDetails());
                                    boolean b2 = myDb.insertTokenDetails("email", username.trim(), items.getLink(), 1);
                                    ProgressLoader.hide();
                                    Intent intent = new Intent(LoginMicrosoftADActivity.this, MeetingsRoomMeetings.class);
                                    intent.putExtra("Azure_status", Azure_status);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.enter, R.anim.exit);
                                } else {
                                    ProgressLoader.hide();
                                }
                            } else if (LoginType.equalsIgnoreCase("OTP")) {
                                otp = Conversions.getNDigitRandomNumber(4);
                                editor1.putString("ProvizitOtp", otp + "");
                                editor1.putString("ProvizitEmail", edit_email.getText().toString().trim());
                                editor1.commit();
                                editor1.apply();
                                //otp send api
                                OtpSendEmaiModelRequest otpSendEmaiModelRequest = new OtpSendEmaiModelRequest(edit_email.getText().toString().trim(), edit_email.getText().toString().trim(), aesUtil.encrypt(otp + "", "egems_2013_grms_2017_provizit_2020"), aesUtil.encrypt(otp + "", edit_email.getText().toString().trim()));
                                apiViewModel.otpsendemail(getApplicationContext(), otpSendEmaiModelRequest);

                            } else {
                                ProgressLoader.hide();
                            }
                        } catch (IndexOutOfBoundsException e) {
                            Log.e("error", e.getMessage());
                            ProgressLoader.hide();
                        }

                    } else {
                        AlertDialog alertDialog = new AlertDialog.Builder(LoginMicrosoftADActivity.this).setTitle("ACCESS DENIED").setMessage("You don't have access!").setPositiveButton(android.R.string.ok, (dialog, which) -> {
                            Intent intent = new Intent(LoginMicrosoftADActivity.this, InitialActivity.class);
                            intent.putExtra("email", edit_email.getText().toString());
                            startActivity(intent);
                            overridePendingTransition(R.anim.enter, R.anim.exit);
                            dialog.cancel();
                            finish();
                        }).show();
                    }
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(LoginMicrosoftADActivity.this).setTitle("ACCESS DENIED").setMessage("You don't have access!").setPositiveButton(android.R.string.ok, (dialog, which) -> {
                        Intent intent = new Intent(LoginMicrosoftADActivity.this, InitialActivity.class);
                        intent.putExtra("email", edit_email.getText().toString());
                        startActivity(intent);
                        overridePendingTransition(R.anim.enter, R.anim.exit);
                        dialog.cancel();
                        finish();
                    }).show();
                }

            } else {
                ProgressLoader.hide();
            }
        });

        apiViewModel.getotpsendemailResponse().observe(this, response -> {
            ProgressLoader.hide();
            if (response != null) {
                Intent intent = new Intent(LoginMicrosoftADActivity.this, OtpActivity.class);
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
        next.setOnClickListener(this);
    }

    private void getazureaddetails(String comp_id) {
        DataManger dataManger = DataManger.getDataManager();
        dataManger.getazureaddetails(new Callback<AzureaddetailsModel>() {
            @Override
            public void onResponse(Call<AzureaddetailsModel> call, Response<AzureaddetailsModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    AzureaddetailsModel model = response.body();
                    // Status code handling
                    if (model != null) {
                        Integer statuscode = model.getResult();
                        Integer successcode = 200, failurecode = 401, not_verified = 404;

                        if (statuscode.equals(failurecode)) {
                            return;
                        } else if (statuscode.equals(not_verified)) {
                            return;
                        } else if (!statuscode.equals(successcode)) {
                            return;
                        }
                    }
                    azuredetailsmodel = model;
                    if (azuredetailsmodel.items != null) {
                        Boolean active = azuredetailsmodel.items.getActive();
                        Boolean online = azuredetailsmodel.items.getOnline();
                        if (Boolean.TRUE.equals(active) && Boolean.TRUE.equals(online)) {
                            Azure_status = true;
                        } else {
                        }
                    } else {

                    }
                } else {
                }
            }

            @Override
            public void onFailure(Call<AzureaddetailsModel> call, Throwable t) {
            }
        }, LoginMicrosoftADActivity.this, sharedPreferences1.getString("company_id", ""));
    }

    protected void registoreNetWorkBroadcast() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.next) {
            AnimationSet animations = Conversions.animation();
            v.startAnimation(animations);
            if (ViewController.isEmailValid(edit_email.getText().toString())) {
                //api mvvm
                CheckSetupModelRequest checkSetupModelRequest = new CheckSetupModelRequest(edit_email.getText().toString().trim());
                apiViewModel.checkSetup(getApplicationContext(), checkSetupModelRequest);
                ProgressLoader.show(LoginMicrosoftADActivity.this);
            } else {
                email_textinput.setErrorEnabled(true);
                email_textinput.setError("Invalid Email");
            }
        }
    }

    private void microsoft_ad(String clientid, String tenantid, String clientsecret) {
        editor1.putString("company_id", company_id);
        editor1.commit();
        editor1.apply();
        Company_ID = company_id;
        AUTHORITY = "https://login.microsoftonline.com/" + tenantid;
        REDIRECT_URI = "msauth://com.provizit.dnp/RNy5oraIA7QxAEY9MB%2FZ5j%2FwWgo%3D";
        CLIENT_ID = clientid;

        // Create PublicClientApplication instance
        PublicClientApplication.create(getApplicationContext(), CLIENT_ID, AUTHORITY, REDIRECT_URI, new IPublicClientApplication.ApplicationCreatedListener() {

            @Override
            public void onCreated(IPublicClientApplication application) {
                // Handle application creation success
                String[] scopes = {"User.Read", "Mail.Read"};
                application.acquireToken(LoginMicrosoftADActivity.this, scopes, new AuthenticationCallback() {
                    @Override
                    public void onSuccess(IAuthenticationResult authenticationResult) {
                        IAccount account = authenticationResult.getAccount();
                        // Get the username from the account

                        username = account.getUsername();
                        String id = account.getId();
                        JsonObject gsonObject = new JsonObject();
                        JSONObject jsonObj_ = new JSONObject();
                        try {
                            jsonObj_.put("id", company_id);
                            jsonObj_.put("val", username.trim());
                            String encryptPWD = DataManger.Pwd_encrypt(getApplicationContext(), id, username.trim());
                            jsonObj_.put("adval", encryptPWD);
                            JsonParser jsonParser = new JsonParser();
                            gsonObject = (JsonObject) jsonParser.parse(jsonObj_.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        apiViewModel.userADlogin(getApplicationContext(), gsonObject);
                        ProgressLoader.show(LoginMicrosoftADActivity.this);
                    }

                    @Override
                    public void onError(MsalException exception) {
                        // Handle authentication error
                        exception.printStackTrace();
                        Log.e("exceptionAD1",exception.getMessage().toString());
                    }

                    @Override
                    public void onCancel() {
                        // Handle user cancellation
                        Log.e("exceptionA","123");
                    }
                });
            }

            @Override
            public void onError(MsalException exception) {
                // Handle application creation error
                exception.printStackTrace();
                Log.e("exceptionAD",exception.getMessage().toString());
            }
        });
    }

    private void otp_send() {
        JsonObject gsonObject = new JsonObject();
        JSONObject jsonObj_ = new JSONObject();
        try {
            jsonObj_.put("id", company_id);
            jsonObj_.put("val", edit_email.getText().toString().trim());
            String encryptPWD = DataManger.Pwd_encrypt(getApplicationContext(), otp + "", edit_email.getText().toString().trim());
            jsonObj_.put("adval", encryptPWD);
            JsonParser jsonParser = new JsonParser();
            gsonObject = (JsonObject) jsonParser.parse(jsonObj_.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        apiViewModel.userADlogin(getApplicationContext(), gsonObject);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

}
