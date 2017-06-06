package com.hotel.service.net.module.payment.model;

import com.hotel.service.net.module.BaseReqModel;

/**
 * Created by aoxiang on 2016/2/17.
 */
public class ReqPay extends BaseReqModel {
    public String userId;
    public String orderId;  //	订单号流水id


    public ReqPay(String module, String method, String userId, String orderId) {
        this.module = module;
        this.method = method;
        this.userId = userId;
        this.orderId = orderId;
    }

    public ReqPay(String userId) {
        this.userId = userId;
    }

    public static class Builder {
        private String module;
        private String method;
        private String userId;
        private String orderId;

        public Builder setModule(String module) {
            this.module = module;
            return this;
        }

        public Builder setMethod(String method) {
            this.method = method;
            return this;
        }

        public Builder setUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder setOrderId(String orderId) {
            this.orderId = orderId;
            return this;
        }

        public ReqPay build() {
            return new ReqPay(module, method, userId, orderId);
        }

    }
}
