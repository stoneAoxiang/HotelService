package com.hotel.service.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hotel.service.R;
import com.hotel.service.logic.PopMenuCallback;

import java.util.ArrayList;
import java.util.HashMap;

public class CommunitySelectPopAdapter extends BaseAdapter {
	
	private ArrayList<HashMap<String, String>> spinnerValue;
	private Context context;
	private String TAG = CommunitySelectPopAdapter.class.getCanonicalName();
	
	private PopMenuCallback pcb;
	
	
	
	public CommunitySelectPopAdapter(Context con, ArrayList<HashMap<String, String>> spinnerValue,  PopMenuCallback pcb) {
		this.context = con;
		this.pcb = pcb;
		this.spinnerValue = spinnerValue; 
	}

	@Override
	public int getCount() {

		return spinnerValue.size();
	}

	@Override
	public Object getItem(int position) {

		return spinnerValue.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	public String getItemValueId(int position) {

		return spinnerValue.get(position).get("id");
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.area_select_pomenu_item, null);
			holder = new ViewHolder();

			convertView.setTag(holder);

			holder.spinnerName = (TextView) convertView
					.findViewById(R.id.spinner_name);
			holder.spinnerID = (TextView) convertView
					.findViewById(R.id.spinner_id);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final HashMap<String, String> t = spinnerValue.get(position);

		holder.spinnerName.setText(t.get("name"));
		holder.spinnerID.setText(t.get("id"));
		
		holder.spinnerName.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {

				String communityID = t.get("id");
				String communityName = t.get("name");
//				Toast.makeText(context, communityID+"    "+communityName, Toast.LENGTH_LONG).show();
				
				Log.i(TAG, "选择社区名称======================="+communityName);
				
				pcb.setSelectCommunity(communityID, communityName);

//				ServiceProcess processEngine = ServiceProcess.getInstance();
//				processEngine.getCommuityOfCountyInfoRequest(obj2Json(ConstantValue.GET_COMMUITY_OF_COUNTY_INFO, t.get("name")));
			}
		});

		return convertView;
	}

	private final class ViewHolder {
		TextView spinnerName;
		TextView spinnerID;
	} 
 
}
