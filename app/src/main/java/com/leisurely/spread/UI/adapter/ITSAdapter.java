package com.leisurely.spread.UI.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leisurely.spread.R;
import com.leisurely.spread.UI.activity.money.ITSActivity;
import com.leisurely.spread.model.bean.ITS;
import com.leisurely.spread.util.StringUtil;
import com.leisurely.spread.util.TextUtil;

import java.util.List;


/**
 * 右侧主界面ListView的适配器
 *
 * @author Administrator
 */
public class ITSAdapter extends BaseAdapter {
    private List<ITS> list;
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
            convertView = mInflater.inflate(R.layout.activity_its_list,
                    parent, false);
            viewHolder = new ViewHolder();
            viewHolder.name = convertView.findViewById(R.id.name);
            viewHolder.volume = convertView.findViewById(R.id.volume);
            viewHolder.price = convertView.findViewById(R.id.price);
            viewHolder.alipay = convertView.findViewById(R.id.alipay);
            viewHolder.wechat = convertView.findViewById(R.id.wechat);
            viewHolder.card = convertView.findViewById(R.id.card);
            viewHolder.num = convertView.findViewById(R.id.num);
            viewHolder.stock = convertView.findViewById(R.id.stock);
            viewHolder.label = convertView.findViewById(R.id.label);
            viewHolder.title_li = convertView.findViewById(R.id.title_li);
            viewHolder.list_li = convertView.findViewById(R.id.list_li);
            viewHolder.buy = convertView.findViewById(R.id.buy);
            viewHolder.sell = convertView.findViewById(R.id.sell);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        onBindViewHolder(viewHolder, position);
        return convertView;
    }


    public ITSAdapter(Context context, List<ITS> list) {
        mInflater = LayoutInflater.from(context);
        this.list = list;
        this.mContext = context;
    }

    public static class ViewHolder {
        TextView name;
        TextView volume;
        TextView price;
        ImageView alipay;
        ImageView wechat;
        ImageView card;
        TextView num;
        TextView stock;
        LinearLayout label;
        LinearLayout title_li;
        LinearLayout list_li;
         TextView buy;
         TextView sell;

    }

    public void changeList(List<ITS> list) {
        this.list = list;
        list.add(0,new ITS());
        notifyDataSetChanged();
    }

    public void addList(List<ITS> bean) {
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
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        if (position ==0) {
            holder.title_li.setVisibility(View.VISIBLE);
            holder.list_li.setVisibility(View.GONE);
            holder.buy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ITSActivity)mContext).changeType(0,holder.buy,holder.sell);
                }
            });
            holder.sell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ITSActivity)mContext).changeType(1,holder.buy,holder.sell);
                }
            });
        } else {
            holder.title_li.setVisibility(View.GONE);
            holder.list_li.setVisibility(View.VISIBLE);
            ITS its = list.get(position);
            holder.name.setText(its.getName());
            holder.volume.setText("成交 " + its.getVolume() + " | 成功率" + its.getRate());
            holder.price.setText(TextUtil.get2Double(its.getPrice()) + " CNY");
            holder.alipay.setVisibility(View.GONE);
            holder.wechat.setVisibility(View.GONE);
            holder.card.setVisibility(View.GONE);
            for (String s : its.getPaymentdata().split(",")) {
                if ("1".equals(s)) {
                    holder.alipay.setVisibility(View.VISIBLE);
                } else if ("2".equals(s)) {
                    holder.wechat.setVisibility(View.VISIBLE);
                } else if ("3".equals(s)) {
                    holder.card.setVisibility(View.VISIBLE);
                }
            }

            holder.num.setText(TextUtil.get2Double(its.getMinimum()) +
                    " 一 " + TextUtil.get2Double(its.getMaximum()) + " CNY");
            holder.stock.setText(its.getStock() + " ITS");
            holder.label.removeAllViews();
            if (its.getLabel() != null) {
                for (String s : its.getLabel().split(",")) {
                    if (StringUtil.isNotNull(s)) {
                        holder.label.addView(addText(s));
                    }
                }
            }
        }
    }

    private TextView addText(String content) {

        TextView textView = new TextView(mContext);
        textView.setTextColor(mContext.getResources().getColor(R.color.color_DCB981));
        textView.setTextSize(11);
        textView.setBackground(mContext.getResources().getDrawable(R.drawable.dcb981_6dp_side));
        textView.setText(content);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10, 0, 0, 0);//4个参数按顺序分别是左上右下
        textView.setLayoutParams(layoutParams);
        textView.setPadding(4, 2, 4, 2);
        return textView;
    }

}
