package com.khatwa.zilalalrahmaapp.ui.socialserviceshub;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.khatwa.zilalalrahmaapp.R;

public class SocialServicesHubFragment extends Fragment {

    private SocialServicesHubViewModel mViewModel;

    public static SocialServicesHubFragment newInstance() {
        return new SocialServicesHubFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.social_services_hub_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SocialServicesHubViewModel.class);
        // TODO: Use the ViewModel
    }

}
