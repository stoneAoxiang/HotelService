package com.hotel.service.net.module.newinfo.model;

import com.hotel.service.net.module.BaseReqModel;

/**
 * Created by hyz on 2015/12/7.
 *  客户端请求参数
 */
public class ReqNewInfoList extends BaseReqModel{

    public String userId;
    public String typeId;
    public String index;
    public String num;

    public ReqNewInfoList(String method, String userId, String typeId,String index, String num ) {

        this.method = method;
        this.userId = userId;
        this.typeId = typeId;
        this.index = index;
        this.num = num;
    }

    public static class Builder{

        public String method;
        public String userId;
        public String typeId;
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

        public Builder setTypeId(String typeId) {
            this.typeId = typeId;
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


        public ReqNewInfoList build() {
            return new ReqNewInfoList( method, userId,typeId,index,num);
        }
    }
}
