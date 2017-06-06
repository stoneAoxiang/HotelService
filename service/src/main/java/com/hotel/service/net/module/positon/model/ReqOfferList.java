package com.hotel.service.net.module.positon.model;

import com.hotel.service.net.module.BaseReqModel;

/**
 * Created by aoxiang on 2015/12/9.
 */
public class ReqOfferList extends BaseReqModel {
    public String userId;
    public String typeId;   //类型id
    public String position;   //职位名称（查询时用）
    public String index;
    public String num;

    public ReqOfferList(String module, String method, String userId, String typeId, String position, String index, String num) {
        this.module = module;
        this.method = method;
        this.userId = userId;
        this.typeId = typeId;
        this.position = position;
        this.index = index;
        this.num = num;

    }

    public static class Builder {
        public String module;
        public String method;
        public String userId;
        public String typeId;   //类型id
        public String position;
        public String index;
        public String num;

        public Builder setModule(String module) {
            this.module = module;
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

        public Builder setPosition(String position) {
            this.position = position;
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

        public Builder setMethod(String method) {
            this.method = method;
            return this;
        }

        public ReqOfferList build() {
            return new ReqOfferList(module, method, userId, typeId, position, index, num);
        }
    }
}
