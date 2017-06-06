package com.hotel.service.net.module.communityOffer;

import com.hotel.service.net.module.bulletin.model.ReqBulletinList;
import com.hotel.service.net.module.bulletin.model.ResBulletinList;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by aoxiang on 2015/12/9.
 */
public interface offerInterface {

    /**
     * 获取服务公告信息
     * @return
     */
    @POST("/app/terminalapi/call")
    @FormUrlEncoded
    Observable<ResBulletinList> getBulletinList(@Field("requestValue") ReqBulletinList reqBulletin);

}
