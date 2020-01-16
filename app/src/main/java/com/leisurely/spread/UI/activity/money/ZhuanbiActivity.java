package com.leisurely.spread.UI.activity.money;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.leisurely.spread.R;
import com.leisurely.spread.UI.activity.setting.ScanActivity;
import com.leisurely.spread.config.SysConstants;
import com.leisurely.spread.framework.base.BaseActivity;
import com.leisurely.spread.model.XclModel;
import com.leisurely.spread.model.enums.YZMEnum;
import com.leisurely.spread.util.ImageSelectUtil;
import com.leisurely.spread.util.SharedPreferencesUtil;
import com.leisurely.spread.util.StringUtil;
import com.leisurely.spread.util.ToastUtil;
import com.leisurely.spread.widget.permission.MyPermission;
import com.leisurely.spread.widget.permission.Permission;
import com.leisurely.spread.widget.permission.PermissionListener;

import java.util.List;

/**
 * Created by Administrator on 2018/12/11.
 */

public class ZhuanbiActivity extends BaseActivity {

    private TextView balance;
    private EditText address;
    private EditText number;
    private ImageView scan;
    private TextView confirm_modift;
    private TextView textView_right_title_bar;
    private EditText paypwd;


    private static final int REQUEST_SCAN = 49374;

    public static ZhuanbiActivity activity;


    private XclModel xclModel;
    private ImageSelectUtil mImageSelectUtil;//图片选择器

    public ImageSelectUtil getmImageSelectUtil() {
        return mImageSelectUtil;
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_zhuanbi);
        setTitleName("转币");
        balance = findViewById(R.id.balance);
        address = findViewById(R.id.address);
        number = findViewById(R.id.number);
        scan = findViewById(R.id.scan);
        confirm_modift = findViewById(R.id.confirm_modift);
        paypwd = findViewById(R.id.paypwd);
        textView_right_title_bar = findViewById(R.id.textView_right_title_bar);
        textView_right_title_bar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initData() {
        super.initData();
        activity = this;
        textView_right_title_bar.setText("转币记录");
        xclModel = new XclModel(this);
        xclModel.rechargeindex("2");
        mImageSelectUtil = new ImageSelectUtil(this);
    }

    @Override
    protected void initListener() {
        confirm_modift.setOnClickListener(this);
        textView_right_title_bar.setOnClickListener(this);
        scan.setOnClickListener(this);
    }

    public void commitSuccess() {
        ToastUtil.showToast("提交成功");
        finish();
    }

    public void rechargeindexSuccess(JSONObject json) {
        String balance = json.getString("balance");
//        addressStr = json.getString("address");
        this.balance.setText(balance);
//        address.setText(addressStr);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get_captcha:
                xclModel.sendVerficationCode(SharedPreferencesUtil.readString(SysConstants.TELLPHONE, ""),
                        YZMEnum.WITHDRAWAL.getId(), "");
                break;
            case R.id.scan:
                new MyPermission(this, Permission.CAMERA, new PermissionListener() {
                    @Override
                    public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
                        openScan();
                    }

                    @Override
                    public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                        ToastUtil.makeTextAndShow("未打开相机权限！");
                    }
                }).request();
                break;
            case R.id.confirm_modift:
                String result = validate();
                if (StringUtil.isNullOrEmpty(result)) {
                    xclModel.transfer(number.getText().toString(), paypwd.getText().toString(), address.getText().toString());
                } else {
                    ToastUtil.showToast(result);
                }
                break;
            case R.id.textView_right_title_bar:
                startActivity(new Intent(this, ZhuanbiDetailActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SCAN && resultCode == RESULT_OK) {
         String code  =data.getStringExtra("result");
         if(StringUtil.isNotNull(code)) {
             try{
                 if(code.indexOf("LCHGetCoin")>-1){
                    JSONObject json = JSONObject.parseObject(code);
                    code  =json.getString("LCHGetCoin");
                     address.setText(code);
                     address.setSelection(code.length());
                 }else {
                     ToastUtil.showToast("无效的二维码");
                 }
             }catch (Exception e){
                 ToastUtil.showToast("无效的二维码");
             }
         }else {
             ToastUtil.showToast("无效的二维码");
         }
        }

    }

    /**
     * 打开系统默认扫描界面
     */
    public void openScan() {
        if (Build.VERSION.SDK_INT>22){
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                //先判断有没有权限 ，没有就在这里进行权限的申请
//                ActivityCompat.requestPermissions(this,
//                        new String[]{android.Manifest.permission.CAMERA},CAMERA_OK);
                Intent intent =new Intent(Settings.ACTION_SETTINGS);
                startActivity(intent);
            }else {
                startActivityForResult(new Intent(this, ScanActivity.class),REQUEST_SCAN);
            }
        }else {
            startActivityForResult(new Intent(this, ScanActivity.class),REQUEST_SCAN);

        }

//        IntentIntegrator integrator = new IntentIntegrator(this);
//        integrator.setCaptureActivity(MyCaptureActivity.class);
//        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
//        integrator.setPrompt("Scan something");
//        integrator.setOrientationLocked(false);
//        integrator.setBeepEnabled(false);
//        integrator.initiateScan();
//        IntentIntegrator integrator = new IntentIntegrator(this);
//        integrator.setCaptureActivity(MyCaptureActivity.class);
//        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
//        integrator.setPrompt("Scan a barcode");
//        integrator.setCameraId(0);  // Use a specific camera of the device
//        integrator.setBeepEnabled(false);
//        integrator.setBarcodeImageEnabled(true);
//        integrator.initiateScan();
    }

    public void scanResult(final String code) {
        address.setText(code);
        address.setSelection(code.length());
    }

    @NonNull
    private String validate() {
        if (StringUtil.isNullOrEmpty(address.getText().toString())) {
            return "请输入账号";
        }
        if (StringUtil.isNullOrEmpty(number.getText().toString())) {
            return "请输入转出数量";
        }
        if (StringUtil.isNullOrEmpty(paypwd.getText().toString())) {
            return "请输入支付密码";
        }
        return "";
    }
}
