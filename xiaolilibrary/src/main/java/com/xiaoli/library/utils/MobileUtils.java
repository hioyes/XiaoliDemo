package com.xiaoli.library.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.xiaoli.library.C;


/**
 *手机相关工具类
 *  xiaokx
 *  hioyes@qq.com
 *  2014-11-6
 */
public class MobileUtils {

	/**
	 * 照片，媒体，文件访问权限
	 * true 可用，false不可用
	 */
	public static boolean FILE_AND_ALBUM_PERMISSION_ENABLE = true;
	static{
		int version = getSystemVersionCode();
		if(version>=23){
			FILE_AND_ALBUM_PERMISSION_ENABLE = false;
		}
	}

	/**
	 * 照片，媒体，文件访问权限-请求码
	 */
	public static int FILE_AND_ALBUM_PERMISSION_REQUESTCODE = 123456789;

	/**
	 * 调用系统界面，给指定的号码拨打电话
	 * param activity
	 * param number 电话号码
     * return 0为成功，1 没有拨打电话的权限
     */
	public static int call(Activity activity, String number) {
		Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		if (ActivityCompat.checkSelfPermission(activity.getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
			//没有打拨打电话的权限

			return 1;
		}
		activity.startActivity(intent);
		return 0;
	}

	/**
	 * 获取系统版本号
	 * return
     */
	public static int getSystemVersionCode() {
		try {
			return android.os.Build.VERSION.SDK_INT;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 1;
	}

	/**
	 * 处理6.0系统-照片，媒体，文件访问权限
	 * param activity
     */
	public static void processFileAndAlbumPermission(Activity activity){
		int hasWriteContactsPermission = ContextCompat.checkSelfPermission(C.mCurrentActivity.getApplication(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
		if(hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED){
			//没有权限去申请
			ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, FILE_AND_ALBUM_PERMISSION_REQUESTCODE);
		}
	}

}
