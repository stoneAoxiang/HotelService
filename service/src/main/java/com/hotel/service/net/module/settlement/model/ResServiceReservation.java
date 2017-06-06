package com.hotel.service.net.module.settlement.model;

import com.hotel.service.net.module.BaseResModel;

/**
 * Created by hyz on 2016/2/24.
 */
public class ResServiceReservation extends BaseResModel{
    public String result;   //	返回码（200:结算成功;300: 结算失败）
    public String orderId;   //	订单ID
    public String orderPrice;   //	订单价格
    public String tradeTime;   //	交易日期
    public String orderName;   //	订单物品名称
    public String reservationCode;   //	优惠码

}
