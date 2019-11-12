package com.khatwa.zilalalrahmaapp.di.module;

import com.khatwa.zilalalrahmaapp.ui.NewsDetails.NewsDetailsContract;

import dagger.Module;
import dagger.Provides;

@Module
public class NewsDetailsMvpModule {

    private NewsDetailsContract.View view;

    public NewsDetailsMvpModule(NewsDetailsContract.View view) {

        this.view = view;
    }

    @Provides
    public NewsDetailsContract.View provideView() {

        return view;
    }

}