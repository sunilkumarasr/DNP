package com.provizit.dnp.Activities;
import static android.view.View.GONE;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationSet;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.provizit.dnp.config.ConnectionReceiver;
import com.provizit.dnp.Conversions;
import com.provizit.dnp.R;
import com.provizit.dnp.Services.DataManger;

public class PrivacyPolicyActivity extends AppCompatActivity {

    BroadcastReceiver broadcastReceiver;
    RelativeLayout relative_internet;
    RelativeLayout relative_ui;
    ImageView img_back;
    WebView webView;
    TextView txt_no_data;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimary));
        }
        //internet connection
        relative_internet = findViewById(R.id.relative_internet);
        relative_ui = findViewById(R.id.relative_ui);
        broadcastReceiver = new ConnectionReceiver(){
            @Override
            public void onReceive(Context context, Intent intent) {
                if (isConnecteds(context)){
                    relative_internet.setVisibility(GONE);
                    relative_ui.setVisibility(View.VISIBLE);
                }else {
                    relative_internet.setVisibility(View.VISIBLE);
                    relative_ui.setVisibility(GONE);
                }
            }
        };
        registoreNetWorkBroadcast();
        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationSet animation = Conversions.animation();
                v.startAnimation(animation);
                finish();
            }
        });
        webView = findViewById(R.id.webView);
        txt_no_data = findViewById(R.id.txt_no_data);
        load_data(DataManger.IMAGE_URL+"/privacy-policy/");
    }

    protected void registoreNetWorkBroadcast(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            registerReceiver(broadcastReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }

    private void load_data(String url) {
        if (url.startsWith("https://trusted-url.com")) {
            WebSettings settings = webView.getSettings();
            settings.setJavaScriptEnabled(false); // Safe since the URL is trusted.
            webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
            webView.getSettings().setBuiltInZoomControls(false);
            webView.getSettings().setUseWideViewPort(true);
            webView.getSettings().setLoadWithOverviewMode(true);
            progressDialog = new ProgressDialog(PrivacyPolicyActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();

            webView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    progressDialog.dismiss();
                }
            });

            webView.loadUrl(url);
        } else {
            // Handle untrusted URL case
            Log.e("WebView", "Untrusted URL blocked: " + url);
        }
    }


}