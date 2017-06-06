package com.hotel.service.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.hotel.service.R;

import java.util.ArrayList;
import java.util.Map;

public class DynamicPicAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<Map<String, Object>> pics; 
	
	private String TAG = DynamicPicAdapter.class.getCanonicalName();
	
	public DynamicPicAdapter(Context con, ArrayList<Map<String, Object>> pics) {
		this.context = con;
		this.pics = pics; 
	}

	@Override
	public int getCount() {

		return pics.size();
	}

	@Override
	public Object getItem(int position) {

		return pics.get(position);
	}

	@Override
	public long getItemId(int position) {

		return 0;
	}

	@SuppressLint("ResourceAsColor")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder; 
		
		if (convertView == null) { 
			convertView = LayoutInflater.from(context).inflate(R.layout.dynamic_pic_layout,
					null); 
			
			holder = new ViewHolder(); 
			
			holder.typeName = (ImageView) convertView.findViewById(R.id.img_iv); 
		 
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		} 
		

		holder.typeName.setImageBitmap((Bitmap)(pics.get(position).get("imageItem"))); 
		return convertView;
	}
	
	static class ViewHolder { 
		ImageView typeName;  
    }

}
