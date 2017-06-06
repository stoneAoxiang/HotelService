package com.hotel.service.net.module.settlement;

import com.hotel.service.net.module.settlement.model.ReqServiceReservation;
import com.hotel.service.net.module.settlement.model.ResServiceReservation;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by hyz on 2016/1/25.
 */
public interface SettlementInterface {

    //获取服务预约
    @POST("/app/terminalapi/call")
    @FormUrlEncoded
    Observable<ResServiceReservation> getServiceReservation(@Field("requestValue") ReqServiceReservation reqInfo);

}
