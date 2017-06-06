package com.hotel.service.net.module.recruit;

import com.hotel.service.net.module.newinfo.model.ReqNewInfoList;
import com.hotel.service.net.module.newinfo.model.ResNewInfoList;
import com.hotel.service.net.module.recruit.model.ReqNewRecruit;
import com.hotel.service.net.module.recruit.model.ResNewRecruit;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by hyz on 2015/12/26.
 */
public interface NewRecruitInterface {

    /**
     * 获取资讯详细信息
     * @return
     */
    @POST("/app/terminalapi/call")
    @FormUrlEncoded
    Observable<ResNewRecruit> getNewRecruitType(@Field("requestValue") ReqNewRecruit reqNewRecruit);

}
