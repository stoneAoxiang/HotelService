package com.hotel.service.net.module.repairs;

import com.hotel.service.net.BaseApi;
import com.hotel.service.net.ViewProxyInterface;
import com.hotel.service.net.module.BaseReqModel;
import com.hotel.service.net.module.repairs.model.ReqPublicProgression;
import com.hotel.service.net.module.repairs.model.ReqPublicRepairs;
import com.hotel.service.net.module.repairs.model.ResPublicProgression;
import com.hotel.service.net.module.repairs.model.ResPublicRepairs;

import javax.inject.Inject;

import retrofit.RestAdapter;
import rx.Observable;

/**
 * Created by hyz on 2016/1/26.
 */
public class PublicRepairsApi extends BaseApi{

    static publicRepairsInterface server;

    @Inject
    public PublicRepairsApi(RestAdapter restAdapter){
        server = restAdapter.create(publicRepairsInterface.class);
    }

    //列表详情
    public Observable<ResPublicRepairs> getPublicRepairsInfo(ReqPublicRepairs reqPublicRepairs){
        return  getPublicRepairsInfo(reqPublicRepairs,null);
    }

    public Observable<ResPublicRepairs> getPublicRepairsInfo(ReqPublicRepairs reqPublicRepairs, ViewProxyInterface viewProxy) {
        onLoading(viewProxy);
        reqPublicRepairs.module = BaseReqModel.MODULE_COMMON;
        reqPublicRepairs.method = BaseReqModel.METHOD_GET_PUBLIC_REPAIRS_LIST;

        return getRes(server.getPublicRepairsInfo(reqPublicRepairs), viewProxy);
    }




    //获取公共报修进度
    public Observable<ResPublicProgression> getPublicPro(ReqPublicProgression reqPublicProgression){
        return  getPublicPro(reqPublicProgression, null);
    }

    public Observable<ResPublicProgression> getPublicPro(ReqPublicProgression reqPublicProgression, ViewProxyInterface viewProxy) {
        onLoading(viewProxy);
        reqPublicProgression.module = BaseReqModel.MODULE_COMMON;
        reqPublicProgression.method = BaseReqModel.METHOD_GET_PUBLIC_REPAIRS_PROGRESSION;

        return getRes(server.getPublicPro(reqPublicProgression), viewProxy);
    }


}
