package com.leisurely.spread.UI.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leisurely.spread.R;
import com.leisurely.spread.framework.base.BaseActivity;
import com.leisurely.spread.model.bean.Zijin;
import com.leisurely.spread.util.StringUtil;

import java.util.List;


/**
 * 右侧主界面ListView的适配器
 *
 * @author Administrator
 */
public class ZijinAdapter extends BaseAdapter {
    private List<Zijin> list;
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
            convertView = mInflater.inflate(R.layout.zijin_list,
                    parent, false);
            viewHolder = new ViewHolder();
            viewHolder.edu = convertView.findViewById(R.id.edu);
            viewHolder.shouzhi = convertView.findViewById(R.id.shouzhi);
            viewHolder.time = convertView.findViewById(R.id.time);
            viewHolder.mark = convertView.findViewById(R.id.mark);
            viewHolder.title = convertView.findViewById(R.id.title);
            viewHolder.lay = convertView.findViewById(R.id.lay);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        onBindViewHolder(viewHolder, position);
        return convertView;
    }


    public ZijinAdapter(BaseActivity context, List<Zijin> list) {
        mInflater = LayoutInflater.from(context);
        this.list = list;
        this.mContext = context;
    }

    public static class ViewHolder {
        TextView time;
        TextView edu;
        TextView shouzhi;
        TextView mark;
        LinearLayout title;
        LinearLayout lay;

    }

    public void changeList(List<Zijin> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void addList(List<Zijin> bean) {
        list.addAll(bean);
        notifyDataSetChanged();
    }

    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Zijin zijin = list.get(position);
        if(position ==0){
            holder.title.setVisibility(View.VISIBLE);
        }else {
            holder.title.setVisibility(View.GONE);
        }
        if(StringUtil.isNullOrEmpty(zijin.getAsset())){
            holder.lay.setVisibility(View.GONE);
        }else {
            holder.lay.setVisibility(View.VISIBLE);
        }
        if(position%2==0){
            holder.lay.setBackground(mContext.getResources().getDrawable(R.color.white));
        }else {
            holder.lay.setBackground(mContext.getResources().getDrawable(R.color.color_e2e2e2));
        }
        holder.edu.setText(zijin.getAffectMoney());
        holder.mark.setText(zijin.getOperateWayName());
        holder.shouzhi.setText(zijin.getType()==0?"收入":"支出");
        holder.time.setText(zijin.getCreateTime());
    }

}
