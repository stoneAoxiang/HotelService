package com.hotel.service.net.module.award.model;

import com.hotel.service.net.module.BaseReqModel;

/**
 * Created by aoxiang on 2015/12/2.
 */
public class ReqCompleteAward extends BaseReqModel {

    public String userId;
    public String id; //	流水id( 6:首次注册安装；7：上午探班；8：下午探班；9：补填邀请码
    public String code; //	奖励代码类型 3表示奖励
    public String awardIntegral; //	奖励积分
    public String inviteCode; //邀请码


    public ReqCompleteAward(String module, String method, String userId, String id,
                            String code, String awardIntegral, String inviteCode) {
        this.module = module;
        this.method = method;
        this.userId = userId;
        this.id = id;
        this.code = code;
        this.awardIntegral = awardIntegral;
        this.inviteCode = inviteCode;

    }


    public static class Builder {
        public String module;
        public String method;
        public String userId;
        public String id;
        public String code;
        public String awardIntegral;
        public String inviteCode;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setModule(String module) {
            this.module = module;

            return this;
        }

        public Builder setUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder setCode(String code) {
            this.code = code;
            return this;
        }

        public Builder setAwardIntegral(String awardIntegral) {
            this.awardIntegral = awardIntegral;
            return this;
        }

        public Builder setInviteCode(String inviteCode) {
            this.inviteCode = inviteCode;
            return this;
        }

        public Builder setMethod(String method) {
            this.method = method;
            return this;
        }


        public ReqCompleteAward build() {
            return new ReqCompleteAward(module, method, userId, id,
                    code, awardIntegral, inviteCode);
        }
    }
}
