package com.hotel.service.ui.user;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.hotel.service.R;
import com.hotel.service.ui.base.BaseFragmentActivity;
import com.hotel.service.util.Config;
import com.hotel.service.util.ConstantValue;
import com.hotel.service.util.PropertiesUtil;
import com.hotel.service.volley.ApiParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;


public class ModifyPasswordActivity extends BaseFragmentActivity{

    private EditText originalPassword;
    private EditText newPassword;
    private EditText affirmPassword;
    private AlertDialog builder;

    private String originalPasswordStr;
    private String newPasswordStr;
    private String affirmPasswordStr;

    private String TAG = ModifyPasswordActivity.class.getCanonicalName();

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.modify_password_view);

        modifyPassword();

    }


    private Response.Listener<String> responseListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String responeData) {

                if (responeData.contains("modifypasswordInfo")) {
                    updataPassword(responeData);
                }
            }
        };
    }


    /**
     * 修改密码
     **/
    private void updataPassword(String responeData) {
        String result = getResultStr(responeData);

        if (result.equals("200")) {
            Toast.makeText(this, "修改密码成功", Toast.LENGTH_SHORT).show();
            PropertiesUtil.setProperties("password", newPasswordStr);
            finish();
        } else {
            Toast.makeText(this, "修改密码失败", Toast.LENGTH_SHORT).show();
        }
    }


    private void modifyPassword() {


        builder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.modify_password, (ViewGroup) findViewById(R.id.dialog));
        builder.setView(layout);
        builder.show();
        Window window = builder.getWindow();
        window.setContentView(R.layout.modify_password);

        originalPassword = (EditText) window.findViewById(R.id.original_password);
        newPassword = (EditText) window.findViewById(R.id.new_password);
        affirmPassword = (EditText) window.findViewById(R.id.affirm_password);

        Button submit = (Button) window.findViewById(R.id.submit);
        submit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                originalPasswordStr = originalPassword.getText().toString();
                newPasswordStr = newPassword.getText().toString();
                affirmPasswordStr = affirmPassword.getText().toString();

                if (originalPasswordStr.equals("") || newPasswordStr.equals("") || affirmPasswordStr.equals("")) {
                    Toast.makeText(ModifyPasswordActivity.this, "输入项不能为空", Toast.LENGTH_LONG).show();

                } else if (!getPassword().equals(originalPasswordStr)) {
                    Toast.makeText(ModifyPasswordActivity.this, "输入的原密码不正确", Toast.LENGTH_LONG).show();

                } else if (!newPasswordStr.equals(affirmPasswordStr)) {
                    Toast.makeText(ModifyPasswordActivity.this, "新密码不一致", Toast.LENGTH_LONG).show();

                } else {
                    requestModifyPassword();
                    builder.cancel();
                }

            }
        });

        Button cancel = (Button) window.findViewById(R.id.cancel);
        cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                builder.cancel();
                finish();
            }
        });

    }

    private void requestModifyPassword() {
        executeRequest(new StringRequest(Method.POST, Config.SERVER_URL_FULL, responseListener(),
                errorListener()) {
            protected Map<String, String> getParams() {
                return new ApiParams().with("requestValue", obj2Json(ConstantValue.MODIFY_PASSWORD_INFO));
            }
        });
    }

    public String obj2Json(int requestFlag) {
        JSONObject jsonInfo = new JSONObject();

        try {
            jsonInfo.put("module", "Function");
            jsonInfo.put("method", "getPersonDetailInfo");
            jsonInfo.put("userid", PropertiesUtil.getProperties("userID"));

            if (requestFlag == ConstantValue.MODIFY_PASSWORD_INFO) {
                jsonInfo.put("method", "modifypasswordInfo");
                jsonInfo.put("new_password", newPasswordStr);
            }

        } catch (JSONException e) {

            e.printStackTrace();
        }

        String info = jsonInfo.toString();
        Log.i(TAG, "个人详细信息请求：" + info);

        return info;
    }


    private String getPassword() {
        String result = PropertiesUtil.getProperties("password");
        return result;
    }


}
