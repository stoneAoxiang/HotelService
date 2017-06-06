package com.hotel.service.net.module.recruit;

import com.hotel.service.net.BaseApi;
import com.hotel.service.net.ViewProxyInterface;
import com.hotel.service.net.module.market.model.ReqMenuList;
import com.hotel.service.net.module.newinfo.model.ReqNewInfoList;
import com.hotel.service.net.module.newinfo.model.ResNewInfoList;
import com.hotel.service.net.module.recruit.model.ReqNewRecruit;
import com.hotel.service.net.module.recruit.model.ResNewRecruit;

import javax.inject.Inject;

import retrofit.RestAdapter;
import rx.Observable;

/**
 * Created by hyz on 2015/12/26.
 */
public class NewRecruitApi extends BaseApi {

    static NewRecruitInterface server;

    @Inject
    public NewRecruitApi(RestAdapter restAdapter){
        server = restAdapter.create(NewRecruitInterface.class);
    }

    public Observable<ResNewRecruit> getNewRecruitType(ReqNewRecruit reqNewRecruit) {
        return getNewRecruitType(reqNewRecruit, null);
    }

    public Observable<ResNewRecruit> getNewRecruitType(ReqNewRecruit reqNewRecruit, ViewProxyInterface viewProxy) {
        onLoading(viewProxy);
        reqNewRecruit.module = ReqMenuList.MODULE_COMMON;
        reqNewRecruit.method = ReqMenuList.METHOD_GET_NEW_CLIENT_PAGE;

        return getRes(server.getNewRecruitType(reqNewRecruit), viewProxy);
    }
}
