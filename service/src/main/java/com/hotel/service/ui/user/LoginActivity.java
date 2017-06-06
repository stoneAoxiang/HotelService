package com.hotel.service.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class LoginActivity extends BaseFragmentActivity implements OnClickListener {

    private EditText loginName;
    private EditText password;
    private String TAG = LoginActivity.class.getCanonicalName();
    private String homeLogin;
    private CheckBox savePasswordCB;
    private TextView forgetPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login_page);

        homeLogin = getIntent().getStringExtra("homeLogin");

        Log.i(TAG, "==========================LoginActivity=======================");

        initWidget();

    }

    private void initWidget() {

        TextView activityName = (TextView) findViewById(R.id.activity_name);
        activityName.setText(R.string.login);

        Button userRegister = (Button) findViewById(R.id.regesitor_bt);
        userRegister.setOnClickListener(this);

        loginName = (EditText) findViewById(R.id.login_name);

        String loginNameStr = PropertiesUtil.getProperties("loginName");
        if (null != loginNameStr && !loginNameStr.equals("false")) {

            loginName.setText(loginNameStr);
        }

        password = (EditText) findViewById(R.id.password);

        Button loginBT = (Button) findViewById(R.id.login_bt);
        loginBT.setOnClickListener(this);


        savePasswordCB = (CheckBox) findViewById(R.id.save_password);
        forgetPassword = (TextView) findViewById(R.id.forget_password);
        forgetPassword.setOnClickListener(this);

        ImageView returnBack = (ImageView) findViewById(R.id.return_back);
        returnBack.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                finish();
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == ConstantValue.REGISTER_FIRST && resultCode == RESULT_OK) {

            finish();
        }
    }

    @Override
    public void onClick(View arg0) {

        switch (arg0.getId()) {
            case R.id.regesitor_bt:

                startActivity(new Intent(this, RegisterActivity.class));
                break;


            case R.id.forget_password:

                startActivity(new Intent(this, ForgetPasswordActivity.class));

                break;

            case R.id.login_bt:

                if (loginName.getText().toString().equals("") || password.getText().toString().equals("")) {
                    Toast.makeText(LoginActivity.this, "请输入用户名或密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                setDataDialog();

                executeRequest(new StringRequest(Method.POST, Config.SERVER_URL_FULL, responseListener(),
                        errorListener()) {
                    protected Map<String, String> getParams() {
                        return new ApiParams().with("requestValue", obj2Json());
                    }
                });
                break;

        }
    }

    private Response.Listener<String> responseListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String responeData) {

                Log.i(TAG, "登录返回的数据============================" + responeData);

                if (null != getPd()) {
                    changeDialogStatus();
                }

                if (responeData.contains("login")) {
                    parseUserInfo(responeData);

                }


            }
        };
    }

    public String obj2Json() {

        JSONObject jsonInfo = new JSONObject();

        try {
            jsonInfo.put("module", "Function");
            jsonInfo.put("method", "login");
            jsonInfo.put("login_name", loginName.getText().toString());
            jsonInfo.put("pwd", password.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String info = jsonInfo.toString();
        Log.i(TAG, "请求信息：" + info);

        return info;

    }

    private void parseUserInfo(String responeData) {

        Log.i(TAG, "服务器返回的基本信息===============" + responeData);

        String resultStr = getResultStr(responeData);


        if (resultStr.equals("200")) {

            //由于要访问大服务，所以在成功登陆戴德梁行后需要再登录利浪大服务
            loginBigService();

            processCustomersInfo(responeData);

            //发送广播信息，通知信息推送服务启动
            Log.d(TAG, "发送广播信息，通知信息推送服务启动================");
            Intent i = new Intent("com.hotel.service.jpush");
            sendBroadcast(i);

        } else if (resultStr.equals("300")) {

            Toast.makeText(LoginActivity.this, "账号名或密码错误", Toast.LENGTH_SHORT).show();

        } else if (resultStr.equals("400")) {

            Toast.makeText(LoginActivity.this, "账户已删除，请重新注册", Toast.LENGTH_SHORT).show();

        }
    }

    private void loginBigService() {

        JSONObject jsonInfo = new JSONObject();

        try {
            jsonInfo.put("module", "Home");
            jsonInfo.put("method", "login");
            jsonInfo.put("login_name", loginName.getText().toString());
            jsonInfo.put("pwd", password.getText().toString());

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

    /*
     * 处理普通用户登录
     */
    private void processCustomersInfo(String responeData) {

        UserInfoBean userInfoBean = null;

        try {

            JSONObject jsonObject = new JSONObject(responeData);

            userInfoBean = new UserInfoBean();
            userInfoBean.setUserID(jsonObject.getString("userId"));
            userInfoBean.setFlag(jsonObject.getString("flag"));


        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }

        userInfoBean.setLoginName(loginName.getText().toString());
        userInfoBean.setPassword(password.getText().toString());
        userInfoBean.setPhone(loginName.getText().toString());

        PropertiesUtil.setUserFlag(userInfoBean, savePasswordCB.isChecked());


        //判断是否是从首页进来的
        if (null != homeLogin && homeLogin.equals("y")) {
            Log.i(TAG, "是从首页进来的 ==================");
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);

        } else if (null != homeLogin && homeLogin.equals("lottery")) {
            Intent intent = new Intent(this, MainTabActivity.class);
            this.startActivity(intent);
        }


        finish();
    }

}
