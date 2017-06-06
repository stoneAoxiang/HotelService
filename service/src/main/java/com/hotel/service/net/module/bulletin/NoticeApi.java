package com.hotel.service.net.module.bulletin;

import com.hotel.service.net.BaseApi;
import com.hotel.service.net.ViewProxyInterface;
import com.hotel.service.net.module.bulletin.model.ReqBulletinList;
import com.hotel.service.net.module.bulletin.model.ResBulletinList;
import com.hotel.service.net.module.market.model.ReqMenuList;

import javax.inject.Inject;

import retrofit.RestAdapter;
import rx.Observable;

/**
 * Created by aoxiang on 2015/12/9.
 */
public class NoticeApi extends BaseApi {
    static NoticeInterface server;

    @Inject
    public NoticeApi(RestAdapter restAdapter) {
        server = restAdapter.create(NoticeInterface.class);
    }

    /**
     * 获取服务公告信息
     * @return
     */
    public Observable<ResBulletinList> getBulletinList(ReqBulletinList reqBulletinList) {
        return getBulletinList(reqBulletinList, null);
    }

    public Observable<ResBulletinList> getBulletinList(ReqBulletinList reqBulletinList, ViewProxyInterface viewProxy) {
        onLoading(viewProxy);
        reqBulletinList.module = ReqMenuList.MODULE_HOME;
        reqBulletinList.method = ReqMenuList.METHOD_GET_SERVICE_BULLETIN_LIST;
        return getRes(server.getBulletinList(reqBulletinList), viewProxy);
    }


}
