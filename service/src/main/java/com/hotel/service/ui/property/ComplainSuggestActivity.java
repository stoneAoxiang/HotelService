package com.hotel.service.ui.property;

import android.content.Intent;
import android.os.Bundle;
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
import com.hotel.service.net.module.suggestion.SuggestionApi;
import com.hotel.service.net.module.suggestion.model.ReqSuggestList;
import com.hotel.service.net.module.suggestion.model.ResSuggestList;
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
import butterknife.OnItemClick;
import retrofit.RetrofitError;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by aoxiang on 2016/3/11.
 */
public class ComplainSuggestActivity extends BaseFragmentActivity implements PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener {

    private String TAG = ComplainSuggestActivity.class.getCanonicalName();

    @Inject
    SuggestionApi suggestionApi;

    @InjectView(R.id.return_back)
    ImageView returnBack;

    @InjectView(R.id.activity_name)
    TextView activityName;

    @InjectView(R.id.write_edit)
    ImageView writeEdit;

    @InjectView(R.id.process_bar)
    ProgressBar processBar;

    @InjectView(R.id.no_data)
    TextView noData;

    //选择按钮
    @InjectView(R.id.select_button)
    RadioGroup selectButton;

    @InjectView(R.id.wait_handle)
    RadioButton waitHandleButton;

    @InjectView(R.id.handled_button)
    RadioButton handledButton;

    private QueryType selectTab = QueryType.waitHandleButton;

    private enum QueryType {

        waitHandleButton, handleButton
    }

    @InjectView(R.id.refreshable_view)
    PullToRefreshView refreshableView;

    @InjectView(R.id.handle_grid)
    HomeGridView handleGrid;

    private boolean firstLoad = true; // 是否第一次读取数据

    private SuggestAdapter suggestAdapter;

    private Intent i;

    private String[] picArray;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_complain_suggestion_list);

        HotelApp.get(this).inject(this);

        initWidget();
    }


    @Override
    protected void onStart() {
        super.onStart();
        initPage();
        getQualityInfo(selectTab);
    }

    private void initWidget() {

        activityName.setText(R.string.function_complan);

        String typeNum = PropertiesUtil.getProperties("type");      //登录人员的type
        refreshableView.setOnHeaderRefreshListener(this);
        refreshableView.setOnFooterRefreshListener(this);

        selectButton.setOnCheckedChangeListener(listener);
        suggestAdapter = new SuggestAdapter();

        returnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                finish();
            }
        });

        writeEdit.setVisibility(View.VISIBLE);
        writeEdit.setImageResource(R.mipmap.add);
        writeEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ComplainSuggestActivity.this, ComplainSuggestionAddActivity.class);
                startActivity(intent);

            }
        });

    }

    RadioGroup.OnCheckedChangeListener listener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            if (checkedId == waitHandleButton.getId()) {

                selectTab = QueryType.waitHandleButton;
                initPage();
                getQualityInfo(selectTab);

            } else if (checkedId == handledButton.getId()) {

                selectTab = QueryType.handleButton;
                initPage();
                getQualityInfo(selectTab);
            }
        }
    };

    @OnItemClick(R.id.handle_grid)
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        ResSuggestList.SuggestionList tm = suggestAdapter.getItem(position);
        i = new Intent(this, ComplainSuggestionInfoActivity.class);
        i.putExtra("suggestId", tm.userSuggestionId);
        startActivity(i);
    }

    /**
     * 获取列表信息
     */

    private void getQualityInfo(QueryType selectTab) {

        String procStatus = "";

        switch (selectTab) {

            case waitHandleButton:
                procStatus = "0";
                break;
            case handleButton:
                procStatus = "1";
                break;
        }

        subscription.add(suggestionApi.getSuggestList(
                new ReqSuggestList.Builder()
                        .setUserId(PropertiesUtil.getProperties("userID"))
                        .setStatus(procStatus)
                        .setType("0")
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
                                    Toast.makeText(ComplainSuggestActivity.this, "网络问题", Toast.LENGTH_SHORT).show();
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
                .subscribe(this::getDateQuality));

    }

    /**
     * 更新列表信息
     */
    private void getDateQuality(ResSuggestList resSuggestList) {
        totalPage = StringUtil.str2int(resSuggestList.s_total);
        nowPage = StringUtil.str2int(resSuggestList.s_num);
        nowPage++;

        List<ResSuggestList.SuggestionList> tmp = resSuggestList.getData();

        if (firstLoad) {

            refreshableView.completeNow();
            suggestAdapter.clear();
            suggestAdapter.addAll(resSuggestList.getData());
            handleGrid.setAdapter(suggestAdapter);
//            Log.i(TAG, "返回数据 =============" + tmp.get(0).content);

        } else {
            suggestAdapter.addAll(resSuggestList.getData());
            suggestAdapter.notifyDataSetChanged();
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
        getQualityInfo(selectTab);
    }

    public class SuggestAdapter extends QuickAdapter<ResSuggestList.SuggestionList> {

        public SuggestAdapter() {
            super(ComplainSuggestActivity.this, R.layout.repairs_list_item);
        }

        @Override
        protected void convert(BaseAdapterHelper helper, ResSuggestList.SuggestionList item) {

            if (item.iconurl != null && item.iconurl.split(";").length > 0) {

                picArray = item.iconurl.split(";");

                String imgUrl = picArray[0];
//            Log.i(TAG, "是否执行  222 =============" + imgUrl);       //服务器返回图片压缩显示
                Picasso.with(context).load(Tools.getPicUrl(imgUrl, ConstantValue.IMG_PRODUCT_DETAIL_WIDTH, ConstantValue.IMG_PRODUCT_DETAIL_HEIGHT))
                        .placeholder(R.mipmap.default_picture)
                        .error(R.mipmap.default_picture)
                        .into((ImageView) helper.getView(R.id.repairs_img));
            }

            if (null == picArray) {
                Picasso.with(context).load(R.mipmap.default_picture)            //  默认图片
                        .into((ImageView) helper.getView(R.id.repairs_img));


            }

            helper.setText(R.id.repairs_id, item.userSuggestionId);
            helper.setText(R.id.repairs_time, item.createTime);
//            helper.setText(R.id.repairs_address, item.address);
            helper.setVisible(R.id.repairs_address, false);
            helper.setText(R.id.repairs_content, item.content);
        }


    }

    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        reloadData();
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        initPage();
        getQualityInfo(selectTab);
    }
}
