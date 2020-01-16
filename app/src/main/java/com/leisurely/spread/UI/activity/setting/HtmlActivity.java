package com.leisurely.spread.UI.activity.setting;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.leisurely.spread.R;
import com.leisurely.spread.UI.activity.money.AlipayActivity;
import com.leisurely.spread.framework.base.BaseActivity;
import com.leisurely.spread.model.XclModel;
import com.leisurely.spread.util.StringUtil;
import com.leisurely.spread.util.ToastUtil;
import com.leisurely.spread.widget.permission.MyPermission;
import com.leisurely.spread.widget.permission.Permission;
import com.leisurely.spread.widget.permission.PermissionListener;


/**
 * Created by qianwenbin on 2017/8/28.
 * webview展示类
 */

public class HtmlActivity extends BaseActivity {

    /**
     * webview
     */
    private static WebView agreement_web_view;
    /**
     * 传入的h5地址
     */
    private String url;

    /**
     * 传入的h5界面标题
     */
    private String title;

    private static String imageBase64;

    private RelativeLayout head;

    private ValueCallback<Uri> mUploadMessage;// 表单的数据信息
    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    private final static int FILECHOOSER_RESULTCODE = 1;// 表单的结果回调</span>
    private Uri imageUri;
    private int from;
    private boolean hasError;//是否加载页面错误
    private XclModel xclModel;
    public static int resultPayCode;
    private static String callbackPayPath;
    private String qrcode;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (agreement_web_view != null) {
            agreement_web_view.destroy();
        }
    }

    @Override
    protected void initView() {
        url = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");
        from = getIntent().getIntExtra("from", 0);
        setContentView(R.layout.activity_register_agreement);
        setTitleName(title);
        agreement_web_view = findViewById(R.id.agreement_web_view);
        head = findViewById(R.id.head);
        if (from == 1) {
            head.setVisibility(View.GONE);
        }

        agreement_web_view.addJavascriptInterface(new HtmlActivity.JsInteration(), "android");
        WebSettings webSettings = agreement_web_view.getSettings();
        //设置为可调用js方法
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);

        //支持插件
        webSettings.setPluginState(WebSettings.PluginState.ON);

        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        //其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); //关闭webview中缓存
        webSettings.setDomStorageEnabled(true);
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式

        //将token 加入userAgentString
//        webSettings.setUserAgentString(webSettings.getUserAgentString() + "\"Authorization/" + AESUtil.decrypt(SysConstants.AES_PASSWORD,
//                SharedPreferencesUtil.readString(SysConstants.ACCESS_TOKEN, "")) + " business-version/" + SysConstants.VERSION + "\"");
        agreement_web_view.setHorizontalScrollBarEnabled(false);//水平不显示
        agreement_web_view.setVerticalScrollBarEnabled(false); //垂直不显示
        agreement_web_view.setWebChromeClient(new WebChromeClient() {

            @Override
            public boolean onShowFileChooser(WebView webView,
                                             ValueCallback<Uri[]> filePathCallback,
                                             FileChooserParams fileChooserParams) {
                mUploadCallbackAboveL = filePathCallback;
                take();
                return true;
            }


            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                mUploadMessage = uploadMsg;
                take();
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
                mUploadMessage = uploadMsg;
                take();
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                mUploadMessage = uploadMsg;
                take();
            }
        });

        agreement_web_view.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
            }


        });
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        xclModel = new XclModel(this);
        agreement_web_view.clearCache(true);
        agreement_web_view.clearHistory();
        if (from == 1) {
            showProgressDialog("加载中");
            hasError = true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    closeDialog();
                }
            }).start();
        }
        agreement_web_view.loadUrl(url);
    }

    @Override
    public void onClick(View v) {

    }


    public class JsInteration {

        @JavascriptInterface
        public String openCamera() {
            imageBase64 = "";
//            String destFileName = SysConstants.LOCAL_URL + "upload/" + StringUtil.makeRandom(15) + ".jpg";
//            ImageUtil.getInstance().startSelect(HtmlActivity.this, null, destFileName, false, 0);
//            while (StringUtil.isNullOrEmpty(imageBase64)) {
//                try {
//                    wait(1000);
//                } catch (Exception e) {
//
//                }
//            }
            return imageBase64;
        }

        /**
         * 是否隐藏(需要改动实现方式 暂不做隐藏处理)
         *
         * @param isShow
         */
        @JavascriptInterface
        public void showTabF(String isShow) {//true显示 false隐藏
            hasError = false;
            dismissProgressDialog();
        }

        /**
         * 返回
         */
        @JavascriptInterface
        public void searchBack() {
            HtmlActivity.this.finish();
        }

        /**
         * 拨打电话
         *
         * @param phone
         */
        @SuppressLint("MissingPermission")
        @JavascriptInterface
        public void callPhone(String phone) {
            //用intent启动拨打电话
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
            HtmlActivity.this.startActivity(intent);
        }

        /**
         * 调用相机扫码
         *
         * @return
         */
        @JavascriptInterface
        public String getCam() {
            qrcode = "";
            new MyPermission(sContext, Permission.CAMERA, new PermissionListener() {
                @Override
                public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
                    openScan();
                }

                @Override
                public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                    ToastUtil.makeTextAndShow("未打开相机权限！");
                }
            }).request();
            while (StringUtil.isNullOrEmpty(qrcode)) {
                try {
                    wait(1000);
                } catch (Exception e) {

                }
            }
            return qrcode;
        }
    }

    /**
     * 防止没网络加载失败后 一直显示加载中
     */
    synchronized private void closeDialog() {
        try {
            wait(15000);
            if (hasError) {
                dismissProgressDialog();
            }
        } catch (Exception e) {

        }
    }

    /**
     * 拿签名调用支付宝付款
     *
     * @param url
     */
    public void payAlignSignSuccess(String url) {
//        ToastUtil.showToast(url);
        startActivityForResult(new Intent(this, AlipayActivity.class).putExtra("type", 1).putExtra("url", url), 2222);
    }

    public static void callbackPay() {
        agreement_web_view.loadUrl("javascript:getPayMsg('" + resultPayCode + "','" + callbackPayPath + "')");
    }

    /**
     * 打开系统默认扫描界面
     */
    public void openScan() {
//        IntentIntegrator integrator = new IntentIntegrator(this);
//        integrator.setCaptureActivity(PurchaseCaptureActivity.class);
//        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
//        integrator.setPrompt("Scan something");
//        integrator.setOrientationLocked(false);
//        integrator.setBeepEnabled(false);
//        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        try {
        // 上传文件后续动作
//            if (ImageUtil.getInstance().onActivityResult(requestCode, resultCode, data)) {
//                if (requestCode == ImageUtil.PHOTO_REQUEST_GALLERY||requestCode == ImageUtil.PHOTO_REQUEST_CAMERA||requestCode == ImageUtil.PHOTO_REQUEST_CUT) {
//                    final File file = new File(ImageUtil.getInstance().getDestFileName());
//
//                    if (!file.exists()) {
//                        return;
//                    } else {
//                        FileInputStream fis = new FileInputStream(file.getAbsolutePath());
//                        final Bitmap bitmap = BitmapFactory.decodeStream(fis);
//                        new Thread() {
//                            @Override
//                            public void run() {
//                                try {
//                                    imageBase64 = BitmapUtils.bitmapToBase64(bitmap);
//                                    if(StringUtil.isNullOrEmpty(imageBase64)){
//                                        imageBase64 ="出错了";
//                                    }
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }.start();
//                    }
//                }
//                return;
//            }
//        } catch (Exception e) {
//            Log.e("上传图片", e.getMessage(), e);
//        }

        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage && null == mUploadCallbackAboveL) return;
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            if (mUploadCallbackAboveL != null) {
                onActivityResultAboveL(requestCode, resultCode, data);
            } else if (mUploadMessage != null) {

                if (result != null) {
                    String path = getPath(getApplicationContext(),
                            result);
                    Uri uri = Uri.fromFile(new File(path));
                    mUploadMessage
                            .onReceiveValue(uri);
                } else {
                    mUploadMessage.onReceiveValue(imageUri);
                }
                mUploadMessage = null;


            }
        }
    }


    @SuppressWarnings("null")
    @TargetApi(Build.VERSION_CODES.BASE)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent data) {
        if (requestCode != FILECHOOSER_RESULTCODE
                || mUploadCallbackAboveL == null) {
            return;
        }

        Uri[] results = null;

        if (resultCode == Activity.RESULT_OK) {

            if (data == null) {

                results = new Uri[]{imageUri};
            } else {
                String dataString = data.getDataString();
                ClipData clipData = data.getClipData();

                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }

                if (dataString != null)
                    results = new Uri[]{Uri.parse(dataString)};
            }
        }
        if (results != null) {
            mUploadCallbackAboveL.onReceiveValue(results);
            mUploadCallbackAboveL = null;
        } else {
            results = new Uri[]{imageUri};
            mUploadCallbackAboveL.onReceiveValue(results);
            mUploadCallbackAboveL = null;
        }

        return;
    }


    private void take() {
        File imageStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MyApp");
        // Create the storage directory if it does not exist
        if (!imageStorageDir.exists()) {
            imageStorageDir.mkdirs();
        }
        File file = new File(imageStorageDir + File.separator + "IMG_" + String.valueOf(System.currentTimeMillis()) + ".jpg");
        imageUri = Uri.fromFile(file);

        final List<Intent> cameraIntents = new ArrayList<Intent>();
        final Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        final PackageManager packageManager = getPackageManager();
        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            final String packageName = res.activityInfo.packageName;
            final Intent i = new Intent(captureIntent);
            i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            i.setPackage(packageName);
            i.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            cameraIntents.add(i);

        }
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        Intent chooserIntent = Intent.createChooser(i, "Image Chooser");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[]{}));
        HtmlActivity.this.startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE);
    }

    @SuppressLint("NewApi")
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }


    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null) cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }


}
