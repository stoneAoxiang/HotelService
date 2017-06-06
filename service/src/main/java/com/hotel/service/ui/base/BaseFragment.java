package com.hotel.service.ui.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.hotel.service.logic.PopMenuCallback;
import com.hotel.service.ui.SelectpropertyPopMenu;
import com.hotel.service.util.ConstantValue;
import com.hotel.service.volley.RequestManager;

import butterknife.ButterKnife;
import rx.subscriptions.CompositeSubscription;

public class BaseFragment extends Fragment {

    protected CompositeSubscription subscription = new CompositeSubscription();

    private String TAG = BaseFragment.class.getCanonicalName();

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

    /**
     * 区县名称
     */
    public String countyName;

    /**
     * 选择小区
     */
    public TextView selectProperty;

    public ImageView pressFlag;

    /**
     * 弹出窗
     */
    public SelectpropertyPopMenu selectpropertyPopMenu;



    //功能标题名
    public TextView tabName;

    private PopMenuCallback pcb;

    /**
     * 小区ID
     */
//	public String commuityID;

    /**
     * 推荐类型ID
     */
    public String recommendTypeID;

    /**
     * 广告位ID 0：首页主广告 1：生活超市 2：励生活团
     */
    public String advertisePositionID;

    /**
     * 二级下拉选择值名称
     */
    public String secondSpinnerTypeName = "";

    protected FragmentActivity activity;

    private Handler loadingHandler;

    private BaseFunction baseFunction;

    @Override
    public void onDetach() {
        super.onDetach();
        subscription.unsubscribe();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        activity = getActivity();

        baseFunction = new BaseFunction(activity);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "注册RX=============================" + getClass().getName());
        subscription = new CompositeSubscription();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);

        subscription = new CompositeSubscription();

    }

    @Override
    public void onStop() {
        super.onStop();
        RequestManager.cancelAll(this);
        subscription.unsubscribe();
        Log.i(TAG, "移除注册RX=============================" + getClass().getName());
    }

    protected void executeRequest(Request<?> request) {
        RequestManager.addRequest(request, this);
    }

    protected Response.ErrorListener errorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity, "网路异常或获取数据有问题", Toast.LENGTH_LONG).show();
            }
        };
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

}
