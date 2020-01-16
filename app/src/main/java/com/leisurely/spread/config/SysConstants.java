package com.leisurely.spread.config;

import android.os.Environment;

public class SysConstants {

    public static final String VERSION = "1.0";

    public static final String ISLOGIN = "islogin";
    public static final String USERID = "userId";
    public static final String IS_RISK_AUTH = "isRiskAuth";//是否实名
    public static final String TELLPHONE = "tellphone";
    public static final String AVATAR = "avatar";
    public static final String TOKEN = "token";
    public static final String CHATTYPE = "chatType";
    public static final String PAYMETHOD = "payMethod";
    public static final String WECHAT_TOKEN = "wechatToken";
    public static final String WECHAT_REFRESH_TOKNE = "wechatRefreshToken";
    public static final String CLIENTID = "Clientid";

    public static final boolean IS_DEBUG = true;

    // 系统下载文件存放目录
    public final static String DOWNLOAD_PATH = Environment.getExternalStorageDirectory() + "/bzs/";

    //    public static final String Base_URL = "http://114.67.80.221/api"; //正式库
    public static final String Base_URL = "http://114.67.176.178/api"; //测试库
//    public static final String Base_URL = "https://www.shydspt.com/api"; //三禾缘正式库


    public static final String Base_Upload_URL = "https://www.shydspt.com/hbf";//正式库
//    public static final String Base_Upload_URL = "http://192.168.0.110:8055";//大军

    public static final String OrderQueryUrl = "https://www.kuaidi100.com/";
    //AES password
    public final static String AES_PASSWORD = "bzs";

    public static final String LOCAL_URL = Environment.getExternalStorageDirectory().getAbsolutePath()
            + "/Android/data/com.leisurely.bzs/";

    public static final String GetCountry = Base_URL + "/common/countryList";

    public static final String GetBank = Base_URL + "/zbaccount/getBank";

//    public static final String GetRegisterVerificationCode = Base_URL + "/Register/getYzm";

    public static final String Register = Base_URL + "/user/register";

    public static final String Login = Base_URL + "/user/login";

    public static final String customerService = Base_URL + "/user/servicelist";

    public static final String perfect = Base_URL + "/user/profile";


//
//    public static final String GetOldVerificationCode = Base_URL + "/ModifyUser/editMobilYzm";
//
//    public static final String OldVerificationCode = Base_URL + "/ModifyUser/checkYzm";

    public static final String NewVerificationCode = Base_URL + "/user/changemobile";

    public static final String EditUser = Base_URL + "/user/changepwd";

    public static final String profile = Base_URL + "/user/profile";


    public static final String EditPayPwd = Base_URL + "/user/changepaypwd";

    public static final String GetUserInfo = Base_URL + "/Login/getUserInfo";

    public static final String GetAddress = Base_URL + "/user/addresslist";

    public static final String AddOrEditAddress = Base_URL + "/user/saveaddress";

    public static final String DeleteAddress = Base_URL + "/user/deladdress";

    public static final String GetOrderCoupons = Base_URL + "/order/userCouponList";

//    public static final String GetPackage = Base_URL + "/Package/getPackage";

//    public static final String pack = Base_URL + "/Yundan/pack";

//    public static final String QueryOrder = Base_URL + "/Yundan/queryYundan";

//    public static final String AllOrderCount = Base_URL + "/Yundan/queryNum";

//    public static final String OrderConfirm = Base_URL + "/Yundan/orderConfirm";

//    public static final String Pay = Base_URL + "/Apppay/payment";

//    public static final String GetServiceHistory = Base_Service_URL + "/index/index/historicalrecords.html";
//
//    public static final String GetOnlineServiceChoose = Base_Service_URL + "/index/index/customerServices.html";
//
//    public static final String UploadOnlineServiceImage = Base_Service_URL + "/index/upload/uploadimg.html";

    public static final String CheckChat = Base_URL + "/user/checkservice";

    public static final String sendVerificationCode = Base_URL + "/user/send";

    public static final String thirdRegister = Base_URL + "/user/thirdregister";

    public static final String qiniuToken = Base_URL + "/qiniuauth/uploadtoken";

    public static final String getUserDetail = Base_URL + "/user/userdetail";

    public static final String resetpwd = Base_URL + "/user/resetpwd";

    public static final String feedback = Base_URL + "/Feedback/addFeedback";

    public static final String inform = Base_URL + "/Notice/noticeList";

    public static final String noticeNew = Base_URL + "/Notice/noticeNew";

    public static final String shareUrl = Base_URL + "/user/shareUrl";

    public static final String addInvestmentList = Base_URL + "/Investment/addInvestmentList";

    public static final String rechargeindex = Base_URL + "/its_ct/rechargeindex";

    public static final String recharge = Base_URL + "/its_ct/recharge_new";

    public static final String addInvestment = Base_URL + "/Investment/addInvestment";

    public static final String rechargeList = Base_URL + "/its_ct/rechargeList";

    public static final String withdrawal = Base_URL + "/its_ct/withdrawal_new";

    public static final String withdrawalList = Base_URL + "/its_ct/withdrawalList";

    public static final String transfer = Base_URL + "/its_zs/transfer";

    public static final String transferList = Base_URL + "/its_zs/transferList";

    public static final String collectList = Base_URL + "/its_zs/collectList";

    public static final String quantificationWin = Base_URL + "/Investment/lhjl";

    public static final String shareWin = Base_URL + "/Investment/fxjl";

    public static final String investmentList = Base_URL + "/Investment/investmentList";

    public static final String community = Base_URL + "/user/community";

    public static final String checkVersion = Base_URL + "/user/versions";

    public static final String getItsSell = Base_URL + "/merchant/selllist";

    public static final String getItsBuy = Base_URL + "/buyer/purchaselist";

    public static final String createOrder = Base_URL + "/merchant/createOrder";

    public static final String paylist = Base_URL + "/user/paylist";

    public static final String sellOrder = Base_URL + "/buyer/createOrder";

    public static final String userpayway = Base_URL + "/user/userpayway";

    public static final String savepayway = Base_URL + "/user/savepayway";

    public static final String orderDetails = Base_URL + "/merchant/orderDetails";

    public static final String cancelOrder = Base_URL + "/merchant/cancelOrder";

    public static final String sellDetails = Base_URL + "/buyer/orderDetail";

    public static final String sellTransfer = Base_URL + "/buyer/transfer";

    public static final String orderList = Base_URL + "/merchant/orderList";

    public static final String sellLogList = Base_URL + "/buyer/orderlist";

    public static final String payinformation = Base_URL + "/merchant/payinformation";

    public static final String checkPhone = Base_URL + "/register/checkPhone.html";
    //3校验用户名是否已注册
    public static final String checkName = Base_URL + "/register/checkUserName.html";
    //4校验推荐人id是否存在
    public static final String checkInvite = Base_URL + "/register/checkInviteId.html";
    //发送短信
    public static final String getCaptcha = Base_URL + "/message/sendMessage.html";

    public static final String registerTest = Base_URL + "/register/register.html";

    public static final String login = Base_URL + "/login/login.html";

    public static final String registerQrCode = Base_URL + "/register/registerQrCode.html";

    public static final String queryMemberInfo = Base_URL + "/members/queryMemberInfo.html";

    public static final String queryMoney = Base_URL + "/members/money/%s.html";

    public static final String offlineAccount = Base_URL + "/deposit/offlineAccount.html";

    public static final String uploadFile = Base_Upload_URL + "/file/uploadImg.htm";

    public static final String uploadIdCardFile = Base_URL + "/zbaccount/uploadImg";

    public static final String offlineSubmit = Base_URL + "/deposit/offlineSubmit.html";

    public static final String realName = Base_URL + "/members/realName.html";

    public static final String bindCard = Base_URL + "/members/bindCard.html";

    public static final String queryCards = Base_URL + "/members/cards.html";

    public static final String deleteBank = Base_URL + "/members/card/%s.html";

    public static final String withdraw = Base_URL + "/withdraw.html";
    //10忘记密码
    public static final String modifPws = Base_URL + "/members/modifPws.html";

    public static final String moneyDetail = Base_URL + "/members/moneyDetail.html";

    public static final String offlinePage = Base_URL + "/deposit/offlinePage.html";

    public static final String queryPage = Base_URL + "/withdraw/queryPage.html";
    public static final String queryRechargePage = Base_URL + "/recharge/queryPage.html";

    public static final String queryIndexGoods = Base_URL + "/good/queryIndexGoods.html";

    public static final String findAddressPage = Base_URL + "/addressManage/findAddressPage.html";

    public static final String deleteAddress = Base_URL + "/addressManage/deleteAddress.html";

    public static final String saveOrUpdateAddress = Base_URL + "/addressManage/saveOrUpdateAddress.html";

    public static final String defaultAddress = Base_URL + "/addressManage/defaultAddress.html";

    public static final String queryGoodDetailedById = Base_URL + "/good/queryGoodDetailedById.html";

    public static final String queryGoodsList = Base_URL + "/good/queryGoodsList.html";

    public static final String goodPurchase = Base_URL + "/good/goodPurchase.html";

    public static final String searchOrder = Base_URL + "/good/orders/orders.html";

    public static final String getOrders = Base_URL + "/good/orders/getOrders.html";

    public static final String getScore = Base_URL + "/members/score/score.html";

    public static final String getScoreDetail = Base_URL + "/members/score/scoreDetail.html";

    public static final String addTradePassword = Base_URL + "/register/addTradePassword.html";

    public static final String queryMemberExtensionStatistics = Base_URL + "/members/queryMemberExtensionStatistics.html";

    public static final String commissionDetail = Base_URL + "/members/commission/commissionDetail.html";

    public static final String getCarouselList = Base_URL + "/banner/getCarouselList.html";

    public static final String getNewsList = Base_URL + "/news/getNewsList.html";

    public static final String queryNewsById = Base_URL + "/news/queryNewsById.html";

    public static final String uploadHead = Base_URL + "/members/uploadHead.html";

    public static final String resetUserName = Base_URL + "/members/resetUserName.html";

    public static final String equalToCode = Base_URL + "/message/equalToCodeApp.html";

    public static final String modifPhone = Base_URL + "/members/modifPhone.html";

    public static final String commission = Base_URL + "/members/commission/commission.html";

    public static final String bonusWithdraw = Base_URL + "/members/bonusWithdraw.html";

    public static final String alertOrderStatus = Base_URL + "/good/orders/alertOrderStatus.html";

    public static final String queryLowerLevel = Base_URL + "/members/queryLowerLevel.html";

    public static final String queryIndirectLowerLevel = Base_URL + "/members/queryIndirectLowerLevel.html";

    public static final String getShopList = Base_URL + "/shop/getShopList.html";

    public static final String shopKeep = Base_URL + "/shop/shopKeep.html";

    public static final String getAccount = Base_URL + "/account/getAccount.html";

    public static final String getShopDetail = Base_URL + "/shop/getShopDetail.html";

    public static final String shopPurchase = Base_URL + "/shop/order/shopPurchase.html";

    public static final String getAccountDetail = Base_URL + "/account/getAccountDetail.html";

    public static final String getKeepShopList = Base_URL + "/shop/getKeepShopList.html";

    public static final String getProvinces = Base_URL + "/addressManage/getProvinces.html";

    public static final String getCities = Base_URL + "/addressManage/getCities.html";

    public static final String getAreas = Base_URL + "/addressManage/getAreas.html";

    public static final String queryBank = Base_URL + "/zbaccount/queryBank";
    public static final String sendValidateCode = Base_URL + "/zbaccount/sendValidateCode";
    public static final String checkUserAccount = Base_URL + "/zbaccount/checkUserAccount";
    public static final String validateAndOpenAcct = Base_URL + "/zbaccount/validateAndOpenAcct";
    public static final String openAcctResult = Base_URL + "/zbaccount/openAcctResult";

    public static String getUserMoneyInfo(String uid) {
        return Base_URL + "/members/money/" + uid + ".html";
    }


    public static final String getUserBankInfo = Base_URL + "/zbaccount/get";
    public static final String modifTPws = Base_URL + "/members/modifTPws.html";


}
