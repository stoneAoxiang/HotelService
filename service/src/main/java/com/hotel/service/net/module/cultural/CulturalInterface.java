package com.hotel.service.net.module.cultural;

import com.hotel.service.net.module.cultural.model.ReqCulturalList;
import com.hotel.service.net.module.cultural.model.ResCulturalList;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by Administrator on 2016/4/5.
 */
public interface CulturalInterface {

    /**
     * 文化活动
     * @return
     */
    @POST("/app/terminalapi/call")
    @FormUrlEncoded
    Observable<ResCulturalList> getCulturalList(@Field("requestValue") ReqCulturalList reqCulturalList);
}
