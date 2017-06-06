package com.hotel.service.net.module.signin;

import com.hotel.service.net.module.signin.model.ReqSetSignin;
import com.hotel.service.net.module.signin.model.ReqSigninList;
import com.hotel.service.net.module.signin.model.ResSetSignin;
import com.hotel.service.net.module.signin.model.ResSigninList;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by aoxiang on 2015/12/15.
 */
public interface SigninInterface {

    /**
     * 获取签到列表信息
     *
     * @return
     */
    @POST("/app/terminalapi/call")
    @FormUrlEncoded
    Observable<ResSigninList> getSigninList(@Field("requestValue") ReqSigninList reqSigninList);

    /**
     * 提交签到表信息
     * @return
     */
    @POST("/app/terminalapi/call")
    @FormUrlEncoded
    Observable<ResSetSignin> setSignin(@Field("requestValue") ReqSetSignin reqSetSignin);
}
