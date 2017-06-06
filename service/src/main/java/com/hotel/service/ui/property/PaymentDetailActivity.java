package com.hotel.service.ui.property;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hotel.service.HotelApp;
import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;
import com.hotel.service.R;
import com.hotel.service.net.ViewProxyImp;
import com.hotel.service.net.module.payment.PayApi;
import com.hotel.service.net.module.payment.model.ReqPaymentDetail;
import com.hotel.service.net.module.payment.model.ResPaymentDetail;
import com.hotel.service.ui.base.BaseFragmentActivity;
import com.hotel.service.ui.custom.HomeListView;
import com.hotel.service.ui.custom.PullToRefreshView;
import com.hotel.service.util.PropertiesUtil;
import com.hotel.service.util.StringUtil;

import javax.inject.Inject;

import butterknife.InjectView;
import retrofit.RetrofitError;
import rx.Observable;

/**
 * 我的缴费记录
 *
 * @author aoxiang
 */
public class PaymentDetailActivity extends BaseFragmentActivity implements PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener {

    private String TAG = ServiceBulletinActivity.class.getCanonicalName();

    @InjectView(R.id.activity_name)
    TextView activityName;

    @InjectView(R.id.return_back)
    ImageView returnBack;

    @InjectView(R.id.content_gv)
    HomeListView activeList;

    @InjectView(R.id.house_name)
    TextView houseName;

    @InjectView(R.id.area)
    TextView area;

    @InjectView(R.id.rent_name)
    TextView rentName;

    @InjectView(R.id.arrearage_fee)
    TextView arrearageFee;

    @InjectView(R.id.process_bar)
    ProgressBar processBar;

    @InjectView(R.id.no_data)
    TextView noData;

    @InjectView(R.id.refreshable_view)
    PullToRefreshView refreshableView;

    @Inject
    PayApi paymentApi;

    // 缴费详情信息数据
    private PaymentDetailAdapter paymentDetailAdapter;

    // 数据列表滑动到下方时，加载一个的布局，用于提示用户数据正在加载
    private boolean firstLoad = true; // 是否第一次读取数据

    private String pIDStr;
    private String houseNameStr;
    private String areaStr;
    private String rentNameStr;
    private String totalScoreStr;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.payment_detail_view);

        HotelApp.get(this).inject(this);

        Intent i = getIntent();

        pIDStr = i.getStringExtra("pID");
        houseNameStr = i.getStringExtra("houseName");
        areaStr = i.getStringExtra("area");
        rentNameStr = i.getStringExtra("rentName");
        totalScoreStr = i.getStringExtra("totalScore");

        initWidget();
    }

    @Override
    protected void onStart() {

        super.onStart();

        paymentDetailAdapter = new PaymentDetailAdapter();

        initPage();
        getPaymentDetailListInfo();

    }

    private void initWidget() {
        activityName.setText(R.string.fee_detail);

        refreshableView.setOnHeaderRefreshListener(this);
        refreshableView.setOnFooterRefreshListener(this);

        houseName.setText(houseNameStr);
        area.setText(areaStr);
        rentName.setText(rentNameStr);
        arrearageFee.setText(totalScoreStr + "元");

        returnBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                finish();
            }
        });

    }

    /**
     * 获取列表
     */
    private void getPaymentDetailListInfo() {

        Log.i(TAG, "刷新列表======================");

        subscription.add(paymentApi.getPaymentDetail(
                new ReqPaymentDetail.Builder()
                        .setP_id(pIDStr)
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
                                Toast.makeText(PaymentDetailActivity.this, "网络问题", Toast.LENGTH_SHORT).show();
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
                .subscribe(this::updatePaymentDetail));
    }

    /**
     * 更新我的缴费详情
     */
    private void updatePaymentDetail(ResPaymentDetail resPaymentDetail) {


        Log.i(TAG, "更新我的缴费记录列表UI=======================");
        totalPage = StringUtil.str2int(resPaymentDetail.s_total);
        nowPage = StringUtil.str2int(resPaymentDetail.s_num);
        nowPage++;

        if (firstLoad) {
            refreshableView.completeNow();
            paymentDetailAdapter.clear();
            paymentDetailAdapter.addAll(resPaymentDetail.getData());
            activeList.setAdapter(paymentDetailAdapter);

        } else {
            paymentDetailAdapter.addAll(resPaymentDetail.getData());
            paymentDetailAdapter.notifyDataSetChanged();
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
//        processBar.setVisibility(View.VISIBLE);
//        activeList.setVisibility(View.GONE);
    }

    /**
     * 翻页加载数据
     */
    private void reloadData() {
        setIsGetData(false);
        firstLoad = false;
        getPaymentDetailListInfo();
    }

    //上拉加载更多
    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        refreshableView.onFooterRefreshComplete();
//        reloadData();
    }

    //下拉刷新
    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        Log.i(TAG, "下拉刷新======================");
        initPage();
        getPaymentDetailListInfo();
    }


    public class PaymentDetailAdapter extends QuickAdapter<ResPaymentDetail.PaymentDetail> {

        public PaymentDetailAdapter() {
            super(PaymentDetailActivity.this, R.layout.payment_detail_list);
        }

        @Override
        protected void convert(BaseAdapterHelper helper, ResPaymentDetail.PaymentDetail item) {

            helper.setText(R.id.payment_id, item.paymentId);
            helper.setText(R.id.payment_name, item.paymentTypeName);
            helper.setText(R.id.fee, item.cost + "元");

        }
    }


}
