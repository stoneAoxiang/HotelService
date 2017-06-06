package com.hotel.service.ui;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hotel.service.R;
import com.hotel.service.adapter.CommunitySelectPopAdapter;
import com.hotel.service.adapter.CountySelectPopAdapter;
import com.hotel.service.logic.PopMenuCallback;

import java.util.ArrayList;
import java.util.HashMap;


public class SelectpropertyPopMenu
{ 
	private Context context;
	private PopupWindow popupWindow; 
	
	private GridView countyGV;
	private GridView communityGV;
	
	private String TAG = SelectpropertyPopMenu.class.getCanonicalName();
	
	private PopMenuCallback pcb;
	
	private CountySelectPopAdapter countyAdapter;
	private CommunitySelectPopAdapter communityAdapter;
	

	public SelectpropertyPopMenu(Context context, View v, PopMenuCallback pcb) {
		// TODO Auto-generated constructor stub
		this.context = context; 
		this.pcb = pcb;
		
		View view = LayoutInflater.from(context).inflate(R.layout.select_property_popmenu, null);  
        
        popupWindow = new PopupWindow(view, LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);         
        
        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        
        //在点击的空间下方
//        popupWindow.showAsDropDown(v);         
        showAsDropDown(v);
        
        countyGV = (GridView)view.findViewById(R.id.county_gv);
        
		communityGV = (GridView)view.findViewById(R.id.community_gv);
		
		//获取区县数据 
		pcb.getCountyList();
        
        TextView bottomTV = (TextView)view.findViewById(R.id.bottom_tv);
        bottomTV.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {

				dismiss();
			}
		});  
       
	}  
	
	/**
	 * 设置区县数据
	 * @param CountyList
	 */
	public void setCountyData(ArrayList<HashMap<String, String>> CountyList){
		int count = CountyList.size();
		if(count > 0){
			countyAdapter = new CountySelectPopAdapter(context, CountyList, pcb);
			countyGV.setAdapter(countyAdapter);
			
		}else{
			 
		}
	}
	
	public void setCommunityData(ArrayList<HashMap<String, String>> commuityList){
		int count = commuityList.size();
		if(count > 0){
			communityAdapter = new CommunitySelectPopAdapter(context, commuityList, pcb);
			communityGV.setVisibility(View.VISIBLE);
			communityGV.setAdapter(communityAdapter);
			
		}else{
			 
		}
	}

	//下拉式 弹出 pop菜单 parent 右下角
	public void showAsDropDown(View parent) {
//		popupWindow.showAsDropDown(parent, 10, 10);
		popupWindow.showAsDropDown(parent);
		
		// 取得焦点，创建出来的PopupWindow默认无焦点
        popupWindow.setFocusable(true);
        // 设置允许在外点击消失
        popupWindow.setOutsideTouchable(true);
        //刷新状态
        popupWindow.update();
	}
	
	//隐藏菜单
	public void dismiss() {
		popupWindow.dismiss();
	} 
	 
}
