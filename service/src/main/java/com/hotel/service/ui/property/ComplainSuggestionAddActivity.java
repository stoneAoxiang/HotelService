package com.hotel.service.ui.property;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.hotel.service.HotelApp;
import com.hotel.service.R;
import com.hotel.service.adapter.DynamicPicAdapter;
import com.hotel.service.net.module.menu.MenuApi;
import com.hotel.service.net.module.menu.model.ReqRepairsProject;
import com.hotel.service.ui.base.BaseFragmentActivity;
import com.hotel.service.ui.widget.SinglePopUnCheckBoxWindowHelper;
import com.hotel.service.util.Base64Coder;
import com.hotel.service.util.Config;
import com.hotel.service.util.ConstantValue;
import com.hotel.service.util.PropertiesUtil;
import com.hotel.service.volley.ApiParams;

import org.hybridsquad.android.library.CropHandler;
import org.hybridsquad.android.library.CropHelper;
import org.hybridsquad.android.library.CropParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnClick;
import rx.Observable;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * 添加投诉建议
 * Created by hyz on 2016/1/13.
 */
public class ComplainSuggestionAddActivity extends BaseFragmentActivity implements View.OnClickListener, CropHandler {


    private String TAG = ComplainSuggestionAddActivity.class.getCanonicalName();

    private ArrayList<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
    private DynamicPicAdapter dynamicPicAdapter;


    @OnClick(R.id.return_back)
    public void back() {
        finish();
    }

    @InjectView(R.id.activity_name)
    TextView activityName;

    @InjectView(R.id.project_name_layout)
    LinearLayout projectNameLayout;

    @InjectView(R.id.project_draw_flag)
    ImageView projectDrawFlag;

    @InjectView(R.id.project_name)
    TextView projectName;

    private String projectId = "";

    private SinglePopUnCheckBoxWindowHelper projectPopupWindow;

    @OnClick(R.id.take_photo)
    public void OnClick() {
        showPopupWindow();
    }

    @InjectView(R.id.commit)
    Button btnSubsmit;

    private String txt;     //输入的内容

    private EditText repairsCommunity;
    /**
     * 保存图片字符串
     */
    private String[] picArray;

    private CropParams mCropParams = new CropParams();
    /**
     * 动态给图片添加布局
     */
    @InjectView(R.id.photo_img_layout)
    GridView imgLayout;

    /**
     * 给ImageView设置ID;
     */
    private int imgID;
    /**
     * 保存图片的ID
     */
    public ArrayList<Integer> imgIDList = new ArrayList<Integer>();
    /**
     * 图像以String格式
     */
    public String imgString;
    /**
     * 图像以HashMap格式保存
     */
    public HashMap<Integer, String> imgArray = new HashMap<Integer, String>();

    @Inject
    MenuApi menuApi;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        CropHelper.handleResult(this, requestCode, resultCode, data);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_complain_suggestion_add);

        HotelApp.get(this).inject(this);

        initWidget();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void initWidget() {

        activityName.setText("添加投诉建议");

        repairsCommunity = (EditText) findViewById(R.id.repair_community);

        btnSubsmit.setOnClickListener(this);

        projectPopupWindow = SinglePopUnCheckBoxWindowHelper.newInstance(this, 1);

        projectPopupWindow.setPopWindowEvent(new SinglePopUnCheckBoxWindowHelper.PopWindowEvent() {
            @Override
            public void onPopWindowSelect(SinglePopUnCheckBoxWindowHelper.DataModel secondModel) {
                String tips = secondModel.name;
                projectName.setText(tips);
                projectId = secondModel.id;
                projectDrawFlag.setSelected(false);

            }

            @Override
            public Observable<List<SinglePopUnCheckBoxWindowHelper.DataModel>> onPopWindowInitList() {

                return menuApi.getRepairsProject(
                        new ReqRepairsProject.Builder().build())
                        .map(menuList -> menuList.ProjectList)
                        .flatMap(Observable::from)
                        .map(spinnerList -> new SinglePopUnCheckBoxWindowHelper.DataModel.Builder()
                                .setName(spinnerList.projectName)
                                .setId(spinnerList.projectId)
                                .build())
                        .toList()
                        .onErrorResumeNext(Observable.empty());
            }
        });

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.commit:
                int imgCount = imgIDList.size();
                picArray = new String[imgCount];
                if (picArray.length > 4) {
                    Toast.makeText(getContext(), "最多添加4张图片", Toast.LENGTH_SHORT).show();
                }
                for (int i = 0; i < imgCount; i++) {
                    picArray[i] = imgArray.get(imgIDList.get(i));
                }
                txt = repairsCommunity.getText().toString();
                if (txt.length() == 0) {
                    Toast.makeText(this, "请输入您的建议！", LENGTH_SHORT).show();
                    break;
                }

                setDataDialog();

                StringRequest sr = new StringRequest(Request.Method.POST, Config.SERVER_URL_FULL, responseListener(),
                        errorListener()) {
                    protected Map<String, String> getParams() {
                        return new ApiParams().with("requestValue", obj2Json());
                    }
                };

                sr.setRetryPolicy(new DefaultRetryPolicy(10000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                executeRequest(sr);

                break;
        }

    }


    public String obj2Json() {
        JSONObject jsonInfo = new JSONObject();
        try {
            jsonInfo.put("module", "Function");
            jsonInfo.put("method", "addUserSuggestions");
            jsonInfo.put("projectId", projectId);
            jsonInfo.put("userId", PropertiesUtil.getProperties("userID"));
            jsonInfo.put("content", txt);

            int picCount = picArray.length;
            JSONArray array = new JSONArray();
            if (picCount == 0) {
                JSONObject tmp = new JSONObject();
                tmp.put("img_url", "");
                array.put(tmp);
            } else {
                for (int i = 0; i < picCount; i++) {
                    JSONObject tmp = new JSONObject();
                    tmp.put("img_url", picArray[i]);
                    array.put(tmp);
                }
            }
            jsonInfo.put("img_info", array);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String info = jsonInfo.toString();
        Log.i(TAG, "发送请求信息：" + info);
        return info;

    }

    private Response.Listener<String> responseListener() {

        return new Response.Listener<String>() {
            @Override
            public void onResponse(String responeData) {
                if (null != getPd()) {
                    changeDialogStatus();
                }
                if (responeData.contains("addUserSuggestions")) {

                    if(getPd() != null){
                        changeDialogStatus();
                    }

                    Log.i(TAG, "提交报修信息=============" + responeData);
                    if (responeData.contains("200")) {
                        toastDialog("建议提交成功", ConstantValue.success);

                        Intent intent = new Intent(getContext(), ComplainSuggestActivity.class);
                        startActivity(intent);
                        getContext().finish();

                    } else if (responeData.contains("300")) {
                        toastDialog("建议提交失败", ConstantValue.failure);
                    }
                }
            }
        };
    }

    @OnClick(R.id.project_draw_flag)
    public void projectSpinner() {
        projectPopupWindow.showAsDropDown(projectNameLayout);
        projectDrawFlag.setSelected(true);
    }

    private void showPopupWindow() {

        // 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.photo_popwindow, null);

        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()

        PopupWindow window = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);

        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        window.setBackgroundDrawable(dw);

        //设置popwindow的显示和消失动画
        window.setAnimationStyle(R.style.mypopwindow_anim_style);
        //在底部显示
        window.showAtLocation(ComplainSuggestionAddActivity.this.findViewById(R.id.take_photo), Gravity.BOTTOM, 0, 0);

        //点击按钮
        view.findViewById(R.id.from_phone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mCropParams.buildUri();

                Log.i(TAG, "获取图片保存的地址================================" + mCropParams.uri);
                startActivityForResult(CropHelper.buildCropFromGalleryIntent(mCropParams), CropHelper.REQUEST_CROP);

                window.dismiss();
            }
        });
        view.findViewById(R.id.from_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCropParams.buildUri();

                Log.i(TAG, "保存照片的地址================================" + mCropParams.uri);
                Intent intent = CropHelper.buildCaptureIntent(mCropParams.uri);
                startActivityForResult(intent, CropHelper.REQUEST_CAMERA);

                window.dismiss();
            }
        });
        view.findViewById(R.id.call_off).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                window.dismiss();
            }
        });

        //popwindow消失监听方法
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

                window.dismiss();
//                Toast.makeText(getContext(), "popwindow关闭", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public Activity getContext() {
        return this;
    }

    @Override
    public void onCropCancel() {
//        Toast.makeText(this, "Crop canceled!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCropFailed(String message) {
//        Toast.makeText(this, "Crop failed:" + message, Toast.LENGTH_LONG).show();
    }

    @Override
    public CropParams getCropParams() {
        return mCropParams;
    }

    @Override
    public void onPhotoCropped(Uri uri) {
        Log.i(TAG, "Crop Uri in path: " + uri.getPath());
        Bitmap photo = CropHelper.decodeUriAsBitmap(this, mCropParams.uri);
        Log.i(TAG, "读取剪切后的图片========================" + photo);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.JPEG, 20, stream);
        imgID = (int) System.currentTimeMillis();

        byte[] b = stream.toByteArray();
        // 将图片流以字符串形式存储下来
        imgString = new String(Base64Coder.encodeLines(b));
        imgIDList.add(imgID);
        imgArray.put(imgID, imgString);
        int count = imgArray.size();
        Log.i(TAG, "图片数	===========" + count);

        Log.i(TAG, "imgID=======================================" + imgID);
        Map<String, Object> item = new HashMap<String, Object>();
        item.put("imageItem", photo);
        item.put("imgID", imgID);
        item.put("imgSourceID", uri.getPath());
        items.add(item);
        dynamicPicAdapter = new DynamicPicAdapter(this, items);
        imgLayout.setAdapter(dynamicPicAdapter);

        imgLayout.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                new AlertDialog.Builder(getContext())
                        .setTitle("是否删除图片...")
                        .setNegativeButton("是", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                dialog.cancel();
                                Map<String, Object> temp = (Map<String, Object>) dynamicPicAdapter.getItem(position);
                                int selectImgID = (Integer) temp.get("imgID");
                                imgArray.remove(selectImgID);
                                for (int i = 0; i < imgIDList.size(); i++) {
                                    if (imgIDList.get(i).intValue() == selectImgID) {
                                        imgIDList.remove(i);
                                        items.remove(i);
                                        dynamicPicAdapter.notifyDataSetChanged();
                                        break;
                                    }
                                }
                            }
                        })
                        .setPositiveButton("否", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                                dialog.cancel();
//                                mCropParams = new CropParams();
//                                Log.i(TAG, "保存照片的地址================================" + mCropParams.uri);
//                                Intent intent = CropHelper.buildCaptureIntent(mCropParams.buildUri());
//                                startActivityForResult(intent, CropHelper.REQUEST_CAMERA);
                            }
                        }).show();

                return true;
            }
        });

        imgLayout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Map<String, Object> temp = (Map<String, Object>) dynamicPicAdapter.getItem(position);
                String selectImgID = (String) temp.get("imgSourceID");

                Log.i(TAG, "图片id	1===========" + items.get(position).get("imgSourceID"));
                Log.i(TAG, "图片id	2===========" + selectImgID);
                Intent intent = new Intent(ComplainSuggestionAddActivity.this, ImageActivity.class);
                intent.putExtra("picID", selectImgID);
                startActivity(intent);
            }
        });

    }
}
