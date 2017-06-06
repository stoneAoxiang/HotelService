package com.hotel.service.util;
import android.util.Log;

import com.hotel.service.bean.UserInfoBean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Properties;

public class PropertiesUtil { 
	private static String TAG = PropertiesUtil.class.getCanonicalName();  
	private static final String path = "/data/data/com.hotel.service/userinfo.properties";

	private static String keyValue = ""; 
	
	public static String getProperties(String keyName) {
		Properties props = new Properties();
		try {
			InputStream in = new FileInputStream(getSettingFile());
			props.load(in);
			keyValue = props.getProperty (keyName); 
			
//			Log.i(TAG, "获取的值：：：：：keyValue===="+keyValue);
			
			
		} catch (Exception e1) {

			e1.printStackTrace();
		} 
		return keyValue;
	}

	public static void setProperties(String keyName, String keyValue) {
		Properties props = new Properties();
		try {
			props.load(new FileInputStream(getSettingFile()));
			OutputStream out = new FileOutputStream(getSettingFile());
			
			Enumeration<?> e = props.propertyNames();
			if (e.hasMoreElements()) {
				while (e.hasMoreElements()) {
					String s = (String) e.nextElement();
					if (!s.equals(keyName))
						props.setProperty(s, props.getProperty(s));
				}
			}
			props.setProperty(keyName, null == keyValue ? "" : keyValue);
			props.store(out, null); 
			
//			Log.i(TAG, "将数据写入文件中====================="+keyValue);
		} catch (IOException e) { 
			e.printStackTrace();
		}
	}

	private static File getSettingFile() {
		File setting = new File(path);
		if (!setting.exists())
			try {
				setting.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		return setting;
	}
	
	/**
	 * 
	 * @param flag 0代表普通用户  1代表物业用户
	 */
	public static void clearProperties(int flag){
		switch (flag) {
		case 0:
			clearCustomerProperties();
			break;
			
		case 1:
			clearPropertyProperties();
			break;

		default:
			break;
		}
	}
	
	
	public static void clearPropertyProperties() {
		PropertiesUtil.setProperties("isLogin", "false");  //重置登录状态
    	PropertiesUtil.setProperties("userID", "");
		PropertiesUtil.setProperties("password", "");
		PropertiesUtil.setProperties("phone", "");
		PropertiesUtil.setProperties("roleId", "");
		PropertiesUtil.setProperties("flag", "");
		PropertiesUtil.setProperties("loginBigService", "false");	//重置登录大服务状态

	}
	
	public static void clearCustomerProperties() {
		ConstantValue.isLogin = false; 
		ConstantValue.isMater = false;
    	
    	//设置自动登录信息标记 用户登录信息
    	PropertiesUtil.setProperties("isLogin", "false");
    	PropertiesUtil.setProperties("userID", "");
		PropertiesUtil.setProperties("password", "");
		PropertiesUtil.setProperties("phone", "");
		PropertiesUtil.setProperties("alias", "");
        PropertiesUtil.setProperties("tag", "");
		PropertiesUtil.setProperties("flag", "");
		PropertiesUtil.setProperties("loginBigService", "false");
	}

	/**
	 * 保存用户名ID，密码
	 */
	public static void setUserFlag(UserInfoBean userInfoBean, boolean saveFlag) {
		//标记为登录用户
		ConstantValue.isLogin = true;

		Log.i(TAG, " 用户ID存入文件中  :::::::::::::::" + userInfoBean.getUserID());

		//用户ID, 登录名、 密码存入文件中，方便每次使用
		PropertiesUtil.setProperties("userID", userInfoBean.getUserID());
		PropertiesUtil.setProperties("loginName", userInfoBean.getLoginName());
		PropertiesUtil.setProperties("password", userInfoBean.getPassword());
		PropertiesUtil.setProperties("phone", userInfoBean.getLoginName());
		PropertiesUtil.setProperties("flag", userInfoBean.getFlag());

		//是否保存密码自动登录
		PropertiesUtil.setProperties("isLogin", saveFlag + "");

	}

}