package com.hotel.service.net.module.friend.model;

import com.hotel.service.net.module.BaseResModel;

/**
 * Created by aoxiang on 2015/12/24.
 */
public class ResShareFriend extends BaseResModel {

    public String invitedFriendNums; //成功邀请人数
    public String invitedRegistered; //	邀请注册得的分数
    public String invitedIntegralProportion; //	通过好友获得的积分比例
    public String referralCode; //	邀请码
    public String result; //返回码（200：成功；300:失败；）

}
