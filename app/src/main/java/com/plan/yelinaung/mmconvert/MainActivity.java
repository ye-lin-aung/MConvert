package com.plan.yelinaung.mmconvert;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.plan.yelinaung.mmconvert.Recievers.TooleapOnOffReceiver;
import com.plan.yelinaung.mmconvert.Services.BackgroundService;
import com.tooleap.sdk.Tooleap;
import com.tooleap.sdk.TooleapPopOutMiniApp;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
sharedPreferences=getSharedPreferences(Config.sharedPreferences,MODE_PRIVATE);
         editor=sharedPreferences.edit();
        editor.putBoolean(Config.isToolLeap, false);
       // popUpMiniApp();
        editor.apply();
    }

    @Override
    protected void onStart() {
        super.onStart();
if(sharedPreferences.getBoolean(Config.TooleapServiceOnOff,true) ){
            Intent intent = new Intent(this, BackgroundService.class);
            startService(intent);
        }

        ((LinearLayout) findViewById(R.id.remove)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                removeAllMiniApps();
                popUpMiniApp();

            }
        });



//
//        ((LinearLayout)findViewById(R.id.remove)).setOnLongClickListener((v)->  {
//            popUpMiniApp();
//
//        return  true;
//        });
        ((LinearLayout)findViewById(R.id.setting)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Pro.class);
                startActivity(intent);
            }
        });
    }

    private void popUpMiniApp() {
        TooleapPopOutMiniApp miniApp = new TooleapPopOutMiniApp(MainActivity.this, MMConvertActivity.class);
       miniApp.contentTitle = "M Convert";
        miniApp.contentText = "Myanmar Convert";
        miniApp.bubbleBackgroundColor = 0xFF3498DB;
        miniApp.notificationText = "Welcome from M Convert!";
        miniApp.notificationBadgeNumber = 1;
        miniApp.when = new Date();
        editor.putBoolean(Config.isToolLeap, true);
        editor.apply();

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.m_convert);
       miniApp.setIcon(bitmap);
        Tooleap.getInstance(MainActivity.this).addMiniApp(miniApp);


       IntentFilter intentFiltertwo = new IntentFilter();
        intentFiltertwo.addAction(Tooleap.Consts.USER_REMOVED_MINI_APP);
        registerReceiver(new TooleapOnOffReceiver(), intentFiltertwo);

        long l=Tooleap.getInstance(MainActivity.this).getVisibleAppId();



    }

    private void getAllMiniApps() {
        Tooleap.getInstance(MainActivity.this).getAllMiniApps();
    }

    private void removeAllMiniApps() {
        Tooleap.getInstance(MainActivity.this).removeAllMiniApps();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        int id = item.getItemId();


        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent=new Intent(MainActivity.this,Settings.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
