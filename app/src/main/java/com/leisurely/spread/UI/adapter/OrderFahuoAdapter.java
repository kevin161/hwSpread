package com.leisurely.spread.UI.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leisurely.spread.R;
import com.leisurely.spread.UI.activity.home.OrderFahuoActivity;
import com.leisurely.spread.UI.activity.setting.OrderQueryActivity;
import com.leisurely.spread.config.SysConstants;
import com.leisurely.spread.model.bean.Order;
import com.leisurely.spread.util.DateUtil;
import com.leisurely.spread.util.TextUtil;

import java.util.List;


/**
 * 右侧主界面ListView的适配器
 *
 * @author Administrator
 */
public class OrderFahuoAdapter extends BaseAdapter {
    private List<Order> list;
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
            convertView = mInflater.inflate(R.layout.order_list,
                    parent, false);
            viewHolder = new ViewHolder();
            viewHolder.order_no = convertView.findViewById(R.id.order_no);
            viewHolder.name = convertView.findViewById(R.id.name);
            viewHolder.phone = convertView.findViewById(R.id.phone);
            viewHolder.status = convertView.findViewById(R.id.status);
            viewHolder.good_name = convertView.findViewById(R.id.good_name);
            viewHolder.creat_time = convertView.findViewById(R.id.creat_time);
            viewHolder.send_time = convertView.findViewById(R.id.send_time);
            viewHolder.number = convertView.findViewById(R.id.number);
//            viewHolder.price = convertView.findViewById(R.id.price);
            viewHolder.total_price = convertView.findViewById(R.id.total_price);
            viewHolder.address = convertView.findViewById(R.id.address);
            viewHolder.all = convertView.findViewById(R.id.all);
            viewHolder.company = convertView.findViewById(R.id.company);
            viewHolder.express_no = convertView.findViewById(R.id.express_no);
            viewHolder.send_time_li = convertView.findViewById(R.id.send_time_li);
            viewHolder.express_no_li = convertView.findViewById(R.id.express_no_li);
            viewHolder.company_li = convertView.findViewById(R.id.company_li);
            viewHolder.btn_re = convertView.findViewById(R.id.btn_re);
            viewHolder.commit = convertView.findViewById(R.id.commit);
            viewHolder.wuliu = convertView.findViewById(R.id.wuliuxinxi);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        onBindViewHolder(viewHolder, position);
        return convertView;
    }


    public OrderFahuoAdapter(Context context, List<Order> list) {
        mInflater = LayoutInflater.from(context);
        this.list = list;
        this.mContext = context;
    }

    public static class ViewHolder {
        TextView order_no;
        TextView name;
        TextView phone;
        TextView good_name;
        TextView creat_time;
        TextView send_time;
        TextView number;
        TextView status;
        //        TextView price;
        TextView total_price;
        TextView address;
        LinearLayout all;
        TextView company;
        TextView express_no;
        LinearLayout send_time_li;
        LinearLayout company_li;
        LinearLayout express_no_li;
        RelativeLayout btn_re;
        TextView commit;
        TextView wuliu;

    }

    public void changeList(List<Order> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void addList(List<Order> bean) {
        list.addAll(bean);
        notifyDataSetChanged();
//        notifyItemInserted(position);
    }

    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Order order = list.get(position);
        holder.send_time_li.setVisibility(View.VISIBLE);
        holder.company_li.setVisibility(View.VISIBLE);
        holder.express_no_li.setVisibility(View.VISIBLE);
        holder.commit.setVisibility(View.VISIBLE);
        holder.wuliu.setVisibility(View.VISIBLE);
        holder.btn_re.setVisibility(View.VISIBLE);
        holder.order_no.setText(order.getOrderNo());
        holder.address.setText(order.getAddress());
        holder.creat_time.setText(DateUtil.getdata(order.getCreatedAt()));
        if (order.getSendTime() == 0) {
            holder.send_time.setText("未发货");
        } else {
            holder.send_time.setText(DateUtil.getdata(order.getSendTime()));
        }
        holder.good_name.setText(order.getGoodName());
        holder.number.setText(order.getNumber());
        holder.phone.setText(order.getPhone());
//        holder.price.setText(order.getPrice());
        holder.total_price.setText(order.getTotalPrice());
        holder.name.setText(order.getUserName());
        holder.company.setText(order.getExpressCompany());
        holder.express_no.setText(order.getExpressNo());
        String s = "未发货";
        if ("1".equals(order.getIsSend())) {
            holder.status.setTextColor(mContext.getResources().getColor(R.color.color_083CFD));
            s = "未发货";
        } else if ("2".equals(order.getIsSend())) {
            holder.status.setTextColor(mContext.getResources().getColor(R.color.color_fdcd00));
            s = "已发货";
        } else if ("3".equals(order.getIsSend())) {
            holder.status.setTextColor(mContext.getResources().getColor(R.color.color_969696));
            s = "已收货";
        } else if ("4".equals(order.getIsSend())) {
            holder.status.setTextColor(mContext.getResources().getColor(R.color.color_FF3230));
            s = "已奖励";
        }
        holder.status.setText(s);
        holder.wuliu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextUtil.onClickCopy(order.getExpressNo(), (OrderFahuoActivity) mContext, "快递单号已复制，请到输入框粘贴");
                mContext.startActivity(new Intent(mContext, OrderQueryActivity.class)
                        .putExtra("url", SysConstants.OrderQueryUrl)
                        .putExtra("id", order.getId())
                );
            }
        });
        holder.commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((OrderFahuoActivity) mContext).queren(position);
            }
        });
    }

}
