package com.hotel.service.net.module.newinfo;

import com.hotel.service.net.module.newinfo.model.ReqNewInfo;
import com.hotel.service.net.module.newinfo.model.ResNewInfo;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by hyz on 2015/12/3.
 */
public interface NewInfoInterface {

    /**
     * 资讯分类获取
     * @return
     */
    @POST("/app/terminalapi/call")
    @FormUrlEncoded
    Observable<ResNewInfo> getNewInfoList(@Field("requestValue") ReqNewInfo reqNewInfo);
}
