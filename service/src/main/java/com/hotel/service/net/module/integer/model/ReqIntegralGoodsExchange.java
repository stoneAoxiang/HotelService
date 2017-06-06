package com.hotel.service.net.module.integer.model;

import com.hotel.service.net.module.BaseReqModel;

/**
 * Created by hyz on 2015/12/14.
 */
public class ReqIntegralGoodsExchange extends BaseReqModel {

    public String userid;
    public String goodid;

    public ReqIntegralGoodsExchange(String userid,String goodid,String method){
        this.method = method;
        this.userid = userid;
        this.goodid = goodid;
    }

    public static class Builder{

        public String method;
        public String userId;
        public String goodId;

        public Builder setMethod(String method){
           this.method = method;
            return this;
        }
        public  Builder setUserId(String userId){
            this.userId = userId;
            return this;
        }
        public Builder setGoodsId(String goodsId){
            this.goodId = goodsId;
            return this;
        }
        public ReqIntegralGoodsExchange Builder(){

            return new ReqIntegralGoodsExchange(goodId,method,userId);
        }
    }
}
