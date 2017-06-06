package com.hotel.service.net.module.menu.model;

import com.hotel.service.net.module.BaseReqModel;

/**
 * Created by Administrator on 2016/2/19.
 */
public class ReqServiceAddress extends BaseReqModel {
    private String id;

    public ReqServiceAddress(String module, String method, String name) {
        this.module = module;
        this.method = method;
        this.id = name;
    }

    public static class Builder {
        private String module;
        private String method;
        private String id;

        public Builder setModule(String module) {
            this.module = module;
            return this;
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setMethod(String method) {
            this.method = method;
            return this;
        }

        public ReqServiceAddress build() {
            return new ReqServiceAddress(method, module, id);
        }
    }

}
