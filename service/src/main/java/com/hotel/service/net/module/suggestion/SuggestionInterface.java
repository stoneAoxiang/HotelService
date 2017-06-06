package com.hotel.service.net.module.suggestion;

import com.hotel.service.net.module.suggestion.model.ReqSuggestEvaluate;
import com.hotel.service.net.module.suggestion.model.ReqSuggestInfo;
import com.hotel.service.net.module.suggestion.model.ReqSuggestList;
import com.hotel.service.net.module.suggestion.model.ResSuggestEvaluate;
import com.hotel.service.net.module.suggestion.model.ResSuggestInfo;
import com.hotel.service.net.module.suggestion.model.ResSuggestList;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by hyz on 2016/2/24.
 */
public interface SuggestionInterface {


    //获取业主投诉与建议列表
    @POST("/app/terminalapi/call")
    @FormUrlEncoded
    Observable<ResSuggestList> getSuggestList(@Field("requestValue") ReqSuggestList reqOwnerSuggestList);

    //获取业主投诉与建议详情
    @POST("/app/terminalapi/call")
    @FormUrlEncoded
    Observable<ResSuggestInfo> getSuggestInfo(@Field("requestValue") ReqSuggestInfo reqOwnerSuggestInfo);

    //设置业主投诉建议满意度评分
    @POST("/app/terminalapi/call")
    @FormUrlEncoded
    Observable<ResSuggestEvaluate> setComplainSuggestionEvaluate(@Field("requestValue") ReqSuggestEvaluate reqSuggestEvaluate);


}
