package com.hotel.service.net.module.house.model;

import com.hotel.service.net.PageModel;
import com.hotel.service.net.module.BaseResModel;

import java.util.List;

/**
 * Created by aoxiang on 2015/12/9.
 */
public class ResRentalList extends BaseResModel implements PageModel<ResRentalList.RentalList> {
    public String result;
    public List<RentalList> viewScore_list;
    public String s_total;
    public String s_num;


    @Override
    public List<RentalList> getData() {
        return viewScore_list;
    }

    public class RentalList {

        public String ShouseRentalId;   //房屋租售信息id
        public String title;   //	标题	string
        public String houseName;   //	门牌号
        public String project;   //	项目名称
        public String rental;   //	租售方式 0出租 1出售
        public String size;   //	面积
        public String rent;   //	租金
        public String payment;   //	付费方式
        public String employeeName;   //	出租发布人
        public String phone;   //	联系电话
        public String floor;   //	楼层/总层数
        public String features;   //	房源特色
        public String contenturl;   //	房源描述
        public String iconurl;   //	图片地址（'只有一张图片'）
        public String auditTime;   //	发布时间

    }
}
