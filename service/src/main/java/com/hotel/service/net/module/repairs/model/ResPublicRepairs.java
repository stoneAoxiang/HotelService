package com.hotel.service.net.module.repairs.model;

import com.hotel.service.net.PageModel;
import com.hotel.service.net.module.BaseResModel;

import java.util.List;

/**
 * Created by hyz on 2016/1/26.
 */
public class ResPublicRepairs extends BaseResModel implements  PageModel<ResPublicRepairs.ReparisInfoList>{


    public String method;               //getRepairssList
    public String s_total;
    public String s_num;
    public String Dep;
    public String result;

    public List<ReparisInfoList> RepairssList;

    @Override
    public List<ReparisInfoList> getData() {
        return RepairssList;
    }

    public class ReparisInfoList{

        public String repairId;
        public String repairsAddress;
        public String repairsContent;
        public String createTime;
        public String repairEmpName;

        public String progression;
        public String imgsDesc;
        public String iconurl;
        public String appointDep;
        public String cSDepart;
        public String repairPersonnelName;
        public String repairPersonnelId;
        public String repairManName;

    }

}
