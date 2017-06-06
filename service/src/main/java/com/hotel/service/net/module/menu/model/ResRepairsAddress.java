package com.hotel.service.net.module.menu.model;

import com.hotel.service.net.module.BaseResModel;

import java.util.List;

/**
 * Created by Administrator on 2016/2/19.
 */
public class ResRepairsAddress extends BaseResModel{
    public List<AddressLists> AddressList;
    public String method;

    public static class AddressLists {
        public String typeName;
    }

}
