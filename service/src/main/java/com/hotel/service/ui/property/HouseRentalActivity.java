package com.hotel.service.ui.property;

import android.content.Intent;
import android.os.Bundle;
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
import com.hotel.service.net.module.house.HouseRentalApi;
import com.hotel.service.net.module.house.model.ReqRentalList;
import com.hotel.service.net.module.house.model.ResRentalList;
import com.hotel.service.ui.base.BaseFragmentActivity;
import com.hotel.service.ui.custom.PullToRefreshView;
import com.hotel.service.util.ConstantValue;
import com.hotel.service.util.StringUtil;
import com.hotel.service.util.Tools;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnItemClick;
import retrofit.RetrofitError;
import rx.Observable;

/**
 * Created by aoxiang on 2016/3/15.
 * 房屋出租
 */
public class HouseRentalActivity  extends BaseFragmentActivity implements PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener {

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
    HouseRentalApi houseRentalApi;

    // 我的抽奖信息数据
    private RentalListAdapter rentalListAdapter;

    // 数据列表滑动到下方时，加载一个的布局，用于提示用户数据正在加载
    private boolean firstLoad = true; // 是否第一次读取数据

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.house_rental_view);

        HotelApp.get(this).inject(this);

        initWidget();
    }

    @Override
    protected void onStart() {

        super.onStart();

        rentalListAdapter = new RentalListAdapter();

        initPage();
        getRentalListInfo();

    }

    private void initWidget() {
        activityName.setText(R.string.house_rental);

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
        ResRentalList.RentalList tm = rentalListAdapter.getItem(arg2);

        Intent intent=new Intent(this, HouseRentalDetailActivity.class);
        intent.putExtra("rental", tm.rental);
        intent.putExtra("iconurl", tm.iconurl);
        intent.putExtra("title", tm.title);
        intent.putExtra("auditTime", tm.auditTime);
        intent.putExtra("size", tm.size);
        intent.putExtra("rent", tm.rent);
        intent.putExtra("floor", tm.floor);
        intent.putExtra("payment", tm.payment);
        intent.putExtra("employeeName", tm.employeeName);
        intent.putExtra("houseName", tm.houseName);
        intent.putExtra("phone", tm.phone);
        intent.putExtra("contenturl", tm.contenturl);
        intent.putExtra("features", tm.features);

        startActivity(intent);
    }

    /**
     * 获取抽奖列表
     */
    private void getRentalListInfo() {

        subscription.add(houseRentalApi.getRentalList(
                new ReqRentalList.Builder()
                        .setType("")
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
                                Toast.makeText(HouseRentalActivity.this, "网络问题", Toast.LENGTH_SHORT).show();
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
                .subscribe(this::updateRentalListInfo));
    }

    /**
     * 更新房屋租凭列表UI
     */
    private void updateRentalListInfo(ResRentalList resRentalList) {

        totalPage = StringUtil.str2int(resRentalList.s_total);
        nowPage = StringUtil.str2int(resRentalList.s_num);
        nowPage++;

        if (firstLoad) {
            refreshableView.completeNow();
            rentalListAdapter.clear();
            rentalListAdapter.addAll(resRentalList.getData());
            activeList.setAdapter(rentalListAdapter);
        } else {
            rentalListAdapter.addAll(resRentalList.getData());
            rentalListAdapter.notifyDataSetChanged();
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

        getRentalListInfo();
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
        getRentalListInfo();
    }

    public class RentalListAdapter extends QuickAdapter<ResRentalList.RentalList> {

        public RentalListAdapter() {
            super(HouseRentalActivity.this, R.layout.house_rental_list);
        }

        @Override
        protected void convert(BaseAdapterHelper helper, ResRentalList.RentalList item) {

            helper.setText(R.id.rental_title, item.title);

            if(item.rental.equals("0")){
                helper.setText(R.id.rent, item.rent + "元/月");

            }else if(item.rental.equals("1")){
                helper.setText(R.id.rent, item.rent + "元");
            }

            helper.setText(R.id.project_area, item.project);

            helper.setText(R.id.audit_time, item.auditTime);

            String imgUrl = item.iconurl;
            Picasso.with(context).load(Tools.getPicUrl(imgUrl, ConstantValue.IMG_PRODUCT_WIDTH, ConstantValue.IMG_PRODUCT_HEIGHT))
                    .placeholder(R.mipmap.default_picture)
                    .error(R.mipmap.default_picture)
                    .into((ImageView) helper.getView(R.id.rental_img));


        }
    }

}