package com.hotel.service.ui.property;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hotel.service.HotelApp;
import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;
import com.hotel.service.R;
import com.hotel.service.net.ViewProxyImp;
import com.hotel.service.net.module.newinfo.NewInfoListApi;
import com.hotel.service.net.module.newinfo.model.ReqNewInfoList;
import com.hotel.service.net.module.newinfo.model.ResNewInfoList;
import com.hotel.service.ui.base.BaseFragmentActivity;
import com.hotel.service.ui.custom.PullToRefreshView;
import com.hotel.service.util.StringUtil;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.RetrofitError;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by Administrator on 2016/3/7.
 */
public class InformaitionDetail extends BaseFragmentActivity  implements PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener{

    private String TAG = InformaitionDetail.class.getCanonicalName();
    @Inject
    NewInfoListApi newInfoListApi;

    @InjectView(R.id.activity_name)
    TextView activityName;

    @OnClick(R.id.return_back)
    public void back() {
        finish();
    }

    @InjectView(R.id.process_bar)
    ProgressBar processBar;

    @InjectView(R.id.no_data)
    TextView noData;

    @InjectView(R.id.refreshable_view)
    PullToRefreshView refreshableView;

    @InjectView(R.id.content_gv)
    ListView infoDetail;

    private boolean firstLoad = true; // 是否第一次读取数据
    private InforDetailAdapter inforDetailAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.information_detail);
        HotelApp.get(this).inject(this);
        initWidget();
        initPage();
        inforDetailAdapter = new InforDetailAdapter();
        getCultureInfo();

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void initWidget() {

        activityName.setText(getIntent().getStringExtra("name"));

        refreshableView.setOnHeaderRefreshListener(this);
        refreshableView.setOnFooterRefreshListener(this);

        infoDetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ResNewInfoList.InfoListTypeList tm = inforDetailAdapter.getItem(position);
                Intent i = new Intent(InformaitionDetail.this, InformationContent.class);
                i.putExtra("createTime", tm.createTime);
                i.putExtra("details", tm.details);
                i.putExtra("title", tm.title);
                i.putExtra("linkAddress", tm.linkAddress);
                i.putExtra("detailsType", tm.detailsType);
                startActivity(i);
            }
        });


    }

    /**
     * 获取列表信息
     */
    private void getCultureInfo() {

        subscription.add(newInfoListApi.getNewInfoListType(
                new ReqNewInfoList.Builder()
                        .setUserId("")
                        .setTypeId(getIntent().getStringExtra("typeId"))
                        .setIndex(String.valueOf(nowPage))
                        .setNum(String.valueOf(countPage))
                        .build(),
                new ViewProxyImp(new ViewProxyImp.Builder()
                        .setIsSecondLoad(!firstLoad)
                        .setSuccessView(infoDetail)
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
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (throwable instanceof RetrofitError) {
                            switch (((RetrofitError) throwable).getKind()) {
                                case NETWORK:
                                    Toast.makeText(InformaitionDetail.this, "网络问题", Toast.LENGTH_SHORT).show();
                                    break;
                                case CONVERSION:
                                    break;
                                case HTTP:
                                    break;
                                case UNEXPECTED:
                                    break;
                            }
                        }
                    }
                })
                .onErrorResumeNext(Observable.empty())
                .subscribe(this::getDateEvent));
    }

    /**
     * 更新列表信息
     */
    private void getDateEvent(ResNewInfoList resNewInfoList) {

        totalPage = StringUtil.str2int(resNewInfoList.s_total);
        nowPage = StringUtil.str2int(resNewInfoList.s_num);
        nowPage++;

//        if(totalPage==0){
//
//            infoDetail.setVisibility(View.GONE);
//            noData.setVisibility(View.VISIBLE);
//            processBar.setVisibility(View.GONE);
//        }


        if (firstLoad) {
            refreshableView.completeNow();
            inforDetailAdapter.clear();
            inforDetailAdapter.addAll(resNewInfoList.getData());
            infoDetail.setAdapter(inforDetailAdapter);
        } else {
            inforDetailAdapter.addAll(resNewInfoList.getData());
            inforDetailAdapter.notifyDataSetChanged();
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
        infoDetail.setVisibility(View.GONE);
    }

    /**
     * 翻页加载数据
     */
    private void reloadData() {
        setIsGetData(false);
        firstLoad = false;

        getCultureInfo();
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
        getCultureInfo();
    }

    //配置adpter
    public class InforDetailAdapter extends QuickAdapter<ResNewInfoList.InfoListTypeList> {

        public InforDetailAdapter() {
            super(InformaitionDetail.this, R.layout.info_detail_item);
        }

        @Override
        protected void convert(BaseAdapterHelper helper, ResNewInfoList.InfoListTypeList item) {
            helper.setText(R.id.info_title, item.title);
//            helper.setText(R.id.detail_text, item.details);

        }
    }

}
