package com.hotel.service.net.module.friend.model;

import com.hotel.service.net.PageModel;
import com.hotel.service.net.module.BaseResModel;

import java.util.List;

/**
 * Created by aoxiang on 2015/12/24.
 */
public class ResShareFriendList extends BaseResModel  implements PageModel<ResShareFriendList.InvitedFrendsList> {
    public List<InvitedFrendsList> invitedFrendsList;

    public String s_num;
    public String s_total;

    @Override
    public List<InvitedFrendsList> getData() {
        return invitedFrendsList;
    }

    public class InvitedFrendsList {

        public String userName; //邀请好友的登录名
        public String createTime; //邀请好友，好友成功注册的时间
        public String invitedRegistered; //邀请好友，获得的分数
        public String referralCode; //邀请码

    }
}
