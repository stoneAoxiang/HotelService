package com.hotel.service.net.module.market.model;

import com.hotel.service.net.PageModel;
import com.hotel.service.net.module.BaseResModel;

import java.util.List;

/**
 * ReqProductList
 * Created by sharyuke on 15-6-5.
 */
public class ResProductList extends BaseResModel implements PageModel<ResProductList.ProductList> {
    public List<ProductList> product_list;
    public String num;
    public String index;
    public String first_product_type_id;
    public String s_total;
    public String second_product_type_id;
    public String method;
    public String s_num;
    public String community_id;

    @Override
    public List<ProductList> getData() {
        return product_list;
    }

    public class ProductList {
        public int buy_count;
        public String product_descript_url;
        public String product_id;
        public String product_price;
        public String product_img_url;
        public String product_name;
        public String product_score;
    }
}
