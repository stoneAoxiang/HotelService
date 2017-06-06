package com.hotel.service.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.hotel.service.HotelApp;
import com.hotel.service.R;
import com.hotel.service.net.module.userInfo.UserInfoApi;
import com.hotel.service.net.module.userInfo.model.ReqRecieverAddress;
import com.hotel.service.net.module.userInfo.model.ResReciverAddress;
import com.hotel.service.ui.base.BaseFragmentActivity;
import com.hotel.service.util.PropertiesUtil;

import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.RetrofitError;
import rx.Observable;

/**
 * 用户默认选择的收货地址
 * 如果没有选择默认地址，则提示用户增加默认收货地址
 */
public class DefaultRecieverAddressActivity_copy extends BaseFragmentActivity {

    private String TAG = DefaultRecieverAddressActivity_copy.class.getCanonicalName();

    @Inject
    UserInfoApi userInfoApi;

    @InjectView(R.id.recevie_name)
    TextView recevieName;

    @InjectView(R.id.recevie_address)
    TextView recevieAddress;

    @InjectView(R.id.recevie_tel)
    TextView recevieTel;

    @InjectView(R.id.recevie_memo)
    TextView recevieMemo;

    private List<ResReciverAddress.PushBuyersList> pushBuyersList;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.default_reciever_address);

        HotelApp.get(this).inject(this);

    }

    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        getConsigneeAddress();
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
                                Toast.makeText(DefaultRecieverAddressActivity_copy.this, "网络问题", Toast.LENGTH_SHORT).show();
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

    @OnClick(R.id.submit)
    public void submitClick() {
        if (pushBuyersList.size() != 0) {
            Intent intent = new Intent();
            intent.putExtra("recevieAddress", pushBuyersList.get(0).address);
            intent.putExtra("recevieName", pushBuyersList.get(0).name);
            intent.putExtra("recevieTel", pushBuyersList.get(0).phone);
            intent.putExtra("memo", recevieMemo.getText().toString());
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @OnClick(R.id.cancel)
    public void cancelClick() {
        finish();
    }

}
