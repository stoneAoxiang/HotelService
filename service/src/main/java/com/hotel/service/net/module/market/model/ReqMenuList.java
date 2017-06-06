package com.hotel.service.net.module.market.model;

import com.hotel.service.net.module.BaseReqModel;

/**
 * ReqMenuList
 * Created by sharyuke on 15-6-4.
 */
public class ReqMenuList extends BaseReqModel {
    public String community_id;
    public String first_product_type_id;

    public ReqMenuList(String module, String method, String community_id, String first_product_type_id) {
        this.module = module;
        this.method = method;
        this.community_id = community_id;
        this.first_product_type_id = first_product_type_id;
    }

    public static class Builder {
        private String module;
        private String method;
        private String community_id;
        private String first_product_type_id;

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

        public ReqMenuList build() {
            return new ReqMenuList(module, method, community_id, first_product_type_id);
        }
    }
}
