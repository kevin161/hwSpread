package com.leisurely.spread.UI.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leisurely.spread.R;
import com.leisurely.spread.UI.activity.home.NewsDatailActivity;
import com.leisurely.spread.model.bean.News;
import com.leisurely.spread.util.DateUtil;
import com.leisurely.spread.util.ImageOptions;

import java.util.List;


/**
 * 右侧主界面ListView的适配器
 *
 * @author Administrator
 */
public class GoodsIntroduceAdapter extends BaseAdapter {
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
            convertView = mInflater.inflate(R.layout.list_item_goods_introduce, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imageView = convertView.findViewById(R.id.imageView);
            viewHolder.txtTitle = convertView.findViewById(R.id.txtTitle);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
//        onBindViewHolder(viewHolder, position);
        final News news = (News) getItem(position);
        ImageOptions.showImage(news.getImageUrl(), viewHolder.imageView);
        viewHolder.txtTitle.setText(news.getSubject());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, NewsDatailActivity.class);
                intent.putExtra("id", news.getId());
                intent.putExtra("topic","PRODUCT");
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }


    public GoodsIntroduceAdapter(Context context, List<News> list) {
        mInflater = LayoutInflater.from(context);
        this.list = list;
        this.mContext = context;
    }

    public static class ViewHolder {
        ImageView imageView;
        TextView txtTitle;
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
//        final News news = list.get(position);
//        holder.time.setText(DateUtil.longToDate(news.getPublicationDate()));
//        holder.title.setText(news.getSubject());
//
//        holder.news_re.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mContext.startActivity(new Intent(mContext, NewsDatailActivity.class)
//                        .putExtra("id", news.getId()));
//            }
//        });
    }

}
