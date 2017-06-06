package com.hotel.service.net.module.integer;

import com.hotel.service.net.BaseApi;
import com.hotel.service.net.ViewProxyInterface;
import com.hotel.service.net.module.integer.model.ReqMyScore;
import com.hotel.service.net.module.integer.model.ResMyScore;
import com.hotel.service.net.module.market.model.ReqMenuList;

import javax.inject.Inject;

import retrofit.RestAdapter;
import rx.Observable;

/**
 * Created by hyz on 2015/12/14.
 */
public class MyScoreApi extends BaseApi {

    static MyScoreInterface server;

    @Inject
    public MyScoreApi(RestAdapter restAdapter) {
        server = restAdapter.create(MyScoreInterface.class);
    }

    public Observable<ResMyScore> getMyScore(ReqMyScore reqMyScore) {

        return getMyScore(reqMyScore, null);
    }

    public Observable<ResMyScore> getMyScore(ReqMyScore reqMyScore, ViewProxyInterface viewProxy) {
        onLoading(viewProxy);
        reqMyScore.module = ReqMenuList.MODULE_BUY;
        reqMyScore.method = ReqMenuList.METHOD_GET_MY_SCORE;
        return getRes(server.getMyScore(reqMyScore), viewProxy);
    }
}
