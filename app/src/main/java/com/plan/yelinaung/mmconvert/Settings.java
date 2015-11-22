package com.plan.yelinaung.mmconvert;

import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;


import com.plan.yelinaung.mmconvert.CustomViews.Circle;
import com.plan.yelinaung.mmconvert.Recievers.TooleapOnOffReceiver;
import com.plan.yelinaung.mmconvert.Services.BackgroundService;
import com.rey.material.app.Dialog;
import com.tooleap.sdk.Tooleap;
import com.tooleap.sdk.TooleapMiniApp;
import com.tooleap.sdk.TooleapPopOutMiniApp;

import org.w3c.dom.Text;

import java.util.Date;

public class Settings extends ActionBarActivity  {
    RadioButton m3,our,thar,padauk,mon3,pyi;
    Dialog dialog,dialog_two;
    LinearLayout layout,about;
    SeekBar seekBar;
    Circle font_c,bg_c;
    TextView font_size;
    TextView fonts;
    EditText zaw,uni;
    String font, font_name;
    RadioGroup radioGroup;
    Switch aSwitch;
    int postions,i;
    float f;
    ImageView imageView;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorMConvert)));
        getSupportActionBar().setTitle("Settings");



        //getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        sharedPreferences = getSharedPreferences(Config.sharedPreferences, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        layout = (LinearLayout) findViewById(R.id.font_setting);

        fonts = (TextView) findViewById(R.id.font);
        seekBar = (SeekBar) findViewById(R.id.seekbar);
        font_size = (TextView) findViewById(R.id.size);
        zaw=(EditText)findViewById(R.id.zawgyi_test);
        uni=(EditText)findViewById(R.id.uni_test);
        font_c=(Circle)findViewById(R.id.fontc);
        imageView=(ImageView)findViewById(R.id.info_service);
aSwitch=(Switch)findViewById(R.id.service_switch);
   aSwitch.setChecked(sharedPreferences.getBoolean(Config.TooleapServiceOnOff,true));


        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    editor.putBoolean(Config.TooleapServiceOnOff,true);
                    editor.putBoolean(Config.isToolLeap,false);
                    editor.commit();
                    Intent intent1 = new Intent(Settings.this, BackgroundService.class);
                    Settings.this.stopService(intent1);
                    Settings.this.startService(intent1);

                }
                else{
                    editor.putBoolean(Config.TooleapServiceOnOff,false);
                    editor.commit();
                 //   Intent intent1 = new Intent(Settings.this, BackgroundService.class);
                    //Settings.this.stopService(intent1);
                }


            }
        });
bg_c=(Circle)findViewById(R.id.bgc);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = createAlert();
                alertDialog.show();
            }
        });


//




        bg_c.setCircleColor(sharedPreferences.getInt(Config.color,getResources().getColor(R.color.colorMConvert)));
        font_c.setCircleColor(sharedPreferences.getInt(Config.fontColor, getResources().getColor(R.color.textView_color)));





bg_c.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        colorPick();
    }
});
        font_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fontColorPick();
            }
        });


  zaw.setTextSize(sharedPreferences.getFloat(Config.seekBar, 20f));
            uni.setTextSize(sharedPreferences.getFloat(Config.seekBar, 20f));
font_size.setText("Size : " + sharedPreferences.getFloat(Config.seekBar, 20f) + "");


        zaw.setTypeface(Typeface.createFromAsset(getAssets(), sharedPreferences.getString(Config.fonts, "font/our.ttf")));
        uni.setTypeface(Typeface.createFromAsset(getAssets(),"font/zaw.ttf"));
        fonts.setText(SelectFont());
        seekBar.setMax(50);
        seekBar.setProgress(Math.round(sharedPreferences.getFloat(Config.seekBar, 20f)));
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            float f = (float) (sharedPreferences.getFloat(Config.seekBar, 20f));
            int progress = Math.round(f);





            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                font_size.setText("Size : " + progress + "");
                zaw.setTextSize(progress);
                uni.setTextSize(progress);
                editor.putFloat(Config.seekBar, (float) progress);
                editor.commit();

                //zaw.setTextSize(new Float(progress));
                //  uni.setTextSize(new Float( progress));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                removeAllMiniApps();
                Intent myIntent = new Intent(MMConvertActivity.ACTION_CLOSE);
                sendBroadcast(myIntent);

                popUpMiniApp();

            }
        });

        ((Button)findViewById(R.id.default_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zaw.setTextSize(20f);
                uni.setTextSize(20f);
                font_size.setText("Size : " + 20 + "");
                fonts.setText("Our");
                try{
                our.setChecked(true);}
                catch (NullPointerException ne){

                }

                bg_c.setCircleColor(getResources().getColor(R.color.colorMConvert));
                font_c.setCircleColor(getResources().getColor(R.color.textView_color));
                editor.clear();
                editor.apply();
                editor.putBoolean(Config.firstTime, false);
                editor.putBoolean(Config.TooleapServiceOnOff, true);
                editor.putBoolean(Config.isToolLeap, false);

                editor.apply();
                removeAllMiniApps();
                Intent myIntent = new Intent(MMConvertActivity.ACTION_CLOSE);
                sendBroadcast(myIntent);
                popUpMiniApp();
            }
        });





        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog alertDialog = getInstance();
                alertDialog.show();


            }
        });

    }





    public Dialog getInstance() {

        if (dialog == null) {


            dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_choose);
            m3=(RadioButton)dialog.findViewById(R.id.m3);
            our=(RadioButton)dialog.findViewById(R.id.our);
            thar=(RadioButton)dialog.findViewById(R.id.Tharlon);
            padauk=(RadioButton)dialog.findViewById(R.id.padauk_book);
            mon3=(RadioButton)dialog.findViewById(R.id.mon_three);
            pyi=(RadioButton)dialog.findViewById(R.id.pyi);


            switch (sharedPreferences.getString(Config.fonts, "/font/our.ttf")){

        default:our.setChecked(true);break;
                case "font/myanmarthree.ttf":m3.setChecked(true);break;
                case "font/our.ttf":our.setChecked(true);break;
                case "font/tharlon.ttf":thar.setChecked(true);break;
                case "font/padauk_book.ttf":padauk.setChecked(true);break;
                case "font/mon3.ttf":mon3.setChecked(true);break;
                case "font/pyidaungsu.ttf":pyi.setChecked(true);break;
            }
dialog.setCanceledOnTouchOutside(false);
            dialog.positiveAction("ok").positiveActionClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    editor.putString(Config.fonts, font);
                    editor.commit();
                    uni.setTypeface(Typeface.createFromAsset(getAssets(), "font/zaw.ttf"));
                    zaw.setTypeface(Typeface.createFromAsset(getAssets(), font));
                    fonts.setText(SelectFont());
                    removeAllMiniApps();
                    popUpMiniApp();
                    dialog.dismiss();

                }
            });

            radioGroup = (RadioGroup) dialog.findViewById(R.id.font_choose);

            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    int id = group.getCheckedRadioButtonId();
                    switch (id) {
                        case R.id.m3:
                            font = "font/myanmarthree.ttf";
                            m3.setChecked(true);
                            postions = 0;
                            break;
                        case R.id.our:
                            font = "font/our.ttf";
                            our.setChecked(true);
                            postions = 1;
                            break;

                        case R.id.Tharlon:
                            font = "font/tharlon.ttf";
                            thar.setChecked(true);
                            postions = 2;
                            break;
                        case R.id.padauk_book:
                            font = "font/padauk_book.ttf";
                            padauk.setChecked(true);
                            postions = 3;
                            break;
                        case R.id.mon_three:
                            font = "font/mon3.ttf";
                            mon3.setChecked(true);
                            postions = 4;
                            break;
                        case R.id.pyi:
                            font = "font/pyidaungsu.ttf";
                            pyi.setChecked(true);
                            postions = 5;
                            break;
                        default:break;

                    }
                }
            });

        }

            return dialog;
        }




    public String SelectFont(){
        switch ( sharedPreferences.getString(Config.fonts,"font/our.ttf")){
default:break;
            case "font/myanmarthree.ttf":font_name="Myanmar Three";
                  break;
            case "font/our.ttf":font_name="Our";break;
            case "font/padauk_book.ttf":font_name="Padauk";break;
            case "font/tharlon.ttf":font_name="Tharlon";break;
            case "font/mon3.ttf":font_name="Mon3";break;
            case "font/pyidaungsu.ttf":font_name="Pyidaung Su";break;

        }
        return font_name;
    }


    @Override
    protected void onStart() {
        super.onStart();

    }

    private void popUpMiniApp() {
        TooleapPopOutMiniApp miniApp = new TooleapPopOutMiniApp(Settings.this, MMConvertActivity.class);
        miniApp.contentTitle = "M Convert";
        miniApp.contentText = "Myanmar Convert";
        miniApp.bubbleBackgroundColor = 0xFF3498DB;
        miniApp.notificationText = "Welcome from M Convert!";
        miniApp.notificationBadgeNumber = 1;
        miniApp.when = new Date();
        editor.putBoolean(Config.isToolLeap,true);
        editor.apply();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.m_convert);
        miniApp.setIcon(bitmap);
        Tooleap.getInstance(Settings.this).addMiniApp(miniApp);
        IntentFilter intentFiltertwo = new IntentFilter();
        intentFiltertwo.addAction(Tooleap.Consts.USER_REMOVED_MINI_APP);
        registerReceiver(new TooleapOnOffReceiver(), intentFiltertwo);



    }



    private void fontColorPick() {
        int colorp = sharedPreferences.getInt(Config.fontColor, 0xffffffff);
        ColorPickerDialogBuilder
                .with(Settings.this)
                .setTitle("Choose color")
                .initialColor(colorp)
                .wheelType(ColorPickerView.WHEEL_TYPE.CIRCLE)
                .density(7)
                .setOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int selectedColor) {

                    }
                })
                .setPositiveButton("ok", new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                        editor.putInt(Config.fontColor, selectedColor);
                        editor.apply();
                        font_c.setCircleColor(selectedColor);


                        removeAllMiniApps();
                        Intent myIntent = new Intent(MMConvertActivity.ACTION_CLOSE);
                        sendBroadcast(myIntent);
                        popUpMiniApp();
                        dialog.dismiss();

                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .build()
                .show();

    }










    private void colorPick() {
int colorp=sharedPreferences.getInt(Config.color, 0xffffffff);
        ColorPickerDialogBuilder
                .with(Settings.this)
                .setTitle("Choose color")
                .initialColor(colorp)
                .wheelType(ColorPickerView.WHEEL_TYPE.CIRCLE)
                .density(7)
                .setOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int selectedColor) {

                    }
                })
                .setPositiveButton("ok", new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                        editor.putInt(Config.color, selectedColor);
                        editor.apply();
                        bg_c.setCircleColor(selectedColor);

removeAllMiniApps();
                        Intent myIntent = new Intent(MMConvertActivity.ACTION_CLOSE);
                        sendBroadcast(myIntent);
                        popUpMiniApp();
                        dialog.dismiss();

                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .build()
                .show();



    }


    private void getAllMiniApps() {
        Tooleap.getInstance(Settings.this).getAllMiniApps();
    }


    private void UptateMiniApps(){

        Tooleap.getInstance(Settings.this).updateMiniApp(0, Tooleap.getInstance(Settings.this).getMiniApp(0));

    }
    private void removeAllMiniApps() {


        Tooleap.getInstance(Settings.this).removeAllMiniApps();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_ads, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        int id = item.getItemId();


        //noinspection SimplifiableIfStatement
        if (id == R.id.about_) {



                    Intent intent = new Intent(getBaseContext(), AboutActivity.class);
                    startActivity(intent);


            return true;


        }

        return super.onOptionsItemSelected(item);
    }
    public  AlertDialog createAlert(){

        AlertDialog.Builder builder=new AlertDialog.Builder(Settings.this);

        builder.setMessage("Service that make Floating App Appears whenever u copy the text from anywhere....Enable this to make Floating App appears whenever you Copy the text anywhere without opening the App.Disable this not to appear the Floating app whenever you copy the text.");



        return builder.create();
    }

}
