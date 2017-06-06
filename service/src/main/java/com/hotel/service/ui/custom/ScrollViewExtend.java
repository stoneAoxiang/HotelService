package com.hotel.service.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class ScrollViewExtend extends ScrollView {
	private String TAG = ScrollViewExtend.class.getCanonicalName();

	private float xDistance, yDistance, xLast, yLast;

	public ScrollViewExtend(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			Log.i(TAG, "ScrollView拦截，ACTION_DOWN==========================");
			xDistance = yDistance = 0f;
			xLast = ev.getX();
			yLast = ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			Log.i(TAG, "ScrollView拦截，ACTION_MOVE==========================");
			final float curX = ev.getX();
			final float curY = ev.getY();

			xDistance += Math.abs(curX - xLast);
			yDistance += Math.abs(curY - yLast);
			xLast = curX;
			yLast = curY;

			Log.i(TAG, "ScrollView拦截事件 xDistance=========================="+xDistance);
			Log.i(TAG, "ScrollView拦截事件 yDistance=========================="+yDistance);
			
			if (xDistance > yDistance) {
				Log.i(TAG, "ScrollView不拦截，向子控件传递==========================");
				return false;
//				return super.onInterceptTouchEvent(ev);
			}

			break; 
			
		case MotionEvent.ACTION_UP:
			Log.i(TAG, "ScrollView拦截事件，ACTION_UP ==========================");
			break;
		case MotionEvent.ACTION_CANCEL:
			
			Log.i(TAG, "ScrollView拦截事件，ACTION_CANCEL==========================");
//			return false; 
			break;
			
		}
//		Log.i(TAG, "ScrollView拦截，不向子控件传递==========================");

		return super.onInterceptTouchEvent(ev); 
	} 
	
}
