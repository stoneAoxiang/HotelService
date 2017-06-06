package com.hotel.service.util;


public class ConstantValue {



    public final static int SET_PUBLIC_REPAIRS = 16; //提交公共报修

    public final static int SET_REGISTER_INFO = 21; //注册用户

    public final static int GET_COUNTY_INFO = 43; //获取区县列表请求信息
    public final static int GET_COMMUITY_OF_COUNTY_INFO = 44; //获取区县对应的小区信息
    public final static int GET_MERCHANT_LIST = 52; //获取一公里商圈商家列表

    public final static int HOME_ADVERTISE = 0; //首页主广告
    public final static int PUBLIC_REPAIRS = 4; //公共报修

    public final static int GET_DISCOUNT_INFO = 8;
    public final static int PAGE_SIZE = 20; //每页显示的数据条数
    public final static int REQUEST_DELAY = 20000;

    public static int REGISTER_FIRST = 0;
    public static int HOME_LOGIN = 2; //首页登录

    public final static int MODIFY_PASSWORD_INFO = 32; //修改用户登录密码

    public final static int GET_REPAIRS_ADDRESS = 67; //获得报修地址

    //============================底部导航菜单================================
    public final static String HOME_PAGE_TAB = "首页";
    public final static String LIFE_TAB = "励即购";
    public final static String SERVICE_TAB = "便民服务";
    public final static String HOUSE_TAB = "房屋租售";
    public final static String SHOPPING_CART_TAB = "购物车";


    public final static String AWARD = "奖励";
    public final static String I_HOME = "i家";
    public final static String ME_TAB = "我";
    public final static String I_HOME_TAB = "i家";
    public final static String  BARGAIN_TAB = "商城";

    public final static int GET_MAIN_TAB = 1; //获取首页信息
    public final static int GET_SHOP_CITY_TAB = 2; //获取商城信息
    public final static int GET_IHOME_TAB = 3; //获取i家信息
    public final static int GET_MY_HOME_TAB = 4; //获取个人中心

    public final static int START_LODING = 1;
    public final static int STOP_LODING = 2;

    /**
     * // 已下单 0
     */
    public final static int BOOKED = 0;
    /**
     * // 商户取消 1
     */
    public final static int REJECTED = 1;
    /**
     * // 客户自己取消 2
     */
    public final static int CANCEL = 2;
    /**
     * // 商户已发货/已派工 3
     */
    public final static int OKSEND = 3;
    /**
     * // 客户确认收货/客户确认已服务 4
     */
    public final static int CONFIRM = 4;
    /**
     * // 客户已评价 5
     */
    public final static int RANKED = 5;

    /**
     * 已支付状态
     */
    public final static int PAID = 6;

    public final static int DELETE_ORDER = 99; //回收站

    /**
     * 推送启动延迟
     */
    public final static int PUSH_DELAY_TIME = 1000 * 10;

    //===============================订单状态结束====================================

    public final static int failure = 0; //失败

    public final static int success = 1; //成功

    public static boolean isMore = false;

    /**
     * 用户是否登录
     */
    public static boolean isLogin = false;

    /**
     * 用户是否业主
     */
    public static boolean isMater = false;

    /**
     * 默认社区ID
     */
    public final static String EXPERIENCE_COMMUNITY_ID = "28"; //体验小区ID

    /**
     * 默认社区名称
     */
    public final static String EXPERIENCE_COMMUNITY_NAME = "俪生活"; //体验小区

    public final static String IMG_PRODUCT_WIDTH = "150";
    public final static String IMG_PRODUCT_HEIGHT = "150";

    public final static String IMG_PRODUCT_DETAIL_WIDTH = "300";
    public final static String IMG_PRODUCT_DETAIL_HEIGHT = "300";

}
