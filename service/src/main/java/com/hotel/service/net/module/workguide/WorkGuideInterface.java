package com.hotel.service.net.module.workguide;

import com.hotel.service.net.module.workguide.model.ReqWorkGuide;
import com.hotel.service.net.module.workguide.model.ResWorkGuide;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by hyz on 2015/12/7.
 */
public interface WorkGuideInterface {

    /**
     * 获取办事指南
     * @return
     */
    @POST("/app/terminalapi/call")
    @FormUrlEncoded
    Observable<ResWorkGuide> getWorkGuide(@Field("requestValue") ReqWorkGuide reqWorkGuide);

}
