package com.hotel.service.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.hotel.service.R;
import com.hotel.service.ui.base.BaseFragmentActivity;
import com.hotel.service.util.Config;
import com.hotel.service.util.PropertiesUtil;
import com.hotel.service.util.Tools;
import com.hotel.service.volley.ApiParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import butterknife.InjectView;

/**
 * 新建收货地址
 */
public class AddRecieverAddressActivity extends BaseFragmentActivity {

    @InjectView(R.id.return_back)
    ImageView returnBack;

    @InjectView(R.id.activity_name)
    TextView activityName;

    @InjectView(R.id.recevie_address)
    EditText recevieAddress;

    @InjectView(R.id.recevie_name)
    EditText recevieName;

    @InjectView(R.id.recevie_tel)
    EditText recevieTel;

    @InjectView(R.id.default_checked)
    CheckBox defaultChecked;

    @InjectView(R.id.save_address)
    TextView submit;

    private String recevieAddressStr;
    private String recevieNameStr;
    private String recevieTelStr;

    private String TAG = AddRecieverAddressActivity.class.getCanonicalName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add_reciever_address);

        initWidget();
    }

    private void initWidget() {

        activityName.setText("新建收货地址");

        returnBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                finish();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!checkInput()) {
                    return;
                } else {

                    if(Tools.isFastDoubleClick()){
                        return;
                    }
                    addAddress();
                }

            }
        });
    }

    private void addAddress(){

        JSONObject jsonInfo = new JSONObject();

        try {
            jsonInfo.put("module", "Business");
            jsonInfo.put("method", "pushBuyersInfo");
            jsonInfo.put("userid", PropertiesUtil.getProperties("userID"));
            jsonInfo.put("type", "C");
            jsonInfo.put("name", recevieNameStr);
            jsonInfo.put("phone", recevieTelStr);
            jsonInfo.put("address", recevieAddressStr);
            jsonInfo.put("isDefault", defaultChecked.isChecked() ? "d":"");

            jsonInfo.put("id", "");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String info = jsonInfo.toString();
        Log.i(TAG, "请求信息：" + info);
        executeRequest(new StringRequest(Request.Method.POST, Config.PUBLIC_SERVER_URL_FULL, responseListener(),
                errorListener()) {
            protected Map<String, String> getParams() {
                return new ApiParams().with("requestValue", info);
            }
        });
    }

    private Response.Listener<String> responseListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String responeData) {
                if (null != getPd()) {
                    changeDialogStatus();
                }

                Log.i(TAG, "响应的数据=================="+responeData);

                String resultStr = getResultStr(responeData);

                if(resultStr.equals("200")){
                    Toast.makeText(AddRecieverAddressActivity.this, "增加地址成功", Toast.LENGTH_SHORT).show();

                    Log.i(TAG, "增加成功============================" + responeData);
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();

                }else if(resultStr.equals("300")){
                    Toast.makeText(AddRecieverAddressActivity.this, "增加地址成功", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "增加失败============================" + responeData);
                }
            }
        };
    }

    private boolean checkInput() {

        recevieAddressStr = recevieAddress.getText().toString();
        recevieNameStr = recevieName.getText().toString();
        recevieTelStr = recevieTel.getText().toString();

        if (recevieAddressStr.equals("")) {
            Toast.makeText(this, "收货地址不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (recevieNameStr.equals("")) {
            Toast.makeText(this, "收货人姓名不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (recevieTelStr.equals("") || recevieTelStr.length() < 11) {
            Toast.makeText(this, "电话为空或不足11位", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }



}
