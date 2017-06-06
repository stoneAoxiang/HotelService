package com.hotel.service.net.module.award;

import com.hotel.service.net.BaseApi;
import com.hotel.service.net.ViewProxyInterface;
import com.hotel.service.net.module.award.model.ReqAwardList;
import com.hotel.service.net.module.award.model.ReqCompleteAward;
import com.hotel.service.net.module.award.model.ResAwardList;
import com.hotel.service.net.module.award.model.ResCompleteAward;
import com.hotel.service.net.module.market.model.ReqMenuList;

import javax.inject.Inject;

import retrofit.RestAdapter;
import rx.Observable;

/**
 * Created by aoxiang on 2015/12/2.
 */
public class AwardApi extends BaseApi {
    static AwardInterface server;

    @Inject
    public AwardApi(RestAdapter restAdapter) {
        server = restAdapter.create(AwardInterface.class);
    }

    public Observable<ResAwardList> getaAwardList(ReqAwardList reqAwardList) {
        return getaAwardList(reqAwardList, null);
    }

    public Observable<ResAwardList> getaAwardList(ReqAwardList reqAwardList, ViewProxyInterface viewProxy) {
        onLoading(viewProxy);
        reqAwardList.module = ReqMenuList.MODULE_INFORMATION;
        reqAwardList.method = ReqMenuList.METHOD_GET_REWARD_LIST;
        return getRes(server.getaAwardList(reqAwardList), viewProxy);
    }

    public Observable<ResCompleteAward> setAward(ReqCompleteAward reqCompleteAward) {
        return setAward(reqCompleteAward, null);
    }

    public Observable<ResCompleteAward> setAward(ReqCompleteAward reqCompleteAward, ViewProxyInterface viewProxy) {
        onLoading(viewProxy);
        reqCompleteAward.module = ReqMenuList.MODULE_INFORMATION;
        reqCompleteAward.method = ReqMenuList.METHOD_COMPLETE_REWARD;
        return getRes(server.setAward(reqCompleteAward), viewProxy);
    }
}
