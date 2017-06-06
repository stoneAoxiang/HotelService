package com.hotel.service.net.module.settlement.model;

import com.hotel.service.net.module.BaseReqModel;

/**
 * Created by hyz on 2016/2/24.
 */
public class ReqServiceReservation extends BaseReqModel {

    public String userId;   // 	用户 ID 号
    public String serviceId;   //	服务ID
    public String reservationTime;   //	预约时间
    public String shippingMethod;   //	消费方式
    public String address;   //	地址
    public String name;   //	姓名
    public String tel;   //	电话
    public String memo;   //	备注


    public ReqServiceReservation(String method, String userId, String serviceId, String reservationTime,
                                 String shippingMethod, String address, String name, String tel, String memo) {
        this.method = method;
        this.userId = userId;
        this.serviceId = serviceId;
        this.reservationTime = reservationTime;
        this.shippingMethod = shippingMethod;
        this.address = address;
        this.name = name;
        this.tel = tel;
        this.memo = memo;
    }

    public static class Builder {

        public String method;
        public String userId;   // 	用户 ID 号
        public String serviceId;   //	服务ID
        public String reservationTime;   //	预约时间
        public String shippingMethod;   //	消费方式
        public String address;   //	地址
        public String name;   //	姓名
        public String tel;   //	电话
        public String memo;   //	备注

        public Builder setMethod(String method) {
            this.method = method;
            return this;
        }

        public Builder setUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder setServiceId(String serviceId) {
            this.serviceId = serviceId;
            return this;
        }

        public Builder setReservationTime(String reservationTime) {
            this.reservationTime = reservationTime;
            return this;
        }

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setTel(String tel) {
            this.tel = tel;
            return this;
        }

        public Builder setShippingMethod(String shippingMethod) {
            this.shippingMethod = shippingMethod;
            return this;
        }

        public Builder setMemo(String memo) {
            this.memo = memo;
            return this;
        }

        public ReqServiceReservation build() {
            return new ReqServiceReservation(method, userId, serviceId, reservationTime,
                    shippingMethod, address, name, tel, memo);
        }
    }
}