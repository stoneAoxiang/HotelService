package com.hotel.service.net.module.award;

import com.hotel.service.net.module.award.model.ReqAwardList;
import com.hotel.service.net.module.award.model.ReqCompleteAward;
import com.hotel.service.net.module.award.model.ResAwardList;
import com.hotel.service.net.module.award.model.ResCompleteAward;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by aoxiang on 2015/12/2.
 */
public interface AwardInterface {
    /**
     * 获取奖励列表
     * @return
     */
    @POST("/app/terminalapi/call")
    @FormUrlEncoded
    Observable<ResAwardList> getaAwardList(@Field("requestValue") ReqAwardList reqAwardList);

    /**
     * 获取奖励列表
     * @return
     */
    @POST("/app/terminalapi/call")
    @FormUrlEncoded
    Observable<ResCompleteAward> setAward(@Field("requestValue") ReqCompleteAward reqCompleteAward);
}
