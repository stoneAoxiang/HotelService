package com.hotel.service.net.module.house;

import com.hotel.service.net.BaseApi;
import com.hotel.service.net.ViewProxyInterface;
import com.hotel.service.net.module.house.model.ReqRentalList;
import com.hotel.service.net.module.house.model.ResRentalList;
import com.hotel.service.net.module.market.model.ReqMenuList;

import javax.inject.Inject;

import retrofit.RestAdapter;
import rx.Observable;

/**
 * Created by aoxiang on 2015/12/9.
 * 缴费记录
 */
public class HouseRentalApi extends BaseApi {
    static HouseRentalInterface server;

    @Inject
    public HouseRentalApi(RestAdapter restAdapter) {
        server = restAdapter.create(HouseRentalInterface.class);
    }

    /**
     * 获取房屋出租信息
     * @return
     */
    public Observable<ResRentalList> getRentalList(ReqRentalList reqRentalList) {

        return getRentalList(reqRentalList, null);
    }

    public Observable<ResRentalList> getRentalList(ReqRentalList reqRentalList, ViewProxyInterface viewProxy) {
        onLoading(viewProxy);

        reqRentalList.module = ReqMenuList.MODULE_FUNCTION;
        reqRentalList.method = "showHouseRentalList";
        return getRes(server.getRentalList(reqRentalList), viewProxy);
    }


}
