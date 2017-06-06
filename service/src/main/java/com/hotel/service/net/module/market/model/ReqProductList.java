package com.hotel.service.net.module.market.model;

import com.hotel.service.net.module.BaseReqModel;

/**
 * ReqProductList
 * Created by sharyuke on 15-6-5.
 */
public class ReqProductList extends BaseReqModel {
    public String community_id;
    public String first_product_type_id;
    public String second_product_type_id;
    public String orderby;
    public String order;
    public String like;
    public String index;
    public String num;
    public String flag;

    public String interface_type;

    public ReqProductList(String module, String method, String community_id, String orderby, String order,
                          String like, String first_product_type_id, String second_product_type_id,
                          String index, String num, String flag, String interface_type) {
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
        this.flag = flag;
        this.interface_type = interface_type;
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

        public String interface_type;

        public String getModule() {
            return module;
        }

        public String getMethod() {
            return method;
        }

        public String getCommunity_id() {
            return community_id;
        }

        public String getOrderby() {
            return orderby;
        }

        public String getOrder() {
            return order;
        }

        public String getLike() {
            return like;
        }

        public String getFirst_product_type_id() {
            return first_product_type_id;
        }

        public String getSecond_product_type_id() {
            return second_product_type_id;
        }

        public String getIndex() {
            return index;
        }

        public String getNum() {
            return num;
        }

        public String getFlag() {
            return flag;
        }

        public String getInterface_type() {
            return interface_type;
        }

        public Builder setInterface_type(String interface_type) {
            this.interface_type = interface_type;
            return this;
        }

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
        }

        public Builder setFlag(String flag) {
            this.flag = flag;
            return this;
        }

        public ReqProductList build() {
            return new ReqProductList(module, method, community_id, orderby, order, like, first_product_type_id,
                    second_product_type_id, index, num, flag, interface_type);
        }
    }
}
