package com.chunlangjiu.app.util;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.igexin.sdk.GTServiceManager;

/**
 * @CreatedbBy: liucun on 2018/7/24
 * @Describe:
 */
public class GeTuiPushService extends Service {
    public static final String TAG = GeTuiPushService.class.getName();

    @Override
    public void onCreate() {
        Log.d(TAG, TAG + " call -> onCreate -------");
        super.onCreate();
        GTServiceManager.getInstance().onCreate(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, TAG + " call -> onStartCommand -------");
        super.onStartCommand(intent, flags, startId);
        return GTServiceManager.getInstance().onStartCommand(this, intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind -------");
        return GTServiceManager.getInstance().onBind(intent);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy -------");
        super.onDestroy();
        GTServiceManager.getInstance().onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        GTServiceManager.getInstance().onLowMemory();
    }
}
