package com.hotel.service.net.module;

import com.google.gson.Gson;

/**
 * 基类模型
 * Created by sharyuke on 5/9/15 at 4:21 PM.
 */
public class BaseReqModel {

    public static final String MODULE_BUY = "Buy";
    public static final String MODULE_HOME = "Home";
    public static final String MODULE_INFORMATION = "Information";
    public static final String MODULE_SHOP = "Shop";
    public static final String MODULE_COMMON = "Common";
    public static final String MODULE_FUNCTION = "Function";
    public static final String MODULE_PAY = "PayMent";
    public static final String MODULE_BUSINESS = "Business";

    public static final String METHOD_FIRST_PRODUCT_TYPE = "getFirstProductType";
    public static final String METHOD_SECOND_PRODUCT_TYPE = "getSecondProductType";
    public static final String METHOD_FIRST_MERCHANT_TYPE = "getFirstMerchantType";
    public static final String METHOD_SECOND_MERCHANT_TYPE = "getSecondMerchantType";
    public static final String METHOD_GET_PRODUCT_LIST = "getProductList";
    public static final String METHOD_GET_MERCHANT_LIST = "getMerchant";


    //获取服务公告列表
    public static final String METHOD_GET_SERVICE_BULLETIN_LIST = "getServiceBulletinList";

    //获取报修地址
    public static final String METHOD_GET_REPAIRS_ADDRESS_LIST = "getRepairsAddressList";

    //获取拼好货数据
    public static final String METHOD_GET_GOOD_PRODUCT_LIST = "getSpellGoods";
    //预定信息
    public static final String METHOD_SET_ORDER_SPELL_GOODS = "orderSpellGoods";

    //获取奖励列表
    public static final String METHOD_GET_REWARD_LIST = "getReward";

    //提交积分奖励
    public static final String METHOD_COMPLETE_REWARD = "completeReward";

    //获取资讯分类
    public static final String METHOD_GET_INFORMATION_TYPE = "getInformationType";

    //获取活动区
    public static final String METHOD_GET_ACTIVITYS = "getActivitys";

    //获取资讯信息列表
    public static final String METHOD_GET_INFORMATION_LIST = "getInformationList";

    //获取致辞列表
    public static final String METHOD_GET_ADDRESS_LIST = "getAddressList";

    //获取致辞列表
    public static final String METHOD_GET_WORK_GUIDES_LIST = "getWorkGuidesList";

    //获取抽奖表盘信息(已登录用户)
    public static final String METHOD_GET_PRIZES = "getPrizes";

    //获取抽奖表盘信息(未登录用户)
    public static final String METHOD_GET_UN_PRIZES = "getUnPrizes";

    //保存抽奖信息
    public static final String METHOD_SET_PRIZES = "savePrizes";

    //回复结算成功
    public static final String METHOD_SET_RETRIEVE = "retrieve";

    //获取积分兑换商品信息
    public static final String METHOD_GET_SCORE_GOODS = "getScoreGoodsList";

    //获取我的抽奖列表信息
    public static final String METHOD_GET_MY_PRIZES = "getMyPrizes";

    //获取我的积分信息
    public static final String METHOD_GET_MY_SCORE = "getMyScore";

    //获取我的积分列表信息
    public static final String METHOD_GET_SCORE_LIST = "viewScoreList";

    //获取积分分级信息
    public static final String METHOD_GET_SCORE_LEVEL = "getScoreLevel";

    //获取积分兑换列表信息
//    public static final String METHOD_GET_SCORE_CHANGE_LIST = "getScoreChangeList";
    public static final String METHOD_SET_INTEGRAL_ORDER = "getMyIntegralOrderInfo";

    //提交我的积分兑换信息
    public static final String METHOD_SET_SCORE_CHANGE = "doScoreChange";


    //获取签到列表信息
    public static final String METHOD_GET_SIGNIN_INFO = "getSignInInfo";

    //提交签到信息
    public static final String METHOD_SET_SIGNIN_INFO = "getSignInIntegral";

    //设置用户昵称
    public static final String METHOD_SET_NICK_NAME = "setNickName";

    //获取分享好友汇总信息
    public static final String METHOD_GET_SHARE_FRIENDS = "shareFriends";

    //获取分享好友列表信息
    public static final String METHOD_GET_SHARE_FRIENDS_LIST = "shareFriendsInformation";

    //获取用户公告信息
    public static final String METHOD_GET_MY_NOTICE_LIST = "getMyInformation";

    //获取用户公告信息
    public static final String METHOD_GET_NEW_CLIENT_PAGE = "getNewClientPage";

    //获取报事保修事件
    public static final String METHOD_GET_REPAIRSS_LIST = "getRepairssList";

    //修改报修状态
    public static final String METHOD_UP_DATE_REPAIRS_STATUS = "updateRepairsStatus";

    //获取公共报事保修事件
    public static final String METHOD_GET_PUBLIC_REPAIRS_LIST = "getPublicRepairsList";

    //获取公共报事部门列表
    public static final String METHOD_GET_APPOINT_DEPARTMENT = "getAppointDepartment";

    //获取公共报事部门人员列表
    public static final String METHOD_GET_APPOINT_MAN = "getAppointMan";

    //公共报事部门人员指派
    public static final String METHOD_APPOINT_MAN = "appointMan";

    //公共报事部门人员指派
    public static final String METHOD_GET_PUBLIC_REPAIRS_PROGRESSION = "getPublicRepairsProgression";

    //修改公共报修状态
    public static final String METHOD_UP_DATE_PUBLIC_REPAIRS_STATUS = "updatePublicRepairsStatus";

    //维修进度
    public static final String METHOD_REPAIRS_PROGRESSION = "getRepairsProgression";

    //添加报事报修
    public static final String METHOD_SET_REPAIRSS = "setRepairss";

    //获取项目名称列表
    public static final String METHOD_GET_REPAIRS_PROJECT = "getRepairsProject";

    //业主投诉与建议列表
    public static final String METHOD_SHOW_USER_SUGGESTION_LIST= "showUserSuggestionList";

    //业主投诉与建议详情
    public static final String METHOD_SHOW_USER_SUGGESTION_INFO= "showUserSuggestionInfo";

    //业主投诉建议满意度评分
    public static final String METHOD_SET_COMPLAIN_SUGGESTION_EVALUATE= "finishUserSuggestion";

    //业主投诉建议满意度评分
    public static final String METHOD_GET_CULTURAL_ACTIVITYS_LIST= "getCulturalActivitysList";


    //-------------E家汇首页start
    public static final String METHOD_GET_EPRODUCT_LIST = "getFirstHot";
    public static final String METHOD_GET_ESEARCH_LIST = "searchFirstHot";
    //---------------end
    public static final String METHOD_GET_ADVERTISE = "getAdvertise";
    public static final String METHOD_CHECK_VERSION = "checkVersion";

    public String module;
    public String method;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
