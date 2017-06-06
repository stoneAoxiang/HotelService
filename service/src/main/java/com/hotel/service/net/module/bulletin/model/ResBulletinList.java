package com.hotel.service.net.module.bulletin.model;

import com.hotel.service.net.PageModel;
import com.hotel.service.net.module.BaseResModel;

import java.util.List;

/**
 * Created by aoxiang on 2015/12/9.
 */
public class ResBulletinList extends BaseResModel implements PageModel<ResBulletinList.BulletinLists> {
    public List<BulletinLists> BulletinList;
    public String s_total;
    public String s_num;


    @Override
    public List<BulletinLists> getData() {
        return BulletinList;
    }

    public class BulletinLists {
        public String id; //公告id
        public String title; //公告标题
        public String content; //公告内容
        public String sendTime; //发送时间
        public String createTime; //创建时间
        public String sendStatus; //发送状态

    }
}
