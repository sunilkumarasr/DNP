package com.provizit.dnp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.widget.Toast;

public class BatteryBroadcastReceiver extends BroadcastReceiver {
    int deviceStatus;
    @Override
    public void onReceive(Context context, Intent intent) {

        deviceStatus = intent.getIntExtra(BatteryManager.EXTRA_STATUS,-1);
        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        int batteryLevel=(int)(((float)level / (float)scale) * 100.0f);

        if(deviceStatus == BatteryManager.BATTERY_STATUS_CHARGING){
            Toast.makeText(context, "BATTERY_STATUS_CHARGING"+batteryLevel+" %", Toast.LENGTH_SHORT).show();
        }
        if(deviceStatus == BatteryManager.BATTERY_STATUS_DISCHARGING) {
            Toast.makeText(context, " = Discharging at " + batteryLevel + " %", Toast.LENGTH_SHORT).show();
        }
        if (deviceStatus == BatteryManager.BATTERY_STATUS_FULL){
            Toast.makeText(context, "= Battery Full at "+batteryLevel+" %", Toast.LENGTH_SHORT).show();
        }
        if(deviceStatus == BatteryManager.BATTERY_STATUS_UNKNOWN){
            Toast.makeText(context, " = Unknown at "+batteryLevel+" %", Toast.LENGTH_SHORT).show();
        }
        if (deviceStatus == BatteryManager.BATTERY_STATUS_NOT_CHARGING){
            Toast.makeText(context, " = Not Charging at "+batteryLevel+" %", Toast.LENGTH_SHORT).show();
        }

    }
}