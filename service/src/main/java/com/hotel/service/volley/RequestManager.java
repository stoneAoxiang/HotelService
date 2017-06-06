package com.hotel.service.volley;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import timber.log.Timber;

public class RequestManager {
    private static RequestQueue mRequestQueue;
    //	private static ImageLoader mImageLoader;
    public static ImageLoader mImageLoader;

    private static String TAG = RequestManager.class.getCanonicalName();

    private RequestManager() {
        // no instances
    }

    @SuppressLint("NewApi")
    public static void init(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);

        int memClass = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
        Timber.d("image loader 可用内存空间:" + memClass);
        int cacheSize = 16 * 1024 * 1024;

        Timber.d("image loader 分配缓存空间:" + cacheSize);
        mImageLoader = new ImageLoader(mRequestQueue, new BitmapLruCache(cacheSize));
    }

    public static RequestQueue getRequestQueue() {
        if (mRequestQueue != null) {
            return mRequestQueue;
        } else {
            throw new IllegalStateException("RequestQueue not initialized");
        }
    }

    public static void addRequest(Request<?> request, Object tag) {
        if (tag != null) {
            request.setTag(tag);
        }
//        Log.i(TAG, "调用VOLLEY加入请求队列==================");
        mRequestQueue.add(request);
    }

    public static void cancelAll(Object tag) {
        Log.i(TAG, "移除监听===========================" + tag.getClass().getName());
        mRequestQueue.cancelAll(tag);
    }

    /**
     * Returns instance of ImageLoader initialized with {@see FakeImageCache}
     * which effectively means that no memory caching is used. This is useful
     * for images that you know that will be show only once.
     *
     * @return
     */
    public static ImageLoader getImageLoader() {
        if (mImageLoader != null) {
            return mImageLoader;
        } else {
            throw new IllegalStateException("ImageLoader not initialized");
        }
    }
}
