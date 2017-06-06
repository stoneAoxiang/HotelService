package com.hotel.service.net.module.house;

import com.hotel.service.net.module.house.model.ReqRentalList;
import com.hotel.service.net.module.house.model.ResRentalList;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by aoxiang on 2015/12/9.
 * 缴费记录
 */
public interface HouseRentalInterface {

    /**
     * 获取房屋出租信息
     * @return
     */
    @POST("/app/terminalapi/call")
    @FormUrlEncoded
    Observable<ResRentalList> getRentalList(@Field("requestValue") ReqRentalList reqRentalList);

}
