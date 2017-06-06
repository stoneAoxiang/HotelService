package com.hotel.service.net.module.payment;

import android.util.Log;

import com.hotel.service.BuildConfig;
import com.hotel.service.net.BaseApi;
import com.hotel.service.net.ViewProxyInterface;
import com.hotel.service.net.module.market.model.ReqMenuList;
import com.hotel.service.net.module.payment.model.ReqPay;
import com.hotel.service.net.module.payment.model.ReqPaymentDetail;
import com.hotel.service.net.module.payment.model.ReqPaymentList;
import com.hotel.service.net.module.payment.model.ResPay;
import com.hotel.service.net.module.payment.model.ResPaymentDetail;
import com.hotel.service.net.module.payment.model.ResPaymentList;
import com.hotel.service.util.Config;

import javax.inject.Inject;

import retrofit.RestAdapter;
import rx.Observable;
import timber.log.Timber;

/**
 * Created by aoxiang on 2015/12/9.
 * 缴费记录
 */
public class PayApi extends BaseApi {
    static PayInterface server;

    static PayInterface interfaceServer;

    private String TAG = PayApi.class.getCanonicalName();

    @Inject
    public PayApi(RestAdapter restAdapter) {
        server = restAdapter.create(PayInterface.class);
    }

    /**
     * 获取缴费记录信息
     * @return
     */
    public Observable<ResPaymentList> getPaymentList(ReqPaymentList reqPaymentList) {

        return getPaymentList(reqPaymentList, null);
    }

    public Observable<ResPaymentList> getPaymentList(ReqPaymentList reqPaymentList, ViewProxyInterface viewProxy) {
        onLoading(viewProxy);

        reqPaymentList.module = ReqMenuList.MODULE_PAY;
        reqPaymentList.method = "getPropertyPaymentList";
        return getRes(server.getPaymentList(reqPaymentList), viewProxy);
    }

    public Observable<ResPaymentDetail> getPaymentDetail(ReqPaymentDetail reqPaymentDetail) {

        return getPaymentDetail(reqPaymentDetail, null);
    }

    public Observable<ResPaymentDetail> getPaymentDetail(ReqPaymentDetail reqPaymentDetail, ViewProxyInterface viewProxy) {
        onLoading(null);

        reqPaymentDetail.module = ReqMenuList.MODULE_PAY;
        reqPaymentDetail.method = "getPropertyPaymentDetail";
        return getRes(server.getPaymentDetail(reqPaymentDetail), null);
    }

    public Observable<ResPay> getPayTN(ReqPay reqPay) {

        Log.i(TAG, "获取交通银行测试平台TN值");

        return getPayTN(reqPay, null);
    }



    public Observable<ResPay> getPayTN(ReqPay reqPay, ViewProxyInterface viewProxy) {
        onLoading(viewProxy);
        reqPay.module = "AppPay";
        reqPay.method = "fastAppBalance";

        newInterFaceServer();

        return getRes(interfaceServer.getPayTN(reqPay), viewProxy);
    }

    /**
     * 链接到利浪平台，需要新建RestAdapter
     */
    private PayInterface newInterFaceServer() {
        Log.i(TAG, "链接到利浪平台===================");

        if (null == interfaceServer) {
            RestAdapter ra = new RestAdapter.Builder()
                  .setEndpoint(Config.PUBLIC_SERVER_URL)
//                    .setEndpoint("http://192.168.1.51:9000")

            .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
                    .setLog(Timber::i)
                    .build();

            return interfaceServer = ra.create(PayInterface.class);

        }

        return interfaceServer;
    }


}
