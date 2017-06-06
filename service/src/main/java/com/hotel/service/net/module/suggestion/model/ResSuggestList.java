package com.hotel.service.net.module.suggestion.model;

import com.hotel.service.net.PageModel;
import com.hotel.service.net.module.BaseResModel;

import java.util.List;

/**
 * Created by hyz on 2016/3/11.
 */
public class ResSuggestList extends BaseResModel implements PageModel<ResSuggestList.SuggestionList> {

    public String method;
    public String result;
    public String s_total;
    public String s_num;

    public List<SuggestionList> viewScore_list;

    @Override
    public List<ResSuggestList.SuggestionList> getData() {
        return viewScore_list;
    }

    public class SuggestionList {

        public String userSuggestionId;
        public String content;
        public String iconurl;
        public String createTime;
    }
}
