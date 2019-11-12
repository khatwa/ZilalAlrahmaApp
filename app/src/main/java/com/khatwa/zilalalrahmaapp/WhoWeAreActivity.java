package com.khatwa.zilalalrahmaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.khatwa.zilalalrahmaapp.ui.whoweare.WhoWeAreFragment;

public class WhoWeAreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.who_we_are_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, WhoWeAreFragment.newInstance())
                    .commitNow();
        }
    }
}
