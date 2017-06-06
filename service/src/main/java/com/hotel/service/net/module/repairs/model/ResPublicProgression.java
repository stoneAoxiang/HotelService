package com.hotel.service.net.module.repairs.model;

import com.hotel.service.net.PageModel;
import com.hotel.service.net.module.BaseResModel;

import java.util.List;

/**
 * Created by hyz on 2016/2/24.
 */
public class ResPublicProgression extends BaseResModel implements PageModel<ResPublicProgression.getProgressionInfo> {

    public String result;
    public String method;

    public List<getProgressionInfo> ProgressionInfo;

    @Override
    public List<getProgressionInfo> getData() {
        return ProgressionInfo;
    }

    public class getProgressionInfo{

        public String repairsAddress;
        public String repairsContent;
        public String createTime;
        public String progression;
    }

}
