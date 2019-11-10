package com.khatwa.zilalalrahmaapp.di.component;

import com.khatwa.zilalalrahmaapp.di.module.DonationMvpModule;
import com.khatwa.zilalalrahmaapp.di.scope.DonationFragmentScope;
import com.khatwa.zilalalrahmaapp.ui.Donation.DonationFragment;

import dagger.Component;

@DonationFragmentScope
@Component(dependencies = AppComponent.class, modules = DonationMvpModule.class)

public interface DonationComponent {

    void inject(DonationFragment donationFragment);

}