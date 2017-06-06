package com.hotel.service.net.module.integer;

import com.hotel.service.net.BaseApi;
import com.hotel.service.net.ViewProxyInterface;
import com.hotel.service.net.module.integer.model.ReqIntegralGoodsExchange;
import com.hotel.service.net.module.integer.model.ResIntegralGoodsExchange;
import com.hotel.service.net.module.market.model.ReqMenuList;

import javax.inject.Inject;

import retrofit.RestAdapter;
import rx.Observable;

/**
 * Created by hyz on 2015/12/14.
 */
public class IntegralGoodsExchangeApi extends BaseApi {

    static IntegralGoodsExchangeInterface server;

    @Inject
    public IntegralGoodsExchangeApi(RestAdapter restAdapter){
        server = restAdapter.create(IntegralGoodsExchangeInterface.class);
    }

    public Observable<ResIntegralGoodsExchange> getIntegralGoodsExchangeType(ReqIntegralGoodsExchange reqIntegralGoodsExchange) {
        return getIntegralGoodsExchangeType(reqIntegralGoodsExchange, null);
    }

    public Observable<ResIntegralGoodsExchange> getIntegralGoodsExchangeType(ReqIntegralGoodsExchange reqIntegralGoodsExchange,ViewProxyInterface viewProxy){
        reqIntegralGoodsExchange.module = ReqMenuList.MODULE_BUY;
        reqIntegralGoodsExchange.method = ReqMenuList.METHOD_SET_SCORE_CHANGE;

        return getRes(server.getIntegralGoodsExchangeType(reqIntegralGoodsExchange), viewProxy);
    }

}
