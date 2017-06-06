package com.hotel.service.ui;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.hotel.service.HotelApp;
import com.hotel.service.R;
import com.hotel.service.ui.base.BaseFragmentActivity;
import com.hotel.service.util.Config;
import com.hotel.service.util.ConstantValue;
import com.hotel.service.util.Tools;
import com.squareup.picasso.Picasso;

import butterknife.InjectView;


/**
 * Created by Administrator on 2016/3/8.
 */
public class CulturalContentActivity extends BaseFragmentActivity {

    private String TAG = CulturalContentActivity.class.getCanonicalName();


    private TextView title;
    private TextView creattime;
    private WebView wvContent;
    private TextView activityName;
    private NetworkImageView speechContent;
    private ImageView returnBack;

    private String knowTitle;
    private String knowCreatTime;
    private String knowContent;
    private String imgPic;
    private String detailsType;

    @InjectView(R.id.info_desc_content)
    ImageView tvContent;
    /**
     * 保存图片字符串
     */
    private String[] picArray;

    private  String PictureImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
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
        speechContent = (NetworkImageView) findViewById(R.id.speech_content_img);
        activityName = (TextView) findViewById(R.id.activity_name);
        activityName.setText("文化活动详情");

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
        knowContent = getIntent().getStringExtra("content");
        knowCreatTime = getIntent().getStringExtra("createTime");
        imgPic = getIntent().getStringExtra("imgsDesc");
        picArray = imgPic.split(";");

        PictureImg = picArray[0];
    }

    //加载UI界面
    private void setContent() {
        title.setText(knowTitle);
        creattime.setText(knowCreatTime);

        wvContent.setVisibility(View.VISIBLE);
        wvContent.loadUrl(Config.HOST_NAME + knowContent);

        tvContent.setVisibility(View.VISIBLE);

        String imgUrl = PictureImg;
        Picasso.with(CulturalContentActivity.this).load(Tools.getPicUrl(imgUrl, ConstantValue.IMG_PRODUCT_DETAIL_WIDTH, ConstantValue.IMG_PRODUCT_DETAIL_HEIGHT))
                .placeholder(R.mipmap.default_picture)
                .error(R.mipmap.default_picture)
                .into((ImageView) findViewById(R.id.info_desc_content));

    }
}

