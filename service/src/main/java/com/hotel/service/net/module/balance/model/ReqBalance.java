package com.hotel.service.net.module.balance.model;

import com.hotel.service.net.module.BaseReqModel;

/**
 * Created by aoxiang on 2015/12/11.
 */
public class ReqBalance extends BaseReqModel {
    public String id;

    public ReqBalance(String module, String method, String id) {
        this.module = module;
        this.method = method;
        this.id = id;

    }


    public static class Builder {
        public String module;
        public String method;
        public String id;

        public Builder setModule(String module) {
            this.module = module;
            return this;
        }

        public Builder setMethod(String method) {
            this.method = method;
            return this;
        }


        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public ReqBalance build() {
            return new ReqBalance(module, method, id);
        }
    }
}
