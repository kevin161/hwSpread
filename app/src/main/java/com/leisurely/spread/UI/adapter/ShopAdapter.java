package com.leisurely.spread.UI.adapter;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leisurely.spread.R;
import com.leisurely.spread.UI.activity.home.ShopActivity;
import com.leisurely.spread.UI.activity.home.ShopDetailActivity;
import com.leisurely.spread.UI.activity.home.ShouCangActivity;
import com.leisurely.spread.UI.view.ContentViewPager;
import com.leisurely.spread.framework.base.BaseActivity;
import com.leisurely.spread.model.bean.Shop;

import java.util.List;


/**
 * 右侧主界面ListView的适配器
 *
 * @author Administrator
 */
public class ShopAdapter extends BaseAdapter {
    private List<Shop> list;
    private LayoutInflater mInflater;
    private BaseActivity mContext;
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
            convertView = mInflater.inflate(R.layout.shop_list,
                    parent, false);
            viewHolder = new ViewHolder();
            viewHolder.alias_name = convertView.findViewById(R.id.alias_name);
            viewHolder.shop_id = convertView.findViewById(R.id.shop_id);
            viewHolder.qu = convertView.findViewById(R.id.qu);
            viewHolder.full_address = convertView.findViewById(R.id.full_address);
            viewHolder.phone = convertView.findViewById(R.id.phone);
            viewHolder.fullname = convertView.findViewById(R.id.fullname);
//            viewHolder.logo = convertView.findViewById(R.id.logo);
            viewHolder.shoucang = convertView.findViewById(R.id.shoucang);
            viewHolder.view_page = convertView.findViewById(R.id.view_page);
            viewHolder.all = convertView.findViewById(R.id.all);
            viewHolder.num = convertView.findViewById(R.id.num);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        onBindViewHolder(viewHolder, position);
        return convertView;
    }


    public ShopAdapter(BaseActivity context, List<Shop> list) {
        mInflater = LayoutInflater.from(context);
        this.list = list;
        this.mContext = context;
    }

    public static class ViewHolder {
        TextView alias_name;
        TextView shop_id;
        TextView qu;
        TextView full_address;
        TextView phone;
        TextView fullname;
        //        ImageView logo;
        ImageView shoucang;
        LinearLayout all;
        TextView num;
        ContentViewPager view_page;
    }

    public void changeList(List<Shop> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void addList(List<Shop> bean) {
        list.addAll(bean);
        notifyDataSetChanged();
    }

    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Shop shop = list.get(position);
        holder.alias_name.setText(shop.getAliasName());
        holder.shop_id.setText(shop.getShopId());
        holder.full_address.setText(shop.getFullAddress());
        holder.fullname.setText(shop.getFullName());

        ShopViewPagerAda viewPageAda = new ShopViewPagerAda(shop.getLbtImg(), mContext);
        holder.view_page.setCurrentItem(shop.getLbtImg().size() * 100);
        holder.view_page.setAdapter(viewPageAda);
        holder.num.setText(1 + "/" + shop.getLbtImg().size());
        holder.view_page.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (shop.getLbtImg() != null && shop.getLbtImg().size() > 0) {
                    holder.num.setText((position % shop.getLbtImg().size() + 1) + "/" + shop.getLbtImg().size());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

//        if (!shop.getLbtImg().isEmpty()) {
//            mContext.runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    //设置图片圆角角度
//                    RoundedCorners roundedCorners = new RoundedCorners(20);
////通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
//                    RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).override(300, 300);
//
//                    Glide.with(mContext).load(shop.getLbtImg().get(0)).apply(options).into(holder.logo);
////                ImageOptions.showImageRound(banner.getImage(), home_banner, ImageOptions.getRoundOptions(20));
//                }
//            });
//        }

        holder.phone.setText("联系电话 : " + shop.getPhone());
        holder.qu.setText(shop.getProvince() + " - " + shop.getCity() + " - " + shop.getArea());
        if (shop.getKeep() == null || !shop.getKeep()) {
            holder.shoucang.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.weishoucang));
        } else {
            holder.shoucang.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.yishoucang));
        }

        holder.all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, ShopDetailActivity.class)
                        .putExtra("id", shop.getShopId())
                        .putExtra("name", shop.getAliasName()));
            }
        });
        holder.shoucang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mContext instanceof ShopActivity) {
                    ((ShopActivity) mContext).showDialogShoucang(shop.getKeep() ? 2 : 1, shop.getAliasName(), shop.getShopId(), position);
                } else if (mContext instanceof ShouCangActivity) {
                    ((ShouCangActivity) mContext).showDialogShoucang(shop.getKeep() ? 2 : 1, shop.getAliasName(), shop.getShopId(), position);
                }

            }
        });
    }

}
