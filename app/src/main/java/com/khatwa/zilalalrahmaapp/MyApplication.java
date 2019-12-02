package com.khatwa.zilalalrahmaapp;


import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.khatwa.zilalalrahmaapp.di.component.AppComponent;
import com.khatwa.zilalalrahmaapp.di.component.DaggerAppComponent;
import com.khatwa.zilalalrahmaapp.di.module.AppModule;
import com.khatwa.zilalalrahmaapp.di.module.ContextModule;
import com.khatwa.zilalalrahmaapp.di.module.RetrofitModule;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

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

        Calendar calendar = Calendar.getInstance();

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int dayDiff = 6 - dayOfWeek;  //different (in Days) between curr day and Friday
        if (dayDiff < 0)
            dayDiff += 7;

        int currHour = calendar.get(Calendar.HOUR_OF_DAY);
        int hourDiff = 15 - currHour;    //different between curr Hour and 15:00

        int timeDiff = dayDiff * 24 + hourDiff;  //different (in Hours) between curr time and Friday at 15:00
        Log.e("time diff", String.valueOf(timeDiff));

        PeriodicWorkRequest notifyRequest =
                new PeriodicWorkRequest.Builder(MyWorker.class, 7, TimeUnit.DAYS)
                        .setInitialDelay(timeDiff, TimeUnit.HOURS)                   // remind user every friday at 15:00 to 16:00
                        .build();

        WorkManager.getInstance(this)
                .enqueueUniquePeriodicWork(
                        "notify_worker",
                        ExistingPeriodicWorkPolicy.KEEP,         // todo  use KEEP instaed of REPLACE
                        notifyRequest);
    }

    public AppComponent component() {
        return component;
    }
}