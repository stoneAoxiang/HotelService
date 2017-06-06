package com.hotel.service.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

public class PhotoUtil {

	/** 拍照 */
	public static final int TAKE_PHOTO = 1001;

	/** 拍照后裁剪 */
	public static final int CROP_PHOTO = 1002;

	/** 从相册选择图片 */
	public static final int PICK_PHOTO = 1003;

	/** 输出宽度 */
	private static final int OUTPUT_WIDTH = 400;

	/** 输出高度 */
	private static final int OUTPUT_HEIGHT = 400;

	/**
	 * 调用相机拍照
	 */
	public static void takePhoto(Activity activity, Uri uri) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		activity.startActivityForResult(intent, TAKE_PHOTO);
	}

	/**
	 * 相机拍照后剪切
	 */
	public static void cropPhoto(Activity activity, Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", OUTPUT_WIDTH);
		intent.putExtra("outputY", OUTPUT_HEIGHT);
		intent.putExtra("return-data", false);
		activity.startActivityForResult(intent, CROP_PHOTO);
	}

	/**
	 * 从相册选择图片，并剪切
	 */
	public static void pickPhoto(Activity activity, Uri uri) {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("image/*");
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", OUTPUT_WIDTH);
		intent.putExtra("outputY", OUTPUT_HEIGHT);
		intent.putExtra("scale", true);
		intent.putExtra("scaleUpIfNeeded", true); // 图片尺寸过小，适当放大到需要尺寸.
		intent.putExtra("return-data", false);
		activity.startActivityForResult(intent, PICK_PHOTO);
	}
}
