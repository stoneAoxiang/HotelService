package com.hotel.service.ui.service;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.hotel.service.HotelApp;
import com.hotel.service.R;
import com.hotel.service.net.module.market.MarketApi;
import com.hotel.service.net.module.market.model.ReqProviderServiceDetail;
import com.hotel.service.net.module.market.model.ResProviderServiceDetail;
import com.hotel.service.net.module.menu.MenuApi;
import com.hotel.service.net.module.menu.model.ReqServiceMethod;
import com.hotel.service.ui.base.BaseFragmentActivity;
import com.hotel.service.ui.user.DefaultRecieverAddressActivity;
import com.hotel.service.ui.widget.SinglePopUnCheckBoxWindowHelper;
import com.hotel.service.util.Config;
import com.hotel.service.util.PropertiesUtil;
import com.hotel.service.util.Tools;
import com.hotel.service.volley.ApiParams;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnClick;
import rx.Observable;

public class ServiceDetailActivity extends BaseFragmentActivity {

    private String TAG = ServiceDetailActivity.class.getCanonicalName();

    private boolean isFavorites;
    private String favoriteID;
    private String productID;
    private String productName;

    @InjectView(R.id.favorites)
    ImageView favorites;

    @InjectView(R.id.merchant_tel)
    TextView merchantTel;

    @InjectView(R.id.merchant_address)
    TextView merchantAddress;

    @InjectView(R.id.merchant_name)
    TextView merchantName;

    @InjectView(R.id.product_icon)
    ImageView productIcon;

    @InjectView(R.id.product_id)
    TextView productid;

    @InjectView(R.id.discount_price)
    TextView discountPrice;

    @InjectView(R.id.price)
    TextView price;

    @InjectView(R.id.grade)
    TextView grade;

    @InjectView(R.id.buy_amount)
    TextView buyAmount;

    @InjectView(R.id.reservation_buy)
    TextView reservationBuy;

    @InjectView(R.id.select_service_method)
    TextView selectServiceMethod;

    @InjectView(R.id.content_web)
    WebView contentWeb;

    @OnClick(R.id.return_back)
    public void back() {
        finish();
    }

    @Inject
    MarketApi marketApi;

    @Inject
    MenuApi menuApi;

    private String merchantTelStr;
    private String serviceMethod;

    private SinglePopUnCheckBoxWindowHelper serviceMethodPopupWindow;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.service_detail_page);
        HotelApp.get(this).inject(this);

        Intent intent = getIntent();
        productID = intent.getStringExtra("productID");
        productName = intent.getStringExtra("productName");

        initWidget();

        getProductInfo();
    }

    @SuppressLint("ResourceAsColor")
    private void initWidget() {

        TextView activityName = (TextView) findViewById(R.id.activity_name);
        activityName.setText(R.string.service_descript);

        serviceMethodPopupWindow = SinglePopUnCheckBoxWindowHelper.newInstance(this, 1);
        serviceMethodPopupWindow.setOnDismissListener(() -> selectServiceMethod.setSelected(false));

        serviceMethodPopupWindow.setPopWindowEvent(new SinglePopUnCheckBoxWindowHelper.PopWindowEvent() {
            @Override
            public void onPopWindowSelect(SinglePopUnCheckBoxWindowHelper.DataModel secondModel) {
                String tips = secondModel.name;
                selectServiceMethod.setText(tips);
                selectServiceMethod.setSelected(false);

                serviceMethod = secondModel.name;

            }

            @Override
            public Observable<List<SinglePopUnCheckBoxWindowHelper.DataModel>> onPopWindowInitList() {

                return menuApi.getServiceMethodList(
                        new ReqServiceMethod.Builder()
                                .build())
                        .map(menuList -> menuList.methodList)
                        .flatMap(Observable::from)
                        .map(spinnerList -> new SinglePopUnCheckBoxWindowHelper.DataModel.Builder()
                                .setName(spinnerList.name)
                                .setId(spinnerList.id)
                                .build())
                        .toList()
                        .onErrorResumeNext(Observable.empty());
            }
        });

        favorites = (ImageView) findViewById(R.id.favorites);
        favorites.setVisibility(View.VISIBLE);
        favorites.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if (!checkUser()) {
                    return;
                }

                if (isFavorites) {
                    executeRequest(new StringRequest(Method.POST, Config.PUBLIC_SERVER_URL_FULL, delFavoriteListener(),
                            errorListener()) {
                        protected Map<String, String> getParams() {
                            return new ApiParams().with("requestValue", obj2Json("DEL_FAVORITES"));
                        }
                    });
                } else {
                    executeRequest(new StringRequest(Method.POST, Config.PUBLIC_SERVER_URL_FULL, addFavoritesListener(),
                            errorListener()) {
                        protected Map<String, String> getParams() {
                            return new ApiParams().with("requestValue", obj2Json("ADD_FAVORITES"));
                        }
                    });
                }
            }
        });

        ImageView returnBack = (ImageView) findViewById(R.id.return_back);
        returnBack.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                finish();
            }
        });

    }

    @OnClick(R.id.select_service_method)
    public void chooseServiceMethod() {
        Log.i(TAG, "select_area==========================");
        serviceMethodPopupWindow.showAsDropDown(selectServiceMethod);
        selectServiceMethod.setSelected(true);
    }

    /**
     * 获取产品信息
     */

    private void getProductInfo() {

        subscription.add(marketApi.getServiceDetail(
                new ReqProviderServiceDetail.Builder()
                        .setServiceId(productID)
                        .setUserId(null == PropertiesUtil.getProperties("userID") ? "" : PropertiesUtil.getProperties("userID"))
                        .build(), null)
                .onErrorResumeNext(Observable.empty())
                .subscribe(this::updateServiceDetail));


    }

    public String obj2Json(String requestFlag) {

        JSONObject jsonInfo = new JSONObject();

        try {
            if (requestFlag.equals("ADD_FAVORITES")) {
                jsonInfo.put("module", "Information");
                jsonInfo.put("method", "addFavorites");
                jsonInfo.put("user_id", PropertiesUtil.getProperties("userID"));
                jsonInfo.put("favorites_type", "2");
                jsonInfo.put("product_id", productID);

            } else if (requestFlag.equals("DEL_FAVORITES")) {
                jsonInfo.put("module", "Information");
                jsonInfo.put("method", "delFavorite");
                jsonInfo.put("user_id", PropertiesUtil.getProperties("userID"));
                jsonInfo.put("favorite_id", favoriteID);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        String info = jsonInfo.toString();
        Log.i(TAG, "发送请求信息：" + info);

        return info;

    }

    private void updateServiceDetail(ResProviderServiceDetail resInfo) {
        if (resInfo.isFavorite.equals("1")) {
            favorites.setImageResource(R.mipmap.favorites_sel);
            isFavorites = true;
            favoriteID = resInfo.favoriteId;

        } else if (resInfo.isFavorite.equals("0")) {
            favorites.setImageResource(R.mipmap.favorites_unsel);
            isFavorites = false;
        }

        productid.setText(productID);

        merchantTelStr = resInfo.tel;
        merchantAddress.setText("商家地址:" + resInfo.address);
        merchantName.setText("商家名称:" + resInfo.address);

        String imgUrl = resInfo.imgUrl;
        if (imgUrl != null && !imgUrl.equals("")) {

            Picasso.with(this).load(Tools.getPicUrl(imgUrl, String.valueOf(getWindowSize().x), "160"))
                    .placeholder(R.mipmap.default_picture)
                    .error(R.mipmap.default_picture)
                    .into(productIcon);
        }else{
            Picasso.with(this).load(R.mipmap.default_picture).into(productIcon);
        }

        price.setText("价格:" + resInfo.servicePrice);
        discountPrice.setText("折扣价:" + resInfo.discountPrice);

        reservationBuy.setText(resInfo.reservationPrice + "元预约");

        grade.setText("评分:" + (resInfo.grade.equals("") ? "暂无评分" : resInfo.grade));

        buyAmount.setText("购买人数:" + resInfo.buyCount);

        if (!resInfo.descriptUrl.equals("")) {

            contentWeb.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
            contentWeb.setVerticalScrollBarEnabled(false);
            contentWeb.setVerticalScrollbarOverlay(false);
            contentWeb.setHorizontalScrollBarEnabled(false);
            contentWeb.setHorizontalScrollbarOverlay(false);

            contentWeb.loadUrl(Config.PUBLIC_SERVER_URL + resInfo.descriptUrl);
        }


    }

    private Response.Listener<String> addFavoritesListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String responeData) {

                String result = getResultStr(responeData);
                if (result.equals("200")) {

                    try {
                        json2Obj(responeData);
                    } catch (JSONException e) {
                        Toast.makeText(ServiceDetailActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    toastDialog("收藏成功", -1);

                    favorites.setImageResource(R.mipmap.favorites_sel);
                    isFavorites = true;
                }
            }
        };
    }

    private Response.Listener<String> delFavoriteListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String responeData) {
                String result = getResultStr(responeData);
                if (result.equals("200")) {
                    toastDialog("取消收藏", -1);
                    favorites.setImageResource(R.mipmap.favorites_unsel);
                    isFavorites = false;
                }
            }
        };
    }

    private void json2Obj(String jso) throws JSONException {

        Log.i(TAG, "要解析的JSON数据::::::::::::");

        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jso);

            favoriteID = getFavoriteID(jsonObject);


        } catch (JSONException e1) {
            throw e1;
        }
    }

    private String getFavoriteID(JSONObject jsonObject) throws JSONException {

        try {
            String readResult = null;
            readResult = jsonObject.getString("favorite_id");

            return readResult;
        } catch (JSONException e) {
            throw e;
        }

    }

    @OnClick(R.id.reservation_buy)
    public void buyClick() {

        if(null == serviceMethod){
            toastDialog("请选择服务方式", -1);
            return;
        }

        Intent i = new Intent(this, DefaultRecieverAddressActivity.class);
        i.putExtra("productID", productID);
        i.putExtra("productName", productName);
        i.putExtra("serviceMethod", serviceMethod);

        startActivity(i);
    }

    @OnClick(R.id.merchant_tel)
    public void merchantTelClick() {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + merchantTelStr));
        startActivity(intent);
    }


}
