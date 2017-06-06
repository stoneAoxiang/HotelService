package com.hotel.service.net.module.market.model;

import com.hotel.service.net.PageModel;
import com.hotel.service.net.module.BaseResModel;

import java.util.List;

/**
 * ReqProductList
 * Created by sharyuke on 15-6-5.
 */
public class ResMerchantList extends BaseResModel implements PageModel<ResMerchantList.MerchantList> {
    public List<MerchantList> merchant_list;

    public String num;
    public String index;
    public String first_merchant_type_id;
    public String s_total;
    public String second_merchant_type_id;
    public String method;
    public String s_num;
    public String community_id;

    @Override
    public List<MerchantList> getData() {
        return merchant_list;
    }

    public class MerchantList {
        public String merchant_id;
        public String merchant_img_url;
        public String merchant_name;

    }
}
