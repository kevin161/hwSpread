package com.leisurely.spread.UI.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.leisurely.spread.R;
import com.leisurely.spread.model.bean.Quantification;
import com.leisurely.spread.util.DateUtil;

import java.util.List;


/**
 * 右侧主界面ListView的适配器
 *
 * @author Administrator
 */
public class QuantificationDetailAdapter extends BaseAdapter {
    private List<Quantification> list;
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
            convertView = mInflater.inflate(R.layout.activity_quantification_detail_list,
                    parent, false);
            viewHolder = new ViewHolder();
            viewHolder.day = convertView.findViewById(R.id.day);
            viewHolder.quantification_day = convertView.findViewById(R.id.quantification_day);
            viewHolder.day_money = convertView.findViewById(R.id.day_money);
            viewHolder.status = convertView.findViewById(R.id.status_img);
            viewHolder.count = convertView.findViewById(R.id.count);
            viewHolder.complete = convertView.findViewById(R.id.complete);
            viewHolder.time = convertView.findViewById(R.id.time);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        onBindViewHolder(viewHolder, position);
        return convertView;
    }


    public QuantificationDetailAdapter(Context context, List<Quantification> list) {
        mInflater = LayoutInflater.from(context);
        this.list = list;
        this.mContext = context;
    }

    public static class ViewHolder {
        TextView day;
        TextView day_money;
        TextView quantification_day;
        ImageView status;
        TextView count;
        TextView complete;
        TextView time;
    }

    public void changeList(List<Quantification> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void addList(List<Quantification> bean) {
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
        final Quantification quantification = list.get(position);
        holder.day.setText(quantification.getDay() + "天");
        holder.quantification_day.setText(quantification.getDay() + "天");
        holder.day_money.setText(quantification.getRate()+"%");
        holder.time.setText(DateUtil.getdata(quantification.getCreatetime()));
        holder.complete.setText(quantification.getComplete()+"LCH");
        holder.count.setText(quantification.getMoney()+"LCH");

        if(quantification.getIsend()==0){
            holder.status.setBackground(mContext.getResources().getDrawable(R.mipmap.me_lianghaumingxi_makeing_con));
        }else if(quantification.getIsend()==1){
            holder.status.setBackground(mContext.getResources().getDrawable(R.mipmap.me_lianghaumingxi_done_con));
        }else if(quantification.getIsend()==2){
            holder.status.setBackground(mContext.getResources().getDrawable(R.mipmap.me_lianghuamingxi_out_icon));
        }

    }

}
