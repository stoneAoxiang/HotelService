package com.hotel.service.net.module.menu.model;

import com.hotel.service.net.module.BaseResModel;

import java.util.List;

/**
 * Created by Administrator on 2016/2/19.
 */
public class ResServiceMethod extends BaseResModel{
    public List<MethodList> methodList;

    public static class MethodList {
        public String id;
        public String name;
    }

}
