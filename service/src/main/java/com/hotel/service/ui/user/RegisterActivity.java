package com.hotel.service.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.hotel.service.R;
import com.hotel.service.bean.UserInfoBean;
import com.hotel.service.ui.MainTabActivity;
import com.hotel.service.ui.base.BaseFragmentActivity;
import com.hotel.service.util.Config;
import com.hotel.service.util.ConstantValue;
import com.hotel.service.util.PropertiesUtil;
import com.hotel.service.volley.ApiParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class RegisterActivity extends BaseFragmentActivity implements OnClickListener {

    private String TAG = RegisterActivity.class.getCanonicalName();

//    private EditText loginName;
    private EditText inputPassword;
    private EditText affirmPassword;
    private Button registerBT;
//    private EditText inviteCode;


    private TextView getVerifyCode;

//    private String loginNameStr;
    private String password;
    private String comparePassword;

    private TextView delayTip;
    private EditText verifyCode;
    private EditText phone;


    //保存用户输入的数据
//    private String companyNameStr;
//    private String propertyNameStr;

    private View tempView;

    //短信校验码
    private String verifyCodeStr = "";

    private Handler handler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.register_info);

        initWidget();

//		mHandler = new MyHandler(this);


    }

    int countTime = 60;
    Runnable runnable = new Runnable() {
        @Override
        public void run() {

//			Log.i(TAG,"剩余" + countTime+"秒");
            delayTip.setText("剩余" + countTime + "秒后重新获取校验码");

            if (countTime-- >= 0) {
                handler.postDelayed(this, 1000);

            } else {
                Log.i(TAG, "重置获取校验码按钮");
                countTime = 60;
                delayTip.setVisibility(View.GONE);
                getVerifyCode.setEnabled(true);
                getVerifyCode.getBackground().setAlpha(255);
                getVerifyCode.setBackgroundResource(R.drawable.red_shape_click_bg);

                handler.removeCallbacks(this);
            }

        }
    };


    private void initWidget() {

        TextView activityName = (TextView) findViewById(R.id.activity_name);
        activityName.setText(R.string.register);

//        loginName = (EditText) findViewById(R.id.login_name);
        inputPassword = (EditText) findViewById(R.id.input_password);
        affirmPassword = (EditText) findViewById(R.id.affirm_password);
        registerBT = (Button) findViewById(R.id.register_bt);
        registerBT.setOnClickListener(this);

        ImageView returnBack = (ImageView) findViewById(R.id.return_back);
        returnBack.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                finish();
            }
        });

        phone = (EditText) findViewById(R.id.phone);
        delayTip = (TextView) findViewById(R.id.delay_tip);
        verifyCode = (EditText) findViewById(R.id.verify_code);

        getVerifyCode = (TextView) findViewById(R.id.get_verify_code);
        getVerifyCode.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                String tmp = phone.getText().toString();

                if (null == tmp || tmp.equals("") || tmp.length() != 11) {
                    Toast.makeText(RegisterActivity.this, "电话号码不正确", Toast.LENGTH_SHORT).show();
                    return;
                }

                Log.i(TAG, "getVerifyCode点击=======================");

//				handler.postDelayed(runnable, 0);


                JSONObject jsonInfo = new JSONObject();

                try {

                    jsonInfo.put("module", "Function");
                    jsonInfo.put("method", "getPhoneValiNum");


                    Log.i(TAG, "获取默认电话号码=======================" + tmp);

//					jsonInfo.put("phonenum",tmp.equals("") ? "18623411568" : tmp);
                    jsonInfo.put("phonenum", tmp);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                final String info = jsonInfo.toString();
                Log.i(TAG, "请求手机验证码信息：" + info);

                StringRequest sr = new StringRequest(Method.POST, Config.SERVER_URL_FULL, responseListener(), errorListener()) {
                    protected Map<String, String> getParams() {
                        return new ApiParams().with("requestValue", info);
                    }
                };

                sr.setRetryPolicy(new DefaultRetryPolicy(10000, 0,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                executeRequest(sr);

            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.register_bt:
//                loginNameStr = loginName.getText().toString();
                password = inputPassword.getText().toString();
                comparePassword = affirmPassword.getText().toString();

                if (!checkInput()) {
                    return;
                }

                setDataDialog();

                executeRequest(new StringRequest(Method.POST, Config.SERVER_URL_FULL, responseListener(),
                        errorListener()) {
                    protected Map<String, String> getParams() {
                        return new ApiParams().with("requestValue", obj2Json(ConstantValue.SET_REGISTER_INFO));
                    }
                });

                //		setRegisterInfo();
                break;
        }

    }

    private boolean checkInput() {


        String tmp = verifyCode.getText().toString();
        if (null == tmp || tmp.equals("")) {
            Toast.makeText(this, "请输入的校验码", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!verifyCodeStr.equals(verifyCode.getText().toString())) {
            Toast.makeText(this, "输入的校验码不一致", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!password.equals(comparePassword)) {
            Toast.makeText(this, "密码不一致，请重新输入", Toast.LENGTH_LONG).show();
            return false;
        }
        if (phone.getText().toString().length() != 11) {
            Toast.makeText(this, "请输入11位手机号码", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;

    }

    private Response.Listener<String> responseListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String responeData) {
                if (null != getPd()) {
                    changeDialogStatus();
                }

                Log.i(TAG, "信息=============" + responeData);


                if (responeData.contains("register")) {

                    try {
                        JSONObject jsonObject = new JSONObject(responeData);

                        String result = jsonObject.getString("result");

                        if (result.equals("300")) {
                            Toast.makeText(RegisterActivity.this, "登录名已存在", Toast.LENGTH_LONG).show();

                        } else if (result.equals("200")) {
                            Toast.makeText(RegisterActivity.this, "注册成功可以使用了", Toast.LENGTH_SHORT).show();
                            saveRegisterToFile(responeData);
                            startActivity(new Intent(RegisterActivity.this, MainTabActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                            finish();
                        } else if (result.equals("301")) {
                            Toast.makeText(RegisterActivity.this, "未知错误", Toast.LENGTH_LONG).show();
                        }

                       /* else if (result.equals("301")) {
                            Toast.makeText(RegisterActivity.this, "邀请码不正确,请重新输入", Toast.LENGTH_SHORT).show();

                        }*/

                    } catch (JSONException e) {

                        e.printStackTrace();
                    }


                } else if (responeData.contains("getPhoneValiNum")) {

                    try {
                        JSONObject jsonObject = new JSONObject(responeData);

                        String result = jsonObject.getString("result");

                        if (result.equals("300")) {
                            Toast.makeText(RegisterActivity.this, "手机号码已被注册，请换号码", Toast.LENGTH_SHORT).show();

                        } else if (result.equals("200")) {

                            displaySwitch();

                            parseVerifyCode(responeData);
                        }

                    } catch (JSONException e) {

                        e.printStackTrace();
                    }


                }

            }
        };
    }

    /**
     * 验证码按钮和计时器的显示隐藏
     */
    private void displaySwitch() {
        delayTip.setVisibility(View.VISIBLE);
        getVerifyCode.setEnabled(false);
        getVerifyCode.getBackground().setAlpha(30);
        handler.postDelayed(runnable, 0);
    }

    /**
     * 保存用户重要信息
     *
     * @param responeData
     */
    private void saveRegisterToFile(String responeData) {

        UserInfoBean t = new UserInfoBean();

        try {
            JSONObject jsonObject = new JSONObject(responeData);
            t.setUserID(jsonObject.getString("userId"));
            t.setLoginName(phone.getText().toString());
            t.setPassword(password);
            t.setPhone(phone.getText().toString());

        } catch (JSONException e) {

            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        PropertiesUtil.setUserFlag(t, false);
    }

    /**
     * 解析校验码
     *
     * @param responeData
     */
    private void parseVerifyCode(String responeData) {

        // {"reason":"短信发送成功","error_code":"0.0","method":"getPhoneValiNum","code":"112995"}

        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(responeData);

            String result = jsonObject.getString("error_code");

            verifyCodeStr = jsonObject.getString("code");

            if (result.equals("0.0")) {

                Log.i(TAG, "校验码成功===================================" + jsonObject.getString("reason"));
                Toast.makeText(this, jsonObject.getString("reason"), Toast.LENGTH_SHORT).show();


            } else {
                Log.i(TAG, "校验码失败===================================" + jsonObject.getString("reason"));
                Toast.makeText(this, jsonObject.getString("reason"), Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {

            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public String obj2Json(int requestFlag) {

        JSONObject jsonInfo = new JSONObject();
        try {

            switch (requestFlag) {

                case ConstantValue.SET_REGISTER_INFO:
                    jsonInfo.put("module", "Function");
                    jsonInfo.put("method", "register");
                    jsonInfo.put("login_name", phone.getText().toString());
//                    jsonInfo.put("nickName", loginNameStr);
//                    jsonInfo.put("inviteCode", inviteCode.getText().toString() + "");
                    jsonInfo.put("pwd", password);

                    break;

                default:
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String info = jsonInfo.toString();
        Log.i(TAG, "请求信息：" + info);

        return info;

    }



}
