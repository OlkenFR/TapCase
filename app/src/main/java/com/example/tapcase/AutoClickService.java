package com.example.tapcase;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import androidx.annotation.Nullable;
import java.util.Timer;
import java.util.TimerTask;

public class AutoClickService extends Service {

    private Timer timer;
    private Handler handler;
    private String test = "Hello World";
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }

    public class MyBinder extends Binder {
        AutoClickService getService() {
            return AutoClickService.this;
        }
    }
    private final MyBinder myBinder = new MyBinder();
    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        Log.println(Log.DEBUG, "AutoClickService", "Service started");
        handler = new Handler(getMainLooper());
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
//                handler.post(() -> Log.println(Log.DEBUG, "AutoClickService", "Service is running"));

                Intent intent1 = new Intent(Clicker.BROADCAST);
                intent1.putExtra("message", "Hello!");
                sendBroadcast(intent1);
            }
        }, 0, 999);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        Log.println(Log.DEBUG, "AutoClickService", "Service destroyed");
        if (timer != null) {
            timer.cancel();
        }
    }

    public String getAutoClicker() {
        return test;
    }
}