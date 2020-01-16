package com.leisurely.spread.UI.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leisurely.spread.R;
import com.leisurely.spread.UI.activity.home.GoodsOnSellActivity;
import com.leisurely.spread.UI.activity.home.RegisterOrLoginActivity;
import com.leisurely.spread.framework.base.BaseActivity;
import com.leisurely.spread.model.bean.Commodity;
import com.leisurely.spread.util.ImageOptions;
import com.leisurely.spread.util.SharedPreferencesUtil;

import java.util.List;


/**
 * 右侧主界面ListView的适配器
 *
 * @author Administrator
 */
public class HomeAdapter extends BaseAdapter {
    private List<Commodity> list;
    private LayoutInflater mInflater;
    private Context mContext;
    private BaseActivity activity;
    private ViewHolder viewHolder;

    @Override
    public int getCount() {
//        int count = 0;
//        if (list.size() % 2 == 0) {
//            count = list.size() / 2;
//        }else {
//            count = list.size() / 2;
//            count++;
//        }
//        return count;
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
//            convertView = mInflater.inflate(R.layout.home_item, parent, false);
//            viewHolder = new ViewHolder();
//            viewHolder.text1 = convertView.findViewById(R.id.text1);
//            viewHolder.text2 = convertView.findViewById(R.id.text2);
//            viewHolder.lay1 = convertView.findViewById(R.id.lay1);
//            viewHolder.lay2 = convertView.findViewById(R.id.lay2);
//            viewHolder.image1 = convertView.findViewById(R.id.image1);
//            viewHolder.image2 = convertView.findViewById(R.id.image2);
//            viewHolder.lay = convertView.findViewById(R.id.lay);

            convertView = mInflater.inflate(R.layout.list_item_home, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imgGoods = convertView.findViewById(R.id.imgGoods);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
//        onBindViewHolder(viewHolder, position);
        final Commodity goods = list.get(position);

        ImageOptions.showImage(goods.getImgUrl(), viewHolder.imgGoods);
        viewHolder.imgGoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mContext.startActivity(new Intent(mContext, GoodsDetailActivity.class).putExtra("id", goods.getId()));
                if (SharedPreferencesUtil.isLogin()) {
                    mContext.startActivity(new Intent(mContext, GoodsOnSellActivity.class).putExtra("goods", goods));
                } else {
                    mContext.startActivity(new Intent(mContext, RegisterOrLoginActivity.class));
                }
            }
        });

        return convertView;
    }


    public HomeAdapter(BaseActivity context, List<Commodity> list) {
        mInflater = LayoutInflater.from(context);
        this.list = list;
        this.mContext = context;
        this.activity = context;
    }

    public static class ViewHolder {
        TextView text1;
        TextView text2;
        ImageView image1;
        ImageView image2;
        ImageView imgGoods;
        LinearLayout lay1;
        LinearLayout lay2;
        LinearLayout lay;
    }

    public void changeList(List<Commodity> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void addList(List<Commodity> bean) {
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
//        final  int index = position*2;
//        final Goods goods = list.get(index);
//            holder.lay.setVisibility(View.VISIBLE);
//            holder.text1.setText(goods.getName());
//            if (goods.getGoodImgs() != null && goods.getGoodImgs().size() > 0) {
//                activity.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//
////                        RoundedCorners roundedCorners= new RoundedCorners(20);
//////通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
////                        RequestOptions options=RequestOptions.bitmapTransform(roundedCorners).override(300, 300);
////
////                        Glide.with(mContext).load(JSONObject.parseObject(goods.getGoodImgs().get(0)).getString("imgUrl"))
////                                .apply(options).into(holder.image1);
//                        ImageOptions.showImage(JSONObject.parseObject(goods.getGoodImgs().get(0)).getString("imgUrl"), holder.image1);
//                    }
//                });
//            }else {
//                holder.image1.setImageDrawable(null);
//            }
//            holder.lay1.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    mContext.startActivity(new Intent(mContext, GoodsDetailActivity.class).putExtra("id", goods.getId()));
//                }
//            });
//            if ((index + 1) == list.size()) {
//                holder.lay2.setVisibility(View.INVISIBLE);
//            } else {
//                final Goods goods2 = list.get(index + 1);
//                holder.lay2.setVisibility(View.VISIBLE);
//                holder.text2.setText(goods2.getName());
//                if (goods2.getGoodImgs() != null && goods2.getGoodImgs().size() > 0) {
//                    activity.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            ImageOptions.showImage(JSONObject.parseObject(goods2.getGoodImgs().get(0)).getString("imgUrl"), holder.image2);
//                        }
//                    });
//                }else {
//                    holder.image2.setImageDrawable(null);
//                }
//                holder.lay2.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        mContext.startActivity(new Intent(mContext, GoodsDetailActivity.class).putExtra("id", goods2.getId()));
//                    }
//                });
//            }

    }

}
