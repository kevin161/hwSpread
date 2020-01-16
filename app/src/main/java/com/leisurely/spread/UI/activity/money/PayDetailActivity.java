package com.leisurely.spread.UI.activity.money;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leisurely.spread.R;
import com.leisurely.spread.framework.base.BaseActivity;
import com.leisurely.spread.model.XclModel;
import com.leisurely.spread.model.bean.Payinformation;
import com.leisurely.spread.util.FileUtil;
import com.leisurely.spread.util.ImageOptions;
import com.leisurely.spread.util.TextUtil;

/**
 * Created by Administrator on 2019/1/2.
 */

public class PayDetailActivity extends BaseActivity {

    private XclModel xclModel;
    private TextView amount;
    private LinearLayout card_li;
    private LinearLayout img_li;
    private TextView save_pic;
    private TextView copy_url;
    private ImageView img;
    private TextView name;
    private ImageView copy_name;
    private TextView opening;
    private ImageView copy_opening;
    private TextView branch;
    private ImageView copy_branch;
    private TextView card;
    private ImageView copy_card;
    private TextView confirm_modift;

    private int paytype;
    private String purchaseid;

    public static Bitmap bitmap;

    private Payinformation payinformation;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_pay_deatil);
        setTitleName("付款");
        amount = findViewById(R.id.amount);
        card_li = findViewById(R.id.card_li);
        name = findViewById(R.id.name);
        copy_name = findViewById(R.id.copy_name);
        opening = findViewById(R.id.opening);
        copy_opening = findViewById(R.id.copy_opening);
        branch = findViewById(R.id.branch);
        copy_branch = findViewById(R.id.copy_branch);
        card = findViewById(R.id.branchcard);
        copy_card = findViewById(R.id.copy_card);
        confirm_modift = findViewById(R.id.confirm_modift);
        img = findViewById(R.id.img);
        img_li = findViewById(R.id.img_li);
        save_pic = findViewById(R.id.save_pic);
        copy_url = findViewById(R.id.copy_url);
    }

    @Override
    protected void initData() {
        super.initData();
        xclModel = new XclModel(this);
        paytype = getIntent().getIntExtra("paytype", 1);
        purchaseid = getIntent().getStringExtra("purchaseid");
        amount.setText(getIntent().getStringExtra("amount") + " CNY");
        xclModel.payinformation(String.valueOf(paytype), purchaseid);

    }

    @Override
    protected void initListener() {
        copy_name.setOnClickListener(this);
        copy_opening.setOnClickListener(this);
        copy_branch.setOnClickListener(this);
        copy_card.setOnClickListener(this);
        confirm_modift.setOnClickListener(this);
        save_pic.setOnClickListener(this);
        copy_url.setOnClickListener(this);
    }

    public void payinformationSuccess(final Payinformation payinformation) {
        this.payinformation = payinformation;
        if (paytype == 3) {
            card_li.setVisibility(View.VISIBLE);
            img_li.setVisibility(View.GONE);
            card.setText(payinformation.getCode());
            name.setText(payinformation.getUname());
            branch.setText(payinformation.getBranch());
            opening.setText(payinformation.getOpening());
        } else {
            card_li.setVisibility(View.GONE);
            img_li.setVisibility(View.VISIBLE);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                   ImageOptions.loadImageToBackground(payinformation.getImg(),img);
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.copy_name:
                TextUtil.onClickCopy(payinformation.getUname(), this);
                break;
            case R.id.copy_opening:
                TextUtil.onClickCopy(payinformation.getOpening(), this);
                break;
            case R.id.copy_branch:
                TextUtil.onClickCopy(payinformation.getBranch(), this);
                break;
            case R.id.copy_card:
                TextUtil.onClickCopy(payinformation.getCode(), this);
                break;
            case R.id.save_pic:
                if (bitmap != null) {
                    FileUtil.saveImageToGallery(this, bitmap);
                }
                break;
            case R.id.copy_url:
                TextUtil.onClickCopy(payinformation.getCode(), this);
                break;
            case R.id.confirm_modift:
                showAlert();
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
        alert_title.setText("确认付款");
        TextView alert_content = layout.findViewById(R.id.alert_content);
        alert_content.setText("确认已付款, 交易中, 请耐心等待卖家核实划转!");

        TextView text1 = layout.findViewById(R.id.awardpage_button1);
        text1.setText("重新付款");
        text1.setTextColor(this.getResources().getColor(R.color.light));
        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        TextView text2 = layout.findViewById(R.id.awardpage_button2);
        text2.setText("查看订单");
        text2.setTextColor(this.getResources().getColor(R.color.color_1490D8));
        text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK, new Intent());
                finish();
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
}
