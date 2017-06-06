package com.hotel.service.net.module.market.model;

import com.hotel.service.net.module.BaseReqModel;

/**
 * ReqProductList
 * Created by aoxiang on 15-6-5.
 */
public class ReqProviderServiceDetail extends BaseReqModel {
    public String serviceId;     //	产品ID
    public String userId;     // 	用户ID


    public ReqProviderServiceDetail(String module, String method, String serviceId, String userId) {
        this.module = module;
        this.method = method;
        this.serviceId = serviceId;
        this.userId = userId;

    }


    public static class Builder {
        public String module;
        public String method;
        public String serviceId;     //	产品ID
        public String userId;     // 	用户ID

        public Builder setModule(String module) {
            this.module = module;
            return this;
        }

        public Builder setMethod(String method) {
            this.method = method;
            return this;
        }

        public Builder setServiceId(String serviceId) {
            this.serviceId = serviceId;
            return this;
        }

        public Builder setUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public ReqProviderServiceDetail build() {
            return new ReqProviderServiceDetail(module, method, serviceId, userId);
        }
    }
}
