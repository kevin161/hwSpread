package com.leisurely.spread.UI.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.leisurely.spread.R;
import com.leisurely.spread.model.bean.OrderDetail;
import com.leisurely.spread.util.DateUtil;
import com.leisurely.spread.util.StringUtil;

import java.util.List;


/**
 * 右侧主界面ListView的适配器
 *
 * @author Administrator
 */
public class ITSLogAdapter extends BaseAdapter {
    private List<OrderDetail> list;
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
            convertView = mInflater.inflate(R.layout.activity_its_log_list,
                    parent, false);
            viewHolder = new ViewHolder();
            viewHolder.name = convertView.findViewById(R.id.name);
            viewHolder.orderno = convertView.findViewById(R.id.orderno);
            viewHolder.status = convertView.findViewById(R.id.status);
            viewHolder.amount = convertView.findViewById(R.id.amount);
            viewHolder.time = convertView.findViewById(R.id.time);
            viewHolder.num = convertView.findViewById(R.id.num);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        onBindViewHolder(viewHolder, position);
        return convertView;
    }


    public ITSLogAdapter(Context context, List<OrderDetail> list) {
        mInflater = LayoutInflater.from(context);
        this.list = list;
        this.mContext = context;
    }

    public static class ViewHolder {
        TextView name;
        TextView orderno;
        TextView num;
        TextView status;
        TextView amount;
        TextView time;

    }

    public void changeList(List<OrderDetail> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void addList(List<OrderDetail> bean) {
        list.addAll(bean);
        notifyDataSetChanged();
    }

    public void onBindViewHolder(ViewHolder holder, final int position) {
        OrderDetail orderDetail = list.get(position);
        holder.name.setText(StringUtil.isNotNull(orderDetail.getBuyername()) ?
                orderDetail.getBuyername() : orderDetail.getMerchant().getName());
        holder.orderno.setText("订单号: " + orderDetail.getOrderno());
        if (orderDetail.getStatus() == 0) {
            holder.status.setText("交易中");
            holder.status.setTextColor(mContext.getResources().getColor(R.color.color_fc3b40));
        } else if (orderDetail.getStatus() == 1) {
            holder.status.setText("已完成");
            holder.status.setTextColor(mContext.getResources().getColor(R.color.color_1490D8));
        } else if (orderDetail.getStatus() == 2) {
            holder.status.setText("已取消");
            holder.status.setTextColor(mContext.getResources().getColor(R.color.light));
        }

        holder.amount.setText((StringUtil.isNotNull(orderDetail.getAmount()) ?
                orderDetail.getAmount() : orderDetail.getCny()) + " CNY");
        holder.time.setText(orderDetail.getAddtime() > 0 ?
                DateUtil.getdata(orderDetail.getAddtime()) : DateUtil.getdata(orderDetail.getCreatetime()));
        holder.num.setText((StringUtil.isNotNull(orderDetail.getNumber()) ?
                orderDetail.getNumber() : orderDetail.getIts()) + " ITS");
    }

}
