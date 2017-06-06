package com.hotel.service.ui.property;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hotel.service.HotelApp;
import com.hotel.service.R;
import com.hotel.service.net.module.repairs.PublicRepairsApi;
import com.hotel.service.net.module.repairs.model.ReqPublicProgression;
import com.hotel.service.net.module.repairs.model.ResPublicProgression;
import com.hotel.service.ui.base.BaseFragmentActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.RetrofitError;
import rx.Observable;
import rx.functions.Action1;

/**
 * 报事报修进度
 * Created by hyz on 2016/1/13.
 */
public class PublicRepairsPlanActivity extends BaseFragmentActivity {

    private String TAG = PublicRepairsPlanActivity.class.getCanonicalName();

    @Inject
    PublicRepairsApi publicRepairsApi;

    @InjectView(R.id.activity_name)
    TextView activityName;

    @OnClick(R.id.return_back)
    public void back() {
        finish();
    }

    @InjectView(R.id.progression_time)
    TextView progressionTime;

    @InjectView(R.id.progression_aderss)
    TextView progressionAderss;

    @InjectView(R.id.progression_content)
    TextView progressionContent;

    @InjectView(R.id.repairs_img)
    ImageView repairsImg;

    @InjectView(R.id.title)
    TextView title;

    private String RepairsId;
    private String Progression;

    @InjectView(R.id.apply_for)
    ImageView apply;
    @InjectView(R.id.send)
    ImageView Send;
    @InjectView(R.id.repairs)
    ImageView Repairs;
    @InjectView(R.id.finish)
    ImageView Finish;

    @InjectView(R.id.line1)
    TextView Line1;
    @InjectView(R.id.line2)
    TextView Line2;
    @InjectView(R.id.line3)
    TextView Line3;

    private Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_property_repairs_plan);

        HotelApp.get(this).inject(this);
        initWidget();

        intent = getIntent();


        //获取报修事件id
        RepairsId = intent.getStringExtra("repairId");

    }

    @Override
    public void onStart() {
        super.onStart();
        getRepqirsInfo();
    }

    private void initWidget() {

        activityName.setText("报事报修进度");


    }

    /**
     * 获取列表信息
     */
    private void getRepqirsInfo() {

        Log.i(TAG, "是否获得数据 11 =============");

        subscription.add(publicRepairsApi.getPublicPro(
                new ReqPublicProgression.Builder()
                        .setRepairsId(RepairsId)
                        .build())
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (throwable instanceof RetrofitError) {
                            switch (((RetrofitError) throwable).getKind()) {
                                case NETWORK:
                                    Toast.makeText(PublicRepairsPlanActivity.this, "网络问题", Toast.LENGTH_SHORT).show();
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
                .subscribe(this::getDateProgression));
    }

    /**
     * 更新列表信息
     */
    private void getDateProgression(ResPublicProgression resPublicProgression) {

        List<ResPublicProgression.getProgressionInfo> tmp = resPublicProgression.ProgressionInfo;


        progressionTime.setText("时间:" + tmp.get(0).createTime);
        progressionAderss.setText("地址:" + tmp.get(0).repairsAddress);
        progressionContent.setText("内容:" + tmp.get(0).repairsContent);


        if (tmp.get(0).progression.equals("untreated")) {
            apply.setImageResource(R.mipmap.apply_on);
            Line1.setBackgroundColor(getResources().getColor(R.color.base_color));

        } else if (tmp.get(0).progression.equals("ongoing")) {
            apply.setImageResource(R.mipmap.apply_on);
            Send.setImageResource(R.mipmap.send_order_on);
            Line1.setBackgroundColor(getResources().getColor(R.color.base_color));
        } else if (tmp.get(0).progression.equals("ongoings")) {
            apply.setImageResource(R.mipmap.apply_on);
            Send.setImageResource(R.mipmap.send_order_on);
            Repairs.setImageResource(R.mipmap.repairs_on);
            Line1.setBackgroundColor(getResources().getColor(R.color.base_color));
            Line2.setBackgroundColor(getResources().getColor(R.color.base_color));
        } else if (tmp.get(0).progression.equals("finished")) {

            title.setText("感谢支持！");

            apply.setImageResource(R.mipmap.apply_on);
            Send.setImageResource(R.mipmap.send_order_on);
            Repairs.setImageResource(R.mipmap.repairs_on);
            Finish.setImageResource(R.mipmap.finish_on);
            Line1.setBackgroundColor(getResources().getColor(R.color.base_color));
            Line2.setBackgroundColor(getResources().getColor(R.color.base_color));
            Line3.setBackgroundColor(getResources().getColor(R.color.base_color));
        }


    }
}
