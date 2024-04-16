package com.example.tapcase;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import androidx.annotation.Nullable;
import java.util.Timer;
import java.util.TimerTask;

public class AutoClickService extends Service {

    private Timer timer;
    private Handler handler;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.println(Log.DEBUG, "AutoClickService", "Service started");
        handler = new Handler(getMainLooper());
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Clicker.clickAuto();

                handler.post(() -> Log.println(Log.DEBUG, "AutoClickService", "Service is running"));
            }
        }, 0, 1000);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.println(Log.DEBUG, "AutoClickService", "Service destroyed");
        if (timer != null) {
            timer.cancel();
        }
    }
}