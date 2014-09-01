package com.example.BootServer;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by hanbowen on 2014/8/28.
 */
public class AntiArpService extends Service {
    public static final String TAG = "AntiArpService";
    private AntiArpBinder mBinder = new AntiArpBinder();
    @Override
    public IBinder onBind(Intent arg0) {
        Log.d(TAG, "onBind");
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
// 在这里创建线程
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "The background service is destroyed!!!");
    }
    class AntiArpBinder extends Binder {
        AntiArpService getService(){
            return AntiArpService.this;
        }
    }
}