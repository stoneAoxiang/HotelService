package com.hotel.service.net.module.newinfo.model;

import com.hotel.service.net.PageModel;
import com.hotel.service.net.module.BaseResModel;

import java.util.List;

/**
 * Created by hyz on 2015/12/3.
 * 服务器返回参数
 */
public class ResNewInfo extends BaseResModel implements PageModel<ResNewInfo.InfoTypeList> {


    public  String result;
    public String method;
    public String s_total;
    public String s_num;
    public List<InfoTypeList> TypeList;    //资讯分类集合数组

    @Override
    public List<InfoTypeList> getData() {
        return TypeList;
    }

    public class InfoTypeList {
        public String id; //资讯分类id
        public String name; //资讯分类名称
    }

}
