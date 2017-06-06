package com.hotel.service.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

import com.hotel.service.BuildConfig;
import com.hotel.service.R;
import com.hotel.service.logic.ChangeViewListener;
import com.hotel.service.ui.ihome.IHomeFragmentPage;
import com.hotel.service.util.ConstantValue;
import com.hotel.service.util.PropertiesUtil;
import com.hotel.service.util.Tools;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainTabActivity extends FragmentActivity implements ChangeViewListener {

    private String TAG = MainTabActivity.class.getCanonicalName();
    // 定义FragmentTabHost对象
    private FragmentTabHost mTabHost;

    // 定义一个布局
    private LayoutInflater layoutInflater;

    @InjectView(R.id.home_draw_layout)
    DrawerLayout drawerLayout;

    private Class fragmentArray[] = {HomeFragmentPage.class, IHomeFragmentPage.class,
            IHomeFragmentPage.class, AboutMeFragmentPage.class};

    private int mImageViewArray[] = {R.drawable.home_icon_click, R.drawable.bargain_icon_click,
            R.drawable.ihome_icon_click,
            R.drawable.owen_icon_click};

    private String mTextviewArray[] = {ConstantValue.HOME_PAGE_TAB, ConstantValue.BARGAIN_TAB,
            ConstantValue.I_HOME_TAB, ConstantValue.ME_TAB};

    /**
     * 要跳转的导航
     */
    private String jumpTab = ConstantValue.HOME_PAGE_TAB;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.main_tab_layout);
        ButterKnife.inject(this);

        drawerLayout.setDrawerLockMode(BuildConfig.DEBUG ? DrawerLayout.LOCK_MODE_UNLOCKED : DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        jumpTab = getIntent().getStringExtra("jumpTab");

        initView();

        Tools.checkNet(this);
    }

    /**
     * 初始化组件
     */
    private void initView() {
        // 实例化布局对象

        layoutInflater = LayoutInflater.from(this);

        // 实例化TabHost对象，得到TabHost
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        // 得到fragment的个数
        int count = fragmentArray.length;

        for (int i = 0; i < count; i++) {
            // 为每一个Tab按钮设置图标、文字和内容
            TabSpec tabSpec = mTabHost.newTabSpec(mTextviewArray[i])
                    .setIndicator(getTabItemView(i));
            // 将Tab按钮添加进Tab选项卡中
            mTabHost.addTab(tabSpec, fragmentArray[i], null);
            // 设置Tab按钮的背景
//            mTabHost.getTabWidget().getChildAt(i)
//                    .setBackgroundResource(R.color.tab_background_color);
            mTabHost.setCurrentTabByTag(jumpTab);
            mTabHost.setOnTabChangedListener(mTabHost::setCurrentTabByTag);
        }
    }

    /**
     * 给Tab按钮设置图标和文字
     */
    private View getTabItemView(int index) {
        View view = layoutInflater.inflate(R.layout.tab_item_view, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
        imageView.setImageResource(mImageViewArray[index]);
        TextView textView = (TextView) view.findViewById(R.id.textview);
        textView.setText(mTextviewArray[index]);
        return view;
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        ConstantValue.isMore = false;
    }




    long time = 0l;
    @Override
    public void onBackPressed() {

        long timeNow = System.currentTimeMillis();
        if (timeNow - time < 1500) {
            super.onBackPressed();
            String isLogin = PropertiesUtil.getProperties("isLogin");

            Log.i(TAG, "isLogin=====================" + isLogin);
            //如果没有选择记住密码，则删除用户保存的信息
            if (null == isLogin || isLogin.equals("false")) {
                PropertiesUtil.clearProperties(0);
            }
            Intent exit = new Intent(Intent.ACTION_MAIN);
            exit.addCategory(Intent.CATEGORY_HOME);
            exit.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(exit);
            System.exit(0);
        } else {
            time = timeNow;
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
        }
    }
    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 弹出确定退出对话框
            new AlertDialog.Builder(this)
                    .setTitle("退出")
                    .setMessage("确定退出吗？")
                    .setPositiveButton("确定",
                            (dialog, which) -> {
                                String isLogin = PropertiesUtil.getProperties("isLogin");

                                Log.i(TAG, "isLogin=====================" + isLogin);
                                //如果没有选择记住密码，则删除用户保存的信息
                                if (null == isLogin || isLogin.equals("false")) {
                                    PropertiesUtil.clearProperties(0);
                                }

                                Intent exit = new Intent(Intent.ACTION_MAIN);
                                exit.addCategory(Intent.CATEGORY_HOME);
                                exit.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(exit);
                                System.exit(0);
                            })
                    .setNegativeButton("取消",
                            (dialog, which) -> {
                                dialog.cancel();
                            }).show();
            // 这里不需要执行父类的点击事件，所以直接return
            return true;
        }

        return super.onKeyDown(keyCode, event);

    }*/

    @Override
    public void onChangeView(int viewFlag) {

        switch (viewFlag) {
            case ConstantValue.GET_MAIN_TAB:
                changeTabView(ConstantValue.HOME_PAGE_TAB);
                break;
            case ConstantValue.GET_SHOP_CITY_TAB:
                changeTabView(ConstantValue.BARGAIN_TAB);
                break;

            case ConstantValue.GET_IHOME_TAB:
                changeTabView(ConstantValue.I_HOME_TAB);
                break;

            case ConstantValue.GET_MY_HOME_TAB:
                changeTabView(ConstantValue.ME_TAB);
                break;

        }

    }

    private void changeTabView(String tabName) {

        mTabHost.setCurrentTabByTag(tabName);
        jumpTab = tabName;
    }

    private void showLifeView() {
        mTabHost.setCurrentTabByTag("首页");
    }
}
