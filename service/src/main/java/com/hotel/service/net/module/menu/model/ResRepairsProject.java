package com.hotel.service.net.module.menu.model;

import com.hotel.service.net.module.BaseResModel;

import java.util.List;

/**
 * Created by Administrator on 2016/2/19.
 */
public class ResRepairsProject extends BaseResModel {
    public List<ProjectLists> ProjectList;
    public String method;


    public static class ProjectLists {
        public String projectId;
        public String projectName;
    }

}
