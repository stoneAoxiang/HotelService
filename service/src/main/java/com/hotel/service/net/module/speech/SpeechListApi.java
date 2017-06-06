package com.hotel.service.net.module.speech;

import com.hotel.service.net.BaseApi;
import com.hotel.service.net.ViewProxyInterface;
import com.hotel.service.net.module.market.model.ReqMenuList;
import com.hotel.service.net.module.speech.model.ReqSpeechList;
import com.hotel.service.net.module.speech.model.ResSpeechList;

import javax.inject.Inject;

import retrofit.RestAdapter;
import rx.Observable;

/**
 * Created by hyz on 2015/12/7.
 */
public class SpeechListApi extends BaseApi {

    static SpeechListInterface server;

    @Inject
    public SpeechListApi(RestAdapter restAdapter){
        server = restAdapter.create(SpeechListInterface.class);
    }

    public Observable<ResSpeechList> getSpeechListType(ReqSpeechList reqSpeechList) {
        return getSpeechListType(reqSpeechList, null);
    }

    public Observable<ResSpeechList> getSpeechListType(ReqSpeechList reqSpeechList, ViewProxyInterface viewProxy) {
        onLoading(viewProxy);
        reqSpeechList.module = ReqMenuList.MODULE_HOME;
        reqSpeechList.method = ReqMenuList.METHOD_GET_ADDRESS_LIST;

        return getRes(server.getSpeechListType(reqSpeechList), viewProxy);
    }


}
