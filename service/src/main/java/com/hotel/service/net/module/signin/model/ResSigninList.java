package com.hotel.service.net.module.signin.model;

import com.hotel.service.net.PageModel;
import com.hotel.service.net.module.BaseResModel;

import java.util.List;

/**
 * Created by aoxiang on 2015/12/15.
 */
public class ResSigninList extends BaseResModel implements PageModel<ResSigninList.SignInList> {
    public List<SignInList> mySignInList;

    @Override
    public List<SignInList> getData() {
        return mySignInList;
    }

    public class SignInList {


        public String SignIn_num; //签到次数
        public String SignIn_iR; //每天签到可获得积分
        public String SignIn_cIR; //连续11天签到可获得奖励积分
        public String SignIn_status; //	签到状态（0不可签到，1可以）



    }
}