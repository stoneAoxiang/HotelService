package com.hotel.service.net.module.friend.model;

import com.hotel.service.net.Page2Model;
import com.hotel.service.net.module.BaseResModel;

import java.util.List;

/**
 * Created by aoxiang on 2015/11/9.
 */

public class ResGoodProduct extends BaseResModel implements Page2Model<ResGoodProduct.ShippingMethod, ResGoodProduct.ShippingTime> {

    public List<ShippingMethod> shipping_method;
    public List<ShippingTime> shipping_time;

    public String id;
    public String name;
    public String userOrderEnable; //用户预定许可(1:许可；0：不许可）
    public String reserveNum; //预定人数
    public String price; //价格
    public String priceDescription; //价格描述
    public String busAddress; //商家详细地址
    public String description; //描述
    public String iconurl; //商品图片
    public String carouselPicture; //轮播图片
    public String details; //商品详情html路径
    public String startTime; //开始时间
    public String endTime; //结束时间
    public String nextFrameTime; //下架时间
    public String salesVolume; //销量
    public String stock; //库存
    public String gradeOne; // 等级1人数
    public String gradeOnePrice; // 等级1价格
    public String gradeTwo; // 等级2人数
    public String gradeTwoPrice; // 等级2价格
    public String gradeThree; // 等级3人数
    public String gradeThreePrice; // 等级3价格
    public String gradeFour; // 等级4人数
    public String gradeFourPrice; // 等级4价格
    public String gradeFive; // 等级5人数
    public String gradeFivePrice; // 等级5价格
    public String gradeSix; // 等级6人数
    public String gradeSixPrice; // 等级6价格

    public String result;// 返回码（200：成功； 300:失败；）

    @Override
    public List<ShippingMethod> getData() {
        return shipping_method;
    }

    public class ShippingMethod {
        public String name;
    }

    @Override
    public List<ShippingTime> getData2() {
        return shipping_time;
    }

    public class ShippingTime {
        public String name;
    }


}