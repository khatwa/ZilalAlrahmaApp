package com.khatwa.zilalalrahmaapp.di.module;

import com.khatwa.zilalalrahmaapp.ui.NewesList.NewsListContract;

import dagger.Module;
import dagger.Provides;

@Module
public class NewsListMvpModule {

    private NewsListContract.View view;

    public NewsListMvpModule(NewsListContract.View view) {
        this.view = view;
    }

    @Provides
    public NewsListContract.View provideView() {
        return view;
    }

}