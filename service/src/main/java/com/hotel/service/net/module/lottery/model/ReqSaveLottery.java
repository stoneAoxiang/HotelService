package com.hotel.service.net.module.lottery.model;

import com.hotel.service.net.module.BaseReqModel;

/**
 * Created by aoxiang on 2015/12/9.
 */
public class ReqSaveLottery extends BaseReqModel {
    public String userId;
    public String prizeId;
    public String prizeCount;

    public ReqSaveLottery(String module, String method, String userId, String prizeId, String prizeCount) {
        this.module = module;
        this.method = method;
        this.userId = userId;
        this.prizeId = prizeId;
        this.prizeCount = prizeCount;

    }


    public static class Builder {
        public String module;
        public String method;
        public String userId;
        public String prizeId;
        public String prizeCount;

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

        public Builder setPrizeId(String prizeId) {
            this.prizeId = prizeId;
            return this;
        }

        public Builder setPrizeCount(String prizeCount) {
            this.prizeCount = prizeCount;
            return this;
        }

        public ReqSaveLottery build() {
            return new ReqSaveLottery(module, method, userId, prizeId, prizeCount);
        }
    }
}
