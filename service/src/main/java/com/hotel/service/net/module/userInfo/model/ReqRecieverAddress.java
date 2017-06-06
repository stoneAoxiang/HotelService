package com.hotel.service.net.module.userInfo.model;

import com.hotel.service.net.module.BaseReqModel;

/**
 * Created by aoxiang on 2015/12/23.
 */
public class ReqRecieverAddress extends BaseReqModel {
    public String userid;
    public String type; //类型（增删改查依次为C,D,U,R；s为默认信息）
    public String isDefault; //	是否为默认（d为默认地址,""为不默认）
    public String id; //	地址流水id
    public String name; //	名称
    public String address; //	地址
    public String phone; //	联系电话


    public ReqRecieverAddress(String module, String method, String userid, String type, String isDefault, String id
            , String name, String address, String phone) {
        this.module = module;
        this.method = method;
        this.userid = userid;
        this.type = type;
        this.isDefault = isDefault;
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;

    }


    public static class Builder {
        public String module;
        public String method;
        public String userid;
        public String type; //类型（增删改查依次为C,D,U,R；s为默认信息）
        public String isDefault; //	是否为默认（d为默认地址,""为不默认）
        public String id; //	地址流水id
        public String name; //	名称
        public String address; //	地址
        public String phone; //	联系电话

        public Builder setModule(String module) {
            this.module = module;
            return this;
        }

        public Builder setMethod(String method) {
            this.method = method;
            return this;
        }

        public Builder setUserid(String userid) {
            this.userid = userid;
            return this;
        }

        public Builder setType(String type) {
            this.type = type;
            return this;
        }

        public Builder setIsDefault(String isDefault) {
            this.isDefault = isDefault;
            return this;
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder setPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public ReqRecieverAddress build() {
            return new ReqRecieverAddress(module, method, userid, type, isDefault, id, name, address, phone);
        }
    }
}