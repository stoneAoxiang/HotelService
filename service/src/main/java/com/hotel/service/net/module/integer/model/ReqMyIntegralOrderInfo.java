package com.hotel.service.net.module.integer.model;

import com.hotel.service.net.module.BaseReqModel;

/**
 * Created by hyz on 2015/12/22.
 */
public class ReqMyIntegralOrderInfo extends BaseReqModel {



    public String userid;
    public String index;
    public String order_status;
    public String num;

    public ReqMyIntegralOrderInfo(String module,String method, String userid, String index,String order_status, String num ) {

        this.module = module;
        this.method = method;
        this.userid = userid;
        this.index = index;
        this.order_status = order_status;
        this.num = num;
    }

    public static class Builder{

        public String module;
        public String method;
        public String userid;
        public String index;
        public String order_status;
        public String num;

        public Builder setModule(String module) {
            this.module = module;
            return this;
        }
        public Builder setMethod(String method) {
            this.method = method;
            return this;
        }

        public Builder setUserId(String userid) {
            this.userid = userid;
            return this;
        }


        public Builder setIndex(String index) {
            this.index = index;
            return this;
        }
        public Builder setOrder_Status(String order_status){
            this.order_status = order_status;
            return  this;
        }
        public Builder setNum(String num) {
            this.num = num;
            return this;
        }


        public ReqMyIntegralOrderInfo build() {
            return new ReqMyIntegralOrderInfo( module,method, userid,index,order_status,num);
        }
    }

}
