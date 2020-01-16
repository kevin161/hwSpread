package com.leisurely.spread.UI.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.leisurely.spread.R;
import com.leisurely.spread.UI.activity.home.GoodsDetailActivity;
import com.leisurely.spread.model.bean.GoodsDetail;
import com.leisurely.spread.util.ImageOptions;

import java.util.List;


public class GoodsOnSellAdapter extends BaseAdapter {
    private List<GoodsDetail> list;
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
            convertView = mInflater.inflate(R.layout.list_item_goods_on_sell, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.img = convertView.findViewById(R.id.img);
            viewHolder.txtTitle = convertView.findViewById(R.id.txtTitle);
            viewHolder.txtLeft = convertView.findViewById(R.id.txtLeft);
            viewHolder.txtLeftCount = convertView.findViewById(R.id.txtLeftCount);
            viewHolder.txtCost = convertView.findViewById(R.id.txtCost);
            viewHolder.txtPurchase = convertView.findViewById(R.id.txtPurchase);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final GoodsDetail detail = list.get(position);
        ImageOptions.showImage(detail.getDefaultImgUrl(), viewHolder.img);
        viewHolder.txtTitle.setText(detail.getName());
        viewHolder.txtLeft.setText(detail.getSurplusStock());
        viewHolder.txtLeftCount.setText(detail.getNumber() + detail.getUnits());
        viewHolder.txtCost.setText(detail.getPrice());
        if (detail.getRewardType() == 1) {
//            代理商
            viewHolder.txtPurchase.setText("认购代理商");
        } else {
//            分销商
            viewHolder.txtPurchase.setText("认购分销商");
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, GoodsDetailActivity.class).putExtra("id", detail.getId() + ""));
            }
        });

        return convertView;
    }


    public GoodsOnSellAdapter(Context context, List<GoodsDetail> list) {
        mInflater = LayoutInflater.from(context);
        this.list = list;
        this.mContext = context;
    }

    public static class ViewHolder {
        ImageView img;
        TextView txtTitle;
        TextView txtLeft;
        TextView txtLeftCount;
        TextView txtCost;
        TextView txtPurchase;


    }

    public void changeList(List<GoodsDetail> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void addList(List<GoodsDetail> bean) {
        list.addAll(bean);
        notifyDataSetChanged();
//        notifyItemInserted(position);
    }


}
