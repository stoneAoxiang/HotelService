package com.hotel.service.net.module.friend.model;

import com.hotel.service.net.module.BaseReqModel;

/**
 * Created by aoxiang on 2015/11/9.
 */
public class ReqReserveProduct extends BaseReqModel {
    public String userid;
    public String goodsid;


    public ReqReserveProduct(String module, String method, String userid, String goodsid) {
        this.module = module;
        this.method = method;
        this.userid = userid;
        this.goodsid = goodsid;

    }


    public static class Builder {
        public String module;
        public String method;
        public String userid;
        public String goodsid;

        public Builder setModule(String module) {
            this.module = module;
            return this;
        }

        public Builder setGoodsid(String goodsid) {
            this.goodsid = goodsid;
            return this;
        }

        public Builder setMethod(String method) {
            this.method = method;
            return this;
        }

        public Builder setUserid(String userid) {
            this.userid = userid;
            return this;
        }

        public ReqReserveProduct build() {
            return new ReqReserveProduct(module, method, userid, goodsid);
        }
    }
}
