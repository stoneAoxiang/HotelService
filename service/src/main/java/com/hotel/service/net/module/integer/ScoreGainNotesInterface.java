package com.hotel.service.net.module.integer;

import com.hotel.service.net.module.integer.model.ReqScoreGainNotes;
import com.hotel.service.net.module.integer.model.ResScoreGainNotes;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by hyz on 2015/12/15.
 */
public interface ScoreGainNotesInterface {

    /**
     * 积分获取记录
     * @return
     */
    @POST("/app/terminalapi/call")
    @FormUrlEncoded
    Observable<ResScoreGainNotes> getScoreGainNotesList(@Field("requestValue") ReqScoreGainNotes reqScoreGainNotes);
}
