package com.plan.yelinaung.mmconvert.Recievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.plan.yelinaung.mmconvert.Config;
import com.plan.yelinaung.mmconvert.Services.BackgroundService;
import com.tooleap.sdk.Tooleap;

/**
 * Created by user on 9/8/15.
 */
public class TooleapOnOffReceiver extends BroadcastReceiver {

SharedPreferences sharedPreferences;


    @Override
    public void onReceive(Context context, Intent intent) {
        sharedPreferences=context.getSharedPreferences(Config.sharedPreferences,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean(Config.isToolLeap, false);
        editor.commit();
      //  Toast.makeText(context,"TOOLEAP DELETED",Toast.LENGTH_SHORT).show();
        Intent intent1=new Intent(context,BackgroundService.class);
        context.stopService(intent1);
        context.startService(intent1);



    }
}
