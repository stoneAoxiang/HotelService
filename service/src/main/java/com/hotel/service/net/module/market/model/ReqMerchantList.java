package com.hotel.service.net.module.market.model;

import com.hotel.service.net.module.BaseReqModel;

/**
 * ReqProductList
 * Created by sharyuke on 15-6-5.
 */
public class ReqMerchantList extends BaseReqModel {
    public String community_id;
    public String first_merchant_type_id;
    public String second_merchant_type_id;
    public String like;
    public String index;
    public String num;

    public ReqMerchantList(String module, String method, String community_id, String like, String first_merchant_type_id, String second_merchant_type_id, String index, String num) {
        this.module = module;
        this.method = method;
        this.community_id = community_id;
        this.like = like;
        this.first_merchant_type_id = first_merchant_type_id;
        this.second_merchant_type_id = second_merchant_type_id;
        this.index = index;
        this.num = num;
    }


    public static class Builder {
        public String module;
        public String method;
        public String community_id;
        public String like;
        public String first_merchant_type_id;
        public String second_merchant_type_id;
        public String index;
        public String num;

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

        public Builder setFirst_merchant_type_id(String first_merchant_type_id) {
            this.first_merchant_type_id = first_merchant_type_id;
            return this;
        }

        public Builder setSecond_merchant_type_id(String second_merchant_type_id) {
            this.second_merchant_type_id = second_merchant_type_id;
            return this;
        }

        public Builder setLike(String like) {
            this.like = like;
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

        public ReqMerchantList build() {
            return new ReqMerchantList(module, method, community_id, like, first_merchant_type_id, second_merchant_type_id, index, num);
        }
    }
}
