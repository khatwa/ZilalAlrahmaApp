package com.khatwa.zilalalrahmaapp.di.module;

import com.khatwa.zilalalrahmaapp.ui.Donation.DonationContract;

import dagger.Module;
import dagger.Provides;

@Module
public class DonationMvpModule {

    private DonationContract.View view;

    public DonationMvpModule(DonationContract.View view) {
        this.view = view;
    }

    @Provides
    public DonationContract.View provideView() {
        return view;
    }

}
