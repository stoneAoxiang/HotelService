package com.hotel.service.net.module.balance;

import com.hotel.service.net.BaseApi;
import com.hotel.service.net.ViewProxyInterface;
import com.hotel.service.net.module.balance.model.ReqBalance;
import com.hotel.service.net.module.balance.model.ResBalance;
import com.hotel.service.net.module.market.model.ReqMenuList;

import javax.inject.Inject;

import retrofit.RestAdapter;
import rx.Observable;

/**
 * Created by aoxiang on 2015/12/11.
 */
public class BalanceApi  extends BaseApi {
    static BalanceInterface server;

    @Inject
    public BalanceApi(RestAdapter restAdapter) {
        server = restAdapter.create(BalanceInterface.class);
    }

    public Observable<ResBalance> setBalanceSucess(ReqBalance reqBalance) {
        return setBalanceSucess(reqBalance, null);
    }

    public Observable<ResBalance> setBalanceSucess(ReqBalance reqBalance, ViewProxyInterface viewProxy) {
        onLoading(viewProxy);
        reqBalance.module = ReqMenuList.MODULE_SHOP;
        reqBalance.method = ReqMenuList.METHOD_SET_RETRIEVE;
        return getRes(server.setBalanceSucess(reqBalance), viewProxy);
    }
}
