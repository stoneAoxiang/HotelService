package com.hotel.service.net.module.integer.model;

import com.hotel.service.net.PageModel;
import com.hotel.service.net.module.BaseResModel;

import java.util.List;

/**
 * Created by hyz on 2015/12/22.
 */
public class ResMyIntegeralOrderInfo  extends BaseResModel implements PageModel<ResMyIntegeralOrderInfo.MyIntegeralOrderInfoList> {


    public String method ;           //获取资讯某类型的详细列表（getMyOrderInfo）
                                     //返回码（200:获取成功;300: 获取失败）
    public String s_total  ;         //请求总页数
    public String s_num  ;           // 页号

    public List<MyIntegeralOrderInfoList> order_list;

    @Override
    public List<MyIntegeralOrderInfoList> getData(){
        return order_list;
    }
    public class MyIntegeralOrderInfoList{
        public String order_id;               //记录id
        public String order_number;          //兑换商品id
        public String product_img_url;        //兑换使用积分
        public String product_name;       //兑换时间
        public String product_score;
        public String order_count;
        public String order_status_id;
        public String order_status_name;
        public String merchant_name;
        public String merchant_tel;
        public String exchange_time;
        public String receiptCode;
        public String householderName;
        public String consignee;
        public String householderAddress;
    }
}
