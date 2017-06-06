package com.hotel.service.net.module.lottery.model;

import com.hotel.service.net.PageModel;
import com.hotel.service.net.module.BaseResModel;

import java.util.List;

/**
 * Created by aoxiang on 2015/12/9.
 */
public class ResLottery  extends BaseResModel implements PageModel<ResLottery.PrizeList> {
    public List<PrizeList> prizeList;
    public String result;
    public String prizeId; //本次默认中奖奖品id
    public String prizeCount; //本次默认抽奖次数
    public String totalIntegral; //个人积分
    public String consumptionScore; //本次抽奖消耗积分


    @Override
    public List<PrizeList> getData() {
        return prizeList;
    }

    public class PrizeList {
        public String id; //奖品id
        public String prizeName; //奖品名称
        public String prizeType; //奖品类型  1：积分 2：奖品
        public String score; //积分
        public String iconurl; //奖品图片
        public String imgsDesc; //图片介绍
        public String areas; //奖品区域A-H

    }
}
