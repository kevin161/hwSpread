package com.leisurely.spread.UI.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.leisurely.spread.R;
import com.leisurely.spread.model.bean.Chongbi;
import com.leisurely.spread.util.DateUtil;

import java.util.List;


/**
 * 右侧主界面ListView的适配器
 *
 * @author Administrator
 */
public class ChongbiDetailAdapter extends BaseAdapter {
    private List<Chongbi> list;
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
            convertView = mInflater.inflate(R.layout.activity_chongbi_detail_list,
                    parent, false);
            viewHolder = new ViewHolder();
            viewHolder.text = convertView.findViewById(R.id.text);
            viewHolder.money = convertView.findViewById(R.id.money);
            viewHolder.status = convertView.findViewById(R.id.status);
            viewHolder.time = convertView.findViewById(R.id.time);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        onBindViewHolder(viewHolder, position);
        return convertView;
    }


    public ChongbiDetailAdapter(Context context, List<Chongbi> list) {
        mInflater = LayoutInflater.from(context);
        this.list = list;
        this.mContext = context;
    }

    public static class ViewHolder {
        TextView text;
        TextView money;
        TextView status;
        TextView time;
    }

    public void changeList(List<Chongbi> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void addList(List<Chongbi> bean) {
        list.addAll(bean);
        notifyDataSetChanged();
//        notifyItemInserted(position);
    }

    public void onBindViewHolder(ViewHolder holder, final int position) {
        Chongbi cb = list.get(position);
        holder.money.setText("+"+cb.getCount());
        holder.time.setText(DateUtil.getdata(cb.getAddtime()));
        if(cb.getStatus() ==0){
            holder.status.setText("待审核");
            holder.status.setTextColor(mContext.getResources().getColor(R.color.color_CCCCCC));
        }else if(cb.getStatus() ==1){
            holder.status.setText("成功");
            holder.status.setTextColor(mContext.getResources().getColor(R.color.light));
        }else if(cb.getStatus() ==2){
            holder.status.setText("驳回");
            holder.status.setTextColor(mContext.getResources().getColor(R.color.color_fc3b40));
        }


    }

}
