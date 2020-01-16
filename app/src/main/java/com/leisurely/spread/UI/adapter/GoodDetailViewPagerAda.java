package com.leisurely.spread.UI.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.leisurely.spread.UI.activity.home.GoodsDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class GoodDetailViewPagerAda extends PagerAdapter {

    private List<String> Urllist = new ArrayList<String>();
    private GoodsDetailActivity mcontext;

    public GoodDetailViewPagerAda(List<String> mlist, GoodsDetailActivity context) {
        // TODO Auto-generated constructor stub
        this.Urllist = mlist;
        this.mcontext = context;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return Urllist.size() > 1 ? Integer.MAX_VALUE / 1000 : Urllist.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        // TODO Auto-generated method stub
        return arg0 == arg1;
    }

    public void setUrllist(List<String> urllist) {
        Urllist = urllist;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ItemGooddetailViewpager item = new ItemGooddetailViewpager(mcontext);
        if (Urllist.size() > 0) {
            item.set(Urllist.get(position % Urllist.size()));
        }
        container.addView(item);
        return item;
    }

    @Override
    public Object instantiateItem(View container, int position) {


        if (container == null) {
            ItemGooddetailViewpager mitem = new ItemGooddetailViewpager(mcontext);
            container = mitem;
        }
        ItemGooddetailViewpager item = (ItemGooddetailViewpager) container;
        item.set(Urllist.get(position));
//		container.addView(item);

        return container;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // TODO Auto-generated method stub
//		container.removeView(container.getChildAt(position));
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        // TODO Auto-generated method stub
//		super.destroyItem(container, position, object);
    }
}
