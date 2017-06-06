package com.hotel.service.ui.property;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.hotel.service.R;
import com.hotel.service.util.Config;


/**
 * 公告详情
 * 
 * @author aoxiang
 * 
 */
public class BulletinDetailActivity extends Activity {

	private String TAG = BulletinDetailActivity.class.getCanonicalName();

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.bulletin_detail_view);

		Intent i = getIntent();

		((TextView) findViewById(R.id.title)).setText(i.getStringExtra("title"));
		((TextView) findViewById(R.id.time)).setText(i.getStringExtra("time"));
		((WebView) findViewById(R.id.content)).loadUrl(Config.HOST_NAME + i.getStringExtra("content"));
		
		((TextView) findViewById(R.id.activity_name)).setText("公告详情");
		
		((ImageView) findViewById(R.id.return_back)).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				finish();
			}
		});
	} 

}
