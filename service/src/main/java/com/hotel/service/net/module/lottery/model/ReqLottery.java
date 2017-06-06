package com.hotel.service.net.module.lottery.model;

import com.hotel.service.net.module.BaseReqModel;

/**
 * Created by aoxiang on 2015/12/9.
 */
public class ReqLottery extends BaseReqModel {
    public String userId;
    public boolean isLogin;

    public ReqLottery(String module, String method, String userId, boolean isLogin) {
        this.module = module;
        this.method = method;
        this.userId = userId;
        this.isLogin = isLogin;

    }


    public static class Builder {
        public String module;
        public String method;
        public String userId;
        public boolean isLogin;

        public Builder setModule(String module) {
            this.module = module;
            return this;
        }

        public Builder setIsLogin(boolean isLogin) {
            this.isLogin = isLogin;
            return this;
        }

        public Builder setUserId(String userId) {
            this.userId = userId;
            return this;

        }

        public Builder setMethod(String method) {
            this.method = method;
            return this;
        }

        public ReqLottery build() {
            return new ReqLottery(module, method, userId, isLogin);
        }
    }
}
