package com.leisurely.spread.UI.activity.home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.leisurely.spread.R;
import com.leisurely.spread.UI.activity.money.AddressActivity;
import com.leisurely.spread.UI.activity.money.AuthNameBindCardActivity;
import com.leisurely.spread.UI.activity.money.MyMoneyActivity;
import com.leisurely.spread.UI.activity.money.MyRecommenderActivity;
import com.leisurely.spread.UI.activity.setting.MainAct;
import com.leisurely.spread.config.SysConstants;
import com.leisurely.spread.framework.base.BaseActivity;
import com.leisurely.spread.framework.view.AvatarImageView;
import com.leisurely.spread.model.XclModel;
import com.leisurely.spread.util.FileUtil;
import com.leisurely.spread.util.ImageOptions;
import com.leisurely.spread.util.ImageSelectUtil;
import com.leisurely.spread.util.ImageUtil;
import com.leisurely.spread.util.SharedPreferencesUtil;
import com.leisurely.spread.util.StringUtil;
import com.leisurely.spread.util.TextUtil;
import com.leisurely.spread.util.ToastUtil;

import java.io.File;


public class PersonCenterActivity extends BaseActivity {

    private XclModel xclModel;


    private TextView loginout, txtUid, txtVip, txtVipLab;
    private RelativeLayout extend_re;
    private TextView name, txtMyMoney, txtGoAuth;
    private TextView phone;
    private ImageView imgArrow;
    private LinearLayout money_li, layoutHome, layoutSetting, layoutGoAuth;
    private LinearLayout score_li;
    private LinearLayout touzi_li;
    private LinearLayout layoutMoney;
    private LinearLayout tongzheng_li;
    private LinearLayout chabei_li;
    private LinearLayout dianpu_li;
    private RelativeLayout order_re;
    private RelativeLayout setting;


    private ImageSelectUtil mImageSelectUtil;//图片选择器
    private String imageUrl;

    public ImageSelectUtil getmImageSelectUtil() {
        return mImageSelectUtil;
    }

    /**
     * 初始化布局
     */
    protected void initView() {
        setContentView(R.layout.person_center_activity);
        extend_re = findViewById(R.id.extend_re);
        layoutGoAuth = findViewById(R.id.layoutGoAuth);
        txtVipLab = findViewById(R.id.txtVipLab);
        txtVip = findViewById(R.id.txtVip);
        loginout = findViewById(R.id.loginout);
        name = findViewById(R.id.txtUserName);
        imgArrow = findViewById(R.id.imgArrow);
        txtGoAuth = findViewById(R.id.txtGoAuth);
        txtMyMoney = findViewById(R.id.txtMyMoney);
        phone = findViewById(R.id.txtPhone);
        money_li = findViewById(R.id.money_li);
        score_li = findViewById(R.id.score_li);
        layoutMoney = findViewById(R.id.layoutMoney);
        order_re = findViewById(R.id.order_re);
        setting = findViewById(R.id.setting);
        layoutSetting = findViewById(R.id.layoutSetting);
        layoutHome = findViewById(R.id.layoutHome);
        touzi_li = findViewById(R.id.touzi_li);
        tongzheng_li = findViewById(R.id.tongzheng_li);
        chabei_li = findViewById(R.id.chabei_li);
        dianpu_li = findViewById(R.id.dianpu_li);
        txtUid = findViewById(R.id.txtUid);
    }

    @Override
    public void onResume() {
        super.onResume();
        xclModel.queryMemberInfo();

        if (SharedPreferencesUtil.isLogin()) {
            txtVip.setText("注册会员");
            txtVipLab.setText("会员");
        }

        if (SharedPreferencesUtil.isAuthBindCard()) {
            layoutGoAuth.setOnClickListener(null);
            txtGoAuth.setText("已实名");
            imgArrow.setImageDrawable(null);
        } else {
            txtGoAuth.setText("去实名");
            layoutGoAuth.setOnClickListener(this);
            imgArrow.setImageResource(R.mipmap.person_right_arrow);
        }
    }

    @Override
    protected void initListener() {
        touzi_li.setOnClickListener(this);
        tongzheng_li.setOnClickListener(this);
        layoutMoney.setOnClickListener(this);
        chabei_li.setOnClickListener(this);
        dianpu_li.setOnClickListener(this);
        extend_re.setOnClickListener(this);
        loginout.setOnClickListener(this);
        money_li.setOnClickListener(this);
        score_li.setOnClickListener(this);
        layoutHome.setOnClickListener(this);
        layoutSetting.setOnClickListener(this);
        order_re.setOnClickListener(this);
        setting.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        xclModel = new XclModel(this);
        mImageSelectUtil = new ImageSelectUtil(this);

        String uid = SharedPreferencesUtil.readString("uid", null);
        if (TextUtils.isEmpty(uid)) {
            ToastUtil.makeTextAndShow("无法获取用户ID，请尝试重新登录");
            return;
        }
        xclModel.getUserMoneyInfo(uid);

    }


    public void getUserSuccess(JSONObject json) {
        if (json.getBoolean("success")) {
            json = json.getJSONObject("data");
            String uid = json.getString("uid");
            String userPhone = json.getString("userPhone");
            String userName = json.getString("userName");

            txtUid.setText("ID:" + uid);
            name.setText(userName);
            phone.setText(userPhone);

//            SharedPreferencesUtil.saveString("userPhone", json.getString("userPhone"));
//            SharedPreferencesUtil.saveString("inviteId", json.getString("inviteId"));
//            SharedPreferencesUtil.saveString("rank", json.getString("rank"));
//            SharedPreferencesUtil.saveString("invitePhone", json.getString("invitePhone"));
//            SharedPreferencesUtil.saveString("realname", json.getString("name"));
//            SharedPreferencesUtil.saveString("rank", json.getString("rank"));
//            SharedPreferencesUtil.saveString("isTrade", json.getString("isTrade"));
//            SharedPreferencesUtil.saveString("totalTrade", json.getString("totalTrade"));
//            SharedPreferencesUtil.saveString("payPass", json.getString("payPass"));
//            SharedPreferencesUtil.saveBoolean(SysConstants.ISLOGIN, true);
//            double money = Double.parseDouble(SharedPreferencesUtil.readString("totalTrade", "0"));
//            if (money >= 5000) {
//            }
        } else {
            ToastUtil.showToast(json.getString("msg"));
        }

    }


    public void getUserMoneyInfoSuccess(JSONObject response) {
        if (response.getBoolean("success")) {
            String dd = response.getJSONObject("data").getString("available");
            txtMyMoney.setText(dd);
        } else {
            ToastUtil.makeTextAndShow(response.getString("msg"));
            startActivity(new Intent(PersonCenterActivity.this, RegisterOrLoginActivity.class));
            finish();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginout:
                SharedPreferencesUtil.saveBoolean(SysConstants.ISLOGIN, false);
//                startActivity(new Intent(this, RegisterOrLoginActivity.class));
                MainAct.toHome();
                break;
            case R.id.layoutMoney:
                startActivity(new Intent(this, MyMoneyActivity.class));
                break;
            case R.id.layoutHome:
                finish();
                break;
            case R.id.layoutGoAuth:
                startActivityForResult(new Intent(this, AuthNameBindCardActivity.class), 1111);
                break;
            case R.id.layoutSetting:
                startActivityForResult(new Intent(this, SettingActivity.class), 233);
                break;
            case R.id.extend_re:
                double money = Double.parseDouble(SharedPreferencesUtil.readString("totalTrade", "0"));
//                if (money >= 2000) {
//                    startActivity(new Intent(this, MyExtensionActivity.class));
                startActivity(new Intent(this, MyExtension2Activity.class));
//                } else {
//                    ToastUtil.showToast("暂无推广权限，请先购买商品");
//                }
                break;
            case R.id.money_li:
                startActivity(new Intent(this, MyMoneyActivity.class).putExtra("title", "个人中心"));
                break;
            case R.id.score_li:
                startActivity(new Intent(this, ScoreActivity.class).putExtra("title", "个人中心"));
                break;
            case R.id.order_re:
                startActivity(new Intent(this, OrderActivity.class).putExtra("title", "个人中心"));
                break;
            case R.id.address_re:
                startActivity(new Intent(this, AddressActivity.class).putExtra("title", "个人中心"));
                break;
            case R.id.setting:
                startActivity(new Intent(this, AddressActivity.class));
                break;
            case R.id.qrcode:
                xclModel.registerQrCode();
                break;
            case R.id.touzi_li:
                startActivity(new Intent(this, ZijinActivity.class)
                        .putExtra("type", 0));
                break;
            case R.id.tongzheng_li:
                startActivity(new Intent(this, ZijinActivity.class)
                        .putExtra("type", 1));
                break;
            case R.id.chabei_li:
                startActivityForResult(new Intent(this, ZijinActivity.class)
                        .putExtra("type", 2), 1110);
                break;
            case R.id.dianpu_li:
                MainAct.toShop();
                break;
            default:
                break;
        }
    }


    public void getQrcodeSuccess(JSONObject json) {
        if (json.getBoolean("success")) {
            showDialog(json.getString("data"));

        } else {
            ToastUtil.showToast(json.getString("msg"));
        }

    }

    private void showDialog(final String url) {
        final PopupWindow popupWindow = new PopupWindow();
        View view = LayoutInflater.from(this).inflate(R.layout.show_qrcode, null);
//        final Dialog backDialog = new Dialog(this, R.style.officeDialog);
//        backDialog.setCancelable(true);
//        backDialog.setContentView(view);
        final ImageView close = view.findViewById(R.id.close);
        final ImageView alert_qrcode = view.findViewById(R.id.alert_qrcode);
        final TextView copy = view.findViewById(R.id.copy);
        final TextView phone = view.findViewById(R.id.phone);
        phone.setText("推广手机号: " + SharedPreferencesUtil.readString("userPhone", ""));
        final TextView copy_phone = view.findViewById(R.id.copy_phone);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ImageUtil.createQRcodeImage(url, alert_qrcode, 400);
            }
        });
        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextUtil.onClickCopy(url, PersonCenterActivity.this);
            }
        });
        copy_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextUtil.onClickCopy(SharedPreferencesUtil.readString("userPhone", ""),
                        PersonCenterActivity.this);
            }
        });
        LinearLayout all = view.findViewById(R.id.all);
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        alert_qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        popupWindow.setContentView(view);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.showAtLocation(((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0), Gravity.CENTER, 0, 0);
    }

    public void uploadLogoSuccess() {
        final File file = new File(mImageSelectUtil.getDestFileName());
        if (!file.exists()) {

            return;
        } else {
            findImageUrl(file);
//                    head_portrait.setImageBitmap(FileUtil.decodeFile(mImageSelectUtil.getDestFileName()));
        }
    }

    private void findImageUrl(final File file) {
        xclModel.uploadHead(FileUtil.compressImage(file.getAbsolutePath()));

    }

    public void uploadFileSuccess(final String imageUrl) {
        if (StringUtil.isNotNull(imageUrl)) {
            this.imageUrl = imageUrl;
//            xclModel.editAvatar(imageUrl);
        } else {
            ToastUtil.showToast("上传失败,请重试");
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 110:
                case 1111:
                    if (SharedPreferencesUtil.isLogin()) {
                        if (SharedPreferencesUtil.isAuthBindCard()) {
                            //已实名
                        }
                    }
                    break;
                case 1110:
                    MainAct.toShop();
                    break;
                case 233:
                    startActivity(new Intent(this, RegisterOrLoginActivity.class));
                    finish();
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onPause() {

        Log.e("tag", "PersonCenterActivity---onPause----isAuthBindCard=" + SharedPreferencesUtil.isAuthBindCard());
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
