package com.hotel.service.net.module.market;

import com.hotel.service.BuildConfig;
import com.hotel.service.net.BaseApi;
import com.hotel.service.net.ViewProxyInterface;
import com.hotel.service.net.module.market.model.EReqProductList;
import com.hotel.service.net.module.market.model.EReqSearchList;
import com.hotel.service.net.module.market.model.EResProductList;
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
import com.hotel.service.net.module.market.model.ResProviderServiceDetail;
import com.hotel.service.net.module.market.model.ResProviderServiceList;
import com.hotel.service.net.module.market.model.ResSecMenuList;
import com.hotel.service.net.module.market.model.ResSpePriceFirstMenuList;
import com.hotel.service.net.module.market.model.ResSpePriceSecMenuList;
import com.hotel.service.util.Config;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit.RestAdapter;
import rx.Observable;
import timber.log.Timber;

/**
 * MarketApi
 * Created by sharyuke on 15-6-4.
 */
@Singleton
public class MarketApi extends BaseApi {
   static MarketInterface server;

    //利浪专用接口
    private MarketInterface interfaceServer;

    @Inject
    public MarketApi(RestAdapter restAdapter) {
        server = restAdapter.create(MarketInterface.class);
    }

    public Observable<ResFirstMenuList> getFirstMenuList(ReqMenuList reqMenuList) {
        return getFirstMenuList(reqMenuList, null);
    }

    public Observable<ResSecMenuList> getSecondMenuList(ReqMenuList reqMenuList) {
        return getSecondMenuList(reqMenuList, null);
    }


    /**
     * 调用利浪平台二级下拉菜单
     * @param reqMenuList
     * @return
     */
    public Observable<ResSecMenuList> getPublicSecondMenuList(ReqMenuList reqMenuList) {
        return getPublicSecondMenuList(reqMenuList, null);
    }

    public Observable<ResProductList> getProductList(ReqProductList reqProductList) {
        return getProductList(reqProductList, null);
    }
  public Observable<EResProductList> getEproductList(EReqProductList reqProductList) {
        return getEproductList(reqProductList, null);
    }
    public Observable<EResSearchList> getEsearchList(EReqSearchList reqProductList) {
        return getEsearchList(reqProductList, null);
    }

    public Observable<ResGetAdvertise> getAdvertise(ReqGetAdvertise reqGetAdvertise) {
        return getAdvertise(reqGetAdvertise, null);
    }

    /**
     * 调用利浪平台广告
     * @param reqGetAdvertise
     * @return
     */
    public Observable<ResGetAdvertise> getPublicAdvertise(ReqGetAdvertise reqGetAdvertise) {
        return getPublicAdvertise(reqGetAdvertise, null);
    }

    public Observable<ResSpePriceFirstMenuList> getSpePriceFirstMenu(ReqSpePriceMenuList reqMenuList) {
        return getSpePriceFirstMenu(reqMenuList, null);
    }

    public Observable<ResSpePriceSecMenuList> getSpePriceSecondMenu(ReqSpePriceMenuList reqMenuList) {
        return getSpePriceSecondMenu(reqMenuList, null);
    }

    public Observable<ResMerchantList> getMerchantList(ReqMerchantList reqMerchantList) {
        return getMerchantList(reqMerchantList, null);
    }

    public Observable<ResFirstMenuList> getFirstMenuList(ReqMenuList reqMenuList, ViewProxyInterface viewProxy) {
        onLoading(viewProxy);
        reqMenuList.module = ReqMenuList.MODULE_BUY;
        reqMenuList.method = ReqMenuList.METHOD_FIRST_PRODUCT_TYPE;
        return getRes(server.getFirstMenuList(reqMenuList), viewProxy);
    }

    public Observable<ResSecMenuList> getSecondMenuList(ReqMenuList reqMenuList, ViewProxyInterface viewProxy) {
        onLoading(viewProxy);
        reqMenuList.module = ReqMenuList.MODULE_BUY;
        reqMenuList.method = ReqMenuList.METHOD_SECOND_PRODUCT_TYPE;
        return getRes(server.getSecondMenuList(reqMenuList), viewProxy);
    }

    /**
     * 调用利浪平台二级下拉菜单
     * @param reqMenuList
     * @return
     */
    public Observable<ResSecMenuList> getPublicSecondMenuList(ReqMenuList reqMenuList, ViewProxyInterface viewProxy) {
        onLoading(viewProxy);
        reqMenuList.module = ReqMenuList.MODULE_BUY;
        reqMenuList.method = ReqMenuList.METHOD_SECOND_PRODUCT_TYPE;

        return getRes(server.getSecondMenuList(reqMenuList), viewProxy);
    }

    public Observable<ResSpePriceFirstMenuList> getSpePriceFirstMenu(ReqSpePriceMenuList reqMenuList, ViewProxyInterface viewProxy) {
        onLoading(viewProxy);
        reqMenuList.module = ReqMenuList.MODULE_SHOP;
        reqMenuList.method = ReqMenuList.METHOD_FIRST_MERCHANT_TYPE;
        return getRes(server.getSpePriceFirstMenu(reqMenuList), viewProxy);
    }

    public Observable<ResSpePriceSecMenuList> getSpePriceSecondMenu(ReqSpePriceMenuList reqMenuList, ViewProxyInterface viewProxy) {
        onLoading(viewProxy);
        reqMenuList.module = ReqMenuList.MODULE_SHOP;
        reqMenuList.method = ReqMenuList.METHOD_SECOND_MERCHANT_TYPE;
        return getRes(server.getSpePriceSecondMenu(reqMenuList), viewProxy);
    }

    /**
     * get ProductList
     *
     * @param reqProductList request
     * @param viewProxy      view
     * @return Observable<ResProductList>
     */
    public Observable<ResProductList> getProductList(ReqProductList reqProductList, ViewProxyInterface viewProxy) {
        onLoading(viewProxy);
        reqProductList.module = ReqMenuList.MODULE_BUY;
        reqProductList.method = ReqMenuList.METHOD_GET_PRODUCT_LIST;
        return getRes(server.getProductList(reqProductList), viewProxy);
    }

    /**
     * get ProductList
     *
     * @param reqProductList request
     * @param viewProxy      view
     * @return Observable<ResProductList>
     */
    public Observable<ResProductList> getServiceList(ReqProductList reqProductList, ViewProxyInterface viewProxy) {
        onLoading(viewProxy);
        reqProductList.module = ReqMenuList.MODULE_BUY;
        reqProductList.method = ReqMenuList.METHOD_GET_PRODUCT_LIST;

        return getRes(server.getServiceList(reqProductList), viewProxy);
    }

    //------------
    public Observable<EResProductList> getEproductList(EReqProductList reqProductList, ViewProxyInterface viewProxy){
        onLoading(viewProxy);
        reqProductList.module = ReqMenuList.MODULE_SHOP;
        reqProductList.method = ReqMenuList.METHOD_GET_EPRODUCT_LIST;
        return getRes(server.getEproductList(reqProductList),viewProxy);
    }

    public static Observable<EResSearchList> getEsearchList(EReqSearchList reqSearchList, ViewProxyInterface viewProxy){
        onLoading(viewProxy);
        reqSearchList.module = ReqMenuList.MODULE_SHOP;
        reqSearchList.method = ReqMenuList.METHOD_GET_ESEARCH_LIST;
        return getRes(server.getEsearchList(reqSearchList),viewProxy);
    }
    //------------

    public Observable<ResGetAdvertise> getAdvertise(ReqGetAdvertise reqGetAdvertise, ViewProxyInterface viewProxy) {
        onLoading(viewProxy);
        reqGetAdvertise.module = ReqMenuList.MODULE_HOME;
        reqGetAdvertise.method = ReqMenuList.METHOD_GET_ADVERTISE;

        return getRes(server.getAdvertise(reqGetAdvertise), viewProxy);
    }


    /**
     * 调用利浪平台广告
     * @param reqGetAdvertise
     * @return
     */
    public Observable<ResGetAdvertise> getPublicAdvertise(ReqGetAdvertise reqGetAdvertise, ViewProxyInterface viewProxy) {
        onLoading(viewProxy);
        reqGetAdvertise.module = ReqMenuList.MODULE_HOME;
        reqGetAdvertise.method = ReqMenuList.METHOD_GET_ADVERTISE;

        newInterFaceServer();

        return getRes(interfaceServer.getAdvertise(reqGetAdvertise), viewProxy);
    }

    /**
     * 链接到利浪平台，需要新建RestAdapter
     */
    private MarketInterface newInterFaceServer(){

        if (null == interfaceServer) {
            RestAdapter ra = new RestAdapter.Builder()
                    .setEndpoint(Config.PUBLIC_SERVER_URL)
                    .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
                    .setLog(Timber::i)
                    .build();

            return interfaceServer = ra.create(MarketInterface.class);

        }

        return interfaceServer;

    }

    public Observable<ResMerchantList> getMerchantList(ReqMerchantList reqProductList, ViewProxyInterface viewProxy) {
        onLoading(viewProxy);
        reqProductList.module = ReqMenuList.MODULE_SHOP;
        reqProductList.method = ReqMenuList.METHOD_GET_MERCHANT_LIST;
        return getRes(server.getMerchantList(reqProductList), viewProxy);
    }

    public Observable<ResProviderServiceList> getProviderService(ReqProviderServiceList reqInfo) {
        return getProviderService(reqInfo, null);
    }

    public Observable<ResProviderServiceList> getProviderService(ReqProviderServiceList reqInfo, ViewProxyInterface viewProxy) {
        onLoading(viewProxy);
        reqInfo.module = ReqMenuList.MODULE_BUSINESS;
        reqInfo.method = "getProviderServiceList";

        newInterFaceServer();

        return getRes(interfaceServer.getProviderService(reqInfo), viewProxy);
    }

    public Observable<ResProviderServiceDetail> getServiceDetail(ReqProviderServiceDetail reqInfo) {
        return getServiceDetail(reqInfo, null);
    }

    public Observable<ResProviderServiceDetail> getServiceDetail(ReqProviderServiceDetail reqInfo, ViewProxyInterface viewProxy) {
        onLoading(viewProxy);
        reqInfo.module = ReqMenuList.MODULE_COMMON;
        reqInfo.method = "getServiceDetail";

        newInterFaceServer();

        return getRes(interfaceServer.getServiceDetail(reqInfo), viewProxy);
    }

}
