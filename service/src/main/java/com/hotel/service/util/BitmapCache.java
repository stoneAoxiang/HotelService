package com.hotel.service.util;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;

import com.android.volley.toolbox.ImageLoader.ImageCache;

public class BitmapCache implements ImageCache {
	private LruCache<String, Bitmap> mCache;
	private static BitmapCache bitmapCache;  
	private String TAG = BitmapCache.class.getCanonicalName();

	public BitmapCache() {
		int maxSize = 4 * 1024 * 1024;
		mCache = new LruCache<String, Bitmap>(maxSize) {
			@Override
			protected int sizeOf(String key, Bitmap bitmap) {
				return bitmap.getRowBytes() * bitmap.getHeight();
			}
		};
	}
	
	 public static BitmapCache instance(){  
		 if(bitmapCache == null){  
			 bitmapCache = new BitmapCache();  
		 }  
		 return bitmapCache;  
	 } 


	@Override
	public Bitmap getBitmap(String url) {
//		Log.i(TAG, "get cache " + url);
		return mCache.get(url);
	}

	@Override
	public void putBitmap(String url, Bitmap bitmap) {
//		Log.i(TAG, "add cache " + url);
		if (bitmap != null) {
			mCache.put(url, bitmap);
		}
	}
}
