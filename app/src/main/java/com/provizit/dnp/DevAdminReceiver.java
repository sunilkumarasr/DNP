package com.provizit.dnp;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.UserManager;
import android.util.Log;

import androidx.annotation.NonNull;

public class DevAdminReceiver extends DeviceAdminReceiver{
    private static final String TAG = "1" ;

    @Override
    public void onEnabled(@NonNull Context context, @NonNull Intent intent) {
        super.onEnabled(context, intent);
        UserManager userManager = (UserManager) context.getSystemService(Context.USER_SERVICE);
        long serialNumber = userManager.getSerialNumberForUser(Binder.getCallingUserHandle());
        Log.i(TAG, "Device admin enabled in user with serial number: " + serialNumber);
    }
}
