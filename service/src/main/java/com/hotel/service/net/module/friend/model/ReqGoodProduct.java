package com.hotel.service.net.module.friend.model;

import com.hotel.service.net.module.BaseReqModel;

/**
 * Created by aoxiang on 2015/11/9.
 */
public class ReqGoodProduct extends BaseReqModel {
    public String userid;

    public ReqGoodProduct(String module, String method, String userid) {
        this.module = module;
        this.method = method;
        this.userid = userid;

    }


    public static class Builder {
        public String module;
        public String method;
        public String userid;

        public Builder setModule(String module) {
            this.module = module;
            return this;
        }

        public Builder setMethod(String method) {
            this.method = method;
            return this;
        }

        public Builder setUserid(String userid) {
            this.userid = userid;
            return this;
        }

        public ReqGoodProduct build() {
            return new ReqGoodProduct(module, method, userid);
        }
    }
}
