package com.hotel.service.net.module.balance;

import com.hotel.service.net.module.balance.model.ReqBalance;
import com.hotel.service.net.module.balance.model.ResBalance;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by aoxiang on 2015/12/11.
 */
public interface BalanceInterface{

        /**
         * 设置结算成功
         *
         * @return
         */
        @POST("/app/terminalapi/call")
        @FormUrlEncoded
        Observable<ResBalance> setBalanceSucess(@Field("requestValue") ReqBalance reqBalance);
}
