package com.example.BootServer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by hanbowen on 2014/8/28.
 */
public class BootReceiver extends BroadcastReceiver {
    public static final String TAG = "BootReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service = new Intent(context, AntiArpService.class);
        context.startService(service);
        Log.d(TAG, "starting Service");
    }

}
