package com.hotel.service.net.module.positon;

import com.hotel.service.net.module.positon.model.ReqOfferList;
import com.hotel.service.net.module.positon.model.ReqOfferType;
import com.hotel.service.net.module.positon.model.ResOfferList;
import com.hotel.service.net.module.positon.model.ResOfferType;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by aoxiang on 2015/12/9.
 */
public interface OfferInterface {

    /**
     * 获取职位信息
     * @return
     */
    @POST("/app/terminalapi/call")
    @FormUrlEncoded
    Observable<ResOfferList> getOfferList(@Field("requestValue") ReqOfferList reqOfferList);

    /**
     * 获取职位类型
     * @return
     */
    @POST("/app/terminalapi/call")
    @FormUrlEncoded
    Observable<ResOfferType> getOfferType(@Field("requestValue") ReqOfferType reqOfferType);
}
