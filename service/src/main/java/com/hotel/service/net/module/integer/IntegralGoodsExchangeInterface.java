package com.hotel.service.net.module.integer;

import com.hotel.service.net.module.integer.model.ReqIntegralGoodsExchange;
import com.hotel.service.net.module.integer.model.ResIntegralGoodsExchange;
import com.hotel.service.net.module.newinfo.model.ReqNewInfoList;
import com.hotel.service.net.module.newinfo.model.ResNewInfoList;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by hyz on 2015/12/14.
 */
public interface IntegralGoodsExchangeInterface {

    /**
     * 获取积分商品兑换信息
     * @return
     */
    @POST("/app/terminalapi/call")
    @FormUrlEncoded
    Observable<ResIntegralGoodsExchange> getIntegralGoodsExchangeType(@Field("requestValue") ReqIntegralGoodsExchange reqIntegralGoodsExchange);
}
