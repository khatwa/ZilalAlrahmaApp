package com.khatwa.zilalalrahmaapp;


import android.app.Application;
import android.content.Context;

import com.khatwa.zilalalrahmaapp.di.component.AppComponent;
import com.khatwa.zilalalrahmaapp.di.component.DaggerAppComponent;
import com.khatwa.zilalalrahmaapp.di.module.AppModule;
import com.khatwa.zilalalrahmaapp.di.module.ContextModule;
import com.khatwa.zilalalrahmaapp.di.module.RetrofitModule;

public class MyApplication extends Application {

    private AppComponent component;

    public static MyApplication get(Context context) {
        return (MyApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .contextModule(new ContextModule(this))
                .retrofitModule(new RetrofitModule())
                .build();
    }

    public AppComponent component() {
        return component;
    }
}