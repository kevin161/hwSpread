package com.leisurely.spread.UI.activity.money;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.PayTask;

import java.util.Map;

import com.leisurely.spread.R;
import com.leisurely.spread.framework.base.BaseActivity;
import com.leisurely.spread.model.XclModel;
import com.leisurely.spread.util.AuthResultUtil;
import com.leisurely.spread.util.PayResultUtil;
import com.leisurely.spread.util.ToastUtil;


/**
 * 重要说明:
 * <p>
 * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
 * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
 * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
 */
public class AlipayActivity extends BaseActivity {

    /**
     * 支付宝支付业务：入参app_id
     */
//    public static final String APPID = SysConstants.alipayAppId;

    /**
     * 支付宝账户登录授权业务：入参pid值
     */
//    public static final String PID = SysConstants.alipayPId;
    /**
     * 支付宝账户登录授权业务：入参target_id值
     */
//    public static final String TARGET_ID = new Date().toString();

    /** 商户私钥，pkcs8格式 */
    /** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
    /** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
    /** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
    /** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
    /**
     * 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1
     */
//    public static String RSA2_PRIVATE = "";
//    public static final String RSA_PRIVATE = "";

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

//    public static final String clideid = "alipay-private-key";
//    public static final String FileName = "alipay-private-key.pem";

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
//    private GoogleApiClient client;

    private XclModel model;
    private int type;//0授权 1支付

    //该方法用于打开assets中的alipay-private-key.pem文档资源，获得里面的alipay-private-key.pem数据
//    private static String getRSA2(Context context) {
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        String str = null;
//        try {
//            InputStream is = context.getResources().getAssets().open("private_key_pkcs8.pem");
//            byte[] bytes = new byte[1024];
//            int length = 0;
//            while ((length = is.read(bytes)) != -1) {
//                outputStream.write(bytes, 0, length);
//            }
//            is.close();
//            outputStream.close();
//            str = outputStream.toString();
//    str = str.substring(str.indexOf("\n")+1,str.lastIndexOf("\n"));
//
//    str = str.substring(0,str.lastIndexOf("\n"));0
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return str;
//    }

    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResultUtil PayResultUtil = new PayResultUtil((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = PayResultUtil.getResult();// 同步返回需要验证的信息
                    String resultStatus = PayResultUtil.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功

                    Intent intent = new Intent();
                    intent.putExtra("resultStatus", resultStatus);
                    setResult(RESULT_OK, intent);
                    finish();
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResultUtil AuthResultUtil = new AuthResultUtil((Map<String, String>) msg.obj, true);
                    String resultStatus = AuthResultUtil.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(AuthResultUtil.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
//                        Toast.makeText(AlipayLoginActivity.this,
//                                "授权成功\n" + String.format("authCode:%s", AuthResultUtil.getAuthCode()), Toast.LENGTH_SHORT)
//                                .show();
                        //TODO 将获得的用户信息传给服务器
//                        model.alipayAuthcodeUserId(AuthResultUtil.getAuthCode(), AuthResultUtil.getUserId(), AuthResultUtil.getAlipayOpenId());
                    } else if (TextUtils.equals(resultStatus, "4000")) {
                        ToastUtil.showToast(getResources().getString(R.string.pls_install_alipay));
                    } else if (TextUtils.equals(resultStatus, "6001")) {
                        ToastUtil.showToast(getResources().getString(R.string.cancel_authorization));
                    } else if (TextUtils.equals(resultStatus, "6002")) {
                        ToastUtil.showToast(getResources().getString(R.string.network_error));
                    } else {
                        // 其他状态值则为授权失败
                        ToastUtil.showToast(getResources().getString(R.string.fail_authorization));
                    }
                    Intent intent = new Intent();
                    intent.putExtra("resultStatus", resultStatus);
                    setResult(RESULT_OK, intent);
                    finish();
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

    @Override
    protected void initView() {

    }

    public static boolean checkAliPayInstalled(Context context) {

        Uri uri = Uri.parse("alipays://platformapi/startApp");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        ComponentName componentName = intent.resolveActivity(context.getPackageManager());
        return componentName != null;
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initData() {
        model = new XclModel(this);
        String url = getIntent().getStringExtra("url");
        type = getIntent().getIntExtra("type", 0);
        switch (type) {
            case 0:
                authV2(url);
                break;
            case 1:
                payV2(url);
                break;
            default:
                break;
        }
    }

    /**
     * 支付宝支付业务
     */
    public void payV2(final String url) {
//        if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
//            new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
//                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialoginterface, int i) {
//                            //
//                            finish();
//                        }
//                    }).show();
//            return;
//        }

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo的获取必须来自服务端；
         */
//        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
//        Map<String, String> params = OrderInfoUtil.buildOrderParamMap(APPID, rsa2);
//        String orderParam = OrderInfoUtil.buildOrderParam(params);
//
//        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
//        String sign = OrderInfoUtil.getSign(params, privateKey, rsa2);
//        final String orderInfo = orderParam + "&" + sign;

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {

                PayTask alipay = new PayTask(AlipayActivity.this);
//                JSONObject json = new JSONObject();
//                json.put("sign",url);
//                json.put("app_id", SysConstants.Alipay_APP_ID);
//                json.put("method","alipay.trade.app.pay");
//                json.put("format","JSON");
//                json.put("charset","utf-8");
//                json.put("sign_type","RSA2");
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                json.put("timestamp", sdf.format(new Date()));
//                json.put("version","1.0");
//json.put("notify_url","http://api.test.alipay.net/atinterface/receive_notify.htm");
//json.put("biz_content",content);

                Map<String, String> result = alipay.payV2(url, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * 支付宝账户授权业务
     */
    public void authV2(final String url) {
//        if (TextUtils.isEmpty(PID) || TextUtils.isEmpty(APPID)
//                || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))
//                || TextUtils.isEmpty(TARGET_ID)) {
//            new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置PARTNER |APP_ID| RSA_PRIVATE| TARGET_ID")
//                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialoginterface, int i) {
//                        }
//                    }).show();
//            return;
//        }

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * authInfo的获取必须来自服务端；
         */
//        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
//        Map<String, String> authInfoMap = OrderInfoUtil.buildAuthInfoMap(PID, APPID, TARGET_ID, rsa2);
//        String info = OrderInfoUtil.buildOrderParam(authInfoMap);
//
//        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
//        String sign = OrderInfoUtil.getSign(authInfoMap, privateKey, rsa2);
//        String sign = url.substring(url.indexOf("sign=")+5);
//        final String authInfo = info + "&" + sign;
        Runnable authRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造AuthTask 对象
                AuthTask authTask = new AuthTask(AlipayActivity.this);
                // 调用授权接口，获取授权结果
                Map<String, String> result = authTask.authV2(url, true);

                Message msg = new Message();
                msg.what = SDK_AUTH_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread authThread = new Thread(authRunnable);
        authThread.start();
    }


    /**
     * get the sdk version. 获取SDK版本号
     */
    public void getSDKVersion() {
        PayTask payTask = new PayTask(this);
        String version = payTask.getVersion();
        Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
//    public Action getIndexApiAction() {
//        Thing object = new Thing.Builder()
//                .setName("PayDemo Page")
//                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
//                .build();
//        return new Action.Builder(Action.TYPE_VIEW)
//                .setObject(object)
//                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
//                .build();
//    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        client.connect();
//        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        AppIndex.AppIndexApi.end(client, getIndexApiAction());
//        client.disconnect();
    }

    @Override
    public void onClick(View v) {

    }
}
