package com.hotel.service.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hotel.service.R;
import com.hotel.service.ui.widget.ProgressRelativeLayout;
import com.sharyuke.tool.model.ProgressModel;

public class TabContentListAdapter extends BaseAdapter {
    private Context context;
    private int[] viewList;
    private String[] textList;
    private View allProductView;

    private Resources rs;

    private boolean isUpdate = false;
    private boolean isDownloading = false;

    private ProgressModel mProgress = new ProgressModel();


    private String TAG = TabContentListAdapter.class.getCanonicalName();

    public void setProgress(ProgressModel progress) {
        this.mProgress = progress;
    }

    public void setIsDownloading(boolean isDownloading) {
        this.isDownloading = isDownloading;
    }

    public boolean isUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(boolean isUpdate) {
        this.isUpdate = isUpdate;
    }

    public TabContentListAdapter(Context con, int[] viewList, String[] textList) {
        this.context = con;
        this.viewList = viewList;
        this.textList = textList;

        rs = context.getResources();
    }

    @Override
    public int getCount() {
        return textList.length;
    }

    @Override
    public Object getItem(int position) {

        return null;
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            allProductView = LayoutInflater.from(context).inflate(
                    R.layout.tab_content_list, null);
        } else {
            allProductView = convertView;
        }


        // int[] colors = { Color.WHITE, Color.rgb(219, 238, 244) };

        int[] colors = {rs.getColor(R.color.white), rs.getColor(R.color.background_grey)};

        TextView deviceName = (TextView) allProductView
                .findViewById(R.id.product_name);
        ImageView devicePic = (ImageView) allProductView
                .findViewById(R.id.product_pic);

        View progress = allProductView.findViewById(R.id.about_progress_bar);

        progress.setVisibility(isUpdate && position == 4 ? View.VISIBLE : View.GONE);

        ProgressRelativeLayout progress1 = (ProgressRelativeLayout) allProductView.findViewById(R.id.service_list);
        if (position == 5) {
            progress1.setProgress((int) mProgress.getProgress());
            progress1.setTotal((int) mProgress.getTotalLength());
        }
        progress1.setBackgroundColor(colors[position % 2]);

        devicePic.setBackgroundResource(viewList[position]);

        String tmp = textList[position];

        deviceName.setText(tmp);

        return allProductView;
    }

}
