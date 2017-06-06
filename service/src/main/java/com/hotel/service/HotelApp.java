package com.hotel.service;

import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.SDKInitializer;
import com.hotel.service.dagger.ModulesList;
import com.hotel.service.volley.RequestManager;

import dagger.ObjectGraph;
import timber.log.Timber;

public class HotelApp extends Application {

    ObjectGraph objectGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {

        }
    }

    private void init() {
        SDKInitializer.initialize(this);
        RequestManager.init(this);
//		JPushInterface.setDebugMode(true);
//      JPushInterface.init(this);

        objectGraph = ObjectGraph.create(ModulesList.list(this));
        inject(this);
    }

    public static HotelApp get(Context context) {
        return (HotelApp) context.getApplicationContext();
    }

    public void inject(Object o) {
        objectGraph.inject(o);
    }
}
