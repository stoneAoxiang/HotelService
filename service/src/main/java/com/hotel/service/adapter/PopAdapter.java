package com.hotel.service.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hotel.service.R;

import java.util.ArrayList;
import java.util.HashMap;

public class PopAdapter extends BaseAdapter {

    private ArrayList<HashMap<String, String>> spinnerValue;
    private Context context;

    public PopAdapter(Context con, ArrayList<HashMap<String, String>> spinnerValue) {
        this.context = con;
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
                    R.layout.product_recieve_type, null);
            holder = new ViewHolder();

            convertView.setTag(holder);

            holder.spinnerName = (TextView) convertView
                    .findViewById(R.id.spinner_name);
            holder.spinnerID = (TextView) convertView
                    .findViewById(R.id.spinner_id);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        HashMap<String, String> t = spinnerValue.get(position);

        holder.spinnerName.setText(t.get("name"));
        holder.spinnerID.setText(t.get("id"));

        return convertView;
    }

    private final class ViewHolder {
        TextView spinnerName;
        TextView spinnerID;
    }
}
