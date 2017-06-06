package com.hotel.service.net.module.integer.model;

import com.hotel.service.net.module.BaseReqModel;

/**
 * Created by hyz on 2015/12/15.
 */
public class ReqScoreGainNotes extends BaseReqModel {

    public String userid;
    public String index;
    public String num;

    public ReqScoreGainNotes(String method,String userid,String index,String num){

        this.method = method;
        this.userid = userid;
        this.index = index;
        this.num = num;
    }
    public static class Builder{

        public  String method;
        public String userid;
        public String index;
        public String num;

        public Builder setMethod(String method){
            this.method = method;
            return this;
        }
        public Builder setUserId(String userid){
            this.userid = userid;
            return this;
        }
        public Builder seIndex(String index){
            this.index = index;
            return this;
        }
        public Builder setNum(String num){
            this.num = num;
            return this;
        }
        public ReqScoreGainNotes build(){
            return new ReqScoreGainNotes(method,userid,num,index);
        }


    }
}
