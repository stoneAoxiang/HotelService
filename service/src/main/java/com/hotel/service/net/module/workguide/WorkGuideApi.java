package com.hotel.service.net.module.workguide;

import com.hotel.service.net.BaseApi;
import com.hotel.service.net.ViewProxyInterface;
import com.hotel.service.net.module.workguide.model.ReqWorkGuide;
import com.hotel.service.net.module.workguide.model.ResWorkGuide;
import com.hotel.service.net.module.market.model.ReqMenuList;

import javax.inject.Inject;

import retrofit.RestAdapter;
import rx.Observable;

/**
 * Created by hyz on 2015/12/7.
 */
public class WorkGuideApi extends BaseApi {

    static WorkGuideInterface server;

    @Inject
    public WorkGuideApi(RestAdapter restAdapter){
        server = restAdapter.create(WorkGuideInterface.class);
    }

    public Observable<ResWorkGuide> getWorkGuide(ReqWorkGuide reqWorkGuide) {
        return getWorkGuide(reqWorkGuide, null);
    }

    public Observable<ResWorkGuide> getWorkGuide(ReqWorkGuide reqWorkGuide, ViewProxyInterface viewProxy) {
        onLoading(viewProxy);
        reqWorkGuide.module = ReqMenuList.MODULE_HOME;
        reqWorkGuide.method = ReqMenuList.METHOD_GET_WORK_GUIDES_LIST;

        return getRes(server.getWorkGuide(reqWorkGuide), viewProxy);
    }


}
