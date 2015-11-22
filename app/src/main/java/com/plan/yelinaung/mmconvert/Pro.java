package com.plan.yelinaung.mmconvert;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.plan.yelinaung.mmconvert.CustomViews.Circle;
import com.plan.yelinaung.mmconvert.convert.UniCode2ZawGyi;

public class Pro extends AppCompatActivity {
    EditText editText, editedText;
    RadioButton Z2U, U2Z;
    Button  copy, paste;

    CheckBox checkBox;
    String typeFace;

    float f;
    int i;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String temp, original;
    Typeface typeface,type_zaw;
    ClipboardManager clipboardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro);
        setUp();

Log.i("Created>>>>>>>>>>>>>>>", "Created");


        sharedPreferences=getSharedPreferences(Config.sharedPreferences, MODE_PRIVATE);
        editor=sharedPreferences.edit();
        editor.putBoolean(Config.isToolLeap, true);
        editor.apply();
        typeface = Typeface.createFromAsset(getApplicationContext().getAssets(), sharedPreferences.getString(Config.fonts, "font/our.ttf"));
        String chgText = sharedPreferences.getString(Config.edit, "");
        editedText.setText(sharedPreferences.getString(Config.pro_edited,""));


        editText.setText(sharedPreferences.getString(Config.pro_text,""));

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
                String chgText = editText.getText().toString().trim();
                int item_Code = UniCode2ZawGyi.detect(chgText);
                switch (item_Code) {
                    case 1:
                        editedText.setTypeface(typeface);
                        editedText.setText((CharSequence) UniCode2ZawGyi.zg2uni(chgText));
                        editedText.setTextSize(TypedValue.COMPLEX_UNIT_SP, i);
                        break;
                    case 2:
                        editedText.setTypeface(type_zaw);
                        editedText.setText((CharSequence) UniCode2ZawGyi.uni2zg(chgText));
                        editedText.setTextSize(TypedValue.COMPLEX_UNIT_SP, i);
                        break;

                    default:
                        editedText.setTypeface(typeface);
                        editedText.setText((CharSequence)(chgText));
                        editedText.setTextSize(TypedValue.COMPLEX_UNIT_SP, i);
                        break;
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
                }
                catch (NullPointerException ne){

                    pasteData="";
                }



                editText.setText(pasteData);

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

        paste = (Button) findViewById(R.id.paste);


    }
    @Override
    protected void onDestroy() {
editor.putString(Config.pro_edited,editedText.getText().toString());
        editor.putString(Config.pro_text, editText.getText().toString());
        editor.putBoolean(Config.isToolLeap, false);
        editor.commit();
        Log.i("Created>>>>>>>>>>>>", "Distroyed");
        super.onDestroy();
    }


    @Override
    protected void onResume() {
        editor.putBoolean(Config.isToolLeap, true);
        editor.commit();
        Log.i("Created>>>>>>>>>>>>", "Resume");
        super.onResume();
    }

    @Override
    protected void onStart() {
        Log.i("Created>>>>>>>>>>>>", "Started");
        super.onStart();
    }

    @Override
    protected void onPause() {
        editor.putString(Config.pro_edited,editedText.getText().toString());
        editor.putString(Config.pro_text, editText.getText().toString());

        editor.putBoolean(Config.isToolLeap, false);
        editor.commit();
        Log.i("Created>>>>>>>>>>>>", "Pause");
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_pro, menu);

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
         editText.setText("");
            editedText.setText("");
            return true;


        }
//      else  if (id == R.id.cut) {
//
//            ClipData clip = ClipData.newPlainText("simple text", editedText.getText().toString());
//            clipboardManager.setPrimaryClip(clip);
//            editText.setText("");
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }
}
