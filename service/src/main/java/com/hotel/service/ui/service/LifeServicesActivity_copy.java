package com.hotel.service.ui.service;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;
import com.hotel.service.HotelApp;
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
import com.hotel.service.ui.widget.SinglePopUnCheckBoxWindowHelper;
import com.hotel.service.util.StringUtil;

import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemClick;
import rx.Observable;
import rx.functions.Func1;

/**
 * 生活服务
 */
public class LifeServicesActivity_copy extends BaseFragmentActivity implements PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener, PopWindowHelper.PopWindowEvent {

    private String TAG = LifeServicesActivity_copy.class.getCanonicalName();

    @InjectView(R.id.activity_name)
    TextView activityName;

    @InjectView(R.id.select_product_type)
    TextView selectProductType;

    @InjectView(R.id.content_gv)
    HomeGridView itemGV;

    @InjectView(R.id.no_data)
    TextView noData;

    @InjectView(R.id.select_area)
    TextView selectArea;

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
    private SinglePopUnCheckBoxWindowHelper areaPopupWindow;

    ResCategoryList.CategoryList firstType;
    ResCategoryList.CategoryList secondType;

    private String serviceTypeId;
    private String areaId;

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

        areaPopupWindow = SinglePopUnCheckBoxWindowHelper.newInstance(this, 1);
        serviceTypePopupWindow = PopWindowHelper.newInstance(this);

        serviceTypePopupWindow.setOnDismissListener(() -> selectProductType.setSelected(false));
        serviceTypePopupWindow.setPopWindowEvent(this);

        areaPopupWindow.setOnDismissListener(() -> selectArea.setSelected(false));

        firstType = new ResCategoryList.CategoryList();
        firstType.id = "";
        firstType.name = "全部";

        secondType = new ResCategoryList.CategoryList();
        secondType.id = "";
        secondType.name = "全部";

        areaPopupWindow.setPopWindowEvent(new SinglePopUnCheckBoxWindowHelper.PopWindowEvent() {
            @Override
            public void onPopWindowSelect(SinglePopUnCheckBoxWindowHelper.DataModel secondModel) {
                String tips = secondModel.name;
                selectArea.setText(tips);
                selectArea.setSelected(false);

                areaId = secondModel.id;
                getProviderService();

            }

            @Override
            public Observable<List<SinglePopUnCheckBoxWindowHelper.DataModel>> onPopWindowInitList() {

                return menuApi.getServiceAreasList(
                        new ReqServiceAddress.Builder()
                                .setId("")
                                .build())
                        .map(menuList -> menuList.areasList)
                        .flatMap(new Func1<List<ResServiceAddress.AreasList>, Observable<? extends ResServiceAddress.AreasList>>() {
                            @Override
                            public Observable<? extends ResServiceAddress.AreasList> call(List<ResServiceAddress.AreasList> iterable) {
                                return Observable.from(iterable);
                            }
                        })
                        .map(new Func1<ResServiceAddress.AreasList, SinglePopUnCheckBoxWindowHelper.DataModel>() {
                            @Override
                            public SinglePopUnCheckBoxWindowHelper.DataModel call(ResServiceAddress.AreasList spinnerList) {
                                return new SinglePopUnCheckBoxWindowHelper.DataModel.Builder()
                                        .setName(spinnerList.name)
                                        .setId(spinnerList.id)
                                        .build();
                            }
                        })
                        .toList()
                        .onErrorResumeNext(Observable.empty());
            }
        });

    }


    @OnClick(R.id.select_product_type)
    public void chooseProductClass() {
        Log.i(TAG, "select_product_type==================================");
        serviceTypePopupWindow.showAsDropDown(selectProductType);
        selectProductType.setSelected(true);
    }

    @OnClick(R.id.select_area)
    public void chooseAreaClass() {
        Log.i(TAG, "select_area==========================");
        areaPopupWindow.showAsDropDown(selectArea);
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
//                        .setCategory(null == serviceTypeId ? "" : serviceTypeId)
                        .setKind("0")
                        .setArea(null == areaId ? "" : areaId)
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

        Log.i(TAG, " 返回数据 =============" + resProductList.s_num);

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

    @Override
    public Observable<List<PopWindowHelper.DataModel>> onPopWindowFirstSelect(PopWindowHelper.DataModel position) {
        return menuApi.getCategoryList(
                new ReqCategoryList.Builder()
                        .setParent(position.id)
                        .setName("")
                        .build())
                .doOnNext(menuList -> menuList.categoryList.add(0, secondType))
//                .doOnNext(menuList -> secMenuList = menuList)
                .map(menuList -> menuList.categoryList)
                .flatMap(Observable::from)
                .map(firstTypeList -> new PopWindowHelper.DataModel.Builder()
                        .setName(firstTypeList.id)
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

        serviceTypeId = secondModel.id;
        getProviderService();
    }

    @Override
    public Observable<List<PopWindowHelper.DataModel>> onPopWindowInitFirstList() {
        return menuApi.getCategoryList(
                new ReqCategoryList.Builder()
                        .setParent("")
                        .setName("")
                        .build())
                .doOnNext(menuList -> menuList.categoryList.add(0, firstType))
//                .doOnNext(menuList -> firstMenuList = menuList)
                .map(menuList -> menuList.categoryList)
                .flatMap(Observable::from)
                .map(firstTypeList -> new PopWindowHelper.DataModel.Builder()
                        .setName(firstTypeList.name)
                        .setId(firstTypeList.id)
                        .build())
                .onErrorResumeNext(Observable.empty())
                .toList();
    }

    public class ProductListAdapter extends QuickAdapter<ResProviderServiceList.ProviderServiceList> {

        public ProductListAdapter() {
            super(LifeServicesActivity_copy.this, R.layout.life_service_list);
        }

        @Override
        protected void convert(BaseAdapterHelper helper, ResProviderServiceList.ProviderServiceList item) {

            /*List<String> imgUrl = item.picUrl;

            if(imgUrl.size() == 0){
                Picasso.with(context).load(R.mipmap.default_picture).into((ImageView) helper.getView(R.id.product_icon));

            }else{
                Picasso.with(context).load(Tools.getPicUrl(imgUrl.get(0), ConstantValue.IMG_PRODUCT_WIDTH, ConstantValue.IMG_PRODUCT_HEIGHT))
                        .placeholder(R.mipmap.default_picture)
                        .error(R.mipmap.default_picture)
                        .into((ImageView) helper.getView(R.id.product_icon));
            }

            helper.setText(R.id.product_name, item.productName);
            helper.setText(R.id.product_id, item.productId);
            ((TextView) helper.getView(R.id.product_price)).setText(Html.fromHtml("<font color=\"red\">￥" + item.price + "</font>"));*/
        }
    }

}
