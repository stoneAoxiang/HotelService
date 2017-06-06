package com.hotel.service.ui.property;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.hotel.service.R;
import com.hotel.service.util.Config;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class APPDownActivity extends Activity {


    @InjectView(R.id.qc_code)
    ImageView qcCode;

    @OnClick(R.id.return_back)
    public void back() {
        finish();
    }

    @InjectView(R.id.activity_name)
    TextView activityName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.app_down_page);
        ButterKnife.inject(this);

        initWidget();

    }

    private void initWidget() {

        activityName.setText("APP下载");

        //测试库
        if (Config.HOST_NAME.equals("http://120.25.78.157:9100")) {
            qcCode.setImageResource(R.mipmap.qc_test);
        } else {
            qcCode.setImageResource(R.mipmap.qc_release);
        }


    }


}
