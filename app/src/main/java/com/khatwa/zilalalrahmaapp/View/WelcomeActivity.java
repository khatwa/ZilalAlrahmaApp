package com.khatwa.zilalalrahmaapp.View;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.khatwa.zilalalrahmaapp.R;

public class WelcomeActivity extends AppCompatActivity {

    private int SPLASH_TIME_OUT = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent MainIntent = new Intent(WelcomeActivity.this, RsedeeActivity.class);
                startActivity(MainIntent);
                finish();
            }
        },SPLASH_TIME_OUT);
    }
}
