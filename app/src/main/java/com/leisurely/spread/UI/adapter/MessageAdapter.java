package com.leisurely.spread.UI.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leisurely.spread.R;
import com.leisurely.spread.model.bean.Message;
import com.leisurely.spread.util.DateUtil;

import java.util.List;


public class MessageAdapter extends BaseAdapter {

    private Context mContext;
    private List<Message> list;
    private LayoutInflater mInflater;
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
            convertView = mInflater.inflate(R.layout.activity_message_list,
                    parent, false);
            viewHolder = new ViewHolder();
            viewHolder.message_re = convertView.findViewById(R.id.message_re);
            viewHolder.title = convertView.findViewById(R.id.title);
            viewHolder.time = convertView.findViewById(R.id.time);
            viewHolder.content = convertView.findViewById(R.id.content);
            viewHolder.image = convertView.findViewById(R.id.image);
            viewHolder.read_alert = convertView.findViewById(R.id.read_alert);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        onBindViewHolder(viewHolder, position);
        return convertView;
    }


    public MessageAdapter(Context context, List<Message> list) {
        mInflater = LayoutInflater.from(context);
        this.list = list;
        this.mContext = context;
    }

    public static class ViewHolder {
        RelativeLayout message_re;
        TextView title;
        TextView time;
        TextView content;
        ImageView image;
        TextView read_alert;
    }

    public void changeList(List<Message> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void addList(List<Message> bean) {
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
//        Country country = list.get(position);
//        holder.country.setText(country.getName() + country.getAbbr());
//        holder.code.setText(country.getCode());
        Message message = list.get(position);
        holder.title.setText(message.getTitle());
        holder.content.setText(message.getContent());
        holder.time.setText(DateUtil.getdata(message.getCreated()));
        holder.message_re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.content.getMaxLines() == 1) {
                    holder.content.setMaxLines(99);
                    holder.image.setBackground(mContext.getResources().getDrawable(R.mipmap.youhuiquan_tiaojian_off));
                } else {
                    holder.content.setMaxLines(1);
                    holder.image.setBackground(mContext.getResources().getDrawable(R.mipmap.youhuiquan_tiaojian));
                }
            }
        });

//        if(message.isRead()){
//            holder.read_alert.setVisibility(View.INVISIBLE);
//        }else {
//            holder.read_alert.setVisibility(View.VISIBLE);
//        }
    }

    public List<Message> getList() {
        return list;
    }
}
