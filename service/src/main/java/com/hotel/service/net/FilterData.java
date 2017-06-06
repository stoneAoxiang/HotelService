package com.hotel.service.net;

import rx.functions.Func1;

/**
 * 数据过滤器
 * Created by sharyuke on 5/9/15 at 4:37 PM.
 */
public class FilterData<T> implements Func1<T, Boolean> {
    private ViewProxyInterface viewProxy;

    private FilterData(ViewProxyInterface viewProxy) {
        this.viewProxy = viewProxy;
    }

    public static <T> FilterData<T> getFilter(ViewProxyInterface viewProxy) {
        return new FilterData<>(viewProxy);
    }

    @Override
    public Boolean call(T t) {
        if (t == null || (t instanceof PageModel && ((PageModel) t).getData() == null)) {
            BaseApi.onFailed(viewProxy);
            return false;
        } else {
            if (t instanceof PageModel) {
                boolean isEmpty = ((PageModel) t).getData().isEmpty();
                if (isEmpty) {
                    if (viewProxy.isNotFirstLoad()) {
                        BaseApi.onNoMore(viewProxy);
                    } else {
                        BaseApi.onEmpty(viewProxy);
                    }
                } else {
                    BaseApi.onSuccess(viewProxy);
                }
            }
//            BaseApi.onSuccess(viewProxy);
            return true;
        }
    }
}