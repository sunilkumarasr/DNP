package com.provizit.dnp.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.TrafficStats;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.microsoft.identity.client.AuthenticationCallback;
import com.microsoft.identity.client.IAccount;
import com.microsoft.identity.client.IAuthenticationResult;
import com.microsoft.identity.client.IPublicClientApplication;
import com.microsoft.identity.client.PublicClientApplication;
import com.microsoft.identity.client.exception.MsalException;
import com.provizit.dnp.AESUtil;
import com.provizit.dnp.Calendar.myCalendarData;
import com.provizit.dnp.CircleImageView;
import com.provizit.dnp.Config.ProgressLoader;
import com.provizit.dnp.Logins.LoginMicrosoftADActivity;
import com.provizit.dnp.Config.Preferences;
import com.provizit.dnp.Conversions;
import com.provizit.dnp.Logins.InitialActivity;
import com.provizit.dnp.Logins.OtpActivity;
import com.provizit.dnp.MVVM.ApiViewModel;
import com.provizit.dnp.MVVM.RequestModels.CheckSetupModelRequest;
import com.provizit.dnp.MVVM.RequestModels.OtpSendEmaiModelRequest;
import com.provizit.dnp.R;
import com.provizit.dnp.Services.DataManger;
import com.provizit.dnp.Services.Model;
import com.provizit.dnp.Services.Model1;
import com.provizit.dnp.Services.OutlookItems;
import com.provizit.dnp.Services.OutlookModel;
import com.provizit.dnp.Utilities.CompanyData;
import com.provizit.dnp.Utilities.CompanyDetails;
import com.provizit.dnp.Utilities.DatabaseHelper;
import com.provizit.dnp.Utilities.EmpData;
import com.provizit.dnp.Utilities.EmployeeScheduledetailsModel;
import com.provizit.dnp.Utilities.Inviteeitem;
import com.provizit.dnp.Utilities.Inviteemodelclass;
import com.provizit.dnp.Utilities.LocationData;
import com.provizit.dnp.Utilities.PeopleAdapter;
import com.provizit.dnp.Utilities.RoleDetails;
import com.provizit.dnp.Utilities.ScheduledetailsModel;
import com.squareup.picasso.Picasso;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MeetingsRoomMeetings extends AppCompatActivity {

    private static Locale locale;
    TextView presentTime;
    EthernetDataReceiver ethernetDataReceiver;
    TextView inviteesT;
    ImageView logo;
    TextView meetingRoom;
    TextView hostName;
    TextView invitee1Name;
    TextView invitee2Name;
    TextView m_Status;
    TextView m_timeText;
    TextView m_start_endtime;
    TextView count;
    TextView employee_name, designation, department;
    CircleImageView hostImg;
    CircleImageView invitee1Image;
    CircleImageView invitee2Image;
    CircleImageView employee_img;
    RecyclerView recyclerView;
    CardView m_time;
    PeopleAdapter adapter1;
    ArrayList<LocationData> companyAddressArrayList;
    SharedPreferences sharedPreferences1;
    SharedPreferences.Editor editor1;
    LinearLayout invitee2layout;
    LinearLayout meetinglayout;
    LinearLayout invitee1layout;
    long p_time;
    Inviteeitem item;
    ArrayList<CompanyData> meetings;
    ArrayList<ScheduledetailsModel> emp_shedulerlist;
    ArrayList<CompanyData> meetingsubject;
    ArrayList<CompanyData> meetingsubjectss;
    ArrayList<OutlookItems> outLookMeetings;
    AlertDialog.Builder builder;
    AESUtil aesUtil;
    TextView confidentialData;
    ArrayList<String> picArray, picsArray;
    ArrayList<CompanyData> toDayMeetings_Appointments;
    ArrayList<CompanyData> toDayMeetings;
    ArrayList<CompanyData> meetings1;
    DatabaseHelper myDb;
    EmpData empData;
    RoleDetails roleDetails;
    MeetingsAdapter meetingsAdapter;
    ApiViewModel apiViewModel;
    Calendar cal;
    String email = "";
    String comp_id = "";
    String password = "";
    String id = "";
    int otp;
    String username = "";
    String LoginType = "";
    String AUTHORITY = "https://login.microsoftonline.com/c6416dc9-4961-4429-a1bc-1c1bfee7f846";
    String REDIRECT_URI = "msauth://com.provizit.dnp/RNy5oraIA7QxAEY9MB%2FZ5j%2FwWgo%3D";
    String CLIENT_ID = "3e4a6142-7057-4e08-a278-114688ab51ef";
    String Company_ID = "";
    Boolean Azure_status = false;
    long start;
    long end;
    long SelectedDate;
    long toDay;
    Thread t1;

    public static Calendar getEndOfADay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar;
    }

    public static String millitotime(Long millSec, Boolean is24hours) {
        locale = new Locale(DataManger.appLanguage);
        DateFormat simple = new SimpleDateFormat("hh:mm aa", locale);

        if (is24hours) {
            simple = new SimpleDateFormat("HH:mm");
        }
        Date result = new Date(millSec);
        String time = simple.format(result) + "";
        return time;
    }

    public static String millitotimeTest(Long millSec, Boolean is24hours) {
        DateFormat simple;

        if (is24hours) {
            simple = new SimpleDateFormat("HH:mm");   // 24h format
        } else {
            simple = new SimpleDateFormat("hh:mm aa"); // 12h AM/PM format
        }

        // 👉 Force Universal Time Zone (UTC)
        simple.setTimeZone(TimeZone.getTimeZone("UTC"));

        Date result = new Date(millSec);
        return simple.format(result);
    }


    public static String millitoDate(Long millSec) {
        DateFormat simple = new SimpleDateFormat("yyyyMMdd");

        Date result = new Date(millSec);
        return simple.format(result);
    }

    public static String checkNextDay(String todayEnd, String nextDayStart, String nextDay) {
        int numDays = 7; // Number of days to increment
//        adbcommand("/system/xbin/test 202303151333 202303151340 enable");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat day = new SimpleDateFormat("EEE");

        Calendar calendar = Calendar.getInstance();
        String today = sdf.format(calendar.getTime());
        String shutdowntime = todayEnd.replace(":", "");
        if (shutdowntime.length() == 3) {
            shutdowntime = 0 + shutdowntime;
        }


        String dateStr = today + shutdowntime + " ";
        String onTime = nextDayStart.replace(":", "");
        if (onTime.length() == 3) {
            onTime = 0 + onTime;
        }


        for (int i = 0; i < numDays; i++) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            if ((day.format(calendar.getTime()).toUpperCase()).equals(nextDay)) {

                dateStr = dateStr + sdf.format(calendar.getTime()) + onTime;

                break;
            }
        }
        return dateStr;
    }

    public static String afterEndTime(String nextDayStart, String nextDay) {
        int numDays = 7; // Number of days to increment
//        adbcommand("/system/xbin/test 202303151333 202303151340 enable");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat shutdownFormat = new SimpleDateFormat("yyyyMMddHHmm");
        SimpleDateFormat day = new SimpleDateFormat("EEE");

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 1);
        String today = shutdownFormat.format(calendar.getTime());
        String dateStr = today + " ";
        String onTime = nextDayStart.replace(":", "");
        if (onTime.length() == 3) {
            onTime = 0 + onTime;
        }
        for (int i = 0; i < numDays; i++) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            if ((day.format(calendar.getTime()).toUpperCase()).equals(nextDay)) {

                dateStr = dateStr + sdf.format(calendar.getTime()) + onTime;

                break;
            }
        }
        return dateStr;
    }

    private static String TodayHolidayDeviceOpenned(String nextDayStart, String nextDay) {
        int numDays = 7; // Number of days to increment
//        adbcommand("/system/xbin/test 202303151333 202303151340 enable");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat shutdownFormat = new SimpleDateFormat("yyyyMMddHHmm");
        SimpleDateFormat day = new SimpleDateFormat("EEE");

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 1);
        String today = shutdownFormat.format(calendar.getTime());

        String dateStr = today + " ";
        String onTime = nextDayStart.replace(":", "");
        if (onTime.length() == 3) {
            onTime = 0 + onTime;
        }


        for (int i = 0; i < numDays; i++) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            if ((day.format(calendar.getTime()).toUpperCase()).equals(nextDay)) {

                dateStr = dateStr + sdf.format(calendar.getTime()) + onTime;

                break;
            }
        }
        return dateStr;
    }

    private static String beforeStartTime(String Start) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat shutdownFormat = new SimpleDateFormat("yyyyMMddHHmm");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 1);
        String today = shutdownFormat.format(calendar.getTime());
        String todayDate = sdf.format(calendar.getTime());
        String onTime = Start.replace(":", "");
        if (onTime.length() == 3) {
            onTime = 0 + onTime;
        }
        return today + " " + todayDate + onTime;
    }

    public static int timezone() {
        Date d = new Date();
        return d.getTimezoneOffset() * 60;
    }

    public static AnimationSet animation() {
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
        fadeIn.setDuration(1000);
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator()); //and this
        fadeOut.setStartOffset(1000);
        fadeOut.setDuration(1000);
        AnimationSet animation = new AnimationSet(false); //change to false
        animation.addAnimation(fadeIn);
        return animation;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView(R.layout.activity_meetings_room_meetings);

        myDb = new DatabaseHelper(this);
        roleDetails = new RoleDetails();
        empData = myDb.getEmpdata();
        picArray = new ArrayList<>();
        picsArray = new ArrayList<>();
        toDayMeetings = new ArrayList<>();
        toDayMeetings_Appointments = new ArrayList<>();
        meetings1 = new ArrayList<>();
        meetings = new ArrayList<>();
        meetingsubject = new ArrayList<>();
        meetingsubjectss = new ArrayList<>();
        emp_shedulerlist = new ArrayList<>();
        outLookMeetings = new ArrayList<>();

        apiViewModel = new ViewModelProvider(MeetingsRoomMeetings.this).get(ApiViewModel.class);

        Preferences.saveStringValue(getApplicationContext(), Preferences.LOGINCHECK, "Login");
        sharedPreferences1 = MeetingsRoomMeetings.this.getSharedPreferences("EGEMSS_DATA", MODE_PRIVATE);
        comp_id = sharedPreferences1.getString("company_id", "");
        password = sharedPreferences1.getString("password", "");

        ResouresIds();

        Intent iin = getIntent();
        Bundle b = iin.getExtras();
        if (b != null) {
            email = (String) b.get("email");
            Azure_status = (Boolean) b.get("Azure_status");
        }
        editor1 = sharedPreferences1.edit();

        builder = new AlertDialog.Builder(MeetingsRoomMeetings.this);
        aesUtil = new AESUtil(MeetingsRoomMeetings.this);
        ethernetDataReceiver = new EthernetDataReceiver();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(ethernetDataReceiver, filter);
        adbcommand("echo w 0x03 > ./sys/devices/platform/led_con_h/zigbee_reset");

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        apiViewModel.getEmployeeDetails(getApplicationContext(), empData.getEmp_id());
        getuserDetails();

        Calendar calendar = Calendar.getInstance();

        // Set start time (00:00:00)
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        start = calendar.getTimeInMillis() / 1000;

        // Set end time (23:59:59)
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        end = calendar.getTimeInMillis() / 1000;


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MeetingsRoomMeetings.this);
        recyclerView.setLayoutManager(linearLayoutManager);

        cal = Calendar.getInstance();

        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        // Sleep for 30 seconds
                        Thread.sleep(1000 * 30);

                        // Run UI-related tasks on the UI thread
                        runOnUiThread(() -> {
                            getbusyScheduledetails(comp_id, id, "");
//                            getoutlookappointments("upcoming", email, comp_id, start, end);
                        });
                    }
                } catch (InterruptedException e) {
                    // Handle interruption exception here, if needed
                }
            }
        };

        // Start the thread
        t.start();
        t1 = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(() -> {
                            long date = System.currentTimeMillis();

                            ledColors();

                            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
                            String dateString = sdf.format(date);
                            SimpleDateFormat sdf1 = new SimpleDateFormat("EE dd MMM yyyy");
                            String dateString1 = sdf1.format(date);
                            String sourceString = dateString1;
                            presentTime.setText(Html.fromHtml(sourceString));
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        t1.start();

//      autoShutdown(dayInt);

        apiViewModel.getEmployeeDetailsResponse().observe(this, new Observer<JsonObject>() {
            @Override
            public void onChanged(JsonObject response) {
                ProgressLoader.hide();
                if (response != null) {
                    Gson gson = new Gson();
                    Inviteemodelclass model = new Inviteemodelclass();
                    model = gson.fromJson(String.valueOf(response), Inviteemodelclass.class);
                    if (model != null) {
                        Integer statuscode = model.getResult();
                        Integer successcode = 200, failurecode = 401, not_verified = 404;
                        if (statuscode.equals(failurecode)) {

                        } else if (statuscode.equals(not_verified)) {

                        } else if (statuscode.equals(successcode)) {
                            item = new Inviteeitem();
                            item = model.getItems();
                            employee_name.setText(item.getName());
                            // Apply custom font from resources (if needed)
                            Typeface typeface = ResourcesCompat.getFont(getApplicationContext(), R.font.roboto_medium);
                            employee_name.setTypeface(typeface);

                            // Get the Paint object from the TextView
                            Paint paint = employee_name.getPaint();

                            // Remove any gradient shader (if there was one)
                            paint.setShader(null);

                            // Apply a dark shadow above the text (shadow slightly offset upwards)
                            employee_name.setShadowLayer(10, 0, -5, Color.parseColor("#80000000")); // Dark shadow

                            // Ensure the TextView is redrawn to apply changes
                            employee_name.invalidate(); // Redraw the TextView

                            department.setText(item.getHierarchys().getName());
                            designation.setText(item.getDesignation());
                            picArray = item.getPics();
                            if (item.getPic() != null && item.getPic().size() != 0) {
                                //preferences
                                Preferences.saveStringValue(getApplicationContext(), Preferences.Profile_Url, DataManger.IMAGE_URL + "/uploads/" + sharedPreferences1.getString("company_id", null) + "/" + item.getPic().get(item.getPic().size() - 1));
                                Glide.with(MeetingsRoomMeetings.this).load(DataManger.IMAGE_URL + "/uploads/" + sharedPreferences1.getString("company_id", null) + "/" + item.getPic().get(item.getPic().size() - 1))
                                        .into(employee_img);
                            } else {
                                // Set a default image if no employee image is available
                                Glide.with(MeetingsRoomMeetings.this)
                                        .load(R.drawable.ic_user) // Assuming `ic_user` is the default image resource
                                        .into(employee_img);
                            }
                        }
                    }
                }
            }
        });

        CheckSetupModelRequest checkSetupModelRequest = new CheckSetupModelRequest(empData.getEmail().trim());
        apiViewModel.checkSetup(getApplicationContext(), checkSetupModelRequest);

        logo.setOnClickListener(v -> {
//            ViewController.hideKeyboard(MeetingsRoomMeetings.this);
//            JsonObject gsonObject = new JsonObject();
//            JSONObject jsonObj_ = new JSONObject();
//            try {
//                jsonObj_.put("id", "");
//                jsonObj_.put("mverify", 0);
//                jsonObj_.put("password", password);
//                jsonObj_.put("type", "email");
//                jsonObj_.put("val", empData.getEmail().trim());

//                JsonParser jsonParser = new JsonParser();
//                gsonObject = (JsonObject) jsonParser.parse(jsonObj_.toString());
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            ProgressLoader.show(MeetingsRoomMeetings.this);
//            CheckSetupModelRequest checkSetupModelRequest = new CheckSetupModelRequest(empData.getEmail().trim());
//            apiViewModel.checkSetup(getApplicationContext(), checkSetupModelRequest);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Logout");
            builder.setMessage("Are you sure you want to Logout?");
            builder.setPositiveButton("Yes", (dialog, which) -> {
                sharedPreferences1.edit().clear().apply();
                editor1.clear();
                editor1.apply();
                myDb.clearDatabase("token_table");
                Intent intent = new Intent(MeetingsRoomMeetings.this, LoginMicrosoftADActivity.class);
                startActivity(intent);
                finish();
                dialog.dismiss();
            });

            builder.setNeutralButton("Cancel", (dialog, which) -> {
                dialog.dismiss();
            });
            AlertDialog dialog = builder.create();
            dialog.show();

        });

        apiViewModel.getappuserloginResponse().observe(this, response -> {
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
//                    otp = Conversions.getNDigitRandomNumber(4);
//                    editor1.putString("ProvizitOtp", String.valueOf(otp));
//                    editor1.putString("ProvizitEmail", empData.getEmail().trim());
//                    editor1.commit();
//                    editor1.apply();
//                    OtpSendEmaiModelRequest otpSendEmaiModelRequest = new OtpSendEmaiModelRequest(empData.getEmail().trim(), empData.getEmail().trim(), aesUtil.encrypt(String.valueOf(otp), "egems_2013_grms_2017_provizit_2020"), aesUtil.encrypt(String.valueOf(otp), empData.getEmail().trim()));
//                    apiViewModel.otpsendemail(getApplicationContext(), otpSendEmaiModelRequest);
                }
            } else {
                ProgressLoader.hide();
            }
        });

        apiViewModel.getotpsendemailResponse().observe(this, response -> {
            ProgressLoader.hide();
            if (response != null) {
                Intent intent = new Intent(MeetingsRoomMeetings.this, OtpActivity.class);
                intent.putExtra("activity_type", "Logout");
                overridePendingTransition(R.anim.enter, R.anim.exit);
                startActivity(intent);
            }
        });

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
                    comp_id = items.getCid();
                    Azure_status = items.isAzure();
                    if (items.isAzure()) {
                        ProgressLoader.hide();
                        LoginType = "Azure";
                        microsoft_ad(items.getClientid(), items.getTenantid(), items.getClientsecret());
                    } else if (items.isTwofa()) {
                        ProgressLoader.hide();
                        Intent intent = new Intent(MeetingsRoomMeetings.this, InitialActivity.class);
                        intent.putExtra("email", empData.getEmail().trim());
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
                try {
                    if (LoginType.equalsIgnoreCase("Azure")) {
                        CompanyData items = new CompanyData();
                        items = response.getItems();
                        if (items != null && items.getRoleDetails() != null) {
                            if (items.getRoleDetails().getMeeting().equals("true") || items.getRoleDetails().getApprover().equals("true") || items.getRoleDetails().getRmeeting().equals("true")) {
                                SharedPreferences sharedPreferences11 = MeetingsRoomMeetings.this.getSharedPreferences("EGEMSS_DATA", MODE_PRIVATE);
                                editor1 = sharedPreferences11.edit();
                                editor1.putString("company_id", comp_id);
                                editor1.putString("link", items.getLink());
                                editor1.putInt("mverify", items.getMverify());
                                editor1.commit();
                                editor1.apply();
                                new CompanyDetails(MeetingsRoomMeetings.this).execute(items.getComp_id());
                                if (items.getVerify() == 1) {
                                    EmpData empData = new EmpData();
                                    items = response.getItems();
                                    String id = items.getEmp_id();
                                    empData = items.getEmpData();
                                    boolean b1 = myDb.insertEmp(id, empData);
                                    boolean b3 = myDb.insertRole(items.getRoleDetails());
                                    boolean b2 = myDb.insertTokenDetails("email", username.trim(), items.getLink(), 1);
                                    ProgressLoader.hide();
                                    Intent intent = new Intent(MeetingsRoomMeetings.this, OtpActivity.class);
                                    intent.putExtra("activity_type", "Azure");
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.enter, R.anim.exit);
                                } else {
                                    ProgressLoader.hide();
                                }
                            } else {
                                AlertDialog alertDialog = new AlertDialog.Builder(MeetingsRoomMeetings.this).setTitle("ACCESS DENIED").setMessage("You don't have access!").setPositiveButton(android.R.string.ok, (dialog, which) -> {
                                    Intent intent = new Intent(MeetingsRoomMeetings.this, InitialActivity.class);
                                    intent.putExtra("email", empData.getEmail().trim());
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.enter, R.anim.exit);
                                    dialog.cancel();
                                    finish();
                                }).show();
                            }
                        } else {
                            ProgressLoader.hide();
                        }
                    } else if (LoginType.equalsIgnoreCase("OTP")) {
//                        otp = Conversions.getNDigitRandomNumber(4);
//                        editor1.putString("ProvizitOtp", String.valueOf(otp));
//                        editor1.putString("ProvizitEmail", empData.getEmail().trim());
//                        editor1.commit();
//                        editor1.apply();
//                        //otp send api
//                        OtpSendEmaiModelRequest otpSendEmaiModelRequest = new OtpSendEmaiModelRequest(empData.getEmail().trim(), empData.getEmail().trim(), aesUtil.encrypt(String.valueOf(otp), "egems_2013_grms_2017_provizit_2020"), aesUtil.encrypt(String.valueOf(otp), empData.getEmail().trim()));
//                        apiViewModel.otpsendemail(getApplicationContext(), otpSendEmaiModelRequest);

                    } else {
                        ProgressLoader.hide();
                    }
                } catch (IndexOutOfBoundsException e) {
                    Log.e("error", e.getMessage());
                    ProgressLoader.hide();
                }
            } else {
                ProgressLoader.hide();
            }
        });

        apiViewModel.getotpsendemailResponse().observe(this, response -> {
            ProgressLoader.hide();
            if (response != null) {
                Intent intent = new Intent(MeetingsRoomMeetings.this, OtpActivity.class);
                intent.putExtra("activity_type", "Azure");
                overridePendingTransition(R.anim.enter, R.anim.exit);
                startActivity(intent);
            }
        });

    }

    private void getuserDetails() {
        DataManger dataManager = DataManger.getDataManager();
        dataManager.getuserDetails1(new Callback<Model>() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {

                final Model model = response.body();

                if (model != null) {
                    Integer statuscode = model.getResult();
                    Integer successcode = 200;
                    Integer failurecode = 401;
                    Integer not_verified = 404;
                    Integer invalid_time = 201;
                    if (statuscode.equals(failurecode)) {
                    } else if (statuscode.equals(invalid_time)) {
                        incorrectTimePopup();
                    } else if (statuscode.equals(successcode)) {
                        CompanyData items = model.getItems();
                        Log.d(String.valueOf(statuscode), items.toString());

                        companyAddressArrayList = new ArrayList<>();
                        companyAddressArrayList = items.getAddress();

                        adapter1 = new PeopleAdapter(MeetingsRoomMeetings.this, R.layout.row, R.id.lbl_name, companyAddressArrayList);
                        if (!items.getPic().isEmpty()) {
                            Picasso.get()
                                    .load(DataManger.IMAGE_URL + "/uploads/" + sharedPreferences1.getString("company_id", null) + "/" + items.getPic().get(0))
                                    .into(logo);

                        } else {
                            logo.setImageDrawable(getDrawable(R.drawable.logo));
                        }
                        logo.setVisibility(View.VISIBLE);

                        getbusyScheduledetails(comp_id, id, "");
//                        getoutlookappointments("upcoming", email, comp_id, start, end);

                    } else if (statuscode.equals(not_verified)) {
                    }
                }
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Log.e("Error", String.valueOf(t));
            }
        }, MeetingsRoomMeetings.this, sharedPreferences1.getString("company_id", ""));

    }

    private void getbusyScheduledetails(String comp_id, String id, String type) {
        DataManger dataManger = DataManger.getDataManager();
        dataManger.getbusyScheduledetails(new Callback<EmployeeScheduledetailsModel>() {
            @Override
            public void onResponse(Call<EmployeeScheduledetailsModel> call, Response<EmployeeScheduledetailsModel> response) {
                EmployeeScheduledetailsModel model = response.body();
                meetings = new ArrayList<>();

                if (model != null) {
                    Integer statuscode = model.getResult();
                    Integer successcode = 200,
                            failurecode = 401,
                            not_verified = 404;
                    if (statuscode.equals(successcode)) {
                        emp_shedulerlist = model.getItems();
                        ArrayList<CompanyData> meetingsArraylist = new ArrayList<>();
                        toDay = (new myCalendarData(0).getTimeMilli()) - Conversions.timezone();
                        SelectedDate = toDay;
                        for (int i = 0; i < emp_shedulerlist.size(); i++) {
                            if ("busyschedule".equals(emp_shedulerlist.get(i).getSupertype()) &&
                                    emp_shedulerlist.get(i).getStatus() == 0) {
                                Long from_date = emp_shedulerlist.get(i).getFrom_date();
                                Long to_date = emp_shedulerlist.get(i).getTo_date();
                                Long from_time = emp_shedulerlist.get(i).getFrom_time();
                                Long to_time = emp_shedulerlist.get(i).getTo_time();
                                String supertype = emp_shedulerlist.get(i).getSupertype();
                                long status = emp_shedulerlist.get(i).getStatus();
                                // Split the meeting by day
                                Calendar startCal = Calendar.getInstance();
                                startCal.setTimeInMillis(from_date * 1000); // Convert to milliseconds
                                Calendar endCal = Calendar.getInstance();
                                endCal.setTimeInMillis(to_date * 1000); // Convert to milliseconds
                                while (!startCal.after(endCal)) {
                                    Long dayStart = startCal.getTimeInMillis() / 1000; // Convert back to seconds
                                    Long dayEnd = (getEndOfADay(cal.getTime()).getTimeInMillis() / 1000) - timezone(); // End of day in seconds
                                    Long meetingStart = Math.max(from_time, dayStart);
                                    Long meetingEnd = Math.min(to_time, dayEnd);
                                    if (SelectedDate == toDay) {
                                        if (dayStart >= toDay && (dayStart < (toDay + (60 * 60 * 24 * 1)))) {
                                            if (meetingEnd >= cal.getTimeInMillis() / 1000 - Conversions.timezone()) {
                                                CompanyData meeting = new CompanyData();
                                                meeting.setStart(meetingStart);
                                                meeting.setEnd(meetingEnd);
                                                meeting.setSupertype(supertype);
                                                meetingsArraylist.add(meeting);
                                            }
                                        }
                                    }
                                    // Move to the next day
                                    startCal.add(Calendar.DATE, 1);
                                }
                            }

                        }
                        meetingsArraylist = insertionSort(meetingsArraylist);
                        meetings.addAll(meetingsArraylist);

                        // outlookmeetings
                        if (Azure_status != null && Azure_status) {
                            getoutlookappointments("upcoming", email, comp_id, start, end);
                        } else {
                            String s_time = String.valueOf((cal.getTimeInMillis() / 1000) - timezone());
                            String e_time = String.valueOf((getEndOfADay(cal.getTime()).getTimeInMillis() / 1000) - timezone());
                            getmeetings(s_time, e_time);
                        }

                    } else if (statuscode.equals(failurecode)) {
                        // Handle failure code
                    } else if (statuscode.equals(not_verified)) {
                        // Handle not verified code
                    }
                } else {
                }
            }

            @Override
            public void onFailure(Call<EmployeeScheduledetailsModel> call, Throwable t) {
            }
        }, MeetingsRoomMeetings.this, sharedPreferences1.getString("company_id", ""), empData.getEmp_id(), "busy");
    }

    private void getoutlookappointments(String type, String email, String comp_id, Long start, Long end) {
        DataManger dataManger = DataManger.getDataManager();
        dataManger.getoutlookappointments(new Callback<OutlookModel>() {
            @Override
            public void onResponse(Call<OutlookModel> call, Response<OutlookModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    OutlookModel model = response.body();

                    // Status code handling
                    if (model != null) {
                        Integer statuscode = model.getResult();
                        Integer successcode = 200, failurecode = 401, not_verified = 404;

                        if (statuscode.equals(failurecode)) {
                            // Handle failure
                            return;  // Exit if failure
                        } else if (statuscode.equals(not_verified)) {
                            // Handle not verified
                            return;  // Exit if not verified
                        } else if (!statuscode.equals(successcode)) {
                            // Handle unexpected status codes
                            return;
                        }
                    }
                    outLookMeetings = model.getItems();
                    ArrayList<CompanyData> meetingsArraylist = new ArrayList<>();
                    if (outLookMeetings != null && !outLookMeetings.isEmpty()) {
                        for (int i = 0; i < outLookMeetings.size(); i++) {
                            CompanyData meeting = new CompanyData();

                            // Convert t_start and t_end to IST
                            long t_start_IST = convertToIST(outLookMeetings.get(i).t_start);
                            long t_end_IST = convertToIST(outLookMeetings.get(i).t_end);

                            meeting.setStart(t_start_IST);  // Use the converted IST time
                            meeting.setEnd(t_end_IST);  // Use the converted IST time
                            meeting.setSupertype(outLookMeetings.get(i).getSupertype());

                            Log.e("t_start_",outLookMeetings.get(i).t_start+"");
                            Log.e("t_start_",outLookMeetings.get(i).start+"");
                            Log.e("t_end_",outLookMeetings.get(i).t_end+"");
                            Log.e("t_end_",outLookMeetings.get(i).end+"");

//                            meetingsArraylist.add(meeting);
                            meetings.add(meeting);
                        }
                    }
                    // Sort and update meetings
                    if (outLookMeetings != null) {
                        outLookMeetings.clear();
                    }
                    meetingsArraylist = insertionSort(meetingsArraylist);
                    meetings.addAll(meetingsArraylist);

                    String s_time = String.valueOf((cal.getTimeInMillis() / 1000) - timezone());
                    String e_time = String.valueOf((getEndOfADay(cal.getTime()).getTimeInMillis() / 1000) - timezone());

                    getmeetings(s_time, e_time);

                } else {
                    //Handle the case where the response is not successful or body is null
                }
            }

            @Override
            public void onFailure(Call<OutlookModel> call, Throwable t) {
            }
        }, type, empData.getEmail(), sharedPreferences1.getString("company_id", ""), start, end);
    }

    private long convertToIST(long utcTime) {
        // Convert UTC time to IST (UTC+5:30)
//        return utcTime + 19800000L; // 5 hours 30 minutes in milliseconds
        return utcTime + 10800000L; // 3 hours in milliseconds
    }

    private void getmeetings(String s_time, String e_time) {
        DataManger dataManager = DataManger.getDataManager();
        dataManager.getmeetings(new Callback<Model1>() {
            @Override
            public void onResponse(Call<Model1> call, Response<Model1> response) {
                Model1 model = response.body();
                if (model != null) {
                    Integer statuscode = model.getResult();
                    Integer successcode = 200, failurecode = 401, not_verified = 404;
                    if (statuscode.equals(failurecode)) {
                        // Handle failure
                    } else if (statuscode.equals(not_verified)) {
                        // Handle not verified
                    } else if (statuscode.equals(successcode)) {
                        ArrayList<CompanyData> meetingsArraylist = new ArrayList<>();

                        meetings1 = model.getItems();
                        for (int i = 0; i < meetings1.size(); i++) {
                            if (meetings1.get(i).getSupertype().equals("meeting")) {
                                CompanyData meeting = new CompanyData();
                                EmpData host = new EmpData();
                                meeting.setStart(meetings1.get(i).getStart());
                                meeting.setEnd(meetings1.get(i).getEnd());
                                meeting.setSupertype(meetings1.get(i).getSupertype());
                                meetingsArraylist.add(meeting);
                            }
                        }

                        meetingsArraylist = insertionSort(meetingsArraylist);
                        meetings.addAll(meetingsArraylist);
                        meetings = insertionSort(meetings);
//                        ledColors();

                    } else {
                        // Handle the case when meetings or meetings1 is null
                    }
                }
            }

            @Override
            public void onFailure(Call<Model1> call, Throwable t) {
                // Handle failure
            }
        }, MeetingsRoomMeetings.this, "custom", empData.getEmp_id(), s_time, e_time);
    }

    private void microsoft_ad(String clientid, String tenantid, String clientsecret) {
        editor1.putString("company_id", comp_id);
        editor1.commit();
        editor1.apply();
        Company_ID = comp_id;
        AUTHORITY = "https://login.microsoftonline.com/" + tenantid;
        REDIRECT_URI = "msauth://com.provizit.dnp/RNy5oraIA7QxAEY9MB%2FZ5j%2FwWgo%3D";
        CLIENT_ID = clientid;

        // Create PublicClientApplication instance
        PublicClientApplication.create(getApplicationContext(), CLIENT_ID, AUTHORITY, REDIRECT_URI, new IPublicClientApplication.ApplicationCreatedListener() {
            @Override
            public void onCreated(IPublicClientApplication application) {
                // Handle application creation success
                String[] scopes = {"User.Read", "Mail.Read"};
                application.acquireToken(MeetingsRoomMeetings.this, scopes, new AuthenticationCallback() {
                    @Override
                    public void onSuccess(IAuthenticationResult authenticationResult) {
                        IAccount account = authenticationResult.getAccount();
                        if (account != null) {
                            // Get the username from the account
                            username = account.getUsername();
                            String id = account.getId();
                            JsonObject gsonObject = new JsonObject();
                            JSONObject jsonObj_ = new JSONObject();
                            try {
                                jsonObj_.put("id", comp_id);
                                jsonObj_.put("val", username.trim());
                                String encryptPWD = DataManger.Pwd_encrypt(getApplicationContext(), id, username.trim());
                                jsonObj_.put("adval", encryptPWD);
                                JsonParser jsonParser = new JsonParser();
                                gsonObject = (JsonObject) jsonParser.parse(jsonObj_.toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            apiViewModel.userADlogin(getApplicationContext(), gsonObject);
                            ProgressLoader.show(MeetingsRoomMeetings.this);
                        }
                    }
                    @Override
                    public void onError(MsalException exception) {
                        // Handle authentication error
                        exception.printStackTrace();
                    }
                    @Override
                    public void onCancel() {
                        // Handle user cancellation
                    }
                });
            }
            @Override
            public void onError(MsalException exception) {
                // Handle application creation error
                exception.printStackTrace();
            }
        });
    }

    public String adbcommand(String command) {
        Process process = null;
        DataOutputStream os = null;
        String excresult = "";
        try {
            process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(command + "\n");
            os.writeBytes("exit\n");
            os.flush();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    process.getInputStream()));
            StringBuilder stringBuffer = new StringBuilder();
            String line = null;
            while ((line = in.readLine()) != null) {
                stringBuffer.append(line + " ");
            }
            excresult = stringBuffer.toString();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return excresult;
    }

    public void ResouresIds() {
        presentTime = findViewById(R.id.presentTime);
        invitee1layout = findViewById(R.id.invitee1layout);
        m_time = findViewById(R.id.m_time);
        logo = findViewById(R.id.logo);
        m_Status = findViewById(R.id.m_Status);
        meetingRoom = findViewById(R.id.meetingRoom);
        hostImg = findViewById(R.id.hostImg);
        employee_img = findViewById(R.id.employee_img);
        hostName = findViewById(R.id.hostName);
        invitee1Image = findViewById(R.id.invitee1Image);
        invitee2Image = findViewById(R.id.invitee2Image);
        invitee1Name = findViewById(R.id.invitee1Name);
        invitee2Name = findViewById(R.id.invitee2Name);
        recyclerView = findViewById(R.id.recyclerView);
        invitee2layout = findViewById(R.id.invitee2layout);
        m_timeText = findViewById(R.id.m_timeText);
        m_start_endtime = findViewById(R.id.m_start_endtime);
        meetinglayout = findViewById(R.id.meetinglayout);
        employee_name = findViewById(R.id.employee_name);
        designation = findViewById(R.id.designation);
        department = findViewById(R.id.department);
        count = findViewById(R.id.count);
        inviteesT = findViewById(R.id.inviteesT);
        confidentialData = findViewById(R.id.Confidential);

    }

    private void ledColors() {
        p_time = (Calendar.getInstance().getTimeInMillis() / 1000);

        if (meetings != null && !meetings.isEmpty()) {
            meetinglayout.setVisibility(View.VISIBLE);
            long Mstarttime = meetings.get(0).getStart() + timezone();
            long Mendtime = meetings.get(0).getEnd() + timezone() + 1;
            meetingsAdapter = new MeetingsAdapter(MeetingsRoomMeetings.this, meetings);
            recyclerView.setAdapter(meetingsAdapter);
            if (p_time >= Mstarttime && p_time < Mendtime) {
                //Red Color
                adbcommand("echo w 0x04 > ./sys/devices/platform/led_con_h/zigbee_reset");
                m_time.setCardBackgroundColor(getResources().getColor(R.color.inprogress));
                employee_img.setBorderColor(getResources().getColor(R.color.inprogress));
                employee_img.setBorderWidth(10);
                m_Status.setText("Busy");
                m_timeText.setText("Busy");
                String s_time = millitotime((meetings.get(0).getStart() + timezone()) * 1000, false);
                String e_time = millitotime((meetings.get(0).getEnd() + 1 + timezone()) * 1000, false);
                // Get the current layout parameters
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) m_timeText.getLayoutParams();
                // Set the desired bottom margin (e.g., 20dp)
                int bottomMarginInDp = 35;
                float scale = getResources().getDisplayMetrics().density;  // To convert dp to pixels
                int bottomMarginInPx = (int) (bottomMarginInDp * scale + 0.5f);

                // Update the bottom margin
                params.setMargins(params.leftMargin, params.topMargin, params.rightMargin, bottomMarginInPx);

                // Apply the updated layout parameters to the TextView
                m_timeText.setLayoutParams(params);
                m_start_endtime.setText(s_time + " to " + e_time);
                m_start_endtime.setVisibility(View.VISIBLE);
            } else if (p_time >= Mendtime) {
                meetings.remove(meetings.get(0));
                ledColors();
            } else if (p_time < Mstarttime && Mstarttime - p_time >= 3600) {
                adbcommand("echo w 0x05 > ./sys/devices/platform/led_con_h/zigbee_reset");
                meetinglayout.setVisibility(View.GONE);
                m_Status.setText("");
                m_timeText.setText("Available");
                m_time.setCardBackgroundColor(getResources().getColor(R.color.nextmeeting));
                employee_img.setBorderColor(getResources().getColor(R.color.nextmeeting));
                employee_img.setBorderWidth(10);
                m_start_endtime.setVisibility(View.GONE);
            } else {
                //Greeen
                adbcommand("echo w 0x05 > ./sys/devices/platform/led_con_h/zigbee_reset");
                m_Status.setText("Next meeting");
                m_timeText.setText("Available");
                m_time.setCardBackgroundColor(getResources().getColor(R.color.nextmeeting));
                employee_img.setBorderColor(getResources().getColor(R.color.nextmeeting));
                employee_img.setBorderWidth(10);
                m_start_endtime.setVisibility(View.GONE);
            }
        } else {
            Log.e("testEmpty_",meetings.size()+"");
            adbcommand("echo w 0x05 > ./sys/devices/platform/led_con_h/zigbee_reset");
            m_time.setCardBackgroundColor(getResources().getColor(R.color.nextmeeting));
            employee_img.setBorderColor(getResources().getColor(R.color.nextmeeting));
            employee_img.setBorderWidth(10);
            meetinglayout.setVisibility(View.GONE);
            confidentialData.setVisibility(View.GONE);

            m_timeText.setText("Available");
            m_Status.setText("");
            meetingsAdapter = new MeetingsAdapter(MeetingsRoomMeetings.this, new ArrayList<CompanyData>());
            recyclerView.setAdapter(meetingsAdapter);
            recyclerView.setVisibility(View.VISIBLE);
            m_start_endtime.setVisibility(View.GONE);
        }
    }

    private void autoShutdown(int day) {
        DataManger dataManager = DataManger.getDataManager();
        dataManager.getworkingdaymrddetails(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {

                final Model model = response.body();

                if (model != null) {
                    Integer statuscode = model.getResult();
                    Integer successcode = 200;
                    Integer failurecode = 401;
                    Integer not_verified = 404;
                    Integer invalid_time = 201;
                    if (statuscode.equals(failurecode)) {
                    } else if (statuscode.equals(invalid_time)) {
                        incorrectTimePopup();
                    } else if (statuscode.equals(successcode)) {
                        if (model.getItems() != null && model.getItems().getToday() != null) {
                            if (model.getItems().getToday().getName().equals("THUR")) {
                                model.getItems().getToday().setName("THU");
                            }
                            if (model.getItems().getNextday().getName().equals("THUR")) {
                                model.getItems().getNextday().setName("THU");
                            }
                            SimpleDateFormat day = new SimpleDateFormat("EEE");
                            Calendar calendar = Calendar.getInstance();
                            String presentHour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
                            String presentMin = String.valueOf(calendar.get(Calendar.MINUTE));
                            if (presentMin.length() == 1) {
                                presentMin = 0 + presentMin;
                            }
                            String presentTimeNow = presentHour + presentMin;
                            String todayEndTime = model.getItems().getToday().getEnd();
                            todayEndTime = todayEndTime.replace(":", "");
                            String todayStartTime = model.getItems().getToday().getStart();
                            todayStartTime = todayStartTime.replace(":", "");
                            int presentTimeInt = Integer.parseInt(presentTimeNow);
                            int todayEndTimeInt = Integer.parseInt(todayEndTime);
                            int todayStartTimeInt = Integer.parseInt(todayStartTime);
                            if (day.format(calendar.getTime()).toUpperCase().equals(model.getItems().getToday().getName())) {
                                if (presentTimeInt >= todayEndTimeInt) {
                                    String autoshutDown = afterEndTime(model.getItems().getNextday().getStart(), model.getItems().getNextday().getName());

                                    adbcommand("/system/xbin/test " + autoshutDown + " enable");
                                } else if (presentTimeInt < todayStartTimeInt) {

                                    String autoshutDown = beforeStartTime(model.getItems().getToday().getStart());
                                    adbcommand("/system/xbin/test " + autoshutDown + " enable");
                                } else {
                                    String autoshutDown = checkNextDay(model.getItems().getToday().getEnd(), model.getItems().getNextday().getStart(), model.getItems().getNextday().getName());
                                    adbcommand("/system/xbin/test " + autoshutDown + " enable");
                                }
                            } else {
                                String autoshutDown = TodayHolidayDeviceOpenned(model.getItems().getToday().getStart(), model.getItems().getToday().getName());
                                adbcommand("/system/xbin/test " + autoshutDown + " enable");
                            }
                        }
                    } else if (statuscode.equals(not_verified)) {
                    }
                }
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Log.e("Error", String.valueOf(t));
            }
        }, MeetingsRoomMeetings.this, String.valueOf(day));

    }

    private void incorrectTimePopup() {
        final Dialog invalidTimedialog = new Dialog(MeetingsRoomMeetings.this);
        invalidTimedialog.setCancelable(false);
        invalidTimedialog.setContentView(R.layout.invalidtimepopup);
        Objects.requireNonNull(invalidTimedialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Button close = invalidTimedialog.findViewById(R.id.ok_button);
        close.setOnClickListener(v -> {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

            invalidTimedialog.dismiss();
        });
        invalidTimedialog.show();


    }

    public ArrayList<CompanyData> insertionSort(ArrayList<CompanyData> meetingsA) {
        ArrayList<CompanyData> meetingsSort = new ArrayList<>();
        meetingsSort = meetingsA;
        int n = meetingsSort.size();
        int i;
        int j;
        CompanyData key;
        for (i = 1; i < n; i++) {
            key = meetingsSort.get(i);
            j = i - 1;

        /* Move elements of arr[0..i-1], that are
        greater than key, to one position ahead
        of their current position */
            while (j >= 0 && meetingsSort.get(j).getStart() > key.getStart()) {
                meetingsSort.set(j + 1, meetingsSort.get(j));
                j = j - 1;
            }
            meetingsSort.set(j + 1, key);
        }
        return meetingsSort;
    }

    private void otp_send() {
        JsonObject gsonObject = new JsonObject();
        JSONObject jsonObj_ = new JSONObject();
        try {
            jsonObj_.put("id", comp_id);
            jsonObj_.put("val", empData.getEmail().trim());
            String encryptPWD = DataManger.Pwd_encrypt(getApplicationContext(), String.valueOf(otp), empData.getEmail().trim());
            jsonObj_.put("adval", encryptPWD);
            JsonParser jsonParser = new JsonParser();
            gsonObject = (JsonObject) jsonParser.parse(jsonObj_.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        apiViewModel.userADlogin(getApplicationContext(), gsonObject);
    }

    public static class EthernetDataReceiver extends BroadcastReceiver {
        private static final long CHECK_INTERVAL = 1000; // Check every second
        private boolean isChecking = false;
        private final Handler handler = new Handler(Looper.getMainLooper());
        private final Runnable checkRunnable = new Runnable() {
            @Override
            public void run() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (isEthernetReceivingData()) {
                        Log.d("EthernetDataReceiver", "Ethernet is receiving data");
                        // Ethernet is receiving data
                        // Handle the case when Ethernet is receiving data
                    } else {
                        Log.d("EthernetDataReceiver", "Ethernet is not receiving data");
                        // Ethernet is not receiving data
                        // Handle the case when Ethernet is not receiving data
                    }
                }
                // Schedule the next check
                if (isChecking) {
                    handler.postDelayed(this, CHECK_INTERVAL);
                }
            }
        };

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() != null && intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    checkEthernetDataStatus(context);
                }
            }
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        private boolean isEthernetReceivingData() {
            long rxBytes = TrafficStats.getTotalRxBytes();
            try {
                Thread.sleep(1000); // Sleep for a second
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long newRxBytes = TrafficStats.getTotalRxBytes();

            // If the difference in received bytes is greater than 0, it means data is being received.
            return (newRxBytes - rxBytes) > 0;
        }

        private void checkEthernetDataStatus(Context context) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager != null && isEthernet(connectivityManager)) {
                // Start periodic checking
                startChecking();
            } else {
                // Stop checking if not connected to Ethernet
                stopChecking();
            }
        }

        private boolean isEthernet(ConnectivityManager connectivityManager) {
            return connectivityManager.getActiveNetworkInfo() != null &&
                    connectivityManager.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_ETHERNET;
        }

        private void startChecking() {
            isChecking = true;
            handler.post(checkRunnable);
        }

        private void stopChecking() {
            isChecking = false;
            handler.removeCallbacks(checkRunnable);
        }
    }

    public class MeetingsAdapter extends RecyclerView.Adapter<MeetingsAdapter.MeetingViewHolder> {
        ArrayList<CompanyData> meetigsArrayList;
        private final Context context;

        public MeetingsAdapter(Context context, ArrayList<CompanyData> meetigsArrayList) {
            this.meetigsArrayList = meetigsArrayList;
            this.context = context;
        }

        @NonNull
        @Override
        public MeetingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meetings_listcards, parent, false);
            return new MeetingViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MeetingViewHolder holder, int position) {

            if (meetigsArrayList.isEmpty()) {
                holder.empty_card.setVisibility(View.VISIBLE);
                holder.l1.setVisibility(View.GONE);
                holder.l2.setVisibility(View.GONE);
            } else {
                Log.e("getT_start_",meetigsArrayList.get(position).getT_start()+"");
                Log.e("getT_start_",meetigsArrayList.get(position).getStart()+"");
                Log.e("getT_end_",meetigsArrayList.get(position).getT_end()+"");
                Log.e("getT_end_",meetigsArrayList.get(position).getEnd()+"");
                String supertype = meetigsArrayList.get(position).getSupertype();
                String s_date = millitoDate((meetigsArrayList.get(position).getStart() + timezone()) * 1000);
                String s_time = millitotimeTest(meetigsArrayList.get(position).getStart() * 1000, false);
                String e_time = millitotimeTest(meetigsArrayList.get(position).getEnd() * 1000, false);
                holder.m_start.setText(s_time);
                holder.m_end.setText(e_time);
                holder.m_end.setVisibility(View.VISIBLE);
//                holder.txtType.setText(meetigsArrayList.get(position));
            }

        }

        @Override
        public int getItemCount() {
            if (meetigsArrayList == null || meetigsArrayList.isEmpty()) {
                return 6;
            } else if (p_time < meetigsArrayList.get(0).getStart() + timezone() && meetigsArrayList.get(0).getStart() + timezone() - p_time >= 3600) {
                return meetigsArrayList.size();
            } else {
                return meetigsArrayList.size();
            }
        }

        public class MeetingViewHolder extends RecyclerView.ViewHolder {
            TextView txtType, m_start, m_end, empty, slash;
            TextView confidential_text, non_confidential_text;
            LinearLayout static_card;
            CardView empty_card;
            LinearLayout l1;
            LinearLayout l2;

            public MeetingViewHolder(@NonNull View itemView) {
                super(itemView);
                txtType = itemView.findViewById(R.id.txtType);
                m_start = itemView.findViewById(R.id.m_start);
                m_end = itemView.findViewById(R.id.m_end);
                slash = itemView.findViewById(R.id.slash);
                static_card = itemView.findViewById(R.id.static_card);
                empty_card = itemView.findViewById(R.id.empty_card);
                l1 = itemView.findViewById(R.id.l1);
                l2 = itemView.findViewById(R.id.l2);
                confidential_text = itemView.findViewById(R.id.confidential_text);
                non_confidential_text = itemView.findViewById(R.id.non_confidential_text);
            }
        }
    }

}