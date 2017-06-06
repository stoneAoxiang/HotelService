package com.hotel.service.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tools {
    private static String TAG = Tools.class.getCanonicalName();

    private static long lastClickTime;
    private static ArrayList<String> timeList;

    /**
     * 组装要获取图片的地址
     *
     * @param imgUrl
     * @param width
     * @param height
     * @return
     */
    public static String getPicUrl(String imgUrl, String width, String height) {

        String picUrl = null;

        if (null != width) {
            picUrl = Config.HOST_NAME + "/images/getimg?src=" + imgUrl.trim() + "&h=" + height + "&w=" + width;
        } else {
            picUrl = Config.HOST_NAME + "/images/getimg?src=" + imgUrl.trim() + "&h=" + height;
        }

//		Log.i(TAG, "组装要获取图片的地址**********************"+picUrl);

        return picUrl;

    }

    /**
     * 获取利浪服务器的图片
     *
     * @param imgUrl
     * @param width
     * @param height
     * @return
     */
    public static String getPublicPicUrl(String imgUrl, String width, String height) {

        String picUrl = null;

       /* if (null != width) {
            picUrl = Config.PUBLIC_SERVER_URL + "/images/getimg?src=" + imgUrl.trim() + "&h=" + height + "&w=" + width;
        } else {
            picUrl = Config.PUBLIC_SERVER_URL + "/images/getimg?src=" + imgUrl.trim() + "&h=" + height;
        }*/

//		Log.i(TAG, "组装要获取图片的地址**********************"+picUrl);

        return picUrl;

    }

    /**
     * 获取用户的社区ID
     *
     * @return
     */
    public static String getCommuityID() {
        String commuityID = PropertiesUtil.getProperties("communityID");

        if (null == commuityID || commuityID.equals("")) {
            commuityID = ConstantValue.EXPERIENCE_COMMUNITY_ID;
        }
        Log.i(TAG, "获取用户的社区ID==========" + commuityID);
        return commuityID;
    }

    /**
     * 小于800毫秒，认为用户在双击
     *
     * @return
     */
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 800) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * 小于6秒，则认为点击速度过快
     *
     * @return
     */
    public static boolean isFastClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 1000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    public static String getIP(String name) {
        InetAddress address = null;
        try {
            address = InetAddress.getByName(name);
        } catch (UnknownHostException e) {

            e.printStackTrace();
            Log.e(TAG, "******************************域名转化为IP地址失败******************************");

            return null;
        }
        return address.getHostAddress().toString();
    }

    public static ArrayList<HashMap<String, Object>> getData() {
        ArrayList<HashMap<String, Object>> arrayList = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> tempHashMap = new HashMap<String, Object>();
        tempHashMap.put("nullText", "暂时还没有数据");
        arrayList.add(tempHashMap);
        return arrayList;
    }

    /**
     * 验证手机号码
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
//        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,1,5,6,9]))\\d{8}$");     
//        Matcher m = p.matcher(mobiles);    
//        return m.matches();    

        return true;
    }

    /**
     * 验证EMAIL
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 验证身份证后6位
     *
     * @param email
     * @return
     */
    public static boolean isCard(String email) {
        String str = "^((\\d{5})(?:\\d|x|X)$)";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 获取2个时间的差值 单位秒
     * @param beginTimeStr
     * @param endTimeStr
     * @return
     */
    public static long getTimeSub(String beginTimeStr, String endTimeStr) {
        long day = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date beginDate;
        Date endDate;

        try {
            beginDate = format.parse(beginTimeStr);
            endDate = format.parse(endTimeStr);

            day = endDate.getTime() / 1000 - beginDate.getTime() / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day;
    }



    /**
     * 转换为天，时，分，秒
     * @param calculateDate
     * @return
     */
    public static ArrayList<String> surplusLongToStrng(long calculateDate){

        long day1=calculateDate/(24*3600);
        long hour1=calculateDate%(24*3600)/3600;
        long minute1=calculateDate%3600/60;
        long second1=calculateDate%60;

        timeList = new ArrayList<String>();
        timeList.add(day1+"天 ");
        timeList.add(hour1+"小时 ");
        timeList.add(minute1 + "分 ");
        timeList.add(second1 + "秒 ");

        return timeList;
    }

    /**
     * 获取系统时间并转换
     * @return
     */
    public static String getSystemTime(){
        Date date = new Date();
        DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    /**
     * 和系统时间进行比较大小
     * @return
     */
    public static long compareTime(String inputTime){

        long compareValue = 0l;

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //系统当前时间
        Date currentDate = new Date();

        Date d1;
        Date d2;

        try {
            d1 = format.parse(inputTime);
            d2 = format.parse(format.format(currentDate));

            compareValue = d1.getTime()/1000 - d2.getTime()/1000;

//            Log.i(TAG, inputTime+" 和系统时间进行比较大小========"+compareValue);

        }catch (ParseException e) {
            e.printStackTrace();
        }

        return compareValue;
    }

    public static boolean checkNet(Context context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {

                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        Log.i(TAG, "网络连接正常");
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Toast.makeText(context, "无可用网络，请打开网络", Toast.LENGTH_LONG).show();
        return false;
    }

}
