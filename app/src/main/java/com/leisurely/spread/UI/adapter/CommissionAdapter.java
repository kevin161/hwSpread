package com.leisurely.spread.UI.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leisurely.spread.R;
import com.leisurely.spread.model.bean.Commission;
import com.leisurely.spread.model.bean.Score;
import com.leisurely.spread.util.DateUtil;
import com.leisurely.spread.util.SharedPreferencesUtil;

import java.util.List;


/**
 * 右侧主界面ListView的适配器
 *
 * @author Administrator
 */
public class CommissionAdapter extends BaseAdapter {
    private List<Commission> list;
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
            convertView = mInflater.inflate(R.layout.commission_list,
                    parent, false);
            viewHolder = new ViewHolder();
            viewHolder.order_id = convertView.findViewById(R.id.order_id);
            viewHolder.proceeds_uid = convertView.findViewById(R.id.proceeds_uid);
            viewHolder.pay_uid = convertView.findViewById(R.id.pay_uid);
            viewHolder.real_commission = convertView.findViewById(R.id.real_commission);
            viewHolder.time = convertView.findViewById(R.id.time);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        onBindViewHolder(viewHolder, position);
        return convertView;
    }


    public CommissionAdapter(Context context, List<Commission> list) {
        mInflater = LayoutInflater.from(context);
        this.list = list;
        this.mContext = context;
    }

    public static class ViewHolder {
        TextView order_id;
        TextView proceeds_uid;
        TextView pay_uid;
        TextView real_commission;
        TextView time;

    }

    public void changeList(List<Commission> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void addList(List<Commission> bean) {
        list.addAll(bean);
        notifyDataSetChanged();
    }

    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Commission commission = list.get(position);
            holder.time.setText(DateUtil.getdata(commission.getCreateAt()));
            holder.order_id.setText(commission.getOrderId());
            holder.pay_uid.setText(commission.getPayUid());
            holder.proceeds_uid.setText(commission.getProceedsUid());
            holder.real_commission.setText(commission.getRealCommission());
    }

}
