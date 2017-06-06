package com.hotel.service.net.module.integer;

import com.hotel.service.net.module.integer.model.ReqMyScore;
import com.hotel.service.net.module.integer.model.ResMyScore;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by hyz on 2015/12/14.
 */
public interface MyScoreInterface {

    /**
     * 我的积分
     * @return
     */
    @POST("/app/terminalapi/call")
    @FormUrlEncoded
    Observable<ResMyScore> getMyScore(@Field("requestValue") ReqMyScore reqMyScore);
}
