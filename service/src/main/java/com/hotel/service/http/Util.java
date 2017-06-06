package com.hotel.service.http;

import android.content.Context;
import android.os.Environment;

public class Util {
	private static Util util;
	public static int flag = 0;
	private Util(){
		
	}
	
	public static Util getInstance(){
		if(util == null){
			util = new Util();
		}
		return util;
	}
	
	/**
	 * 判断是否有sdcard
	 * @return
	 */
	public boolean hasSDCard(){
		boolean b = false;
		if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
			b = true;
		}
		return b;
	}
	
	/**
	 * 得到sdcard路径
	 * @return
	 */
	public String getExtPath(){
		String path = "";
		if(hasSDCard()){
			path = Environment.getExternalStorageDirectory().getPath();
		}
		return path;
	}
	
	/**
	 * @param mActivity
	 * @return
	 */
	public String getPackagePath(Context context){
		return context.getFilesDir().toString();
	}

	/**
	 * 根据url得到图片�?
	 * @param url
	 * @return
	 */
	public String getImageName(String url) {
		String imageName = "";
		if(url != null){
			imageName = url.substring(url.lastIndexOf("/") + 1);
		}
		return imageName;
	}
}
