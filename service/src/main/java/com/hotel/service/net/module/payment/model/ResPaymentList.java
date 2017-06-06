package com.hotel.service.net.module.payment.model;

import com.hotel.service.net.PageModel;
import com.hotel.service.net.module.BaseResModel;

import java.util.List;

/**
 * Created by aoxiang on 2015/12/9.
 */
public class ResPaymentList extends BaseResModel implements PageModel<ResPaymentList.PaymentList> {
    public String result;
    public List<PaymentList> propertyPayment_list;
    public String s_total;
    public String s_num;
    public String houseName; //房间门牌号
    public String area; //区域
    public String tenantName; //租户名称


    @Override
    public List<PaymentList> getData() {
        return propertyPayment_list;
    }

    public class PaymentList {

        public String p_id; //缴费记录Id
        public String p_totalcost; //缴费合计
        public String p_date; //缴费日期
        public String p_paymentstatus; //缴费状态


    }
}
