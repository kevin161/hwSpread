package com.leisurely.spread.UI.activity.setting;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.Toast;

import com.leisurely.spread.R;
import com.leisurely.spread.UI.activity.home.HomeActivity;
import com.leisurely.spread.UI.activity.home.NewsActivity;
import com.leisurely.spread.UI.activity.home.PersonCenterActivity;
import com.leisurely.spread.UI.activity.home.RegisterOrLoginActivity;
import com.leisurely.spread.UI.activity.home.ShopActivity;
import com.leisurely.spread.config.SysConstants;
import com.leisurely.spread.util.SharedPreferencesUtil;
import com.leisurely.spread.util.ToastUtil;

@SuppressWarnings("deprecation")
public class MainAct extends TabActivity implements OnCheckedChangeListener {
    private RadioGroup ma_group;
    private TabHost tabhost;
    private Intent iHome;
    private Intent iHangqing;
    private Intent iBBMarket;
    private Intent iEHE;
    private Intent iDaxin;
    private Intent iChangwai;
    private Intent iMine;
    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.act_main);
//		F.activityList.add(this);
//		ibroad = new Intent(this, GetTickersService.class);
        ma_group = (RadioGroup) findViewById(R.id.ma_group);
        ma_btn1 = (RadioButton) findViewById(R.id.ma_btn1);
        ma_btn2 = (RadioButton) findViewById(R.id.ma_btn2);
        ma_btn3 = (RadioButton) findViewById(R.id.ma_btn3);
        ma_btn4 = (RadioButton) findViewById(R.id.ma_btn4);
        ma_btn5 = (RadioButton) findViewById(R.id.ma_btn5);
        ma_btn6 = (RadioButton) findViewById(R.id.ma_btn6);
        ma_btn7 = (RadioButton) findViewById(R.id.ma_btn7);
        ma_btn = ma_btn1;
        ma_group.setOnCheckedChangeListener(this);
        tabhost = getTabHost();

        iHome = new Intent(this, HomeActivity.class);
        tabhost.addTab(tabhost.newTabSpec("iHome")
                .setIndicator(getResources().getString(R.string.home), getResources().getDrawable(R.mipmap.tn_home_icon_select))
                .setContent(iHome));

        iHangqing = new Intent(this, NewsActivity.class);
        tabhost.addTab(tabhost.newTabSpec("iHangqing").setIndicator(getResources().getString(R.string.dianpu),
                getResources().getDrawable(R.mipmap.tn_gonggao_icon_select)).setContent(iHangqing));
//		iBBMarket = new Intent(this, PersonCenterActivity.class);
//		tabhost.addTab(tabhost.newTabSpec("iBBMarket").setIndicator(getResources().getString(R.string.bbmarket),
//				getResources().getDrawable(R.drawable.home_over)).setContent(iBBMarket));
//		iEHE = new Intent(this, EHEMarketAct.class);
//		tabhost.addTab(tabhost.newTabSpec("iEHE")
//				.setIndicator(getResources().getString(R.string.iEHE), getResources().getDrawable(R.drawable.home_over))
//				.setContent(iEHE));
//		iDaxin = new Intent(this, O2CAct.class);
//		tabhost.addTab(tabhost.newTabSpec("iDaxin").setIndicator("O2C",
//				getResources().getDrawable(R.drawable.home_over)).setContent(iDaxin));
//		iChangwai = new Intent(this, ChangWaiAct.class);
//		tabhost.addTab(tabhost.newTabSpec("iChangwai").setIndicator(getResources().getString(R.string.iChangwai),
//				getResources().getDrawable(R.drawable.home_over)).setContent(iChangwai));
        iMine = new Intent(this, PersonCenterActivity.class);
        tabhost.addTab(tabhost.newTabSpec("iMine")
                .setIndicator(getResources().getString(R.string.mine), getResources().getDrawable(R.mipmap.tn_mine_icon_select))
                .setContent(iMine));

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.ma_btn1:
                ma_btn = ma_btn1;
                this.tabhost.setCurrentTabByTag("iHome");
                break;
            case R.id.ma_btn2:
                ma_btn = ma_btn2;
                this.tabhost.setCurrentTabByTag("iHangqing");
                // BBMarketAct.market="";
                break;
            case R.id.ma_btn3:
                ma_btn = ma_btn3;
                this.tabhost.setCurrentTabByTag("iBBMarket");
                break;
            case R.id.ma_btn4:
                ma_btn = ma_btn4;
                this.tabhost.setCurrentTabByTag("iEHE");
                break;
            case R.id.ma_btn5:
                if (!SharedPreferencesUtil.readBoolean(SysConstants.ISLOGIN, false)) {
                    Intent intent = new Intent();
                    intent.setClass(MainAct.this, RegisterOrLoginActivity.class);
                    startActivity(intent);
                    ma_btn.setChecked(true);
                } else {
                    ma_btn = ma_btn5;
                    this.tabhost.setCurrentTabByTag("iMine");
                }
                break;
//		case R.id.ma_btn6:
//			if (F.TOKEN.length() <= 0) {
//				Intent intent = new Intent();
//				intent.setClass(MainAct.this, LoginAct.class);
//				startActivity(intent);
//				ma_btn.setChecked(true);
//			} else {
//				ma_btn = ma_btn6;
//				this.tabhost.setCurrentTabByTag("iDaxin");
//			}
//			break;
            case R.id.ma_btn7:
                ma_btn = ma_btn7;
                this.tabhost.setCurrentTabByTag("iChangwai");
                break;

        }
    }

    static RadioButton ma_btn;
    static RadioButton ma_btn1;
    static RadioButton ma_btn2;
    static RadioButton ma_btn3;
    static RadioButton ma_btn4;
    static RadioButton ma_btn5;
    static RadioButton ma_btn6;
    static RadioButton ma_btn7;

    public static void toBBMarket() {
        ma_btn3.setChecked(true);
    }

    public static void toShop() {
        ma_btn2.setChecked(true);
    }

    public static void toHome() {
        ma_btn1.setChecked(true);
    }

//	public static void toEHEMarket() {
//		ma_btn4.setChecked(true);
//	}

//    @Override
//    public boolean dispatchKeyEvent(KeyEvent event) {
//        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
//            if ((System.currentTimeMillis() - exitTime) > 2000) {
//                Toast.makeText(MainAct.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
//                exitTime = System.currentTimeMillis();
//            } else {
////				stopService(ibroad);
//                finish();
////                System.exit(0);
//            }
//            return true;
//
//        }
//        return super.dispatchKeyEvent(event);
//    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastUtil.showToast((getResources().getString(R.string.toast_system_exit)));
                exitTime = System.currentTimeMillis();
            } else {
                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(startMain);

            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

//	Intent ibroad;

    protected void onPause() {
//		stopService(ibroad);
        super.onPause();
    }

    ;

    protected void onDestroy() {
//		stopService(ibroad);
//        System.exit(0);
        super.onDestroy();
    }
}
