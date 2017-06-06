package com.hotel.service.net.module.signin.model;

import com.hotel.service.net.module.BaseReqModel;

/**
 * Created by aoxiang on 2015/12/15.
 */
public class ReqSigninList extends BaseReqModel {
    public String userId;

    public ReqSigninList(String module, String method, String userId) {
        this.module = module;
        this.method = method;
        this.userId = userId;

    }


    public static class Builder {
        public String module;
        public String method;
        public String userId;

        public Builder setModule(String module) {
            this.module = module;
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

        public ReqSigninList build() {
            return new ReqSigninList(module, method, userId);
        }
    }
}