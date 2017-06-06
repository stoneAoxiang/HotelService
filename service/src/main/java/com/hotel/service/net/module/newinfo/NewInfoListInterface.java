package com.hotel.service.net.module.newinfo;

import com.hotel.service.net.module.newinfo.model.ReqNewInfoList;
import com.hotel.service.net.module.newinfo.model.ResNewInfoList;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by hyz on 2015/12/7.
 */
public interface NewInfoListInterface {

    /**
     * 获取资讯详细信息
     * @return
     */
    @POST("/app/terminalapi/call")
    @FormUrlEncoded
    Observable<ResNewInfoList> getNewInfoListType(@Field("requestValue") ReqNewInfoList reqNewInfoList);

}
