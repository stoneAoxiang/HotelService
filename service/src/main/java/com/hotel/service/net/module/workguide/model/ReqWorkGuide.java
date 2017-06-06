package com.hotel.service.net.module.workguide.model;

import com.hotel.service.net.module.BaseReqModel;

/**
 * Created by hyz on 2015/12/7.
 *  客户端请求参数
 */
public class ReqWorkGuide extends BaseReqModel{


    public String userId;
    public String index;
    public String num;

    public ReqWorkGuide(String method,String userId,String index,String num) {
        this.method = method;
        this.userId = userId;
        this.index = index;
        this.num = num;
    }

    public static class Builder{

        public String method;
        public String userId;
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

        public Builder setIndex(String index) {
            this.index = index;
            return this;
        }

        public Builder setNum(String num) {
            this.num = num;
            return this;
        }

        public ReqWorkGuide build() {
            return new ReqWorkGuide( method,userId,index,num);
        }
    }
}
