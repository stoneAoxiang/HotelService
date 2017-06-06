package com.hotel.service.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hotel.service.R;

/**
 * 对用户指定的功能集合进行适配
 */
public class FunctionListAdapter extends BaseAdapter {
	private Context context;
	private int[] viewList;
	private String[] textList;
	private View allProductView;
	private int unreadDiscountCount;
	private int unreadBulletinCount;
	
	private String TAG = FunctionListAdapter.class.getCanonicalName();
	
	public FunctionListAdapter(Context con, int[] viewList, String[] textList, int unreadDiscountCount, int unreadBulletinCount) {
		this.context = con;
		this.viewList = viewList;
		this.textList = textList;
		this.unreadDiscountCount = unreadDiscountCount;
		this.unreadBulletinCount = unreadBulletinCount;
	}

	@Override
	public int getCount() {
		return textList.length;
	}

	@Override
	public Object getItem(int position) {
		return textList[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) { 
			allProductView = LayoutInflater.from(context).inflate(R.layout.function_list,
					null); 
		} else {
			allProductView = convertView;
		}
		
		TextView deviceName = (TextView) allProductView.findViewById(R.id.product_name);
		ImageView devicePic = (ImageView) allProductView.findViewById(R.id.product_pic); 
		TextView unreadCountTV = (TextView) allProductView.findViewById(R.id.unread_count);
		
		devicePic.setBackgroundResource(viewList[position]);
		
		String tmp = textList[position];
		
		deviceName.setText(tmp);
		
		if(tmp.equals("物业公告")){
			if(unreadBulletinCount > 0){
				unreadCountTV.setVisibility(View.VISIBLE);
				unreadCountTV.setText(unreadBulletinCount+"");
			}
			
		}else if(tmp.equals("打折信息")){
			if(unreadDiscountCount > 0){
				unreadCountTV.setVisibility(View.VISIBLE);
				unreadCountTV.setText(unreadDiscountCount+"");
			}
			
		}else{
			unreadCountTV.setVisibility(View.GONE);
		}
		
		
		return allProductView;
	}

}
