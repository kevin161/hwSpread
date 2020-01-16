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
import com.leisurely.spread.model.bean.Money;
import com.leisurely.spread.util.DateUtil;

import java.util.List;


/**
 * 右侧主界面ListView的适配器
 *
 * @author Administrator
 */
public class MoneyAdapter extends BaseAdapter {
    private List<Money> list;
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
            convertView = mInflater.inflate(R.layout.money_list,parent, false);
            viewHolder = new ViewHolder();
            viewHolder.txtTime = convertView.findViewById(R.id.txtTime);
            viewHolder.txtType = convertView.findViewById(R.id.txtType);
            viewHolder.txtCost = convertView.findViewById(R.id.txtCost);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        onBindViewHolder(viewHolder, position);
        return convertView;
    }


    public MoneyAdapter(Context context, List<Money> list) {
        mInflater = LayoutInflater.from(context);
        this.list = list;
        this.mContext = context;
    }

    public static class ViewHolder {
        TextView txtType;
        TextView txtCost;
        TextView txtTime;

    }

    public void changeList(List<Money> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void addList(List<Money> bean) {
        list.addAll(bean);
        notifyDataSetChanged();
    }

    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Money money = list.get(position);

        holder.txtTime.setText(DateUtil.getdata(money.getCreatedAt()));
        if (money.getOperateWay() == 3) {
            holder.txtType.setText("充值");
        } else if (money.getOperateWay() == 4) {
            holder.txtType.setText("提现");
        } else if (money.getOperateWay() == 1) {
            holder.txtType.setText("冻结");
        } else if (money.getOperateWay() == 2) {
            holder.txtType.setText("解冻");
        } else if (money.getOperateWay() == 6) {
            holder.txtType.setText("消费");
        } else if (money.getOperateWay() == 5) {
            holder.txtType.setText("收入");
        } else if (money.getOperateWay() == 8) {
            holder.txtType.setText("提现手续费");
        }
        String s = "";
        if (money.getType() == 0) {
            s = "+";
            holder.txtCost.setTextColor(Color.parseColor("#FD3A3C"));
        } else {
            s = "-";
            holder.txtCost.setTextColor(Color.parseColor("#515151"));
        }
        holder.txtCost.setText(s + money.getAffectMoney());


    }


}
