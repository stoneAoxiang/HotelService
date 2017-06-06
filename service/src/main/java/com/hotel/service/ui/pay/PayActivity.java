package com.hotel.service.ui.pay;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.hotel.service.HotelApp;
import com.hotel.service.net.module.payment.PayApi;
import com.hotel.service.net.module.payment.model.ReqPay;
import com.hotel.service.net.module.payment.model.ResPay;
import com.hotel.service.ui.base.BaseFragmentActivity;
import com.hotel.service.util.PropertiesUtil;
import com.unionpay.UPPayAssistEx;

import javax.inject.Inject;

import butterknife.ButterKnife;
import retrofit.RetrofitError;
import rx.Observable;

public class PayActivity extends BaseFragmentActivity implements Callback {

    private String TAG = PayActivity.class.getCanonicalName();

    private String orderId;

    @Inject
    PayApi payApi;

//    @InjectView(R.id.guide)
//    TextView tv;

    public static final String LOG_TAG = "PayDemo";
    private Context mContext = null;
    private int mGoodsIdx = 0;
    private Handler mHandler = null;
    private ProgressDialog mLoadingDialog = null;

    public static final int PLUGIN_VALID = 0;
    public static final int PLUGIN_NOT_INSTALLED = -1;
    public static final int PLUGIN_NEED_UPGRADE = 2;

    /*****************************************************************
     * mMode参数解释： "00" - 启动银联正式环境 "01" - 连接银联测试环境
     *****************************************************************/
    private final String mMode = "00";
//    private static final String TN_URL_01 = "http://202.101.25.178:8080/sim/gettn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setContentView(R.layout.pay_activity);
        HotelApp.get(this).inject(this);
        ButterKnife.inject(this);

        orderId = getIntent().getStringExtra("orderId");

        mContext = this;
        mHandler = new Handler(this);

//        tv.setTextSize(16);
//        updateTextView(tv);

        getTN();
    }

    private void doStartUnionPayPlugin(Activity activity, String tn, String mode) {

        Toast.makeText(this, "交行返回的tn:" + tn, Toast.LENGTH_SHORT).show();
        // mMode参数解释：
        // 0 - 启动银联正式环境
        // 1 - 连接银联测试环境
        int ret = UPPayAssistEx.startPay(this, null, null, tn, mode);
        if (ret == PLUGIN_NEED_UPGRADE || ret == PLUGIN_NOT_INSTALLED) {
            // 需要重新安装控件
            Log.e(LOG_TAG, " plugin not found or need upgrade!!!");

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("提示");
            builder.setMessage("完成购买需要安装银联支付控件，是否安装？");

            builder.setNegativeButton("确定",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            UPPayAssistEx.installUPPayPlugin(PayActivity.this);
                            dialog.dismiss();
                        }
                    });

            builder.setPositiveButton("取消",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            builder.create().show();

        }
        Log.e(LOG_TAG, "" + ret);
    }

    private void updateTextView(TextView tv) {
        String txt = "接入指南：\n1:拷贝sdk目录下的UPPayAssistEx.jar到libs目录下\n2:获取tn后通过UPPayAssistEx.startPay(...)方法调用控件";
        tv.setText(txt);
    }

    @Override
    public boolean handleMessage(Message msg) {
        Log.e(LOG_TAG, " " + "" + msg.obj);
        if (mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }

        String tn = "";
        if (msg.obj == null || ((String) msg.obj).length() == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("错误提示");
            builder.setMessage("网络连接失败,请重试!");
            builder.setNegativeButton("确定",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            builder.create().show();
        } else {
            tn = (String) msg.obj;
            /*************************************************
             * 步骤2：通过银联工具类启动支付插件
             ************************************************/
            doStartUnionPayPlugin(this, tn, mMode);
        }

        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*************************************************
         * 步骤3：处理银联手机支付控件返回的支付结果
         ************************************************/

        if (data == null) {
            return;
        }

        String msg = "";
        /*
         * 支付控件返回字符串:success、fail、cancel 分别代表支付成功，支付失败，支付取消
         */
        String str = data.getExtras().getString("pay_result");
        if (str.equalsIgnoreCase("success")) {
            msg = "支付成功！";
        } else if (str.equalsIgnoreCase("fail")) {
            msg = "支付失败！";
        } else if (str.equalsIgnoreCase("cancel")) {
            msg = "用户取消了支付";
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("支付结果通知");
        builder.setMessage(msg);
        builder.setInverseBackgroundForced(true);
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        builder.create().show();
    }

    private void getTN() {

        Log.i(TAG, "线程=====" + Thread.currentThread().getName());

        mLoadingDialog = ProgressDialog.show(mContext,
                "", // title
                "正在努力的获取tn中,请稍候...", // message
                true); // 进度是否是不确定的，这只和创建进度条有关

        subscription.add(payApi.getPayTN(
                new ReqPay.Builder()
                        .setUserId(PropertiesUtil.getProperties("userID"))
                        .setOrderId(orderId)
                        .build())
                .doOnError(throwable -> {
                    if (throwable instanceof RetrofitError) {
                        switch (((RetrofitError) throwable).getKind()) {
                            case NETWORK:
                                Toast.makeText(PayActivity.this, "网络问题", Toast.LENGTH_SHORT).show();
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
                .subscribe(this::updateTN));


    }

    private void updateTN(ResPay resPay) {

        Log.i(TAG, "resPay.tn==============" + resPay.tradeTn);
        Message msg = mHandler.obtainMessage();
        msg.obj = resPay.tradeTn;
        mHandler.sendMessage(msg);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        subscription.unsubscribe();
    }

}
