package com.plan.yelinaung.mmconvert;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by user on 8/20/15.
 */
public class AboutActivity extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        textView=(TextView)findViewById(R.id.email);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent emailIntent = new Intent( android.content.Intent.ACTION_SEND);

                emailIntent.setType("plain/text");

                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                        new String[] { "whiteteenboy@gmail.com" });

                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                        "Email Subject");

                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                        "Email Body");

                startActivity(Intent.createChooser(
                        emailIntent, "Send mail..."));
            }
        });
    }
}
