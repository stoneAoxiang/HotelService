package com.hotel.service.ui.base;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.hotel.service.util.ConstantValue;
import com.hotel.service.volley.RequestManager;

import butterknife.ButterKnife;
import rx.subscriptions.CompositeSubscription;

/**
 * baseActivity
 * Created by aoxiang on 15-5-27.
 */
public class BaseFragmentActivity extends FragmentActivity {

    private String TAG= BaseFragmentActivity.class.getCanonicalName();

    protected CompositeSubscription subscription = new CompositeSubscription();

    private BaseFunction baseFunction;

    /**
     * 当前页数据索引
     */
    public int nowPage = 1;
    /**
     * 每页显示数据量: 20条
     */
    public int countPage = ConstantValue.PAGE_SIZE;
    /**
     * 数据总页数
     */
    public int totalPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        baseFunction = new BaseFunction(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "注册RX=============================" + getClass().getName());
        subscription = new CompositeSubscription();
    }

    @Override
    protected void onStop() {
        super.onStop();
        RequestManager.cancelAll(this);
        subscription.unsubscribe();
        Log.i(TAG, "移除注册RX=============================" + getClass().getName());
    }


    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.inject(this);
    }

    protected void executeRequest(Request<?> request) {
        RequestManager.addRequest(request, this);
    }

    protected Response.ErrorListener errorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BaseFragmentActivity.this, "网路异常或获取数据有问题", Toast.LENGTH_LONG).show();
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscription != null) {
            subscription.unsubscribe();
        }
        ButterKnife.reset(this);
    }

    public boolean checkUser() {
        return baseFunction.checkUser();
    }

    public ProgressDialog getPd(){
        return baseFunction.pd;
    }

    public void changeDialogStatus(){
        baseFunction.changeDialogStatus();
    }

    public void toastDialog(String info, int iconFlag) {
        baseFunction.showToast(info);
    }

    protected void setDataDialog() {
        baseFunction.setDataDialog();
    }

    public String getResultStr(String responeData) {
        return baseFunction.getResultStr(responeData);
    }

    public void requestError(Throwable throwable){
        baseFunction.requestError(throwable);
    }

    public boolean isGetData() {
        return baseFunction.isGetData();
    }

    public void setIsGetData(boolean isGetData) {
        baseFunction.setIsGetData(isGetData);
    }

    public Point getWindowSize(){
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);

        return size;
    }

}
