package com.hotel.service.net.module.cultural;

import com.hotel.service.net.BaseApi;
import com.hotel.service.net.ViewProxyInterface;
import com.hotel.service.net.module.cultural.model.ReqCulturalList;
import com.hotel.service.net.module.cultural.model.ResCulturalList;
import com.hotel.service.net.module.market.model.ReqMenuList;

import javax.inject.Inject;

import retrofit.RestAdapter;
import rx.Observable;

/**
 * Created by Administrator on 2016/4/5.
 */
public class CulturalApi extends BaseApi {

    static CulturalInterface server;

    @Inject
    public CulturalApi(RestAdapter restAdapter){
        server = restAdapter.create(CulturalInterface.class);
    }

    public Observable<ResCulturalList> getCulturalList(ReqCulturalList reqCulturalList) {
        return getCulturalList(reqCulturalList, null);
    }

    public Observable<ResCulturalList> getCulturalList(ReqCulturalList reqCulturalList, ViewProxyInterface viewProxy) {
        onLoading(viewProxy);
        reqCulturalList.module = ReqMenuList.MODULE_HOME;
        reqCulturalList.method = ReqMenuList.METHOD_GET_CULTURAL_ACTIVITYS_LIST;

        return getRes(server.getCulturalList(reqCulturalList), viewProxy);
    }

}
