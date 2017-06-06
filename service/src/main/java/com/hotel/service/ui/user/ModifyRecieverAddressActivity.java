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
import com.hotel.service.net.module.market.model.ReqMenuList;
import com.hotel.service.ui.base.BaseFragmentActivity;
import com.hotel.service.util.Config;
import com.hotel.service.util.PropertiesUtil;
import com.hotel.service.volley.ApiParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 账户信息
 */
public class ModifyRecieverAddressActivity extends BaseFragmentActivity {

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
    TextView saveAddress;

    private String recevieAddressStr;
    private String recevieNameStr;
    private String recevieTelStr;

    private String TAG = ModifyRecieverAddressActivity.class.getCanonicalName();

    private Intent info;
    private String addressID;
    private String isDefault;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add_reciever_address);

        info = getIntent();

        initWidget();
    }

    private void initWidget() {

        activityName.setText("修改收货地址");

        recevieAddress.setText(info.getStringExtra("recevieAddress"));
        recevieName.setText(info.getStringExtra("recevieName"));
        recevieTel.setText(info.getStringExtra("recevieTel"));

        saveAddress.setText("修改地址");

        String isDefault = info.getStringExtra("isDefault");
        if (isDefault.equals("d")) {
            defaultChecked.setChecked(true);

        } else {
            defaultChecked.setChecked(false);
        }

        addressID = info.getStringExtra("addressID");

        returnBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                finish();
            }
        });

    }

    @OnClick(R.id.save_address)
    public void addAddress() {

        if (!checkInput()) {
            return;
        }

        JSONObject jsonInfo = new JSONObject();

        try {
            jsonInfo.put("module", ReqMenuList.MODULE_BUSINESS);
            jsonInfo.put("method", "pushBuyersInfo");
            jsonInfo.put("userid", PropertiesUtil.getProperties("userID"));
            jsonInfo.put("type", "U");
            jsonInfo.put("name", recevieName.getText().toString());
            jsonInfo.put("phone", recevieTel.getText().toString());
            jsonInfo.put("address", recevieAddress.getText().toString());
            jsonInfo.put("isDefault", defaultChecked.isChecked() ? "d" : "");
            jsonInfo.put("id", addressID);

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

                String result = getResultStr(responeData);

                if (result.equals("200")) {
                    Toast.makeText(ModifyRecieverAddressActivity.this, "修改地址成功", Toast.LENGTH_SHORT).show();

                    Log.i(TAG, "修改成功============================" + responeData);
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();

                } else if (result.equals("300")) {
                    Toast.makeText(ModifyRecieverAddressActivity.this, "修改地址失败", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "修改失败============================" + responeData);
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
