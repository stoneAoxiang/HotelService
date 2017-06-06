package com.hotel.service.net.module.integer.model;

import com.hotel.service.net.PageModel;
import com.hotel.service.net.module.BaseResModel;

import java.util.List;

/**
 * Created by hyz on 2015/12/14.
 */
public class ResScoreLevel extends BaseResModel implements PageModel<ResScoreLevel.ScoreLevelList> {

    public String method ;            //getScoreLevel
    public String result  ;           //返回结果（200：成功； 300：失败

    public List<ScoreLevelList> level_list;    //团购信息数组

    @Override
    public List<ScoreLevelList> getData() {
        return level_list;
    }

    public static class ScoreLevelList {
        public String level;
        public String isCheck;

        public ScoreLevelList() {
        }
    }

}
