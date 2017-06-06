package com.hotel.service.net.module.repairs.model;

import com.hotel.service.net.module.BaseReqModel;

/**
 * Created by hyz on 2016/2/24.
 */
public class ReqPublicProgression extends BaseReqModel {


    public String repairsId;

    public ReqPublicProgression(String method, String repairsId){
        this.method = method;
        this.repairsId = repairsId;
    }
    public static class Builder{

        public String method;
        public String repairsId;

        public Builder setMethod(String method) {
            this.method = method;
            return this;
        }

        public Builder setRepairsId(String repairsId) {
            this.repairsId = repairsId;
            return this;
        }
        public ReqPublicProgression build(){
            return new ReqPublicProgression(method,repairsId);
        }
    }
}