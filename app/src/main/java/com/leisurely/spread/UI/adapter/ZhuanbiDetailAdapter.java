package com.leisurely.spread.UI.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.leisurely.spread.R;
import com.leisurely.spread.model.bean.Shoubi;
import com.leisurely.spread.util.DateUtil;

import java.util.List;


/**
 * 右侧主界面ListView的适配器
 *
 * @author Administrator
 */
public class ZhuanbiDetailAdapter extends BaseAdapter {
    private List<Shoubi> list;
    private LayoutInflater mInflater;
    private Context mContext;
    private ViewHolder viewHolder;

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.activity_zhuanbi_detail_list,
                    parent, false);
            viewHolder = new ViewHolder();
            viewHolder.text = convertView.findViewById(R.id.text);
            viewHolder.money = convertView.findViewById(R.id.money);
            viewHolder.type = convertView.findViewById(R.id.type);
            viewHolder.time = convertView.findViewById(R.id.time);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        onBindViewHolder(viewHolder, position);
        return convertView;
    }


    public ZhuanbiDetailAdapter(Context context, List<Shoubi> list) {
        mInflater = LayoutInflater.from(context);
        this.list = list;
        this.mContext = context;
    }

    public static class ViewHolder {
        TextView text;
        TextView money;
        TextView type;
        TextView time;
    }

    public void changeList(List<Shoubi> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void addList(List<Shoubi> bean) {
        list.addAll(bean);
        notifyDataSetChanged();
//        notifyItemInserted(position);
    }

    public void onBindViewHolder(ViewHolder holder, final int position) {
        Shoubi cb = list.get(position);
        holder.money.setText("- "+cb.getNumber());
        holder.time.setText(DateUtil.getdata(cb.getAddtime()));
        holder.type.setText("转币");

    }

}
