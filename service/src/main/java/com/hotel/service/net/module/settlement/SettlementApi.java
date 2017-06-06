package com.hotel.service.net.module.settlement;

import com.hotel.service.BuildConfig;
import com.hotel.service.net.BaseApi;
import com.hotel.service.net.ViewProxyInterface;
import com.hotel.service.net.module.BaseReqModel;
import com.hotel.service.net.module.settlement.model.ReqServiceReservation;
import com.hotel.service.net.module.settlement.model.ResServiceReservation;
import com.hotel.service.util.Config;

import javax.inject.Inject;

import retrofit.RestAdapter;
import rx.Observable;
import timber.log.Timber;

/**
 * Created by hyz on 2016/1/26.
 */
public class SettlementApi extends BaseApi{

    static SettlementInterface server;

    //利浪专用接口
    private SettlementInterface interfaceServer;

    /**
     * 链接到利浪平台，需要新建RestAdapter
     */
    private SettlementInterface newInterFaceServer() {

        if (null == interfaceServer) {
            RestAdapter ra = new RestAdapter.Builder()
                    .setEndpoint(Config.PUBLIC_SERVER_URL)
                    .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
                    .setLog(Timber::i)
                    .build();

            return interfaceServer = ra.create(SettlementInterface.class);

        }

        return interfaceServer;
    }

    @Inject
    public SettlementApi(RestAdapter restAdapter){
        server = restAdapter.create(SettlementInterface.class);
    }

    public Observable<ResServiceReservation> getServiceReservation(ReqServiceReservation reqInfo){
        return  getServiceReservation(reqInfo, null);
    }

    public Observable<ResServiceReservation> getServiceReservation(ReqServiceReservation reqInfo, ViewProxyInterface viewProxy) {
        onLoading(viewProxy);
        reqInfo.module = BaseReqModel.MODULE_COMMON;
        reqInfo.method = "serviceReservation";

        newInterFaceServer();

        return getRes(interfaceServer.getServiceReservation(reqInfo), viewProxy);
    }


}
