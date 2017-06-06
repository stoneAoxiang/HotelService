package com.hotel.service.net.module.userInfo;

import com.hotel.service.net.module.userInfo.model.ReqMyNoticeList;
import com.hotel.service.net.module.userInfo.model.ReqRecieverAddress;
import com.hotel.service.net.module.userInfo.model.ReqSetNickName;
import com.hotel.service.net.module.userInfo.model.ResMyNoticeList;
import com.hotel.service.net.module.userInfo.model.ResReciverAddress;
import com.hotel.service.net.module.userInfo.model.ResSetNickName;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by aoxiang on 2015/12/23.
 */
public interface UserInfoInterface {
    /**
     * 设置用户昵称
     * @return
     */
    @POST("/app/terminalapi/call")
    @FormUrlEncoded
    Observable<ResSetNickName> setNickName(@Field("requestValue") ReqSetNickName reqSetNickName);

    /**
     * 获取用户公告信息列表
     * @return
     */
    @POST("/app/terminalapi/call")
    @FormUrlEncoded
    Observable<ResMyNoticeList> getMyNoticeList(@Field("requestValue") ReqMyNoticeList reqMyNoticeList);

    /**
     * 获取用户收货地址
     * @return
     */
    @POST("/app/terminalapi/call")
    @FormUrlEncoded
    Observable<ResReciverAddress> recieverAddressManager(@Field("requestValue") ReqRecieverAddress reqInfo);
}
