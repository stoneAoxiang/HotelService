package com.hotel.service.net.module.market.model;

import com.hotel.service.net.module.BaseResModel;

import java.util.List;

/**
 * ResFirstMenuList
 * Created by sharyuke on 15-6-4.
 */
public class ResSecMenuList extends BaseResModel {
    public List<SecTypeList> second_product_type_list;
    public String method;
    public String community_id;
    public String first_product_type_id;

    public static class SecTypeList {
        public String second_product_type_id;
        public String second_product_type_name;
    }

}
