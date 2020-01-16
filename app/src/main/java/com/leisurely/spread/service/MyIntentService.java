//package com.leisurely.spread.service;
//
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.content.Context;
//import android.graphics.Color;
//import android.os.Build;
//
//import com.alibaba.fastjson.JSONObject;
//import com.igexin.sdk.GTIntentService;
//import com.igexin.sdk.message.GTCmdMessage;
//import com.igexin.sdk.message.GTNotificationMessage;
//import com.igexin.sdk.message.GTTransmitMessage;
//
//import com.leisurely.spread.config.SysConstants;
//import com.leisurely.spread.util.SharedPreferencesUtil;
//
///**
// * Created by jbuy on 2018/10/23.
// */
//
//public class MyIntentService extends GTIntentService {
//    private static final int PUSH_NOTIFICATION_ID = (0x001);
//    private static final String PUSH_CHANNEL_ID = "PUSH_NOTIFY_ID";
//    private static final String PUSH_CHANNEL_NAME = "PUSH_NOTIFY_NAME";
//
//    public MyIntentService() {
//
//    }
//
//    @Override
//    public void onReceiveServicePid(Context context, int pid) {
//
//    }
//
//    @Override
//    public void onReceiveMessageData(Context context, GTTransmitMessage gtm) {
//        //透传
//        byte[] bytes = gtm.getPayload();
//        String s = new String(bytes);//{"purpose":"6","title":"\u6d4b\u8bd5","body":"\u6d4b\u8bd5\u662f\u662f\u662f","unread":1}
//        JSONObject json = JSONObject.parseObject(s);
////        GTNotificationMessage msg = new GTNotificationMessage(gtm.getMessageId(), gtm.getTaskId(),
////                json.getString("title"), json.getString("body"));
////        onNotificationMessageArrived(context, msg);
//        System.out.println(context);
//        sendSubscribeMsg(json.getString("title"), json.getString("body"), json.getString("purpose"));
//
////        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
////        //创建通知建设类
////        Notification.Builder builder = new Notification.Builder(AppApplication.getAppContext());
////        //设置跳转的页面
////        PendingIntent intent = PendingIntent.getActivity(AppApplication.getAppContext(),
////                100, new Intent(AppApplication.getAppContext(), MainActivity.class),
////                PendingIntent.FLAG_CANCEL_CURRENT);
////
////        //设置通知栏标题
////        builder.setContentTitle(json.getString("title"));
////        //设置通知栏内容
////        builder.setContentText(json.getString("body"));
////        //设置跳转
////        builder.setContentIntent(intent);
////        //设置图标
////        builder.setSmallIcon(R.mipmap.app_icon);
////        //设置
////        builder.setDefaults(Notification.DEFAULT_ALL);
////        builder.setAutoCancel(true);
////        //创建通知类
////        Notification notification = builder.build();
////        //显示在通知栏
////        manager.notify(1, notification);
//
//    }
//
//    @Override
//    public void onReceiveClientId(Context context, String clientid) {
//        SharedPreferencesUtil.saveString(SysConstants.CLIENTID, clientid);
////        Log.e(TAG, "onReceiveClientId -> " + "clientid = " + clientid);
//    }
//
//    @Override
//    public void onReceiveOnlineState(Context context, boolean online) {
//    }
//
//    @Override
//    public void onReceiveCommandResult(Context context, GTCmdMessage cmdMessage) {
//        System.out.println(context);
//    }
//
//    @Override
//    public void onNotificationMessageArrived(Context context, GTNotificationMessage msg) {
//        //点击后打开app
//
//        System.out.println(context);
//    }
//
//    @Override
//    public void onNotificationMessageClicked(Context context, GTNotificationMessage msg) {
//        System.out.println(context);
//    }
//
//    public void sendSubscribeMsg(String title, String content, String index) {
//        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel channel = new NotificationChannel(PUSH_CHANNEL_ID, PUSH_CHANNEL_NAME,
//                    NotificationManager.IMPORTANCE_DEFAULT);
//            channel.enableLights(true); //是否在桌面icon右上角展示小红点
//            channel.setLightColor(Color.RED); //小红点颜色
//            channel.setShowBadge(true); //是否在久按桌面图标时显示此渠道的通知
//            manager.createNotificationChannel(channel);
//        }
//        //设置跳转的页面
////        PendingIntent intent = PendingIntent.getActivity(AppApplication.getAppContext(),
////                100, new Intent(AppApplication.getAppContext(), ReceiverActivity.class).putExtra("index", index),
////                PendingIntent.FLAG_CANCEL_CURRENT);
////        Notification notification = new NotificationCompat.Builder(this, PUSH_CHANNEL_ID)
////                .setContentTitle(title)
////                .setContentText(content)
////                .setWhen(System.currentTimeMillis())
////                .setSmallIcon(R.mipmap.push_small)
////                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.push))
////                .setAutoCancel(true)
////                .setContentIntent(intent)
////                .build();
////        manager.notify(1, notification);
//    }
//
//}
