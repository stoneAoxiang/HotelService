package com.hotel.service.net.module.speech.model;

import com.hotel.service.net.module.BaseReqModel;

/**
 * Created by hyz on 2015/12/7.
 *  客户端请求参数
 */
public class ReqSpeechList extends BaseReqModel{

    public String userId;

    public ReqSpeechList(String method, String userId) {

        this.method = method;
        this.userId = userId;
    }

    public static class Builder{

        public String method;
        public String userId;

        public Builder setMethod(String method) {
            this.method = method;
            return this;
        }

        public Builder setUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public ReqSpeechList build() {
            return new ReqSpeechList( method, userId);
        }
    }
}
