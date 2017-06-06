package com.hotel.service.net.module.util;

import com.hotel.service.net.module.BaseReqModel;
import com.hotel.service.net.module.BaseResModel;
import com.hotel.service.net.module.util.model.ResCheckVersion;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import rx.Observable;

/**
 * UtilInterface
 * Created by sharyuke on 15-6-8.
 */
public interface UtilInterface {

    @POST("/app/terminalapi/call")
    @FormUrlEncoded
    Observable<ResCheckVersion> checkVersion(@Field("requestValue") BaseReqModel reqModel);
}
