package com.leisurely.spread.UI.activity.setting;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.RectF;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.leisurely.spread.R;
import com.leisurely.spread.UI.activity.home.RegisterOrLoginActivity;
import com.leisurely.spread.framework.base.BaseActivity;
import com.leisurely.spread.util.DecodeUtils;
import com.leisurely.spread.util.ToastUtil;
import com.leisurely.spread.widget.permission.MyPermission;
import com.leisurely.spread.widget.permission.PermissionListener;
import com.qingmei2.library.view.QRCodeScannerView;
import com.qingmei2.library.view.QRCoverView;

import java.io.IOException;
import java.util.List;

public class ScanActivity extends BaseActivity {

    private QRCodeScannerView mScannerView;
    private QRCoverView mCoverView;
    private LinearLayout test,layout_left_title_bar;
    private static final String TAG = "ScanActivity";

    private final int PERMISSION_REQUEST_CAMERA = 0;


    @Override
    protected void initView() {
        setContentView(R.layout.activity_scan);
        mScannerView = findViewById(R.id.scanner_view);
        mCoverView = findViewById(R.id.cover_view);
        test = findViewById(R.id.test);
        layout_left_title_bar = findViewById(R.id.layout_left_title_bar);

        //自动聚焦间隔2s
        mScannerView.setAutofocusInterval(2000L);
        mScannerView.setQRDecodingEnabled(true);
        //扫描结果监听处理
        mScannerView.setOnQRCodeReadListener(new QRCodeScannerView.OnQRCodeScannerListener() {
            @Override
            public void onDecodeFinish(String text, PointF[] points) {

                //【可选】判断二维码是否在扫描框中
                judgeResult(text, points);
            }
        });
        //相机权限监听(如果你有相关的权限类，可以不实现该接口)
        mScannerView.setOnCheckCameraPermissionListener(new QRCodeScannerView.OnCheckCameraPermissionListener() {
            @Override
            public boolean onCheckCameraPermission() {
                if (ContextCompat.checkSelfPermission(ScanActivity.this, Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED) {
                    return true;
                } else {
                    ActivityCompat.requestPermissions(ScanActivity.this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
                    return false;
                }
            }
        });
        mScannerView.stopCamera();
        mScannerView.startCamera();
        //开启后置摄像头
        mScannerView.setBackCamera();

    }

    @Override
    protected void initListener() {
        layout_left_title_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //检查sd卡是否可以访问
                new MyPermission(ScanActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE, new PermissionListener() {
                    @Override
                    public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
                        // 从相册照片
                        goAlbum();
                    }

                    @Override
                    public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                        ToastUtil.showToast("没有开启sd卡访问权限！");
                    }
                }).request();
            }
        });
    }

    public static final int PHOTO_REQUEST_GALLERY = 2009;//相册中选择

    //前往相册
    private void goAlbum() {
        Intent intent_img = new Intent(Intent.ACTION_PICK);
        intent_img.setType("image/*");
        startActivityForResult(intent_img, PHOTO_REQUEST_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PHOTO_REQUEST_GALLERY) {//相册中选择
            if (data != null) {
                // 得到图片的全路径
                try {
                    Uri picUri = data.getData();
                    Bitmap bm = MediaStore.Images.Media.getBitmap(this.getContentResolver(), picUri);
                    DecodeUtils.DecodeAsyncTask decodeAsyncTask = new DecodeUtils.DecodeAsyncTask(this);
                    decodeAsyncTask.execute(bm);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
//        try {
//            // 上传文件后续动作
//            if (mImageSelectUtil.onActivityResult(requestCode, resultCode, data)) {
//                Log.e("tag", "imag=========================" + mImageSelectUtil.getDestFileName());
//                Uri picUri = mImageSelectUtil.getDestUri();
//                Bitmap bm = MediaStore.Images.Media.getBitmap(this.getContentResolver(), picUri);
//                DecodeUtils.DecodeAsyncTask decodeAsyncTask = new DecodeUtils.DecodeAsyncTask(this);
//                decodeAsyncTask.execute(bm);
//                return;
//            }
//        } catch (Exception e) {
//            Log.e("tag", e.getMessage(), e);
//        }


    }


    private void judgeResult(String result, PointF[] points) {
        //接下来是处理二维码是否在扫描框中的逻辑
        RectF finderRect = mCoverView.getViewFinderRect();
        Log.d("tag", "points.length = " + points.length);
        boolean isContain = true;
        //依次判断扫描结果的每个point是否都在扫描框内
        for (int i = 0, length = points.length; i < length; i++) {
            if (!finderRect.contains(points[i].x, points[i].y)) {
                isContain = false;  //只要有一个不在，说明二维码不完全在扫描框中
                break;
            }
        }
        if (isContain) {
//            Intent intent = new Intent();
//            intent.putExtra("result", result);
//            setResult(RESULT_OK, intent);
            Log.d(TAG, "扫描结果 result -> " + result); //扫描到的内容
            String[] strings = result.split("/");
            if (strings.length > 0) {

                String investPhone = strings[strings.length - 1];
                if (investPhone.length() == 11) {
                    startActivity(new Intent(this, RegisterOrLoginActivity.class).putExtra(RegisterOrLoginActivity.SUB_PAGE, 1)
                            .putExtra(RegisterOrLoginActivity.INVEST_PHONE, investPhone));
                } else {
                    ToastUtil.makeTextAndShow("该二维码无效。");
                }
                finish();
            }
        } else {
            Log.d(TAG, "扫描失败！请将二维码图片摆放在正确的扫描区域中...");
        }
    }

    /**
     * 权限请求回调
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != PERMISSION_REQUEST_CAMERA) {
            return;
        }

        if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mScannerView.grantCameraPermission();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }
}
