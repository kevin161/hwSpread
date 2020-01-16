package com.leisurely.spread.UI.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leisurely.spread.R;
import com.leisurely.spread.model.bean.Order;
import com.leisurely.spread.model.bean.Score;
import com.leisurely.spread.util.DateUtil;
import com.leisurely.spread.util.SharedPreferencesUtil;

import java.util.List;


/**
 * 右侧主界面ListView的适配器
 *
 * @author Administrator
 */
public class ScoreAdapter extends BaseAdapter {
    private List<Score> list;
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
            convertView = mInflater.inflate(R.layout.score_list,
                    parent, false);
            viewHolder = new ViewHolder();
            viewHolder.user_id = convertView.findViewById(R.id.user_id);
            viewHolder.system_no = convertView.findViewById(R.id.system_no);
            viewHolder.type = convertView.findViewById(R.id.type);
            viewHolder.score_type = convertView.findViewById(R.id.score_type);
            viewHolder.way = convertView.findViewById(R.id.way);
            viewHolder.money = convertView.findViewById(R.id.money);
            viewHolder.content = convertView.findViewById(R.id.content);
            viewHolder.text = convertView.findViewById(R.id.text);
            viewHolder.lay = convertView.findViewById(R.id.lay);
            viewHolder.time = convertView.findViewById(R.id.time);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        onBindViewHolder(viewHolder, position);
        return convertView;
    }


    public ScoreAdapter(Context context, List<Score> list) {
        mInflater = LayoutInflater.from(context);
        this.list = list;
        this.mContext = context;
    }

    public static class ViewHolder {
        TextView user_id;
        TextView system_no;
        TextView type;
        TextView score_type;
        TextView way;
        TextView money;
        TextView content;
        TextView text;
        TextView time;
        LinearLayout lay;
    }

    public void changeList(List<Score> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void addList(List<Score> bean) {
        list.addAll(bean);
        notifyDataSetChanged();
    }

    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Score score = list.get(position);
        if (position == 0) {
            holder.text.setVisibility(View.VISIBLE);
        } else {
            holder.text.setVisibility(View.GONE);
        }
        if (score.getScoreType() == null) {
            holder.lay.setVisibility(View.GONE);
        } else {
            holder.lay.setVisibility(View.VISIBLE);
            if ("false".equals(score.isType())) {
                holder.type.setText("增加");
                holder.type.setTextColor(mContext.getResources().getColor(R.color.color_FF3230));
            } else {
                holder.type.setText("减少");
                holder.type.setTextColor(mContext.getResources().getColor(R.color.color_3BA370));
            }
            if (score.getScoreType() == 1) {
                holder.score_type.setText("盈利积分");
            } else {
                holder.score_type.setText("非盈利积分");
            }
            if (score.getOperateWay() == 1) {
                holder.way.setText("交易");
            }

            holder.content.setText(score.getMemo());
            holder.money.setText(score.getAffectMoney());
            holder.system_no.setText(score.getSystemTraceNo());
            holder.user_id.setText(SharedPreferencesUtil.readString("uid", ""));
            holder.time.setText(DateUtil.getdata(score.getCreatedAt()));
        }
    }

}
