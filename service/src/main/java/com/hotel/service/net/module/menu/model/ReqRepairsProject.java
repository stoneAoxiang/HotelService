package com.hotel.service.net.module.menu.model;

import com.hotel.service.net.module.BaseReqModel;

/**
 * Created by Administrator on 2016/2/19.
 */
public class ReqRepairsProject extends BaseReqModel {

    public ReqRepairsProject(String module, String method) {
        this.module = module;
        this.method = method;
    }
    public static class Builder {
        private String module;
        private String method;

        public Builder setModule(String module) {
            this.module = module;
            return this;
        }

        public Builder setMethod(String method) {
            this.method = method;
            return this;
        }

        public ReqRepairsProject build() {
            return new ReqRepairsProject(method,module);
        }
    }

}
