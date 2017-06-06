package com.hotel.service.net;

/**
 * ViewProxyInterface
 * Created by sharyuke on 15-6-8.
 */
public interface ViewProxyInterface {

    /**
     * @return is second load list data
     */
    boolean isNotFirstLoad();

    /**
     * when network request the net ,then callback
     */
    void onLoading();

    /**
     * when network successfully response
     */
    void onSuccess();

    /**
     * when list data has empty list
     */
    void onEmpty();

    /**
     * when list data has no more data
     */
    void onNoMore();

    /**
     * when network failed to connect the internet
     */
    void onFailed();

}
