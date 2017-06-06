package com.hotel.service.net.module.friend.model;

import com.hotel.service.net.module.BaseReqModel;

/**
 * Created by aoxiang on 2015/12/7.
 */
public class ReqActiveRegion  extends BaseReqModel {
    public String index;
    public String num;

    public ReqActiveRegion(String module, String method, String index, String num) {
        this.module = module;
        this.method = method;
        this.index = index;
        this.num = num;

    }


    public static class Builder {
        public String module;
        public String method;
        public String index;
        public String num;

        public Builder setModule(String module) {
            this.module = module;
            return this;
        }

        public Builder setIndex(String index) {
            this.index = index;
            return this;
        }

        public Builder setNum(String num) {
            this.num = num;
            return this;
        }

        public Builder setMethod(String method) {
            this.method = method;
            return this;
        }

        public ReqActiveRegion build() {
            return new ReqActiveRegion(module, method, index, num);
        }
    }
}