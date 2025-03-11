package com.provizit.dnp.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import androidx.appcompat.app.AppCompatActivity;
import com.provizit.dnp.config.Preferences;
import com.provizit.dnp.config.ViewController;
import com.provizit.dnp.Logins.LoginMicrosoftADActivity;
import com.provizit.dnp.R;
import com.provizit.dnp.Utilities.CompanyDetails;
import com.provizit.dnp.Utilities.DatabaseHelper;
import com.provizit.dnp.Utilities.EmpData;
import com.provizit.dnp.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";
    ActivitySplashBinding activitySplashBinding;
    Animation animationUp;
    SharedPreferences.Editor editor1;
    SharedPreferences sharedPreferences1;
    DatabaseHelper myDb;
    EmpData empData;

    @SuppressLint("SuspiciousIndentation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySplashBinding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(activitySplashBinding.getRoot());
        ViewController.barPrimaryColorWhite(SplashActivity.this);
        myDb = new DatabaseHelper(this);
        empData = new EmpData();
        empData = myDb.getEmpdata();
        sharedPreferences1 = SplashActivity.this.getSharedPreferences("EGEMSS_DATA", MODE_PRIVATE);
        editor1 = sharedPreferences1.edit();
        SplashAnimation();
    }

    public void SplashAnimation() {
        animationUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
        activitySplashBinding.upImage.setAnimation(animationUp);
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(1200);
                } catch (Exception ignored) {
                } finally {
                    Cursor res = myDb.getAllData();
                    if (res.getCount() == 0) {
                        Intent intent = new Intent(SplashActivity.this, LoginMicrosoftADActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    } else {
                        while (res.moveToNext()) {
                            int Status = res.getInt(4);
                            if (Status == 1) {
                                String LOGINCHECK = Preferences.loadStringValue(getApplicationContext(), Preferences.LOGINCHECK, "");
                                if (!LOGINCHECK.isEmpty() && !LOGINCHECK.equals(null)) {
                                    new CompanyDetails(SplashActivity.this).execute(sharedPreferences1.getString("company_id", null));
                                    Intent intent = new Intent(SplashActivity.this, MeetingsRoomMeetings.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();
                                }
                            } else {
                                Intent intent = new Intent(SplashActivity.this, LoginMicrosoftADActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            }
                        }
                    }
                }
            }
        };
        t.start();
    }
}


