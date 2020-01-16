package com.leisurely.spread.UI.activity.money;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.leisurely.spread.R;
import com.leisurely.spread.framework.base.BaseActivity;
import com.leisurely.spread.model.XclModel;
import com.leisurely.spread.model.bean.SellDetail;
import com.leisurely.spread.util.DateUtil;
import com.leisurely.spread.util.TextUtil;
import com.leisurely.spread.util.ToastUtil;

/**
 * Created by Administrator on 2018/12/29.
 */

public class SellDetailActivity extends BaseActivity {

    private XclModel xclModel;

    private TextView name;
    private TextView amount;
    private ImageView copy_amount;
    private TextView price;
    private TextView num;
    private TextView orderno;
    private ImageView copy_orderno;
    private TextView time;
    private TextView commit;

    private SellDetail order;


    @Override
    protected void initView() {
        setContentView(R.layout.activity_sell_detail);
        setTitleName("划转");
        name = findViewById(R.id.name);
        amount = findViewById(R.id.amount);
        copy_amount = findViewById(R.id.copy_amount);
        price = findViewById(R.id.price);
        num = findViewById(R.id.num);
        orderno = findViewById(R.id.orderno);
        copy_orderno = findViewById(R.id.copy_orderno);
        time = findViewById(R.id.time);
        commit = findViewById(R.id.commit);

    }

    @Override
    protected void initData() {
        super.initData();
        xclModel = new XclModel(this);
        xclModel.sellDetails(getIntent().getStringExtra("id"));
    }

    @Override
    protected void initListener() {
        commit.setOnClickListener(this);
        copy_amount.setOnClickListener(this);
        copy_orderno.setOnClickListener(this);
    }

    public void getSellDetailSuccess(SellDetail order) {
        this.order = order;
        name.setText(order.getBuyername());
        price.setText(order.getBuyerprice() + " CNY");
        amount.setText(order.getAmount() + " CNY");
        num.setText(order.getNum() + " ITS");
        time.setText(DateUtil.getdata(order.getCreatetime()));
        orderno.setText( order.getOrderno());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.commit:
                showAlert();
                break;
            case R.id.copy_amount:
                TextUtil.onClickCopy(order.getAmount(), this);
                break;
            case R.id.copy_orderno:
                TextUtil.onClickCopy(order.getOrderno(), this);
                break;
            default:
                break;
        }
    }

    public void showAlert() {
        View layout = LayoutInflater.from(this).inflate(R.layout.view_alert, null);
        final Dialog dialog = new Dialog(this, R.style.officeDialog);
        dialog.setCancelable(true);
        dialog.setContentView(layout);

        TextView alert_title = layout.findViewById(R.id.alert_title);
        alert_title.setText("立即划转");
        TextView alert_content = layout.findViewById(R.id.alert_content);
        alert_content.setText("确认已收款, ITS将直接划转到买家账户!");

        TextView text1 = layout.findViewById(R.id.awardpage_button1);
        text1.setText("取消划转");
        text1.setTextColor(this.getResources().getColor(R.color.light));
        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        TextView text2 = layout.findViewById(R.id.awardpage_button2);
        text2.setText("立即划转");
        text2.setTextColor(this.getResources().getColor(R.color.color_1490D8));
        text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xclModel.sellTransfer(getIntent().getStringExtra("id"));
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

    public void sellTransferSuccess(){
        ToastUtil.showToast("划转成功");
        Intent it = new Intent();
        setResult(RESULT_OK, it);
        this.finish();
    }
}
