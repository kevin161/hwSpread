package com.leisurely.spread.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import com.leisurely.spread.R;
import com.leisurely.spread.config.SysConstants;
import com.leisurely.spread.framework.base.BaseActivity;
import com.leisurely.spread.util.ToastUtil;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    public static BaseActivity activity;

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//		setContentView(R.layout.pay_result);

//        api = WXAPIFactory.createWXAPI(this, SysConstants.weChatAppId,false);
//        api.registerApp(SysConstants.weChatAppId);//注册appid
        api.handleIntent(getIntent(), this);

//        String jsonStr = getIntent().getStringExtra("json");
//        if(StringUtil.isNotNull(jsonStr)) {
//            JSONObject json =JSONObject.parseObject(jsonStr);
//            PayReq req = new PayReq();
//            req.appId = json.getString("appid");
//            req.partnerId = json.getString("partnerid");
//            req.prepayId = json.getString("prepayid");
//            req.nonceStr = json.getString("noncestr");
//            req.timeStamp = json.getString("timestamp");
//            req.packageValue = json.getString("package");
//            req.sign = json.getString("sign");
//            req.extData = "app data"; // optional
//            api.sendReq(req);
//        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {

        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
//			AlertDialog.Builder builder = new AlertDialog.Builder(this);
//			builder.setTitle(R.string.app_tip);
//			builder.setMessage(getString(R.string.pay_result_callback_msg, String.valueOf(resp.errCode)));
//			builder.show();
            if (resp.errCode == -2) {
                ToastUtil.showToast(R.string.pay_cancel);
            } else if (resp.errCode == -1) {
                ToastUtil.showToast(R.string.pay_fail);
            } else if (resp.errCode == 0) {
//                if (activity instanceof OnlinePayActivity) {
//                    ((OnlinePayActivity) activity).paySuccess();
//                }else if(activity instanceof WaitPayActivity){
//                    ((WaitPayActivity) activity).paySuccess();
//                }
            }else{
                ToastUtil.showToast(R.string.pay_fail);
            }
        }
        finish();
    }
}