package com.hotel.service.ui.property;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.hotel.service.R;
import com.hotel.service.util.Config;


/**
 * 招聘详情
 * 
 * @author aoxiang
 * 
 */
public class CommunityOfferDetailActivity extends Activity {

	private String TAG = CommunityOfferDetailActivity.class.getCanonicalName();

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.community_offer_detail_view);

		Intent i = getIntent();

		((TextView) findViewById(R.id.position)).setText(i.getStringExtra("position"));

		setTextProperty((TextView) findViewById(R.id.offer_time), "发布时间:", i.getStringExtra("offerTime"), R.color.grey_dark);

		String salary = i.getStringExtra("salary");
		((TextView) findViewById(R.id.salary)).setText(salary.equals("面议") ? salary : salary + "元");

		setTextProperty((TextView) findViewById(R.id.company_name), "公司名称:", i.getStringExtra("companyname"), R.color.grey_dark);
		setTextProperty((TextView) findViewById(R.id.work_address), "工作地点:", i.getStringExtra("workAddress"), R.color.grey_dark);
		setTextProperty((TextView) findViewById(R.id.people_num), "招聘人数:", i.getStringExtra("people_num") + "人", R.color.grey_dark);
		setTextProperty((TextView) findViewById(R.id.education), "学历要求:", i.getStringExtra("education"), R.color.grey_dark);
		setTextProperty((TextView) findViewById(R.id.work_experience), "经验要求:", i.getStringExtra("workExperience") + "年", R.color.grey_dark);
		setTextProperty((TextView) findViewById(R.id.phone), "联系电话:", i.getStringExtra("phone"), R.color.grey_dark);

		((WebView) findViewById(R.id.work_details)).loadUrl(Config.HOST_NAME + i.getStringExtra("workDetails"));
		
		((TextView) findViewById(R.id.activity_name)).setText(getString(R.string.offer_detail));
		
		((ImageView) findViewById(R.id.return_back)).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				finish();
			}
		});
	}

	/**
	 * 设置文本属性
	 */
	private void setTextProperty(TextView v, String first, String second, int colorID) {
		SpannableString spanText = new SpannableString(first);
		TextAppearanceSpan textAppearanceSpan = new TextAppearanceSpan(this, R.style.text_style);
		spanText.setSpan(textAppearanceSpan, 0, spanText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//		spanText.setSpan(new ForegroundColorSpan(getResources().getColor(colorID)), 0, spanText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

		v.setText(spanText);

		SpannableString spanText2 = new SpannableString(second);
		TextAppearanceSpan textAppearanceSpan2 = new TextAppearanceSpan(this, R.style.text_style);
		spanText2.setSpan(textAppearanceSpan2, 0, spanText2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		spanText2.setSpan(new ForegroundColorSpan(getResources().getColor(colorID)), 0, spanText2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		v.append(spanText2);
	}

}
