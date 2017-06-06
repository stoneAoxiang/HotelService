package com.hotel.service.net.module.payment;

import com.hotel.service.net.module.payment.model.ReqPay;
import com.hotel.service.net.module.payment.model.ReqPaymentDetail;
import com.hotel.service.net.module.payment.model.ReqPaymentList;
import com.hotel.service.net.module.payment.model.ResPay;
import com.hotel.service.net.module.payment.model.ResPaymentDetail;
import com.hotel.service.net.module.payment.model.ResPaymentList;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by aoxiang on 2015/12/9.
 * 缴费记录
 */
public interface PayInterface {

    /**
     * 获取缴费记录信息
     * @return
     */
    @POST("/app/terminalapi/call")
    @FormUrlEncoded
    Observable<ResPaymentList> getPaymentList(@Field("requestValue") ReqPaymentList reqPaymentList);

    /**
     * 获取缴费详情信息
     * @return
     */
    @POST("/app/terminalapi/call")
    @FormUrlEncoded
    Observable<ResPaymentDetail> getPaymentDetail(@Field("requestValue") ReqPaymentDetail reqPaymentDetail);

    /**
     * 获取交易流水号(TN)
     * @param reqPay
     * @return
     */
    @POST("/app/terminalapi/call")
    @FormUrlEncoded
    Observable<ResPay> getPayTN(@Field("requestValue") ReqPay reqPay);

}
