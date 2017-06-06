package com.hotel.service.net.module.menu.model;

import com.hotel.service.net.module.BaseResModel;

import java.util.List;

/**
 * Created by Administrator on 2016/2/19.
 */
public class ResServiceAddress extends BaseResModel{
    public List<AreasList> areasList;

    public String result;   //返回码（200：成功； 300:失败；）

    public static class AreasList {
        public String parent;   //父区域id
        public String id;   //区域id
        public String name;   //区域名
        public String fullPath;   //全路径名
    }

}
