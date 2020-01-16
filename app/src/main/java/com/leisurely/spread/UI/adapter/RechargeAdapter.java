package com.leisurely.spread.UI.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leisurely.spread.R;
import com.leisurely.spread.model.bean.Recharge;
import com.leisurely.spread.util.DateUtil;

import java.util.List;


/**
 * 右侧主界面ListView的适配器
 *
 * @author Administrator
 */
public class RechargeAdapter extends BaseAdapter {
    private List<Recharge> list;
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
            convertView = mInflater.inflate(R.layout.list_item_money_history, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.status = convertView.findViewById(R.id.status);
            viewHolder.amount = convertView.findViewById(R.id.amount);
            viewHolder.time = convertView.findViewById(R.id.time);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        onBindViewHolder(viewHolder, position);
        return convertView;
    }


    public RechargeAdapter(Context context, List<Recharge> list) {
        mInflater = LayoutInflater.from(context);
        this.list = list;
        this.mContext = context;
    }

    public static class ViewHolder {
        TextView status;
        TextView amount;
        TextView time;

    }

    public void changeList(List<Recharge> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void addList(List<Recharge> bean) {
        list.addAll(bean);
        notifyDataSetChanged();
    }

    public void onBindViewHolder(ViewHolder holder, final int position) {
        Recharge recharge = list.get(position);

        holder.time.setText(DateUtil.getdata(recharge.getCreatedAt()));
        holder.amount.setText(recharge.getAmount());
        String status = "";
        if (recharge.getStatus() == 1) {
            status = "充值中";
            holder.status.setTextColor(Color.parseColor("#000000"));
        } else if (recharge.getStatus() == 2) {
            status = "充值成功";
            holder.status.setTextColor(Color.parseColor("#2f7dff"));
        } else if (recharge.getStatus() == 3) {
            status = "充值失败";
            holder.status.setTextColor(Color.parseColor("##fd3a3c"));
        } else if (recharge.getStatus() == 4) {
            status = "充值撤销";
            holder.status.setTextColor(Color.parseColor("#000000"));
        }
        holder.status.setText(status);

    }


}
