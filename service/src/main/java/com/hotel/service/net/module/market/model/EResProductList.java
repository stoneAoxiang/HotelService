package com.hotel.service.net.module.market.model;

import com.hotel.service.net.PageModel;
import com.hotel.service.net.module.BaseResModel;

import java.util.List;

/**
 * ReqProductList
 * Created by sharyuke on 15-6-5.
 */
public class EResProductList extends BaseResModel implements PageModel<EResProductList.EProductList> {
    public List<EProductList> first_hot_list;
    public String num;
    public String index;
    public String s_total;
    public String method;
    public String s_num;


    @Override
    public List<EProductList> getData() {
        return first_hot_list;
    }

    public class EProductList {
        public int buy_count;
        public String id;
        public double price;
        public String name;
        public String iconurl;
        public String type;
        public String description;
        public String product_score;


    }
}
