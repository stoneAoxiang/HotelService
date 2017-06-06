package com.hotel.service.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.hotel.service.R;
import com.hotel.service.logic.JsonDataCallback;
import com.hotel.service.logic.PassionlifeBroadcast;
import com.hotel.service.ui.base.BaseFragmentActivity;
import com.hotel.service.util.Config;
import com.hotel.service.util.ConstantValue;
import com.hotel.service.util.PropertiesUtil;
import com.hotel.service.util.Tools;
import com.hotel.service.volley.ApiParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import cn.jpush.android.api.JPushInterface;
import rx.Observable;
import rx.functions.Action1;

/**
 * 启动画面 判断是否是首次加载应 采取读取SharedPreferences的方法
 * 是首次加载，则进入GuideActivity；否则进入MainActivity
 * 3s后自动执行操作
 */
public class BranchActivity extends BaseFragmentActivity implements JsonDataCallback {
    boolean isFirstIn = false;

    private String TAG = BranchActivity.class.getCanonicalName();

    private static final int GO_HOME = 1000;
    private static final int GO_GUIDE = 1001;
    // 延迟3秒
    private static final long BRANCH_DELAY_MILLIS = 3000;

    private static final String SHAREDPREFERENCES_NAME = "first_pref";

    private MyHandler mHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.branch_page);

        Tools.checkNet(this);

        PassionlifeBroadcast.engine(this);

        mHandler = new MyHandler(this);
    }

    @Override
    public void onStart() {

        super.onStart();

        init();

    }

    private void init() {
        // 读取SharedPreferences中需要的数据
        // 使用SharedPreferences来记录程序的使用次数
        SharedPreferences preferences = getSharedPreferences(SHAREDPREFERENCES_NAME, MODE_PRIVATE);

        // 取得相应的值，如果没有该值，说明还未写入，用true作为默认
        isFirstIn = preferences.getBoolean("isFirstIn", true);

        // 判断程序与第几次运行，如果是第一次运行则跳转到引导界面，否则跳转到主界面
        // 使用Handler的postDelayed方法3秒后执行跳转到MainActivity
        subscription.add(Observable.timer(BRANCH_DELAY_MILLIS, TimeUnit.MILLISECONDS).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                mHandler.sendEmptyMessage(!isFirstIn ? GO_HOME : GO_GUIDE);
            }
        }));

    }

    private Response.Listener<String> responseListener() {
        return responeData -> {
            if (null != getPd()) {
                changeDialogStatus();
            }
            try {
                JSONObject jsonObject = new JSONObject(responeData);

                String result = jsonObject.getString("result");

                if (result.equals("300")) {
//                    toastDialog("登录失败", -1);
                    goCustomerHome();

                } else if (result.equals("200")) {
                    //由于要访问大服务，所以在成功登陆戴德梁行后需要再登录利浪大服务
                    loginBigService();

                    goCustomerHome();
                }
            } catch (JSONException e) {

                e.printStackTrace();
            }
        };
    }

    private void loginBigService() {

        JSONObject jsonInfo = new JSONObject();

        try {
            jsonInfo.put("module", "Home");
            jsonInfo.put("method", "login");
            jsonInfo.put("login_name", PropertiesUtil.getProperties("loginName"));
            jsonInfo.put("pwd", PropertiesUtil.getProperties("password"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String info = jsonInfo.toString();
        Log.i(TAG, "大服务登录请求信息：" + info);

        executeRequest(new StringRequest(Method.POST, Config.PUBLIC_SERVER_URL_FULL, new Response.Listener<String>() {
            @Override
            public void onResponse(String responeData) {

                String resultStr = getResultStr(responeData);
                if (resultStr.equals("200")) {
                    //大服务登录成功
                    PropertiesUtil.setProperties("loginBigService", "true");
                }
            }
        }, errorListener()) {
            protected Map<String, String> getParams() {
                return new ApiParams().with("requestValue", info);
            }
        });
    }

    static class MyHandler extends Handler {

        WeakReference<BranchActivity> mActivity;

        MyHandler(BranchActivity activity) {
            mActivity = new WeakReference<BranchActivity>(activity);
        }

        @SuppressLint("HandlerLeak")
        public void handleMessage(Message msg) {

            final BranchActivity theActivity = mActivity.get();

            Bundle bundle = (Bundle) msg.obj;
            String responeData = null;

            if (null != bundle) {
                responeData = (String) bundle.getString("responeData");
            }

            if (null != theActivity.getPd()) {
                theActivity.changeDialogStatus();
            }

            switch (msg.what) {
                case GO_HOME:

                    if (theActivity.isAutoLogin()) {

                        Log.i(theActivity.TAG, "用户自动登录=================");
                        //设置用户已登录
                        ConstantValue.isLogin = true;

                        String houseID = PropertiesUtil.getProperties("houseID");

                        Log.i(theActivity.TAG, "用户houseID=================" + houseID);

                        //如果houseID不为空，则为业主用户
                        if (null != houseID && !houseID.equals("")) {
                            ConstantValue.isMater = true;
                        }

                        //发送登录请求
                        theActivity.setDataDialog();
                        theActivity.executeRequest(new StringRequest(Method.POST, Config.SERVER_URL_FULL, theActivity.responseListener(),
                                theActivity.errorListener()) {
                            protected Map<String, String> getParams() {
                                return new ApiParams().with("requestValue", theActivity.obj2Json());
                            }
                        });

                    } else {
                        Log.i(theActivity.TAG, "用户未自动登录=================");
                        theActivity.goCustomerHome();
                    }

                    break;
                case GO_GUIDE:
                    theActivity.goGuide();
                    break;
            }
        }
    }


    /**
     * 判断用户是否自动登录
     *
     * @return
     */
    private boolean isAutoLogin() {
        String isLogin = PropertiesUtil.getProperties("isLogin");

        Log.i(TAG, "判断用户是否自动登录==================" + isLogin);
        if (null != isLogin && !isLogin.equals("false")) {

            return true;
        }

        return false;
    }

    private String obj2Json() {

        JSONObject jsonInfo = new JSONObject();

        try {
            jsonInfo.put("module", "Function");
            jsonInfo.put("method", "login");
            jsonInfo.put("login_name", PropertiesUtil.getProperties("loginName"));
            jsonInfo.put("pwd", PropertiesUtil.getProperties("password"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String info = jsonInfo.toString();
        Log.i(TAG, "请求信息：" + info);

        return info;

    }

    /**
     * 登录普通用户界面
     */
    private void goCustomerHome() {
        Intent intent = new Intent(BranchActivity.this, MainTabActivity.class);
        BranchActivity.this.startActivity(intent);
        BranchActivity.this.finish();
    }

    /**
     * 登录物业用户界面
     */
    private void goPropertyHome() {

        Toast.makeText(this, "物业用户界面", Toast.LENGTH_LONG).show();
//        Intent intent = new Intent(BranchActivity.this, PropertymanegementActivity.class);
//        BranchActivity.this.startActivity(intent);
//        BranchActivity.this.finish();
    }

    private void goGuide() {
        Intent intent = new Intent(BranchActivity.this, GuidancePageActivity.class);
        BranchActivity.this.startActivity(intent);
        BranchActivity.this.finish();
    }

    @Override
    protected void onPause() {

        super.onPause();
        JPushInterface.onPause(this);
    }

    @Override
    protected void onResume() {

        super.onResume();
        JPushInterface.onResume(this);
    }

    @Override
    public void setJson(Message message) {

        mHandler.sendMessage(mHandler.obtainMessage(message.what,
                message.getData()));
    }
}
