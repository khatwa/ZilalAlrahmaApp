package com.khatwa.zilalalrahmaapp.ui.wateringaxis;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.khatwa.zilalalrahmaapp.R;

public class WateringAxisFragment extends Fragment {

    private WateringAxisViewModel mViewModel;

    public static WateringAxisFragment newInstance() {
        return new WateringAxisFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.watering_axis_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(WateringAxisViewModel.class);
        // TODO: Use the ViewModel
    }

}
