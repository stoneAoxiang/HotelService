package com.hotel.service.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hotel.service.HotelApp;
import com.hotel.service.R;
import com.hotel.service.adapter.TabContentListAdapter;
import com.hotel.service.logic.ChangeViewListener;
import com.hotel.service.ui.base.BaseFragment;
import com.hotel.service.ui.user.LoginActivity;
import com.hotel.service.ui.user.ModifyPasswordActivity;
import com.hotel.service.util.ConstantValue;
import com.hotel.service.util.DialogHelper;
import com.hotel.service.util.PropertiesUtil;
import com.sharyuke.tool.model.ProgressModel;
import com.sharyuke.tool.update.UpdateManager;

import javax.inject.Inject;

import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

public class AboutMeFragmentPage extends BaseFragment implements OnClickListener {

    private String TAG = AboutMeFragmentPage.class.getCanonicalName();

    private String goActivity;
    private View view;

    private ListView aboutMeLV;

    private TextView userName;
    private TextView address;

    private Intent goIntent;
    private TabContentListAdapter allAdapter;

    private ChangeViewListener callback;

    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);

        try {
            callback = (ChangeViewListener) activity;

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + "必须实现 OnChangeViewListener接口");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.about_me_fragment_view, null);
        HotelApp.get(getActivity()).inject(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if( !ConstantValue.isLogin){
            Intent intent = new Intent(getActivity(),LoginActivity.class);
            startActivity(intent);

            callback.onChangeView(ConstantValue.GET_MAIN_TAB);
        }


        updateManager.setOnStatusUpdateListener(status -> {
            switch (status) {
                case NORMAL:
                    allAdapter.setIsUpdate(false);
                    allAdapter.setIsDownloading(false);
                    allAdapter.notifyDataSetChanged();
                    break;
                case CHECKING:
                    allAdapter.setIsUpdate(true);
                    allAdapter.setIsDownloading(false);
                    allAdapter.notifyDataSetChanged();
                    break;
                case DOWNLOADING:
                    allAdapter.setIsUpdate(false);
                    allAdapter.setIsDownloading(true);
                    allAdapter.notifyDataSetChanged();
                    break;
            }
        }).setOnProgressUpdateListener(this::updateDownloadProgress);
    }

    @Override
    public void onStart() {
        super.onStart();

        initWidget();
    }

    private void initWidget() {
        LinearLayout isLoginTitle = (LinearLayout) view.findViewById(R.id.is_login_title);
        TextView tabName = (TextView) view.findViewById(R.id.tab_name);
        tabName.setText(R.string.my_info);

        TextView userStatus = (TextView) view.findViewById(R.id.user_status);
        userStatus.setOnClickListener(this);


        TextView userHead = (TextView)view.findViewById(R.id.user_head);
        userHead.setText(Html.fromHtml("<u>" + "修改密码" + "</u>"));

        if (ConstantValue.isLogin) {
            isLoginTitle.setVisibility(View.VISIBLE);


            userStatus.setText(R.string.log_off);

            userName = (TextView) view.findViewById(R.id.user_name);
            address = (TextView) view.findViewById(R.id.address);

            String tmp = PropertiesUtil.getProperties("loginName");
            String processName = (null == tmp || tmp.equals("")) ? "未绑定用户" : tmp;

            userName.setText(getText(R.string.user_name) + ":" + processName);
            address.setText(getText(R.string.address) + ":" + PropertiesUtil.getProperties("address"));

        } else {
            isLoginTitle.setVisibility(View.GONE);
            userStatus.setText(R.string.login);
            callback.onChangeView(ConstantValue.GET_MAIN_TAB);
        }

        aboutMeLV = (ListView) view.findViewById(R.id.about_me_lv);
        aboutMeLV.setOnItemClickListener(aboutMeLVClickListener);

        initHomeGV();

    }

    private void initHomeGV() {
        int mImageViewArray[] = {R.mipmap.my_shoppingcart_icon, R.mipmap.order_info,
                R.mipmap.recevie_address, R.mipmap.my_collect_icon,
                R.mipmap.ver_updata_icon, R.mipmap.about_icon};
        String mTextViewArray[] = {"购物车", "订单记录",  "收货地址", "我的收藏", "版本更新", "关于"};
        allAdapter = new TabContentListAdapter(getActivity().getApplicationContext(), mImageViewArray, mTextViewArray);
        aboutMeLV.setAdapter(allAdapter);

        allAdapter.setIsUpdate(UpdateManager.Status.CHECKING == updateManager.getStatus());
        allAdapter.notifyDataSetChanged();
    }

    OnItemClickListener aboutMeLVClickListener = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                long arg3) {

            switch (arg2) {

                case 0:
                    toastDialog("正在升级敬请期待", -1);
//                    goIntent = new Intent(getActivity(), ShoppingCartActivity.class);
//                    startActivity(goIntent);
                    break;

                case 1:
                    toastDialog("正在升级敬请期待", -1);
//                    goIntent = new Intent(getActivity(), OrderInfoActivity.class);
//                    startActivity(goIntent);
                    break;

                case 2:
                    toastDialog("正在升级敬请期待", -1);
                    //goIntent = new Intent(getActivity(), RequirsRecord.class);
//                    goIntent = new Intent(getActivity(), RepairsInfoActivity.class);
//                    startActivity(goIntent);
                    break;

//                case 3:
//                    if (!checkUser()) {
//                        return;
//                    }
//                    goIntent = new Intent(getActivity(), RecieverAddressManagerActivity.class);
//                    startActivity(goIntent);
//                    break;

                case 3:
                    toastDialog("正在升级敬请期待", -1);
//                    goIntent = new Intent(getActivity(), MyCollection.class);
//                    startActivity(goIntent);
                    break;
                case 4:
                    // 检查是否有更新
                    updateManager.checkUpdate(getActivity());
                    break;

                case 5:
                    goIntent = new Intent(getActivity(), AboutPassionlifeActivity.class);
                    startActivity(goIntent);
                    break;

                default:
                    break;
            }

        }

    };

    @OnClick(R.id.user_head)
    public void modifyPasswordClick() {
        goIntent = new Intent(getActivity(), ModifyPasswordActivity.class);
        startActivity(goIntent);
    }

    @Inject
    UpdateManager updateManager;

    public void updateDownloadProgress(ProgressModel progress) {
        allAdapter.setIsUpdate(false);
        allAdapter.setIsDownloading(true);
        allAdapter.setProgress(progress);
        allAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.user_status:

                if (ConstantValue.isLogin) {
                    logOff();
                } else {

                  Intent  intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
//                    goActivity = "LoginActivity";
//                    goActivity();
                    break;
                }


        }

    }

    private void logOff() {
        DialogHelper.Confirm(getActivity(),
                null,
                getText(R.string.affirm_logoff).toString(), getText(R.string.yes).toString(),
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog,
                                        int which) {
                        //清楚用户信息，并停止信息推送服务
                        PropertiesUtil.clearProperties(0);
                        Log.i(TAG, "停止信息推送服务================");
                        JPushInterface.stopPush(getActivity());

                        startActivity(new Intent(getActivity(), LoginActivity.class));
                    }
                }, getText(R.string.no), null, false);
    }

    private void goActivity() {

        Intent intent = null;

        Class cls = null;
        try {
            cls = Class.forName("com.hotel.service.ui." + goActivity);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        intent = new Intent(getActivity(), cls);
        startActivity(intent);
    }
}