package com.hotel.service.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hotel.service.R;
import com.hotel.service.adapter.GuidancePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class GuidancePageActivity extends Activity{
	private String TAG = GuidancePageActivity.class.getCanonicalName(); 
	private ViewGroup advHint;
	private ViewPager advVG; 
	
	private int advertCount;
	private ImageView[] tips;
	
	private List<View> views;
	private GuidancePagerAdapter vpAdapter;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.guidance_page); 
		
		advHint = (ViewGroup) findViewById(R.id.adv_hint); 
		advVG = (ViewPager) findViewById(R.id.adv_pager);   
		
//		initFragmentView(getDavertData()); 
		initViews();
		initTipsView(advertCount);
	}
	
	private void initViews() {
		LayoutInflater inflater = LayoutInflater.from(this);

		views = new ArrayList<View>();
		// 初始化引导图片列表

		View v1 = inflater.inflate(R.layout.new_one, null);
		v1.setBackgroundResource(R.mipmap.guid_pic_1);
		v1.findViewById(R.id.activity_start_btn).setVisibility(View.GONE);
		views.add(v1);

		View v2 = inflater.inflate(R.layout.new_one, null);
		v2.setBackgroundResource(R.mipmap.guid_pic_2);
		views.add(v2);
		
		advertCount = views.size();

		// 初始化Adapter
		vpAdapter = new GuidancePagerAdapter(views, this);
		advVG.setAdapter(vpAdapter); 
		
		advVG.setCurrentItem(0);
        advVG.addOnPageChangeListener(new MyOnPageChangeListener());
	} 
	
	public class MyOnPageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageSelected(int arg0) {
        	
        	if(advertCount == 1){
        		advVG.setEnabled(false);
        	}else{
        		advVG.setEnabled(true);
        	}
        	
        	setImageBackground(arg0 % advertCount);
    	}
    	
    	/**
    	 * 设置选中的tip的背景
    	 * @param selectItems
    	 */
    	private void setImageBackground(int selectItems){
    		for(int i=0; i<tips.length; i++){
    			if(i == selectItems){
    				tips[i].setBackgroundResource(R.mipmap.page_indicator_focused);
    			}else{
    				tips[i].setBackgroundResource(R.mipmap.page_indicator_unfocused);
    			}
    		}
    	}

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }
	
	private void initTipsView(int advertCount){
		tips = new ImageView[advertCount];
		for(int i=0; i<tips.length; i++){
			
			ImageView imageView = new ImageView(this);
	    	imageView.setLayoutParams(new LayoutParams(10,10));
	    	tips[i] = imageView;
	    	if(i == 0){
	    		tips[i].setBackgroundResource(R.mipmap.page_indicator_focused);
	    	}else{
	    		tips[i].setBackgroundResource(R.mipmap.page_indicator_unfocused);
	    	}
	    	
	    	LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT,  
                    LayoutParams.WRAP_CONTENT));
	    	layoutParams.leftMargin = 5;
	    	layoutParams.rightMargin = 5;
	    	advHint.addView(imageView, layoutParams);
		}
	} 
}
