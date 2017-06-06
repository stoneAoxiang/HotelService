package com.hotel.service.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.hotel.service.R;
import com.hotel.service.util.ConstantValue;
import com.hotel.service.util.Tools;
import com.squareup.picasso.Picasso;


public class PicArrayAdapter extends BaseAdapter {

    private Context context;
    private String[] arrayPic;

    public PicArrayAdapter(Context con, String[] arrayPic) {
        this.context = con;
        this.arrayPic = arrayPic;
    }

    @Override
    public int getCount() {
        return arrayPic.length;
    }

    @Override
    public Object getItem(int position) {
        return arrayPic[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.house_info_list, null);
        }
        holder = new ViewHolder();

        holder.houseImg = (ImageView) convertView.findViewById(R.id.house_img);

        if (null == arrayPic || arrayPic[0] == "") {
            Picasso.with(context).load(R.mipmap.default_picture)
                    .into(holder.houseImg);//  默认图片
         } else if (null != arrayPic) {
            Picasso.with(context).load(Tools.getPicUrl(arrayPic[position], ConstantValue.IMG_PRODUCT_DETAIL_WIDTH, ConstantValue.IMG_PRODUCT_DETAIL_HEIGHT))
                    .placeholder(R.mipmap.default_picture)
                    .error(R.mipmap.default_picture)
                    .into(holder.houseImg);
        }


        return convertView;
    }

    public static class ViewHolder {

        public ImageView houseImg;
    }
}