package com.leisurely.spread.UI.activity.order;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leisurely.spread.R;
import com.leisurely.spread.framework.base.BaseActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 支付成功页
 *
 * @version V3.0
 * @FileName: com.leisurely.spread.UI.activity.order.PurchaseSuccessActivity.java
 * @author: GYZ
 * asia161@qq.com
 * @date: 2020-01-13 13:23
 */
public class PurchaseSuccessActivity extends BaseActivity {

    private LinearLayout back;
    private TextView txtOrder, txtHome;
    private Timer timer;
    private MyTimerTask timerTask;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_purchase_success);
        back = findViewById(R.id.back);
        txtOrder = findViewById(R.id.txtOrder);
        txtHome = findViewById(R.id.txtHome);
    }

    @Override
    public void onResume() {
        super.onResume();
        startTimer();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (timer != null) {
            timer.cancel();
        }
    }

    @Override
    protected void initListener() {
        back.setOnClickListener(this);
        txtOrder.setOnClickListener(this);
        txtHome.setOnClickListener(this);
    }

    private void startTimer() {
        timer = new Timer();
        timerTask = new MyTimerTask();
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
    }

    int timerUnit = 1000, timerCounting = 3000;

    private class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            Log.e("tag", "timerCounting===" + timerCounting);
            timerCounting -= timerUnit;
            if (timerCounting == 0) {
                cancel();
                stopCountDown();
            }
        }
    }


    public void stopCountDown() {
        if (timer != null) {
            timer.cancel();
        }
        setResult(Activity.RESULT_OK, new Intent().putExtra(PlaceOrderActivity.GOODS_BUY_AFTER, 0));
        finish();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.txtOrder:
                setResult(Activity.RESULT_OK, new Intent().putExtra(PlaceOrderActivity.GOODS_BUY_AFTER, 1));
                finish();
                break;
            case R.id.txtHome:
                setResult(Activity.RESULT_OK, new Intent().putExtra(PlaceOrderActivity.GOODS_BUY_AFTER, 0));
                finish();
                break;
        }
    }
}
