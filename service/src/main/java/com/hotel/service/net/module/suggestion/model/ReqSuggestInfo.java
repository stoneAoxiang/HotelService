package com.hotel.service.net.module.suggestion.model;

import com.hotel.service.net.module.BaseReqModel;

/**
 * Created by hyz on 2016/2/24.
 */
public class ReqSuggestInfo extends BaseReqModel{

    public String userSuggestionId;

    public ReqSuggestInfo(String method, String userSuggestionId){

        this.method = method;
        this.userSuggestionId = userSuggestionId;

    }

    public static  class Builder{

        public String method;
        public String userSuggestionId;

        public Builder setMethod(String method) {
            this.method = method;
            return this;
        }

        public Builder setUserSuggestionId(String userSuggestionId) {
            this.userSuggestionId = userSuggestionId;
            return this;
        }

        public ReqSuggestInfo build(){

            return  new ReqSuggestInfo(method, userSuggestionId);
        }
    }
}
