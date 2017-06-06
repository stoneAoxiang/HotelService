package com.hotel.service.net.module.integer;

import com.hotel.service.net.BaseApi;
import com.hotel.service.net.ViewProxyInterface;
import com.hotel.service.net.module.integer.model.ReqScoreGoods;
import com.hotel.service.net.module.integer.model.ResScoreGoods;
import com.hotel.service.net.module.market.MarketInterface;
import com.hotel.service.net.module.market.model.ReqMenuList;

import javax.inject.Inject;

import retrofit.RestAdapter;
import rx.Observable;

/**
 * Created by hyz on 2015/12/9.
 */
public class ScoreGoodsApi extends BaseApi {

    static ScoreGoodsInterface server;

    //利浪专用接口
    private MarketInterface interfaceServer;

    @Inject
    public ScoreGoodsApi(RestAdapter restAdapter) {
        server = restAdapter.create(ScoreGoodsInterface.class);
    }

    public Observable<ResScoreGoods> getScoreGoodsList(ReqScoreGoods reqScoreGoods) {
        return getScoreGoodsList(reqScoreGoods, null);
    }

    public Observable<ResScoreGoods> getScoreGoodsList(ReqScoreGoods reqScoreGoods, ViewProxyInterface viewProxy) {
        onLoading(viewProxy);
        reqScoreGoods.module = ReqMenuList.MODULE_BUY;
        reqScoreGoods.method = ReqMenuList.METHOD_GET_SCORE_GOODS;

        return getRes(server.getScoreGoodsList(reqScoreGoods), viewProxy);
    }
}