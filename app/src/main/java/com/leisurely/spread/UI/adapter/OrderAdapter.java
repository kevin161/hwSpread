package com.leisurely.spread.UI.adapter;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.leisurely.spread.R;
import com.leisurely.spread.framework.base.BaseActivity;
import com.leisurely.spread.model.bean.Address;
import com.leisurely.spread.model.bean.Order;
import com.leisurely.spread.util.DateUtil;
import com.leisurely.spread.util.ImageOptions;
import com.leisurely.spread.util.StringUtil;
import com.leisurely.spread.util.TextUtil;

import java.util.List;


/**
 * 右侧主界面ListView的适配器
 *
 * @author Administrator
 */
public class OrderAdapter extends BaseAdapter {
    private List<Order> list;
    private LayoutInflater mInflater;
    private BaseActivity mContext;
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
            convertView = mInflater.inflate(R.layout.list_item_order, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imgGoods = convertView.findViewById(R.id.imgGoods);
            viewHolder.txtDate = convertView.findViewById(R.id.txtDate);
            viewHolder.txtTime = convertView.findViewById(R.id.txtTime);
            viewHolder.txtGoodName = convertView.findViewById(R.id.txtGoodName);
            viewHolder.txtOrderNum = convertView.findViewById(R.id.txtOrderNum);
//            viewHolder.send_time = convertView.findViewById(R.id.send_time);
            viewHolder.txtGoodNum = convertView.findViewById(R.id.txtGoodNum);
//            viewHolder.price = convertView.findViewById(R.id.price);
            viewHolder.txtCost = convertView.findViewById(R.id.txtCost);
            viewHolder.exOrder = convertView.findViewById(R.id.exOrder);
            viewHolder.txtStatus = convertView.findViewById(R.id.txtStatus);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final Order order = (Order) getItem(position);
        ImageOptions.showImage(order.getDefaultImgUrl(), viewHolder.imgGoods);

        viewHolder.txtDate.setText(DateUtil.longToDate(order.getCreatedAt()));
        viewHolder.txtTime.setText(DateUtil.getMini(order.getCreatedAt() + ""));
        viewHolder.txtGoodName.setText(order.getGoodName());
        viewHolder.txtOrderNum.setText(order.getNumber());
        viewHolder.txtGoodNum.setText(order.getGoodNumber() + order.getUnits());
        viewHolder.txtCost.setText(order.getTotalPrice());
        if (TextUtils.isEmpty(order.getExpressNo())) {
            viewHolder.exOrder.setText("暂无");
        } else {
            viewHolder.exOrder.setText(order.getExpressNo());
        }
        switch (order.getIsSend()) {
            case "1":
                viewHolder.txtStatus.setText("未发货");
                convertView.setOnClickListener(null);
                break;
            case "2":
                viewHolder.txtStatus.setText("已发货");
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog(mContext, order);
                    }
                });
                break;
            case "3":
                viewHolder.txtStatus.setText("无需发货");
                convertView.setOnClickListener(null);
                break;
            default:
                viewHolder.txtStatus.setText("未知");
                convertView.setOnClickListener(null);
                break;
        }
        return convertView;
    }


    public OrderAdapter(BaseActivity context, List<Order> list) {
        mInflater = LayoutInflater.from(context);
        this.list = list;
        this.mContext = context;
    }

    public static class ViewHolder {
        TextView txtDate, txtTime, txtGoodName, txtOrderNum, txtGoodNum, txtCost, exOrder, txtStatus;
        ImageView imgGoods;


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

    private void showDialog(Context context, Order order) {
        final View view = LayoutInflater.from(context).inflate(R.layout.order_list_dialog, null);
        final Dialog backDialog = new Dialog(context, R.style.officeDialog);
        backDialog.setCancelable(true);
        backDialog.setContentView(view);
        final TextView txtGoodExpress = view.findViewById(R.id.txtGoodExpress);
        TextView txtGoodDetail = view.findViewById(R.id.txtGoodDetail);
        TextView txtCopy = view.findViewById(R.id.txtCopy);
        TextView txtKnow = view.findViewById(R.id.iKnow);

        txtGoodDetail.setText(order.getGoodName());
        txtGoodExpress.setText(order.getExpressCompany() + ":" + order.getExpressNo());
        txtCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtil.isNotNull(txtGoodExpress.getText().toString())) {
                    TextUtil.onClickCopy(txtGoodExpress.getText().toString(), mContext);
                }
                backDialog.cancel();
            }
        });

        txtKnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backDialog.cancel();
            }
        });

        backDialog.show();
    }


    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Order order = list.get(position);

//        holder.order_no.setText(order.getOrderNo());
//        holder.address.setText(order.getAddress());
//        holder.creat_time.setText(DateUtil.getdata(order.getCreatedAt()));
////        if(order.getSendTime()==0){
////            holder.send_time.setText("未发货");
////        }else{
////            holder.send_time.setText(DateUtil.getdata(order.getSendTime()));
////        }
//        holder.good_name.setText(order.getGoodName());
//        holder.number.setText(order.getNumber());
//        holder.phone.setText(order.getPhone());
////        holder.price.setText(order.getPrice());
//        holder.total_price.setText(order.getTotalPrice());
//        holder.name.setText(order.getUserName());
//        String s = "未发货";
//        if ("1".equals(order.getIsSend())) {
//            holder.status.setTextColor(mContext.getResources().getColor(R.color.color_083CFD));
//            s = "未发货";
//        } else if ("2".equals(order.getIsSend())) {
//            holder.status.setTextColor(mContext.getResources().getColor(R.color.color_fdcd00));
//            s = "已发货";
//        } else if ("3".equals(order.getIsSend())) {
//            holder.status.setTextColor(mContext.getResources().getColor(R.color.color_969696));
//            s = "已收货";
//        } else if ("4".equals(order.getIsSend())) {
//            holder.status.setTextColor(mContext.getResources().getColor(R.color.color_FF3230));
//            s = "已奖励";
//        }
//        if ("2".equals(order.getIsSend())) {
//            holder.all.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mContext.startActivity(new Intent(mContext, OrderFahuoActivity.class));
//                }
//            });
//        } else {
//            holder.all.setOnClickListener(null);
//        }
//
//        holder.status.setText(s);
    }

}
