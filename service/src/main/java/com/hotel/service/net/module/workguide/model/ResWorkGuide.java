package com.hotel.service.net.module.workguide.model;

import com.hotel.service.net.PageModel;
import com.hotel.service.net.module.BaseResModel;

import java.util.List;

/**
 * Created by hyz on 2015/12/7.
 */
public class ResWorkGuide extends BaseResModel implements PageModel<ResWorkGuide.WorkGuideInfoList> {

    public String method ;           //获取资讯某类型的详细列表（getInformationDetail）

    public String s_total ;
    public String s_num ;

    public List<WorkGuideInfoList> WorkGuideList;    //资讯某个类型详情列表集合数组

    @Override
    public List<WorkGuideInfoList> getData() {
        return WorkGuideList;
    }

    public class WorkGuideInfoList {

        public String id;
        public String imgsDesc;
        public String content;       //地址
        public String createTime;    //创建时间
        public String title;         //标题
    }
}
