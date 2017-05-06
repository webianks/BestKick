package com.webianks.test.bestkick;

import android.app.Application;
import android.content.Context;

/**
 * Created by R Ankit on 06-05-2017.
 */

public class MyApplication extends Application {

    private static MyApplication instance;

    public static MyApplication getInstance() {
        return instance;
    }

    public static Context getContext() {
        return instance.getApplicationContext();
    }

    @Override
    public void onCreate() {

        super.onCreate();
        instance = this;
    }
}
