package com.leisurely.spread.UI.activity.money;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.leisurely.spread.R;
import com.leisurely.spread.UI.activity.home.AddAlipayActivity;
import com.leisurely.spread.UI.activity.home.AddCardActivity;
import com.leisurely.spread.UI.activity.home.AddWechatActivity;
import com.leisurely.spread.UI.activity.home.PersonDetailActivity;
import com.leisurely.spread.UI.adapter.ITSAdapter;
import com.leisurely.spread.UI.view.PullToRefreshLayout;
import com.leisurely.spread.UI.view.PullableListView;
import com.leisurely.spread.framework.base.BaseActivity;
import com.leisurely.spread.model.XclModel;
import com.leisurely.spread.model.bean.ITS;
import com.leisurely.spread.util.StringUtil;
import com.leisurely.spread.util.TextUtil;
import com.leisurely.spread.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/12/24.
 */

public class ITSActivity extends BaseActivity {
    private XclModel xclModel;

    private final int pageSize = 10;
    private int pageNum = 1;
    private List<ITS> list;
    private PullToRefreshLayout pull_refresh_lay;
    private PullableListView listView;
    private TextView textView_right_title_bar;

    private ITSAdapter adapter;

    private int type;//0 buy 1 sell

    private int paytype;

    private Dialog dialog;

    private int index;

    private TextView alipay;
    private TextView wechat;
    private TextView card;

    private TextView alipay_btn;
    private TextView card_btn;
    private TextView wechat_btn;

    private int alipayId;
    private int wechatId;
    private int cardId;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_its);
        setTitleName("ITS交易");
        pull_refresh_lay = findViewById(R.id.pull_refresh_lay);
        listView = findViewById(R.id.lv_listview);
        textView_right_title_bar = findViewById(R.id.textView_right_title_bar);
        list = new ArrayList<>();
        adapter = new ITSAdapter(this, list);
        listView.setAdapter(adapter);

        textView_right_title_bar.setText("记录");
        textView_right_title_bar.setVisibility(View.VISIBLE);

        ((TextView) pull_refresh_lay.findViewById(R.id.state_tv)).setTextColor(getResources().getColor(R.color.light));
        ((TextView) pull_refresh_lay.findViewById(R.id.loadstate_tv)).setTextColor(getResources().getColor(R.color.light));
    }

    @Override
    protected void initData() {
        super.initData();
        xclModel = new XclModel(this);
        getData();
    }

    @Override
    protected void initListener() {
        textView_right_title_bar.setOnClickListener(this);
        pull_refresh_lay.setOnRefreshListener(new MyListener());

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    return;
                }
                index = position;
                if (type == 0) {
                    paytype = 2;
                    showBuyAlert(list.get(position));
                } else if (type == 1) {
                    paytype = 1;
                    xclModel.paylist();

                }
            }
        });
    }

    public void paylistSuccess(JSONArray jsonArray, double its) {
        showSellAlert(list.get(index), jsonArray, its);
    }

    private void showBuyAlert(final ITS its) {
        View layout = LayoutInflater.from(this).inflate(R.layout.buy_alert, null);
        dialog = new Dialog(this, R.style.officeDialog);
        dialog.setCancelable(true);
        dialog.setContentView(layout);

        ImageView close = layout.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        TextView price = layout.findViewById(R.id.price);
        price.setText(TextUtil.get2Double(its.getPrice()) + " CNY");
        TextView num = layout.findViewById(R.id.num);
        num.setText(TextUtil.get2Double(its.getMinimum()) +
                " 一 " + TextUtil.get2Double(its.getMaximum()) + " CNY");

        LinearLayout wechat = layout.findViewById(R.id.wechat);
        LinearLayout alipay = layout.findViewById(R.id.alipay);
        LinearLayout card = layout.findViewById(R.id.card);

        for (String s : its.getPaymentdata().split(",")) {
            if (s.equals("1")) {
                alipay.setVisibility(View.VISIBLE);
            } else if (s.equals("2")) {
                wechat.setVisibility(View.VISIBLE);
            } else if (s.equals("3")) {
                card.setVisibility(View.VISIBLE);
            }
        }
        final ImageView wechat_sel = layout.findViewById(R.id.wechat_sel);
        final ImageView alipay_sel = layout.findViewById(R.id.alipay_sel);
        final ImageView card_sel = layout.findViewById(R.id.card_sel);

        wechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paytype = 2;
                wechat_sel.setBackground(getResources().getDrawable(R.mipmap.quan_sle));
                alipay_sel.setBackground(getResources().getDrawable(R.mipmap.quan));
                card_sel.setBackground(getResources().getDrawable(R.mipmap.quan));
            }
        });

        alipay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paytype = 1;
                wechat_sel.setBackground(getResources().getDrawable(R.mipmap.quan));
                alipay_sel.setBackground(getResources().getDrawable(R.mipmap.quan_sle));
                card_sel.setBackground(getResources().getDrawable(R.mipmap.quan));
            }
        });

        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paytype = 3;
                wechat_sel.setBackground(getResources().getDrawable(R.mipmap.quan));
                alipay_sel.setBackground(getResources().getDrawable(R.mipmap.quan));
                card_sel.setBackground(getResources().getDrawable(R.mipmap.quan_sle));
            }
        });

        final TextView cny_num = layout.findViewById(R.id.cny_num);
        final EditText its_num = layout.findViewById(R.id.its_num);
        TextView buy_all = layout.findViewById(R.id.buy_all);
        double m = its.getMaximum() / its.getPrice() - 0.00005;
        if (m > Double.parseDouble(its.getStock())) {
            m = Double.parseDouble(its.getStock());
        }
        final double maxCount = Double.parseDouble(TextUtil.get4Double(m));
        its_num.setHint(its_num.getHint().toString() + TextUtil.get4Double(maxCount));
        buy_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cny_num.setText(TextUtil.get2Double(its.getMaximum()));
                its_num.setText(TextUtil.get4Double(its.getMaximum() / its.getPrice() - 0.00005));
                its_num.setSelection(its_num.getText().toString().length());
            }
        });
        TextView cancel = layout.findViewById(R.id.cancel);
        TextView commit = layout.findViewById(R.id.commit);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtil.isNotNull(its_num.getText().toString())) {
                    double money = Double.parseDouble(cny_num.getText().toString());
                    if (money < its.getMinimum()) {
                        ToastUtil.showToast("购买金额小于最小金额");
                    } else if (money > its.getMaximum()) {
                        ToastUtil.showToast("购买金额大于最大金额");
                    } else {
                        xclModel.createOrder(its.getId(), paytype, cny_num.getText().toString(), its_num.getText().toString());
                    }

                } else {
                    ToastUtil.showToast("请输入购买数量");
                }
            }
        });

        its_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String cashString = its_num.getText().toString();
                if (StringUtil.isNotNull(cashString)) {
                    try {
                        if (cashString.length() > 1 && cashString.indexOf("0") == 0 && (cashString.indexOf(".") > 1
                                || cashString.indexOf(".") == -1)) {
                            its_num.setText(cashString.substring(1));
                            cashString = its_num.getText().toString();
                        }

                        if (cashString.indexOf(".") > 0) {
                            if (cashString.indexOf(".") < (cashString.length() - 5)) {
                                its_num.setText(cashString.substring(0, cashString.length() - 1));
                            }
                        }
                        double cash = Double.valueOf(cashString);
                        if (cash > maxCount) {
                            its_num.setText((TextUtil.get4Double(maxCount)));
                        }
                    } catch (Exception e) {
                        if (cashString.length() > 1) {
                            its_num.setText(cashString.substring(0, cashString.length() - 1));
                        } else {
//                        moneyout_et.setText("");
                            its_num.setText("0.");
                        }
                    }
                    its_num.setSelection(its_num.getText().toString().length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    Double d = Double.parseDouble(its_num.getText().toString());
                    cny_num.setText(TextUtil.get2Double(d * its.getPrice() - 0.00005));
                } catch (Exception e) {

                }
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

    private void showSellAlert(final ITS its, final JSONArray jsonArray,  double money) {
        View layout = LayoutInflater.from(this).inflate(R.layout.sell_alert, null);
        dialog = new Dialog(this, R.style.officeDialog);
        dialog.setCancelable(true);
        dialog.setContentView(layout);

        ImageView close = layout.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        final EditText pay_pwd = layout.findViewById(R.id.pay_pwd);
        TextView price = layout.findViewById(R.id.price);
        price.setText(TextUtil.get2Double(its.getPrice()) + " CNY");
        TextView num = layout.findViewById(R.id.num);
        num.setText(TextUtil.get2Double(its.getMinimum()) +
                " 一 " + TextUtil.get2Double(its.getMaximum()) + " CNY");

        RelativeLayout wechat_re = layout.findViewById(R.id.wechat_re);
        RelativeLayout alipay_re = layout.findViewById(R.id.alipay_re);
        RelativeLayout card_re = layout.findViewById(R.id.card_re);
        final ImageView wechat_sel = layout.findViewById(R.id.wechat_sel);
        final ImageView alipay_sel = layout.findViewById(R.id.alipay_sel);
        final ImageView card_sel = layout.findViewById(R.id.card_sel);

        alipay_btn = layout.findViewById(R.id.alipay_btn);
        card_btn = layout.findViewById(R.id.card_btn);
        wechat_btn = layout.findViewById(R.id.wechat_btn);

        alipay = layout.findViewById(R.id.alipay_text);
        card = layout.findViewById(R.id.card_text);
        wechat = layout.findViewById(R.id.wechat_text);

        for (Object o : jsonArray) {
            JSONObject json = (JSONObject) o;
            for (String s : its.getPaymentdata().split(",")) {
                if (s.equals("1")) {
                    alipay_re.setVisibility(View.VISIBLE);
                    if (s.equals(json.getString("type"))) {
                        if (!json.getBooleanValue("status")) {
                            alipay_btn.setText("修改");
                            alipay.setText(json.getString("msg"));
                            if (json.containsKey("id")) {
                                alipayId = json.getIntValue("id");
                            }
                        }
                        alipay_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivityForResult(new Intent(ITSActivity.this, AddAlipayActivity.class)
                                        .putExtra("id", alipayId), 1111);
                            }
                        });
                    }
                } else if (s.equals("2")) {
                    wechat_re.setVisibility(View.VISIBLE);
                    if (s.equals(json.getString("type"))) {
                        if (!json.getBooleanValue("status")) {
                            wechat_btn.setText("修改");
                            wechat.setText(json.getString("msg"));
                            if (json.containsKey("id")) {
                                wechatId = json.getIntValue("id");
                            }
                        }

                        wechat_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivityForResult(new Intent(ITSActivity.this, AddWechatActivity.class)
                                        .putExtra("id", wechatId), 2222);
                            }
                        });
                    }
                } else if (s.equals("3")) {
                    card_re.setVisibility(View.VISIBLE);
                    if (s.equals(json.getString("type"))) {
                        if (!json.getBooleanValue("status")) {
                            card_btn.setText("修改");
                            card.setText(json.getString("msg"));
                            if (json.containsKey("id")) {
                                cardId = json.getIntValue("id");
                            }
                        }
                        card_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivityForResult(new Intent(ITSActivity.this, AddCardActivity.class)
                                        .putExtra("id", cardId), 3333);
                            }
                        });
                    }
                }
            }
        }

        wechat_re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paytype = 2;
                wechat_sel.setBackground(getResources().getDrawable(R.mipmap.quan_sle));
                alipay_sel.setBackground(getResources().getDrawable(R.mipmap.quan));
                card_sel.setBackground(getResources().getDrawable(R.mipmap.quan));
            }
        });

        alipay_re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paytype = 1;
                wechat_sel.setBackground(getResources().getDrawable(R.mipmap.quan));
                alipay_sel.setBackground(getResources().getDrawable(R.mipmap.quan_sle));
                card_sel.setBackground(getResources().getDrawable(R.mipmap.quan));
            }
        });

        card_re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paytype = 3;
                wechat_sel.setBackground(getResources().getDrawable(R.mipmap.quan));
                alipay_sel.setBackground(getResources().getDrawable(R.mipmap.quan));
                card_sel.setBackground(getResources().getDrawable(R.mipmap.quan_sle));
            }
        });

        final TextView cny_num = layout.findViewById(R.id.cny_num);
        final EditText its_num = layout.findViewById(R.id.its_num);
        TextView buy_all = layout.findViewById(R.id.buy_all);
        double m = its.getMaximum() / its.getPrice() - 0.00005;
        if (m > Double.parseDouble(its.getStock())) {
            m = Double.parseDouble(its.getStock());
        }
        if (m > money) {
            m = money;
        }
        final double maxCount = Double.parseDouble(TextUtil.get4Double(m));
        its_num.setHint(its_num.getHint().toString() + TextUtil.get4Double(maxCount));
        buy_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cny_num.setText(TextUtil.get2Double(its.getMaximum()));
                its_num.setText(TextUtil.get4Double(its.getMaximum() / its.getPrice() - 0.00005));
                its_num.setSelection(its_num.getText().toString().length());
            }
        });
        TextView cancel = layout.findViewById(R.id.cancel);
        TextView commit = layout.findViewById(R.id.commit);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtil.isNotNull(its_num.getText().toString())) {
                    double money = Double.parseDouble(cny_num.getText().toString());
                    if (money < its.getMinimum()) {
                        ToastUtil.showToast("出售金额小于最小金额");
                    } else if (money > its.getMaximum()) {
                        ToastUtil.showToast("出售金额大于最大金额");
                    } else {
                        if (StringUtil.isNotNull(pay_pwd.getText().toString())) {
                            xclModel.sellOrder(its.getId(), paytype == 1 ? alipayId : paytype == 2 ? wechatId : cardId,
                                    its_num.getText().toString(), pay_pwd.getText().toString());
                        } else {
                            ToastUtil.showToast("请输入支付密码");
                        }
                    }
                } else {
                    ToastUtil.showToast("请输入出售数量");
                }
            }
        });

        its_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String cashString = its_num.getText().toString();
                if (StringUtil.isNotNull(cashString)) {
                    try {
                        if (cashString.length() > 1 && cashString.indexOf("0") == 0 && (cashString.indexOf(".") > 1
                                || cashString.indexOf(".") == -1)) {
                            its_num.setText(cashString.substring(1));
                            cashString = its_num.getText().toString();
                        }

                        if (cashString.indexOf(".") > 0) {
                            if (cashString.indexOf(".") < (cashString.length() - 5)) {
                                its_num.setText(cashString.substring(0, cashString.length() - 1));
                            }
                        }
                        double cash = Double.valueOf(cashString);
                        if (cash > maxCount) {
                            its_num.setText((TextUtil.get4Double(maxCount)));
                        }
//                        if (its.getMaximum() < cash * its.getPrice()) {
//                            its_num.setText(TextUtil.get4Double(its.getMaximum() / its.getPrice() - 0.00005));
//                        }
                    } catch (Exception e) {
                        if (cashString.length() > 1) {
                            its_num.setText(cashString.substring(0, cashString.length() - 1));
                        } else {
//                        moneyout_et.setText("");
                            its_num.setText("0.");
                        }
                    }
                    its_num.setSelection(its_num.getText().toString().length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    Double d = Double.parseDouble(its_num.getText().toString());
                    cny_num.setText(TextUtil.get2Double(d * its.getPrice() - 0.00005));
                } catch (Exception e) {

                }
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

    public void createOrderSuccess(String id) {
        dialog.dismiss();
        startActivity(new Intent(this, BuyDetailActivity.class).putExtra("id", id));
    }

    public void sellOrderSuccess(String id) {
        dialog.dismiss();
        startActivity(new Intent(this, SellDetailActivity.class).putExtra("id", id));
    }

    class MyListener implements PullToRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
            // 下拉刷新操作
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    pageNum = 1;
                    getData();
//                    xclModel.rechargeList(pageNum, pageSize);
                    // 千万别忘了告诉控件刷新完毕了哦！
                    pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 1000);
        }

        @Override
        public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
            // 加载操作
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    pageNum++;
                    getData();
//                    xclModel.rechargeList(pageNum, pageSize);
                    // 千万别忘了告诉控件加载完毕了哦！
                    pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 1000);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.buy:
//                if (type != 0) {
//                    type = 0;
//                    pageNum = 1;
//                    changeType();
//                }
//                break;
//            case R.id.sell:
//                if (type != 1) {
//                    type = 1;
//                    pageNum = 1;
//                    changeType();
//                }
//                break;
            case R.id.textView_right_title_bar:
                startActivity(new Intent(this, ITSLogActivity.class));
                break;
            default:
                break;
        }
    }

    public void changeType(int type, TextView buy, TextView sell) {
        if (this.type == type) {
            return;
        }
        this.type = type;
        pageNum = 1;
        Drawable leftClick = getResources().getDrawable(R.drawable.dcb981_6dp_left);
        Drawable leftUnClick = getResources().getDrawable(R.drawable.dcb981_6dp_side_left);
        Drawable rightClick = getResources().getDrawable(R.drawable.dcb981_6dp_right);
        Drawable rightUnClick = getResources().getDrawable(R.drawable.dcb981_6dp_side_right);
        if (type == 0) {
            buy.setBackground(leftClick);
            buy.setTextColor(getResources().getColor(R.color.white));
            sell.setBackground(rightUnClick);
            sell.setTextColor(getResources().getColor(R.color.color_DCB981));
        } else if (type == 1) {
            buy.setBackground(leftUnClick);
            buy.setTextColor(getResources().getColor(R.color.color_DCB981));
            sell.setBackground(rightClick);
            sell.setTextColor(getResources().getColor(R.color.white));
        }
        getData();
    }


    public void getDataSuccess(List<ITS> bean) {
        if (pageNum == 1) {
            list = bean;
            adapter.changeList(bean);
        } else {
            adapter.addList(bean);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1111) {
                alipay.setText(data.getStringExtra("msg"));
                alipayId = data.getIntExtra("id", 0);
                alipay_btn.setText("修改");
            } else if (requestCode == 2222) {
                wechat.setText(data.getStringExtra("msg"));
                wechatId = data.getIntExtra("id", 0);
                wechat_btn.setText("修改");
            } else if (requestCode == 3333) {
                card.setText(data.getStringExtra("msg"));
                cardId = data.getIntExtra("id", 0);
                card_btn.setText("修改");
            }
        }
    }

    public void toPerson() {
        startActivity(new Intent(this, PersonDetailActivity.class));
    }

    private void getData() {
        if (type == 0) {
            xclModel.getItsSell(pageNum, pageSize);
        } else if (type == 1) {
            xclModel.getItsBuy(pageNum, pageSize);
        }
    }
}
