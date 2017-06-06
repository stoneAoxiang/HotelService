package com.hotel.service.net.module.menu;

import android.util.Log;

import com.hotel.service.BuildConfig;
import com.hotel.service.net.BaseApi;
import com.hotel.service.net.ViewProxyInterface;
import com.hotel.service.net.module.market.model.ReqMenuList;
import com.hotel.service.net.module.menu.model.ReqCategoryList;
import com.hotel.service.net.module.menu.model.ReqRepairsAddress;
import com.hotel.service.net.module.menu.model.ReqRepairsProject;
import com.hotel.service.net.module.menu.model.ReqServiceAddress;
import com.hotel.service.net.module.menu.model.ReqServiceMethod;
import com.hotel.service.net.module.menu.model.ResCategoryList;
import com.hotel.service.net.module.menu.model.ResRepairsAddress;
import com.hotel.service.net.module.menu.model.ResRepairsProject;
import com.hotel.service.net.module.menu.model.ResServiceAddress;
import com.hotel.service.net.module.menu.model.ResServiceMethod;
import com.hotel.service.util.Config;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit.RestAdapter;
import rx.Observable;
import timber.log.Timber;

/**
 * MarketApi
 * Created by sharyuke on 15-6-4.
 */
@Singleton
public class MenuApi extends BaseApi {
    static MenuInterface server;

    private String TAG = MenuApi.class.getCanonicalName();

    //利浪专用接口
    private MenuInterface interfaceServer;

    @Inject
    public MenuApi(RestAdapter restAdapter) {
        server = restAdapter.create(MenuInterface.class);
    }

    public Observable<ResRepairsAddress> getRepairsAddressList(ReqRepairsAddress reqMenuList) {
        return getRepairsAddressList(reqMenuList, null);
    }

    public Observable<ResRepairsAddress> getRepairsAddressList(ReqRepairsAddress reqMenuList, ViewProxyInterface viewProxy) {
        onLoading(viewProxy);
        reqMenuList.module = ReqMenuList.MODULE_COMMON;
        reqMenuList.method = ReqMenuList.METHOD_GET_REPAIRS_ADDRESS_LIST;
        return getRes(server.getRepairsAddressList(reqMenuList), viewProxy);
    }


    public Observable<ResRepairsProject> getRepairsProject(ReqRepairsProject reqMenuList) {
        return getRepairsProject(reqMenuList, null);
    }

    public Observable<ResRepairsProject> getRepairsProject(ReqRepairsProject reqMenuList, ViewProxyInterface viewProxy) {
        onLoading(viewProxy);
        reqMenuList.module = ReqMenuList.MODULE_HOME;
        reqMenuList.method = ReqMenuList.METHOD_GET_REPAIRS_PROJECT;
        return getRes(server.getRepairsProject(reqMenuList), viewProxy);
    }

    public Observable<ResCategoryList> getCategoryList(ReqCategoryList reqStr) {
        return getCategoryList(reqStr, null);
    }

    public Observable<ResCategoryList> getCategoryList(ReqCategoryList reqStr, ViewProxyInterface viewProxy) {
        onLoading(viewProxy);
        reqStr.module = ReqMenuList.MODULE_COMMON;
        reqStr.method = "getCategoryList";

        newInterFaceServer();

        return getRes(interfaceServer.getCategoryList(reqStr), viewProxy);
    }


    public Observable<ResServiceAddress> getServiceAreasList(ReqServiceAddress reqInfo) {
        return getServiceAreasList(reqInfo, null);
    }

    public Observable<ResServiceAddress> getServiceAreasList(ReqServiceAddress reqInfo, ViewProxyInterface viewProxy) {
        onLoading(viewProxy);
        reqInfo.module = ReqMenuList.MODULE_BUSINESS;
        reqInfo.method = "getServiceAreasList";

        newInterFaceServer();

        return getRes(interfaceServer.getServiceAreasList(reqInfo), viewProxy);
    }

    public Observable<ResServiceMethod> getServiceMethodList(ReqServiceMethod reqInfo) {
        return getServiceMethodList(reqInfo, null);
    }

    public Observable<ResServiceMethod> getServiceMethodList(ReqServiceMethod reqInfo, ViewProxyInterface viewProxy) {
        onLoading(viewProxy);
        reqInfo.module = ReqMenuList.MODULE_COMMON;
        reqInfo.method = "getServiceMethodList";

        newInterFaceServer();

        return getRes(interfaceServer.getServiceMethodList(reqInfo), viewProxy);
    }

    /**
     * 链接到利浪平台，需要新建RestAdapter
     */
    private MenuInterface newInterFaceServer() {
        Log.i(TAG, "链接到利浪平台===================");

        if (null == interfaceServer) {
            RestAdapter ra = new RestAdapter.Builder()
                    .setEndpoint(Config.PUBLIC_SERVER_URL)
                    .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
                    .setLog(Timber::i)
                    .build();

            return interfaceServer = ra.create(MenuInterface.class);

        }

        return interfaceServer;
    }


}
