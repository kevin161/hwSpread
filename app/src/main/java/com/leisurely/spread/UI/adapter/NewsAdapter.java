package com.leisurely.spread.UI.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leisurely.spread.R;
import com.leisurely.spread.UI.activity.home.NewsDatailActivity;
import com.leisurely.spread.model.bean.News;
import com.leisurely.spread.model.bean.Order;
import com.leisurely.spread.util.DateUtil;

import java.util.List;


/**
 * 右侧主界面ListView的适配器
 *
 * @author Administrator
 */
public class NewsAdapter extends BaseAdapter {
    private List<News> list;
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
            convertView = mInflater.inflate(R.layout.news_list,
                    parent, false);
            viewHolder = new ViewHolder();
            viewHolder.title = convertView.findViewById(R.id.title);
            viewHolder.time = convertView.findViewById(R.id.time);
            viewHolder.news_re = convertView.findViewById(R.id.news_re);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        onBindViewHolder(viewHolder, position);
        return convertView;
    }


    public NewsAdapter(Context context, List<News> list) {
        mInflater = LayoutInflater.from(context);
        this.list = list;
        this.mContext = context;
    }

    public static class ViewHolder {
        TextView title;
        TextView time;
        RelativeLayout news_re;
    }

    public void changeList(List<News> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void addList(List<News> bean) {
        list.addAll(bean);
        notifyDataSetChanged();
//        notifyItemInserted(position);
    }

    public void onBindViewHolder(ViewHolder holder, final int position) {
        final News news = list.get(position);
        holder.time.setText(DateUtil.longToDate(news.getPublicationDate()));
        holder.title.setText(news.getSubject());

        holder.news_re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, NewsDatailActivity.class)
                        .putExtra("id", news.getId()));
            }
        });
    }

}
