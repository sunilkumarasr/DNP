package com.provizit.dnp.Logins;
import static android.view.View.GONE;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.provizit.dnp.AESUtil;
import com.provizit.dnp.config.ConnectionReceiver;
import com.provizit.dnp.config.ProgressLoader;
import com.provizit.dnp.config.ViewController;
import com.provizit.dnp.MVVM.ApiViewModel;
import com.provizit.dnp.MVVM.RequestModels.UpdatePwdModelRequest;
import com.provizit.dnp.R;
import com.provizit.dnp.Services.Model;
import com.provizit.dnp.databinding.ActivityForgotPasswordSetBinding;

public class ForgotPasswordSetActivity extends AppCompatActivity {
    ActivityForgotPasswordSetBinding binding;
    SharedPreferences sharedPreferences1;
    BroadcastReceiver broadcastReceiver;
    AESUtil aesUtil;
    ApiViewModel apiViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        ViewController.barPrimaryColor(ForgotPasswordSetActivity.this);
        binding = ActivityForgotPasswordSetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPreferences1 = getSharedPreferences("EGEMSS_DATA", MODE_PRIVATE);
        aesUtil = new AESUtil(ForgotPasswordSetActivity.this);
        apiViewModel = new ViewModelProvider(ForgotPasswordSetActivity.this).get(ApiViewModel.class);

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
        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("");
            actionBar.setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_black_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
        binding.email.setText(sharedPreferences1.getString("ProvizitEmail",null));
        binding.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.ePassword.getText().toString().length() >= 8){
                    binding.pPtl.setErrorEnabled(false);

                    if(!ViewController.isValidPassword(binding.ePassword.getText().toString())){
                        binding.pPtl.setErrorEnabled(true);
                        binding.pPtl.setError("Password is too weak");
                    }else{
                        binding.pPtl.setErrorEnabled(false);
                        if(binding.ePassword.getText().toString().equals(binding.cPassword.getText().toString())){
                            //mvvm
                            UpdatePwdModelRequest updatePwdModelRequest = new UpdatePwdModelRequest(sharedPreferences1.getString("company_id", null),aesUtil.encrypt(binding.cPassword.getText().toString(),sharedPreferences1.getString("ProvizitEmail",null)),sharedPreferences1.getString("link", null));
                            apiViewModel.updatepwd(getApplicationContext(),updatePwdModelRequest);
                            ProgressLoader.show(ForgotPasswordSetActivity.this);
                        }
                        else{
                            binding.cPtl.setErrorEnabled(true);
                            binding.cPtl.setError("Password not matched");
                        }
                    }
                }else {
                    binding.pPtl.setErrorEnabled(true);
                    binding.pPtl.setError("Password minimum length 8 characters");
                }
            }
        });
        apiViewModel.getupdatepwdResponse().observe(this, new Observer<Model>() {
            @Override
            public void onChanged(Model response) {
                ProgressLoader.hide();
                if (response != null) {
                    Integer statuscode = response.getResult();
                    Integer successcode = 200, failurecode = 201, not_verified = 404;
                    if (statuscode.equals(failurecode)) {

                    } else if (statuscode.equals(not_verified)) {
                    } else if (statuscode.equals(successcode)) {

                        Intent intent = new Intent(ForgotPasswordSetActivity.this, InitialActivity.class);
                        overridePendingTransition(R.anim.enter, R.anim.exit);
                        startActivity(intent);

                    }
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
            Intent intent = new Intent(getApplicationContext(), InitialActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), InitialActivity.class);
        startActivity(intent);
    }

}