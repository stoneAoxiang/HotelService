package com.hotel.service.net.module.market.model;

import com.hotel.service.net.module.BaseReqModel;

/**
 * ReqProductList
 * Created by sharyuke on 15-6-5.
 */
public class ReqGetAdvertise extends BaseReqModel {
    public String community_id;
    public String advertise_position_id;

    public ReqGetAdvertise(String module, String method, String community_id, String advertise_position_id) {
        this.module = module;
        this.method = method;
        this.community_id = community_id;
        this.advertise_position_id = advertise_position_id;
    }

    public static class Builder {
        public String module;
        public String method;
        public String community_id;
        public String advertise_position_id;

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

        public Builder setAdvertise_position_id(String advertise_position_id) {
            this.advertise_position_id = advertise_position_id;
            return this;
        }

        public ReqGetAdvertise build() {
            return new ReqGetAdvertise(module, method, community_id, advertise_position_id);
        }
    }
}
