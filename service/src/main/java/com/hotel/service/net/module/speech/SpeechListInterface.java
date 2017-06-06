package com.hotel.service.net.module.speech;

import com.hotel.service.net.module.speech.model.ReqSpeechList;
import com.hotel.service.net.module.speech.model.ResSpeechList;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by hyz on 2015/12/7.
 */
public interface SpeechListInterface {

    /**
     * 获取资讯详细信息
     * @return
     */
    @POST("/app/terminalapi/call")
    @FormUrlEncoded
    Observable<ResSpeechList> getSpeechListType(@Field("requestValue") ReqSpeechList reqSpeechList);

}
