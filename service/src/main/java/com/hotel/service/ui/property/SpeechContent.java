package com.hotel.service.ui.property;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hotel.service.HotelApp;
import com.hotel.service.R;
import com.hotel.service.net.ViewProxyImp;
import com.hotel.service.net.module.speech.SpeechListApi;
import com.hotel.service.net.module.speech.model.ReqSpeechList;
import com.hotel.service.net.module.speech.model.ResSpeechList;
import com.hotel.service.ui.base.BaseFragmentActivity;
import com.hotel.service.util.Config;
import com.hotel.service.util.ConstantValue;
import com.hotel.service.util.Tools;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;
import retrofit.RetrofitError;
import rx.Observable;
import rx.functions.Action1;


/**
 * Created by Administrator on 2016/3/8.
 */
public class SpeechContent extends BaseFragmentActivity {

    private String TAG = SpeechContent.class.getCanonicalName();

    @Inject
    SpeechListApi speechListApi;

    private TextView title;
    private TextView creattime;
    private WebView content;
    private TextView activityName;
    private ImageView speechContent;
    private ImageView returnBack;
    private ImageView imgPic;

    /**
     * 保存图片字符串
     */
    private String[] picArray;

    @InjectView(R.id.process_bar)
    ProgressBar processBar;

    @InjectView(R.id.no_data)
    TextView noData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.info_know_detail);
        HotelApp.get(this).inject(this);
        initWidget();

    }

    @Override
    public void onStart() {
        super.onStart();
        getCultureInfo();
    }

    private void initWidget() {
        title = (TextView) findViewById(R.id.know_title);
        title.setVisibility(View.GONE);
        creattime = (TextView) findViewById(R.id.know_time);
        content = (WebView) findViewById(R.id.know_content);
        speechContent = (ImageView) findViewById(R.id.xian_img);

        imgPic = (ImageView) findViewById(R.id.info_desc_content);

        activityName = (TextView) findViewById(R.id.activity_name);
        activityName.setText("致辞");

        returnBack = (ImageView) findViewById(R.id.return_back);
        returnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }




    /**
     * 获取列表信息
     */
    private void getCultureInfo() {

        Log.i(TAG, "是否获得数据 11 =============");

        subscription.add(speechListApi.getSpeechListType(
                new ReqSpeechList.Builder()
                        .setUserId("")
                        .build(),
                new ViewProxyImp(new ViewProxyImp.Builder()
                        .setIsSecondLoad(false)
                        .setSuccessView(speechContent)
                        .setEmptyView(noData)
                        .setProgressView(processBar)
                        .build()) {
                } )
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (throwable instanceof RetrofitError) {
                            switch (((RetrofitError) throwable).getKind()) {
                                case NETWORK:
                                    Toast.makeText(SpeechContent.this, "网络问题", Toast.LENGTH_SHORT).show();
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
                .subscribe(this::getDateEvent));
    }

    /**
     * 更新列表信息
     */
    private void getDateEvent(ResSpeechList resSpeechList) {

        List<ResSpeechList.SpeechList> tmp = resSpeechList.getData();

//        activityName.setText( tmp.get(0).title);

        if(resSpeechList.result.equals("300")){

            noData.setVisibility(View.VISIBLE);
            creattime.setVisibility(View.GONE);
            speechContent.setVisibility(View.GONE);

        }else {

            creattime.setText(tmp.get(0).createTime);
            WebSettings wSet = content.getSettings();
            wSet.setJavaScriptEnabled(true);

            imgPic.setVisibility(View.VISIBLE);

            String imgUrl = tmp.get(0).imgsDesc;
            Picasso.with(SpeechContent.this).load(Tools.getPicUrl(imgUrl, ConstantValue.IMG_PRODUCT_DETAIL_WIDTH, ConstantValue.IMG_PRODUCT_DETAIL_HEIGHT))
                    .placeholder(R.mipmap.default_picture)
                    .error(R.mipmap.default_picture)
                    .into((ImageView) findViewById(R.id.info_desc_content));

            content.setVisibility(View.VISIBLE);
            content.loadUrl(Config.HOST_NAME + tmp.get(0).content);

        }

    }

}
