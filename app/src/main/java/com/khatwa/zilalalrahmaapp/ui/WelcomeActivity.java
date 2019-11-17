package com.khatwa.zilalalrahmaapp.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.khatwa.zilalalrahmaapp.Notifications.NotificationHelper;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent MainIntent = new Intent(WelcomeActivity.this, MainActivity.class);
        startActivity(MainIntent);
        finish();
        NotificationHelper.scheduleRepeatingRTCNotification(getApplicationContext());
        NotificationHelper.enableBootReceiver(getApplicationContext());
    }
}
