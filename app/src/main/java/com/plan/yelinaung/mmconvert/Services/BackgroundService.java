package com.plan.yelinaung.mmconvert.Services;

import android.app.Service;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.plan.yelinaung.mmconvert.Config;
import com.plan.yelinaung.mmconvert.MMConvertActivity;
import com.plan.yelinaung.mmconvert.MainActivity;
import com.plan.yelinaung.mmconvert.R;
import com.plan.yelinaung.mmconvert.Recievers.TooleapOnOffReceiver;
import com.tooleap.sdk.Tooleap;
import com.tooleap.sdk.TooleapMiniApp;
import com.tooleap.sdk.TooleapPopOutMiniApp;

import java.util.Date;
import java.util.List;

/**
 * Created by user on 9/7/15.
 */
public class BackgroundService extends  Service {
    ClipboardManager clipboardManager;
ClipboardManager.OnPrimaryClipChangedListener clipChangedListener;
    SharedPreferences sharedPreferences;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public void onDestroy() {
        clipboardManager.removePrimaryClipChangedListener(clipChangedListener);
        super.onDestroy();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sharedPreferences=getSharedPreferences(Config.sharedPreferences,MODE_PRIVATE);
        final SharedPreferences.Editor editor=sharedPreferences.edit();

        clipboardManager=(ClipboardManager)getSystemService(CLIPBOARD_SERVICE);

        clipChangedListener=new ClipboardManager.OnPrimaryClipChangedListener() {
            @Override
            public void onPrimaryClipChanged() {
                if (sharedPreferences.getBoolean(Config.TooleapServiceOnOff, true)) {
                    if (!sharedPreferences.getBoolean(Config.isToolLeap, true)) {
                        removeAllMiniApps();
                        popUpMiniApp();
                        editor.putBoolean(Config.isToolLeap, true);
                        editor.commit();
                }


                }
                //getAllMiniApps();
            }
        };
        clipboardManager.addPrimaryClipChangedListener(clipChangedListener);
        return START_STICKY;
    }

    public BackgroundService() {
        super();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    } private void popUpMiniApp() {
        TooleapPopOutMiniApp miniApp = new TooleapPopOutMiniApp(getApplicationContext(), MMConvertActivity.class);
        miniApp.contentTitle = "M Convert";
        miniApp.contentText = "Myanmar Convert";
        miniApp.bubbleBackgroundColor = 0xFF3498DB;
        miniApp.notificationText = "Welcome from M Convert!";
        miniApp.notificationBadgeNumber = 1;
        miniApp.when = new Date();

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.m_convert);
        miniApp.setIcon(bitmap);
        Tooleap.getInstance(getApplicationContext()).addMiniApp(miniApp);
        long l=Tooleap.getInstance(getApplicationContext()).getVisibleAppId();
//Tooleap.getInstance(getApplicationContext()).getMiniApp();
        IntentFilter intentFiltertwo = new IntentFilter();
        intentFiltertwo.addAction(Tooleap.Consts.USER_REMOVED_MINI_APP);
        registerReceiver(new TooleapOnOffReceiver(), intentFiltertwo);


    }

    private void getAllMiniApps() {
       List<TooleapMiniApp> tooleapPopOutMiniApp= Tooleap.getInstance(getApplicationContext()).getAllMiniApps();
        int i=0;

        for(TooleapMiniApp tooleapMiniApp:tooleapPopOutMiniApp) {
            Tooleap.getInstance(getApplicationContext()).addMiniApp(tooleapPopOutMiniApp.get(i));


        }
    }

    private void removeAllMiniApps() {
        Tooleap.getInstance(getApplicationContext()).removeAllMiniApps();

    }

}
