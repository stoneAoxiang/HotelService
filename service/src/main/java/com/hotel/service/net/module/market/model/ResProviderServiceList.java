package com.hotel.service.net.module.market.model;

import com.hotel.service.net.PageModel;
import com.hotel.service.net.module.BaseResModel;

import java.util.List;

/**
 * ReqProductList
 * Created by aoxiang on 15-6-5.
 */
public class ResProviderServiceList extends BaseResModel implements PageModel<ResProviderServiceList.ProviderServiceList> {
    public List<ProviderServiceList> providerServiceList;
    public String result;   //返回码（200：成功； 300:失败；）
    public String s_total;
    public String s_num;

    @Override
    public List<ProviderServiceList> getData() {
        return providerServiceList;
    }

    public class ProviderServiceList {
        public String productId;   //产品/服务id
        public String productName;   //产品/服务名称
        public String model;   //	型号
        public String price;   //	单价
        public String unit;   //	单位
        public String description;   //	描述
        public String picUrl;   //	产品图片
    }
}
