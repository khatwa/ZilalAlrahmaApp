package com.khatwa.zilalalrahmaapp.di.component;


import android.app.Application;
import android.content.Context;

import com.khatwa.zilalalrahmaapp.MyApplication;
import com.khatwa.zilalalrahmaapp.Network.APIInterface;
import com.khatwa.zilalalrahmaapp.di.module.AppModule;
import com.khatwa.zilalalrahmaapp.di.module.ContextModule;
import com.khatwa.zilalalrahmaapp.di.module.RetrofitModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, ContextModule.class, RetrofitModule.class})
public interface AppComponent {

    void inject(MyApplication myApplication);

    Context getContext();

    APIInterface getApiInterface();

    Application getApplication();
}
