package com.hotel.service.net.module.positon.model;

import com.hotel.service.net.module.BaseReqModel;

/**
 * Created by aoxiang on 2015/12/9.
 */
public class ReqOfferType extends BaseReqModel {

    public ReqOfferType(String module, String method) {
        this.module = module;
        this.method = method;

    }

    public static class Builder {
        public String module;
        public String method;

        public Builder setModule(String module) {
            this.module = module;
            return this;
        }

        public Builder setMethod(String method) {
            this.method = method;
            return this;
        }

        public ReqOfferType build() {
            return new ReqOfferType(module, method);
        }
    }
}
