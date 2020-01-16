package com.leisurely.spread.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.Date;

import com.leisurely.spread.R;
import com.leisurely.spread.config.SysConstants;
import com.leisurely.spread.framework.base.BaseActivity;
import com.leisurely.spread.model.XclModel;
import com.leisurely.spread.util.SharedPreferencesUtil;
import com.leisurely.spread.util.ToastUtil;


public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler {

    private IWXAPI api;

    private final String getTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

    private XclModel model;

    public static Activity activity;

    protected void initView() {

        if (api == null) {
//            api = WXAPIFactory.createWXAPI(this, SysConstants.weChatAppId, false);
        }

        if (!api.isWXAppInstalled()) {
            // 提醒用户没有安装微信
            ToastUtil.showToast(getResources().getString(R.string.pls_install_wechat));
            return;
        }


//        api.registerApp(SysConstants.weChatAppId);
        api.handleIntent(getIntent(), this);


        final SendAuth.Req req = new SendAuth.Req();

        req.scope = "snsapi_userinfo";
        req.state = new Date().toString();

        api.sendReq(req);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        model = new XclModel(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
        finish();
    }

    @Override
    public void onReq(BaseReq arg0) {
    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            int errCode = resp.errCode;
            if (errCode == -1) {
                ToastUtil.showToast(resp.errStr);
                finish();
//                EventBus.getDefault().post("wx1");
            } else if (errCode == 0) {
//                EventBus.getDefault().post("wx0");
                ToastUtil.showToast("支付完成");
                this.finish();
            } else {
                ToastUtil.showToast("支付失败");
                finish();
            }
        } else if (resp.getType() == ConstantsAPI.COMMAND_SENDAUTH) {

            switch (resp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    String code = ((SendAuth.Resp) resp).code;
//                ToastUtil.showToast(getResources().getString(R.string.success_authorization));
                    // 上面的code就是接入指南里要拿到的code
//                    model.wechatAuthorize(getTokenUrl
//                            .replace("APPID", SysConstants.weChatAppId)
//                            .replace("SECRET", SysConstants.weChatAppsecret)
//                            .replace("CODE", code));
//                    break;
                case BaseResp.ErrCode.ERR_COMM:
                    ToastUtil.showToast(getResources().getString(R.string.fail_authorization));
                    finish();
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL://用户取消
                    ToastUtil.showToast(getResources().getString(R.string.cancel_authorization));
                    finish();
                    break;
                case BaseResp.ErrCode.ERR_SENT_FAILED:
                    ToastUtil.showToast(getResources().getString(R.string.fail_authorization));
                    finish();
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED://用户拒绝授权
                    ToastUtil.showToast(getResources().getString(R.string.refuse_authorization));
                    finish();
                    break;
                case BaseResp.ErrCode.ERR_UNSUPPORT:
                    ToastUtil.showToast(getResources().getString(R.string.fail_authorization));
                    finish();
                    break;
                case BaseResp.ErrCode.ERR_BAN:
                    ToastUtil.showToast(getResources().getString(R.string.fail_authorization));
                    finish();
                    break;

                default:
                    break;
            }
        }
    }

    public void success(JSONObject result) {

        if (result.containsKey("access_token")) {
            SharedPreferencesUtil.saveString(SysConstants.WECHAT_TOKEN, result.getString("access_token"));
        }

        if (result.containsKey("refresh_token")) {
            SharedPreferencesUtil.saveString(SysConstants.WECHAT_REFRESH_TOKNE, result.getString("refresh_token"));
        }

//        if (activity instanceof LoginRegisterActivity) {
//            ((LoginRegisterActivity) activity).getOpenIdSuccess(result);
//        } else if (activity instanceof SecurityCenterActivity) {
//            ((SecurityCenterActivity) activity).getOpenIdSuccess(result);
//        }
        finish();
    }

    @Override
    public void onClick(View v) {

    }

}
