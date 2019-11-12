package com.khatwa.zilalalrahmaapp.ui.whoweare;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.khatwa.zilalalrahmaapp.R;

public class WhoWeAreFragment extends Fragment {

    private WhoWeAreViewModel mViewModel;

    public static WhoWeAreFragment newInstance() {
        return new WhoWeAreFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.who_we_are_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(WhoWeAreViewModel.class);
        // TODO: Use the ViewModel
    }

}
