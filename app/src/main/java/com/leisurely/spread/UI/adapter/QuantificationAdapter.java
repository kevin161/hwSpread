package com.leisurely.spread.UI.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leisurely.spread.R;
import com.leisurely.spread.UI.activity.home.MainActivity;
import com.leisurely.spread.UI.activity.pager.QuantificationPager;
import com.leisurely.spread.model.bean.Quantification;
import com.leisurely.spread.util.WheelViewUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 右侧主界面ListView的适配器
 *
 * @author Administrator
 */
public class QuantificationAdapter extends BaseAdapter {
    private List<Quantification> list;
    private LayoutInflater mInflater;
    private Context mContext;
    private ViewHolder viewHolder;

    private List<String> key;
    private List<String> value;
    private Map<String, Object> map;

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
            convertView = mInflater.inflate(R.layout.activity_quantification_list,
                    parent, false);
            viewHolder = new ViewHolder();
            viewHolder.day = convertView.findViewById(R.id.day);
            viewHolder.day_money = convertView.findViewById(R.id.day_money);
            viewHolder.quantification_day = convertView.findViewById(R.id.quantification_day);
            viewHolder.count_re = convertView.findViewById(R.id.count_re);
            viewHolder.count_text = convertView.findViewById(R.id.count_text);
            viewHolder.commit = convertView.findViewById(R.id.commit);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        onBindViewHolder(viewHolder, position);
        return convertView;
    }


    public QuantificationAdapter(Context context, List<Quantification> list) {
        mInflater = LayoutInflater.from(context);
        this.list = list;
        this.mContext = context;
    }

    public static class ViewHolder {
        TextView day;
        TextView day_money;
        TextView quantification_day;
        RelativeLayout count_re;
        TextView count_text;
        TextView commit;
    }

    public void changeList(List<Quantification> list, Map<String, Object> map) {
        this.list = list;
        this.map = map;
        key = new ArrayList<>();
        value = new ArrayList<>();
        for (String k : map.keySet()) {
            String v = String.valueOf(map.get(k));
            int i = 0;
            for (String s : value) {
                if (Integer.parseInt(s) < Integer.parseInt(v)) {
                    break;
                }
                i++;
            }
            key.add(i, k);
            value.add(i, v);
        }
        notifyDataSetChanged();
    }

    public void addList(List<Quantification> bean) {
        list.addAll(bean);
        notifyDataSetChanged();
//        notifyItemInserted(position);
    }

    public void commit(final String day, final String count) {
        View layout = LayoutInflater.from(mContext).inflate(R.layout.view_alert, null);
        final Dialog dialog = new Dialog(mContext, R.style.officeDialog);
        dialog.setCancelable(true);
        dialog.setContentView(layout);

        TextView alert_title = layout.findViewById(R.id.alert_title);
        alert_title.setText("确定启动量化");
        TextView alert_content = layout.findViewById(R.id.alert_content);
        alert_content.setText("量化资产: " + day + "天" + "\n" + "量化数量: " + count);

        TextView text1 = layout.findViewById(R.id.awardpage_button1);
        text1.setText(mContext.getResources().getString(R.string.cancel));
        text1.setTextColor(mContext.getResources().getColor(R.color.color_32353C));
        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        TextView text2 = layout.findViewById(R.id.awardpage_button2);
        text2.setText("确定");
        text2.setTextColor(mContext.getResources().getColor(R.color.color_fbdb9d));
        text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuantificationPager.xclModel.addInvestment(count, day);
                dialog.dismiss();
            }
        });
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface arg0) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Quantification quantification = list.get(position);
        holder.day.setText(quantification.getDay() + "天");
        holder.quantification_day.setText(quantification.getDay() + "天");
        holder.day_money.setText(quantification.getRate() + "%");
        holder.count_text.setText(key.get(0));

        holder.count_re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = 0;
                for (String s : key) {
                    if (s.equals(holder.count_text.getText().toString())) {
                        break;
                    }
                    index++;
                }
                WheelViewUtils.alertBottomWheelOption(mContext, key, index, new WheelViewUtils.OnWheelViewClick() {
                    @Override
                    public void onClick(View view, final int index) {
                        ((MainActivity) mContext).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                holder.count_text.setText(key.get(index));
                            }
                        });
                    }
                });
            }
        });
        holder.commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commit(quantification.getDay(), String.valueOf(map.get(holder.count_text.getText().toString())));
            }
        });
    }

}
