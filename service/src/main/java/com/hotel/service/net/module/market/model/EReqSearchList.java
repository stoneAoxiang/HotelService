package com.hotel.service.net.module.market.model;

import com.hotel.service.net.module.BaseReqModel;

/**
 * ReqProductList
 * Created by sharyuke on 15-6-5.
 */
public class EReqSearchList extends BaseReqModel {
    public String searchName;
    public String index;
    public String num;



    public EReqSearchList(String module, String method, String name, String index, String num) {
        this.module = module;
        this.method = method;
        this.searchName = name;
        this.index = index;
        this.num = num;

    }


    public static class Builder {
        public String module;
        public String method;
        public String index;
        public String num;
        public String searchName;


        public Builder setModule(String module) {
            this.module = module;
            return this;
        }public Builder setName(String name) {
            this.searchName = name;
            return this;
        }

        public Builder setMethod(String method) {
            this.method = method;
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

        public EReqSearchList build() {
            return new EReqSearchList(module, method,searchName,index, num);
        }
    }
}
