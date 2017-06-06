package com.hotel.service.net.module.positon;

import com.hotel.service.net.BaseApi;
import com.hotel.service.net.ViewProxyInterface;
import com.hotel.service.net.module.market.model.ReqMenuList;
import com.hotel.service.net.module.positon.model.ReqOfferList;
import com.hotel.service.net.module.positon.model.ReqOfferType;
import com.hotel.service.net.module.positon.model.ResOfferList;
import com.hotel.service.net.module.positon.model.ResOfferType;

import javax.inject.Inject;

import retrofit.RestAdapter;
import rx.Observable;

/**
 * Created by aoxiang on 2015/12/9.
 */
public class OfferApi extends BaseApi {
    static OfferInterface server;

    @Inject
    public OfferApi(RestAdapter restAdapter) {
        server = restAdapter.create(OfferInterface.class);
    }

    /**
     * 获取服务公告信息
     * @return
     */
    public Observable<ResOfferList> getOfferList(ReqOfferList reqOfferList) {
        return getOfferList(reqOfferList, null);
    }

    public Observable<ResOfferList> getOfferList(ReqOfferList reqOfferList, ViewProxyInterface viewProxy) {
        onLoading(viewProxy);

        reqOfferList.module = ReqMenuList.MODULE_HOME;
        reqOfferList.method = "getRecruitmentsList";
        return getRes(server.getOfferList(reqOfferList), viewProxy);
    }

    /**
     * 获取招聘类型
     * @return
     */
    public Observable<ResOfferType> getOfferType(ReqOfferType reqOfferType) {
        return getOfferType(reqOfferType, null);
    }

    public Observable<ResOfferType> getOfferType(ReqOfferType reqOfferType, ViewProxyInterface viewProxy) {
        onLoading(viewProxy);

        reqOfferType.module = ReqMenuList.MODULE_HOME;
        reqOfferType.method = "getRecruitmentsType";
        return getRes(server.getOfferType(reqOfferType), viewProxy);
    }


}
