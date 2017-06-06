package com.hotel.service.ui.base;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.hotel.service.ui.user.LoginActivity;
import com.hotel.service.util.ConstantValue;
import com.sharyuke.tool.util.ToastHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import retrofit.RetrofitError;

/**
 * Created by aoxiang on 2016/3/4.
 */
public class BaseFunction {

    private String TAG = BaseFunction.class.getCanonicalName();

    private Context context;

    private HandlerThread thread;
    public TimeOutHandler timeOutHandler;
    public Message msg;

    public ProgressDialog pd;

    private Timer timer = new Timer();
    private TimerTask task;
    private boolean isGetData = false;

    public BaseFunction(Context c) {
        this.context = c;
        initHandle();
    }

    public boolean isGetData() {
        return isGetData;
    }

    public void setIsGetData(boolean isGetData) {
        this.isGetData = isGetData;
    }

    protected void getDataDialog() {
        if (pd == null) {
            pd = new ProgressDialog(context);
            pd.setTitle("获取数据");
        }
        isGetData = false;
        pd.setMessage("正在从服务器获取数据，请稍后....");
        pd.setCancelable(false);
        pd.show();

        setDialogFontSize(pd);
        notifyEven(ConstantValue.REQUEST_DELAY);

    }

    protected void setDataDialog() {
        if (pd == null) {
            pd = new ProgressDialog(context);
            pd.setTitle("提交服务请求");
        }
        isGetData = false;
        pd.setMessage("正在提交服务请求，请稍后....");
        pd.setCancelable(false);
        pd.show();

        setDialogFontSize(pd);
        notifyEven(ConstantValue.REQUEST_DELAY);

    }

    private void setDialogFontSize(Dialog dialog) {
        Window window = dialog.getWindow();
        View view = window.getDecorView();
        setViewFontSize(view, 26);
    }

    private void setViewFontSize(View view, int size) {
        if (view instanceof ViewGroup) {
            ViewGroup parent = (ViewGroup) view;
            int count = parent.getChildCount();
            for (int i = 0; i < count; i++) {
                setViewFontSize(parent.getChildAt(i), size);
            }
        } else if (view instanceof TextView) {
            TextView textview = (TextView) view;
            textview.setTextSize(size);
        }
    }

    /**
     * 收到服务器的响应后，通知ProgressDialog关闭窗口
     */
    public void changeDialogStatus() {
        isGetData = true;
        pd.cancel();
    }

    public void notifyEven(long delay) {

        task = new TimerTask() {

            public void run() {

                if (!isGetData) {
                    pd.cancel();
                    if (msg != null) {
                        msg = timeOutHandler.obtainMessage();
                    }
                    msg.sendToTarget();

                }

            }
        };

        timer.schedule(task, delay);

    }

    private void initHandle() {
        thread = new HandlerThread("TimeOutThread");
        thread.start();
        timeOutHandler = new TimeOutHandler(thread.getLooper());
        msg = timeOutHandler.obtainMessage();

    }

    @SuppressLint("HandlerLeak")
    class TimeOutHandler extends Handler {
        public TimeOutHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {

            showToast("连接服务器超时，请稍后再试");
        }
    }


    protected void showToast(String msg) {
        ToastHelper.get(context).showShort(msg);
    }

    protected void showToast(int msg) {
        ToastHelper.get(context).showShort(msg);
    }


    public String getResultStr(String responeData) {
        JSONObject jsonObject;
        String result = new String();

        try {
            jsonObject = new JSONObject(responeData);

            result = jsonObject.getString("result");

        } catch (JSONException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        return result;
    }

    public boolean checkUser() {
        Log.i(TAG, "是否登录============" + ConstantValue.isLogin);
        if (!ConstantValue.isLogin) {
            showToast("你还没有登录");
            context.startActivity(new Intent(context, LoginActivity.class));
            return false;

        }
        return true;
    }

    public void requestError(Throwable throwable){
        if (throwable instanceof RetrofitError) {
            switch (((RetrofitError) throwable).getKind()) {
                case NETWORK:
                    Toast.makeText(context, "服务器连接不上...", Toast.LENGTH_LONG).show();
                    break;
                case CONVERSION:
                    break;
                case HTTP:
                    break;
                case UNEXPECTED:
                    break;
            }
        }
    }
}
