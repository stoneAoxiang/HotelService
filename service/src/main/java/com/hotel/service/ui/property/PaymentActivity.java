package com.hotel.service.ui.property;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
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
import com.hotel.service.net.module.payment.PayApi;
import com.hotel.service.net.module.payment.model.ReqPaymentList;
import com.hotel.service.net.module.payment.model.ResPaymentList;
import com.hotel.service.ui.base.BaseFragmentActivity;
import com.hotel.service.ui.custom.PullToRefreshView;
import com.hotel.service.ui.custom.PullToRefreshView.OnFooterRefreshListener;
import com.hotel.service.ui.custom.PullToRefreshView.OnHeaderRefreshListener;
import com.hotel.service.util.PropertiesUtil;
import com.hotel.service.util.StringUtil;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnItemClick;
import retrofit.RetrofitError;
import rx.Observable;

/**
 * 我的缴费记录
 *
 * @author aoxiang
 */
public class PaymentActivity extends BaseFragmentActivity implements OnHeaderRefreshListener, OnFooterRefreshListener {

    private String TAG = ServiceBulletinActivity.class.getCanonicalName();

    @InjectView(R.id.activity_name)
    TextView activityName;

    @InjectView(R.id.return_back)
    ImageView returnBack;

    @InjectView(R.id.content_gv)
    ListView activeList;

    @InjectView(R.id.house_name)
    TextView houseName;

    @InjectView(R.id.area)
    TextView area;

    @InjectView(R.id.rent_name)
    TextView rentName;

    @InjectView(R.id.process_bar)
    ProgressBar processBar;

    @InjectView(R.id.no_data)
    TextView noData;

    @InjectView(R.id.refreshable_view)
    PullToRefreshView refreshableView;

    @Inject
    PayApi paymentApi;

    // 我的抽奖信息数据
    private PaymentListAdapter paymentListAdapter;

    // 数据列表滑动到下方时，加载一个的布局，用于提示用户数据正在加载
    private boolean firstLoad = true; // 是否第一次读取数据

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.my_payment_view);

        HotelApp.get(this).inject(this);

        initWidget();
    }

    @Override
    protected void onStart() {

        super.onStart();

        paymentListAdapter = new PaymentListAdapter();

        initPage();
        getBulletinListInfo();

    }

    private void initWidget() {
        activityName.setText(R.string.fee_record);

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
        ResPaymentList.PaymentList tm = paymentListAdapter.getItem(arg2);

        Intent intent=new Intent(this, PaymentDetailActivity.class);
        intent.putExtra("pID", tm.p_id);
        intent.putExtra("totalScore", tm.p_totalcost);
        intent.putExtra("houseName", houseName.getText().toString());
        intent.putExtra("area", area.getText().toString());
        intent.putExtra("rentName", rentName.getText().toString());
        startActivity(intent);
    }

    /**
     * 获取抽奖列表
     */
    private void getBulletinListInfo() {

        subscription.add(paymentApi.getPaymentList(
                new ReqPaymentList.Builder()
                        .setUserId(PropertiesUtil.getProperties("userID"))
                        .setIndex(String.valueOf(nowPage))
                        .setNum(String.valueOf(countPage))
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
                                Toast.makeText(PaymentActivity.this, "网络问题", Toast.LENGTH_SHORT).show();
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
                .subscribe(this::updatePaymentListInfo));
    }

    /**
     * 更新我的缴费记录列表UI
     */
    private void updatePaymentListInfo(ResPaymentList resPaymentList) {

        if(resPaymentList.result.equals("200")){
//            intent.putExtra("houseName", String.format(getResources().getString(R.string.house_name), houseName.getText().toString()));
//            intent.putExtra("area", String.format(getResources().getString(R.string.area), area.getText().toString()));
//            intent.putExtra("rentName", String.format(getResources().getString(R.string.rent_name), rentName.getText().toString()));


            houseName.setText(getString(R.string.house_name, resPaymentList.houseName));
            area.setText(getString(R.string.area, resPaymentList.area));
            rentName.setText(getString(R.string.rent_name, resPaymentList.tenantName));
        }else{
            return;
        }

        Log.i(TAG, "更新我的缴费记录列表UI=======================");
        totalPage = StringUtil.str2int(resPaymentList.s_total);
        nowPage = StringUtil.str2int(resPaymentList.s_num);
        nowPage++;

        if (firstLoad) {
            refreshableView.completeNow();
            paymentListAdapter.clear();
            paymentListAdapter.addAll(resPaymentList.getData());
            activeList.setAdapter(paymentListAdapter);
        } else {
            paymentListAdapter.addAll(resPaymentList.getData());
            paymentListAdapter.notifyDataSetChanged();
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

        getBulletinListInfo();
    }

    //上拉加载更多
    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        reloadData();
    }

    //下拉刷新
    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        Log.i(TAG, "下拉刷新======================");
        initPage();
        getBulletinListInfo();
    }

    public class PaymentListAdapter extends QuickAdapter<ResPaymentList.PaymentList> {

        public PaymentListAdapter() {
            super(PaymentActivity.this, R.layout.my_payment_list);
        }

        @Override
        protected void convert(BaseAdapterHelper helper, ResPaymentList.PaymentList item) {

            helper.setText(R.id.payment_id, item.p_id);
            helper.setText(R.id.payment_time, item.p_date);
            helper.setText(R.id.fee, item.p_totalcost+"元");

        }
    }

}
