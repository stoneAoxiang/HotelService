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

import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;
import com.hotel.service.HotelApp;
import com.hotel.service.R;
import com.hotel.service.net.ViewProxyImp;
import com.hotel.service.net.module.bulletin.NoticeApi;
import com.hotel.service.net.module.bulletin.model.ReqBulletinList;
import com.hotel.service.net.module.bulletin.model.ResBulletinList;
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
 * Created by aoxiang on 2016/3/9.
 */
public class ServiceBulletinActivity  extends BaseFragmentActivity implements OnHeaderRefreshListener, OnFooterRefreshListener {

    private String TAG = ServiceBulletinActivity.class.getCanonicalName();

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

    @InjectView(R.id.refreshable_view)
    PullToRefreshView refreshableView;

    @Inject
    NoticeApi bulletinApi;

    // 我的抽奖信息数据
    private BulletinListAdapter bulletinListAdapter;

    // 数据列表滑动到下方时，加载一个的布局，用于提示用户数据正在加载
    private boolean firstLoad = true; // 是否第一次读取数据

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.service_bulletin_view);

        HotelApp.get(this).inject(this);

        initWidget();
    }

    @Override
    protected void onStart() {

        super.onStart();

        bulletinListAdapter = new BulletinListAdapter();

        initPage();
        getBulletinListInfo();

    }

    private void initWidget() {
        activityName.setText(R.string.service_bulletin);

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
        ResBulletinList.BulletinLists tm = bulletinListAdapter.getItem(arg2);

        Intent intent=new Intent(this, BulletinDetailActivity.class);
        intent.putExtra("title", tm.title);
        intent.putExtra("time", tm.createTime);
        intent.putExtra("content", tm.content);
        startActivity(intent);
    }

    /**
     * 获取抽奖列表
     */
    private void getBulletinListInfo() {

        subscription.add(bulletinApi.getBulletinList(
                new ReqBulletinList.Builder()
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
                                Toast.makeText(ServiceBulletinActivity.this, "网络问题", Toast.LENGTH_SHORT).show();
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
                .subscribe(this::updateBulletinListInfo));
    }

    /**
     * 更新服务公告列表UI
     */
    private void updateBulletinListInfo(ResBulletinList resBulletinList) {

        Log.i(TAG, "更新我的抽奖列表UI=======================");
        totalPage = StringUtil.str2int(resBulletinList.s_total);
        nowPage = StringUtil.str2int(resBulletinList.s_num);
        nowPage++;

        if (firstLoad) {
            refreshableView.completeNow();
            bulletinListAdapter.clear();
            bulletinListAdapter.addAll(resBulletinList.getData());
            activeList.setAdapter(bulletinListAdapter);
        } else {
            bulletinListAdapter.addAll(resBulletinList.getData());
            bulletinListAdapter.notifyDataSetChanged();
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
        initPage();
        getBulletinListInfo();
    }

    public class BulletinListAdapter extends QuickAdapter<ResBulletinList.BulletinLists> {

        public BulletinListAdapter() {
            super(ServiceBulletinActivity.this, R.layout.service_bulletin_list);
        }

        @Override
        protected void convert(BaseAdapterHelper helper, ResBulletinList.BulletinLists item) {

            helper.setText(R.id.bulletin_name, item.title);
            helper.setText(R.id.bulletin_id, item.id);
            helper.setText(R.id.bulletin_time, item.sendTime);


            /*if (item.isExchange.equals("0")) {
                lotteryStatus.setTextColor(getResources().getColor(R.color.grey_dark_press));
                lotteryStatus.setBackgroundResource(R.mipmap.lottery_unused);


            } else if (item.isExchange.equals("1")) {
                lotteryStatus.setTextColor(getResources().getColor(R.color.white));
                lotteryStatus.setBackgroundResource(R.mipmap.lottery_used);
            }*/

        }
    }

}
