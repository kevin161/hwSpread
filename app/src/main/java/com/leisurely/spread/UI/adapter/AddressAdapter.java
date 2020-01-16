package com.leisurely.spread.UI.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.leisurely.spread.R;
import com.leisurely.spread.UI.activity.money.AddressActivity;
import com.leisurely.spread.model.bean.Address;

import java.util.List;


/**
 * 右侧主界面ListView的适配器
 *
 * @author Administrator
 */
public class AddressAdapter extends BaseAdapter {
    private List<Address> list;
    private LayoutInflater mInflater;
    private Context mContext;
    private ViewHolder viewHolder;
    private AddressActivity activity;

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void OnItemClick(Address address);
    }

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
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.address_list, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.txtNameAndPhone = convertView.findViewById(R.id.txtNameAndPhone);
            viewHolder.imgStatus = convertView.findViewById(R.id.imgStatus);
            viewHolder.delete = convertView.findViewById(R.id.delete);
            viewHolder.edit = convertView.findViewById(R.id.edit);
            viewHolder.address = convertView.findViewById(R.id.address);
            viewHolder.status = convertView.findViewById(R.id.status);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        onBindViewHolder(viewHolder, position);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.OnItemClick((Address) getItem(position));
                }
            }
        });
        return convertView;
    }


    public AddressAdapter(AddressActivity activity, List<Address> list) {
        mInflater = LayoutInflater.from(activity.getApplicationContext());
        this.list = list;
        this.mContext = activity.getApplicationContext();
        this.activity = activity;
    }

    public static class ViewHolder {
        ImageView imgStatus;
        TextView txtNameAndPhone;
        TextView address;
        TextView status;
        TextView delete;
        TextView edit;
    }

    public void changeList(List<Address> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void addList(List<Address> bean) {
        list.addAll(bean);
        notifyDataSetChanged();
//        notifyItemInserted(position);
    }

    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Address address = list.get(position);

        holder.txtNameAndPhone.setText(address.getUserName() + "    " + address.getPhone());

        StringBuffer sb = new StringBuffer(address.getProvince());
        if (!TextUtils.isEmpty(address.getCity())) {
            sb.append(address.getCity());
        }

        if (!TextUtils.isEmpty(address.getArea())) {
            sb.append(address.getArea());
        }
        sb.append(address.getAddress());
        holder.address.setText(sb.toString());

        if (address.getIsStatus() == 1) {
            holder.status.setText("默认地址");
            holder.status.setTextColor(Color.parseColor("#FF3B3D"));
            holder.imgStatus.setImageResource(R.mipmap.select_circle_tick_checked);
            holder.status.setOnClickListener(null);
        } else {
            holder.status.setText("设为默认地址");
            holder.status.setTextColor(Color.parseColor("#7D7D7D"));
            holder.imgStatus.setImageResource(R.mipmap.select_circle_tick_unchecked);
            holder.status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.changeStatus(address, position);
                }
            });
        }
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.edit(address, position);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.showAlert(address, position);
            }
        });
    }


}
