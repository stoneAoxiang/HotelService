package com.hotel.service.ui.timepicker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.hotel.service.HotelApp;
import com.hotel.service.R;
import com.hotel.service.net.module.userInfo.UserInfoApi;
import com.hotel.service.net.module.userInfo.model.ReqRecieverAddress;
import com.hotel.service.net.module.userInfo.model.ResReciverAddress;
import com.hotel.service.ui.user.RecieverAddressManagerActivity;
import com.hotel.service.util.PropertiesUtil;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.InjectView;
import retrofit.RetrofitError;
import rx.Observable;
import rx.subscriptions.CompositeSubscription;

public class SelectTimerActivity extends PopupWindow {

    private Activity mContext;

    private View mMenuView;

    private WheelView day, hours, time;

    private TextView submit, cancel;

    @Inject
    UserInfoApi userInfoApi;

    private RelativeLayout addressBigLayout;
    private RelativeLayout addressSmallLayout;
    private TextView addressTip;

    @InjectView(R.id.recevie_name)
    TextView recevieName;

    @InjectView(R.id.recevie_address)
    TextView recevieAddress;

    @InjectView(R.id.recevie_tel)
    TextView recevieTel;

    @InjectView(R.id.recevie_memo)
    TextView recevieMemo;

    private List<ResReciverAddress.PushBuyersList> pushBuyersList;
    protected CompositeSubscription subscription;

    private ViewFlipper viewfipper;
    private String age;

    private DateNumericAdapter dayAdapter, hoursAdapter, timeAdapter;

    private int mCurDay = 14, mCurHours;
    private String[] dateType;

    private String TAG = SelectTimerActivity.class.getCanonicalName();

    private OnWheelChangedListener listener;

    private Calendar calendar;

    public SelectTimerActivity(Activity context) {
        super(context);
        mContext = context;

        HotelApp.get(mContext).inject(this);
        subscription = new CompositeSubscription();
        getConsigneeAddress();

        this.age = "20-15";

        calendar = Calendar.getInstance();

        initWidget(context);

        addListener();

        if (age != null && age.contains("-")) {
            String str[] = age.split("-");

            mCurDay = Integer.parseInt(str[0]);
            mCurHours = Integer.parseInt(str[1]) - 1;
        }
        dateType = mContext.getResources().getStringArray(R.array.date);

        updateDays(day);
        day.setCurrentItem(mCurDay);
        updateDays(day);
        day.addChangingListener(listener);

        updateHours(day);
        hours.setCurrentItem(mCurHours);
        updateHours(day);


        updateTimes();

        viewfipper.addView(mMenuView);
        viewfipper.setFlipInterval(6000000);

        this.setContentView(viewfipper);
        this.setWidth(LayoutParams.FILL_PARENT);
        this.setHeight(LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x00000000);
        this.setBackgroundDrawable(dw);
        this.update();

    }

    private void initWidget(Activity context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.birthday, null);

        viewfipper = new ViewFlipper(context);
        viewfipper.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        day = (WheelView) mMenuView.findViewById(R.id.day);
        hours = (WheelView) mMenuView.findViewById(R.id.hours);
        time = (WheelView) mMenuView.findViewById(R.id.time);

        submit = (TextView) mMenuView.findViewById(R.id.submit);
        cancel = (TextView) mMenuView.findViewById(R.id.cancel);

        addressBigLayout = (RelativeLayout) mMenuView.findViewById(R.id.address_big_layout);

        addressSmallLayout = (RelativeLayout) mMenuView.findViewById(R.id.address_small_layout);
        addressTip = (TextView) mMenuView.findViewById(R.id.address_tip);
    }

    /**
     * 查询默认收货地址
     */
    private static final int GET_DEFAULT_ADDRESS = 2;


    private void addListener() {

        addressBigLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, RecieverAddressManagerActivity.class);
                i.putExtra("flag", "queryAddress");

                mContext.startActivityForResult(i, GET_DEFAULT_ADDRESS);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, (day.getCurrentItem() + 1) + "号"
                        + (hours.getCurrentItem() + 1) + "点" + time
                        .getCurrentItem());
                Toast.makeText(mContext,
                        ((day.getCurrentItem() + 1) + "号"
                                + (hours.getCurrentItem() + 1) + "点" + time
                                .getCurrentItem()), Toast.LENGTH_LONG).show();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectTimerActivity.this.dismiss();
            }
        });

        listener = new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                updateHours(day);

            }
        };
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        viewfipper.startFlipping();
    }

    private void updateDays(WheelView day) {

        Calendar aCalendar = Calendar.getInstance(Locale.CHINA);
        int maxDays = aCalendar.getActualMaximum(Calendar.DATE);

        calendar.set(Calendar.DATE, day.getCurrentItem());

        dayAdapter = new DateNumericAdapter(mContext, 1, maxDays, calendar.get(Calendar.DATE));
        dayAdapter.setTextType(dateType[2]);
        day.setViewAdapter(dayAdapter);

        int curDay = Math.min(maxDays, day.getCurrentItem());

        day.setCurrentItem(curDay, true);
        day.setCyclic(true);

    }

    private void updateHours(WheelView day) {
        calendar.set(Calendar.DAY_OF_WEEK, day.getCurrentItem());

        int maxHours = calendar.getActualMaximum(Calendar.HOUR_OF_DAY);

        hoursAdapter = new DateNumericAdapter(mContext, 1, maxHours, calendar.get(Calendar.HOUR_OF_DAY) - 1);
        hoursAdapter.setTextType(dateType[3]);
        hours.setViewAdapter(hoursAdapter);

        hours.setCurrentItem(calendar.get(Calendar.HOUR_OF_DAY), true);

        hours.setCyclic(true);

        age = (day.getCurrentItem() + 1) + "-" + hours.getCurrentItem();
    }

    private void updateTimes() {
        timeAdapter = new DateNumericAdapter(mContext, 0, 1, 0);
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
            setTextSize(24);
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
                                Toast.makeText(mContext, "网络问题", Toast.LENGTH_SHORT).show();
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


        }

    }

}

