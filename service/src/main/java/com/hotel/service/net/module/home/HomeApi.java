package com.hotel.service.net.module.home;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit.RestAdapter;

/**
 * HomeApi
 * Created by sharyuke on 15-6-8.
 */
@Singleton
public class HomeApi {
    HomeInterface server;

    @Inject
    public HomeApi(RestAdapter adapter) {
        this.server = adapter.create(HomeInterface.class);
    }
}
