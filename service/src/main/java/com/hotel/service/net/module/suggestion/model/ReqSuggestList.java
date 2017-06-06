package com.hotel.service.net.module.suggestion.model;

import com.hotel.service.net.module.BaseReqModel;

/**
 * Created by hyz on 2016/3/11.
 */
public class ReqSuggestList extends BaseReqModel {

    public String userId;
    public String status;
    public String type; //type为"0"时 消息来自业主APP，为"1"时，消息来自员工APP
    public String index;
    public String num;

    public ReqSuggestList(String method, String userId, String status, String type, String index, String num) {

        this.method = method;
        this.userId = userId;
        this.status = status;
        this.type = type;
        this.index = index;
        this.num = num;
    }

    public static class Builder {

        public String method;
        public String userId;
        public String status;
        public String type;
        public String index;
        public String num;

        public Builder setMethod(String method) {
            this.method = method;
            return this;
        }

        public Builder setUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder setStatus(String status) {
            this.status = status;
            return this;
        }

        public Builder setIndex(String index) {
            this.index = index;
            return this;
        }

        public Builder setType(String type) {
            this.type = type;
            return this;
        }

        public Builder setNum(String num) {
            this.num = num;
            return this;
        }

        public ReqSuggestList build() {

            return new ReqSuggestList(method, userId, status, type, index, num);
        }
    }
}
