package com.hotel.service.volley;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

public class BitmapLruCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache {
	private static BitmapLruCache bitmapLruCache; 
	
	public BitmapLruCache(int maxSize) {
		super(maxSize);
	}

	@Override
	protected int sizeOf(String key, Bitmap bitmap) {
		return bitmap.getRowBytes() * bitmap.getHeight();
	}
	
	public static BitmapLruCache instance(int cacheSize){  
		 if(bitmapLruCache == null){  
			 bitmapLruCache = new BitmapLruCache(cacheSize);  
		 }  
		 return bitmapLruCache;  
	 }

	@Override
	public Bitmap getBitmap(String url) {
		return get(url);
	}

	@Override
	public void putBitmap(String url, Bitmap bitmap) {
		put(url, bitmap);
	}
}
