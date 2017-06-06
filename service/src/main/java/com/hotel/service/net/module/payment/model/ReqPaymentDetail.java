package com.hotel.service.net.module.payment.model;

import com.hotel.service.net.module.BaseReqModel;

/**
 * Created by aoxiang on 2015/12/9.
 */
public class ReqPaymentDetail extends BaseReqModel {
    public String p_id;
    public String userId;
    public String index;
    public String num;
    public ReqPaymentDetail(String module, String method, String userId, String p_id, String index, String num) {
        this.module = module;
        this.method = method;
        this.userId = userId;
        this.p_id = p_id;
        this.index = index;
        this.num = num;

    }


    public static class Builder {
        public String module;
        public String method;
        public String userId;
        public String p_id;
        public String index;
        public String num;

        public Builder setModule(String module) {
            this.module = module;
            return this;
        }

        public Builder setUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder setP_id(String p_id) {
            this.p_id = p_id;
            return this;
        }

        public Builder setMethod(String method) {
            this.method = method;
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

        public ReqPaymentDetail build() {
            return new ReqPaymentDetail(module, method, userId, p_id , index , num);
        }
    }
}
