package com.hotel.service.dagger.module;

import android.util.Log;

import com.hotel.service.BuildConfig;
import com.hotel.service.util.Config;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpCookie;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import timber.log.Timber;

/**
 * 网络框架
 * Created by sharyuke on 4/29/15.
 */
@Module(
        injects = {

        },
        library = true,
        complete = false
)
public class NetModule {

    private CookieManager cookieManager;

    private String TAG = NetModule.class.getCanonicalName();

   /* private void getCookie(){
        cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        CookieHandler.setDefault(cookieManager);
    }*/

    RequestInterceptor interceptor = new RequestInterceptor() {
        @Override
        public void intercept(RequestFacade request) {

            cookieManager = new CookieManager();
            cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
            CookieHandler.setDefault(cookieManager);

            for (HttpCookie cookie : cookieManager.getCookieStore().getCookies()) {
                Date expiration = new Date(System.currentTimeMillis() + 60 * 60 * 1000);
                String expires = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").format(expiration);

                String cookieValue = cookie.getName() + "=" + cookie.getValue() + "; " +
                        "path=" + cookie.getPath() + "; " +
                        "domain=" + cookie.getDomain() + ";" +
                        "expires=" + expires;

                Log.i(TAG, "cookieValue值====================" + cookieValue);

                request.addHeader("Cookie", cookieValue);
            }
        }
    };


    @Provides
    @Singleton
    RestAdapter getRestAdapter() {
        return new RestAdapter.Builder()
                .setRequestInterceptor(interceptor)
                .setEndpoint(Config.HOST_NAME)
                .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
                .setLog(Timber::i)
                .build();
    }

}
