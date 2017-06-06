package com.hotel.service.net.module.lottery.model;

import com.hotel.service.net.PageModel;
import com.hotel.service.net.module.BaseResModel;

import java.util.List;

/**
 * Created by aoxiang on 2015/12/9.
 */
public class ResLotteryList extends BaseResModel implements PageModel<ResLotteryList.MyPrizeList> {
    public List<MyPrizeList> myPrizeList;
    public String result;
    public String s_total;
    public String s_num;


    @Override
    public List<MyPrizeList> getData() {
        return myPrizeList;
    }

    public class MyPrizeList {
        public String prizeId; //奖品id
        public String prizeName; //奖品名称
        public String prizeType; //奖品类型  1：积分 2：奖品
        public String score; //积分
        public String iconurl; //奖品图片
        public String imgsDesc; //图片介绍
        public String areas; //奖品区域A-H
        public String isExchange; //兑换状态 0未兑换 1已兑换
        public String getTime; //抽奖时间

    }
}
