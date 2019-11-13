package com.khatwa.zilalalrahmaapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.khatwa.zilalalrahmaapp.R;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent MainIntent = new Intent(WelcomeActivity.this, MainActivity.class);
        startActivity(MainIntent);
        finish();
    }
}
