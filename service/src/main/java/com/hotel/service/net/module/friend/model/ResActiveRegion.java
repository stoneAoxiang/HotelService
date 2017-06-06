package com.hotel.service.net.module.friend.model;

import com.hotel.service.net.PageModel;
import com.hotel.service.net.module.BaseResModel;

import java.util.List;

/**
 * Created by aoxiang on 2015/12/7.
 */
public class ResActiveRegion  extends BaseResModel implements PageModel<ResActiveRegion.ActivityList> {
    public List<ActivityList> activity_list;

    public String s_num;
    public String s_total;

    @Override
    public List<ActivityList> getData() {
        return activity_list;
    }

    public class ActivityList {
        public String activity_id;
        public String activity_name;
        public String activity_img;
        public String activity_memo;

    }
}
