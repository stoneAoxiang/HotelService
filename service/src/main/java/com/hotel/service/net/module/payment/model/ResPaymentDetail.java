package com.hotel.service.net.module.payment.model;

import com.hotel.service.net.PageModel;
import com.hotel.service.net.module.BaseResModel;

import java.util.List;

/**
 * Created by aoxiang on 2015/12/9.
 */
public class ResPaymentDetail extends BaseResModel implements PageModel<ResPaymentDetail.PaymentDetail> {
    public String result;
    public List<PaymentDetail> ptDetal_list;
    public String s_total;
    public String s_num;


    @Override
    public List<PaymentDetail> getData() {
        return ptDetal_list;
    }

    public class PaymentDetail {

        public String paymentId; //缴费记录Id
        public String paymentTypeName; //缴费单个名目
        public String paymentstatus; //缴费状态
        public String cost; //缴费金额

    }
}
