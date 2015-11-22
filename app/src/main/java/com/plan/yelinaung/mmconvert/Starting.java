package com.plan.yelinaung.mmconvert;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class Starting extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);

        sharedPreferences=getSharedPreferences(Config.sharedPreferences, MODE_PRIVATE);
        editor=sharedPreferences.edit();
        if(sharedPreferences.getBoolean(Config.firstTime,true)){
            Intent intent=new Intent(this,Ads.class);
            editor.putBoolean(Config.firstTime, false);
            editor.commit();

            startActivity(intent);
            this.finish();
        }
        else {

            Intent intent=new Intent(this,MainActivity.class);

            startActivity(intent);
            this.finish();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_starting, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
