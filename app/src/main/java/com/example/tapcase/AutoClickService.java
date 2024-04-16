package com.example.tapcase;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;

public class AutoClickService extends Service {
    private Handler handler;
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
    public int onStartCommand(Intent intent, int flags, int startId){
        handler = new Handler();
        Runnable runnable = new Runnable() {
            public void run() {
                Log.println(Log.DEBUG, "AutoclickService", "Le service d'autoclick est en cours d'exécution.");
                handler.postDelayed(this, 4000);
            }
        };
        handler.post(runnable);
        return START_STICKY;
    }
}