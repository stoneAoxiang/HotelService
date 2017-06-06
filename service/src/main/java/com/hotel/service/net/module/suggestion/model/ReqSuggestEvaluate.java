package com.hotel.service.net.module.suggestion.model;

import com.hotel.service.net.module.BaseReqModel;

/**
 * Created by hyz on 2016/2/24.
 */
public class ReqSuggestEvaluate extends BaseReqModel {

    public String userSuggestionId; //投诉建议id
    public String satisfactionReply; //	满意度内容
    public String score; //	满意度评分（0-5）

    public ReqSuggestEvaluate(String method, String userSuggestionId, String satisfactionReply, String score) {

        this.method = method;
        this.userSuggestionId = userSuggestionId;
        this.satisfactionReply = satisfactionReply;
        this.score = score;

    }

    public static class Builder {

        public String method;
        public String userSuggestionId;
        public String satisfactionReply;
        public String score;

        public Builder setMethod(String method) {
            this.method = method;
            return this;
        }

        public Builder setUserSuggestionId(String userSuggestionId) {
            this.userSuggestionId = userSuggestionId;
            return this;
        }

        public Builder setSatisfactionReply(String satisfactionReply) {
            this.satisfactionReply = satisfactionReply;
            return this;
        }

        public Builder setScore(String score) {
            this.score = score;
            return this;
        }

        public ReqSuggestEvaluate build() {

            return new ReqSuggestEvaluate(method, userSuggestionId, satisfactionReply, score);
        }
    }
}
