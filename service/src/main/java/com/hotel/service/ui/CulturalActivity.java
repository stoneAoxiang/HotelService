package com.hotel.service.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hotel.service.HotelApp;
import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;
import com.hotel.service.R;
import com.hotel.service.net.ViewProxyImp;
import com.hotel.service.net.module.cultural.CulturalApi;
import com.hotel.service.net.module.cultural.model.ReqCulturalList;
import com.hotel.service.net.module.cultural.model.ResCulturalList;
import com.hotel.service.ui.base.BaseFragmentActivity;
import com.hotel.service.ui.custom.HomeListView;
import com.hotel.service.ui.custom.PullToRefreshView;
import com.hotel.service.util.ConstantValue;
import com.hotel.service.util.StringUtil;
import com.hotel.service.util.Tools;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.RetrofitError;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by Administrator on 2016/3/7.
 */
public class CulturalActivity extends BaseFragmentActivity implements PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener {

    private String TAG = CulturalActivity.class.getCanonicalName();
    @Inject
    CulturalApi culturalApi;

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
    HomeListView activeList;

    /**
     * 保存图片字符串
     */
    private String[] picArray;

    private boolean firstLoad = true; // 是否第一次读取数据
    private EventAdapter eventAdapter;
    private String typeId;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.work_guide_activity);
        HotelApp.get(this).inject(this);

        initWidget();

        eventAdapter = new EventAdapter();

        initPage();
        getCultureInfo();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void initWidget() {

        activityName.setText("文化活动");

        refreshableView.setOnHeaderRefreshListener(this);
        refreshableView.setOnFooterRefreshListener(this);


//        activeList.setFocusable(true);
//        activeList.setFocusableInTouchMode(true);
        activeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ResCulturalList.CulturalListInfo tm = eventAdapter.getItem(position);
                Intent i = new Intent(CulturalActivity.this, CulturalContentActivity.class);

                i.putExtra("createTime", tm.createTime);
                i.putExtra("content", tm.content);
                i.putExtra("title", tm.title);
                i.putExtra("imgsDesc", tm.imgsDesc);
                startActivity(i);
            }
        });
    }

    /**
     * 获取列表信息
     */
    private void getCultureInfo() {

        Log.i(TAG, "是否获得数据 11 =============");

        subscription.add(culturalApi.getCulturalList(
                new ReqCulturalList.Builder()
                        .setUserId("")
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
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (throwable instanceof RetrofitError) {
                            switch (((RetrofitError) throwable).getKind()) {
                                case NETWORK:
                                    Toast.makeText(CulturalActivity.this, "网络问题", Toast.LENGTH_SHORT).show();
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
    private void getDateEvent(ResCulturalList resCulturalList) {

        totalPage = StringUtil.str2int(resCulturalList.s_total);
        nowPage = StringUtil.str2int(resCulturalList.s_num);
        nowPage++;

        if (firstLoad) {
            refreshableView.completeNow();
            eventAdapter.clear();
            eventAdapter.addAll(resCulturalList.getData());
            activeList.setAdapter(eventAdapter);
        } else {
            eventAdapter.addAll(resCulturalList.getData());
            eventAdapter.notifyDataSetChanged();
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
    public class EventAdapter extends QuickAdapter<ResCulturalList.CulturalListInfo> {

        public EventAdapter() {
            super(CulturalActivity.this, R.layout.info_detail_img_item);
        }

        @Override
        protected void convert(BaseAdapterHelper helper, ResCulturalList.CulturalListInfo item) {


            if (item.imgsDesc != null && item.imgsDesc.split(";").length > 0) {

                picArray = item.imgsDesc.split(";");
            }

            //图片地址，获取，加载
            String imgUrl = picArray[0];
            Picasso.with(context).load(Tools.getPicUrl(imgUrl, ConstantValue.IMG_PRODUCT_DETAIL_WIDTH, ConstantValue.IMG_PRODUCT_DETAIL_HEIGHT))
                    .placeholder(R.mipmap.default_picture)
                    .error(R.mipmap.default_picture)
                    .into((ImageView) helper.getView(R.id.info_img));

            helper.setText(R.id.info_title, item.title);
//            helper.setText(R.id.info_time, item.createTime);
//            helper.setText(R.id.detail_text, Config.SERVER_URL_FULL + item.content);

        }
    }

}
