package com.hotel.service.net.module.integer;

import com.hotel.service.net.BaseApi;
import com.hotel.service.net.ViewProxyInterface;
import com.hotel.service.net.module.integer.model.ReqIntegralGoodsExchange;
import com.hotel.service.net.module.integer.model.ReqMyIntegralOrderInfo;
import com.hotel.service.net.module.integer.model.ResIntegralGoodsExchange;
import com.hotel.service.net.module.integer.model.ResMyIntegeralOrderInfo;
import com.hotel.service.net.module.market.model.ReqMenuList;

import javax.inject.Inject;

import retrofit.RestAdapter;
import rx.Observable;

/**
 * Created by hyz on 2015/12/22.
 */
public class MyIntegeralOrderInfoApi extends BaseApi {

    static MyIntegeralOrderInfoInterface server;

    @Inject
    public MyIntegeralOrderInfoApi(RestAdapter restAdapter){
        server = restAdapter.create(MyIntegeralOrderInfoInterface.class);
    }

    public Observable<ResMyIntegeralOrderInfo> getMyIntegralOrderInfoType(ReqMyIntegralOrderInfo reqMyIntegralOrderInfo) {
        return getMyIntegralOrderInfoType(reqMyIntegralOrderInfo, null);
    }

    public Observable<ResMyIntegeralOrderInfo> getMyIntegralOrderInfoType(ReqMyIntegralOrderInfo reqMyIntegralOrderInfo,ViewProxyInterface viewProxy){
        reqMyIntegralOrderInfo.module = ReqMenuList.MODULE_INFORMATION;
        reqMyIntegralOrderInfo.method = ReqMenuList.METHOD_SET_INTEGRAL_ORDER;

        return getRes(server.getMyIntegralOrderInfoType(reqMyIntegralOrderInfo), viewProxy);
    }

}
