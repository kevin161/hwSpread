package com.leisurely.spread.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.view.WindowManager;
import android.webkit.WebView;

import java.io.File;
import java.util.List;

import com.leisurely.spread.application.AppApplication;


public class ActivityUtil {

	/**
	 * 检测任务是否存在
	 * 
	 * @param paramAsyncTask AsyncTask
	 * @return boolean
	 */
	public static boolean checkTask(AsyncTask<?, ?, ?> paramAsyncTask) {
		boolean exist = false;
		if (paramAsyncTask != null) {
			AsyncTask.Status taskstatus = paramAsyncTask.getStatus();
			AsyncTask.Status finished = AsyncTask.Status.FINISHED;
			// 如果任务结束或者取消或者已经取消
			if ((taskstatus == finished) || paramAsyncTask.cancel(true) || paramAsyncTask.isCancelled()) {
				exist = false;
			} else {
				exist = true;
			}
		}
		return exist;
	}

	// 获取资源ID，如果不存在返回0
	public static int getResourceId(Context context, String name, String type, String packageName) {
		Resources themeResources = null;
		PackageManager pm = context.getPackageManager();
		try {
			themeResources = pm.getResourcesForApplication(packageName);
			return themeResources.getIdentifier(name, type, packageName);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 网络是否可用
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetworkConnected(Context context) {
		try {
			ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo net = conn.getActiveNetworkInfo();
			if (net != null && net.isConnected()) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	// 判断当前是否使用的是 WIFI网络
	public static boolean isWifiActive(Context icontext) {
		Context context = icontext.getApplicationContext();
		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] info;
		if (connectivity != null) {
			info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getTypeName().equals("WIFI") && info[i].isConnected()) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * GPS是否可用
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isGpsEnabled(Context context) {
		LocationManager locationManager = ((LocationManager) context.getSystemService(Context.LOCATION_SERVICE));
		List<String> accessibleProviders = locationManager.getProviders(true);
		return accessibleProviders != null && accessibleProviders.size() > 0;
	}

	/**
	 * wifi是否打开
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isWifiEnabled(Context context) {

		boolean isWifiConnect = true;
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		// check the networkInfos numbers
		NetworkInfo[] networkInfos = cm.getAllNetworkInfo();
		for (int i = 0; i < networkInfos.length; i++) {
			if (networkInfos[i].getState() == NetworkInfo.State.CONNECTED) {
				if (networkInfos[i].getType() == ConnectivityManager.TYPE_MOBILE) {
					isWifiConnect = false;
				}
				if (networkInfos[i].getType() == ConnectivityManager.TYPE_WIFI) {
					isWifiConnect = true;
				}
			}
		}
		return isWifiConnect;
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int diptopx(Context context, float dpValue) {

		final float scale = context.getResources().getDisplayMetrics().density;

		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */

	public static int pxtodip(Context context, float pxValue) {

		final float scale = context.getResources().getDisplayMetrics().density;

		return (int) (pxValue / scale + 0.5f);
	}

	public final static void webViewLoadData(WebView web, String html) {
		StringBuilder buf = new StringBuilder(html.length());
		for (char c : html.toCharArray()) {
			switch (c) {
			case '#':
				buf.append("%23");
				break;
			case '%':
				buf.append("%25");
				break;
			case '\'':
				buf.append("%27");
				break;
			case '?':
				buf.append("%3f");
				break;
			default:
				buf.append(c);
				break;
			}
		}
		web.loadData(buf.toString(), "text/html", "utf-8");
	}

	public static void setScreenBrightness(Context context) {

		String daynight = SharedPreferencesUtil.readString("daynight", "day");
		int brightness = 90; // 默认亮度设置为90
		try {
			brightness = Settings.System.getInt(context.getContentResolver(),
					Settings.System.SCREEN_BRIGHTNESS);
		} catch (SettingNotFoundException e) {
			e.printStackTrace();
		}
		if (getScreenMode(context) == 1) {// 自动
			return;
		}

		if (daynight.equals("day")) {
			WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
			lp.screenBrightness = Float.valueOf(brightness) * (1f / 255f);
			((Activity) context).getWindow().setAttributes(lp);
		} else {
			WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
			lp.screenBrightness = Float.valueOf(brightness) * (1f / 255f) * 0.6f;
			((Activity) context).getWindow().setAttributes(lp);
		}
	}

	/**
	 * 获得当前屏幕亮度的模式 SCREEN_BRIGHTNESS_MODE_AUTOMATIC=1 为自动调节屏幕亮度
	 * SCREEN_BRIGHTNESS_MODE_MANUAL=0 为手动调节屏幕亮度
	 */
	private static int getScreenMode(Context context) {
		int screenMode = 0;
		try {
			screenMode = Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE);
		} catch (Exception localException) {

		}
		return screenMode;
	}

	/**
	 * 获取手机SD卡根目录
	 * 
	 * @return
	 */
	public static File getExternalStorageDirectory() {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) { // 文件可用使用外部存储
			return Environment.getExternalStorageDirectory();
		} else if ((new File("/mnt/sdcard2")).exists()) {// 特殊的手机，如中兴V955,存储卡为sdcard2
			return new File("/mnt/sdcard2");
		} else if ((new File("/mnt/sdcard0")).exists()) {
			return new File("/mnt/sdcard0");
		} else {
			return null;
		}
	}

	/**
	 * 获取包信息
	 * @param context
	 * @param tag
	 * @return
	 */
	public static PackageInfo getPackageInfo(Context context, int tag)
	{
		PackageInfo pi = null;
		try {
			pi = context.getPackageManager().getPackageInfo(context.getPackageName(), tag);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return pi;
	}

	/**
	 * 获取application信息
	 * @param context
	 * @param tag
	 * @return
	 */
	public static ApplicationInfo getApplicationInfo(Context context, int tag)
	{
		ApplicationInfo ai = null;
		try {
			ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), tag);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return ai;
	}

	/**
	 * 获取app版本号
	 * @param context
	 * @return
	 */
	public static String getVersionCode(Context context)
	{
		String code = "";
		PackageInfo pi = getPackageInfo(context, 0);
		if(pi!=null)
		{
			code = String.valueOf(getPackageInfo(context,0).versionCode);
		}
		return code;
	}
	/**
	 * 获取app版本名称
	 * @param context
	 * @return
	 */
	public static String getVersionName(Context context)
	{
		String name = "";
		PackageInfo pi = getPackageInfo(context, 0);
		if(pi!=null)
		{
			name = String.valueOf(getPackageInfo(context,0).versionName);
		}
		return name;
	}

	/**
	 * 获取manifest信息
	 * @param context
	 * @param key
	 * @return
	 */
	public static String getManifest(Context context, String key)
	{
		String msg = "";
		ApplicationInfo ai = getApplicationInfo(context, PackageManager.GET_META_DATA);
		if(ai!=null)
		{
			msg=ai.metaData.getString(key);
		}
		if(msg==null)
		{
			msg="";
		}
		return msg;
	}

	/**
	 * 获取设备型号
	 * @return
	 */
	public static String getDeviceName()
	{
		Build bd = new Build();
		String model = bd.MODEL;
		return model;
	}


	/**
	 * 获取当前运行的Activity名称
	 * @return
	 */
	public static String getRunningActivityName(){
		ActivityManager manager = (ActivityManager) AppApplication.getInstance().getSystemService(Context.ACTIVITY_SERVICE);
		String activityName = manager.getRunningTasks(1).get(0).topActivity.getClassName();
		String name = activityName.substring(activityName.lastIndexOf(".")+1, activityName.length());
		return name;
	}

	/**
	 * 获取当前显示的Pager名称
	 * @return
	 */
//	public static String getCurrentPagerName(NewBasePager pager){
//		String pagerName = pager.getClass().toString();
//		String name = pagerName.substring(pagerName.lastIndexOf(".")+1, pagerName.length());
//		return name;
//	}
}
