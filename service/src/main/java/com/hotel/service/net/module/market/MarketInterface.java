package com.hotel.service.net.module.market;

import com.hotel.service.net.module.market.model.EReqProductList;
import com.hotel.service.net.module.market.model.EReqSearchList;
import com.hotel.service.net.module.market.model.EResSearchList;
import com.hotel.service.net.module.market.model.ReqGetAdvertise;
import com.hotel.service.net.module.market.model.ReqMenuList;
import com.hotel.service.net.module.market.model.ReqMerchantList;
import com.hotel.service.net.module.market.model.ReqProductList;
import com.hotel.service.net.module.market.model.ReqProviderServiceDetail;
import com.hotel.service.net.module.market.model.ReqProviderServiceList;
import com.hotel.service.net.module.market.model.ReqSpePriceMenuList;
import com.hotel.service.net.module.market.model.ResFirstMenuList;
import com.hotel.service.net.module.market.model.ResGetAdvertise;
import com.hotel.service.net.module.market.model.ResMerchantList;
import com.hotel.service.net.module.market.model.ResProductList;
import com.hotel.service.net.module.market.model.EResProductList;
import com.hotel.service.net.module.market.model.ResProviderServiceDetail;
import com.hotel.service.net.module.market.model.ResProviderServiceList;
import com.hotel.service.net.module.market.model.ResSecMenuList;
import com.hotel.service.net.module.market.model.ResSpePriceFirstMenuList;
import com.hotel.service.net.module.market.model.ResSpePriceSecMenuList;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import rx.Observable;

/**
 * MarketInterface
 * Created by sharyuke on 15-6-4.
 */
public interface MarketInterface {
    @POST("/app/terminalapi/call")
    @FormUrlEncoded
    Observable<ResFirstMenuList> getFirstMenuList(@Field("requestValue") ReqMenuList reqMenuList);

    @POST("/app/terminalapi/call")
    @FormUrlEncoded
    Observable<ResSecMenuList> getSecondMenuList(@Field("requestValue") ReqMenuList reqMenuList);


    @POST("/app/terminalapi/call")
    @FormUrlEncoded
    Observable<ResSecMenuList> getPublicSecondMenuList(@Field("requestValue") ReqMenuList reqMenuList);


    @POST("/app/terminalapi/call")
    @FormUrlEncoded
    Observable<ResProductList> getServiceList(@Field("requestValue") ReqProductList reqProductList);

    @POST("/app/terminalapi/call")
    @FormUrlEncoded
    Observable<ResProductList> getProductList(@Field("requestValue") ReqProductList reqProductList);

    @POST("/app/terminalapi/call")
    @FormUrlEncoded
    Observable<EResProductList> getEproductList(@Field("requestValue") EReqProductList reqProductList);

    @POST("/app/terminalapi/call")
    @FormUrlEncoded
    Observable<EResSearchList> getEsearchList(@Field("requestValue") EReqSearchList reqProductList);

    @POST("/app/terminalapi/call")
    @FormUrlEncoded
    Observable<ResGetAdvertise> getAdvertise(@Field("requestValue") ReqGetAdvertise reqGetAdvertise);

    /**
     * 获取广告
     * @param reqGetAdvertise
     * @return
     */
    @POST("/app/terminalapi/call")
    @FormUrlEncoded
    Observable<ResGetAdvertise> getPublicAdvertise(@Field("requestValue") ReqGetAdvertise reqGetAdvertise);

    /**
     * 获取特价区产品类型下拉一级菜单
     * @param reqMenuList
     * @return
     */
    @POST("/app/terminalapi/call")
    @FormUrlEncoded
    Observable<ResSpePriceFirstMenuList> getSpePriceFirstMenu(@Field("requestValue") ReqSpePriceMenuList reqMenuList);

    /**
     * 获取特价区产品类型下拉二级菜单
     * @param reqMenuList
     * @return
     */
    @POST("/app/terminalapi/call")
    @FormUrlEncoded
    Observable<ResSpePriceSecMenuList> getSpePriceSecondMenu(@Field("requestValue") ReqSpePriceMenuList reqMenuList);

    /**
     * 获取商家列表
     * @param reqMerchantList
     * @return
     */
    @POST("/app/terminalapi/call")
    @FormUrlEncoded
    Observable<ResMerchantList> getMerchantList(@Field("requestValue") ReqMerchantList reqMerchantList);

    /**
     * 获取商家商品/服务信息列表
     * @param reqInfo
     * @return
     */
    @POST("/app/terminalapi/call")
    @FormUrlEncoded
    Observable<ResProviderServiceList> getProviderService(@Field("requestValue") ReqProviderServiceList reqInfo);

    /**
     * 获取商品/服务详情
     * @param reqInfo
     * @return
     */
    @POST("/app/terminalapi/call")
    @FormUrlEncoded
    Observable<ResProviderServiceDetail> getServiceDetail(@Field("requestValue") ReqProviderServiceDetail reqInfo);
}

