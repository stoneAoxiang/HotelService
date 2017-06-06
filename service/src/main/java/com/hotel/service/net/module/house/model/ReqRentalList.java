package com.hotel.service.net.module.house.model;

import com.hotel.service.net.module.BaseReqModel;

/**
 * Created by aoxiang on 2015/12/9.
 */
public class ReqRentalList extends BaseReqModel {
    public String type; //默认传“”显示全部，“0“时出租 ”1“出售
    public String index;
    public String num;

    public ReqRentalList(String module, String method, String type, String index, String num) {
        this.module = module;
        this.method = method;
        this.type = type;
        this.index = index;
        this.num = num;

    }


    public static class Builder {
        public String module;
        public String method;
        public String type;
        public String index;
        public String num;

        public Builder setModule(String module) {
            this.module = module;
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

        public Builder setMethod(String method) {
            this.method = method;
            return this;
        }

        public ReqRentalList build() {
            return new ReqRentalList(module, method, type, index, num);
        }
    }
}
