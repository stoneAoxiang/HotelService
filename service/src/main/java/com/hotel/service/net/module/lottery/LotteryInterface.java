package com.hotel.service.net.module.lottery;

import com.hotel.service.net.module.lottery.model.ReqLottery;
import com.hotel.service.net.module.lottery.model.ReqLotteryList;
import com.hotel.service.net.module.lottery.model.ReqSaveLottery;
import com.hotel.service.net.module.lottery.model.ResLottery;
import com.hotel.service.net.module.lottery.model.ResLotteryList;
import com.hotel.service.net.module.lottery.model.ResSaveLottery;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by aoxiang on 2015/12/9.
 */
public interface LotteryInterface {

    /**
     * 获取抽奖信息
     * @return
     */
    @POST("/app/terminalapi/call")
    @FormUrlEncoded
    Observable<ResLottery> getLottery(@Field("requestValue") ReqLottery reqLottery);

    /**
     * 提交抽奖信息
     * @return
     */
    @POST("/app/terminalapi/call")
    @FormUrlEncoded
    Observable<ResSaveLottery> setLottery(@Field("requestValue") ReqSaveLottery reqSaveLottery);

    /**
     * 获取抽奖列表信息
     * @return
     */
    @POST("/app/terminalapi/call")
    @FormUrlEncoded
    Observable<ResLotteryList> getLotteryList(@Field("requestValue") ReqLotteryList reqLotteryList);
}
