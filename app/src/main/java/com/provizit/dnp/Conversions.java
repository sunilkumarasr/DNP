package com.provizit.dnp;
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
import java.util.Calendar;
import java.util.Date;

public class Conversions {

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
                System.out.println("COMPIDrt::"+sharedPreferences1.getString("company_id", null));
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

    public static int timezone() {
        Date d = new Date();
        int timeZone = d.getTimezoneOffset() * 60;
        return timeZone;
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

}
