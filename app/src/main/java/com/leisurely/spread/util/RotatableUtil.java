//package com.leisurely.xxy.util;
//
//import android.view.View;
//
//import com.nineoldandroids.view.ViewHelper;
//
///**
// * Created by jbuy on 2018/4/20.
// */
//
//public class RotatableUtil {
//
//    /**
//     * 翻牌
//     */
//    public static void cardTurnover(View cardRoot,View front,View back,int type) {
//        if (type ==0) {
//            ViewHelper.setRotationY(front, 180f);//先翻转180，转回来时就不是反转的了
//
//            Rotatable rotatable = new Rotatable.Builder(cardRoot)
//                    .sides(back.getId(), front.getId())
//                    .direction(Rotatable.ROTATE_Y)
//                    .rotationCount(1)
//                    .build();
//            rotatable.setTouchEnable(false);
//            rotatable.rotate(Rotatable.ROTATE_Y, -180, 1500);
//
//        } else if (type ==1) {
//            Rotatable rotatable = new Rotatable.Builder(cardRoot)
//                    .sides(back.getId(), front.getId())
//                    .direction(Rotatable.ROTATE_Y)
//                    .rotationCount(1)
//                    .build();
//            rotatable.setTouchEnable(false);
//            rotatable.rotate(Rotatable.ROTATE_Y, 0, 1500);
//
//        }
//    }
//}
