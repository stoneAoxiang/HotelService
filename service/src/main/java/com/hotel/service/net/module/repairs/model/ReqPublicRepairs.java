package com.hotel.service.net.module.repairs.model;

import com.hotel.service.net.module.BaseReqModel;

/**
 * 客户端
 * Created by hyz on 2016/1/25.
 */
public class ReqPublicRepairs extends BaseReqModel{

    public String userId;
    public String progression;
    public String index;
    public String num;

    public ReqPublicRepairs(String method, String userId, String progression, String index, String num){

        this.userId = userId;
        this.method = method;
        this.progression = progression;
        this.index = index;
        this.num = num;
    }
    public static class Builder{

        public String userId;
        public String method;
        public String progression;
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

        public Builder setProgression(String progression) {
            this.progression = progression;
            return this;
        }

        public Builder setIndex(String index) {
            this.index = index;
            return this;
        }

        public Builder setNum(String num) {
            this.num = num;
            return this;
        }
        public ReqPublicRepairs build(){
            return new ReqPublicRepairs(method ,userId,progression,index,num);
        }
    }

}
