package com.hotel.service.ui.timepicker;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.hotel.service.R;

import java.util.Calendar;

public class SelectBirthday extends PopupWindow implements OnClickListener {

	private Activity mContext;
	private View mMenuView;
	private ViewFlipper viewfipper;
	private Button btn_submit, btn_cancel;
	private String age;
	// private DateNumericAdapter monthAdapter, dayAdapter, yearAdapter;
	private DateNumericAdapter monthAdapter, dayAdapter, hoursAdapter,
			timeAdapter;
	// private WheelView year, month, day;
	private WheelView month, day, hours, time;
	// private int mCurYear = 80, mCurMonth = 5, mCurDay = 14;
	private int mCurDay = 14, mCurHours;
	private String[] dateType;

	private String TAG = SelectBirthday.class.getCanonicalName();
	
	private OnWheelChangedListener listener;
	
	private Calendar calendar;

	public SelectBirthday(Activity context) {
		super(context);
		mContext = context;
		this.age = "20-15";
		
		calendar = Calendar.getInstance();

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(R.layout.birthday, null);
		viewfipper = new ViewFlipper(context);
		viewfipper.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));

//		month = (WheelView) mMenuView.findViewById(R.id.month);
		day = (WheelView) mMenuView.findViewById(R.id.day);

		hours = (WheelView) mMenuView.findViewById(R.id.hours);

		time = (WheelView) mMenuView.findViewById(R.id.time);


		btn_submit = (Button) mMenuView.findViewById(R.id.submit);
		btn_cancel = (Button) mMenuView.findViewById(R.id.cancel);
		btn_submit.setOnClickListener(this);
		btn_cancel.setOnClickListener(this);
		Calendar calendar = Calendar.getInstance();
		
		listener = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				// updateDays(year, month, day);
				
				Log.i(TAG, "OnWheelChangedListener=========================== ===============================");
				updateHours(day);

			}
		};

		if (age != null && age.contains("-")) {
			String str[] = age.split("-");
			
			mCurDay = Integer.parseInt(str[0]);
			mCurHours = Integer.parseInt(str[1]) - 1;

//			Log.i(TAG, "mCurDay===========" + mCurDay + "   mCurHours====" + mCurHours);
		}
		dateType = mContext.getResources().getStringArray(R.array.date);

		updateDays(day);
		day.setCurrentItem(mCurDay);
		updateDays(day);
		day.addChangingListener(listener);
		
		Log.i(TAG, "����Сʱֵ===========================================================");
		
		updateHours(day);
		hours.setCurrentItem(mCurHours);
		updateHours(day);
		

		updateTimes();

		viewfipper.addView(mMenuView);
		viewfipper.setFlipInterval(6000000);

		this.setContentView(viewfipper);
		this.setWidth(LayoutParams.FILL_PARENT);
		this.setHeight(LayoutParams.WRAP_CONTENT);
		this.setFocusable(true);
		ColorDrawable dw = new ColorDrawable(0x00000000);
		this.setBackgroundDrawable(dw);
		this.update();

	}

	@Override
	public void showAtLocation(View parent, int gravity, int x, int y) {
		super.showAtLocation(parent, gravity, x, y);
		viewfipper.startFlipping();
	}

	/*private void updateDays(WheelView year, WheelView month, WheelView day) {

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + year.getCurrentItem());
		calendar.set(Calendar.MONTH, month.getCurrentItem());

		int maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		dayAdapter = new DateNumericAdapter(mContext, 1, maxDays,
				calendar.get(Calendar.DAY_OF_MONTH) - 1);
		dayAdapter.setTextType(dateType[2]);
		day.setViewAdapter(dayAdapter);
		int curDay = Math.min(maxDays, day.getCurrentItem() + 1);
		day.setCurrentItem(curDay - 1, true);
		int years = calendar.get(Calendar.YEAR) - 100;
		age = years + "-" + (month.getCurrentItem() + 1) + "-"
				+ (day.getCurrentItem() + 1);
	}

	private void updateDays(WheelView month, WheelView day) {

		Log.i(TAG,
				"SelectBirthday -> updateDays() ===============================");

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, month.getCurrentItem());

		int maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

		Log.i(TAG,
				"SelectBirthday -> updateDays() -> DateNumericAdapter() ===============================");

		dayAdapter = new DateNumericAdapter(mContext, 1, maxDays,
				calendar.get(Calendar.DAY_OF_MONTH) - 1);
		dayAdapter.setTextType(dateType[2]);
		day.setViewAdapter(dayAdapter);
		int curDay = Math.min(maxDays, day.getCurrentItem() + 1);
		day.setCurrentItem(curDay - 1, true);
		int years = calendar.get(Calendar.YEAR) - 100;
		age = years + "-" + (month.getCurrentItem() + 1) + "-"
				+ (day.getCurrentItem() + 1);

		age = (month.getCurrentItem() + 1) + "-" + (day.getCurrentItem() + 1);
	}*/

	private void updateDays(WheelView day) { 
		
		calendar.set(Calendar.DATE, day.getCurrentItem());

		int maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

		dayAdapter = new DateNumericAdapter(mContext, 1, maxDays, calendar.get(Calendar.DATE));
		dayAdapter.setTextType(dateType[2]);
		day.setViewAdapter(dayAdapter); 
		
		int curDay = Math.min(maxDays, day.getCurrentItem()); 
		
		Log.i(TAG, "curDay============"+curDay);
		
		day.setCurrentItem(curDay, true); 
		
		Log.i(TAG, "day============"+day.getCurrentItem());
		day.setCyclic(true);
		 
	}

	private void updateHours(WheelView day) {  
		calendar.set(Calendar.DAY_OF_WEEK, day.getCurrentItem());

		int maxHours = calendar.getActualMaximum(Calendar.HOUR_OF_DAY); 

		hoursAdapter = new DateNumericAdapter(mContext, 1, maxHours, calendar.get(Calendar.HOUR_OF_DAY) - 1);
		hoursAdapter.setTextType(dateType[3]);
		hours.setViewAdapter(hoursAdapter);	  
		
		hours.setCurrentItem(calendar.get(Calendar.HOUR_OF_DAY), true);
		
		hours.setCyclic(true); 

		age = (day.getCurrentItem() + 1) + "-" + hours.getCurrentItem();  
	}

	private void updateTimes() {
		timeAdapter = new DateNumericAdapter(mContext, 0, 1, 0);
		timeAdapter.setTextType(dateType[4]);
		time.setVisibleItems(2);
		time.setViewAdapter(timeAdapter); 
	}

	/**
	 * Adapter for numeric wheels. Highlights the current value.
	 */
	private class DateNumericAdapter extends NumericWheelAdapter {
		// Index of current item
		int currentItem;
		// Index of item to be highlighted
		int currentValue;

		/**
		 * Constructor
		 */
		public DateNumericAdapter(Context context, int minValue, int maxValue,
				int current) {
			super(context, minValue, maxValue);
			this.currentValue = current;
			setTextSize(24);
		}

		protected void configureTextView(TextView view) {
			super.configureTextView(view);
			view.setTypeface(Typeface.SANS_SERIF);
		}

		public CharSequence getItemText(int index) {
			currentItem = index;
			return super.getItemText(index);
		}

	}

	public void onClick(View v) {

		if (v.getId() == R.id.submit) {
			/*
			 * Toast.makeText( mContext, (month.getCurrentItem() + 1) + "��" +
			 * (day.getCurrentItem() + 1) + "��" + time.getCurrentItem(),
			 * Toast.LENGTH_LONG).show();
			 */

			Toast.makeText(
					mContext,
					((day.getCurrentItem() + 1) + "��"
							+ (hours.getCurrentItem() + 1) + "��" + time
							.getCurrentItem()), Toast.LENGTH_LONG).show();

		} else {
			this.dismiss();
		}

	}

}
