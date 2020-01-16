package com.leisurely.spread.UI.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import com.leisurely.spread.R;
import com.leisurely.spread.model.bean.ForeignCurrency;
import com.leisurely.spread.util.TextUtil;


/**
 * 右侧主界面ListView的适配器
 *
 * @author Administrator
 */
public class CurrencyConverterAdapter extends BaseAdapter {
    private List<ForeignCurrency> list;
    private LayoutInflater mInflater;
    private Context mContext;
    private ViewHolder viewHolder;
    private ForeignCurrency foreignCurrency;
    private double price;

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
            convertView = mInflater.inflate(R.layout.currency_converter_list,
                    parent, false);
            viewHolder = new ViewHolder();
            viewHolder.country_image = convertView.findViewById(R.id.country_image);
            viewHolder.country_es = convertView.findViewById(R.id.country_es);
            viewHolder.price = convertView.findViewById(R.id.price);
            viewHolder.country_money = convertView.findViewById(R.id.country_money);
            viewHolder.line = convertView.findViewById(R.id.line);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        onBindViewHolder(viewHolder, position);
        return convertView;
    }


    public CurrencyConverterAdapter(Context context, List<ForeignCurrency> list, ForeignCurrency foreignCurrency, double price) {
        mInflater = LayoutInflater.from(context);
        this.list = list;
        this.mContext = context;
        this.foreignCurrency = foreignCurrency;
        this.price = price;
    }

    public void changePrice(double price) {
        this.price = price;
        notifyDataSetChanged();
    }

    public static class ViewHolder {
        ImageView country_image;
        TextView country_es;
        TextView price;
        TextView country_money;
        TextView line;
    }

    public void changeList(List<ForeignCurrency> list, ForeignCurrency foreignCurrency) {
        this.list = list;
        this.foreignCurrency = foreignCurrency;
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
        ForeignCurrency fc = list.get(position);
        holder.country_image.setBackground(mContext.getResources().getDrawable(fc.getLogo()));
        holder.country_es.setText(fc.getCode());
        holder.country_money.setText(fc.getName());
        holder.price.setText(TextUtil.parseMoney(TextUtil.get2Double(price * foreignCurrency.getRate() / fc.getRate())));

        if (position == list.size() - 1) {
            holder.line.setVisibility(View.GONE);
        }else{
            holder.line.setVisibility(View.VISIBLE);
        }

//        Country country = list.get(position);
//        holder.country.setText(country.getName() + country.getAbbr());
//        holder.code.setText(country.getCode());

    }

}
