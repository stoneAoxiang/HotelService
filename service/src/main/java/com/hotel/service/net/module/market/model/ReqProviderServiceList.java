package com.hotel.service.net.module.market.model;

import com.hotel.service.net.module.BaseReqModel;

/**
 * ReqProductList
 * Created by aoxiang on 15-6-5.
 */
public class ReqProviderServiceList extends BaseReqModel {
    public String name;     //品名(为空则为全部)
    public String categoryParentId;  //商品/服务类别父级ID(为空则为全部)
    public String categoryName;     //商品/服务类别子级名称(为空则为全部)
    public String kind;     //0: 服务; 1:商品(为空则为全部)
    public String area;     //服务区域ID (为空则为全部)
    public String index;
    public String num;

    public ReqProviderServiceList(String module, String method, String name, String categoryParentId,
                                  String categoryName, String kind,
                                  String area, String index, String num) {
        this.module = module;
        this.method = method;
        this.name = name;
        this.categoryParentId = categoryParentId;
        this.categoryName = categoryName;
        this.kind = kind;
        this.area = area;
        this.index = index;
        this.num = num;

    }


    public static class Builder {
        public String module;
        public String method;
        public String name;     //品名(为空则为全部)
        public String categoryParentId;  //商品/服务类别父级ID(为空则为全部)
        public String categoryName;     //商品/服务类别子级名称(为空则为全部)
        public String kind;     //0: 服务; 1:商品(为空则为全部)
        public String area;
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

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setCategoryParentId(String categoryParentId) {
            this.categoryParentId = categoryParentId;
            return this;
        }

        public Builder setCategoryName(String categoryName) {
            this.categoryName = categoryName;
            return this;
        }

        public Builder setArea(String area) {
            this.area = area;
            return this;
        }

        public Builder setKind(String kind) {
            this.kind = kind;
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

        public ReqProviderServiceList build() {
            return new ReqProviderServiceList(module, method, name, categoryParentId,
                    categoryName, kind, area, index, num);
        }
    }
}
