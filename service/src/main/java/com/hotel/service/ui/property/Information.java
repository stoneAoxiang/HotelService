package com.hotel.service.ui.property;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hotel.service.HotelApp;
import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;
import com.hotel.service.R;
import com.hotel.service.net.ViewProxyImp;
import com.hotel.service.net.module.newinfo.NewInfoApi;
import com.hotel.service.net.module.newinfo.model.ReqNewInfo;
import com.hotel.service.net.module.newinfo.model.ResNewInfo;
import com.hotel.service.ui.base.BaseFragmentActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;
import retrofit.RetrofitError;
import rx.Observable;
import rx.functions.Action1;

public class Information extends BaseFragmentActivity {

    private String TAG = Information.class.getCanonicalName();

    @Inject
    NewInfoApi newInfoApi;

    @InjectView(R.id.activity_name)
    TextView activityName;

    @InjectView(R.id.return_back)
    ImageView returnBack;

    @InjectView(R.id.up_content_gv)
    GridView upGV;

    @InjectView(R.id.below_content_gv)
    GridView belowGV;

    @InjectView(R.id.process_bar)
    ProgressBar processBar;

    @InjectView(R.id.no_data)
    TextView noData;

    private UpInfoListAdapter upInfoListAdapter;
    private BelowInfoListAdapter belowInfoListAdapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.information);
        HotelApp.get(this).inject(this);

        initWidget();
    }

    @Override
    protected void onStart() {

        super.onStart();

        getNewInfo();

    }

    //初始化
    private void initWidget() {
        upInfoListAdapter = new UpInfoListAdapter();
        belowInfoListAdapter = new BelowInfoListAdapter();
        activityName.setText(R.string.new_info);

        returnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                finish();
            }
        });

        upGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(Information.this, InformaitionDetail.class);
                i.putExtra("typeId", upInfoListAdapter.getItem(position).id);
                i.putExtra("name", upInfoListAdapter.getItem(position).name);
                startActivity(i);
            }
        });
        belowGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(Information.this, InformaitionDetail.class);
                //获取id，并传入
                i.putExtra("typeId", belowInfoListAdapter.getItem(position).id);
                i.putExtra("name", belowInfoListAdapter.getItem(position).name);
                //   Toast.makeText(NewInfoActivity.this, "点的选项的id："+belowInfoListAdapter.getItem(position).informationTypeId, Toast.LENGTH_SHORT).show();

                startActivity(i);
            }
        });
    }

    private boolean firstLoad = true; // 是否第一次读取数据

    /**
     * 获取信息
     */
    private void getNewInfo() {

        subscription.add(newInfoApi.getNewInfoList(
                new ReqNewInfo.Builder().build(),
                new ViewProxyImp(new ViewProxyImp.Builder()
                        .setIsSecondLoad(!firstLoad)
                        .setSuccessView(upGV,belowGV)
                        .setEmptyView(noData)
                        .setProgressView(processBar)
                        .build()) {
                })
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (throwable instanceof RetrofitError) {
                            switch (((RetrofitError) throwable).getKind()) {
                                case NETWORK:
                                    Toast.makeText(Information.this, "网络问题", Toast.LENGTH_SHORT).show();
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
                .subscribe((resNewInfo) -> {
                    updateNewInfo(resNewInfo);
                }));
    }

    /**
     * 更新信息
     */

    private void updateNewInfo(ResNewInfo resNewInfo) {

        List<ResNewInfo.InfoTypeList> tmp = resNewInfo.getData();

        if (resNewInfo.result.equals("200")) {
            //上面2个item加载，取前面2个数据
            upInfoListAdapter.clear();
            upInfoListAdapter.addAll(tmp.subList(0, 2));
            upGV.setAdapter(upInfoListAdapter);


            //下面6个itme加载,取数组中的第3个数字到最后一个
            belowInfoListAdapter.clear();
            belowInfoListAdapter.addAll(tmp.subList(2, tmp.size()));
            belowGV.setAdapter(belowInfoListAdapter);
        }else {
            noData.setVisibility(View.VISIBLE);
        }
    }

    //配置adpter
    private int count = 1;

    public class UpInfoListAdapter extends QuickAdapter<ResNewInfo.InfoTypeList> {


        public UpInfoListAdapter() {
            super(Information.this, R.layout.info_item);
        }

        @Override
        protected void convert(BaseAdapterHelper helper, ResNewInfo.InfoTypeList item) {

            helper.setText(R.id.info_id, item.id);
            helper.setText(R.id.info_name, item.name);
            int position = helper.getPosition() + 1;
            if(position == 1){
                count = 1;
            }
            switch (count) {
                case 1:
                    if (position % count == 0) {
                        helper.setBackgroundRes(R.id.info_name, R.color.newinfo_pink);
                    }
                    break;
                case 2:
                    if (position % count == 0) {
                        helper.setBackgroundRes(R.id.info_name, R.color.newinfo_blue);
                    }
                    break;
            }
            count++;
        }
    }

    public class BelowInfoListAdapter extends QuickAdapter<ResNewInfo.InfoTypeList> {

        public BelowInfoListAdapter() {
            super(Information.this, R.layout.info_item);
        }

        @Override
        protected void convert(BaseAdapterHelper helper, ResNewInfo.InfoTypeList item) {

            // Log.i(TAG, "循环列表位置=================" + helper.getPosition());
            helper.setText(R.id.info_id, item.id);
            helper.setText(R.id.info_name, item.name);

            int position = helper.getPosition() + 1;
            if(position == 1){
                count = 1;
            }
//            Log.i(TAG, "循环列表位置=================" + position);
//            Log.i(TAG, "count=================" + count);

            switch (count) {
                case 1:
                    helper.setBackgroundRes(R.id.info_name, R.color.newinfo_purple);
                    break;
                case 2:
                    helper.setBackgroundRes(R.id.info_name, R.color.newinfo_gary);
                    break;
                case 3:
                    helper.setBackgroundRes(R.id.info_name, R.color.newinfo_pink);
                    break;
                case 4:
                    helper.setBackgroundRes(R.id.info_name, R.color.newinfo_gary1);
                    break;
                case 5:
                    helper.setBackgroundRes(R.id.info_name, R.color.newinfo_red);
                    break;
                case 6:
                    helper.setBackgroundRes(R.id.info_name, R.color.newinfo_blue1);
                    break;
                case 7:
                    helper.setBackgroundRes(R.id.info_name, R.color.newinfo_b);
                    break;
                case 8:
                    helper.setBackgroundRes(R.id.info_name, R.color.newinfo_w);
                    break;
                case 9:
                    helper.setBackgroundRes(R.id.info_name, R.color.newinfo_y);
                    count = 0;
                    break;

            }
            count++;
        }
    }
}
