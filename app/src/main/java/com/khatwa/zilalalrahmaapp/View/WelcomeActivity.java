package com.khatwa.zilalalrahmaapp.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.khatwa.zilalalrahmaapp.Donation.DonationActivity;
import com.khatwa.zilalalrahmaapp.Presenter.RepeatingNotification;
import com.khatwa.zilalalrahmaapp.R;

public class WelcomeActivity extends AppCompatActivity {

    RepeatingNotification repeatingNotification = new RepeatingNotification();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        repeatingNotification.repeatingEveryFriday(getApplicationContext());

        int SPLASH_TIME_OUT = 1000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent MainIntent = new Intent(WelcomeActivity.this, DonationActivity.class);
                startActivity(MainIntent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
