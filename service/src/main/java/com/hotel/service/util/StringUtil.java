package com.hotel.service.util;

import android.text.TextUtils;

import java.text.DecimalFormat;

/**
 * StringUtil
 * Created by sharyuke on 15-5-27.
 */
public class StringUtil {

    public static int str2int(String src) {
        try {
            return TextUtils.isEmpty(src) ? 0 : Integer.valueOf(src);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static float str2float(String src) {
        try {
            return TextUtils.isEmpty(src) ? 0f : Float.valueOf(src);
        } catch (NumberFormatException e) {
            return 0f;
        }
    }

    public static String formatDoubleToStringWith2(double d) {
        String decimal = "0.00";
        try {
            DecimalFormat format = new DecimalFormat("#0.00");
            decimal = format.format(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decimal;
    }
}
