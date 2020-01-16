
package com.leisurely.spread.framework.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leisurely.spread.util.ToastUtil;

public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    protected View baseView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        baseView = inflater.inflate(getBaseLayoutId(), null);
        initView();
        initListener();
        initData();
        return baseView;
    }

    protected abstract int getBaseLayoutId();

    protected abstract void initView();

    protected abstract void initListener();

    protected abstract void initData();

    /**
     * 展示toast
     * @param msg
     */
    protected void showToast(String msg)
    {
        ToastUtil.showToast(msg);
    }

    /**
     * 设置view的单击监听
     * @param id view的id
     */
    protected void setClickListener(int id)
    {
        if(baseView==null)
        {
            return;
    }
    baseView.findViewById(id).setOnClickListener(this);
    }
    /**
     * 设置view的单击监听
     * @param view
     */
    protected void setClickListener(View view)
    {
        view.setOnClickListener(this);
    }
}
