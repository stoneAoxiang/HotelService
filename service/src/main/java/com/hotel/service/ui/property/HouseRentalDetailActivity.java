package com.hotel.service.ui.property;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.hotel.service.R;
import com.hotel.service.ui.base.BaseFragmentActivity;
import com.hotel.service.util.Config;
import com.hotel.service.util.ConstantValue;
import com.hotel.service.util.Tools;
import com.squareup.picasso.Picasso;

import butterknife.InjectView;

/**
 * 我的缴费记录
 *
 * @author aoxiang
 */
public class HouseRentalDetailActivity extends BaseFragmentActivity {

    private String TAG = ServiceBulletinActivity.class.getCanonicalName();

    @InjectView(R.id.activity_name)
    TextView activityName;

    @InjectView(R.id.return_back)
    ImageView returnBack;

    @InjectView(R.id.rental_img)
    ImageView rentalImg;

    @InjectView(R.id.rental_title)
    TextView rentalTitle;

    @InjectView(R.id.features)
    TextView features;

    @InjectView(R.id.rental_time)
    TextView rentalTime;

    @InjectView(R.id.size)
    TextView size;

    @InjectView(R.id.rent)
    TextView rent;

    @InjectView(R.id.floor)
    TextView floor;

    @InjectView(R.id.payment)
    TextView payment;

    @InjectView(R.id.link_name)
    TextView linkName;

    @InjectView(R.id.project_area)
    TextView projectArea;

    @InjectView(R.id.link_tel)
    TextView linkTel;

    @InjectView(R.id.house_detail)
    WebView houseDetail;

    private Intent intent;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.house_rental_detail_view);

        intent = getIntent();
        initWidget();
    }

    @Override
    protected void onStart() {

        super.onStart();

        updatePaymentDetail();

    }

    private void initWidget() {
        activityName.setText(R.string.house_detail);

        returnBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                finish();
            }
        });

    }


    /**
     * 更新我的缴费详情
     */
    private void updatePaymentDetail() {

        Picasso.with(this).load(Tools.getPicUrl(intent.getStringExtra("iconurl"), ConstantValue.IMG_PRODUCT_DETAIL_WIDTH, ConstantValue.IMG_PRODUCT_DETAIL_HEIGHT))
                .placeholder(R.mipmap.default_picture)
                .error(R.mipmap.default_picture)
                .into(rentalImg);

        String rental = intent.getStringExtra("rental");

        if(rental.equals("0")){
            rent.setText("租金:"+intent.getStringExtra("rent")+"元/月");
            payment.setText("付费方式:"+intent.getStringExtra("payment"));

        }else if(rental.equals("1")){
            rent.setText("售价:"+intent.getStringExtra("rent")+"元");
        }

        rentalTitle.setText(intent.getStringExtra("title"));
        rentalTime.setText(intent.getStringExtra("auditTime"));
        size.setText("面积:"+intent.getStringExtra("size")+"平方");

        String[] floorArr = intent.getStringExtra("floor").split("/");
        floor.setText("楼层:第"+floorArr[0] + "层/共" + floorArr[1] + "层" );
        linkName.setText("联系人:"+intent.getStringExtra("employeeName"));
        projectArea.setText("地址:"+intent.getStringExtra("houseName"));
        linkTel.setText("电话:"+intent.getStringExtra("phone"));
        features.setText("房源特色:"+intent.getStringExtra("features"));

        houseDetail.loadUrl(Config.HOST_NAME + intent.getStringExtra("contenturl"));

    }


}
