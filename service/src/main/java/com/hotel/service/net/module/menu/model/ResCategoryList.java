package com.hotel.service.net.module.menu.model;

import com.hotel.service.net.module.BaseResModel;

import java.util.List;

/**
 * Created by Administrator on 2016/2/19.
 */
public class ResCategoryList extends BaseResModel{
    public List<CategoryList> categoryList;
    public String result;

    public static class CategoryList {

        public String parent; //父类别id
        public String id; //类别id	string
        public String name; //类别名	string
        public String pictureUrl; //类别图标
    }

}
