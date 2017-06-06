package com.hotel.service.net.module.integer;

import com.hotel.service.net.BaseApi;
import com.hotel.service.net.ViewProxyInterface;
import com.hotel.service.net.module.integer.model.ReqScoreLevel;
import com.hotel.service.net.module.integer.model.ResScoreLevel;
import com.hotel.service.net.module.market.model.ReqMenuList;

import javax.inject.Inject;

import retrofit.RestAdapter;
import rx.Observable;

/**
 * Created by hyz on 2015/12/14.
 */
public class ScoreLevelApi extends BaseApi {

    static ScoreLevelInterface server;

    @Inject
    public ScoreLevelApi(RestAdapter restAdapter) {
        server = restAdapter.create(ScoreLevelInterface.class);
    }

    public Observable<ResScoreLevel> getScoreLevelList(ReqScoreLevel reqScoreLevel) {
        return getScoreLevelList(reqScoreLevel, null);
    }

    public Observable<ResScoreLevel> getScoreLevelList(ReqScoreLevel reqScoreLevel, ViewProxyInterface viewProxy) {
        onLoading(viewProxy);
        reqScoreLevel.module = ReqMenuList.MODULE_BUY;
        reqScoreLevel.method = ReqMenuList.METHOD_GET_SCORE_LEVEL;

        return getRes(server.getScoreLevelList(reqScoreLevel), viewProxy);
    }
}
