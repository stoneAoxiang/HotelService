package com.hotel.service.net.module.integer.model;

import com.hotel.service.net.module.BaseReqModel;

/**
 * Created by hyz on 2015/12/9.
 */
public class ReqScoreGoods extends BaseReqModel {

    public String index;
    public String num;
    public String scoreLevel;

    public ReqScoreGoods(String module, String method, String scoreLevel, String index, String num) {
        this.module = module;
        this.scoreLevel = scoreLevel;
        this.method = method;
        this.index = index;
        this.num = num;
    }

    public static class Builder {
        public String module;
        public String method;        //励生活团商品列表（getNewGroupBuy）
        public String index;
        public String num;
        public String scoreLevel;

        public Builder setModule(String module) {
            this.module = module;
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

        public Builder setScoreLevel(String scoreLevel) {
            this.scoreLevel = scoreLevel;
            return this;
        }

        public ReqScoreGoods build() {
            return new ReqScoreGoods(module, method, scoreLevel, index, num);
        }
    }
}
