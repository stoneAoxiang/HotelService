package com.hotel.service.net.module.market.model;

import com.hotel.service.net.module.BaseResModel;

import java.util.List;

/**
 * ResSpePriceFirstMenuList
 * Created by aoxiang on 2015/6/15.
 */
public class ResSpePriceSecMenuList extends BaseResModel {
    public List<SecondTypeList> second_merchant_type_list;
    public String method;
    public String community_id;
    public String first_merchant_type_id;

    public static class SecondTypeList {
        public String second_merchant_type_id;
        public String second_merchant_type_name;
    }
}
