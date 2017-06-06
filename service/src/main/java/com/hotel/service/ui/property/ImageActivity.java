package com.hotel.service.ui.property;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;

import com.hotel.service.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by hyz on 2016/1/28.
 */
public class ImageActivity extends Activity {


    private String TAG = ImageActivity.class.getCanonicalName();

    @InjectView(R.id.orgin_image_view)
    ImageView imgPic;

    private String img;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.image_activity);
        ButterKnife.inject(this);

        Intent intent = getIntent();
        img =  intent.getStringExtra("picID");
        Log.i(TAG, "图片id	1===========" + img);

        boolean hasSDCard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);

        String fileDrict;
        if (hasSDCard) {
            fileDrict = Environment.getExternalStorageDirectory().toString();
        } else {
            fileDrict = Environment.getDownloadCacheDirectory().toString();
        }

        Log.i(TAG, "fileDrict===========" + fileDrict);

//        imgPic.setImageBitmap((Bitmap) (pics.get(position).get("imageItem")));
        imgPic.setImageBitmap(BitmapFactory.decodeFile(img));

//        imgPic.setImageResource(R.mipmap.quality_off);



//                ImageView dd = new ImageView(getContext());
//                dd.setImageBitmap(BitmapFactory.decodeFile());

    }

}
