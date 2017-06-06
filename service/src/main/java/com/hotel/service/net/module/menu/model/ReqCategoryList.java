package com.hotel.service.net.module.menu.model;

import com.hotel.service.net.module.BaseReqModel;

/**
 * Created by Administrator on 2016/2/19.
 */
public class ReqCategoryList extends BaseReqModel {
    public String name;
    public String parent;

    public ReqCategoryList(String module, String method, String name, String parent) {
        this.module = module;
        this.method = method;
        this.name = name;
        this.parent = parent;

    }

    public static class Builder {
        private String module;
        private String method;
        private String name;
        private String parent;

        public Builder setModule(String module) {
            this.module = module;
            return this;
        }

        public Builder setMethod(String method) {
            this.method = method;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setParent(String parent) {
            this.parent = parent;
            return this;
        }

        public ReqCategoryList build() {
            return new ReqCategoryList(method, module, name, parent);
        }
    }

}
