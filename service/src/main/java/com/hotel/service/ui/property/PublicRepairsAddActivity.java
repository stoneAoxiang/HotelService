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
import com.hotel.service.net.module.menu.model.ReqRepairsAddress;
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

/**
 * 添加报事报修
 * Created by hyz on 2016/1/13.
 */
public class PublicRepairsAddActivity extends BaseFragmentActivity implements View.OnClickListener, CropHandler {


    private String TAG = PublicRepairsAddActivity.class.getCanonicalName();

    private ArrayList<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
    private DynamicPicAdapter dynamicPicAdapter;


    @OnClick(R.id.return_back)
    public void back() {
        finish();
    }

    @InjectView(R.id.activity_name)
    TextView activityName;


    @OnClick(R.id.take_photo)
    public void OnClick() {
        showPopupWindow();
    }

    @InjectView(R.id.subsmit)
    Button btnSubsmit;

    @InjectView(R.id.repairs_area)
    TextView repairsArea;

    @InjectView(R.id.project_name)
    TextView projectName;

    private String projectId = "";

    @InjectView(R.id.repairs_area_layout)
    LinearLayout repairsAareaLayout;

    @InjectView(R.id.project_name_layout)
    LinearLayout projectNameLayout;

    @InjectView(R.id.project_draw_flag)
    ImageView projectDrawFlag;

    @InjectView(R.id.area_draw_flag)
    ImageView areaDrawFlag;

    @InjectView(R.id.link_man)
    TextView linkMan;

    private String selectFlag;

    private View clickView;

    private SinglePopUnCheckBoxWindowHelper areaPopupWindow;
    private SinglePopUnCheckBoxWindowHelper projectPopupWindow;

    @Inject
    MenuApi menuApi;


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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        CropHelper.handleResult(this, requestCode, resultCode, data);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_property_repairs_add);

        HotelApp.get(this).inject(this);
        initWidget();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private void initWidget() {

        areaPopupWindow = SinglePopUnCheckBoxWindowHelper.newInstance(this, 1);
        projectPopupWindow = SinglePopUnCheckBoxWindowHelper.newInstance(this, 1);

        activityName.setText("添加报事报修");

        repairsCommunity = (EditText) findViewById(R.id.repair_community);

        btnSubsmit.setOnClickListener(this);

        areaPopupWindow.setPopWindowEvent(new SinglePopUnCheckBoxWindowHelper.PopWindowEvent() {
            @Override
            public void onPopWindowSelect(SinglePopUnCheckBoxWindowHelper.DataModel secondModel) {
                String tips = secondModel.name;
                repairsArea.setText(tips);
                areaDrawFlag.setSelected(false);

            }

            @Override
            public Observable<List<SinglePopUnCheckBoxWindowHelper.DataModel>> onPopWindowInitList() {

                return menuApi.getRepairsAddressList(
                        new ReqRepairsAddress.Builder().build())
                        .map(menuList -> menuList.AddressList)
                        .flatMap(Observable::from)
                        .map(spinnerList -> new SinglePopUnCheckBoxWindowHelper.DataModel.Builder()
                                .setName(spinnerList.typeName)
                                .setId("")
                                .build())
                        .toList()
                        .onErrorResumeNext(Observable.empty());
            }
        });

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

    @OnClick(R.id.area_draw_flag)
    public void areaSpinner() {

        areaPopupWindow.showAsDropDown(repairsAareaLayout);
        areaDrawFlag.setSelected(true);
    }

    @OnClick(R.id.project_draw_flag)
    public void projectSpinner() {
        projectPopupWindow.showAsDropDown(projectNameLayout);
        projectDrawFlag.setSelected(true);
    }

    public String obj2Json(int requestFlag) {
        JSONObject jsonInfo = new JSONObject();
        try {
            switch (requestFlag) {


                case ConstantValue.SET_PUBLIC_REPAIRS:
                    jsonInfo.put("module", "Home");
                    jsonInfo.put("method", "setRepairs");
                    jsonInfo.put("userId", PropertiesUtil.getProperties("userID"));
                    jsonInfo.put("address", repairsArea.getText().toString());
                    jsonInfo.put("projectId", projectId);
                    jsonInfo.put("userName", linkMan.getText().toString());
                    jsonInfo.put("content", repairsCommunity.getText().toString());

                    int picCount = picArray.length;
                    Log.i(TAG, "上传图片数量============" + picCount);
                    JSONArray array = new JSONArray();
                    if (picCount == 0) {
                        Log.i(TAG, "没有图片=============================");
                        JSONObject tmp = new JSONObject();
                        tmp.put("img_url", "");
                        array.put(tmp);
                    } else {
                        for (int i = 0; i < picCount; i++) {
//						Log.i(TAG, "img_url============"+picArray[i]);
                            JSONObject tmp = new JSONObject();
                            tmp.put("img_url", picArray[i]);
                            array.put(tmp);
                        }
                    }
                    jsonInfo.put("img_info", array);
                    break;
                default:
                    break;
            }
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

                if (responeData.contains("setRepairs")) {
                    Log.i(TAG, "提交报修信息=============" + responeData);

                    String result = getResultStr(responeData);
                    if (result.equals("200")) {
                        toastDialog("报事报修提交成功", ConstantValue.success);

                        Intent intent = new Intent(getContext(), PublicRepairsActivity.class);
                        startActivity(intent);
                        getContext().finish();

                    } else if (result.equals("300")) {
                        toastDialog("报修提交失败", ConstantValue.failure);
                    }
                }
            }
        };
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.subsmit:

                if (!checkInput()) {
                    return;
                }

                int imgCount = imgIDList.size();

                picArray = new String[imgCount];

                for (int i = 0; i < imgCount; i++) {

                    picArray[i] = imgArray.get(imgIDList.get(i));
                }

                setDataDialog();

                StringRequest sr = new StringRequest(Request.Method.POST, Config.SERVER_URL_FULL, responseListener(),
                        errorListener()) {
                    protected Map<String, String> getParams() {
                        return new ApiParams().with("requestValue", obj2Json(ConstantValue.SET_PUBLIC_REPAIRS));
                    }
                };

                sr.setRetryPolicy(new DefaultRetryPolicy(10000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                executeRequest(sr);

                break;
        }
    }

    private boolean checkInput() {

        if (repairsArea.getText().equals("")) {
            Toast.makeText(this, "请输入区域", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (projectId.equals("")) {
            Toast.makeText(this, "请输入项目", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (linkMan.getText().length() == 0) {
            Toast.makeText(this, "请输入联系人！", Toast.LENGTH_SHORT).show();
            return false;

        } else if (repairsCommunity.getText().length() == 0) {
            Toast.makeText(this, "请输入内容！", Toast.LENGTH_SHORT).show();
            return false;
        }


       /* picArray = new String[imgIDList.size()];            //picArray  有地址，无值
        if (picArray.length == 0 || null == picArray) {

            Toast.makeText(getContext(), "请添加图片", Toast.LENGTH_SHORT).show();
            return false;
        }*/

        return true;

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
        window.showAtLocation(PublicRepairsAddActivity.this.findViewById(R.id.take_photo), Gravity.BOTTOM, 0, 0);

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
                Intent intent = new Intent(PublicRepairsAddActivity.this, ImageActivity.class);
                intent.putExtra("picID", selectImgID);
                startActivity(intent);
            }
        });

    }


}
