package com.hotel.service.logic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Handler;
import android.util.Log;

import com.hotel.service.util.ConstantValue;
import com.hotel.service.util.PropertiesUtil;
import com.hotel.service.util.Tools;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class PassionlifeBroadcast extends BroadcastReceiver {

    public static Context mContext;

    private static String TAG = PassionlifeBroadcast.class.getCanonicalName();


    @Override
    public void onReceive(Context context, Intent intent) {
        String intentAction = intent.getAction();

        mContext = context;
        if (intentAction.equals(Intent.ACTION_BOOT_COMPLETED)) {
            engine(context);
        }

        if (intentAction.equals(Intent.ACTION_PACKAGE_REPLACED)) {


            String packageName = intent.getDataString();
            String appName = packageName.substring(packageName.indexOf(":") + 1);
            //获取本应用包名
            PackageInfo info = null;
            try {
                info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            } catch (NameNotFoundException e) {

                e.printStackTrace();
            }
            String packageNames = info.packageName;

            if (appName.equals(packageNames)) {
                engine(context);

            }
        }

        if (JPushInterface.isPushStopped(mContext)) {
            Log.i(TAG, "重新启动极光推送服务===================");
            JPushInterface.resumePush(mContext);
            startPushService();
        }


    }

    /**
     * 启动推送服务客户端
     */
    private static void startPushService() {
        Log.i(TAG, "启动推送服务客户端==================");
        mHandler.sendMessage(mHandler.obtainMessage(START_SERVICE));
    }


    public static synchronized void engine(Context context) {

        if (mContext == null) {
            mContext = context;

            startPushService();
        }
    }

    private final static TagAliasCallback mAliasCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "设置别名成功     Set tag and alias success";
                    Log.i(TAG, logs);
                    PropertiesUtil.setProperties("alias", "success");
                    break;

                case 6002:
                    logs = "设置别名失败    60S后重试    Failed to set alias and tags due to timeout. Try again after 60s.";
                    Log.i(TAG, logs);

                    if (Tools.checkNet(mContext)) {
                        mHandler.sendMessageDelayed(mHandler.obtainMessage(GET_FLAG), ConstantValue.PUSH_DELAY_TIME);
                    } else {
                        Log.i(TAG, "无网络    No network");
                    }
                    break;

                default:
                    logs = "未知的别名请求码       Failed with errorCode = " + code;
                    Log.e(TAG, logs);
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(GET_FLAG), ConstantValue.PUSH_DELAY_TIME);
            }

            Log.i(TAG, logs);
        }

    };

    private final static TagAliasCallback mTagsCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "设置标签成功     Set tag and alias success";
                    Log.i(TAG, logs);
                    PropertiesUtil.setProperties("tag", "success");
                    break;

                case 6002:
                    logs = "设置标签失败    60S后重试    Failed to set alias and tags due to timeout. Try again after 60s.";
                    Log.i(TAG, logs);

                    if (Tools.checkNet(mContext)) {
                        mHandler.sendMessageDelayed(mHandler.obtainMessage(GET_FLAG), ConstantValue.PUSH_DELAY_TIME);

                    } else {
                        Log.i(TAG, "无网络    No network");
                    }
                    break;

                default:
                    logs = "未知的标签请求码       Failed with errorCode = " + code;
                    Log.e(TAG, logs);
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(GET_FLAG), ConstantValue.PUSH_DELAY_TIME);
            }
            Log.i(TAG, logs);
        }

    };

    private static void startServiceFunc() {

        if (!Tools.checkNet(mContext)) {
            Log.i(TAG, "网络不通 15S后再次启动服务::::::::::::::::::::::::");
            mHandler.sendMessageDelayed(mHandler.obtainMessage(START_SERVICE), 1000 * 15);

        } else {
            Log.i(TAG, "Receiver.engine() -->startPushService() 启动推送服务客户端::::::::::::::::::::::::");

            //启动极光推送服务
            JPushInterface.setDebugMode(true);  // 设置开启日志,发布时请关闭日志
            JPushInterface.init(mContext);

            Log.i(TAG, "获取默认的alias tag::::::::::::::::::::::::" + PropertiesUtil.getProperties("phone"));

            if ((null == PropertiesUtil.getProperties("alias") || PropertiesUtil.getProperties("alias").equals(""))) {
                mHandler.sendMessage(mHandler.obtainMessage(GET_FLAG));
            } else {

                Log.i(TAG, "推送服务的 标签和别名 已设置::::::::::::::::::::::::");
            }

        }

    }

    public static void setFlagAlias() {
        Log.i(TAG, "重新设置推送服务的 标签和别名::::::::::::::::::::::::");
        mHandler.sendMessage(mHandler.obtainMessage(GET_FLAG));
    }

    private static void getFlag() {

        if (null == PropertiesUtil.getProperties("phone") || PropertiesUtil.getProperties("phone").equals("")) {
            Log.i(TAG, "极光推送用户未登录 =============             ");
            mHandler.sendMessageDelayed(mHandler.obtainMessage(GET_FLAG), ConstantValue.PUSH_DELAY_TIME);
        } else {

            Log.i(TAG, "调用JPush API设置Tag Alias");
            //调用JPush API设置Tag

            //该项目无标签设置，信息直接推动给个人，所以只需要设置Alias
           /* Set<String> tagSet = new LinkedHashSet<String>();

            Log.i(TAG, "获取标签值=============" + (PropertiesUtil.getProperties("communityID")));
            tagSet.add(PropertiesUtil.getProperties("communityID"));


            mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_TAGS, tagSet));*/


            //调用JPush API设置Alias
            Log.i(TAG, "获取极光推送别名值=============" + (PropertiesUtil.getProperties("phone")));
            mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, PropertiesUtil.getProperties("phone")));
        }

    }

    private static final int START_SERVICE = 1000;
    private static final int MSG_SET_ALIAS = 1001;
    private static final int MSG_SET_TAGS = 1002;
    private static final int GET_FLAG = 1003;

    private static class MyHandler extends Handler {

        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {

                case START_SERVICE:

                    startServiceFunc();
                    break;


                case MSG_SET_ALIAS:
                    Log.d(TAG, "设置别名       Set alias in handler." + (String) msg.obj);
                    JPushInterface.setAliasAndTags(mContext, (String) msg.obj, null, mAliasCallback);
                    break;

                case MSG_SET_TAGS:
                    Set<String> tmp = (Set<String>) msg.obj;
                    for (String str : tmp) {
                        Log.d(TAG, "设置标签名 ====================" + str);
                    }

                    Log.d(TAG, "设置标签       Set tags in handler.");
                    JPushInterface.setAliasAndTags(mContext, null, (Set<String>) msg.obj, mTagsCallback);
                    break;

                case GET_FLAG:
                    Log.d(TAG, "获取标识================");
                    getFlag();
                    break;

                default:
                    Log.i(TAG, "位置信息      Unhandled msg - " + msg.what);
            }
        }

    }

    private final static Handler mHandler = new MyHandler();


}
