package com.hotel.service.net.module.signin;

import com.hotel.service.net.BaseApi;
import com.hotel.service.net.ViewProxyInterface;
import com.hotel.service.net.module.market.model.ReqMenuList;
import com.hotel.service.net.module.signin.model.ReqSetSignin;
import com.hotel.service.net.module.signin.model.ReqSigninList;
import com.hotel.service.net.module.signin.model.ResSetSignin;
import com.hotel.service.net.module.signin.model.ResSigninList;

import javax.inject.Inject;

import retrofit.RestAdapter;
import rx.Observable;

/**
 * Created by aoxiang on 2015/12/15.
 */
public class SigninApi  extends BaseApi {
    static SigninInterface server;

    @Inject
    public SigninApi(RestAdapter restAdapter) {
        server = restAdapter.create(SigninInterface.class);
    }

    public Observable<ResSigninList> getSigninList(ReqSigninList reqSigninList) {
        return getSigninList(reqSigninList, null);
    }

    public Observable<ResSigninList> getSigninList(ReqSigninList reqSigninList, ViewProxyInterface viewProxy) {
        onLoading(viewProxy);
        reqSigninList.module = ReqMenuList.MODULE_INFORMATION;
        reqSigninList.method = ReqMenuList.METHOD_GET_SIGNIN_INFO;
        return getRes(server.getSigninList(reqSigninList), viewProxy);
    }

    public Observable<ResSetSignin> setSignin(ReqSetSignin reqSetSignin) {
        return setSignin(reqSetSignin, null);
    }

    public Observable<ResSetSignin> setSignin(ReqSetSignin reqSetSignin, ViewProxyInterface viewProxy) {
        onLoading(viewProxy);
        reqSetSignin.module = ReqMenuList.MODULE_INFORMATION;
        reqSetSignin.method = ReqMenuList.METHOD_SET_SIGNIN_INFO;
        return getRes(server.setSignin(reqSetSignin), viewProxy);
    }
}
