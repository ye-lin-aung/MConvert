package com.plan.yelinaung.mmconvert.Recievers;

import android.content.BroadcastReceiver;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.plan.yelinaung.mmconvert.Config;
import com.plan.yelinaung.mmconvert.Services.BackgroundService;

/**
 * Created by user on 9/7/15.
 */
public class ClipBoardReceiver  extends BroadcastReceiver{


SharedPreferences sharedPreferences;

    @Override
    public void onReceive(Context context, Intent intent) {
        sharedPreferences=context.getSharedPreferences(Config.TooleapServiceOnOff,Context.MODE_PRIVATE);
        if(sharedPreferences.getBoolean(Config.TooleapServiceOnOff,true)) {
            Intent background = new Intent(context, BackgroundService.class);
            context.startService(background);
        }
    }
}
