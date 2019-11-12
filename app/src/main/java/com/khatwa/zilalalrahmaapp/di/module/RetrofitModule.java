package com.khatwa.zilalalrahmaapp.di.module;

import com.khatwa.zilalalrahmaapp.Constants;
import com.khatwa.zilalalrahmaapp.Network.ApiInterface;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class RetrofitModule {

    @Provides
    @Singleton
    ApiInterface getApiInterface(Retrofit retroFit) {
        return retroFit.create(ApiInterface.class);
    }

    @Provides
    @Singleton
    static Retrofit provideRetrofitInstance(){
        return  new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
