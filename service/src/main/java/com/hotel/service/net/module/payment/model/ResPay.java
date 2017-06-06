package com.hotel.service.net.module.payment.model;

import com.hotel.service.net.module.BaseResModel;

/**
 * Created by aoxiang on 2016/2/17.
 */
public class ResPay  extends BaseResModel {

    public String result;   	//返回码（200:结算成功;300: 结算失败）	string
    public String rspType;   	//	响应类型	string
    public String rspCode;   	//	交易返回码	string
    public String rspMsg;   	//	交易返回消息	string
    public String rspDate;   	//	响应日期	string
    public String rspTime;   	//	响应时间	string
    public String merPtcId;   	//	一级商户协议号	string
    public String merOrderNo;   	//	平台商户（外部）订单号	string
    public String tradeTn;   	//	交易流水号	string
    public String trdMerId;   	//	第三方商户号	string
    public String tranStt;   	//	交易状态	string
    public String tranRspCode;   	//	交易处理码	string
    public String tranRspMsg;   	//	交易处理信息	string


}
