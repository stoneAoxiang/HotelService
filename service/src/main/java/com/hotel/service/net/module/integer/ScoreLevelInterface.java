package com.hotel.service.net.module.integer;



import com.hotel.service.net.module.integer.model.ReqScoreLevel;
import com.hotel.service.net.module.integer.model.ResScoreLevel;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by hyz on 2015/12/14.
 */
public interface ScoreLevelInterface  {

    /**
     * 积分等级信息
     * @return
     */
    @POST("/app/terminalapi/call")
    @FormUrlEncoded
    Observable<ResScoreLevel> getScoreLevelList(@Field("requestValue") ReqScoreLevel reqScoreLevel);
}
