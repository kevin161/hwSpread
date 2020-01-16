package com.leisurely.spread.framework.BaseAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用万能适配器
 *xcl
 *  * @param
 */
public abstract class CommonAdapter<T> extends BaseAdapter {
    protected LayoutInflater mInflater;
    protected Context mContext;
    protected List<T> mDatas= new ArrayList<>() ;
    protected final int mItemLayoutId;

    public CommonAdapter(Context context, List<T> mDatas, int itemLayoutId) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        if (mDatas != null) {
            this.mDatas.clear();
            this.mDatas.addAll(mDatas);
        }
        this.mItemLayoutId = itemLayoutId;
    }

    public List<T> getmDatas() {
        return mDatas;
    }


    public void setmDatas(List<T> Datas) {
        if (Datas==null)
            Datas=new ArrayList<>();
        mDatas.clear();
        mDatas.addAll(Datas);
        notifyDataSetChanged();
    }

    public void setemptyDatas() {
        mDatas.clear();
        notifyDataSetChanged();
    }

    public void addlist(List<T> Datas) {
        mDatas.addAll(Datas);
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {

        int count = 0;
        if (mDatas != null) {
            count = mDatas.size();
        }
        return count;
    }

    @Override
    public T getItem(int position) {
        if (mDatas.size() == 0) {
            return null;
        }
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder = getViewHolder(position, convertView,
                parent);
        convert(viewHolder,position, getItem(position));
        return viewHolder.getConvertView();
    }

    public abstract void convert(ViewHolder helper,int position, T item);


    private ViewHolder getViewHolder(int position, View convertView,
                                     ViewGroup parent) {
        return com.leisurely.spread.framework.BaseAdapter.ViewHolder.get(mContext, convertView, parent, mItemLayoutId,
                position);
    }

}
