package com.hotel.service.net.module.integer.model;

import com.hotel.service.net.module.BaseReqModel;

/**
 * Created by hyz on 2015/12/14.
 */
public class ReqScoreLevel extends BaseReqModel {


    public ReqScoreLevel(String method) {

        this.method = method;

    }

    public static class Builder {

        public String method;     //查看积分（getScoreLevel）


        public Builder setMethod(String method) {
            this.method = method;
            return this;
        }

        public ReqScoreLevel build() {
            return new ReqScoreLevel(method);
        }
    }
}
