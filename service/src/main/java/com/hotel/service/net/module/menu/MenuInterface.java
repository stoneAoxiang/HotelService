package com.hotel.service.net.module.menu;

import com.hotel.service.net.module.menu.model.ReqCategoryList;
import com.hotel.service.net.module.menu.model.ReqRepairsAddress;
import com.hotel.service.net.module.menu.model.ReqRepairsProject;
import com.hotel.service.net.module.menu.model.ReqServiceAddress;
import com.hotel.service.net.module.menu.model.ReqServiceMethod;
import com.hotel.service.net.module.menu.model.ResCategoryList;
import com.hotel.service.net.module.menu.model.ResRepairsAddress;
import com.hotel.service.net.module.menu.model.ResRepairsProject;
import com.hotel.service.net.module.menu.model.ResServiceAddress;
import com.hotel.service.net.module.menu.model.ResServiceMethod;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import rx.Observable;

/**
 * MarketInterface
 * Created by aoxiang on 15-6-4.
 */
public interface MenuInterface {

    /**
     * 获取报修地址
     * @param reqMenuList
     * @return
     */
    @POST("/app/terminalapi/call")
    @FormUrlEncoded
    Observable<ResRepairsAddress> getRepairsAddressList(@Field("requestValue") ReqRepairsAddress reqMenuList);

    // 获取项目名称列表
    @POST("/app/terminalapi/call")
    @FormUrlEncoded
    Observable<ResRepairsProject> getRepairsProject(@Field("requestValue") ReqRepairsProject reqMenuList);

    // 获取服务类型列表
    @POST("/app/terminalapi/call")
    @FormUrlEncoded
    Observable<ResCategoryList> getCategoryList(@Field("requestValue") ReqCategoryList reqStr);

    /**
     * 获取服务区域
     * @return
     */
    @POST("/app/terminalapi/call")
    @FormUrlEncoded
    Observable<ResServiceAddress> getServiceAreasList(@Field("requestValue") ReqServiceAddress reqInfo);

    /**
     * 获取服务方式
     * @return
     */
    @POST("/app/terminalapi/call")
    @FormUrlEncoded
    Observable<ResServiceMethod> getServiceMethodList(@Field("requestValue") ReqServiceMethod reqInfo);

}

