package com.hotel.service.net.module.lottery;

import com.hotel.service.net.BaseApi;
import com.hotel.service.net.ViewProxyInterface;
import com.hotel.service.net.module.lottery.model.ReqLottery;
import com.hotel.service.net.module.lottery.model.ReqLotteryList;
import com.hotel.service.net.module.lottery.model.ReqSaveLottery;
import com.hotel.service.net.module.lottery.model.ResLottery;
import com.hotel.service.net.module.lottery.model.ResLotteryList;
import com.hotel.service.net.module.lottery.model.ResSaveLottery;
import com.hotel.service.net.module.market.model.ReqMenuList;

import javax.inject.Inject;

import retrofit.RestAdapter;
import rx.Observable;

/**
 * Created by aoxiang on 2015/12/9.
 */
public class LotteryApi extends BaseApi {
    static LotteryInterface server;

    @Inject
    public LotteryApi(RestAdapter restAdapter) {
        server = restAdapter.create(LotteryInterface.class);
    }

    public Observable<ResLottery> getLottery(ReqLottery reqLottery) {
        return getLottery(reqLottery, null);
    }

    public Observable<ResLottery> getLottery(ReqLottery reqLottery, ViewProxyInterface viewProxy) {
        onLoading(viewProxy);
        reqLottery.module = ReqMenuList.MODULE_COMMON;

        if(reqLottery.isLogin){
            reqLottery.method = ReqMenuList.METHOD_GET_PRIZES;
        }else {
            reqLottery.method = ReqMenuList.METHOD_GET_UN_PRIZES;
        }

        return getRes(server.getLottery(reqLottery), viewProxy);
    }

    public Observable<ResSaveLottery> setLottery(ReqSaveLottery reqSaveLottery) {
        return setLottery(reqSaveLottery, null);
    }

    public Observable<ResSaveLottery> setLottery(ReqSaveLottery reqSaveLottery, ViewProxyInterface viewProxy) {
        onLoading(viewProxy);
        reqSaveLottery.module = ReqMenuList.MODULE_COMMON;
        reqSaveLottery.method = ReqMenuList.METHOD_SET_PRIZES;
        return getRes(server.setLottery(reqSaveLottery), viewProxy);
    }

    public Observable<ResLotteryList> getLotteryList(ReqLotteryList reqLotteryList) {
        return getLotteryList(reqLotteryList, null);
    }

    public Observable<ResLotteryList> getLotteryList(ReqLotteryList reqLotteryList, ViewProxyInterface viewProxy) {
        onLoading(viewProxy);
        reqLotteryList.module = ReqMenuList.MODULE_INFORMATION;
        reqLotteryList.method = ReqMenuList.METHOD_GET_MY_PRIZES;
        return getRes(server.getLotteryList(reqLotteryList), viewProxy);
    }
}
