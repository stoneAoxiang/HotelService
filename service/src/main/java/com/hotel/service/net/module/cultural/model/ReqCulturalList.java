package com.hotel.service.net.module.cultural.model;

import com.hotel.service.net.module.BaseReqModel;

/**
 * Created by Administrator on 2016/4/5.
 */
public class ReqCulturalList extends BaseReqModel{

    public String userId;
    public String index;
    public String num;

    public ReqCulturalList (String method,String userId,String index,String num){

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

        public ReqCulturalList build(){

            return new ReqCulturalList(method,userId,index,num);
        }
    }
}
