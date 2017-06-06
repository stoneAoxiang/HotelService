package com.hotel.service.net.module.newinfo;

import com.hotel.service.net.BaseApi;

import com.hotel.service.net.ViewProxyInterface;
import com.hotel.service.net.module.market.model.ReqMenuList;
import com.hotel.service.net.module.newinfo.model.ReqNewInfo;
import com.hotel.service.net.module.newinfo.model.ResNewInfo;

import javax.inject.Inject;

import retrofit.RestAdapter;
import rx.Observable;

/**
 * Created by hyz on 2015/12/3.
 */
public class NewInfoApi extends BaseApi {

    static NewInfoInterface server;

    @Inject
    public NewInfoApi(RestAdapter restAdapter){
        server = restAdapter.create(NewInfoInterface.class);
    }

    public Observable<ResNewInfo> getNewInfoList(ReqNewInfo reqNewInfo) {
        return getNewInfoList(reqNewInfo, null);
    }

    public Observable<ResNewInfo> getNewInfoList(ReqNewInfo reqNewInfo, ViewProxyInterface viewProxy) {
        onLoading(viewProxy);
        reqNewInfo.module = ReqMenuList.MODULE_HOME;
        reqNewInfo.method = ReqMenuList.METHOD_GET_INFORMATION_TYPE;

        return getRes(server.getNewInfoList(reqNewInfo), viewProxy);
    }
}
