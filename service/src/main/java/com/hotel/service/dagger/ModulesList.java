package com.hotel.service.dagger;


import com.hotel.service.HotelApp;

/**
 * list modules
 * Created by sharyuke on 4/27/15.
 */
public final class ModulesList {
    private ModulesList() {
    }

    public static Object[] list(HotelApp app) {
        return new Object[]{new AppModule(app)};
    }
}
