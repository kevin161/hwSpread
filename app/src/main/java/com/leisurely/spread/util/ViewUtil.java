package com.leisurely.spread.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import com.leisurely.spread.framework.base.BaseActivity;


public class ViewUtil {
	private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);
	public static float width;
	public static float height;
	public static float density = 1.0f;  // 屏幕密度（0.75 / 1.0 / 1.5）
	public static int densityDpi = 160;  // 屏幕密度DPI（120 / 160 / 240）

	private static int generateViewId() {
	    for (;;) {
	        final int result = sNextGeneratedId.get();
	        // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
	        int newValue = result + 1;
	        if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
	        if (sNextGeneratedId.compareAndSet(result, newValue)) {
	            return result;
	        }
	    }
	}
	/**
	 * 获取view Id(适合动态创建的view)
	 * @return
	 */
	public static int getViewId()
	{
		int id = 0;
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
			id = generateViewId();
	    } else {
	    	id = getView17Id();
	    }
		return id;
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
	private static int getView17Id()
	{
		int id = View.generateViewId();
		return id;
	}
	
	/**
	 * 获取屏幕密度
	 * 
	 * @param context
	 */
	public static float getScreenDensity(Context context) {
		WindowManager wm = (WindowManager) context.getApplicationContext()
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics metric = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(metric);
		return metric.density;  // 屏幕密度（0.75 / 1.0 / 1.5）
		
	}

	/**
	 * 根据资源名称和资源类型 获取资源id
	 * @param context 上下文
	 * @param resName 资源名称
	 * @param resType 资源类型
	 * @return
	 */
	public static int getResId(Context context, String resName, String resType)
	{
		Resources res=context.getResources();
		int i=res.getIdentifier(resName, resType, context.getPackageName());
		return i;
	}

	/**
	 * 根据坐标获取相对应的子控件<br>
	 * 在Activity使用
	 *
	 * @param x 坐标
	 * @param y 坐标
	 * @return 目标View
	 */
	public static View getViewAtActivity(BaseActivity baseActivity, int x, int y) {
		// 从Activity里获取容器
		View root = baseActivity.getWindow().getDecorView();
		return findViewByXY(root, x, y);
	}

	private static View findViewByXY(View view, int x, int y) {
		View targetView = null;
		if (view instanceof ViewGroup) {
			// 父容器,遍历子控件
			ViewGroup v = (ViewGroup) view;
			for (int i = 0; i < v.getChildCount(); i++) {
				targetView = findViewByXY(v.getChildAt(i), x, y);
				if (targetView != null) {
					break;
				}
			}
		} else {
			targetView = getTouchTarget(view, x, y);
		}
		return targetView;

	}

	private static View getTouchTarget(View view, int x, int y) {
		View targetView = null;
		// 判断view是否可以聚焦
		ArrayList<View> TouchableViews = view.getTouchables();
		for (View child : TouchableViews) {
			if (isTouchPointInView(child, x, y)) {
				targetView = child;
				break;
			}
		}
		return targetView;
	}

	private static boolean isTouchPointInView(View view, int x, int y) {
		int[] location = new int[2];
		view.getLocationOnScreen(location);
		int left = location[0];
		int top = location[1];
		int right = left + view.getMeasuredWidth();
		int bottom = top + view.getMeasuredHeight();
		if (view.isClickable() && y >= top && y <= bottom && x >= left
				&& x <= right) {
			return true;
		}
		return false;
	}
}
