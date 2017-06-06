package com.hotel.service.util;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class CountDown {
	public static SimpleDateFormat sm = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	public static SimpleDateFormat sDFM = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm");

	public String setView(Date overTime) {
		long hour = 0;
		long minute = 0;
		long seconds = 0;
		Date nowTime;
		String result = null;
		try {
			nowTime = sm.parse(sm.format(new Date()));
			long ms = overTime.getTime() - nowTime.getTime();

			if (ms > 0) {
				hour = ms / (1000 * 60 * 60);
				minute = (ms % (1000 * 60 * 60)) / (1000 * 60);
				seconds = (ms % (1000 * 60)) / 1000;
				String s = "";
				if (seconds < 10) {
					s = "0" + seconds;
				} else {
					s = "" + seconds;
				}
				if (hour > 9 && minute > 9) {
					result = hour + ":" + minute + ":" + s;
				} else if (hour > 9 && minute < 10) {
					result = hour + ":0" + minute + ":" + s;
				} else if (hour < 10 && minute > 9) {
					result = "0" + hour + ":" + minute + ":" + s;
				} else if (hour < 10 && minute < 10) {
					result = hour + ":0" + minute + ":" + s;
				}
			} else {
				result = "00:00:00";
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String getCurrTime() {
		long hour = 0;
		long minute = 0;
		long seconds = 0;
		String result = null;
		Date nowTime;
		try {
			nowTime = new Date();

			nowTime = sm.parse(sm.format(new Date()));
			long ms = nowTime.getTime();
			if (ms > 0) {
				hour = nowTime.getHours();
//				hour = (ms % (1000 * 60 * 60 * 60)) / (1000 * 60 * 60);

				minute = (ms % (1000 * 60 * 60)) / (1000 * 60);
				seconds = (ms % (1000 * 60)) / 1000;
				String s = "";
				if (seconds < 10) {
					s = "0" + seconds;
				} else {
					s = "" + seconds;
				}
				if (hour > 9 && minute > 9) {
					result = hour + ":" + minute + ":" + s;
				} else if (hour > 9 && minute < 10) {
					result = hour + ":0" + minute + ":" + s;
				} else if (hour < 10 && minute > 9) {
					result = "0" + hour + ":" + minute + ":" + s;
				} else if (hour < 10 && minute < 10) {
					result = hour + ":0" + minute + ":" + s;
				}
			} else {
				result = "00:00:00";
			}
		} catch (Exception e) {

			result = "00:00:00";
			e.printStackTrace();
		}
		return result;
	}

	public static String gerHour(String ms) {
		long s = Long.parseLong(ms);
		String hour = "";
		try {
			Date date = sm.parse(sm.format(s));
			long ss = date.getTime() - new Date().getTime();
			if (ss > 0) {
				long hours = ss / (1000 * 60 * 60);
				hour = hours + "";
			} else {
				hour = "0";
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return hour;
	}

	public static String gerMinute(String ms) {
		long s = Long.parseLong(ms);
		String minute = "";
		try {
			Date date = sm.parse(sm.format(s));
			long ss = date.getTime() - new Date().getTime();
			if (ss > 0) {
				long minutes = (ss % (1000 * 60 * 60)) / (1000 * 60);
				;
				minute = minutes + "";
			} else {
				minute = "0";
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return minute;
	}

	public static String gerTaskHour(String releaseTime, String limitedTime) {
		long s = Long.parseLong(releaseTime);
		long S = Long.parseLong(limitedTime);
		String hour = "";
		try {
			Date dateRelease = sm.parse(sm.format(s));
			Date dateLimited = sm.parse(sm.format(S));
			long ss = dateLimited.getTime() - dateRelease.getTime();
			if (ss > 0) {
				long hours = ss / (1000 * 60 * 60);
				hour = hours + "";
			} else {
				hour = "0";
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return hour;
	}

	public static String gerTaskMinute(String releaseTime, String limitedTime) {
		long s = Long.parseLong(releaseTime);
		long S = Long.parseLong(limitedTime);
		String minute = "";
		try {
			Date dateRelease = sm.parse(sm.format(s));
			Date dateLimited = sm.parse(sm.format(S));
			long ss = dateLimited.getTime() - dateRelease.getTime();
			if (ss > 0) {
				long minutes = (ss % (1000 * 60 * 60)) / (1000 * 60);
				;
				minute = minutes + "";
			} else {
				minute = "0";
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return minute;
	}

	public static String getRefTime(String lastRef) {
		String s = "";
		try {
			long ms = new Date().getTime() - Long.parseLong(lastRef);
			if (ms > 1000 * 60 * 60 * 24) {
				s = "亲,你已经很久没有来过了";
			} else if (ms > 1000 * 60 * 60) {
				String hour = String.valueOf(ms / (1000 * 60 * 60));
				s = hour + "个小时以前";
			} else if (ms > 1000 * 60) {
				String minute = String.valueOf(ms / (1000 * 60));
				s = minute + "分钟以前";
			} else {
				s = "刚刚";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	public String talkTime(String date) {
		String talkTime = "";
		String newDate = sm.format(new Date()).substring(0, 10) + " 00:00:00";

		try {
			long ms = sm.parse(newDate).getTime();
			;
			if (sm.parse(date).getTime() > ms) {
				talkTime = date.substring(11, date.length() - 3);
			} else if (sm.parse(date).getTime() < ms
					&& ms > ms - 24 * 60 * 60 * 1000) {
				talkTime = "昨天  " + date.substring(11, date.length() - 3);
			} else {
				talkTime = date.substring(0, date.length() - 3);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return talkTime;
	}
}
