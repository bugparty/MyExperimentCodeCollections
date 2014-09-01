package com.example.ArpProtector;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


/**
 * Created by hanbowen on 2014/8/28.
 */
public class AntiArpService extends Service {
    public static final String TAG = "AntiArpService";
    private AntiArpBinder mBinder = new AntiArpBinder();
    NotificationCompat.Builder mBuilder =
            new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.shield_blue)
                    .setContentTitle("Arp Protector")
                    .setContentText("Protecting!");
    // Sets an ID for the notification
    int mNotificationId = 001;
    // Gets an instance of the NotificationManager service
    NotificationManager mNotifyMgr;
    private Monitor monitor = null;
    @Override
    public void onCreate() {
        super.onCreate();
        mNotifyMgr =(NotificationManager)
                getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
    }

    public AntiArpService() {
        super();

    }

    public IBinder onBind(Intent arg0) {
        Log.d(TAG, "onBind");
        if(monitor!= null){
            if(!monitor.isAlive()) {
                Log.d(TAG, "monitor seems dead,starting");
                monitor = new Monitor(this);
                monitor.start();
            }
        }else{
            Log.i(TAG, "monitor is null");
            monitor = new Monitor(this);
            monitor.start();
        }
        return mBinder;
    }
    public String getGatewayIP(){
        WifiManager wm = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        return IPV4.int2str(wm.getDhcpInfo().gateway);

    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        monitor = new Monitor(this);
        monitor.start();



// 在这里创建线程
        return START_STICKY;
    }
    class Monitor extends  Thread{
        public final static String TAG = "MonitorThread";
        AntiArpService mContext;

        Monitor(AntiArpService mContext) {
            this.mContext = mContext;
        }
        private void warn(){
            // Builds the notification and issues it.
            mNotifyMgr.notify(mNotificationId, mBuilder.setSmallIcon(R.drawable.shield_red)
            .setContentText("warining!")
            .build());
        }
        @Override
        public void run() {
            super.run();
            String gateway = mContext.getGatewayIP();
            String firstGateMac = ArpReader.readByIP(gateway);
            String lastMac;
            mNotifyMgr.notify(mNotificationId, mBuilder.build());
            while(!isInterrupted()){
                Log.d(TAG, "detecting arp table");
                lastMac = ArpReader.readByIP(gateway);
                if(lastMac == null){
                    Log.d(TAG, "can`t find gateway ip");
                    continue;
                }
                if(!lastMac.equals(firstGateMac)){
                    Log.d(TAG, "diff mac, possible attract");
                    warn();
                    return;
                }
                try{
                    sleep(1000);
                }catch (InterruptedException e){
                    return;
                }

            }


        }
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