package com.hotel.service.net.module.positon.model;

import com.hotel.service.net.module.BaseResModel;

import java.util.List;

/**
 * Created by aoxiang on 2015/12/9.
 */
public class ResOfferType extends BaseResModel{
    public List<TypeLists> TypeList;

    public static class TypeLists {

        public String id;   //职位类型id
        public String typeName;   //职位类型名称
    }
}
