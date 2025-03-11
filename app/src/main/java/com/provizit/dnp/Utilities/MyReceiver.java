package com.provizit.dnp.Utilities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


import com.provizit.dnp.Activities.SplashActivity;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;

public class MyReceiver extends BroadcastReceiver {
    private static final String ACTION_BOOT = "android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(ACTION_BOOT)) {
            Intent i = new Intent(context, SplashActivity.class);
//        Toast.makeText(context,"Restart app here", Toast.LENGTH_LONG).show();
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
            adbcommand("echo w 0x06 > ./sys/devices/platform/led_con_h/zigbee_reset");
            Log.i("Jessica","121212");


        }

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
            // System.out.println(excresult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return excresult;
    }
}
