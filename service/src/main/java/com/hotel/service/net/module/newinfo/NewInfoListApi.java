package com.hotel.service.net.module.newinfo;

import com.hotel.service.net.BaseApi;
import com.hotel.service.net.ViewProxyInterface;
import com.hotel.service.net.module.market.model.ReqMenuList;
import com.hotel.service.net.module.newinfo.model.ReqNewInfoList;
import com.hotel.service.net.module.newinfo.model.ResNewInfoList;

import javax.inject.Inject;

import retrofit.RestAdapter;
import rx.Observable;

/**
 * Created by hyz on 2015/12/7.
 */
public class NewInfoListApi extends BaseApi {

    static NewInfoListInterface server;

    @Inject
    public NewInfoListApi(RestAdapter restAdapter){
        server = restAdapter.create(NewInfoListInterface.class);
    }

    public Observable<ResNewInfoList> getNewInfoListType(ReqNewInfoList reqNewInfoList) {
        return getNewInfoListType(reqNewInfoList, null);
    }

    public Observable<ResNewInfoList> getNewInfoListType(ReqNewInfoList reqNewInfoList, ViewProxyInterface viewProxy) {
        onLoading(viewProxy);
        reqNewInfoList.module = ReqMenuList.MODULE_HOME;
        reqNewInfoList.method = ReqMenuList.METHOD_GET_INFORMATION_LIST;

        return getRes(server.getNewInfoListType(reqNewInfoList), viewProxy);
    }


}
