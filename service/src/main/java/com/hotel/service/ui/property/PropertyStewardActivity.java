package com.hotel.service.ui.property;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.hotel.service.R;
import com.hotel.service.adapter.FunctionListAdapter;
import com.hotel.service.ui.CompanyProActivity;
import com.hotel.service.ui.CulturalActivity;
import com.hotel.service.ui.base.BaseFragmentActivity;

import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * 物业管家主界面
 *
 * @author aoxiang
 */
public class PropertyStewardActivity extends BaseFragmentActivity {

    private String TAG = PropertyStewardActivity.class.getCanonicalName();

    @InjectView(R.id.content_gv)
    GridView itemGV;

    @InjectView(R.id.activity_name)
    TextView activityName;

    private FunctionListAdapter fillAdapter;

    private String goActivity;

    /**
     * 免费服务
     */
    public static int FREE_SERVICE = 0;

    /**
     * 收费服务
     */
    public static int CHARGE_SERVICE = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add_func_view);

        activityName.setText(R.string.property_steward);

        initItemGV();
    }

    private void initItemGV() {

        /*int mImageViewArray[] = {R.mipmap.property_services_on,
                R.mipmap.property_repairs_on, R.mipmap.complaint_suggestion_on, R.mipmap.housing_rental_on,
                R.mipmap.car_fee, R.mipmap.community_office_on, R.mipmap.satisfaction_survey,
                R.mipmap.info, R.mipmap.speech, R.mipmap.cultural_activity, R.mipmap.law_guide, R.mipmap.company_profiles};


        String mTextViewArray[] = {"服务公告", "报事报修", "投诉建议", "房屋租售",
                "停车缴费", "社区求职", "满意度调查", "资讯", "致辞", "文化活动", "办事指南", "企业简介"};*/

        int mImageViewArray[] = {R.mipmap.property_services_on,
                R.mipmap.property_repairs_on, R.mipmap.complaint_suggestion_on, R.mipmap.housing_rental_on,
                R.mipmap.car_fee, R.mipmap.community_office_on,
                R.mipmap.info, R.mipmap.speech, R.mipmap.cultural_activity, R.mipmap.law_guide,
                R.mipmap.company_profiles, R.mipmap.app_download_on};


        String mTextViewArray[] = {"服务公告", "报事报修", "投诉建议", "房屋租售",
                "停车缴费", "企业招聘", "资讯", "致辞", "文化活动", "办事指南", "企业简介", "app下载"};

        fillAdapter = new FunctionListAdapter(this, mImageViewArray, mTextViewArray,
                0, 0);

        itemGV.setAdapter(fillAdapter);
    }

    @OnClick(R.id.return_back)
    public void returnClick() {
        finish();
    }

    @OnItemClick(R.id.content_gv)
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        String tm = (String) fillAdapter.getItem(arg2);

        if (tm.equals("服务公告")) {
            if (!checkUser()) {
                return;

            } else {
                goActivity = "ServiceBulletinActivity";
                goActivity(-1);
            }

        } else if (tm.equals("企业招聘")) {
            goActivity = "CommunityOfferActivity";
            goActivity(-1);


        } else if (tm.equals("报事报修")) {
            if (!checkUser()) {
                return;
            } else {
                goActivity = "PublicRepairsActivity";
                goActivity(-1);
            }


        } else if (tm.equals("投诉建议")) {
            if (!checkUser()) {
                return;

            } else {
                goActivity = "ComplainSuggestActivity";
                goActivity(-1);
            }


        } else if (tm.equals("房屋租售")) {

            goActivity = "HouseRentalActivity";
            goActivity(-1);


        } else if (tm.equals("停车缴费")) {
//            goActivity = "PhoneActivity";
//            goActivity(-1);
            toastDialog("功能正在开发中，请等待", -1);


        } else if (tm.equals("满意度调查")) {
//            goActivity = "GovernmentDocument";
//            goActivity(-1);

        } else if (tm.equals("资讯")) {
            Intent intent = new Intent(PropertyStewardActivity.this, Information.class);
            startActivity(intent);

        } else if (tm.equals("致辞")) {
            Intent intent = new Intent(PropertyStewardActivity.this, SpeechContent.class);
            startActivity(intent);
        } else if (tm.equals("文化活动")) {

           Intent intent = new Intent(PropertyStewardActivity.this, CulturalActivity.class);
            startActivity(intent);

        } else if (tm.equals("办事指南")) {

            Intent intent = new Intent(PropertyStewardActivity.this, WorkGuideActivity.class);
            startActivity(intent);

        } else if (tm.equals("企业简介")) {
            Intent intent = new Intent(PropertyStewardActivity.this, CompanyProActivity.class);
            startActivity(intent);

        } else if (tm.equals("app下载")) {
            goActivity = "APPDownActivity";
            goActivity(-1);


        }


    }

    private void goActivity(int flag) {

        Intent intent = null;

        Class cls = null;
        try {
            if (flag == -1) {
                cls = Class.forName("com.hotel.service.ui.property." + goActivity);

            } else {
                cls = Class.forName("com.hotel.server.ui." + goActivity);
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Log.i(TAG, "cls=================" + cls.getName());
        intent = new Intent(this, cls);

        startActivity(intent);
    }


}
