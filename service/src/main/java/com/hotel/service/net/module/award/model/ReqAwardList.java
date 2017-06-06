package com.hotel.service.net.module.award.model;

import com.hotel.service.net.module.BaseReqModel;

/**
 * Created by aoxiang on 2015/12/2.
 */
public class ReqAwardList  extends BaseReqModel {
    public String userId;

    public ReqAwardList(String module, String method, String userId) {
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

        public Builder setMethod(String method) {
            this.method = method;
            return this;
        }

        public Builder setUserid(String userId) {
            this.userId = userId;
            return this;
        }

        public ReqAwardList build() {
            return new ReqAwardList(module, method, userId);
        }
    }
}
