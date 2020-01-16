package com.leisurely.spread.framework.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class MyBaseFragment extends Fragment {
	protected Activity mActivity;// 抽取上下文对象，让子类使用
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		mActivity = getActivity();
		View view = initView();
		return view;
	}

	// 让子类实现，返回具体的控件
	protected abstract View initView();

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initData();
	}
	// 让子类更新界面，可以不必须实现
	protected void initData() {
		
	}


}
