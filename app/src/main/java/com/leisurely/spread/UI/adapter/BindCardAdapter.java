package com.leisurely.spread.UI.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leisurely.spread.R;
import com.leisurely.spread.UI.activity.money.BindCardActivity;
import com.leisurely.spread.model.bean.Bank;

import java.util.List;


/**
 * 右侧主界面ListView的适配器
 *
 * @author Administrator
 */
public class BindCardAdapter extends BaseAdapter {
    private List<Bank> list;
    private LayoutInflater mInflater;
    private Context mContext;
    private ViewHolder viewHolder;
    private BindCardActivity activity;


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
            convertView = mInflater.inflate(R.layout.bindcard_list,
                    parent, false);
            viewHolder = new ViewHolder();
            viewHolder.bank_name = convertView.findViewById(R.id.bank_name);
            viewHolder.delete = convertView.findViewById(R.id.delete);
            viewHolder.card = convertView.findViewById(R.id.card);
            viewHolder.logo_bc = convertView.findViewById(R.id.logo_bc);
            viewHolder.logo = convertView.findViewById(R.id.logo);
            viewHolder.bind_card_re = convertView.findViewById(R.id.bind_card_re);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        onBindViewHolder(viewHolder, position);
        return convertView;
    }


    public BindCardAdapter(BindCardActivity activity, List<Bank> list) {
        mInflater = LayoutInflater.from(activity.getApplicationContext());
        this.list = list;
        this.mContext = activity.getApplicationContext();
        this.activity = activity;
    }

    public static class ViewHolder {
        TextView bank_name;
        TextView delete;
        TextView card;
        ImageView logo_bc;
        ImageView logo;
        RelativeLayout bind_card_re;

    }

    public void changeList(List<Bank> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void addList(List<Bank> bean) {
        list.addAll(bean);
        notifyDataSetChanged();
//        notifyItemInserted(position);
    }

    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Bank bank = list.get(position);
        String name = bank.getBankName();
        Drawable drawable1;
        Drawable drawable2;
        Drawable drawable3;
        if (name.contains("工商")) {
            drawable1 = mContext.getResources().getDrawable(R.drawable.gongshang_6dp);
            drawable2 = mContext.getResources().getDrawable(R.mipmap.gongshang_logo);
            drawable3 = mContext.getResources().getDrawable(R.mipmap.gongshang_logo_bc);
        } else if (name.contains("建设")) {
            drawable1 = mContext.getResources().getDrawable(R.drawable.jianshe_6dp);
            drawable2 = mContext.getResources().getDrawable(R.mipmap.jianshe_logo);
            drawable3 = mContext.getResources().getDrawable(R.mipmap.jianshe_logo_bc);
        } else if (name.contains("交通")) {
            drawable1 = mContext.getResources().getDrawable(R.drawable.jiaotong_6dp);
            drawable2 = mContext.getResources().getDrawable(R.mipmap.jiaotong_logo);
            drawable3 = mContext.getResources().getDrawable(R.mipmap.jiaotong_logo_bc);
        } else if (name.contains("民生")) {
            drawable1 = mContext.getResources().getDrawable(R.drawable.mingsheng_6dp);
            drawable2 = mContext.getResources().getDrawable(R.mipmap.mingsheng_logo);
            drawable3 = mContext.getResources().getDrawable(R.mipmap.mingsheng_logo_bc);
        } else if (name.contains("农业")) {
            drawable1 = mContext.getResources().getDrawable(R.drawable.nongye_6dp);
            drawable2 = mContext.getResources().getDrawable(R.mipmap.nongye_logo);
            drawable3 = mContext.getResources().getDrawable(R.mipmap.nongye_logo_bc);
        } else if (name.contains("浦发") || name.contains("浦东发展")) {
            drawable1 = mContext.getResources().getDrawable(R.drawable.pufa_6dp);
            drawable2 = mContext.getResources().getDrawable(R.mipmap.pufa_logo);
            drawable3 = mContext.getResources().getDrawable(R.mipmap.pufa_logo_bc);
        } else if (name.contains("邮政")) {
            drawable1 = mContext.getResources().getDrawable(R.drawable.youzheng_6dp);
            drawable2 = mContext.getResources().getDrawable(R.mipmap.youzheng_logo);
            drawable3 = mContext.getResources().getDrawable(R.mipmap.youzheng_logo_bc);
        } else if (name.contains("招商")) {
            drawable1 = mContext.getResources().getDrawable(R.drawable.zhaoshang_6dp);
            drawable2 = mContext.getResources().getDrawable(R.mipmap.zhaoshang_logo);
            drawable3 = mContext.getResources().getDrawable(R.mipmap.zhaoshang_logo_bc);
        } else if (name.contains("中国")) {
            drawable1 = mContext.getResources().getDrawable(R.drawable.zhongguo_6dp);
            drawable2 = mContext.getResources().getDrawable(R.mipmap.zhongguo_logo);
            drawable3 = mContext.getResources().getDrawable(R.mipmap.zhongguo_logo_bc);
        } else if (name.contains("中信")) {
            drawable1 = mContext.getResources().getDrawable(R.drawable.zhongxin_6dp);
            drawable2 = mContext.getResources().getDrawable(R.mipmap.zhongxin_logo);
            drawable3 = mContext.getResources().getDrawable(R.mipmap.zhongxin_logo_bc);
        } else {
            drawable1 = mContext.getResources().getDrawable(R.drawable.moren_6dp);
            drawable2 = mContext.getResources().getDrawable(R.mipmap.moren_logo);
            drawable3 = mContext.getResources().getDrawable(R.color.color_3b3b3b);
        }
        holder.bank_name.setText(name);
        holder.bind_card_re.setBackground(drawable1);
        holder.logo.setImageDrawable(drawable2);
        holder.logo_bc.setImageDrawable(drawable3);
        holder.card.setText("**** **** **** " + bank.getCardNum().substring(bank.getCardNum().length() - 4));
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.showAlert(bank, position);
            }
        });

    }



}
