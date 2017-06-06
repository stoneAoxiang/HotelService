package com.hotel.service.net.module.recruit.model;

import com.hotel.service.net.module.BaseReqModel;

/**
 * Created by hyz on 2015/12/26.
 */
public class ReqNewRecruit extends BaseReqModel {

    public String userId;

    public ReqNewRecruit(String method ,String userId){

        this.method = method;
        this.userId = userId;
    }
    public static class Builder{

        private String method;
        private String userId;

        public Builder setMethod(String method) {
            this.method = method;
            return this;
        }

        public Builder setUserId(String userId) {
            this.userId = userId;
            return this;
        }
        public ReqNewRecruit build(){
            return new ReqNewRecruit(method , userId);
        }
    }
}
