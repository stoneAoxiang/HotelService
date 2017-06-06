package com.hotel.service.dagger;

import com.hotel.service.HotelApp;
import com.hotel.service.dagger.module.ActivityModule;
import com.hotel.service.dagger.module.FragmentModule;
import com.hotel.service.dagger.module.NetModule;
import com.hotel.service.dagger.module.UtilModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * 总的module
 * Created by sharyuke on 4/27/15.
 */
@Module(
        includes = {
                NetModule.class,
                ActivityModule.class,
                FragmentModule.class,
                UtilModule.class,
        },
        injects = {
                HotelApp.class,
        }
)
public class AppModule {

    private HotelApp app;

    public AppModule(HotelApp app) {
        this.app = app;
    }

    @Provides
    @Singleton
    HotelApp getApp() {
        return app;
    }

}
