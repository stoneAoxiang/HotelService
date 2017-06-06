package com.hotel.service.net.module.award.model;

import com.hotel.service.net.PageModel;
import com.hotel.service.net.module.BaseResModel;

import java.util.List;

/**
 * Created by aoxiang on 2015/12/2.
 */
public class ResAwardList extends BaseResModel implements PageModel<ResAwardList.RewardList> {

    public String method;
    public String  result; //	返回码（200：成功；300:失败；）
    public List<RewardList> rewardList; //	奖励类型集合数组	数组

    @Override
    public List<RewardList> getData() {
        return rewardList;
    }

    public class RewardList {
        public String id; //	流水id( 6:首次注册安装；7：上午探班；8：下午探班；9：补填邀请码
        public String code; //	奖励代码类型 3表示奖励
        public String title; //	标题
        public String details; //	奖励描述
        public String awardIntegral; //	奖励积分
        public String acceptStatus; //	领取状态1：已领取0：未领取(id为9时0表示未填邀请码)
    }
}
