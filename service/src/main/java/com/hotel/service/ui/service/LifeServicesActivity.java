package com.hotel.service.ui.service;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hotel.service.HotelApp;
import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;
import com.hotel.service.R;
import com.hotel.service.net.ViewProxyImp;
import com.hotel.service.net.module.market.MarketApi;
import com.hotel.service.net.module.market.model.ReqProviderServiceList;
import com.hotel.service.net.module.market.model.ResProviderServiceList;
import com.hotel.service.net.module.menu.MenuApi;
import com.hotel.service.net.module.menu.model.ReqCategoryList;
import com.hotel.service.net.module.menu.model.ReqServiceAddress;
import com.hotel.service.net.module.menu.model.ResCategoryList;
import com.hotel.service.net.module.menu.model.ResServiceAddress;
import com.hotel.service.ui.base.BaseFragmentActivity;
import com.hotel.service.ui.custom.HomeGridView;
import com.hotel.service.ui.custom.PullToRefreshView;
import com.hotel.service.ui.widget.PopWindowHelper;
import com.hotel.service.util.ConstantValue;
import com.hotel.service.util.StringUtil;
import com.hotel.service.util.Tools;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemClick;
import rx.Observable;

/**
 * 生活服务
 */
public class LifeServicesActivity extends BaseFragmentActivity implements PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener {

    private String TAG = LifeServicesActivity.class.getCanonicalName();

    @InjectView(R.id.activity_name)
    TextView activityName;

    @InjectView(R.id.division_one)
    TextView divisionOne;

    @InjectView(R.id.select_product_type)
    TextView selectProductType;

    @InjectView(R.id.content_gv)
    HomeGridView itemGV;

    @InjectView(R.id.no_data)
    TextView noData;

    @InjectView(R.id.select_area)
    TextView selectArea;

    @OnClick(R.id.return_back)
    public void back() {
        finish();
    }

    @Inject
    MarketApi marketApi;

    @Inject
    MenuApi menuApi;

    private ProductListAdapter productListAdapter;

    private boolean firstLoad = true; //是否第一次读取数据

    @InjectView(R.id.process_bar)
    ProgressBar processBar;

    @InjectView(R.id.refreshable_view)
    PullToRefreshView refreshableView;

    public PopWindowHelper serviceTypePopupWindow;
    private PopWindowHelper areaPopupWindow;
//    private SinglePopUnCheckBoxWindowHelper areaPopupWindow;

    ResCategoryList.CategoryList allType;

    ResServiceAddress.AreasList allArea;

    /**
     * 父类型ID
     */
    private String serviceTypeParentId = "";

    /**
     * 子类型名称
     */
    private String serviceTypeChildName = "";

    /**
     * 区域中文名
     */
    private String areaString = "";

    /**
     * 父级区域中文名
     */
    private String parentAreaString = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.life_service_view);
        HotelApp.get(this).inject(this);

        initWidget();

    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

        initPage();
        getProviderService();

        productListAdapter = new ProductListAdapter();

    }

    private void initWidget() {
        activityName.setText(R.string.life_service);

        refreshableView.setOnHeaderRefreshListener(this);
        refreshableView.setOnFooterRefreshListener(this);

        areaPopupWindow = PopWindowHelper.newInstance(this);
        serviceTypePopupWindow = PopWindowHelper.newInstance(this);

        serviceTypePopupWindow.setOnDismissListener(() -> selectProductType.setSelected(false));
        serviceTypePopupWindow.setPopWindowEvent(serviceTypeListener);

        areaPopupWindow.setOnDismissListener(() -> selectArea.setSelected(false));
        areaPopupWindow.setPopWindowEvent(areaListener);

        allType = new ResCategoryList.CategoryList();
        allType.id = "";
        allType.name = "全部";

        allArea = new ResServiceAddress.AreasList();
        allArea.id = "";
        allArea.name = "全部";
        allArea.fullPath = "";

    }


    @OnClick(R.id.select_product_type)
    public void chooseProductClass() {
        serviceTypePopupWindow.showAsDropDown(divisionOne);
        selectProductType.setSelected(true);
    }

    @OnClick(R.id.select_area)
    public void chooseAreaClass() {
        areaPopupWindow.showAsDropDown(divisionOne);
        selectArea.setSelected(true);
    }

    @OnItemClick(R.id.content_gv)
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        ResProviderServiceList.ProviderServiceList tm = productListAdapter.getItem(arg2);
        Intent intent = new Intent(this, ServiceDetailActivity.class);
        intent.putExtra("productID", tm.productId);
        intent.putExtra("productName", tm.productName);
        startActivity(intent);
    }

    /**
     * 初始化翻页值
     */
    private void initPage() {
        nowPage = 1;
        refreshableView.setAllowToLoadMore(true);
        firstLoad = true;
        processBar.setVisibility(View.VISIBLE);
        itemGV.setVisibility(View.GONE);
    }

    /**
     * 获取商品列表协议
     */
    private void getProviderService() {
        subscription.add(marketApi.getProviderService(
                new ReqProviderServiceList.Builder()
                        .setCategoryParentId(serviceTypeParentId)
                        .setCategoryName(serviceTypeChildName.equals("全部") ? "" : serviceTypeChildName)
                        .setKind("0")
                        .setArea(areaString)
                        .setName("")
                        .setIndex(String.valueOf(nowPage))
                        .setNum(String.valueOf(countPage))
                        .build(),
                new ViewProxyImp(new ViewProxyImp.Builder()
                        .setIsSecondLoad(!firstLoad)
                        .setSuccessView(itemGV)
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
                .onErrorResumeNext(Observable.empty())
                .subscribe(this::updateProductListInfo));
    }

    private void reloadData() {
        setIsGetData(false);
        firstLoad = false;
        getProviderService();
    }

    /**
     * 更新超市区域板块商品UI
     */
    private void updateProductListInfo(ResProviderServiceList resProductList) {
        totalPage = StringUtil.str2int(resProductList.s_total);
        nowPage = StringUtil.str2int(resProductList.s_num);
        nowPage++;

        if (firstLoad) {
            refreshableView.completeNow();
            productListAdapter.clear();
            productListAdapter.addAll(resProductList.getData());
            itemGV.setAdapter(productListAdapter);
        } else {
            productListAdapter.addAll(resProductList.getData());
            productListAdapter.notifyDataSetChanged();
            refreshableView.onFooterRefreshComplete();
        }
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
        getProviderService();
    }

    //###########################################区域下拉处理###################################################
    PopWindowHelper.PopWindowEvent areaListener = new PopWindowHelper.PopWindowEvent() {

        @Override
        public Observable<List<PopWindowHelper.DataModel>> onPopWindowFirstSelect(PopWindowHelper.DataModel dataModel) {

            return menuApi.getServiceAreasList(
                    new ReqServiceAddress.Builder()
                            .setId(dataModel.id.equals("") ? "-1" : dataModel.id)
                            .build())
                    .doOnNext(menuList -> menuList.areasList.add(0, allArea))
                    .map(menuList -> menuList.areasList)
                    .flatMap(Observable::from)
                    .map(spinnerList -> new PopWindowHelper.DataModel.Builder()
                            .setName(spinnerList.name)
                            .setId(spinnerList.fullPath)
                            .build())
                    .toList()
                    .onErrorResumeNext(Observable.empty());
        }

        @Override
        public void onPopWindowSecondSelect(PopWindowHelper.DataModel firstModel, PopWindowHelper.DataModel secondModel) {
            String tips = secondModel.name;
            selectArea.setText(tips);
            selectArea.setSelected(false);
            initPage();

            areaString = secondModel.id.equals("") ? firstModel.parentName : secondModel.id;
            getProviderService();
        }

        @Override
        public Observable<List<PopWindowHelper.DataModel>> onPopWindowInitFirstList() {

            return menuApi.getServiceAreasList(
                    new ReqServiceAddress.Builder()
                            .setId("")
                            .build())
                    .doOnNext(menuList -> menuList.areasList.add(0, allArea))
                    .map(menuList -> menuList.areasList)
                    .flatMap(Observable::from)
                    .map(spinnerList -> new PopWindowHelper.DataModel.Builder()
                            .setName(spinnerList.name)
                            .setId(spinnerList.id)
                            .setParentName(spinnerList.fullPath)
                            .build())
                    .toList()
                    .onErrorResumeNext(Observable.empty());
        }
    };
    //###########################################区域下拉处理###################################################

    //###########################################服务类型下拉处理###################################################
    PopWindowHelper.PopWindowEvent serviceTypeListener = new PopWindowHelper.PopWindowEvent() {

        @Override
        public Observable<List<PopWindowHelper.DataModel>> onPopWindowFirstSelect(PopWindowHelper.DataModel position) {
            Log.i(TAG, "二级下拉============");
            return menuApi.getCategoryList(
                    new ReqCategoryList.Builder()
                            .setParent(position.id.equals("") ? "-1" : position.id)
                            .setName("")
                            .build())
                    .doOnNext(menuList -> menuList.categoryList.add(0, allType))
                    .map(menuList -> menuList.categoryList)
                    .flatMap(Observable::from)
                    .map(firstTypeList -> new PopWindowHelper.DataModel.Builder()
                            .setName(firstTypeList.name)
                            .setId(firstTypeList.id)
                            .build())
                    .toList()
                    .onErrorResumeNext(Observable.empty());
        }

        @Override
        public void onPopWindowSecondSelect(PopWindowHelper.DataModel firstModel, PopWindowHelper.DataModel secondModel) {
            String tips = firstModel.name + "/" + secondModel.name;
            tips = ("全部".equals(firstModel.name) && "全部".equals(secondModel.name)) ? "全部" : tips;
            selectProductType.setText(tips);
            initPage();

            serviceTypeParentId = firstModel.id;
            serviceTypeChildName = secondModel.name;
            getProviderService();
        }

        @Override
        public Observable<List<PopWindowHelper.DataModel>> onPopWindowInitFirstList() {

            Log.i(TAG, "一级下拉============");
            return menuApi.getCategoryList(
                    new ReqCategoryList.Builder()
                            .setParent("")
                            .setName("")
                            .build())
                    .doOnNext(menuList -> menuList.categoryList.add(0, allType))
                    .map(menuList -> menuList.categoryList)
                    .flatMap(Observable::from)
                    .map(firstTypeList -> new PopWindowHelper.DataModel.Builder()
                            .setName(firstTypeList.name)
                            .setId(firstTypeList.id)
                            .build())
                    .onErrorResumeNext(Observable.empty())
                    .toList();
        }
    };
    //###########################################服务类型下拉处理###################################################

    public class ProductListAdapter extends QuickAdapter<ResProviderServiceList.ProviderServiceList> {

        public ProductListAdapter() {
            super(LifeServicesActivity.this, R.layout.life_service_list);
        }

        @Override
        protected void convert(BaseAdapterHelper helper, ResProviderServiceList.ProviderServiceList item) {

//            List<String> imgUrl = item.picUrl;

            if (item.picUrl.equals("")) {
                Picasso.with(context).load(R.mipmap.default_picture).into((ImageView) helper.getView(R.id.product_icon));

            } else {
                Picasso.with(context).load(Tools.getPicUrl(item.picUrl, ConstantValue.IMG_PRODUCT_WIDTH, ConstantValue.IMG_PRODUCT_HEIGHT))
                        .placeholder(R.mipmap.default_picture)
                        .error(R.mipmap.default_picture)
                        .into((ImageView) helper.getView(R.id.product_icon));
            }

            helper.setText(R.id.product_name, item.productName);
            helper.setText(R.id.product_id, item.productId);
            ((TextView) helper.getView(R.id.product_price)).setText(Html.fromHtml("<font color=\"red\">￥" + item.price + "</font>"));
        }
    }

}
