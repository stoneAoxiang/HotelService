package com.hotel.service.net.module.integer;

import com.hotel.service.net.module.integer.model.ReqMyIntegralOrderInfo;

import com.hotel.service.net.module.integer.model.ResMyIntegeralOrderInfo;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by hyz on 2015/12/22.
 */
public interface MyIntegeralOrderInfoInterface {
    /**
     * 获取积分兑换信息列表
     * @return
     */
    @POST("/app/terminalapi/call")
    @FormUrlEncoded
    Observable<ResMyIntegeralOrderInfo> getMyIntegralOrderInfoType(@Field("requestValue") ReqMyIntegralOrderInfo reqMyIntegralOrderInfo);
}

