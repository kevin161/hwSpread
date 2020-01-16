package com.leisurely.spread.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.leisurely.spread.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import pickerview.OptionsPickerView;
import pickerview.TimePickerView;
import pickerview.adapter.ArrayWheelAdapter;
import pickerview.lib.WheelView;

/**
 * Created by user on 2017/1/3.
 */

public class WheelViewUtils {
    /**
     * 底部滚轮点击事件回调
     */
    public interface OnWheelViewClick {
        void onClick(View view, int postion);
    }

    private static TextView text5_1, text5_2, text6_1, text6_2;

    /**
     * 弹出底部滚轮选择
     *
     * @param context
     * @param list
     * @param click
     */
    public static void alertBottomWheelOption(Context context, List<?> list,int index, final OnWheelViewClick click) {

        final PopupWindow popupWindow = new PopupWindow();

        View view = LayoutInflater.from(context).inflate(R.layout.layout_bottom_wheel_option, null);
        TextView tv_confirm =  view.findViewById(R.id.btnSubmit);
        final WheelView wv_option =  view.findViewById(R.id.wv_option);
        ArrayWheelAdapter adapter = new ArrayWheelAdapter(list);
        wv_option.setAdapter(adapter);
        wv_option.setCyclic(false);
        wv_option.setTextSize(16);
        wv_option.setCurrentItem(index);
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                click.onClick(view, wv_option.getCurrentItem());
            }
        });

        view.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int top = view.findViewById(R.id.ll_container).getTop();
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    int y = (int) motionEvent.getY();
                    if (y < top) {
                        popupWindow.dismiss();
                    }
                }
                return true;
            }
        });
        popupWindow.setContentView(view);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.showAtLocation(((ViewGroup) ((Activity) context).findViewById(android.R.id.content)).getChildAt(0), Gravity.CENTER, 0, 0);
    }

    public interface TimerPickerCallBack {
        void onTimeSelect(String date);
    }


    /**
     * 弹出时间选择
     *
     * @param context
     * @param type     TimerPickerView 中定义的 选择时间类型
     * @param format   时间格式化
     * @param callBack 时间选择回调
     */
//    public static void alertTimerPicker(final Context context, TimePickerView.Type type, final String format, final View view, final TimerPickerCallBack callBack) {
//        final TimePickerView pvTime = new TimePickerView(context, type);
//        //控制时间范围
//        //        Calendar calendar = Calendar.getInstance();
//        //        pvTime.setRange(calendar.get(Calendar.YEAR) - 20, calendar.get(Calendar.YEAR));
//        pvTime.setTime(new Date());
//        pvTime.setCyclic(false);
//        pvTime.setCancelable(true);
//        //时间选择后回调
//        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
//
//            @Override
//            public void onTimeSelect(Date date) {
//                SimpleDateFormat sdf = new SimpleDateFormat(format);
//                String result = sdf.format(date);
////                        tvTime.setText(getTime(date));
//                boolean check = true;
//                long beginTime = 0;
//                long endTime = 0;
//                switch (view.getId()) {
//                    case R.id.takeout_begin_time://营业时间(开始)
//                        text5_2 = (TextView) ((Activity) context).findViewById(R.id.takeout_end_time);
//                        beginTime = DateUtil.getTimeto(result);
//                        if (text5_2.getText() != null && StringUtil.isNotNull(text5_2.getText().toString())) {
//                            endTime = DateUtil.getTimeto(text5_2.getText().toString());
//                        }
//                        if (endTime != 0 && beginTime > endTime) {
//                            ToastUtil.showToast("开始时间不能大于结束时间");
//                            check = false;
//                        }
//                        break;
//                    case R.id.takeout_end_time://营业时间(结束)
//                        text5_1 = (TextView) ((Activity) context).findViewById(R.id.takeout_begin_time);
//                        endTime = DateUtil.getTimeto(result);
//                        if (text5_1.getText() != null && StringUtil.isNotNull(text5_1.getText().toString())) {
//                            beginTime = DateUtil.getTimeto(text5_1.getText().toString());
//                        }
//                        if (beginTime != 0 && beginTime > endTime) {
//                            ToastUtil.showToast("结束时间不能小于开始时间");
//                            check = false;
//                        }
//                        break;
////                    case R.id.shop_msg_text6_1://配送时间(开始)
////                        text6_2 = (TextView) ((Activity) context).findViewById(R.id.shop_msg_text6_2);
////                        beginTime = DateUtil.getTimeto(result);
////                        if (text6_2.getText() != null && StringUtil.isNotNull(text6_2.getText().toString())) {
////                            endTime = DateUtil.getTimeto(text6_2.getText().toString());
////                        }
////                        if (endTime != 0 && beginTime > endTime) {
////                            ToastUtil.showToast("开始时间不能大于结束时间");
////                            check = false;
////                        }
////                        break;
////                    case R.id.shop_msg_text6_2://配送时间(结束)
////                        text6_1 = (TextView) ((Activity) context).findViewById(R.id.shop_msg_text6_1);
////                        endTime = DateUtil.getTimeto(result);
////                        if (text6_1.getText() != null && StringUtil.isNotNull(text6_1.getText().toString())) {
////                            beginTime = DateUtil.getTimeto(text6_1.getText().toString());
////                        }
////                        if (beginTime != 0 && beginTime > endTime) {
////                            ToastUtil.showToast("结束时间不能小于开始时间");
////                            check = false;
////                        }
////                        break;
//                    default:
//                        break;
//                }
//                if (check) {
//                    pvTime.dismiss();
//                    callBack.onTimeSelect(result);
//                }
//            }
//        });
//        pvTime.setTextSize(16);
//        //弹出时间选择器
//        pvTime.show();
//    }


    /**
     * 弹出时间选择
     *
     * @param context
     * @param type     TimerPickerView 中定义的 选择时间类型
     * @param format   时间格式化
     * @param callBack 时间选择回调
     */
    public static void alertTimerPickerYMD(final Context context, TimePickerView.Type type, final String format, final View view, final TimerPickerCallBack callBack) {
        final TimePickerView pvTime = new TimePickerView(context, type);
        //控制时间范围
        //        Calendar calendar = Calendar.getInstance();
        //        pvTime.setRange(calendar.get(Calendar.YEAR) - 20, calendar.get(Calendar.YEAR));
        pvTime.setTime(new Date());
        pvTime.setCyclic(false);
        pvTime.setCancelable(true);
        //时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                String result = sdf.format(date);
                Date now = new Date();
                if(now.after(date) || result.equals(sdf.format(now))){
                     ToastUtil.showToast("请选择正确的身份证有效期");
                }else{
                    pvTime.dismiss();
                    callBack.onTimeSelect(result);
                }

            }
        });
        pvTime.setTextSize(16);
        //弹出时间选择器
        pvTime.show();
    }

    /**
     * 弹出三级分类
     *
     * @param context
     * @param list1
     * @param list2
     * @param list3
     * @param listener
     */
    public static void showOptionsPickerViewString(final Context context, final List<String> list1, final List<List<String>> list2,
                                                   final List<List<List<String>>> list3, int selectOne, int selectTwo, int selectThree,
                                                   OptionsPickerView.OnOptionsSelectListener listener) {
        //条件选择器
        OptionsPickerView pvOptions = new OptionsPickerView(context);
        pvOptions.setPicker(list1, list2, list3, true);
        pvOptions.setOnoptionsSelectListener(listener);
        pvOptions.setTextSize(14);
        pvOptions.setCyclic(false, false, false);
        pvOptions.setSelectOptions(selectOne, selectTwo, selectThree);
        pvOptions.show();
    }


    /**
     * 弹出三级分类
     *
     * @param context
     * @param list1
     * @param list2
     * @param list3
     * @param listener
     */
//    public static void showOptionsPickerView(final Context context, final List<CityBean> list1, final List<List<CityBean>> list2,
//                                             final List<List<List<CityBean>>> list3, int selectOne, int selectTwo, int selectThree,
//                                             OptionsPickerView.OnOptionsSelectListener listener) {
//        //条件选择器
//        OptionsPickerView pvOptions = new OptionsPickerView(context);
//        pvOptions.setPicker(list1, list2, list3, true);
//        pvOptions.setOnoptionsSelectListener(listener);
//        pvOptions.setTextSize(14);
//        pvOptions.setCyclic(false, false, false);
//        pvOptions.setSelectOptions(selectOne, selectTwo, selectThree);
//        pvOptions.show();
//    }
}
