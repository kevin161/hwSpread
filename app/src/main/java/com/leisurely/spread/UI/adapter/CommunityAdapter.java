package com.leisurely.spread.UI.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.leisurely.spread.R;
import com.leisurely.spread.model.bean.Community;
import com.leisurely.spread.util.DateUtil;

import java.util.List;


/**
 * 右侧主界面ListView的适配器
 *
 * @author Administrator
 */
public class CommunityAdapter extends BaseAdapter {
    private List<Community> list;
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
            convertView = mInflater.inflate(R.layout.activity_community_list,
                    parent, false);
            viewHolder = new ViewHolder();
            viewHolder.lch_id = convertView.findViewById(R.id.lch_id);
            viewHolder.user_id = convertView.findViewById(R.id.user_id);
            viewHolder.count = convertView.findViewById(R.id.count);
            viewHolder.time = convertView.findViewById(R.id.time);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        onBindViewHolder(viewHolder, position);
        return convertView;
    }


    public CommunityAdapter(Context context, List<Community> list) {
        mInflater = LayoutInflater.from(context);
        this.list = list;
        this.mContext = context;
    }

    public static class ViewHolder {
        TextView lch_id;
        TextView user_id;
        TextView count;
        TextView time;
    }

    public void changeList(List<Community> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void addList(List<Community> bean) {
        list.addAll(bean);
        notifyDataSetChanged();
//        notifyItemInserted(position);
    }

//    public void updetData(int position, ShopCommoditiesBean bean) {
//        removeData(position);
//        addData(position, bean);
//    }

    //        public void removeData(int position) {
//        shopCommoditiess.remove(position);
//        notifyDataSetChanged();
//    }
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Community community = list.get(position);
        holder.count.setText(community.getMoney());
        holder.user_id.setText("ID:"+community.getUserid());
        holder.time.setText("日期:"+ DateUtil.longToDate(community.getCreatetime()));
        holder.lch_id.setText(community.getMobile());
    }

}
