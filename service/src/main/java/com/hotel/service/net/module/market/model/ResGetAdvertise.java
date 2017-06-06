package com.hotel.service.net.module.market.model;

import com.hotel.service.net.PageModel;
import com.hotel.service.net.module.BaseReqModel;
import com.hotel.service.net.module.BaseResModel;

import java.util.List;

/**
 * ReqProductList
 * Created by sharyuke on 15-6-5.
 */
public class ResGetAdvertise extends BaseResModel implements PageModel<ResGetAdvertise.AdvertiseList> {

    public int advertise_position_id;
    public String community_id;
    public List<AdvertiseList> advertise_list;

    @Override
    public List<AdvertiseList> getData() {
        return advertise_list;
    }

    public class AdvertiseList {
        public String advertise_id;
        public String advertise_memo;
        public String advertise_url;
        public String good_id;
        public String advertise_name;
    }
}
