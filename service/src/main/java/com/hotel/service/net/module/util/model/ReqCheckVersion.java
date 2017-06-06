package com.hotel.service.net.module.util.model;


import com.hotel.service.net.module.BaseReqModel;

/**
 * Created by Administrator on 2016/3/23.
 */
public class ReqCheckVersion extends BaseReqModel {
    public String appFlag;

    public ReqCheckVersion(String method, String appFlag) {

        this.method = method;
        this.appFlag = appFlag;
    }

    public ReqCheckVersion() {
    }

    public static class Builder {
        public String method;
        public String appFlag;

        public Builder setAppFlag(String appFlag) {
            this.appFlag = appFlag;
            return this;
        }

        public Builder setMethod(String method) {
            this.method = method;

            return this;
        }


        public ReqCheckVersion build() {
            return new ReqCheckVersion(method, appFlag);
        }
    }

}
