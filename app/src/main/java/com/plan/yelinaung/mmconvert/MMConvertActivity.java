package com.plan.yelinaung.mmconvert;

import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.plan.yelinaung.mmconvert.Recievers.TooleapOnOffReceiver;
import com.plan.yelinaung.mmconvert.convert.UniCode2ZawGyi;
import com.tooleap.sdk.Tooleap;
import com.tooleap.sdk.TooleapActivities;


public class MMConvertActivity extends TooleapActivities.Activity{
    EditText editText, editedText;
    RadioButton Z2U, U2Z;
    String text;
    Button  copy, paste;
    ImageView clear;
Vibrator vibrator;
    CheckBox checkBox;
    String typeFace;
     ClipboardManager clipboardManager;
    LinearLayout bg;
    float f;
    int i;
    SharedPreferences sharedPreferences;
    String temp, original;
    Typeface typeface,type_zaw;
    static String ACTION_CLOSE="com.plan.yelinaung.mmconvert.ACTION_CLOSE";
    IntentFilter intentFiltertwo;

    IntentFilter intentFilter=new IntentFilter(ACTION_CLOSE);

    private  CloseReceiver closeReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mmconvert);
        setUp();





         intentFiltertwo = new IntentFilter();
        intentFiltertwo.addAction(Tooleap.Consts.USER_REMOVED_MINI_APP);
        registerReceiver(new TooleapOnOffReceiver(), intentFiltertwo);

SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean(Config.isToolLeap,true);
        editor.apply();



        Log.i("Created>>>>>>>>>>>>", "Created");
        View view=findViewById(R.id.background_layout);
        int fColor=sharedPreferences.getInt(Config.fontColor,getResources().getColor(R.color.textView_color));
        int color=sharedPreferences.getInt(Config.color,getResources().getColor(R.color.colorMConvert));
        view.setBackgroundColor(color);
editText.setTextColor(fColor);
        editedText.setTextColor(fColor);
closeReceiver=new CloseReceiver();

registerReceiver(closeReceiver,intentFilter);


        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    editedText.setEnabled(false);
                } else {
                    editedText.setEnabled(true);
                }
            }
        });


        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                f = sharedPreferences.getFloat(Config.seekBar, 20f);
                i = (int) f;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                float f = 20f;

                typeface = Typeface.createFromAsset(getApplicationContext().getAssets(), sharedPreferences.getString(Config.fonts, "font/our.ttf"));
                String chgText=editText.getText().toString();
            int item_Code =   UniCode2ZawGyi.detect(chgText);
                switch (item_Code){
                    case 1:editedText.setTypeface(typeface);
                        editedText.setText((CharSequence) UniCode2ZawGyi.zg2uni(chgText));
                        editedText.setTextSize(TypedValue.COMPLEX_UNIT_SP, i);
                        break;
                    case 2:editedText.setTypeface(type_zaw);
                        editedText.setText((CharSequence) UniCode2ZawGyi.uni2zg(chgText));
                        editedText.setTextSize(TypedValue.COMPLEX_UNIT_SP, i);
                        break;

                    default:editedText.setTypeface(typeface);
                        editedText.setText((CharSequence) UniCode2ZawGyi.zg2uni(chgText));
                        editedText.setTextSize(TypedValue.COMPLEX_UNIT_SP, i);break;
                }





        }

                                        });

        paste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence pasteData = "";
                try{
                    ClipData.Item item = clipboardManager.getPrimaryClip().getItemAt(0);
                    pasteData = item.getText();
                    editText.setText(pasteData);
                }catch (NullPointerException ne){
                    pasteData="";
                    editText.setText("");
                }


            }
        });

        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipData clip = ClipData.newPlainText("simple text", editedText.getText().toString());
                clipboardManager.setPrimaryClip(clip);
                Toast.makeText(getApplicationContext(), "Edited Text Copied to ClipBoard", Toast.LENGTH_SHORT).show();
            }
        });


        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrator.vibrate(100);

                editText.setText("");
                editedText.setText("");
            }
        });
        if (savedInstanceState != null) {
            String text = savedInstanceState.getString(Config.edit);
            editText.setText(text);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Config.edit, (original));
    }
    public void setUp(){
        type_zaw=Typeface.createFromAsset(getAssets(),"font/zaw.ttf");
         clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        editText = (EditText) findViewById(R.id.edit_query);
        editedText = (EditText) findViewById(R.id.edited_query);


        copy = (Button) findViewById(R.id.copy);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        sharedPreferences=getSharedPreferences(Config.sharedPreferences,MODE_PRIVATE);
        clear = (ImageView) findViewById(R.id.clear);
        paste = (Button) findViewById(R.id.paste);
        vibrator=(Vibrator)getSystemService(VIBRATOR_SERVICE);


    }

    @Override
    protected void onPause() {
        Log.i("Created>>>>>>>>>>>>", "Pause");
        //unregisterReceiver(new TooleapOnOffReceiver());
        super.onPause();
    }

    @Override
    protected void onResume() {

        Log.i("Created>>>>>>>>>>>>", "Resume");
        intentFiltertwo = new IntentFilter();
        intentFiltertwo.addAction(Tooleap.Consts.USER_REMOVED_MINI_APP);
        registerReceiver(new TooleapOnOffReceiver(), intentFiltertwo);
        super.onResume();
    }

    @Override
    protected void onPostResume() {
        Log.i("Created>>>>>>>>>>>>", "OnPostresume");
        super.onPostResume();
    }

    @Override
    protected void onStart() {
        Log.i("Created>>>>>>>>>>>>", "Start");
        intentFiltertwo = new IntentFilter();
        intentFiltertwo.addAction(Tooleap.Consts.USER_REMOVED_MINI_APP);
        registerReceiver(new TooleapOnOffReceiver(), intentFiltertwo);
        super.onStart();
    }

    public class CloseReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ACTION_CLOSE)) {
                MMConvertActivity.this.finish();
            }
        }
    }


}
