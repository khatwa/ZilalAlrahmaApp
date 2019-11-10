package com.khatwa.zilalalrahmaapp.di.module;


import android.app.Application;

import com.khatwa.zilalalrahmaapp.MyApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private MyApplication myApplication;

    public AppModule(MyApplication myApplication) {
        this.myApplication = myApplication;
    }

    @Provides
    @Singleton
    public Application provideApplication() {
        return myApplication;
    }



}
