package com.khatwa.zilalalrahmaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.khatwa.zilalalrahmaapp.ui.socialserviceshub.SocialServicesHubFragment;

public class SocialServicesHubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.social_services_hub_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, SocialServicesHubFragment.newInstance())
                    .commitNow();
        }
    }
}
