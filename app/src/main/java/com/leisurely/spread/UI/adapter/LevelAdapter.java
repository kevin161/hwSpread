package com.leisurely.spread.UI.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.leisurely.spread.R;
import com.leisurely.spread.model.bean.Level;
import com.leisurely.spread.util.DateUtil;

import java.util.List;


/**
 * 右侧主界面ListView的适配器
 *
 * @author Administrator
 */
public class LevelAdapter extends BaseAdapter {
    private List<Level> list;
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
            convertView = mInflater.inflate(R.layout.level_list,
                    parent, false);
            viewHolder = new ViewHolder();
            viewHolder.user_id = convertView.findViewById(R.id.user_id);
            viewHolder.username = convertView.findViewById(R.id.username);
            viewHolder.rank = convertView.findViewById(R.id.rank);
            viewHolder.phone = convertView.findViewById(R.id.phone);
            viewHolder.time = convertView.findViewById(R.id.time);
            viewHolder.money = convertView.findViewById(R.id.money);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        onBindViewHolder(viewHolder, position);
        return convertView;
    }


    public LevelAdapter(Context context, List<Level> list) {
        mInflater = LayoutInflater.from(context);
        this.list = list;
        this.mContext = context;
    }

    public static class ViewHolder {
        TextView user_id;
        TextView username;
        TextView phone;
        TextView money;
        TextView time;
        TextView rank;
    }

    public void changeList(List<Level> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void addList(List<Level> bean) {
        list.addAll(bean);
        notifyDataSetChanged();
    }

    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Level level = list.get(position);
        holder.user_id.setText(level.getUid());
        holder.money.setText(level.getTotalTrade());
        holder.phone.setText(level.getUserPhone());
        holder.username.setText(level.getUserName());
        holder.time.setText(DateUtil.getdata(level.getCreateTime()));

        switch (level.getRank()) {
            case 1:
                holder.rank.setText("一级");
                break;
            case 2:
                holder.rank.setText("二级");
                break;
            case 3:
                holder.rank.setText("三级");
                break;
            default:
                holder.rank.setText("普通用户");
                break;
        }

    }

}
