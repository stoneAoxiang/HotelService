package com.hotel.service.net.module.userInfo.model;

import com.hotel.service.net.PageModel;
import com.hotel.service.net.module.BaseResModel;

import java.util.List;

/**
 * Created by aoxiang on 2015/12/24.
 */
public class ResMyNoticeList extends BaseResModel implements PageModel<ResMyNoticeList.MyInfoList> {
    public List<MyInfoList> myInfoList;

    public String s_num;
    public String s_total;

    @Override
    public List<MyInfoList> getData() {
        return myInfoList;
    }

    public class MyInfoList {

        public String id; //信息公告id
        public String contentFile; //内容
        public String title; //标题
        public String createTime; //创建时间

    }
}
