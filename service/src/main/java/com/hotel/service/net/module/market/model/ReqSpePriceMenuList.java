package com.hotel.service.net.module.market.model;

import com.hotel.service.net.module.BaseReqModel;

/**
 * ReqSpePriceMenuList
 * Created by aoxiang on 2015/6/16.
 */
public class ReqSpePriceMenuList extends BaseReqModel {
    public String community_id;
    public String first_merchant_type_id;

    public ReqSpePriceMenuList(String module, String method, String community_id, String first_merchant_type_id) {
        this.module = module;
        this.method = method;
        this.community_id = community_id;
        this.first_merchant_type_id = first_merchant_type_id;
    }

    public static class Builder {
        private String module;
        private String method;
        private String community_id;
        private String first_merchant_type_id;

        public Builder setModule(String module) {
            this.module = module;
            return this;
        }

        public Builder setMethod(String method) {
            this.method = method;
            return this;
        }

        public Builder setFirst_merchant_type_id(String first_merchant_type_id) {
            this.first_merchant_type_id = first_merchant_type_id;
            return this;
        }

        public Builder setCommunity_id(String community_id) {
            this.community_id = community_id;
            return this;
        }

        public ReqSpePriceMenuList build() {
            return new ReqSpePriceMenuList(module, method, community_id, first_merchant_type_id);
        }
    }
}
