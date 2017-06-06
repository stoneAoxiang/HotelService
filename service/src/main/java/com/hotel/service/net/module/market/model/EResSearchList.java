package com.hotel.service.net.module.market.model;

import com.hotel.service.net.PageModel;
import com.hotel.service.net.module.BaseResModel;

import java.util.List;

/**
 * ReqProductList
 * Created by sharyuke on 15-6-5.
 */
public class EResSearchList extends BaseResModel implements PageModel<EResSearchList.ESearchList> {
    public List<ESearchList> sear_fh_list;
    public String num;
    public String index;
    public String s_total;
    public String method;
    public String s_num;


    @Override
    public List<ESearchList> getData() {
        return sear_fh_list;
    }

    public class ESearchList {
        public String id;
        public double price;
        public String name;
        public String details;
        public String iconurl;
        public String description;


    }
}
