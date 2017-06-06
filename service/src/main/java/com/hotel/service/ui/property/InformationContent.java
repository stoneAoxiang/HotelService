package com.hotel.service.ui.property;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.hotel.service.HotelApp;
import com.hotel.service.R;
import com.hotel.service.ui.base.BaseFragmentActivity;
import com.hotel.service.util.Config;


/**
 * Created by Administrator on 2016/3/8.
 */
public class InformationContent extends BaseFragmentActivity {
    private String TAG = InformationContent.class.getCanonicalName();
    private TextView title;
    private TextView creattime;
    private WebView wvContent;
    private TextView activityName;
    private ImageView returnBack;
    private String knowTitle;
    private String knowCreatTime;
    private String knowContent;
    private String linkAddress;
    private String detailsType;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_know_detail);
        HotelApp.get(this).inject(this);
        initWidget();
        getIntentMessage();
        setContent();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private void initWidget() {
        title = (TextView) findViewById(R.id.know_title);
        creattime = (TextView) findViewById(R.id.know_time);
        wvContent = (WebView) findViewById(R.id.know_content);

        activityName = (TextView) findViewById(R.id.activity_name);
        activityName.setText("资讯详情");
        returnBack = (ImageView) findViewById(R.id.return_back);
        returnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    //获取上一个页面传过来的数据
    private void getIntentMessage() {
        knowTitle = getIntent().getStringExtra("title");
        knowContent = getIntent().getStringExtra("details");
        knowCreatTime = getIntent().getStringExtra("createTime");
        linkAddress = getIntent().getStringExtra("linkAddress");
        detailsType = getIntent().getStringExtra("detailsType");
        Log.i(TAG, "更新更新活动区总sum=======================" +knowContent);
    }
//加载UI界面
    private void setContent() {
        title.setText(knowTitle);
        creattime.setText(knowCreatTime);

        if(detailsType.equals("1")){
            wvContent.setVisibility(View.VISIBLE);

            Intent i = new Intent();
            i.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse(linkAddress);
            i.setData(content_url);
            startActivity(i);
//            wvContent.loadUrl(intent.getStringExtra("address"));

        }else if(detailsType.equals("2")){
//            wvContent.getSettings().setJavaScriptEnabled(true);
            wvContent.setVisibility(View.VISIBLE);

            wvContent.loadUrl(Config.HOST_NAME + knowContent);

//            wvContent.setWebViewClient(new HelloWebViewClient());
        }
    }
//    @Override
//    //设置回退
//    //覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if ((keyCode == KeyEvent.KEYCODE_BACK) && wvContent.canGoBack()) {
//            wvContent.goBack(); //goBack()表示返回WebView的上一页面
//            return true;
//        }
//        return false;
//    }

    //Web视图
//    private class HelloWebViewClient extends WebViewClient {
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            view.loadUrl(url);
//            return true;
//        }
//    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "start onResume~~~");
    }

    @Override
    public void onStop() {
        super.onStop();
        this.finish();
        Log.i(TAG, "start onStop~~~");
    }
}
