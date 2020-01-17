package com.leisurely.spread.model;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.leisurely.spread.UI.activity.home.AddAlipayActivity;
import com.leisurely.spread.UI.activity.home.AddCardActivity;
import com.leisurely.spread.UI.activity.home.AddWechatActivity;
import com.leisurely.spread.UI.activity.home.CommissionActivity;
import com.leisurely.spread.UI.activity.home.ForgetPayPwdActivity;
import com.leisurely.spread.UI.activity.home.ForgetPwdActivity;
import com.leisurely.spread.UI.activity.home.GoodsDetailActivity;
import com.leisurely.spread.UI.activity.home.GoodIntroduceActivity;
import com.leisurely.spread.UI.activity.home.GoodsOnSellActivity;
import com.leisurely.spread.UI.activity.home.HomeActivity;
import com.leisurely.spread.UI.activity.home.ModifyPasswordActivity;
import com.leisurely.spread.UI.activity.home.ModifyPayPasswordActivity;
import com.leisurely.spread.UI.activity.home.MyExtension2Activity;
import com.leisurely.spread.UI.activity.home.MyExtensionActivity;
import com.leisurely.spread.UI.activity.home.NewsActivity;
import com.leisurely.spread.UI.activity.home.NewsDatailActivity;
import com.leisurely.spread.UI.activity.home.NickNameActivity;
import com.leisurely.spread.UI.activity.home.OrderActivity;
import com.leisurely.spread.UI.activity.home.OrderFahuoActivity;
import com.leisurely.spread.UI.activity.home.PersonCenterActivity;
import com.leisurely.spread.UI.activity.home.PersonDetailActivity;
import com.leisurely.spread.UI.activity.home.RealNameActivity;
import com.leisurely.spread.UI.activity.home.RegisterOrLoginActivity;
import com.leisurely.spread.UI.activity.home.ScoreActivity;
import com.leisurely.spread.UI.activity.home.ShopActivity;
import com.leisurely.spread.UI.activity.home.ShopPay2Activity;
import com.leisurely.spread.UI.activity.home.ShopPayActivity;
import com.leisurely.spread.UI.activity.home.ShouCangActivity;
import com.leisurely.spread.UI.activity.home.UpdatePhoneOneActivity;
import com.leisurely.spread.UI.activity.home.UpdatePhoneTwoActivity;
import com.leisurely.spread.UI.activity.home.ZijinActivity;
import com.leisurely.spread.UI.activity.money.AddUpdateAddressActivity;
import com.leisurely.spread.UI.activity.money.AddressActivity;
import com.leisurely.spread.UI.activity.money.AuthAccountInfoActivity;
import com.leisurely.spread.UI.activity.money.AuthNameBindCardActivity;
import com.leisurely.spread.UI.activity.money.BindCardActivity;
import com.leisurely.spread.UI.activity.money.BonusWithDrawalActivity;
import com.leisurely.spread.UI.activity.money.BuyDetailActivity;
import com.leisurely.spread.UI.activity.money.ChongbiActivity;
import com.leisurely.spread.UI.activity.money.ChongbiDetailActivity;
import com.leisurely.spread.UI.activity.money.ITSActivity;
import com.leisurely.spread.UI.activity.money.ITSLogActivity;
import com.leisurely.spread.UI.activity.money.MoneyHistoryLogActivity;
import com.leisurely.spread.UI.activity.money.MyMoneyActivity;
import com.leisurely.spread.UI.activity.money.MyRecommenderActivity;
import com.leisurely.spread.UI.activity.money.PayDetailActivity;
import com.leisurely.spread.UI.activity.money.QuantificationDetailActivity;
import com.leisurely.spread.UI.activity.money.QuantificationWinActivity;
import com.leisurely.spread.UI.activity.money.Recharge2Activity;
import com.leisurely.spread.UI.activity.money.RechargeActivity;
import com.leisurely.spread.UI.activity.money.SellDetailActivity;
import com.leisurely.spread.UI.activity.money.ShareWinActivity;
import com.leisurely.spread.UI.activity.money.ShoubiActivity;
import com.leisurely.spread.UI.activity.money.ShoubiDetailActivity;
import com.leisurely.spread.UI.activity.money.TibiActivity;
import com.leisurely.spread.UI.activity.money.TibiDetailActivity;
import com.leisurely.spread.UI.activity.money.WithDrawalActivity;
import com.leisurely.spread.UI.activity.money.Withdrawal2Activity;
import com.leisurely.spread.UI.activity.money.ZhuanbiActivity;
import com.leisurely.spread.UI.activity.money.ZhuanbiDetailActivity;
import com.leisurely.spread.UI.activity.order.PlaceOrderActivity;
import com.leisurely.spread.UI.activity.pager.HomePager;
import com.leisurely.spread.UI.activity.pager.PersonalCenterPager;
import com.leisurely.spread.UI.activity.pager.QuantificationPager;
import com.leisurely.spread.UI.activity.pager.SharePager;
import com.leisurely.spread.UI.activity.setting.CommunityActivity;
import com.leisurely.spread.UI.activity.setting.CountrySelectActivity;
import com.leisurely.spread.UI.activity.setting.FeedbackActivity;
import com.leisurely.spread.UI.activity.setting.LoginActivity;
import com.leisurely.spread.UI.activity.setting.MessageActivity;
import com.leisurely.spread.UI.activity.setting.OrderQueryActivity;
import com.leisurely.spread.config.SysConstants;
import com.leisurely.spread.framework.base.BaseActivity;
import com.leisurely.spread.framework.base.BaseModel;
import com.leisurely.spread.framework.httprequest.OkHttp;
import com.leisurely.spread.framework.tabManager.BasePager;
import com.leisurely.spread.model.bean.BankBrief;
import com.leisurely.spread.model.bean.BankSub;
import com.leisurely.spread.model.bean.Chongbi;
import com.leisurely.spread.model.bean.Country;
import com.leisurely.spread.model.bean.ITS;
import com.leisurely.spread.model.bean.Message;
import com.leisurely.spread.model.bean.OrderDetail;
import com.leisurely.spread.model.bean.PayWay;
import com.leisurely.spread.model.bean.Payinformation;
import com.leisurely.spread.model.bean.Quantification;
import com.leisurely.spread.model.bean.QuantificationWin;
import com.leisurely.spread.model.bean.SellDetail;
import com.leisurely.spread.model.bean.Shoubi;
import com.leisurely.spread.model.bean.User;
import com.leisurely.spread.util.SharedPreferencesUtil;
import com.leisurely.spread.util.StringUtil;
import com.leisurely.spread.util.ToastUtil;

import java.util.HashMap;


public class XclModel extends BaseModel {
    private TokenModelListener listener;
    private BaseActivity context;
    private BasePager pager;

    public XclModel(BaseActivity context) {
        this.context = context;
    }

    public XclModel(BaseActivity context, BasePager pager) {
        this.context = context;
        this.pager = pager;
    }

    public void setListener(TokenModelListener listener) {
        this.listener = listener;
    }


    @Override
    public void onStart(String url, String tag) {
        super.onStart(url, tag);
    }


    /**
     * 获取36开户-获取银行
     */
    public void getBank() {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        request(false, OkHttp.METHOD.GET, context, SysConstants.GetBank, header, map, "getBank");
    }

    /**
     * 获取国家列表
     */
    public void getCountry(int pageNum, int pageSize) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        map.put("pagenum", String.valueOf(pageNum));
        map.put("pagesize", String.valueOf(pageSize));
        request(false, OkHttp.METHOD.POST, context, SysConstants.GetCountry, header, map, "getCountry");
    }

    /**
     * 忘记密码
     */
    public void resetpwd(String phone, String yzm, String password) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
//        while (phone.indexOf("0") == 0) {
//            phone.substring(1);
//        }
        map.put("mobile", phone);
        map.put("newpassword", password);
        map.put("captcha", yzm);
        request(true, OkHttp.METHOD.POST, context, SysConstants.resetpwd, header, map, "resetpwd");
    }

    /**
     * 检测版本号
     */
    public void checkVersion() {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
//        while (phone.indexOf("0") == 0) {
//            phone.substring(1);
//        }
        map.put("version", SysConstants.VERSION);
        map.put("type", "2");
        request(false, OkHttp.METHOD.POST, context, SysConstants.checkVersion, header, map, "checkVersion");
    }


    /**
     * 修改密码
     */
    public void editPassword(String oldPassword, String newPassword) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        map.put("oldpassword", oldPassword);
        map.put("newpassword", newPassword);
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        request(true, OkHttp.METHOD.POST, context, SysConstants.EditUser, header, map, "editPassword");
    }

    /**
     * 修改支付密码
     */
    public void editPayPassword(String mobile, String pwd, String captcha) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        map.put("mobile", mobile);
        map.put("newpaypwd", pwd);
        map.put("captcha", captcha);
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        request(true, OkHttp.METHOD.POST, context, SysConstants.EditPayPwd, header, map, "editPayPassword");
    }

    /**
     * 完善个人资料
     */
    public void profile(String name, String trueid) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        map.put("truename", name);
        map.put("trueid", trueid);
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        request(true, OkHttp.METHOD.POST, context, SysConstants.profile, header, map, "profile");
    }

    /**
     * 获得推荐码
     */
    public void shareUrl() {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        request(true, OkHttp.METHOD.POST, context, SysConstants.shareUrl, header, map, "shareUrl");
    }

    /**
     * 充币提币页面信息
     */
    public void rechargeindex(String type) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        map.put("type", type);
        request(true, OkHttp.METHOD.POST, context, SysConstants.rechargeindex, header, map, "rechargeindex");
    }

    /**
     * 充币记录
     */
    public void rechargeList(int pageNum, int pageSize) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        map.put("pagenum", String.valueOf(pageNum));
        map.put("pagesize", String.valueOf(pageSize));
        request(true, OkHttp.METHOD.POST, context, SysConstants.rechargeList, header, map, "rechargeList");
    }

    /**
     * 提币记录
     */
    public void withdrawalList(int pageNum, int pageSize) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        map.put("pagenum", String.valueOf(pageNum));
        map.put("pagesize", String.valueOf(pageSize));
        request(true, OkHttp.METHOD.POST, context, SysConstants.withdrawalList, header,
                map, "withdrawalList");
    }

    /**
     * 提币记录
     */
    public void transferList(int pageNum, int pageSize) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        map.put("pagenum", String.valueOf(pageNum));
        map.put("pagesize", String.valueOf(pageSize));
        request(true, OkHttp.METHOD.POST, context, SysConstants.transferList, header, map, "transferList");
    }

    /**
     * 收币记录
     */
    public void collectList(int pageNum, int pageSize) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        map.put("pagenum", String.valueOf(pageNum));
        map.put("pagesize", String.valueOf(pageSize));
        request(true, OkHttp.METHOD.POST, context, SysConstants.collectList, header, map, "collectList");
    }

    /**
     * 量化奖励
     */
    public void quantificationWin(int pageNum, int pageSize) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        map.put("pagenum", String.valueOf(pageNum));
        map.put("pagesize", String.valueOf(pageSize));
        request(true, OkHttp.METHOD.POST, context, SysConstants.quantificationWin, header,
                map, "quantificationWin");
    }

    /**
     * 分享奖励
     */
    public void shareWin(int pageNum, int pageSize) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        map.put("pagenum", String.valueOf(pageNum));
        map.put("pagesize", String.valueOf(pageSize));
        request(true, OkHttp.METHOD.POST, context, SysConstants.shareWin, header, map, "shareWin");
    }

    /**
     * 量化明细
     */
    public void investmentList(int pageNum, int pageSize) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        map.put("pagenum", String.valueOf(pageNum));
        map.put("pagesize", String.valueOf(pageSize));
        request(true, OkHttp.METHOD.POST, context, SysConstants.investmentList, header,
                map, "investmentList");
    }

    /**
     * 启动量化列表
     */
    public void addInvestmentList() {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        request(true, OkHttp.METHOD.POST, context, SysConstants.addInvestmentList, header,
                map, "addInvestmentList");
    }


    /**
     * 注册
     */
    public void register(String phone, String countryCode, String pwd, String paypwd, String code, String recommended) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        map.put("mobile", phone);
        map.put("country_mobile", countryCode);
        map.put("password", pwd);
        map.put("paypwd", paypwd);
        map.put("captcha", code);
        if (StringUtil.isNotNull(recommended)) {
            map.put("recommended", recommended);
        }
        request(true, OkHttp.METHOD.POST, context, SysConstants.Register, header, map, "register");
    }

    /**
     * 第三方注册
     */
    public void thirdRegister(User user, String platform, String params) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        String mobile = user.getTellphone();
        while (mobile.indexOf("0") == 0) {
            mobile.substring(1);
        }
        map.put("mobile", mobile);
        map.put("country_mobile", user.getCode());
        map.put("password", user.getPassword());
        map.put("captcha", user.getVerificationCode());
        map.put("recommend", user.getRecommend());
        map.put("platform", platform);
        map.put("params", params);
        request(true, OkHttp.METHOD.POST, context, SysConstants.thirdRegister, header,
                map, "thirdRegister");
    }

//    /**
//     * 登录
//     */
//    public void login(String phone, String password) {
//        final HashMap<String, String> map = new HashMap<>();
//        final HashMap<String, String> header = new HashMap<>();
//        header.put("Content-Type", "application/json;charset=UTF-8");
////        while (phone.indexOf("0") == 0) {
////            phone.substring(1);
////        }
//        map.put("username", phone);
//        map.put("password", password);
//        request(true, OkHttp.METHOD.POST, context, SysConstants.Login, header, map, "login");
//    }


    /**
     * 发送验证码
     */
    public void sendVerficationCode(String info, String event, String code) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        if (StringUtil.isNotNull(code)) {
            map.put("country_mobile", code);
        }
        map.put("mobile", info);
        map.put("event", event);
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        request(true, OkHttp.METHOD.POST, context, SysConstants.sendVerificationCode, header,
                map, "sendVerficationCode");
    }

    /**
     * 获取七牛云token
     */
    public void qiniuToken() {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        request(false, OkHttp.METHOD.POST, context, SysConstants.qiniuToken, header, map, "qiniuToken");
    }

    /**
     * 获取用户基本信息
     */
    public void getUserDetail() {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        request(true, OkHttp.METHOD.POST, context, SysConstants.getUserDetail, header, map, "getUserDetail");
    }

    /**
     * 充币
     */
    public void recharge(String number, String captcha, String voucher, String voucherId) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        map.put("number_apply", number);
        map.put("captcha", captcha);
        if (StringUtil.isNotNull(voucher)) {
            map.put("voucher", voucher);
        }
        map.put("voucher_no", voucherId);
        request(true, OkHttp.METHOD.POST, context, SysConstants.recharge, header, map, "recharge");
    }

    /**
     * 提币
     */
    public void withdrawal(String number, String captcha, String paypwd, String address, String qrcode) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        map.put("number_apply", number);
        map.put("captcha", captcha);
        map.put("paypwd", paypwd);
        if (StringUtil.isNotNull(qrcode)) {
            map.put("qrcode", qrcode);
        }
        map.put("wallet_address", address);
        request(true, OkHttp.METHOD.POST, context, SysConstants.withdrawal, header, map, "withdrawal");
    }

    /**
     * 转币
     */
    public void transfer(String number, String paypwd, String address) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        map.put("number", number);
        map.put("paypwd", paypwd);
        map.put("account", address);
        request(true, OkHttp.METHOD.POST, context, SysConstants.transfer, header, map, "transfer");
    }

    /**
     * 获取用户基本信息
     */
    public void noticeNew() {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        request(true, OkHttp.METHOD.POST, context, SysConstants.noticeNew, header, map, "noticeNew");
    }

    /**
     * 投诉建议
     */
    public void feedback(String content) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        map.put("content", content);
        request(true, OkHttp.METHOD.POST, context, SysConstants.feedback, header, map, "feedback");
    }

    /**
     * 提交量化投资
     */
    public void addInvestment(String money, String day) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        map.put("money", money);
        map.put("day", day);
        request(true, OkHttp.METHOD.POST, context, SysConstants.addInvestment, header, map, "addInvestment");
    }

    /**
     * 获取消息数量
     */
    public void inform(int pagenum, int pagesize) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        map.put("pagenum", String.valueOf(pagenum));
        map.put("pagesize", String.valueOf(pagesize));
        request(true, OkHttp.METHOD.POST, context, SysConstants.inform, header, map, "inform");
    }

    /**
     * 获取卖家列表
     */
    public void getItsSell(int pagenum, int pagesize) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        map.put("pagenum", String.valueOf(pagenum));
        map.put("pagesize", String.valueOf(pagesize));
        request(true, OkHttp.METHOD.POST, context, SysConstants.getItsSell, header, map, "getItsSell");
    }

    /**
     * 获取买家列表
     */
    public void getItsBuy(int pagenum, int pagesize) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        map.put("pagenum", String.valueOf(pagenum));
        map.put("pagesize", String.valueOf(pagesize));
        request(true, OkHttp.METHOD.POST, context, SysConstants.getItsBuy, header, map, "getItsBuy");
    }

    /**
     * 获取社区分享
     */
    public void community() {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        request(true, OkHttp.METHOD.POST, context, SysConstants.community, header, map, "community");
    }

    /**
     * 上传文件
     */
    public void uploadFile(String filePath) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        map.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        map.put("file", "img.jpg");
        request(true, OkHttp.METHOD.POSTFILE, context, SysConstants.uploadFile, header, map,
                "uploadFile", filePath);
    }

    /**
     * 上传身份证
     */
    public void uploadIdCardFile(String filePath) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        map.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        map.put("file", "img.jpg");
        request(true, OkHttp.METHOD.POSTFILE, context, SysConstants.uploadIdCardFile, header, map,
                "uploadIdCardFile", filePath);
    }

    /**
     * 上传文件
     */
    public void uploadHead(String filePath) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        map.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        map.put("file", "img.jpg");
        request(true, OkHttp.METHOD.POSTFILE, context, SysConstants.uploadHead, header, map,
                "uploadHead", filePath);
    }


    /**
     * 微信授权
     *
     * @param url
     */
    public void wechatAuthorize(String url) {
        final HashMap<String, String> header = new HashMap<>();
        HashMap<String, String> map = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        request(OkHttp.METHOD.PATCH, context, url, header, map, "wechatAuthorize");
    }


    /**
     * 购买下单
     */
    public void createOrder(String merchantid, int paytype, String amount, String number) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        map.put("merchantid", merchantid);
        map.put("paytype", String.valueOf(paytype));
        map.put("amount", amount);
        map.put("number", number);
        request(true, OkHttp.METHOD.POST, context, SysConstants.createOrder, header, map, "createOrder");
    }

    /**
     * 出售下单
     */
    public void sellOrder(String merchantid, int paytype, String number, String pwd) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        map.put("buyerid", merchantid);
        map.put("paywayid", String.valueOf(paytype));
        map.put("paypwd", pwd);
        map.put("its", number);
        request(true, OkHttp.METHOD.POST, context, SysConstants.sellOrder, header, map, "sellOrder");
    }

    /**
     * 获取社区分享
     */
    public void paylist() {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        request(true, OkHttp.METHOD.POST, context, SysConstants.paylist, header, map, "paylist");
    }

    /**
     * 支付方式详细
     */
    public void userpayway(String id) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        map.put("id", id);
        request(true, OkHttp.METHOD.POST, context, SysConstants.userpayway, header, map, "userpayway");
    }

    /**
     * 保存付款方式
     */
    public void savepayway(String id, int type, String account, String image, String name, String bankdeposit, String bankbranch) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        map.put("id", id);
        map.put("type", String.valueOf(type));
        map.put("account", account);
        map.put("name", name);
        if (type != 3) {
            if (StringUtil.isNotNull(image)) {
                map.put("image", image);
            }
        } else {
            map.put("bankdeposit", bankdeposit);
            if (StringUtil.isNotNull(bankbranch)) {
                map.put("bankbranch", bankbranch);
            }
        }
        request(true, OkHttp.METHOD.POST, context, SysConstants.savepayway, header, map, "savepayway");
    }

    /**
     * 用户购买订单详情
     */
    public void orderDetails(String id) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        map.put("orderid", id);
        request(true, OkHttp.METHOD.POST, context, SysConstants.orderDetails, header, map, "orderDetails");
    }

    /**
     * 会员出售订单详细
     */
    public void sellDetails(String id) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        map.put("orderid", id);
        request(true, OkHttp.METHOD.POST, context, SysConstants.sellDetails, header, map, "sellDetails");
    }

    /**
     * 用户取消购买订单
     */
    public void cancelOrder(String id) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        map.put("orderid", id);
        request(true, OkHttp.METHOD.POST, context, SysConstants.cancelOrder, header, map, "cancelOrder");
    }

    /**
     * 用户出售订单立即划转
     */
    public void sellTransfer(String id) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        map.put("orderid", id);
        request(true, OkHttp.METHOD.POST, context, SysConstants.sellTransfer, header, map, "sellTransfer");
    }

    /**
     * 买家订单列表
     */
    public void orderList(int pagenum, int pagesize) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        map.put("pagenum", String.valueOf(pagenum));
        map.put("pagesize", String.valueOf(pagesize));
        request(true, OkHttp.METHOD.POST, context, SysConstants.orderList, header, map, "orderList");
    }

    /**
     * 用户出售订单列表
     */
    public void sellLogList(int pagenum, int pagesize) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        map.put("pagenum", String.valueOf(pagenum));
        map.put("pagesize", String.valueOf(pagesize));
        request(true, OkHttp.METHOD.POST, context, SysConstants.sellLogList, header, map, "sellLogList");
    }

    /**
     * 商家支付信息
     */
    public void payinformation(String paytype, String purchaseid) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        map.put("paytype", paytype);
        map.put("purchaseid", purchaseid);
        request(true, OkHttp.METHOD.POST, context, SysConstants.payinformation, header,
                map, "payinformation");
    }


    /**
     * 校验用户电话号码是否已注册
     */
    public void checkPhone(String phone) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        map.put("userPhone", String.valueOf(phone));
        request(true, OkHttp.METHOD.POST, context, SysConstants.checkPhone, header, map, "checkPhone");
    }

    /**
     * 校验用户名是否已注册
     */
    public void checkName(String name) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        map.put("userName", String.valueOf(name));
        request(true, OkHttp.METHOD.POST, context, SysConstants.checkName, header, map, "checkName");
    }

    /**
     * 获取
     */
    public void checkInvite(String invite) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        map.put("inviteId", invite);
        request(true, OkHttp.METHOD.POST, context, SysConstants.checkInvite, header, map, "checkInvite");
    }

    /**
     * 发送短信
     */
    public void getCaptcha(String phone) {
//        final HashMap<String, String> map = new HashMap<>();
//        final HashMap<String, String> header = new HashMap<>();
//        header.put("Content-Type", "application/json;charset=UTF-8");
//        map.put("user_phone", phone);
//        map.put("messageType", "1");
//        request(true, OkHttp.METHOD.POST, context, SysConstants.getCaptcha, header, map, "getCaptcha");

        getCaptcha(phone, "1");
    }

    /**
     * 发送短信
     */
    public void getCaptcha(String phone, String messageType) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        map.put("user_phone", phone);
        map.put("messageType", messageType);
        request(true, OkHttp.METHOD.POST, context, SysConstants.getCaptcha, header, map, "getCaptcha");
    }


    /**
     * 获取
     */
    public void registerTest(String phone, String name, String code, String invita, String password) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        map.put("userPhone", phone);
        map.put("invitePhone", invita);
        map.put("message", code);
        map.put("userPass", password);
//        map.put("userName", name);
        request(true, OkHttp.METHOD.POST, context, SysConstants.registerTest, header, map, "registerTest");
    }

    /**
     * 登录
     */
    public void login(String username, String password) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
//        while (phone.indexOf("0") == 0) {
//            phone.substring(1);
//        }
        map.put("userName", username);
        map.put("userPass", password);
        request(true, OkHttp.METHOD.POST, context, SysConstants.login, header, map, "login");
    }

    public void registerQrCode() {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        request(true, OkHttp.METHOD.GET, context, SysConstants.registerQrCode, header, map, "registerQrCode");
    }

    public void queryMemberInfo() {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        request(false, OkHttp.METHOD.GET, context, SysConstants.queryMemberInfo, header, map, "queryMemberInfo");
    }

    public void queryMoney(String uid) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        String url = String.format(SysConstants.queryMoney, uid);
        request(true, OkHttp.METHOD.GET, context, url, header, map, "queryMoney");
    }

    public void offlineAccount() {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        request(true, OkHttp.METHOD.GET, context, SysConstants.offlineAccount, header, map, "offlineAccount");
    }

    public void offlineSubmit(String name, String bankCode, String amount, String url, String payType) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        map.put("payName", name);
        map.put("payAccount", bankCode);
        map.put("payAmount", amount);
        map.put("imgUrl", url);
        map.put("payType", payType);
        request(true, OkHttp.METHOD.POST, context, SysConstants.offlineSubmit, header, map, "offlineSubmit");
    }

    public void realName(String name, String code, String idcard) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        map.put("name", name);
        map.put("idCard", idcard);
        map.put("message", code);
        request(true, OkHttp.METHOD.POST, context, SysConstants.realName, header, map, "realName");
    }

    public void bindCard(String name, String bank_name, String bank_card, String sub_bank_name) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        map.put("cardName", name);
        map.put("bankName", bank_name);
        map.put("cardNum", bank_card);
        map.put("subBankName", sub_bank_name);
        request(true, OkHttp.METHOD.POST, context, SysConstants.bindCard, header, map, "bindCard");
    }

    public void queryCards() {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        request(true, OkHttp.METHOD.GET, context, SysConstants.queryCards, header, map, "queryCards");
    }

    public void deleteBankCard(String uid) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        String url = String.format(SysConstants.deleteBank, uid);
        request(true, OkHttp.METHOD.DELETE, context, url, header, map, "deleteBankCard");
    }

    public void withdraw(String amount, String pwd, String smsCode) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        map.put("message", smsCode);
        map.put("amount", amount);
        map.put("password", pwd);
        request(true, OkHttp.METHOD.POST, context, SysConstants.withdraw, header, map, "withdraw");
    }

    public void modifPws(String userPhone, String message, String pwd, int type) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        if (type != 0) {
            header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        }
        map.put("userPhone", userPhone);
        map.put("message", message);
        map.put("newUserPass", pwd);
        request(true, OkHttp.METHOD.POST, context, SysConstants.modifPws, header, map, "modifPws");
    }

    public void modifTPws(String userPhone, String message, String pwd) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        map.put("userPhone", userPhone);
        map.put("message", message);
        map.put("newUserPass", pwd);
        request(true, OkHttp.METHOD.POST, context, SysConstants.modifTPws, header, map, "modifTPws");
    }

    public void moneyDetail(int pagenum, int pagesize, int type) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        map.put("pageNo", String.valueOf(pagenum));
        map.put("pageSize", String.valueOf(pagesize));
//        map.put("accountType", String.valueOf(type));
        request(false, OkHttp.METHOD.GET, context, SysConstants.moneyDetail, header, map, "moneyDetail");
    }

    public void offlinePage(int pagenum, int pagesize) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        map.put("page", String.valueOf(pagenum));
        map.put("limit", String.valueOf(pagesize));
        request(true, OkHttp.METHOD.GET, context, SysConstants.offlinePage, header, map, "offlinePage");
    }

    public void rechargePage(int pagenum, int pagesize) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        map.put("page", String.valueOf(pagenum));
        map.put("limit", String.valueOf(pagesize));
        request(true, OkHttp.METHOD.GET, context, SysConstants.queryRechargePage, header, map, "rechargePage");
    }


    public void queryPage(int pagenum, int pagesize) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        map.put("page", String.valueOf(pagenum));
        map.put("limit", String.valueOf(pagesize));
        request(true, OkHttp.METHOD.GET, context, SysConstants.queryPage, header, map, "queryPage");
    }

    public void queryIndexGoods() {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        request(true, OkHttp.METHOD.GET, context, SysConstants.queryIndexGoods, header, map, "queryIndexGoods");
    }

    public void findAddressPage(int pagenum, int pagesize) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        map.put("pageNo", String.valueOf(pagenum));
        map.put("pageSize", String.valueOf(pagesize));
        request(true, OkHttp.METHOD.POST, context, SysConstants.findAddressPage, header, map, "findAddressPage");
    }

    public void deleteAddress(String id) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        map.put("id", id);
        request(true, OkHttp.METHOD.POST, context, SysConstants.deleteAddress, header, map, "deleteAddress");
    }

    public void defaultAddress(String id) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        map.put("id", id);
        request(true, OkHttp.METHOD.POST, context, SysConstants.defaultAddress, header, map, "defaultAddress");
    }

    public void saveOrUpdateAddress(String id, String name, String phone, String province, String city, String area, String address) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        if (StringUtil.isNotNull(id)) {
            map.put("id", id);
        }
        map.put("uid", SharedPreferencesUtil.readString("uid", "0"));
        map.put("userName", name);
        map.put("phone", phone);
        map.put("province", province);
        map.put("city", city);
        if (TextUtils.isEmpty(area)) {
            map.put("area", "");
        } else {
            map.put("area", area);
        }
        map.put("address", address);
        request(true, OkHttp.METHOD.POST, context, SysConstants.saveOrUpdateAddress, header, map, "saveOrUpdateAddress");
    }


    public void queryGoodDetailedById(String id) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        map.put("id", id);
        map.put("uid", SharedPreferencesUtil.readString("uid", "0"));
        request(true, OkHttp.METHOD.GET, context, SysConstants.queryGoodDetailedById, header, map, "queryGoodDetailedById");
    }

    public void goodPurchase(String id, String addressId, String number, String price, String pwd) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        map.put("uid", SharedPreferencesUtil.readString("uid", "0"));
        map.put("goodId", id);
        map.put("addressId", addressId);
        map.put("number", "1");
        map.put("amount", price);
        map.put("passWord", pwd);
//        map.put("totalPrice", String.valueOf(Integer.parseInt(number) * Double.parseDouble(price)));
        request(true, OkHttp.METHOD.POST, context, SysConstants.goodPurchase, header, map, "goodPurchase");
    }


    public void queryGoodsList(int pagenum, int pagesize, Integer type) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        map.put("pageNo", String.valueOf(pagenum));
        map.put("pageSize", String.valueOf(pagesize));
        map.put("type", String.valueOf(type));

        request(true, OkHttp.METHOD.GET, context, SysConstants.queryGoodsList, header, map, "queryGoodsList");
    }

    public void getOrders(int pagenum, int pagesize, Integer status) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        map.put("pageNo", String.valueOf(pagenum));
        map.put("pageSize", String.valueOf(pagesize));
        if (status != null) {
            map.put("status", String.valueOf(status));
        }
        request(true, OkHttp.METHOD.GET, context, SysConstants.getOrders, header, map, "getOrders");
    }

    public void searchOrder(String orderNo) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        map.put("orderNo", orderNo);
//        map.put("uid", SharedPreferencesUtil.readString("uid", "0"));
        request(true, OkHttp.METHOD.GET, context, SysConstants.searchOrder, header, map, "searchOrder");
    }

    public void getScore() {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        request(false, OkHttp.METHOD.GET, context, SysConstants.getScore, header, map, "getScore");
    }

    public void getScoreDetail(int pagenum, int pagesize) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        map.put("pageNo", String.valueOf(pagenum));
        map.put("pageSize", String.valueOf(pagesize));
        request(true, OkHttp.METHOD.GET, context, SysConstants.getScoreDetail, header, map, "getScoreDetail");
    }

    public void modifPayPwd(String userPhone, String message, String pwd) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        map.put("userPhone", userPhone);
        map.put("message", message);
        map.put("payPass", pwd);
        request(true, OkHttp.METHOD.POST, context, SysConstants.addTradePassword, header, map, "modifPayPwd");
    }

    public void getTongji() {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        request(true, OkHttp.METHOD.GET, context, SysConstants.queryMemberExtensionStatistics, header, map, "getTongji");
    }

    public void commission() {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        request(false, OkHttp.METHOD.GET, context, SysConstants.commission, header, map, "commission");
    }


    public void commissionDetail(int pagenum, int pagesize) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        map.put("pageNo", String.valueOf(pagenum));
        map.put("pageSize", String.valueOf(pagesize));
        request(true, OkHttp.METHOD.GET, context, SysConstants.commissionDetail, header, map, "commissionDetail");
    }

    public void getCarouselList() {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        map.put("pageNo", "1");
        map.put("pageSize", "100");
        map.put("type", "2");
//        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        request(true, OkHttp.METHOD.GET, context, SysConstants.getCarouselList, header, map, "getCarouselList");
    }

    public void getNewsList(int pagenum, int pagesize, String topic) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
//        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        map.put("page", String.valueOf(pagenum));
        map.put("limit", String.valueOf(pagesize));
        map.put("topic", topic);
        request(true, OkHttp.METHOD.GET, context, SysConstants.getNewsList, header, map, "getNewsList");
    }

    public void queryNewsById(String id) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
//        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));

        map.put("id", id);
        request(true, OkHttp.METHOD.GET, context, SysConstants.queryNewsById, header, map, "queryNewsById");
    }

    public void resetUserName(String nickname) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        map.put("userName", nickname);
        request(true, OkHttp.METHOD.POST, context, SysConstants.resetUserName, header, map, "resetUserName");
    }

    public void equalToCode(String code) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        map.put("code", code);
        map.put("messageType", "1");
        request(true, OkHttp.METHOD.POST, context, SysConstants.equalToCode, header, map, "equalToCode");
    }

    public void modifPhone(String phone, String code) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        map.put("message", code);
        map.put("newUserPhone", phone);
        request(true, OkHttp.METHOD.POST, context, SysConstants.modifPhone, header, map, "modifPhone");
    }


    public void bonusWithdraw(String amount, String pwd) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        map.put("amount", amount);
        map.put("password", pwd);
        request(true, OkHttp.METHOD.POST, context, SysConstants.bonusWithdraw, header, map, "bonusWithdraw");
    }

    public void alertOrderStatus(String id) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        map.put("orderId", id);
        request(true, OkHttp.METHOD.GET, context, SysConstants.alertOrderStatus, header, map, "alertOrderStatus");
    }

    public void updateShouCang(String id, int status) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        map.put("shopId", id);
        map.put("status", String.valueOf(status));
        request(true, OkHttp.METHOD.POST, context, SysConstants.shopKeep, header, map, "updateShouCang");
    }


    public void queryLowerLevel(int pageNum, int pageSize) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        map.put("pageNo", String.valueOf(pageNum));
        map.put("pageSize", String.valueOf(pageSize));
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        request(true, OkHttp.METHOD.GET, context, SysConstants.queryLowerLevel, header, map, "queryLowerLevel");
    }

    public void queryIndirectLowerLevel(int pageNum, int pageSize) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        map.put("pageNo", String.valueOf(pageNum));
        map.put("pageSize", String.valueOf(pageSize));
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        request(true, OkHttp.METHOD.GET, context, SysConstants.queryIndirectLowerLevel, header, map, "queryIndirectLowerLevel");
    }


    public void getShopList(int pagenum, int pagesize, String provinceCode, String cityCode, String areaCode, String fullName) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        map.put("pageNo", String.valueOf(pagenum));
        map.put("pageSize", String.valueOf(pagesize));
        if (StringUtil.isNotNull(provinceCode)) {
            map.put("provinceCode", provinceCode);
        }
        if (StringUtil.isNotNull(cityCode)) {
            map.put("cityCode", cityCode);
        }
        if (StringUtil.isNotNull(areaCode)) {
            map.put("areaCode", areaCode);
        }
        if (StringUtil.isNotNull(fullName)) {
            map.put("fullName", fullName);
        }
        request(true, OkHttp.METHOD.POST, context, SysConstants.getShopList, header, map, "getShopList");
    }

    public void getAccount(String asset) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        map.put("asset", asset);
        request(true, OkHttp.METHOD.GET, context, SysConstants.getAccount, header, map, "getAccount");
    }

    public void getShopDetail(String id) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        map.put("shopId", id);
        request(true, OkHttp.METHOD.GET, context, SysConstants.getShopDetail, header, map, "getShopDetail");
    }

    public void shopPurchase(String id, String num, String amount, String pwd) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        map.put("shopId", id);
        map.put("number", num);
        map.put("amount", amount);
        map.put("passWord", pwd);
        map.put("uid", SharedPreferencesUtil.readString("uid", ""));
        request(true, OkHttp.METHOD.POST, context, SysConstants.shopPurchase, header, map, "shopPurchase");
    }

    public void getAccountDetail(int pageNum, int pageSize, String asset) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        map.put("pageNo", String.valueOf(pageNum));
        map.put("pageSize", String.valueOf(pageSize));
        map.put("asset", asset);
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        request(true, OkHttp.METHOD.GET, context, SysConstants.getAccountDetail, header, map, "getAccountDetail");
    }

    public void getKeepShopList(int pagenum, int pagesize) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        map.put("pageNo", String.valueOf(pagenum));
        map.put("pageSize", String.valueOf(pagesize));
        request(true, OkHttp.METHOD.POST, context, SysConstants.getKeepShopList, header, map, "getKeepShopList");
    }

    public void getProvinces() {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        request(true, OkHttp.METHOD.GET, context, SysConstants.getProvinces, header, map, "getProvinces");
    }

    public void getCities(String id) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        map.put("parentId", id);
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        request(true, OkHttp.METHOD.GET, context, SysConstants.getCities, header, map, "getCities");
    }


    public void getAreas(String id) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        map.put("parentId", id);
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        request(true, OkHttp.METHOD.GET, context, SysConstants.getAreas, header, map, "getAreas");
    }

    public void querySubBank(String bankId, String keyWord) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        map.put("bank_id", bankId);
        map.put("bank_key_words", keyWord);
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        request(true, OkHttp.METHOD.GET, context, SysConstants.queryBank, header, map, "querySubBank");
    }

    public void sendValidateCode(String cert_no, String phone, String corp_name, String bank_acct) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        map.put("cert_no", cert_no);
        map.put("phone", phone);
        map.put("corp_name", corp_name);
        map.put("bank_acct", bank_acct);
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        request(true, OkHttp.METHOD.POST, context, SysConstants.sendValidateCode, header, map, "sendValidateCode");
    }

    public void checkUserAccount() {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        request(true, OkHttp.METHOD.GET, context, SysConstants.checkUserAccount, header, map, "checkUserAccount");
    }

    public void validateAndOpenAcct(String cert_no, String phone, String corp_name, String bank_acct, String cert_front, String cert_back, String acct_name
            , String bank_no, String password, String sms_code, String bank_id, String bank_name) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        map.put("cert_no", cert_no);
        map.put("phone", phone);
        map.put("corp_name", corp_name);
        map.put("bank_acct", bank_acct);
        map.put("cert_front", cert_front);
        map.put("cert_back", cert_back);
        map.put("acct_name", acct_name);
        map.put("bank_no", bank_no);
        if (SysConstants.IS_DEBUG) {
            map.put("password", password);
        }
        map.put("sms_code", sms_code);
        map.put("bank_id", bank_id);
        map.put("bank_name", bank_name);
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        request(true, OkHttp.METHOD.POST, context, SysConstants.validateAndOpenAcct, header, map, "validateAndOpenAcct");
    }

    /**
     * 查询绑卡信息
     */
    public void openAcctResult() {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        request(true, OkHttp.METHOD.GET, context, SysConstants.openAcctResult, header, map, "openAcctResult");
    }

    /**
     * 查询可用余额
     */
    public void getUserMoneyInfo(String uid) {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
//        map.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        request(false, OkHttp.METHOD.GET, context, SysConstants.getUserMoneyInfo(uid), header, map, "getUserMoneyInfo");
    }

    /**
     * 查询可用余额
     */
    public void getUserBankInfo() {
        final HashMap<String, String> map = new HashMap<>();
        final HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("token", SharedPreferencesUtil.readString(SysConstants.TOKEN, ""));
        request(true, OkHttp.METHOD.GET, context, SysConstants.getUserBankInfo, header, map, "getUserBankInfo");
    }

    @Override
    public void onSuccess(String url, JSON response, String tag) {
        super.onSuccess(url, response, tag);
        if (SysConstants.IS_DEBUG) {
            Log.e("http", "url=" + url);
            Log.e("http", "response=" + response.toString());
        }
        switch (tag) {
            case "getUserBankInfo":
                if (context instanceof Withdrawal2Activity) {
                    ((Withdrawal2Activity) context).getUserBankInfoSuccess((JSONObject) response);
                }
                break;
            case "getUserMoneyInfo":
                if (context instanceof Withdrawal2Activity) {
                    ((Withdrawal2Activity) context).getUserMoneyInfoSuccess((JSONObject) response);
                } else if (context instanceof PersonCenterActivity) {
                    ((PersonCenterActivity) context).getUserMoneyInfoSuccess((JSONObject) response);
                }
                break;
            case "openAcctResult":
                if (context instanceof AuthAccountInfoActivity) {
                    ((AuthAccountInfoActivity) context).openAcctResultAcctSuccess((JSONObject) response);
                } else if (context instanceof Recharge2Activity) {
                    ((Recharge2Activity) context).openAcctResultAcctSuccess((JSONObject) response);
                }
                break;
            case "validateAndOpenAcct":
                if (context instanceof AuthNameBindCardActivity) {
                    ((AuthNameBindCardActivity) context).validateAndOpenAcctSuccess((JSONObject) response);
                }
                break;
            case "getCountry":
                if (context instanceof CountrySelectActivity) {
                    ((CountrySelectActivity) context).getCountrySuccess(JSON.parseArray(JSON.toJSONString(((JSONObject) response)
                            .getJSONArray("data")), Country.class));
                }
                break;
            case "getBank":
                if (context instanceof AuthNameBindCardActivity) {
                    ((AuthNameBindCardActivity) context).getBankSuccess(JSON.parseArray(JSON.toJSONString(((JSONObject) response)
                            .getJSONArray("result")), BankBrief.class));
                }
                break;
            case "checkUserAccount":
                if (context instanceof AuthNameBindCardActivity) {
                    ((AuthNameBindCardActivity) context).checkUserAccountSuccess((JSONObject) response);
                } else if (context instanceof RegisterOrLoginActivity) {
                    ((RegisterOrLoginActivity) context).checkUserAccountSuccess((JSONObject) response);
                } else if (context instanceof HomeActivity) {
                    ((HomeActivity) context).checkUserAccountSuccess((JSONObject) response);
                }
                break;
            case "sendValidateCode":
                if (context instanceof AuthNameBindCardActivity) {
                    ((AuthNameBindCardActivity) context).sendValidateCodeSuccess((JSONObject) response);
                }
                break;
            case "querySubBank":
                if (context instanceof AuthNameBindCardActivity) {
                    ((AuthNameBindCardActivity) context).querySubBankSuccess(JSON.parseArray(JSON.toJSONString(((JSONObject) response)
                            .getJSONObject("result").getJSONArray("result_list")), BankSub.class));
                }
                break;
            case "sendVerficationCode":
                if (context instanceof LoginActivity) {
                    ((LoginActivity) context).registerSendSucess();
                } else if (context instanceof ModifyPayPasswordActivity) {
                    ((ModifyPayPasswordActivity) context).sendVerificationCodeSuccess();
                } else if (context instanceof ChongbiActivity) {
                    ((ChongbiActivity) context).sendVerificationCodeSuccess();
                } else if (context instanceof TibiActivity) {
                    ((TibiActivity) context).sendVerificationCodeSuccess();
                }
                break;
            case "register":
                if (context instanceof LoginActivity) {
                    ((LoginActivity) context).registerSuccess(((JSONObject) response).getJSONObject("data")
                            .getJSONObject("userinfo"));
                }
                break;
            case "login":
                if (context instanceof LoginActivity) {
                    ((LoginActivity) context).loginSuccess(((JSONObject) response).getJSONObject("data")
                            .getJSONObject("userinfo"));
                } else if (context instanceof RegisterOrLoginActivity) {
                    ((RegisterOrLoginActivity) context).loginSuccess((JSONObject) response);
                }
                break;
            case "editPassword":
                if (context instanceof ModifyPasswordActivity) {
                    ((ModifyPasswordActivity) context).modifySuccess();
                }
                break;
            case "editPayPassword":
                if (context instanceof ModifyPayPasswordActivity) {
                    ((ModifyPayPasswordActivity) context).modifySuccess();
                }
                break;
            case "feedback":
                if (context instanceof FeedbackActivity) {
                    ((FeedbackActivity) context).feedbackSuccess();
                }
                break;
            case "profile":
                if (context instanceof PersonDetailActivity) {
                    ((PersonDetailActivity) context).modifySuccess();
                }
                break;
            case "getUserDetail":
                if (pager instanceof HomePager) {
//                    ((HomePager) pager).getUserInfoSuccess(JSONObject.parseObject(
//                            JSONObject.toJSONString(((JSONObject) response).getJSONObject("data")), User.class));
                } else if (context instanceof TibiActivity) {
                    ((TibiActivity) context).getUserInfoSuccess(JSONObject.parseObject(
                            JSONObject.toJSONString(((JSONObject) response).getJSONObject("data")), User.class));
                } else if (pager instanceof PersonalCenterPager) {
                    ((PersonalCenterPager) pager).getUserInfoSuccess(JSONObject.parseObject(
                            JSONObject.toJSONString(((JSONObject) response).getJSONObject("data")), User.class));
                }
                break;
//            case "noticeNew":
//                if (pager instanceof HomePager) {
//                    ((HomePager) pager).getNoticeNewSuccess(((JSONObject) response).getJSONObject("data").getString("content"));
//                }
//                break;
            case "shareUrl":
                if (pager instanceof SharePager) {
                    ((SharePager) pager).shareUrlSuccess(((JSONObject) response).getJSONObject("data").getString("url"),
                            ((JSONObject) response).getJSONObject("data").getString("invitation"));
                }
                break;
            case "addInvestmentList":
                if (pager instanceof QuantificationPager) {
                    ((QuantificationPager) pager).getInvestmentListSuccess((JSONObject) response);
                }
                break;
            case "rechargeindex":
                if (context instanceof ChongbiActivity) {
                    ((ChongbiActivity) context).rechargeindexSuccess(((JSONObject) response).getJSONObject("data"));
                } else if (context instanceof TibiActivity) {
                    ((TibiActivity) context).rechargeindexSuccess(((JSONObject) response).getJSONObject("data"));
                } else if (context instanceof ZhuanbiActivity) {
                    ((ZhuanbiActivity) context).rechargeindexSuccess(((JSONObject) response).getJSONObject("data"));
                } else if (context instanceof ShoubiActivity) {
                    ((ShoubiActivity) context).rechargeindexSuccess(((JSONObject) response).getJSONObject("data"));
                }
                break;
            case "uploadIdCardFile":
                if (context instanceof AuthNameBindCardActivity) {

                    ((AuthNameBindCardActivity) context).uploadIdCardFileSuccess(((JSONObject) response).getJSONObject("result")
                            .getString("image_path"));
                }
                break;
            case "uploadFile":
                if (context instanceof ChongbiActivity) {
                    ((ChongbiActivity) context).uploadFileSuccess(((JSONObject) response).getJSONObject("data")
                            .getString("url"));
                } else if (context instanceof TibiActivity) {
                    ((TibiActivity) context).uploadFileSuccess(((JSONObject) response).getJSONObject("data")
                            .getString("url"));
                } else if (context instanceof AddAlipayActivity) {
                    ((AddAlipayActivity) context).uploadFileSuccess(((JSONObject) response).getJSONObject("data")
                            .getString("url"));
                } else if (context instanceof AddWechatActivity) {
                    ((AddWechatActivity) context).uploadFileSuccess(((JSONObject) response).getJSONObject("data")
                            .getString("url"));
                } else if (context instanceof RechargeActivity) {
                    ((RechargeActivity) context).uploadFileSuccess(((JSONObject) response).getJSONObject("data")
                            .getString("slt"));
                }
                break;
            case "recharge":
                if (context instanceof ChongbiActivity) {
                    ((ChongbiActivity) context).commitSuccess();
                }
                break;
            case "addInvestment":
                ToastUtil.showToast("提交成功");
                break;
            case "rechargeList":
                if (context instanceof ChongbiDetailActivity) {
                    ((ChongbiDetailActivity) context).rechargeListSuccess(JSONArray.parseArray(
                            JSONArray.toJSONString(((JSONObject) response).getJSONArray("data")), Chongbi.class)
                    );
                }
                break;
            case "inform":
                if (context instanceof MessageActivity) {
                    ((MessageActivity) context).informSuccess(
                            JSONArray.parseArray(JSONArray.toJSONString(((
                                    JSONObject) response).getJSONArray("data")), Message.class));
                }
                break;
            case "withdrawal":
                if (context instanceof TibiActivity) {
                    ((TibiActivity) context).commitSuccess();
                }
                break;
            case "withdrawalList":
                if (context instanceof TibiDetailActivity) {
                    ((TibiDetailActivity) context).rechargeListSuccess(JSONArray.parseArray(
                            JSONArray.toJSONString(((JSONObject) response).getJSONArray("data")), Chongbi.class)
                    );
                }
                break;
            case "transfer":
                if (context instanceof ZhuanbiActivity) {
                    ((ZhuanbiActivity) context).commitSuccess();
                }
                break;
            case "transferList":
                if (context instanceof ZhuanbiDetailActivity) {
                    ((ZhuanbiDetailActivity) context).transferListSuccess(JSONArray.parseArray(
                            JSONArray.toJSONString(((JSONObject) response).getJSONArray("data")), Shoubi.class)
                    );
                }
                break;
            case "collectList":
                if (context instanceof ShoubiDetailActivity) {
                    ((ShoubiDetailActivity) context).collectListSuccess(JSONArray.parseArray(
                            JSONArray.toJSONString(((JSONObject) response).getJSONArray("data")), Shoubi.class)
                    );
                }
                break;
            case "quantificationWin":
                if (context instanceof QuantificationWinActivity) {
                    ((QuantificationWinActivity) context).quantificationWinSuccess(JSONArray.parseArray(
                            JSONArray.toJSONString(((JSONObject) response).getJSONArray("data")), QuantificationWin.class)
                    );
                }
                break;
            case "shareWin":
                if (context instanceof ShareWinActivity) {
                    ((ShareWinActivity) context).shareWinSuccess(JSONArray.parseArray(
                            JSONArray.toJSONString(((JSONObject) response).getJSONArray("data")), QuantificationWin.class)
                    );
                }
                break;
            case "investmentList":
                if (context instanceof QuantificationDetailActivity) {
                    ((QuantificationDetailActivity) context).investmentListSuccess(JSONArray.parseArray(
                            JSONArray.toJSONString(((JSONObject) response).getJSONArray("data")), Quantification.class));
                }
                break;
            case "community":
                if (context instanceof CommunityActivity) {
                    ((CommunityActivity) context).communitySuccess(((JSONObject) response).getJSONObject("data"));
                }
                break;
//            case "checkVersion":
//                if (pager instanceof HomePager) {
//                    ((HomePager) pager).checkVersionSuccess(((JSONObject) response).getJSONObject("data"));
//                }
//                break;
            case "resetpwd":
                if (context instanceof LoginActivity) {
                    ((LoginActivity) context).resetPwdSuccess();
                }
                break;
            case "getItsSell":
            case "getItsBuy":
                if (context instanceof ITSActivity) {
                    ((ITSActivity) context).getDataSuccess(JSONArray.parseArray(
                            JSONArray.toJSONString(((JSONObject) response).getJSONArray("data")), ITS.class));
                }
                break;
            case "createOrder":
                if (context instanceof ITSActivity) {
                    ((ITSActivity) context).createOrderSuccess(((JSONObject) response).getJSONObject("data").getString("id"));
                }
                break;
            case "paylist":
                if (context instanceof ITSActivity) {
                    ((ITSActivity) context).paylistSuccess(((JSONObject) response).getJSONObject("data")
                            .getJSONArray("paylist"), Double.parseDouble(((JSONObject) response)
                            .getJSONObject("data").getString("userits")));
                }
                break;
            case "sellOrder":
                if (context instanceof ITSActivity) {
                    ((ITSActivity) context).sellOrderSuccess(((JSONObject) response).getJSONObject("data").getString("id"));
                }
                break;
            case "userpayway":
                if (context instanceof AddCardActivity) {
                    ((AddCardActivity) context).userpaywaySuccess(JSONObject.parseObject(
                            JSONObject.toJSONString(((JSONObject) response).getJSONObject("data")), PayWay.class));
                } else if (context instanceof AddAlipayActivity) {
                    ((AddAlipayActivity) context).userpaywaySuccess(JSONObject.parseObject(
                            JSONObject.toJSONString(((JSONObject) response).getJSONObject("data")), PayWay.class));
                } else if (context instanceof AddWechatActivity) {
                    ((AddWechatActivity) context).userpaywaySuccess(JSONObject.parseObject(
                            JSONObject.toJSONString(((JSONObject) response).getJSONObject("data")), PayWay.class));
                }
                break;
            case "savepayway":
                if (context instanceof AddCardActivity) {
                    ((AddCardActivity) context).modifySuccess(
                            ((JSONObject) response).getJSONObject("data").getIntValue("id"));
                } else if (context instanceof AddAlipayActivity) {
                    ((AddAlipayActivity) context).modifySuccess(
                            ((JSONObject) response).getJSONObject("data").getIntValue("id"));
                } else if (context instanceof AddWechatActivity) {
                    ((AddWechatActivity) context).modifySuccess(
                            ((JSONObject) response).getJSONObject("data").getIntValue("id"));
                }
                break;
            case "orderDetails":
                if (context instanceof BuyDetailActivity) {
                    ((BuyDetailActivity) context).getOrderDetailSuccess(JSONObject.parseObject(
                            JSONObject.toJSONString(((JSONObject) response).getJSONObject("data")), OrderDetail.class));
                }
                break;
            case "cancelOrder":
                if (context instanceof BuyDetailActivity) {
                    ((BuyDetailActivity) context).cancelOrderSuccess();
                }
                break;
            case "sellDetails":
                if (context instanceof SellDetailActivity) {
                    ((SellDetailActivity) context).getSellDetailSuccess(JSONObject.parseObject(
                            JSONObject.toJSONString(((JSONObject) response).getJSONObject("data")), SellDetail.class));
                }
                break;
            case "sellTransfer":
                if (context instanceof SellDetailActivity) {
                    ((SellDetailActivity) context).sellTransferSuccess();
                }
                break;
            case "orderList":
            case "sellLogList":
                if (context instanceof ITSLogActivity) {
                    ((ITSLogActivity) context).getDataSuccess(JSONArray.parseArray(
                            JSONArray.toJSONString(((JSONObject) response).getJSONArray("data")), OrderDetail.class));
                }
                break;
            case "payinformation":
                if (context instanceof PayDetailActivity) {
                    ((PayDetailActivity) context).payinformationSuccess(JSONObject.parseObject(
                            JSONObject.toJSONString(((JSONObject) response).getJSONObject("data")), Payinformation.class));
                }
                break;
            case "checkPhone":
                if (context instanceof RegisterOrLoginActivity) {
                    ((RegisterOrLoginActivity) context).checkPhoneSuccess((JSONObject) response);
                } else if (context instanceof ForgetPwdActivity) {
                    ((ForgetPwdActivity) context).checkPhoneSuccess((JSONObject) response);
                } else if (context instanceof UpdatePhoneTwoActivity) {
                    ((UpdatePhoneTwoActivity) context).checkPhoneSuccess((JSONObject) response);
                } else if (context instanceof ForgetPayPwdActivity) {
                    ((ForgetPayPwdActivity) context).checkPhoneSuccess((JSONObject) response);
                }
                break;
            case "checkName":
                if (context instanceof RegisterOrLoginActivity) {
                    ((RegisterOrLoginActivity) context).checkNameSuccess((JSONObject) response);
                } else if (context instanceof NickNameActivity) {
                    ((NickNameActivity) context).checkNameSuccess((JSONObject) response);
                }
                break;
            case "checkInvite":
                if (context instanceof RegisterOrLoginActivity) {
                    ((RegisterOrLoginActivity) context).checkInviteSuccess((JSONObject) response);
                }
                break;
            case "getCaptcha":
                if (context instanceof RegisterOrLoginActivity) {
                    ((RegisterOrLoginActivity) context).getCaptchaSuccess((JSONObject) response);
                } else if (context instanceof RealNameActivity) {
                    ((RealNameActivity) context).getCaptchaSuccess((JSONObject) response);
                } else if (context instanceof ForgetPwdActivity) {
                    ((ForgetPwdActivity) context).getCaptchaSuccess((JSONObject) response);
                } else if (context instanceof ForgetPayPwdActivity) {
                    ((ForgetPayPwdActivity) context).getCaptchaSuccess((JSONObject) response);
                } else if (context instanceof UpdatePhoneOneActivity) {
                    ((UpdatePhoneOneActivity) context).getCaptchaSuccess((JSONObject) response);
                } else if (context instanceof UpdatePhoneTwoActivity) {
                    ((UpdatePhoneTwoActivity) context).getCaptchaSuccess((JSONObject) response);
                } else if (context instanceof Withdrawal2Activity) {
                    ((Withdrawal2Activity) context).getCaptchaSuccess((JSONObject) response);
                }
                break;
            case "registerTest":
                if (context instanceof RegisterOrLoginActivity) {
                    ((RegisterOrLoginActivity) context).modifySuccess((JSONObject) response);
                }
                break;
            case "registerQrCode":
                if (context instanceof MyExtensionActivity) {
                    ((MyExtensionActivity) context).getQrcodeSuccess((JSONObject) response);
                } else if (context instanceof PersonCenterActivity) {
                    ((PersonCenterActivity) context).getQrcodeSuccess((JSONObject) response);
                } else if (context instanceof MyExtension2Activity) {
                    ((MyExtension2Activity) context).getQrcodeSuccess((JSONObject) response);

                }
                break;
            case "queryMemberInfo":
                if (context instanceof PersonCenterActivity) {
                    ((PersonCenterActivity) context).getUserSuccess((JSONObject) response);
                } else if (context instanceof MyRecommenderActivity) {
                    ((MyRecommenderActivity) context).getUserSuccess((JSONObject) response);
                }
                break;
            case "queryMoney":
                if (context instanceof MyMoneyActivity) {
                    ((MyMoneyActivity) context).getMoneySuccess((JSONObject) response);
                }
                break;
            case "offlineAccount":
                if (context instanceof RechargeActivity) {
                    ((RechargeActivity) context).offlineAccountSuccess((JSONObject) response);
                }
                break;
            case "offlineSubmit":
                if (context instanceof RechargeActivity) {
                    ((RechargeActivity) context).offlineSubmitSuccess((JSONObject) response);
                }
                break;
            case "realName":
                if (context instanceof RealNameActivity) {
                    ((RealNameActivity) context).realNameSuccess((JSONObject) response);
                }
                break;
            case "bindCard":
                if (context instanceof BindCardActivity) {
                    ((BindCardActivity) context).bindCardSuccess((JSONObject) response);
                }
                break;
            case "queryCards":
                if (context instanceof BindCardActivity) {
                    ((BindCardActivity) context).queryCardsSuccess((JSONObject) response);
                } else if (context instanceof WithDrawalActivity) {
                    ((WithDrawalActivity) context).queryCardsSuccess((JSONObject) response);
                }
                break;
            case "deleteBankCard":
                if (context instanceof BindCardActivity) {
                    ((BindCardActivity) context).deleteBankSuccess((JSONObject) response);
                }
                break;
            case "withdraw":
                if (context instanceof WithDrawalActivity) {
                    ((WithDrawalActivity) context).withdrawSuccess((JSONObject) response);
                } else if (context instanceof Withdrawal2Activity) {
                    ((Withdrawal2Activity) context).withdrawSuccess((JSONObject) response);
                }
                break;
            case "modifPws":
                if (context instanceof ForgetPwdActivity) {
                    ((ForgetPwdActivity) context).forgetPwdSuccess((JSONObject) response);
                }
                break;
            case "modifTPws":
                if (context instanceof ForgetPayPwdActivity) {
                    ((ForgetPayPwdActivity) context).modifTPwsSuccess((JSONObject) response);
                }
                break;
            case "moneyDetail":
                if (context instanceof MyMoneyActivity) {
                    ((MyMoneyActivity) context).moneyDetailSuccess((JSONObject) response);
                }
                break;
            case "offlinePage":
                if (context instanceof MoneyHistoryLogActivity) {
//                    ((MoneyHistoryLogActivity) context).offlinePageSuccess((JSONObject) response);
                }
                break;
            case "rechargePage":
                if (context instanceof MoneyHistoryLogActivity) {
                    ((MoneyHistoryLogActivity) context).rechargePageSuccess((JSONObject) response);
                }
                break;
            case "queryPage":
                if (context instanceof MoneyHistoryLogActivity) {
                    ((MoneyHistoryLogActivity) context).queryPageSuccess((JSONObject) response);
                }
                break;
            case "queryIndexGoods":
                if (context instanceof HomeActivity) {
                    ((HomeActivity) context).queryIndexGoodsSuccess((JSONObject) response);
                }
                break;
            case "findAddressPage":
                if (context instanceof AddressActivity) {
                    ((AddressActivity) context).findAddressPageSuccess((JSONObject) response);
                } else if (context instanceof PlaceOrderActivity) {
                    ((PlaceOrderActivity) context).findAddressPageSuccess((JSONObject) response);
                }
                break;
            case "deleteAddress":
                if (context instanceof AddressActivity) {
                    ((AddressActivity) context).deleteAddressSuccess((JSONObject) response);
                }
                break;
            case "saveOrUpdateAddress":
                if (context instanceof AddressActivity) {
                    ((AddressActivity) context).saveOrUpdateAddressSuccess((JSONObject) response);
                } else if (context instanceof AddUpdateAddressActivity) {
                    ((AddUpdateAddressActivity) context).saveOrUpdateAddressSuccess((JSONObject) response);
                }
                break;
            case "defaultAddress":
                if (context instanceof AddressActivity) {
                    ((AddressActivity) context).changeSuccess((JSONObject) response);
                }
                break;
            case "queryGoodDetailedById":
                if (context instanceof GoodsDetailActivity) {
                    ((GoodsDetailActivity) context).queryGoodDetailedByIdSuccess((JSONObject) response);
                }
                break;
            case "goodPurchase":
                if (context instanceof GoodsDetailActivity) {
                    ((GoodsDetailActivity) context).goodPurchaseSuccess((JSONObject) response);
                } else if (context instanceof PlaceOrderActivity) {
                    ((PlaceOrderActivity) context).goodPurchaseSuccess((JSONObject) response);
                }
                break;
            case "queryGoodsList":
                if (context instanceof GoodsOnSellActivity) {
                    ((GoodsOnSellActivity) context).queryGoodsListSuccess((JSONObject) response);
                }
                break;
            case "getOrders":
                if (context instanceof OrderActivity) {
                    ((OrderActivity) context).getOrdersSuccess((JSONObject) response);
                } else if (context instanceof OrderFahuoActivity) {
                    ((OrderFahuoActivity) context).getOrdersSuccess((JSONObject) response);
                }
                break;
            case "searchOrder":
                if (context instanceof OrderActivity) {
                    ((OrderActivity) context).searchOrderSuccess((JSONObject) response);
                } else if (context instanceof OrderFahuoActivity) {
                    ((OrderFahuoActivity) context).searchOrderSuccess((JSONObject) response);
                }
                break;
            case "getScore":
                if (context instanceof ScoreActivity) {
                    ((ScoreActivity) context).getScoreSuccess((JSONObject) response);
                }
                break;
            case "getScoreDetail":
                if (context instanceof ScoreActivity) {
                    ((ScoreActivity) context).getScoreDetailSuccess((JSONObject) response);
                }
                break;
            case "getShopList":
                if (context instanceof ShopActivity) {
                    ((ShopActivity) context).getShopListSuccess((JSONObject) response);
                }
                break;
            case "getKeepShopList":
                if (context instanceof ShouCangActivity) {
                    ((ShouCangActivity) context).getShopListSuccess((JSONObject) response);
                }
                break;
            case "modifPayPwd":
                if (context instanceof ForgetPayPwdActivity) {
//                    ((ForgetPayPwdActivity) context).forgetPayPwdSuccess((JSONObject) response);
                }
                break;
            case "getTongji":
                if (context instanceof MyExtensionActivity) {
                    ((MyExtensionActivity) context).getTongjiSuccess((JSONObject) response);
                }
                break;
            case "commissionDetail":
                if (context instanceof CommissionActivity) {
                    ((CommissionActivity) context).getCommissionSuccess((JSONObject) response);
                }
                break;
            case "getCarouselList":
                if (context instanceof HomeActivity) {
                    ((HomeActivity) context).getCarouselListSuccess((JSONObject) response);
                }
                break;
            case "getNewsList":
                if (context instanceof HomeActivity) {
                    ((HomeActivity) context).getNewsListSuccess((JSONObject) response);
                } else if (context instanceof NewsActivity) {
                    ((NewsActivity) context).getNewsListSuccess((JSONObject) response);
                } else if (context instanceof GoodIntroduceActivity) {
                    ((GoodIntroduceActivity) context).getNewsListSuccess((JSONObject) response);
                }
                break;
            case "queryNewsById":
                if (context instanceof NewsDatailActivity) {
                    ((NewsDatailActivity) context).queryNewsByIdSuccess((JSONObject) response);
                }
                break;
            case "uploadHead":
                if (context instanceof PersonCenterActivity) {
                    ((PersonCenterActivity) context).uploadFileSuccess(((JSONObject) response).getString("data"));
                }
                break;
            case "resetUserName":
                if (context instanceof NickNameActivity) {
                    ((NickNameActivity) context).resetUserNameSuccess(((JSONObject) response));
                }
                break;
            case "equalToCode":
                if (context instanceof UpdatePhoneOneActivity) {
                    ((UpdatePhoneOneActivity) context).equalToCodeSuccess(((JSONObject) response));
                }
                break;
            case "modifPhone":
                if (context instanceof UpdatePhoneTwoActivity) {
                    ((UpdatePhoneTwoActivity) context).modifPhoneSuccess(((JSONObject) response));
                }
                break;
            case "commission":
                if (context instanceof MyExtensionActivity) {
                    ((MyExtensionActivity) context).getCommissionSuccess(((JSONObject) response));
                }
                break;
            case "bonusWithdraw":
                if (context instanceof BonusWithDrawalActivity) {
                    ((BonusWithDrawalActivity) context).withdrawSuccess(((JSONObject) response));
                }
                break;
            case "alertOrderStatus":
                if (context instanceof OrderQueryActivity) {
                    ((OrderQueryActivity) context).alertOrderStatusSuccess(((JSONObject) response));
                } else if (context instanceof OrderFahuoActivity) {
                    ((OrderFahuoActivity) context).alertOrderStatusSuccess(((JSONObject) response));
                }
                break;
            case "queryLowerLevel":
                if (context instanceof MyExtensionActivity) {
                    ((MyExtensionActivity) context).getLowerSuccess(((JSONObject) response));
                }
                break;
            case "queryIndirectLowerLevel":
                if (context instanceof MyExtensionActivity) {
                    ((MyExtensionActivity) context).getIndirectLowerSuccess(((JSONObject) response));
                }
                break;
            case "updateShouCang":
                if (context instanceof ShopActivity) {
                    ((ShopActivity) context).updateShouCangSuccess(((JSONObject) response));
                } else if (context instanceof ShouCangActivity) {
                    ((ShouCangActivity) context).updateShouCangSuccess(((JSONObject) response));
                }
                break;
            case "getAccount":
                if (context instanceof ShopPayActivity) {
                    ((ShopPayActivity) context).getAccountSuccess(((JSONObject) response));
                } else if (context instanceof ZijinActivity) {
                    ((ZijinActivity) context).getAccountSuccess(((JSONObject) response));
                }
                break;
            case "getShopDetail":
                if (context instanceof ShopPayActivity) {
                    ((ShopPayActivity) context).getShopDetailSuccess(((JSONObject) response));
                }
                break;
            case "shopPurchase":
                if (context instanceof ShopPay2Activity) {
                    ((ShopPay2Activity) context).shopPurchaseSuccess(((JSONObject) response));
                }
                break;
            case "getAccountDetail":
                if (context instanceof ZijinActivity) {
                    ((ZijinActivity) context).getAccountDetailSuccess(((JSONObject) response));
                }
                break;
            case "getProvinces":
                if (context instanceof ShopActivity) {
                    ((ShopActivity) context).getProvincesSuccess(((JSONObject) response));
                } else if (context instanceof AddUpdateAddressActivity) {
                    ((AddUpdateAddressActivity) context).getProvincesSuccess(((JSONObject) response));
                }
                break;
            case "getCities":
                if (context instanceof ShopActivity) {
                    ((ShopActivity) context).getCitiesSuccess(((JSONObject) response));
                } else if (context instanceof AddUpdateAddressActivity) {
                    ((AddUpdateAddressActivity) context).getCitiesSuccess(((JSONObject) response));
                }
                break;
            case "getAreas":
                if (context instanceof ShopActivity) {
                    ((ShopActivity) context).getAreasSuccess(((JSONObject) response));
                } else if (context instanceof AddUpdateAddressActivity) {
                    ((AddUpdateAddressActivity) context).getAreasSuccess(((JSONObject) response));
                }
                break;
            default:
                break;
        }
        if (baseActivity != null) {
            baseActivity.dismissProgressDialog();
        }

    }

    @Override
    protected void onResult(String url, String result, String tag) {
        super.onResult(url, result, tag);
    }

    @Override
    public void onqueryError(int statuscode, int code, String message, String tag) {
        super.onqueryError(statuscode, code, message, tag);
        switch (tag) {
            case "paylist":
                if (context instanceof ITSActivity && code == 402) {
                    ((ITSActivity) context).toPerson();
                }
            default:
                break;
        }
        baseActivity.dismissProgressDialog();
    }

    @Override
    public void onFail(String url, Object error, String tag) {
        Log.e("http", "url" + url + "-----error=" + error);
        super.onFail(url, error, tag);

        switch (tag) {
            default:
                break;
        }
    }

    @Override
    public void onEnd(String url, String tag) {
        super.onEnd(url, tag);
    }

}
