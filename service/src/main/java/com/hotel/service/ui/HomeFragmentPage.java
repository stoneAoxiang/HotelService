package com.hotel.service.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.hotel.service.HotelApp;
import com.hotel.service.R;
import com.hotel.service.logic.PopMenuCallback;
import com.hotel.service.net.ViewProxyImp;
import com.hotel.service.net.module.market.MarketApi;
import com.hotel.service.net.module.market.model.ReqGetAdvertise;
import com.hotel.service.net.module.market.model.ResGetAdvertise;
import com.hotel.service.net.module.util.UtilApi;
import com.hotel.service.ui.base.BaseFragment;
import com.hotel.service.ui.custom.PullToRefreshView;
import com.hotel.service.ui.custom.PullToRefreshView.OnFooterRefreshListener;
import com.hotel.service.ui.custom.PullToRefreshView.OnHeaderRefreshListener;
import com.hotel.service.ui.property.ComplainSuggestActivity;
import com.hotel.service.ui.property.PaymentActivity;
import com.hotel.service.ui.property.PropertyStewardActivity;
import com.hotel.service.ui.property.PublicRepairsActivity;
import com.hotel.service.ui.property.ServiceBulletinActivity;
import com.hotel.service.ui.service.LifeServicesActivity;
import com.hotel.service.ui.user.LoginActivity;
import com.hotel.service.ui.widget.MySliderView;
import com.hotel.service.util.Config;
import com.hotel.service.util.ConstantValue;
import com.hotel.service.util.PropertiesUtil;
import com.hotel.service.util.Tools;
import com.hotel.service.volley.ApiParams;
import com.sharyuke.tool.update.UpdateManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnClick;
import rx.Observable;
import rx.functions.Action1;

public class HomeFragmentPage extends BaseFragment implements
        OnItemClickListener, OnHeaderRefreshListener, OnFooterRefreshListener{
    private String TAG = HomeFragmentPage.class.getCanonicalName();

    @InjectView(R.id.refreshable_view)
    PullToRefreshView refreshableView;

    private String goActivity;

    @Inject
    UpdateManager updateManager;

    private View view;

    private Callback cb;

    // 临时保存小区民
    String communityName;
    @Inject
    MarketApi marketApi;

    @Inject
    UtilApi utilApi;

    @InjectView(R.id.home_slider_layout)
    SliderLayout advSlider;

    @InjectView(R.id.home_indicator)
    PagerIndicator pagerIndicator;

    @InjectView(R.id.home_estate_fee)
    TextView homeEstateFee;

    @InjectView(R.id.product_preferential)
    TextView productPreferential;

    @InjectView(R.id.good_pruduct)
    TextView goodPruduct;

    @InjectView(R.id.property_services_id)
    TextView propertyServicesTV;

    @InjectView(R.id.property_repairs_id)
    TextView propertyRepairsTV;

    private int advertCount;

    @InjectView(R.id.car_fee)
    TextView carFeeTV;


    @InjectView(R.id.login_bt)
    ImageButton loginBT;

    @InjectView(R.id.no_login)
    TextView noLogin;

    @InjectView(R.id.complaint_suggestion_id)
    TextView complaintSuggestionTV;


    //---------------------------------------PopWindows弹出框说用字段---------------------------------------------------
    /**
     * 区县名称
     */
    private String countyName;

    private LinearLayout selectPropertyLayout;

    /**
     * 选择小区
     */
    private TextView selectProperty;

    private ImageView pressFlag;

    /**
     * 弹出窗
     */
    private SelectpropertyPopMenu selectpropertyPopMenu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.home_fragment_view, null);

        cb = new Callback();
        checkVersion();

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initWidget();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HotelApp.get(getActivity()).inject(this);
    }

    @Override
    public void onStart() {
        super.onStart();

        changeViewStatus();
        getAdvertData();

        Log.i(TAG, "重新加载==============================");
        setLocation();
    }

    private void initWidget() {
        refreshableView.setOnHeaderRefreshListener(this);
        refreshableView.setOnFooterRefreshListener(this);
        refreshableView.setAllowToLoadMore(false);
        refreshableView.hideFooterView();
        advSlider.setCustomIndicator(pagerIndicator);

        selectProperty = (TextView) view.findViewById(R.id.select_property);

        propertyServicesTV.setText("服务公告");
        propertyServicesTV.setTextColor(getResources().getColor(R.color.grey_dark));
        propertyServicesTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkUser()) {
                    Intent intent = new Intent(getActivity(), ServiceBulletinActivity.class);
                    startActivity(intent);
                }


            }
        });

        propertyRepairsTV.setText("报事报修");
        propertyRepairsTV.setTextColor(getResources().getColor(R.color.grey_dark));
        propertyRepairsTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkUser()) {
                    Intent intent = new Intent(getActivity(), PublicRepairsActivity.class);
                    startActivity(intent);
                }

            }
        });

        carFeeTV.setText("停车缴费");
        carFeeTV.setTextColor(getResources().getColor(R.color.grey_dark));
        carFeeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), FriendActivity.class);
//                startActivity(intent);
                toastDialog("功能正在开发中，请等待", -1);

            }
        });

        complaintSuggestionTV.setText("投诉建议");
        complaintSuggestionTV.setTextColor(getResources().getColor(R.color.grey_dark));
        complaintSuggestionTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkUser()) {
                    Intent intent = new Intent(getActivity(), ComplainSuggestActivity.class);
                    startActivity(intent);
                }


            }
        });

        setTextProperty(homeEstateFee, "缴费记录", "手机查询，随时跟踪\n便享生活服务！", R.color.base_color);

        setTextProperty(productPreferential, "商品优惠", "有条优惠信息", R.color.grey_dark);
        productPreferential.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if (checkUser()) {
//                    Intent intent = new Intent(getActivity(), ComplainSuggestActivity.class);
//                    startActivity(intent);
//                }

                toastDialog("功能正在开发中，请等待", -1);

            }
        });

        setTextProperty(goodPruduct, "拼好货", "已有人参与      ", R.color.grey_dark);
        goodPruduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if (checkUser()) {
//                    Intent intent = new Intent(getActivity(), ComplainSuggestActivity.class);
//                    startActivity(intent);
//                }

                toastDialog("功能正在开发中，请等待", -1);

            }
        });


        //-------------------------------初始化顶部界面-------------------------------------
        selectProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectpropertyPopMenu = new SelectpropertyPopMenu(
                        getActivity(), selectProperty, cb);
            }
        });

        pressFlag = (ImageView) view.findViewById(R.id.press_flag);


    }

    /**
     * 设置文本属性
     */
    private void setTextProperty(TextView v, String first, String second, int colorID) {
        SpannableString spanText = new SpannableString(first);
        TextAppearanceSpan textAppearanceSpan = new TextAppearanceSpan(getActivity(), R.style.text_style);
        spanText.setSpan(textAppearanceSpan, 0, spanText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanText.setSpan(new ForegroundColorSpan(getResources().getColor(colorID)), 0, spanText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        v.setText(spanText);
        v.append("\n");

        SpannableString spanText2 = new SpannableString(second);
        TextAppearanceSpan textAppearanceSpan2 = new TextAppearanceSpan(getActivity(), R.style.text_style);
        spanText2.setSpan(textAppearanceSpan2, 0, spanText2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        v.append(spanText2);
    }

    /**
     * 用户选择左上角的小区后刷新整个页面
     */
    private void refreshPage() {
        getAdvertData();
    }

    private void setCommunityName(String communityName) {

        this.communityName = communityName;
    }

    /**
     * 设置顶部地址选择
     */
    private void setLocation() {

        if (!ConstantValue.isLogin) {
            Log.i(TAG, "未登录==============================");

            if (null == communityName) {
                selectProperty.setText(ConstantValue.EXPERIENCE_COMMUNITY_NAME);
            } else {
                selectProperty.setText(communityName);
            }

//            pressFlag.setVisibility(View.VISIBLE);

            //************************************************************************
            //************************************************************************
            //******************************暂时屏蔽点击*******************************
            //************************************************************************
            //************************************************************************
            selectProperty.setClickable(false);
            pressFlag.setVisibility(View.GONE);


        } else {
            Log.i(TAG, "已登录==============================");
            selectProperty.setClickable(false);
            pressFlag.setVisibility(View.GONE);
            Log.i(TAG, "设置小区名==============================" + PropertiesUtil.getProperties("communityName"));
            selectProperty.setText(PropertiesUtil.getProperties("communityName") + "");
        }
    }

    //    @OnClick({R.id.app_layout,R.id.position_layout, R.id.house_layout, R.id.phone_layout, R.id.app_layout})
    public void homeFunctionNeedLogin(View view) {
        if (!checkUser()) {
            return;
        }
        switch (view.getId()) {
            default:
                break;
        }
        goActivity(-1);
    }

    private Response.Listener<String> responseListener() {
        return responseData -> {

            if (null != getPd()) {
                changeDialogStatus();
            }

            refreshableView.completeNow();

            if (responseData.contains("getCountyInfo")) {
                processCountyData(responseData);

            } else if (responseData.contains("getCommuityInfo")) {
                processCountyData(responseData);

            } else if (responseData.contains("getCommuityOfCountyInfo")) {
                processCommuityData(responseData);
            }
        };
    }

    private void checkVersion() {
        // 检查是否有更新
        updateManager.checkUpdate(getActivity(), true);
    }


    private void goActivity(int value) {

        Intent intent = null;

        Class cls = null;
        try {
            cls = Class.forName("com.hotel.service.ui." + goActivity);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        intent = new Intent(getActivity(), cls);

        if (goActivity.equals("RepairsInfoActivity")) {
            intent.putExtra("repairsTypeID", ConstantValue.PUBLIC_REPAIRS + "");
            intent.putExtra("repairsTypeName",
                    getResources().getString(R.string.public_repairs));
            intent.putExtra("isWrite", true);

        } else if (goActivity.equals("PrivateRepairsSetsActivity") || goActivity.equals("ComplainInfoActivity")) {
            intent.putExtra("isWrite", true);

        } else if (goActivity.equals("BusinessAreaActivity")) {

            if (value == 1) {
                intent.putExtra("secondSpinnerTypeName", "汽车美容");
            } else {
                intent.putExtra("merchant_type_name", value);
            }

        } else if (goActivity.equals("LifeActivity") || goActivity.equals("LifeActivity") || goActivity.equals("LifeActivity")
                || goActivity.equals("AddFuncActivity") || goActivity.equals("PropertyServiceMainActivity")) {

            intent.putExtra("selectID", value);

        }


        startActivity(intent);
    }

    /**
     * 广告位
     */
    private void getAdvertData() {
        advertisePositionID = ConstantValue.HOME_ADVERTISE + "";
        subscription.add(marketApi.getPublicAdvertise(
                new ReqGetAdvertise.Builder()
                        .setAdvertise_position_id(advertisePositionID)
                        .setCommunity_id(Tools.getCommuityID())
                        .build(), new ViewProxyImp(null) {
                    @Override
                    public void onFailed() {
                        super.onFailed();
                        refreshableView.onHeaderRefreshComplete();
                    }
                })
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        requestError(throwable);
                    }
                })
                .onErrorResumeNext(Observable.empty())
                .subscribe(this::updateAdvertUI));
    }

    /**
     * 更新广告页面
     */
    private void updateAdvertUI(ResGetAdvertise resGetAdvertise) {
        refreshableView.completeNow();
        advertCount = resGetAdvertise.getData().size();
        advSlider.removeAllSliders();
        for (ResGetAdvertise.AdvertiseList adv : resGetAdvertise.getData()) {
            MySliderView mySliderView = new MySliderView(getActivity());
            mySliderView.image(Config.PUBLIC_SERVER_URL + adv.advertise_url);
            mySliderView.setOnSliderClickListener(baseSliderView -> {
//                Intent intent = new Intent(getActivity(), PropertyIntroActivity.class);
//                intent.putExtra("advertiseID", adv.advertise_id);
//                intent.putExtra("advImg", adv.advertise_url);
//                startActivity(intent);
            });
            advSlider.addSlider(mySliderView);
        }

        if (advertCount == 0) {
            advSlider.stopAutoCycle();
        } else {
            advSlider.startAutoCycle();
        }
    }

    /**
     * 处理收到的区县数据
     *
     * @param responeData
     */
    private void processCountyData(String responeData) {

        ArrayList<HashMap<String, String>> countyList = null;

        try {
            countyList = (ArrayList<HashMap<String, String>>) json2Obj(responeData, ConstantValue.GET_COUNTY_INFO);
        } catch (Exception e) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }

        selectpropertyPopMenu.setCountyData(countyList);
    }

    /**
     * 处理收到的社区数据
     */
    private void processCommuityData(String responseData) {
        ArrayList<HashMap<String, String>> commuityList = null;

        try {
            commuityList = (ArrayList<HashMap<String, String>>) json2Obj(responseData, ConstantValue.GET_COMMUITY_OF_COUNTY_INFO);
        } catch (Exception e) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }

        if (null == commuityList) {
            return;
        }

        Log.i(TAG, "解析收到的社区数据条数=============" + commuityList.size());

        selectpropertyPopMenu.setCommunityData(commuityList);
    }

    private JSONArray jsonArray;

    private Object json2Obj(String jso, int flag) throws JSONException {

        Log.i(TAG, "要解析的JSON数据::::::::::::");

        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jso);

            switch (flag) {
                case ConstantValue.GET_MERCHANT_LIST:

                    jsonArray = jsonObject.getJSONArray("county_list");

                    return countyInfoJsonDate(jsonArray);

                case ConstantValue.GET_COMMUITY_OF_COUNTY_INFO:
                    jsonArray = jsonObject.getJSONArray("commuity_list");

                    return commuityOfCountyJsonDate(jsonArray);
            }

        } catch (JSONException e1) {
            throw e1;
        }

        return null;
    }

    /**
     * 解析区县信息协议
     *
     * @param jsonArray
     * @return
     * @throws JSONException
     */
    private ArrayList<HashMap<String, String>> countyInfoJsonDate(JSONArray jsonArray) throws JSONException {
        ArrayList<HashMap<String, String>> countyData = new ArrayList<HashMap<String, String>>();

        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                HashMap<String, String> map = new HashMap<String, String>();
                JSONObject tmp = (JSONObject) jsonArray.get(i);

//		        map.put("id", tmp.getString("county_id"));
                map.put("name", tmp.getString("county_name"));
                countyData.add(map);
            }

            return countyData;
        } catch (JSONException e) {
            throw e;
        }
    }

    /**
     * 解析社区信息协议
     *
     * @param jsonArray
     * @return
     * @throws JSONException
     */
    private ArrayList<HashMap<String, String>> commuityOfCountyJsonDate(JSONArray jsonArray) throws JSONException {
        ArrayList<HashMap<String, String>> commuityData = new ArrayList<HashMap<String, String>>();

        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                HashMap<String, String> map = new HashMap<String, String>();
                JSONObject tmp = (JSONObject) jsonArray.get(i);

                map.put("id", tmp.getString("community_id"));
                map.put("name", tmp.getString("commuity_name"));
                commuityData.add(map);
            }

            return commuityData;
        } catch (JSONException e) {
            throw e;
        }


    }

    private String localObj2Json(int requestFlag) {

        JSONObject jsonInfo = new JSONObject();
        try {

            switch (requestFlag) {

                case ConstantValue.GET_COMMUITY_OF_COUNTY_INFO:
                    jsonInfo.put("module", "Home");
                    jsonInfo.put("method", "getCommuityOfCountyInfo");
                    jsonInfo.put("county_name", countyName);
                    break;

                case ConstantValue.GET_COUNTY_INFO:
                    jsonInfo.put("module", "Home");
                    jsonInfo.put("method", "getCountyInfo");

                    break;

                default:
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String info = jsonInfo.toString();
        Log.i(TAG, "请求信息：" + info);

        return info;

    }

    /**
     * 实现弹出页面的回调方法
     *
     * @author aoxiang
     */
    class Callback implements PopMenuCallback {

        @Override
        public void setSelectCounty(String countyID) {

        }

        @Override
        public void setSelectCommunity(String communityID, String communityName) {
            selectpropertyPopMenu.dismiss();
            selectProperty.setText(communityName);
            PropertiesUtil.setProperties("communityID", communityID);

            setCommunityName(communityName);
            refreshPage();

        }

        @Override
        public void getCountyList() {


            executeRequest(new StringRequest(Method.POST,
                    Config.SERVER_URL_FULL, responseListener(),
                    errorListener()) {
                protected Map<String, String> getParams() {
                    return new ApiParams().with("requestValue", localObj2Json(ConstantValue.GET_COUNTY_INFO));
                }
            });

        }

        @Override
        public void getCommunityList(String countyName) {

            HomeFragmentPage.this.countyName = countyName;

            executeRequest(new StringRequest(Method.POST,
                    Config.SERVER_URL_FULL, responseListener(),
                    errorListener()) {
                protected Map<String, String> getParams() {
                    return new ApiParams()
                            .with("requestValue", localObj2Json(ConstantValue.GET_COMMUITY_OF_COUNTY_INFO));
                }
            });
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

//		super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ConstantValue.HOME_LOGIN && resultCode == getActivity().RESULT_OK) {
            refreshPage();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {


        if (!ConstantValue.isLogin) {
            toastDialog("你还没有登录", ConstantValue.failure);

            Intent t = new Intent(getActivity(), LoginActivity.class);
            t.putExtra("homeLogin", "y");
            startActivityForResult(t, ConstantValue.HOME_LOGIN);
            return;
        }
    }

    //下拉刷新
    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        refreshPage();
    }

    //上拉加载
    @Override
    public void onFooterRefresh(PullToRefreshView view) {
    }

    /**
     * 已登录图标
     */
    @OnClick(R.id.login_bt)
    public void loginBtClick() {
        if (!ConstantValue.isLogin) {
            startActivity(new Intent(getActivity(), LoginActivity.class));

        }
    }

    @OnClick(R.id.estate_fee_bt)
    public void estateFeeBtClick() {

        String flag = PropertiesUtil.getProperties("flag");

        if (!ConstantValue.isLogin) {
            startActivity(new Intent(getActivity(), LoginActivity.class));

        } else {

            if (null != flag && flag.equals("1")) {
                startActivity(new Intent(getActivity(), PaymentActivity.class));

            } else {    //请绑定
                toastDialog("你不能查看缴费记录", -1);
            }
        }
    }

    /**
     * 未登录
     */
    @OnClick(R.id.no_login)
    public void noLoginBtClick() {
        Intent t = new Intent(activity, LoginActivity.class);
        t.putExtra("homeLogin", "y");

        startActivityForResult(t, ConstantValue.HOME_LOGIN);
    }

    public void changeViewStatus() {
        if (ConstantValue.isLogin) {
            loginBT.setVisibility(View.VISIBLE);
            noLogin.setVisibility(View.GONE);

        } else {
            loginBT.setVisibility(View.GONE);
            noLogin.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.life_more)
    public void lifeMoreClick() {

    }

    /**
     * 物业管家
     */
    @OnClick(R.id.property_more)
    public void propertyMoreClick() {
        Intent t = new Intent(activity, PropertyStewardActivity.class);
        startActivity(t);
    }

    @OnClick(R.id.electric_rinse)
    public void electricRinseClick() {
        Intent t = new Intent(activity, LifeServicesActivity.class);
        startActivity(t);
    }


}
