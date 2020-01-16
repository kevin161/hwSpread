//package com.leisurely.xxy.UI.activity.capture;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.view.KeyEvent;
//
//
//import com.journeyapps.barcodescanner.DecoratedBarcodeView;
//
//
///**
// * Created by qianwenbin on 2017/9/24.
// */
//
//public class PurchaseCaptureActivity extends Activity {
//    private CaptureManagerUtil capture;
//    private DecoratedBarcodeView barcodeScannerView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        barcodeScannerView = initializeContent();
//        capture.from = 2;
//        capture = new CaptureManagerUtil(this, barcodeScannerView);
//        capture.initializeFromIntent(getIntent(), savedInstanceState);
//        capture.decode();
//    }
//
//    /**
//     * Override to use a different layout.
//     *
//     * @return the DecoratedBarcodeView
//     */
//    protected DecoratedBarcodeView initializeContent() {
//        setContentView(com.google.zxing.client.android.R.layout.zxing_capture);
//        return (DecoratedBarcodeView) findViewById(com.google.zxing.client.android.R.id.zxing_barcode_scanner);
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        capture.onResume();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        capture.onPause();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        capture.onDestroy();
//    }
//
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        capture.onSaveInstanceState(outState);
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
//        capture.onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        return barcodeScannerView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
//    }
//}
