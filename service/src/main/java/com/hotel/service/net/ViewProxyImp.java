package com.hotel.service.net;

import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.hotel.service.R;

/**
 * ViewProxyImp
 * Created by sharyuke on 15-6-8.
 */
public class ViewProxyImp implements ViewProxyInterface {

    private boolean isNotFirstLoad = false;

    private View[] successView;
    private View[] progressView;
    private View[] failedView;
    private View[] emptyView;

    public ViewProxyImp(ViewProxyImp viewProxyImp) {
        if (viewProxyImp == null) return;
        this.isNotFirstLoad = viewProxyImp.isNotFirstLoad;
        this.successView = viewProxyImp.successView;
        this.progressView = viewProxyImp.progressView;
        this.failedView = viewProxyImp.failedView;
        this.emptyView = viewProxyImp.emptyView;
    }

    public ViewProxyImp(boolean isNotFirstLoad, View[] successView, View[] progressView, View[] failedView, View[] emptyView) {
        this.isNotFirstLoad = isNotFirstLoad;
        this.successView = successView;
        this.progressView = progressView;
        this.failedView = failedView;
        this.emptyView = emptyView;
    }

    public static class Builder {
        private boolean isSecondLoad;
        private View[] successView;
        private View[] progressView;
        private View[] failedView;
        private View[] emptyView;

        public Builder setIsSecondLoad(boolean isSecondLoad) {
            this.isSecondLoad = isSecondLoad;
            return this;
        }

        public Builder setSuccessView(View... successView) {
            this.successView = successView;
            return this;
        }

        public Builder setProgressView(View... progressView) {
            this.progressView = progressView;
            return this;
        }

        public Builder setFailedView(View... failedView) {
            this.failedView = failedView;
            return this;
        }

        public Builder setEmptyView(View... emptyView) {
            this.emptyView = emptyView;
            return this;
        }

        public ViewProxyImp build() {
            return new ViewProxyImp(isSecondLoad, successView, progressView, failedView, emptyView);
        }
    }

    @Override
    public boolean isNotFirstLoad() {
        return isNotFirstLoad;
    }

    private String TAG = ViewProxyImp.class.getCanonicalName();

    @Override
    public void onLoading() {

        if (!isNotFirstLoad) {
            Log.i(TAG, "不是第一次加载页面=========================");
            setVisibly(successView, View.GONE);
            setVisibly(progressView, View.VISIBLE);
            setVisibly(failedView, View.GONE);
            setVisibly(emptyView, View.GONE);

        }
    }

    @Override
    public void onSuccess() {
        setVisibly(successView, View.VISIBLE);
        setVisibly(progressView, View.GONE);
        setVisibly(failedView, View.GONE);
        setVisibly(emptyView, View.GONE);
    }

    @Override
    public void onEmpty() {
        setVisibly(successView, View.GONE);
        setVisibly(progressView, View.GONE);
        setVisibly(failedView, View.GONE);
        setVisibly(emptyView, View.VISIBLE);
    }

    @Override
    public void onNoMore() {

    }

    @Override
    public void onFailed() {
        setVisibly(successView, View.GONE);
        setVisibly(progressView, View.GONE);
        setVisibly(failedView, View.VISIBLE);
        setVisibly(emptyView, View.GONE);
    }

    private void setVisibly(View[] views, int visibility) {

        if (views == null) return;

        for (View v : views) {
            if (v == null) return;

            if (v instanceof SwipeRefreshLayout) {
                ((SwipeRefreshLayout) v).setRefreshing(visibility == View.VISIBLE);
            } else {
                if ((v.getVisibility() == View.GONE && visibility == View.VISIBLE)
                        || (v.getVisibility() == View.VISIBLE && visibility == View.GONE)) {

                    v.startAnimation(AnimationUtils.loadAnimation(v.getContext(),
                            visibility == View.VISIBLE ? R.anim.anim_in : R.anim.anim_out));
                }
                v.setVisibility(visibility);
            }
        }
    }
}
