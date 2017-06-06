package com.hotel.service.net.module;

/**
 * 基类模型
 * Created by sharyuke on 5/9/15 at 4:21 PM.
 */
public class BaseResModel {
    public String code;
    public Throwable throwable;

    public static BaseResModel error(Throwable throwable) {
        BaseResModel baseResModel = new BaseResModel();
        baseResModel.throwable = throwable;
        return baseResModel;
    }
}
