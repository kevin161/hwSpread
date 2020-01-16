package com.leisurely.spread.UI.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.leisurely.spread.R;
import com.leisurely.spread.model.bean.Withdrawal;
import com.leisurely.spread.util.DateUtil;

import java.util.List;


/**
 * 右侧主界面ListView的适配器
 *
 * @author Administrator
 */
public class WithdrawalAdapter extends BaseAdapter {
    private List<Withdrawal> list;
    private LayoutInflater mInflater;
    private Context mContext;
    private ViewHolder holder;


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
            holder = new ViewHolder();
            holder.status = convertView.findViewById(R.id.status);
            holder.amount = convertView.findViewById(R.id.amount);
            holder.time = convertView.findViewById(R.id.time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
//        onBindViewHolder(viewHolder, position);
        final Withdrawal withdrawal = list.get(position);
        holder.time.setText(DateUtil.getdata(withdrawal.getCreatedAt()));
        holder.amount.setText(withdrawal.getAmount());
        String status = "";

        /**
         *0.待审核
         * 1.审核通过
         * 2.支付处理中
         * 3.提现成功
         * 4.提现失败
         * 5.审核不通过
         */
        if (withdrawal.getStatus() == 0) {
            status = "待审核";
            holder.status.setTextColor(Color.parseColor("#606060"));
            convertView.setOnClickListener(null);
        } else if (withdrawal.getStatus() == 1) {
            status = "审核通过";
            holder.status.setTextColor(Color.parseColor("#606060"));
            convertView.setOnClickListener(null);
        } else if (withdrawal.getStatus() == 2) {
            status = "支付处理中";
            holder.status.setTextColor(Color.parseColor("#606060"));
            convertView.setOnClickListener(null);
        } else if (withdrawal.getStatus() == 3) {
            status = "提现成功";
            holder.status.setTextColor(Color.parseColor("#2f7dff"));
            convertView.setOnClickListener(null);
        } else if (withdrawal.getStatus() == 4) {
            status = "提现失败";
            convertView.setOnClickListener(null);
        } else if (withdrawal.getStatus() == 5) {
            holder.status.setTextColor(Color.parseColor("#000000"));
            status = "审核不通过 -- 查看原因";
            holder.status.setTextColor(Color.parseColor("##fd3a3c"));
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog(mContext, withdrawal);
                }
            });
        }
        holder.status.setText(status);
        return convertView;
    }


    public WithdrawalAdapter(Context context, List<Withdrawal> list) {
        mInflater = LayoutInflater.from(context);
        this.list = list;
        this.mContext = context;
    }

    public static class ViewHolder {
        TextView status;
        TextView amount;
        TextView time;

    }

    public void changeList(List<Withdrawal> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void addList(List<Withdrawal> bean) {
        list.addAll(bean);
        notifyDataSetChanged();
    }

    private void showDialog(Context context, Withdrawal withdrawal) {
        final View view = LayoutInflater.from(context).inflate(R.layout.withdrawal_dialog, null);
        final Dialog backDialog = new Dialog(context, R.style.officeDialog);
        backDialog.setCancelable(true);
        backDialog.setContentView(view);
        TextView txtTime = view.findViewById(R.id.txtTime);
        TextView txtState = view.findViewById(R.id.txtState);
        TextView txtStateDetail = view.findViewById(R.id.txtStateDetail);
        TextView txtKnow = view.findViewById(R.id.txtKnow);

        txtTime.setText(DateUtil.getdata(withdrawal.getCreatedAt()));
        txtState.setText("提现" + withdrawal.getAmount() + "元失败");
        txtStateDetail.setText(withdrawal.getMemos());

        txtKnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backDialog.cancel();
            }
        });

        backDialog.show();
    }


}
