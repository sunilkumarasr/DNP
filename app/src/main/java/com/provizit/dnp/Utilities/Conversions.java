package com.provizit.dnp.Utilities;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;

import com.provizit.dnp.Services.DataManger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Conversions {
    private static Locale locale;
    public static ArrayList<Agenda> agenda;

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static String encrypt(Context context, Boolean isAdmin) {

        AESUtil aesUtil = new AESUtil(context);
        Calendar calendar = Calendar.getInstance();
        if (isAdmin) {
            return aesUtil.encrypt("admin_" + ((calendar.getTimeInMillis() / 1000) - Conversions.timezone() - 60), "egems_2013_grms_2017_provizit_2020");
        } else {
            if (context != null) {
                SharedPreferences sharedPreferences1 = context.getSharedPreferences("EGEMSS_DATA", MODE_PRIVATE);
                return aesUtil.encrypt(sharedPreferences1.getString("company_id", null) + "_" + ((calendar.getTimeInMillis() / 1000) - Conversions.timezone() - 60), "egems_2013_grms_2017_provizit_2020");
            }
            return "";
        }

    }



    public static AnimationSet animation() {
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
        fadeIn.setDuration(300);
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator()); //and this
        fadeOut.setStartOffset(300);
        fadeOut.setDuration(300);
        AnimationSet animation = new AnimationSet(false); //change to false
        animation.addAnimation(fadeIn);
//        animation.addAnimation(fadeOut);

        return animation;
    }

    public static String addChar(String str, String ch, int position) {
        StringBuilder sb = new StringBuilder(str);
        sb.insert(position, ch);
        return sb.toString();
    }

    public static int timezone() {
        Date d = new Date();
        int timeZone = d.getTimezoneOffset() * 60;
        return timeZone;
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

    public static String millisTotime(Long millSec, Boolean ishour) {
        locale = new Locale(DataManger.appLanguage);
        DateFormat simple = new SimpleDateFormat("mm", locale);

        if (ishour) {
            simple = new SimpleDateFormat("HH");
        }
        Date result = new Date(millSec);
        String time = simple.format(result) + "";
        return time;
    }

    public static String millitodate(Long millSec) {
        DateFormat simple = new SimpleDateFormat("EE dd MMM yyyy");
        Date result = new Date(millSec);
        String time = simple.format(result) + "";
        return time;
    }

    public static String dateToFormat(String normal_date) {
        //Mar 13, 2024
        String outputDateString = "";
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
        Date date = null;
        try {
            date = inputDateFormat.parse(normal_date);
            outputDateString = outputDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outputDateString;
    }

    public static String millitodateD(Long millSec) {
        locale = new Locale(DataManger.appLanguage);
        DateFormat simple = new SimpleDateFormat("E, dd MMM yyyy", locale);
        Date result = new Date(millSec);
        String time = simple.format(result) + "";
        return time;
    }

    public static String millitodateLabel(Long millSec) {
        locale = new Locale(DataManger.appLanguage);
        DateFormat simple = new SimpleDateFormat("E MMM dd", locale);
        Date result = new Date(millSec);
        String time = simple.format(result) + "";
        return time;
    }

    public static long getstamp(String time) {
        String str_date = time;
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = null;
        Calendar calendar = Calendar.getInstance();
        try {
            date = (Date) formatter.parse(str_date);
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (calendar.getTimeInMillis() / 1000) - Conversions.timezone();
    }

    public static long gettimestamp(String date_selected, String time) {
        String str_date = date_selected + " " + time;
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Date date = null;
        Calendar calendar = Calendar.getInstance();
        try {
            date = (Date) formatter.parse(str_date);
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (calendar.getTimeInMillis() / 1000) - Conversions.timezone();
    }

    //format like-> Sat Feb 10
    public static long getdatestamp(String date_selected) {
        String str_date = date_selected;
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        Calendar calendar = Calendar.getInstance();
        try {
            date = (Date) formatter.parse(str_date);
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (calendar.getTimeInMillis() / 1000) - Conversions.timezone();
    }

    public static String Capitalize(String value) {
        return value.substring(0, 1).toUpperCase() + value.substring(1);
    }

    public static String datemillirandstring() {

        Date now = new Date();
        long current = now.getTime() / 1000;

        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder(10);
        for (int i = 0; i < 10; i++) {
            int index = (int) (AlphaNumericString.length()  * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }
        return sb.toString() + current;
    }

    public static String convertToArabic(String value) {
        if (DataManger.appLanguage.equals("ar")) {
            String newValue = (((((((((((value + "")
                    .replaceAll("1", "١")).replaceAll("2", "٢"))
                    .replaceAll("3", "٣")).replaceAll("4", "٤"))
                    .replaceAll("5", "٥")).replaceAll("6", "٦"))
                    .replaceAll("7", "٧")).replaceAll("8", "٨"))
                    .replaceAll("9", "٩")).replaceAll("0", "٠"));
            return newValue;
        } else {
            return value;
        }
    }

    public static int getNDigitRandomNumber(int n) {
        int upperLimit = getMaxNDigitNumber(n);
        int lowerLimit = getMinNDigitNumber(n);
        int s = getRandomNumber(upperLimit);
        if (s < lowerLimit) {
            s += lowerLimit;
        }
        return s;
    }

    private static int getMaxNDigitNumber(int n) {
        int s = 0;
        int j = 10;
        for (int i = 0; i < n; i++) {
            int m = 9;
            s = (s * j) + m;
        }
        return s;
    }


    private static int getMinNDigitNumber(int n) {
        int s = 0;
        int j = 10;
        for (int i = 0; i < n - 1; i++) {
            int m = 9;
            s = (s * j) + m;
        }
        return s + 1;
    }


    public static int getRandomNumber(int maxLimit) {
        return (int) (Math.random() * maxLimit);
    }

    public static void setAgenda(ArrayList<Agenda> agenda1) {
        agenda = agenda1;
    }


}
