package com.hotel.service.net.module.market.model;

import com.hotel.service.net.PageModel;
import com.hotel.service.net.module.BaseResModel;

import java.util.List;

/**
 * ReqProductList
 * Created by aoxiang on 15-6-5.
 */
public class ResProviderServiceDetail extends BaseResModel implements PageModel<ResProviderServiceDetail.ShippingMethod> {
    public String result;   //返回码（200：成功； 300:失败；）

    public String imgUrl;   //产品图片
    public String servicePrice;   //服务价格
    public String discountPrice;   //折扣服务价格
    public String reservationPrice;   //预约服务价格
    public String buyCount;   //购买人数
    public String grade;   //评分
    public String address;   //商家地址
    public String tel;   //商家电话
    public String descriptUrl;   //服务详情URL
    public String isFavorite;   //是否收藏 1是0，否
    public String favoriteId;  //收藏id号

    public List<ShippingMethod> shippingMethod; //送货方式

    @Override
    public List<ShippingMethod> getData() {
        return shippingMethod;
    }

    public class ShippingMethod {
        public String name;
    }

}
