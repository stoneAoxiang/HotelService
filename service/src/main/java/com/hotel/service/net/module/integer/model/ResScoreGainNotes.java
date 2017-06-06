package com.hotel.service.net.module.integer.model;

import com.hotel.service.net.PageModel;
import com.hotel.service.net.module.BaseResModel;

import java.util.List;

/**
 * Created by hyz on 2015/12/15.
 */
public class ResScoreGainNotes extends BaseResModel implements PageModel<ResScoreGainNotes.ScoreGainNotesList>{

    public String result;
    public String s_total;
    public String s_num;
    public String method;

    public List<ScoreGainNotesList> viewScore_list;

    @Override
    public List<ScoreGainNotesList>getData(){
        return viewScore_list;
    }

    public class  ScoreGainNotesList{

        public String scoreTime;
        public String score;
        public String scoreWay;
        public String scoreWayID;
    }
}
