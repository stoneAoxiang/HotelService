package com.hotel.service.net.module.integer;

        import com.hotel.service.net.module.integer.model.ReqScoreGoods;
        import com.hotel.service.net.module.integer.model.ResScoreGoods;

        import retrofit.http.Field;
        import retrofit.http.FormUrlEncoded;
        import retrofit.http.POST;
        import rx.Observable;

/**
 * Created by hyz on 2015/12/9.
 */
public interface ScoreGoodsInterface {

    /**
     * 积分兑换商品信息
     * @return
     */
    @POST("/app/terminalapi/call")
    @FormUrlEncoded
    Observable<ResScoreGoods> getScoreGoodsList(@Field("requestValue") ReqScoreGoods reqScoreGoods);
}
