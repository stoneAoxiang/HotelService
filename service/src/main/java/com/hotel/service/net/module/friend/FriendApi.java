package com.hotel.service.net.module.friend;

import com.hotel.service.net.BaseApi;
import com.hotel.service.net.ViewProxyInterface;
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
import com.hotel.service.net.module.market.model.ReqMenuList;

import javax.inject.Inject;

import retrofit.RestAdapter;
import rx.Observable;

/**
 * Created by aoxiang on 2015/11/9.
 */
public class FriendApi extends BaseApi {
    static FriendInterface server;

    @Inject
    public FriendApi(RestAdapter restAdapter) {
        server = restAdapter.create(FriendInterface.class);
    }

    public Observable<ResGoodProduct> getGoodProduct(ReqGoodProduct reqGoodProduct) {
        return getGoodProduct(reqGoodProduct, null);
    }

    public Observable<ResGoodProduct> getGoodProduct(ReqGoodProduct reqGoodProduct, ViewProxyInterface viewProxy) {
        onLoading(viewProxy);
        reqGoodProduct.module = ReqMenuList.MODULE_BUY;
        reqGoodProduct.method = ReqMenuList.METHOD_GET_GOOD_PRODUCT_LIST;
        return getRes(server.getGoodProduct(reqGoodProduct), viewProxy);
    }

    public Observable<ResReserveProduct> setReserveProduct(ReqReserveProduct reqReserveProduct) {
        return setReserveProduct(reqReserveProduct, null);
    }

    public Observable<ResReserveProduct> setReserveProduct(ReqReserveProduct reqReserveProduct, ViewProxyInterface viewProxy) {
        onLoading(viewProxy);
        reqReserveProduct.module = ReqMenuList.MODULE_BUY;
        reqReserveProduct.method = ReqMenuList.METHOD_SET_ORDER_SPELL_GOODS;
        return getRes(server.setReserveProduct(reqReserveProduct), viewProxy);
    }

    public Observable<ResActiveRegion> getActiveRegion(ReqActiveRegion reqActiveRegion) {
        return getActiveRegion(reqActiveRegion, null);
    }

    public Observable<ResActiveRegion> getActiveRegion(ReqActiveRegion reqActiveRegion, ViewProxyInterface viewProxy) {
        onLoading(viewProxy);
        reqActiveRegion.module = ReqMenuList.MODULE_HOME;
        reqActiveRegion.method = ReqMenuList.METHOD_GET_ACTIVITYS;
        return getRes(server.getActiveRegion(reqActiveRegion), viewProxy);
    }

    public Observable<ResShareFriend> getShareFriends(ReqShareFriend reqShareFriend) {
        return getShareFriends(reqShareFriend, null);
    }

    public Observable<ResShareFriend> getShareFriends(ReqShareFriend reqShareFriend, ViewProxyInterface viewProxy) {
        onLoading(viewProxy);
        reqShareFriend.module = ReqMenuList.MODULE_INFORMATION;
        reqShareFriend.method = ReqMenuList.METHOD_GET_SHARE_FRIENDS;
        return getRes(server.getShareFriends(reqShareFriend), viewProxy);
    }

    public Observable<ResShareFriendList> getShareFriendsList(ReqShareFriendList reqShareFriendList) {
        return getShareFriendsList(reqShareFriendList, null);
    }

    public Observable<ResShareFriendList> getShareFriendsList(ReqShareFriendList reqShareFriendList, ViewProxyInterface viewProxy) {
        onLoading(viewProxy);
        reqShareFriendList.module = ReqMenuList.MODULE_INFORMATION;
        reqShareFriendList.method = ReqMenuList.METHOD_GET_SHARE_FRIENDS_LIST;
        return getRes(server.getShareFriendsList(reqShareFriendList), viewProxy);
    }


}
