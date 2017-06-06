package com.hotel.service.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hotel.service.HotelApp;
import com.hotel.service.R;
import com.hotel.service.ui.base.BaseFragmentActivity;
import com.hotel.service.util.Config;
import com.hotel.service.volley.ApiParams;
import com.hotel.service.volley.RequestManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;

import butterknife.InjectView;


/**
 * 企业简介
 * Created by Administrator on 2016/3/8.
 */
public class CompanyProActivity extends BaseFragmentActivity {

    private String TAG = CompanyProActivity.class.getCanonicalName();
    private TextView activityName;
    private ImageView returnBack;

    private TextView title;
//    private TextView time;
    private WebView content;

    @InjectView(R.id.no_data)
    TextView noData;

    private int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.companypro);
        HotelApp.get(this).inject(this);
        getCompanyPro();
        initWidget();
    }

    private void initWidget() {
        activityName = (TextView) findViewById(R.id.activity_name);
//        activityName.setText();
        activityName.setText("企业简介");

//        time = (TextView) findViewById(R.id.know_time);
        title = (TextView) findViewById(R.id.know_title);
        content = (WebView) findViewById(R.id.know_content);

        returnBack = (ImageView) findViewById(R.id.return_back);
        returnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void getCompanyPro() {
        executeRequest(new StringRequest(Request.Method.POST, Config.SERVER_URL_FULL, responseListener(),
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
            protected Map<String, String> getParams() {
                return new ApiParams().with("requestValue", object2());
            }
        });
    }

    protected void executeRequest(Request<?> request) {
        RequestManager.addRequest(request, this);
        request.setRetryPolicy(
                new DefaultRetryPolicy(
                        50000,//默认超时时间，应设置一个稍微大点儿的，例如本处的500000
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,//默认最大尝试次数
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                )
        );
    }

    /**请求json封装*/
    private String object2()  {
        JSONObject jsonHot=new JSONObject();
        try {
            jsonHot.put("method","getCompanyProfile");
            jsonHot.put("module","Home");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String hotline = jsonHot.toString();
        Log.i(TAG, "发送的请求" + hotline);

        return hotline;
    }
    /**响应请求*/
    private Response.Listener<String> responseListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String responeData) {

                if (responeData.contains("getCompanyProfile")) {
                    updataLeftList(responeData);
                    Log.e(TAG, "产品记录信息=============" + responeData);
                }
            }
        };
    }
    /**解析请求*/
    private void updataLeftList(String responeData){

        try {
            JSONObject jsonObj = new JSONObject(responeData);
            Iterator it = jsonObj.keys();
            String bb = "";
            String aa = "";
            while (it.hasNext()){
                bb = it.next().toString();
                aa = aa+jsonObj.getString(bb);
                Log.e("bb------------",bb);
                Log.e("aa------------",aa);
            }
            JSONArray conList = jsonObj.getJSONArray("CompanyProfile");
            int length = conList.length();

            if(length == 0){
                noData.setVisibility(View.VISIBLE);
                content.setVisibility(View.GONE);
                return;
            }


            String cteattime = "";
            String contents = "";
            String titles="";
            for (i = 0;i<length;i++){
                JSONObject obj = conList.getJSONObject(i);
                cteattime = obj.getString("createTime");
                contents = obj.getString("content");
                titles = obj.getString("title");
            }
            activityName.setText(titles);

//            String str = cteattime ;
//            Date date1 = new Date(str);
//            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date1);
//
//            time.setText(date);
            content.loadUrl(Config.HOST_NAME + contents);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
