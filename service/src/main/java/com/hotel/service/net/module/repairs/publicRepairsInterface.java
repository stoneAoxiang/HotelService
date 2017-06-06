package com.hotel.service.net.module.repairs;

import com.hotel.service.net.module.repairs.model.ReqPublicProgression;
import com.hotel.service.net.module.repairs.model.ReqPublicRepairs;
import com.hotel.service.net.module.repairs.model.ResPublicProgression;
import com.hotel.service.net.module.repairs.model.ResPublicRepairs;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by hyz on 2016/1/25.
 */
public interface publicRepairsInterface {

    //列表详情
    @POST("/app/terminalapi/call")
    @FormUrlEncoded
    Observable<ResPublicRepairs> getPublicRepairsInfo(@Field("requestValue") ReqPublicRepairs reqPUblicRepairs);



    // 获取公共报修进度
    @POST("/app/terminalapi/call")
    @FormUrlEncoded
    Observable<ResPublicProgression> getPublicPro(@Field("requestValue") ReqPublicProgression reqPublicProgression);



}
