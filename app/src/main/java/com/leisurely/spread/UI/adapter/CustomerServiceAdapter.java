package com.leisurely.spread.UI.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import com.leisurely.spread.R;
import com.leisurely.spread.model.bean.CustomerService;


/**
 * 右侧主界面ListView的适配器
 *
 * @author Administrator
 */
public class CustomerServiceAdapter extends BaseAdapter {
    private List<CustomerService> list;
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
            convertView = mInflater.inflate(R.layout.activity_country_list,
                    parent, false);
            viewHolder = new ViewHolder();
            viewHolder.country = convertView.findViewById(R.id.country);
            viewHolder.code = convertView.findViewById(R.id.code);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        onBindViewHolder(viewHolder, position);
        return convertView;
    }


    public CustomerServiceAdapter(Context context, List<CustomerService> list) {
        mInflater = LayoutInflater.from(context);
        this.list = list;
        this.mContext = context;
    }

    public static class ViewHolder {
        TextView country;
        TextView code;
    }

    public void changeList(List<CustomerService> list) {
        this.list = list;
        notifyDataSetChanged();
    }

//    public void addData(int position, ShopCommoditiesBean bean) {
//        shopCommoditiess.add(position, bean);
//        notifyItemInserted(position);
//    }

//    public void updetData(int position, ShopCommoditiesBean bean) {
//        removeData(position);
//        addData(position, bean);
//    }

    //        public void removeData(int position) {
//        shopCommoditiess.remove(position);
//        notifyDataSetChanged();
//    }
    public void onBindViewHolder(ViewHolder holder, final int position) {
        CustomerService customerService = list.get(position);
        holder.country.setText(customerService.getName() );
        holder.code.setVisibility(View.GONE);
//        holder.code.setText("客服号:"+customerService.getNumber());

    }

}
