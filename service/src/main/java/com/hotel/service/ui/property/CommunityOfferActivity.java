package com.hotel.service.ui.property;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;
import com.hotel.service.HotelApp;
import com.hotel.service.R;
import com.hotel.service.net.ViewProxyImp;
import com.hotel.service.net.module.positon.OfferApi;
import com.hotel.service.net.module.positon.model.ReqOfferList;
import com.hotel.service.net.module.positon.model.ReqOfferType;
import com.hotel.service.net.module.positon.model.ResOfferList;
import com.hotel.service.net.module.positon.model.ResOfferType;
import com.hotel.service.ui.base.BaseFragmentActivity;
import com.hotel.service.ui.custom.PullToRefreshView;
import com.hotel.service.ui.widget.SinglePopUnCheckBoxWindowHelper;
import com.hotel.service.util.PropertiesUtil;
import com.hotel.service.util.StringUtil;

import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemClick;
import retrofit.RetrofitError;
import rx.Observable;

/**
 * Created by aoxiang on 2016/3/17.
 * 社区求职
 */
public class CommunityOfferActivity   extends BaseFragmentActivity implements PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener {

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

    @InjectView(R.id.offer_name_layout)
    LinearLayout offerNameLayout;

    @InjectView(R.id.offer_name)
    TextView offerName;

    @InjectView(R.id.offer_draw_flag)
    ImageView offerDrawFlag;

    private String offerId = "";

    @Inject
    OfferApi offerApi;

    // 我的抽奖信息数据
    private CommunityOfferAdapter communityOfferAdapter;

    private SinglePopUnCheckBoxWindowHelper offerPopupWindow;

    ResOfferType.TypeLists allType;

    // 数据列表滑动到下方时，加载一个的布局，用于提示用户数据正在加载
    private boolean firstLoad = true; // 是否第一次读取数据

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.community_offer_view);

        HotelApp.get(this).inject(this);

        allType = new ResOfferType.TypeLists();
        allType.id = "";
        allType.typeName = "全部";

        initWidget();
    }

    @Override
    protected void onStart() {

        super.onStart();

        communityOfferAdapter = new CommunityOfferAdapter();

        initPage();
        getBulletinListInfo();

    }

    @OnClick(R.id.offer_draw_flag)
    public void offerSpinner() {
        offerPopupWindow.showAsDropDown(offerNameLayout);
        offerDrawFlag.setSelected(true);
    }

    private void initWidget() {
        activityName.setText(R.string.community_offer);

        refreshableView.setOnHeaderRefreshListener(this);
        refreshableView.setOnFooterRefreshListener(this);

        offerPopupWindow = SinglePopUnCheckBoxWindowHelper.newInstance(this, 1);

        offerPopupWindow.setPopWindowEvent(new SinglePopUnCheckBoxWindowHelper.PopWindowEvent() {
            @Override
            public void onPopWindowSelect(SinglePopUnCheckBoxWindowHelper.DataModel secondModel) {
                String tips = secondModel.name;
                offerName.setText(tips);
                offerId = secondModel.id;
                offerDrawFlag.setSelected(false);

                initPage();
                getBulletinListInfo();

            }

            @Override
            public Observable<List<SinglePopUnCheckBoxWindowHelper.DataModel>> onPopWindowInitList() {

                return offerApi.getOfferType(
                        new ReqOfferType.Builder().build())
                        .doOnNext(menuList -> menuList.TypeList.add(0, allType))
                        .map(menuList -> menuList.TypeList)
                        .flatMap(Observable::from)
                        .map(spinnerList -> new SinglePopUnCheckBoxWindowHelper.DataModel.Builder()
                                .setName(spinnerList.typeName)
                                .setId(spinnerList.id)
                                .build())
                        .toList()
                        .onErrorResumeNext(Observable.empty());
            }
        });

        returnBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                finish();
            }
        });

    }

    @OnItemClick(R.id.content_gv)
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        ResOfferList.RecruitmentsLists tm = communityOfferAdapter.getItem(arg2);

        Intent intent=new Intent(this, CommunityOfferDetailActivity.class);
        intent.putExtra("position", tm.position);
        intent.putExtra("offerTime", tm.createTime);
        intent.putExtra("salary", tm.salary);

        intent.putExtra("companyname", tm.companyName);
        intent.putExtra("workAddress", tm.workAddress);
        intent.putExtra("people_num", tm.peopleNum);
        intent.putExtra("education", tm.education);
        intent.putExtra("workExperience", tm.workExperience);
        intent.putExtra("phone", tm.phone);
        intent.putExtra("workDetails", tm.workDetails);

        startActivity(intent);
    }

    /**
     * 获取求职列表
     */
    private void getBulletinListInfo() {

        subscription.add(offerApi.getOfferList(
                new ReqOfferList.Builder()
                        .setUserId(PropertiesUtil.getProperties("userID"))
                        .setPosition("")
                        .setTypeId(offerId)
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
                                Toast.makeText(CommunityOfferActivity.this, "网络问题", Toast.LENGTH_SHORT).show();
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
                .subscribe(this::updateOfferListInfo));
    }

    /**
     * 更新职位列表UI
     */
    private void updateOfferListInfo(ResOfferList resOfferList) {

        totalPage = StringUtil.str2int(resOfferList.s_total);
        nowPage = StringUtil.str2int(resOfferList.s_num);
        nowPage++;

        if (firstLoad) {
            refreshableView.completeNow();
            communityOfferAdapter.clear();
            communityOfferAdapter.addAll(resOfferList.getData());
            activeList.setAdapter(communityOfferAdapter);
        } else {
            communityOfferAdapter.addAll(resOfferList.getData());
            communityOfferAdapter.notifyDataSetChanged();
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

    public class CommunityOfferAdapter extends QuickAdapter<ResOfferList.RecruitmentsLists> {

        public CommunityOfferAdapter() {
            super(CommunityOfferActivity.this, R.layout.community_offer_list);
        }

        @Override
        protected void convert(BaseAdapterHelper helper, ResOfferList.RecruitmentsLists item) {

            helper.setText(R.id.offer_id, item.id);
            helper.setText(R.id.position, item.position);

            String salaryStr = item.salary;
            helper.setText(R.id.salary, salaryStr.equals("面议") ? salaryStr : salaryStr + "元");

            helper.setText(R.id.work_address, item.workAddress);
            helper.setText(R.id.people_num, context.getString(R.string.job_count, item.peopleNum));
            helper.setText(R.id.work_experience, context.getString(R.string.job_work_experience, item.workExperience));
            helper.setText(R.id.company_name, item.companyName);
            helper.setText(R.id.offer_time, item.createTime);

        }
    }

}
