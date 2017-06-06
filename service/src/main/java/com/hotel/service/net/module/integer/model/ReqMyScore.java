package com.hotel.service.net.module.integer.model;

import com.hotel.service.net.module.BaseReqModel;

/**
 * Created by hyz on 2015/12/14.
 */
public class ReqMyScore extends BaseReqModel {

    public String userid;

    public ReqMyScore(String method,String userid){
        this.method = method;
        this.userid = userid;
    }

    public static class Builder{
        public String userid;
        public String method;
        
        public Builder setUserId(String userid){
            this.userid = userid;
            return this;
        }
        public Builder setMethod(String method){

            this.method = method;
            return this;
        }
        public ReqMyScore build(){
            return new ReqMyScore(method,userid);
        }
    }
}
