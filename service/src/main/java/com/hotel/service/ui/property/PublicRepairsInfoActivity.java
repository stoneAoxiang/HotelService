package com.hotel.service.ui.property;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import android.view.Window;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hotel.service.R;
import com.hotel.service.adapter.PicArrayAdapter;
import com.hotel.service.ui.base.BaseFragmentActivity;
import com.hotel.service.util.PropertiesUtil;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by hyz on 2016/2/23.
 */
public class PublicRepairsInfoActivity extends BaseFragmentActivity implements View.OnClickListener {

    private String TAG = PublicRepairsInfoActivity.class.getCanonicalName();

    @InjectView(R.id.activity_name)
    TextView activityName;

    @InjectView(R.id.btn_layout)
    LinearLayout btnLayout;

    @InjectView(R.id.repairs_tv)
    TextView repairsTv;

    @OnClick(R.id.return_back)
    public void back() {
        finish();
    }

    @InjectView(R.id.repairs_time)
    TextView repairsTime;
    @InjectView(R.id.repairs_status)
    TextView repairsStatus;
    @InjectView(R.id.repairs_address)
    TextView repairsAddress;
    @InjectView(R.id.repairs_people)
    TextView repairsPeople;
    @InjectView(R.id.repairs_details)
    TextView repairsDetails;
    @InjectView(R.id.repairs_serviceman)
    TextView serviceMan;

    /**
     * 动态给图片添加布局
     */
    @InjectView(R.id.photo_img_layout)
    GridView imgLayout;

    /**
     * 保存图片字符串
     */
    private String[] picArray;

    private String createID;
    private String createTime;
    private String createStatus;
    private String createAddress;
    private String createPeople;
    private String createDetails;
    private String createServiceMan;      //维修人员姓名
    private String repairManName; //报修人姓名

    private String createServiceManId;    //维修人员id

    private String createIconUrl;
    private String depNum;
    private String appointDepNum;      //指派部门id

    @InjectView(R.id.handle_Btn)
    TextView handleBtn;

    private Intent intent;


    private String posName;      //部门职位
    private String departmentId;       //部门id
    private String depId;       //被指派的部门id

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_public_repairs_info);

        initView();
    }

    private void initView() {

        activityName.setText("报事保修详情");

        repairsTv.setOnClickListener(this);
        getPublicRepairsInfo();

        departmentId = PropertiesUtil.getProperties("departmentId");
        posName = PropertiesUtil.getProperties("position");

        handleBtn.setVisibility(View.GONE);
    }


    private void getPublicRepairsInfo() {

        intent = getIntent();
        createID = intent.getStringExtra("repairId");
        createTime = intent.getStringExtra("createTime");
        createStatus = intent.getStringExtra("progression");
        createAddress = intent.getStringExtra("repairsAddress");
        createPeople = intent.getStringExtra("repairEmpName");
        createDetails = intent.getStringExtra("repairsContent");

        createServiceMan = intent.getStringExtra("repairPersonnelName"); //维修人员姓名
        createServiceManId = intent.getStringExtra("repairPersonnelId"); //维修人员id

        appointDepNum = intent.getStringExtra("appointDep");
        depNum = intent.getStringExtra("dep");
        createIconUrl = intent.getStringExtra("iconurl");

        repairManName = intent.getStringExtra("repairManName");

        //截取图片数组
        if(createIconUrl !=null && createIconUrl.split(";").length > 0 ){

            picArray = createIconUrl.split(";");
        }

//        picArray = createIconUrl.split(";");

    }

    @Override
    public void onStart() {
        super.onStart();

        Viewinfo();
    }

    private void Viewinfo() {

        setTextProperty(repairsTime, "报修时间:", createTime);
        setTextProperty(repairsAddress, "报修地点:", createAddress);
        setTextProperty(repairsPeople, "报修人员:", repairManName);
        setTextProperty(repairsDetails, "报修内容:", createDetails);


        if (createStatus.equals("untreated")) {
            setTextProperty(repairsStatus, "报修状态:", "待处理");

        } else if (createStatus.equals("ongoing")) {

            setTextProperty(repairsStatus, "报修状态:", "处理中");
        } else if (createStatus.equals("ongoings")) {

            serviceMan.setVisibility(View.VISIBLE);
            setTextProperty(serviceMan, "维修人员:", createServiceMan);
            setTextProperty(repairsStatus, "报修状态:", "维修中");

        } else if (createStatus.equals("finished")) {

            serviceMan.setVisibility(View.VISIBLE);
            setTextProperty(serviceMan, "维修人员:" , createServiceMan );
            setTextProperty(repairsStatus,"报修状态:","已处理");
        }

        PicArrayAdapter adapter = new PicArrayAdapter(this, picArray);
        imgLayout.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.repairs_tv:
                intent = new Intent(PublicRepairsInfoActivity.this, PublicRepairsPlanActivity.class);
                intent.putExtra("progression", createStatus);
                intent.putExtra("repairId", createID);
                startActivity(intent);
                break;

        }
    }




    /**
     * 设置文本属性
     */
    private void setTextProperty(TextView v, String first, String second){
        SpannableString spanText = new SpannableString(first);
        TextAppearanceSpan textAppearanceSpan = new TextAppearanceSpan(PublicRepairsInfoActivity.this, R.style.text_style);
        spanText.setSpan(textAppearanceSpan, 0, spanText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        spanText.setSpan(new ForegroundColorSpan(getResources().getColor(colorID)), 0, spanText.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        v.setText(spanText);
//        v.append("\n");

        SpannableString spanText2 = new SpannableString(second);
        TextAppearanceSpan textAppearanceSpan2 = new TextAppearanceSpan(PublicRepairsInfoActivity.this, R.style.text_style);
        spanText2.setSpan(textAppearanceSpan2, 0, spanText2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        v.append(spanText2);
    }
}
