package com.khatwa.zilalalrahmaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.khatwa.zilalalrahmaapp.ui.education.EducationFragment;

public class EducationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.education_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, EducationFragment.newInstance())
                    .commitNow();
        }
    }
}
