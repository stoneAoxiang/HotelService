package com.hotel.service.ui.user;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hotel.service.HotelApp;
import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;
import com.hotel.service.R;
import com.hotel.service.net.ViewProxyImp;
import com.hotel.service.net.module.userInfo.UserInfoApi;
import com.hotel.service.net.module.userInfo.model.ReqRecieverAddress;
import com.hotel.service.net.module.userInfo.model.ResReciverAddress;
import com.hotel.service.ui.base.BaseFragmentActivity;
import com.hotel.service.ui.custom.PullToRefreshView;
import com.hotel.service.util.DialogHelper;
import com.hotel.service.util.PropertiesUtil;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemClick;
import retrofit.RetrofitError;
import rx.Observable;

public class RecieverAddressManagerActivity extends BaseFragmentActivity  implements PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener {

    private String TAG = RecieverAddressManagerActivity.class.getCanonicalName();

    @InjectView(R.id.activity_name)
    TextView activityName;

    @InjectView(R.id.return_back)
    ImageView returnBack;

    @InjectView(R.id.content_gv)
    ListView activeList;

    @InjectView(R.id.process_bar)
    ProgressBar processBar;

    @InjectView(R.id.no_data)
    TextView noData;

    @OnClick(R.id.return_back)
    public void back() {
        finish();
    }

    @InjectView(R.id.refreshable_view)
    PullToRefreshView refreshableView;

    // 地址信息数据
    private RecieverAddressManageListAdapter recieverAddressAdapter;

    private boolean firstLoad = true; // 是否第一次读取数据

    private enum operateType {
        C, R, U, D;
    }

    private operateType operateFlag;

    private static final int MODIFY_REQUEST_CODE = 0;
    private static final int ADD_REQUEST_CODE = 1;

    private String flag;

    @Inject
    UserInfoApi userInfoApi;

    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        reloadData();
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        initPage();
        getAddressInfoData();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.reciever_address_manage_view);

        HotelApp.get(this).inject(this);

        flag = getIntent().getStringExtra("flag");
        initWidget();

    }

    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        initPage();
        processBar.setVisibility(View.VISIBLE);

        recieverAddressAdapter = new RecieverAddressManageListAdapter();
        getAddressInfoData();
    }

    @OnClick(R.id.return_back)
    public void returnBackClick() {
        finish();
    }

    private void initWidget() {
        activityName.setText("管理收货地址");

        refreshableView.setOnHeaderRefreshListener(this);
        refreshableView.setOnFooterRefreshListener(this);

        returnBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                finish();
            }
        });


    }

    @OnItemClick(R.id.content_gv)
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        ResReciverAddress.PushBuyersList tm = recieverAddressAdapter.getItem(arg2);

        Intent intent = new Intent();

        intent.putExtra("recevieAddress", tm.address);
        intent.putExtra("recevieName", tm.name);
        intent.putExtra("recevieTel", tm.phone);
        setResult(RESULT_OK, intent);
        finish();
    }


    /**
     * 增加地址
     */
    @OnClick(R.id.submit_add)
    public void addAddressInfo() {
        operateFlag = operateType.C;

        Intent i = new Intent(this, AddRecieverAddressActivity.class);

        startActivityForResult(i, ADD_REQUEST_CODE);

    }

    /**
     * 修改地址
     */
    public void modifyAddressInfo(ResReciverAddress.PushBuyersList item) {
        operateFlag = operateType.U;

        Intent i = new Intent(this, ModifyRecieverAddressActivity.class);
        i.putExtra("recevieAddress", item.address);
        i.putExtra("recevieName", item.name);
        i.putExtra("recevieTel", item.phone);
        i.putExtra("isDefault", item.isDefault);
        i.putExtra("addressID", item.id);

        startActivityForResult(i, MODIFY_REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == MODIFY_REQUEST_CODE || requestCode == ADD_REQUEST_CODE) && resultCode == RESULT_OK) {
            initPage();
            getAddressInfoData();
        }
    }

    /**
     * 删除地址
     *
     * @param noticeID
     */
    public void delAddressInfo(final String noticeID) {
        DialogHelper.Confirm(this, R.string.del_service, R.string.affirm_delete, R.string.yes,
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog,
                                        int which) {
                        delRequest(noticeID);
                    }
                }, R.string.no, null, false);


    }

    /**
     * 设置默认地址
     */
    public void setDefaultChecked(ResReciverAddress.PushBuyersList item, boolean isCheck) {
        operateFlag = operateType.U;

        subscription.add(userInfoApi.recieverAddressManager(
                new ReqRecieverAddress.Builder()
                        .setUserid(PropertiesUtil.getProperties("userID"))
                        .setType("U")
                        .setName(item.name)
                        .setPhone(item.phone)
                        .setAddress(item.address)
                        .setIsDefault(isCheck ? "d" : "")
                        .setId(item.id)
                        .build(),
                null)
                .doOnError(throwable -> {
                    if (throwable instanceof RetrofitError) {
                        switch (((RetrofitError) throwable).getKind()) {
                            case NETWORK:
                                Toast.makeText(RecieverAddressManagerActivity.this, "网络问题", Toast.LENGTH_SHORT).show();
                                break;
                            case CONVERSION:
                                break;
                            case HTTP:
                                break;
                            case UNEXPECTED:
                                break;
                        }
                    }
                })
                .onErrorResumeNext(Observable.empty())
                .subscribe(this::resDefaultChecked));

    }

    /**
     * 设置默认收货地址响应信息
     */
    private void resDefaultChecked(ResReciverAddress resInfo) {
        if (resInfo.result.equals("200")) {
            Toast.makeText(RecieverAddressManagerActivity.this,
                    "设置成功", Toast.LENGTH_SHORT).show();

            initPage();
            getAddressInfoData();
        } else {
            Toast.makeText(RecieverAddressManagerActivity.this,
                    "设置失败", Toast.LENGTH_SHORT).show();
        }

    }

    private void delRequest(String id) {
        operateFlag = operateType.D;

        subscription.add(userInfoApi.recieverAddressManager(
                new ReqRecieverAddress.Builder()
                        .setUserid(PropertiesUtil.getProperties("userID"))
                        .setType("D")
                        .setId(id)
                        .build(),
                null)
                .doOnError(throwable -> {
                    if (throwable instanceof RetrofitError) {
                        switch (((RetrofitError) throwable).getKind()) {
                            case NETWORK:
                                Toast.makeText(RecieverAddressManagerActivity.this, "网络问题", Toast.LENGTH_SHORT).show();
                                break;
                            case CONVERSION:
                                break;
                            case HTTP:
                                break;
                            case UNEXPECTED:
                                break;
                        }
                    }
                })
                .onErrorResumeNext(Observable.empty())
                .subscribe(this::delRequestInfo));


    }

    /**
     * 删除收货地址响应信息
     */
    private void delRequestInfo(ResReciverAddress resInfo) {
        if (resInfo.result.equals("200")) {
            Toast.makeText(RecieverAddressManagerActivity.this,
                    "删除地址成功", Toast.LENGTH_SHORT).show();

            initPage();
            getAddressInfoData();
        } else {
            Toast.makeText(RecieverAddressManagerActivity.this,
                    "删除地址失败", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 获取收货地址信息
     */
    private void getAddressInfoData() {
        operateFlag = operateType.R;

        subscription.add(userInfoApi.recieverAddressManager(
                new ReqRecieverAddress.Builder()
                        .setUserid(PropertiesUtil.getProperties("userID"))
                        .setType("R")
                        .build(),
                new ViewProxyImp(new ViewProxyImp.Builder()
                        .setIsSecondLoad(!firstLoad)
                        .setSuccessView(activeList)
                        .setEmptyView(noData)
                        .setProgressView(processBar)
                        .build()) {
                    @Override
                    public void onNoMore() {
                        refreshableView.setAllowToLoadMore(false);
                        refreshableView.onFooterRefreshComplete();
                    }

                    @Override
                    public void onFailed() {
                        super.onFailed();
                        refreshableView.onFooterRefreshComplete();
                        refreshableView.onHeaderRefreshComplete();
                    }
                })
                .doOnError(throwable -> {
                    if (throwable instanceof RetrofitError) {
                        switch (((RetrofitError) throwable).getKind()) {
                            case NETWORK:
                                Toast.makeText(RecieverAddressManagerActivity.this, "网络问题", Toast.LENGTH_SHORT).show();
                                break;
                            case CONVERSION:
                                break;
                            case HTTP:
                                break;
                            case UNEXPECTED:
                                break;
                        }
                    }
                })
                .onErrorResumeNext(Observable.empty())
                .subscribe(this::updateAddressInfo));

    }

    /**
     * 更新收货地址记录列表UI
     */
    private void updateAddressInfo(ResReciverAddress resInfo) {

        Log.i(TAG, "更新我的缴费记录列表UI=======================");

        if (firstLoad) {
            refreshableView.completeNow();
            recieverAddressAdapter.clear();
            recieverAddressAdapter.addAll(resInfo.getData());
            activeList.setAdapter(recieverAddressAdapter);
        } else {
            recieverAddressAdapter.addAll(resInfo.getData());
            recieverAddressAdapter.notifyDataSetChanged();
            refreshableView.onFooterRefreshComplete();
        }
    }

    /**
     * 初始化翻页值
     */
    private void initPage() {

        nowPage = 1;
        refreshableView.setAllowToLoadMore(true);
        firstLoad = true;
        processBar.setVisibility(View.VISIBLE);
        activeList.setVisibility(View.GONE);
    }

    /**
     * 翻页加载数据
     */
    private void reloadData() {
        setIsGetData(false);
        firstLoad = false;

        getAddressInfoData();
    }

    public class RecieverAddressManageListAdapter extends QuickAdapter<ResReciverAddress.PushBuyersList> {

        public RecieverAddressManageListAdapter() {
            super(RecieverAddressManagerActivity.this, R.layout.reciever_address_manage_list);
        }

        @Override
        protected void convert(BaseAdapterHelper helper, ResReciverAddress.PushBuyersList item) {


            TextView modifyAddress = helper.getView(R.id.modify_address);
            TextView delAddress = helper.getView(R.id.del_address);
            CheckBox defaultChecked = helper.getView(R.id.default_checked);

            helper.setText(R.id.id, item.id);
            helper.setText(R.id.name, item.name);
            helper.setText(R.id.phone, item.phone);
            helper.setText(R.id.address, item.address);


            if (null == flag || flag.equals("")) {
                helper.setChecked(R.id.default_checked, item.isDefault.equals("d") ? true : false);

            } else {
                helper.setVisible(R.id.default_checked, false);
                helper.setVisible(R.id.default_tv, false);
            }

            modifyAddress.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    modifyAddressInfo(item);


                }
            });

            defaultChecked.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    setDefaultChecked(item, defaultChecked.isChecked());


                }
            });

            delAddress.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    delAddressInfo(item.id);
                }
            });

        }
    }


}
