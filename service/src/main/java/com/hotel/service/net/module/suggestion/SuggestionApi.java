package com.hotel.service.net.module.suggestion;

import com.hotel.service.net.BaseApi;
import com.hotel.service.net.ViewProxyInterface;
import com.hotel.service.net.module.BaseReqModel;
import com.hotel.service.net.module.suggestion.model.ReqSuggestEvaluate;
import com.hotel.service.net.module.suggestion.model.ReqSuggestInfo;
import com.hotel.service.net.module.suggestion.model.ReqSuggestList;
import com.hotel.service.net.module.suggestion.model.ResSuggestEvaluate;
import com.hotel.service.net.module.suggestion.model.ResSuggestInfo;
import com.hotel.service.net.module.suggestion.model.ResSuggestList;

import javax.inject.Inject;

import retrofit.RestAdapter;
import rx.Observable;

/**
 * Created by hyz on 2016/2/24.
 */
public class SuggestionApi extends BaseApi {

    static SuggestionInterface server;

    @Inject
    public SuggestionApi(RestAdapter restAdapter) {
        server = restAdapter.create(SuggestionInterface.class);
    }

    //业主投诉建议列表
    public Observable<ResSuggestList> getSuggestList(ReqSuggestList reqOwnerSuggestList) {
        return getSuggestList(reqOwnerSuggestList, null);
    }

    public Observable<ResSuggestList> getSuggestList(ReqSuggestList reqOwnerSuggestList, ViewProxyInterface viewProxy) {
        onLoading(viewProxy);
        reqOwnerSuggestList.module = BaseReqModel.MODULE_FUNCTION;
        reqOwnerSuggestList.method = BaseReqModel.METHOD_SHOW_USER_SUGGESTION_LIST;

        return getRes(server.getSuggestList(reqOwnerSuggestList), viewProxy);
    }

    //业主投诉建议详情
    public Observable<ResSuggestInfo> getSuggestInfo(ReqSuggestInfo reqOwnerSuggestInfo) {
        return getSuggestInfo(reqOwnerSuggestInfo, null);
    }

    public Observable<ResSuggestInfo> getSuggestInfo(ReqSuggestInfo reqOwnerSuggestInfo, ViewProxyInterface viewProxy) {
        onLoading(viewProxy);
        reqOwnerSuggestInfo.module = BaseReqModel.MODULE_FUNCTION;
        reqOwnerSuggestInfo.method = BaseReqModel.METHOD_SHOW_USER_SUGGESTION_INFO;

        return getRes(server.getSuggestInfo(reqOwnerSuggestInfo), viewProxy);
    }


    //设置业主投诉建议满意度评分
    public Observable<ResSuggestEvaluate> setComplainSuggestionEvaluate(ReqSuggestEvaluate reqSuggestEvaluate) {
        return setComplainSuggestionEvaluate(reqSuggestEvaluate, null);
    }

    public Observable<ResSuggestEvaluate> setComplainSuggestionEvaluate(ReqSuggestEvaluate reqSuggestEvaluate, ViewProxyInterface viewProxy) {
        onLoading(viewProxy);
        reqSuggestEvaluate.module = BaseReqModel.MODULE_FUNCTION;
        reqSuggestEvaluate.method = BaseReqModel.METHOD_SET_COMPLAIN_SUGGESTION_EVALUATE;

        return getRes(server.setComplainSuggestionEvaluate(reqSuggestEvaluate), viewProxy);
    }

}
