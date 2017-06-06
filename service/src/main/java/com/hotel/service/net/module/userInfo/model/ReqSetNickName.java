package com.hotel.service.net.module.userInfo.model;

import com.hotel.service.net.module.BaseReqModel;

/**
 * Created by aoxiang on 2015/12/23.
 */
public class ReqSetNickName extends BaseReqModel {
    public String userId;
    public String nickName; //用户昵称

    public ReqSetNickName(String module, String method, String userId, String nickName) {
        this.module = module;
        this.method = method;
        this.userId = userId;
        this.nickName = nickName;

    }


    public static class Builder {
        public String module;
        public String method;
        public String userId;
        public String nickName;

        public Builder setModule(String module) {
            this.module = module;
            return this;
        }

        public Builder setMethod(String method) {
            this.method = method;
            return this;
        }

        public Builder setUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder setNickName(String nickName) {
            this.nickName = nickName;
            return this;
        }

        public ReqSetNickName build() {
            return new ReqSetNickName(module, method, userId, nickName);
        }
    }
}