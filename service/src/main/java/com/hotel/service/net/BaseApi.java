package com.hotel.service.net;

import com.hotel.service.net.module.BaseResModel;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * BaseApi
 * Created by sharyuke on 15-6-8.
 */
public class BaseApi {

    private static String TAG = BaseApi.class.getCanonicalName();

    public static void onLoading(ViewProxyInterface viewProxy) {
        if (viewProxy != null) {
            viewProxy.onLoading();
        }
    }

    public static void onFailed(ViewProxyInterface viewProxy) {
        if (viewProxy != null) {
            viewProxy.onFailed();
        }
    }

    public static void onSuccess(ViewProxyInterface viewProxy) {
        if (viewProxy != null) {
            viewProxy.onSuccess();
        }
    }

    public static void onEmpty(ViewProxyInterface viewProxy) {
        if (viewProxy != null) {
            viewProxy.onEmpty();
        }
    }

    public static void onNoMore(ViewProxyInterface viewProxy) {
        if (viewProxy != null) {
            viewProxy.onNoMore();
        }
    }

    public static <T extends BaseResModel> Observable<T> getRes(Observable<T> observable, ViewProxyInterface viewProxy) {
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> onFailed(viewProxy))
                .doOnError(throwable -> Timber.e(throwable, throwable.getMessage()))
                .filter(FilterData.getFilter(viewProxy));
    }
}
