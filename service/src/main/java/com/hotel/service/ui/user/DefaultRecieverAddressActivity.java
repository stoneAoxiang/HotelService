package com.hotel.service.ui.user;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hotel.service.HotelApp;
import com.hotel.service.R;
import com.hotel.service.net.module.settlement.SettlementApi;
import com.hotel.service.net.module.settlement.model.ReqServiceReservation;
import com.hotel.service.net.module.settlement.model.ResServiceReservation;
import com.hotel.service.net.module.userInfo.UserInfoApi;
import com.hotel.service.net.module.userInfo.model.ReqRecieverAddress;
import com.hotel.service.net.module.userInfo.model.ResReciverAddress;
import com.hotel.service.ui.base.BaseFragmentActivity;
import com.hotel.service.ui.pay.PayActivity;
import com.hotel.service.ui.timepicker.NumericWheelAdapter;
import com.hotel.service.ui.timepicker.OnWheelChangedListener;
import com.hotel.service.ui.timepicker.WheelView;
import com.hotel.service.util.PropertiesUtil;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.RetrofitError;
import rx.Observable;

/**
 * 用户默认选择的收货地址
 * 如果没有选择默认地址，则提示用户增加默认收货地址
 */
public class DefaultRecieverAddressActivity extends BaseFragmentActivity {

    private String TAG = DefaultRecieverAddressActivity.class.getCanonicalName();

    @Inject
    UserInfoApi userInfoApi;

    @Inject
    SettlementApi settlementApi;

    @InjectView(R.id.activity_name)
    TextView activityName;

    @InjectView(R.id.recevie_name)
    TextView recevieName;

    @InjectView(R.id.recevie_address)
    TextView recevieAddress;

    @InjectView(R.id.recevie_tel)
    TextView recevieTel;

    @InjectView(R.id.recevie_memo)
    TextView recevieMemo;

    @InjectView(R.id.day)
    WheelView day;

    @InjectView(R.id.hours)
    WheelView hours;

    @InjectView(R.id.time)
    WheelView time;

    @InjectView(R.id.address_big_layout)
    RelativeLayout addressBigLayout;

    @InjectView(R.id.address_small_layout)
    RelativeLayout addressSmallLayout;

    @InjectView(R.id.address_tip)
    TextView addressTip;

    @OnClick(R.id.return_back)
    public void back() {
        finish();
    }

    private List<ResReciverAddress.PushBuyersList> pushBuyersList;

    private String age;

    private DateNumericAdapter dayAdapter, hoursAdapter, timeAdapter;

    private int mCurDay = 14, mCurHours;
    private String[] dateType;

    private OnWheelChangedListener listener;

    private Calendar calendar;
    private String productName, serviceMethod, productID;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.default_reciever_address);

        HotelApp.get(this).inject(this);

        Intent i = getIntent();
        productID = i.getStringExtra("productID");
        productName = i.getStringExtra("productName");
        serviceMethod = i.getStringExtra("serviceMethod");

        activityName.setText(R.string.reservation_info);

    }

    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        getTimer();

        if (serviceMethod.equals("上门消费")) {
            addressBigLayout.setVisibility(View.GONE);
        } else {
            getConsigneeAddress();
        }

    }

    private void getTimer() {
        this.age = "20-15";

        calendar = Calendar.getInstance();

        if (age != null && age.contains("-")) {
            String str[] = age.split("-");

            mCurDay = Integer.parseInt(str[0]);
            mCurHours = Integer.parseInt(str[1]) - 1;
        }
        dateType = getResources().getStringArray(R.array.date);

        listener = new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                updateHours(day);

            }
        };

        day.addChangingListener(listener);

        updateDays(day);
        day.setCurrentItem(mCurDay);
        updateDays(day);

        updateHours(day);
        hours.setCurrentItem(mCurHours);
        updateHours(day);

        updateTimes();
    }

    /**
     * 获取收货地址信息
     */
    private void getConsigneeAddress() {

        subscription.add(userInfoApi.recieverAddressManager(
                new ReqRecieverAddress.Builder()
                        .setUserid(PropertiesUtil.getProperties("userID"))
                        .setType("s")
                        .build(), null)
                .doOnError(throwable -> {
                    if (throwable instanceof RetrofitError) {
                        switch (((RetrofitError) throwable).getKind()) {
                            case NETWORK:
                                Toast.makeText(DefaultRecieverAddressActivity.this, "网络问题", Toast.LENGTH_SHORT).show();
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
                .subscribe(this::recieverAddressInfo));

    }

    /**
     * 获取默认收货地址
     */
    private void recieverAddressInfo(ResReciverAddress resInfo) {
        if (resInfo.result.equals("200")) {

            addressSmallLayout.setVisibility(View.VISIBLE);
            addressTip.setVisibility(View.GONE);

            pushBuyersList = resInfo.getData();

            if (pushBuyersList.size() != 0) {

                recevieAddress.setText(pushBuyersList.get(0).address);
                recevieName.setText(pushBuyersList.get(0).name);
                recevieTel.setText(pushBuyersList.get(0).phone);
            } else {
                recevieAddress.setText(PropertiesUtil.getProperties("phone"));
                recevieName.setText(PropertiesUtil.getProperties("phone"));
                recevieTel.setText(PropertiesUtil.getProperties("phone"));
            }


        } else if (resInfo.result.equals("201") || resInfo.result.equals("300")) {

            addressSmallLayout.setVisibility(View.GONE);
            addressTip.setVisibility(View.VISIBLE);
        }

    }

    /**
     * 查询收货地址
     */
    private static final int QUERY_REQUEST_CODE = 2;

    @OnClick(R.id.address_big_layout)
    public void startAddressManager() {
        Intent i = new Intent(this, RecieverAddressManagerActivity.class);
        i.putExtra("flag", "queryAddress");

        startActivityForResult(i, QUERY_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == QUERY_REQUEST_CODE && resultCode == RESULT_OK) {


            addressSmallLayout.setVisibility(View.VISIBLE);
            addressTip.setVisibility(View.GONE);

            recevieAddress.setText(data.getStringExtra("recevieAddress"));
            recevieName.setText(data.getStringExtra("recevieName"));
            recevieTel.setText(data.getStringExtra("recevieTel"));

        }
    }

    @OnClick(R.id.submit_reciever)
    public void submitClick() {

        toastDialog(((day.getCurrentItem() + 1) + "号" + (hours.getCurrentItem() + 1) + "点" + time
                .getCurrentItem()), -1);

        subscription.add(settlementApi.getServiceReservation(
                new ReqServiceReservation.Builder()
                        .setName(recevieName.getText().toString())
                        .setAddress(recevieAddress.getText().toString())
                        .setTel(recevieTel.getText().toString())
                        .setMemo(recevieMemo.getText().toString())
                        .setShippingMethod(serviceMethod)
                        .setReservationTime((day.getCurrentItem() + 1) + ":" + (hours.getCurrentItem() + 1) + ":" + time
                                .getCurrentItem())
                        .setServiceId(productID)
                        .setUserId(null == PropertiesUtil.getProperties("userID") ? "" : PropertiesUtil.getProperties("userID"))
                        .build(), null)
                .onErrorResumeNext(Observable.empty())
                .subscribe(this::resReservation));

    }

    @OnClick(R.id.cancel)
    public void cancelClick() {
        finish();
    }

    private void resReservation(ResServiceReservation resInfo) {
        if (resInfo.result.equals("200")) {

            Intent i = new Intent(this, PayActivity.class);
            i.putExtra("orderId", resInfo.orderId);

            startActivity(i);

        } else if (resInfo.result.equals("300")) {
            toastDialog("订单生成失败", -1);
        }


    }

    private void updateDays(WheelView day) {

        Calendar aCalendar = Calendar.getInstance(Locale.CHINA);
        int maxDays = aCalendar.getActualMaximum(Calendar.DATE);
        Log.i(TAG, "maxDays============" + maxDays);

        calendar.set(Calendar.DATE, day.getCurrentItem());

        dayAdapter = new DateNumericAdapter(this, 1, maxDays, calendar.get(Calendar.DATE));
        dayAdapter.setTextType(dateType[2]);
        day.setViewAdapter(dayAdapter);

        int curDay = Math.min(maxDays, day.getCurrentItem());

        Log.i(TAG, "curDay============" + curDay);

        day.setCurrentItem(curDay, true);

        Log.i(TAG, "day============" + day.getCurrentItem());
        day.setCyclic(true);

    }

    private void updateHours(WheelView day) {
        calendar.set(Calendar.DAY_OF_WEEK, day.getCurrentItem());

        int maxHours = calendar.getActualMaximum(Calendar.HOUR_OF_DAY);

        hoursAdapter = new DateNumericAdapter(this, 1, maxHours, calendar.get(Calendar.HOUR_OF_DAY) - 1);
        hoursAdapter.setTextType(dateType[3]);
        hours.setViewAdapter(hoursAdapter);

        hours.setCurrentItem(calendar.get(Calendar.HOUR_OF_DAY), true);

        hours.setCyclic(true);

        age = (day.getCurrentItem() + 1) + "-" + hours.getCurrentItem();
    }

    private void updateTimes() {
        timeAdapter = new DateNumericAdapter(this, 0, 1, 0);
        timeAdapter.setTextType(dateType[4]);
        time.setVisibleItems(2);
        time.setViewAdapter(timeAdapter);
    }


    private class DateNumericAdapter extends NumericWheelAdapter {
        int currentItem;
        int currentValue;

        public DateNumericAdapter(Context context, int minValue, int maxValue,
                                  int current) {
            super(context, minValue, maxValue);
            this.currentValue = current;
            setTextSize(22);
        }

        protected void configureTextView(TextView view) {
            super.configureTextView(view);
            view.setTypeface(Typeface.SANS_SERIF);
        }

        public CharSequence getItemText(int index) {
            currentItem = index;
            return super.getItemText(index);
        }

    }

}
