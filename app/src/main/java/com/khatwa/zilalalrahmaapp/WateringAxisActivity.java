package com.khatwa.zilalalrahmaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.khatwa.zilalalrahmaapp.ui.wateringaxis.WateringAxisFragment;

public class WateringAxisActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.watering_axis_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, WateringAxisFragment.newInstance())
                    .commitNow();
        }
    }
}
