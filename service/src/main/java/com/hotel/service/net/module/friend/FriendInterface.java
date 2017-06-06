package com.hotel.service.net.module.friend;

import com.hotel.service.net.module.friend.model.ReqActiveRegion;
import com.hotel.service.net.module.friend.model.ReqGoodProduct;
import com.hotel.service.net.module.friend.model.ReqReserveProduct;
import com.hotel.service.net.module.friend.model.ReqShareFriend;
import com.hotel.service.net.module.friend.model.ReqShareFriendList;
import com.hotel.service.net.module.friend.model.ResActiveRegion;
import com.hotel.service.net.module.friend.model.ResGoodProduct;
import com.hotel.service.net.module.friend.model.ResReserveProduct;
import com.hotel.service.net.module.friend.model.ResShareFriend;
import com.hotel.service.net.module.friend.model.ResShareFriendList;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by aoxiang on 2015/11/9.
 */
public interface FriendInterface {
    /**
     * 获取拼好货信息
     * @return
     */
    @POST("/app/terminalapi/call")
    @FormUrlEncoded
    Observable<ResGoodProduct> getGoodProduct(@Field("requestValue") ReqGoodProduct reqGoodProduct);

    /**
     * 预定信息
     * @return
     */
    @POST("/app/terminalapi/call")
    @FormUrlEncoded
    Observable<ResReserveProduct> setReserveProduct(@Field("requestValue") ReqReserveProduct reqReserveProduct);

    /**
     * 获取活动区信息
     * @return
     */
    @POST("/app/terminalapi/call")
    @FormUrlEncoded
    Observable<ResActiveRegion> getActiveRegion(@Field("requestValue") ReqActiveRegion reqActiveRegion);

    /**
     * 获取分享好友汇总信息
     * @return
     */
    @POST("/app/terminalapi/call")
    @FormUrlEncoded
    Observable<ResShareFriend> getShareFriends(@Field("requestValue") ReqShareFriend reqShareFriend);

    /**
     * 获取分享好友列表信息
     * @return
     */
    @POST("/app/terminalapi/call")
    @FormUrlEncoded
    Observable<ResShareFriendList> getShareFriendsList(@Field("requestValue") ReqShareFriendList reqShareFriendList);


}
