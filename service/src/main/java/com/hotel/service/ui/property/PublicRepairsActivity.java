package com.hotel.service.ui.property;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;
import com.hotel.service.HotelApp;
import com.hotel.service.R;
import com.hotel.service.net.ViewProxyImp;
import com.hotel.service.net.module.repairs.PublicRepairsApi;
import com.hotel.service.net.module.repairs.model.ReqPublicRepairs;
import com.hotel.service.net.module.repairs.model.ResPublicRepairs;
import com.hotel.service.ui.base.BaseFragmentActivity;
import com.hotel.service.ui.custom.HomeGridView;
import com.hotel.service.ui.custom.PullToRefreshView;
import com.hotel.service.util.ConstantValue;
import com.hotel.service.util.PropertiesUtil;
import com.hotel.service.util.StringUtil;
import com.hotel.service.util.Tools;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemClick;
import retrofit.RetrofitError;
import rx.Observable;
import rx.functions.Action1;

/**
 * 报事报修主页面
 * Created by hyz on 2016/1/13.
 */
public class PublicRepairsActivity extends BaseFragmentActivity implements PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener {


    private String TAG = PublicRepairsActivity.class.getCanonicalName();

    @Inject
    PublicRepairsApi publicRepairsApi;

    @InjectView(R.id.activity_name)
    TextView activityName;

    @OnClick(R.id.return_back)
    public void back() {
        finish();
    }

    @InjectView(R.id.write_edit)
    ImageView addCs;

    @InjectView(R.id.process_bar)
    ProgressBar processBar;

    @InjectView(R.id.no_data)
    TextView noData;

    @InjectView(R.id.order_mode)
    RadioGroup orderMode;

    private RadioButton waitHandleButton;
    private RadioButton inHandleButton;
    private RadioButton handledButton;

    private QueryType selectTab = QueryType.waitHandle;

    private enum QueryType {
        waitHandle, inHandle, handled;
    }

    //保修状态
    private String progression;

    // 数据列表滑动到下方时，加载一个的布局，用于提示用户数据正在加载
    private View loadingView;

    @InjectView(R.id.refreshable_view)
    PullToRefreshView refreshableView;

    @InjectView(R.id.handle_grid)
    HomeGridView handleGrid;

    private RepairsAdapter repairsAdapter;

    private boolean firstLoad = true; // 是否第一次读取数据

    private Intent i;

    private String depNum;   //判断是否为客户部的值

    /**
     * 保存图片字符串
     */
    private String[] picArray;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_property_repairs);

        HotelApp.get(this).inject(this);
        initWidget();


    }

    @Override
    public void onStart() {
        super.onStart();
        initPage();
        getRepqirsInfo(selectTab);
    }

    private void initWidget() {

        activityName.setText(R.string.public_repairs);

        handleGrid = (HomeGridView) findViewById(R.id.handle_grid);
        refreshableView.setOnHeaderRefreshListener(this);
        refreshableView.setOnFooterRefreshListener(this);

        repairsAdapter = new RepairsAdapter();

        addCs.setVisibility(View.VISIBLE);
        addCs.setImageResource(R.mipmap.add);
        addCs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PublicRepairsActivity.this, PublicRepairsAddActivity.class);
                startActivity(intent);

            }
        });




        orderMode.setOnCheckedChangeListener(rglistener);
        // 三个分状态bt
        waitHandleButton = (RadioButton) findViewById(R.id.all_order_button);
        inHandleButton = (RadioButton) findViewById(R.id.complete_order_button);
        handledButton = (RadioButton) findViewById(R.id.incomplete_order_button);
    }

    RadioGroup.OnCheckedChangeListener rglistener = new RadioGroup.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            if (checkedId == waitHandleButton.getId()) {
                selectTab = QueryType.waitHandle;

                initPage();
                getRepqirsInfo(selectTab);


            } else if (checkedId == inHandleButton.getId()) {
                selectTab = QueryType.inHandle;

                initPage();
                getRepqirsInfo(selectTab);


            } else if (checkedId == handledButton.getId()) {
                selectTab = QueryType.handled;

                initPage();
                getRepqirsInfo(selectTab);


            }
        }
    };

    @OnItemClick(R.id.handle_grid)
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        ResPublicRepairs.ReparisInfoList tm = repairsAdapter.getItem(position);
        i = new Intent(this, PublicRepairsInfoActivity.class);


        i.putExtra("repairId", tm.repairId);
        i.putExtra("createTime", tm.createTime);
        i.putExtra("progression", tm.progression);
        i.putExtra("repairsAddress", tm.repairsAddress);
        i.putExtra("repairEmpName", tm.repairEmpName);
        i.putExtra("repairsContent", tm.repairsContent);
        i.putExtra("repairPersonnelName",tm.repairPersonnelName);
        i.putExtra("repairPersonnelId",tm.repairPersonnelId);
        i.putExtra("repairManName",tm.repairManName);
        i.putExtra("appointDep", tm.appointDep);        //指派部门id
        i.putExtra("dep", depNum);
        i.putExtra("iconurl", tm.iconurl);
        startActivity(i);
    }

    /**
     * 获取列表信息
     */
    private void getRepqirsInfo(QueryType selectTab) {

        String procStatus = "";

        switch (selectTab) {
            case waitHandle:
                procStatus = "0";
                break;
            case inHandle:
                procStatus = "1";
                break;

            case handled:
                procStatus = "3";
                break;

            default:
                break;
        }

        subscription.add(publicRepairsApi.getPublicRepairsInfo(
                new ReqPublicRepairs.Builder()
                        .setProgression(procStatus)
                        .setUserId(PropertiesUtil.getProperties("userID"))
                        .setIndex(String.valueOf(nowPage))
                        .setNum(String.valueOf(countPage))
                        .build(),

                new ViewProxyImp(new ViewProxyImp.Builder()
                        .setIsSecondLoad(!firstLoad)
                        .setSuccessView(handleGrid)
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
                                    Toast.makeText(PublicRepairsActivity.this, "网络问题", Toast.LENGTH_SHORT).show();
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
                .subscribe(this::getDatePbulicRepairs));
    }

    /**
     * 更新列表信息
     */
    private void getDatePbulicRepairs(ResPublicRepairs resPublicRepairs) {

        //获取页数和总页数
        totalPage = StringUtil.str2int(resPublicRepairs.s_total);
        nowPage = StringUtil.str2int(resPublicRepairs.s_num);
        nowPage++;


        List<ResPublicRepairs.ReparisInfoList> tmp = resPublicRepairs.getData();

        depNum =  resPublicRepairs.Dep;
        Log.i(TAG, "depNum        返回数据 =============" + depNum);
        if (firstLoad) {
            refreshableView.completeNow();
            repairsAdapter.clear();
            repairsAdapter.addAll(tmp);
            handleGrid.setAdapter(repairsAdapter);
        } else {
            repairsAdapter.addAll(tmp);
            repairsAdapter.notifyDataSetChanged();
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
        handleGrid.setVisibility(View.GONE);
    }



    /**
     * 翻页加载数据
     */
    private void reloadData() {
        setIsGetData(false);
        firstLoad = false;
        getRepqirsInfo(selectTab);
    }

    //配置adpter
    public class RepairsAdapter extends QuickAdapter<ResPublicRepairs.ReparisInfoList> {

        public RepairsAdapter() {
            super(PublicRepairsActivity.this, R.layout.repairs_list_item);
        }

        @Override
        protected void convert(BaseAdapterHelper helper, ResPublicRepairs.ReparisInfoList item) {

            if(item.iconurl !=null && item.iconurl.split(";").length > 0 ){

                picArray = item.iconurl.split(";");
            }

            //图片地址，获取，加载
            String imgUrl = picArray[0];
            Picasso.with(context).load(Tools.getPicUrl(imgUrl, ConstantValue.IMG_PRODUCT_DETAIL_WIDTH, ConstantValue.IMG_PRODUCT_DETAIL_HEIGHT))
                    .placeholder(R.mipmap.default_picture)
                    .error(R.mipmap.default_picture)
                    .into((ImageView) helper.getView(R.id.repairs_img));

            helper.setText(R.id.repairs_time, item.createTime);
            helper.setText(R.id.repairs_address, item.repairsAddress);
            helper.setText(R.id.repairs_content, item.repairsContent);


            progression = item.progression;
        }
    }

    //下拉刷新
    @Override
    public void onHeaderRefresh(PullToRefreshView view) {

        initPage();
        getRepqirsInfo(selectTab);
    }

    //上拉加载更多
    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        reloadData();
    }
}
