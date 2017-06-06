package com.hotel.service.net.module.newinfo.model;

import com.hotel.service.net.module.BaseReqModel;

/**
 * Created by hyz on 2015/12/3.
 * 客户端请求参数
 */
public class ReqNewInfo extends BaseReqModel {

    public ReqNewInfo( String method) {
        this.method = method;

    }

    public static class Builder{
        public String method;

        public Builder setMethod(String method) {
            this.method = method;
            return this;
        }


        public ReqNewInfo build() {
            return new ReqNewInfo( method);
        }
    }
}
