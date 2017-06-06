package com.hotel.service.net.module.market.model;

import com.hotel.service.net.module.BaseReqModel;

/**
 * ReqProductList
 * Created by sharyuke on 15-6-5.
 */
public class EReqProductList extends BaseReqModel {
    public String community_id;
    public String first_product_type_id;
    public String second_product_type_id;
    public String orderby;
    public String order;
    public String like;
    public String index;
    public String num;
    public String flag;


    public EReqProductList(String module, String method, String community_id, String orderby, String order, String like, String first_product_type_id, String second_product_type_id, String index, String num, String flag) {
        this.module = module;
        this.method = method;
        this.community_id = community_id;
        this.orderby = orderby;
        this.order = order;
        this.like = like;
        this.first_product_type_id = first_product_type_id;
        this.second_product_type_id = second_product_type_id;
        this.index = index;
        this.num = num;
        this.flag=flag;
    }


    public static class Builder {
        public String module;
        public String method;
        public String community_id;
        public String orderby;
        public String order;
        public String like;
        public String first_product_type_id;
        public String second_product_type_id;
        public String index;
        public String num;
        public String flag;

        public Builder setModule(String module) {
            this.module = module;
            return this;
        }

        public Builder setMethod(String method) {
            this.method = method;
            return this;
        }

        public Builder setCommunity_id(String community_id) {
            this.community_id = community_id;
            return this;
        }

        public Builder setFirst_product_type_id(String first_product_type_id) {
            this.first_product_type_id = first_product_type_id;
            return this;
        }

        public Builder setSecond_product_type_id(String second_product_type_id) {
            this.second_product_type_id = second_product_type_id;
            return this;
        }

        public Builder setOrderby(String orderby) {
            this.orderby = orderby;
            return this;
        }

        public Builder setOrder(String order) {
            this.order = order;
            return this;
        }

        public Builder setIndex(String index) {
            this.index = index;

            return this;
        }

        public Builder setLike(String like) {
            this.like = like;
            return this;
        }

        public Builder setNum(String num) {
            this.num = num;
            return this;
        }public Builder setFlag(String flag) {
            this.flag = flag;
            return this;
        }

        public EReqProductList build() {
            return new EReqProductList(module, method, community_id, orderby, order, like, first_product_type_id, second_product_type_id, index, num,flag);
        }
    }
}
