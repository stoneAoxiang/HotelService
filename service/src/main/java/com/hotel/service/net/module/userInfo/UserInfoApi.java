package com.hotel.service.net.module.userInfo;

import com.hotel.service.BuildConfig;
import com.hotel.service.net.BaseApi;
import com.hotel.service.net.ViewProxyInterface;
import com.hotel.service.net.module.market.model.ReqMenuList;
import com.hotel.service.net.module.userInfo.model.ReqMyNoticeList;
import com.hotel.service.net.module.userInfo.model.ReqRecieverAddress;
import com.hotel.service.net.module.userInfo.model.ReqSetNickName;
import com.hotel.service.net.module.userInfo.model.ResMyNoticeList;
import com.hotel.service.net.module.userInfo.model.ResReciverAddress;
import com.hotel.service.net.module.userInfo.model.ResSetNickName;
import com.hotel.service.util.Config;

import javax.inject.Inject;

import retrofit.RestAdapter;
import rx.Observable;
import timber.log.Timber;

/**
 * Created by aoxiang on 2015/12/23.
 */
public class UserInfoApi extends BaseApi {
    static UserInfoInterface server;

    //利浪专用接口
    private UserInfoInterface interfaceServer;

    /**
     * 链接到利浪平台，需要新建RestAdapter
     */
    private UserInfoInterface newInterFaceServer() {

        if (null == interfaceServer) {
            RestAdapter ra = new RestAdapter.Builder()
                    .setEndpoint(Config.PUBLIC_SERVER_URL)
                    .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
                    .setLog(Timber::i)
                    .build();

            return interfaceServer = ra.create(UserInfoInterface.class);

        }

        return interfaceServer;
    }

    @Inject
    public UserInfoApi(RestAdapter restAdapter) {
        server = restAdapter.create(UserInfoInterface.class);
    }

    public Observable<ResSetNickName> setNickName(ReqSetNickName reqSetNickName) {
        return setNickName(reqSetNickName, null);
    }

    public Observable<ResSetNickName> setNickName(ReqSetNickName reqSetNickName, ViewProxyInterface viewProxy) {
        onLoading(viewProxy);
        reqSetNickName.module = ReqMenuList.MODULE_INFORMATION;
        reqSetNickName.method = ReqMenuList.METHOD_SET_NICK_NAME;
        return getRes(server.setNickName(reqSetNickName), viewProxy);


    }

    public Observable<ResMyNoticeList> getMyNoticeList(ReqMyNoticeList reqMyNoticeList) {
        return getMyNoticeList(reqMyNoticeList, null);
    }

    public Observable<ResMyNoticeList> getMyNoticeList(ReqMyNoticeList
                                                               reqMyNoticeList, ViewProxyInterface viewProxy) {
        onLoading(viewProxy);
        reqMyNoticeList.module = ReqMenuList.MODULE_INFORMATION;
        reqMyNoticeList.method = ReqMenuList.METHOD_GET_MY_NOTICE_LIST;
        return getRes(server.getMyNoticeList(reqMyNoticeList), viewProxy);
    }

    public Observable<ResReciverAddress> recieverAddressManager(ReqRecieverAddress reqInfo) {
        return recieverAddressManager(reqInfo, null);
    }

    public Observable<ResReciverAddress> recieverAddressManager(ReqRecieverAddress reqInfo, ViewProxyInterface viewProxy) {
        onLoading(viewProxy);
        reqInfo.module = ReqMenuList.MODULE_BUSINESS;
        reqInfo.method = "pushBuyersInfo";

        newInterFaceServer();
        return getRes(interfaceServer.recieverAddressManager(reqInfo), viewProxy);
    }
}
