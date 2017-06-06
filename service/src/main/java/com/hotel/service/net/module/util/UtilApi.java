package com.hotel.service.net.module.util;

import com.hotel.service.net.BaseApi;
import com.hotel.service.net.module.BaseReqModel;
import com.hotel.service.net.module.util.model.ReqCheckVersion;
import com.hotel.service.net.module.util.model.ResCheckVersion;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit.RestAdapter;
import rx.Observable;

/**
 * UtilApi
 * Created by sharyuke on 15-6-8.
 */
@Singleton
public class UtilApi extends BaseApi {

    UtilInterface server;

    @Inject
    public UtilApi(RestAdapter restAdapter) {
        server = restAdapter.create(UtilInterface.class);
    }

    public Observable<ResCheckVersion> checkVersion() {

        ReqCheckVersion reqCheckVersion = new ReqCheckVersion();
        reqCheckVersion.module = BaseReqModel.MODULE_INFORMATION;
        reqCheckVersion.method = BaseReqModel.METHOD_CHECK_VERSION;

        reqCheckVersion.appFlag = "1";
        return getRes(server.checkVersion(reqCheckVersion), null);
    }
}
