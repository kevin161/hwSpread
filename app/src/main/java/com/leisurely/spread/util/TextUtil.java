package com.leisurely.spread.util;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.InputType;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.leisurely.spread.framework.base.BaseActivity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtil {

    /**
     * 按钮重复点击问题 返回true 表示单位时间内重复点击了
     */
    private static long lastClickTime;

    /**
     * 金额转换方法
     */
    public static String parseMoney(String money) {
        BigDecimal bd = new BigDecimal(money);
        DecimalFormat df = new DecimalFormat(",###,##0.00");
        return df.format(bd);
    }


    public static String parseMoneyTwo(String money) {
        BigDecimal bd = new BigDecimal(money);
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(bd);
    }

    public static String parseMoneyFour(String money) {
        BigDecimal bd = new BigDecimal(money);
        DecimalFormat df = new DecimalFormat("#.0000");
        return df.format(bd);
    }

    /**
     * 将double保留2位小数
     *
     * @param a
     * @return
     */
    public static double parse2Double(double a) {
        BigDecimal b = new BigDecimal(a);
        return b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static double parseDouble(String str) {
        double dd;
        try {
            dd = Double.parseDouble(str);
        } catch (Exception e) {
            dd = 0;
        }

        return dd;

    }

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 1000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
    // 3-16可以买学生票。60-69可以买优惠票。

    /**
     * @param age 当前输入的身份证号
     * @param now 当前日期
     * @return
     */
    public static Boolean isStudent(String age, String now) {
        // 41152419910803259;
        String str = age.substring(6, 10);
        Integer i = Integer.parseInt(str);
        // 2015-11-11;
        String n = now.substring(0, 4);
        Integer n1 = Integer.parseInt(n);
        Integer x = n1 - i;
        if (x >= 3 && x <= 16) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * @param age 当前输入的身份证号
     * @param now 当前日期
     * @return
     */
    public static Boolean isOld(String age, String now) {
        // 41152419910803259;
        String str = age.substring(6, 10);
        Integer i = Integer.parseInt(str);
        // 2015-11-11;
        String n = now.substring(0, 4);
        Integer n1 = Integer.parseInt(n);
        Integer x = n1 - i;
        if (x >= 60 && x <= 69) {
            return true;
        } else {
            return false;
        }

    }

    /***
     * String转int类型
     *
     * @param
     * @return
     */
    public static int StrToInt(String s) {

        return Integer.valueOf(s).intValue();

    }
    // 保留两位小数点

    /**
     * 格式话金额数据 保留俩位 String类型的金额就行
     *
     * @param str
     * @return
     */
    public static String get2format(String str) {
        DecimalFormat df = new DecimalFormat("#0.00");

        return df.format(Float.parseFloat(str)).toString();
    }

    // double保留两位小数点
    public static String get2Double(double a) {
        DecimalFormat df = new DecimalFormat("#0.00");
        return df.format(a).toString();
    }

    // double保留四位小数点
    public static String get4Double(double a) {
        DecimalFormat df = new DecimalFormat("#0.0000");
        return df.format(a).toString();
    }

    /**
     * 格式话金额数据 保留一位 String类型的金额就行
     *
     * @param str
     * @return
     */
    public static String get1format(String str) {
        DecimalFormat df = new DecimalFormat("#0.0");

        return df.format(Float.parseFloat(str)).toString();
    }

    public static String[] getmonth(String month) {
        return month.split("-");
    }

    public static boolean isZeroOrNull(String result) {
        if (TextUtils.isEmpty(result) || "0".equals(result)
                || "0.0".equals(result) || "0.00".equals(result)) {
            return true;
        }
        return false;
    }

    /**
     * 判断字符串是否全为中文
     *
     * @param string
     * @return
     */
    public static boolean isNumber(String string) {
        if (string.matches("[0-9]+")) {
            return true;
        } else {
            return false;
        }

//		for(int i=0; i<string.length(); i++)
//		{
//			char oneChar = string.charAt(i);
//			if((oneChar >= 'u4e00' && oneChar <= 'u9fa5")
//					||(oneChar >= "uf900" && oneChar <="ufa2d"))
//				return true;
//			return false;
//
//		}
    }

    /**
     * 判断字符串是否全为中文
     *
     * @param string
     * @return
     */
    public static boolean isChinese(String string) {
        if (string.matches("[\\u4E00-\\u9FA5]+")) {
            return true;
        } else {
            return false;
        }

//		for(int i=0; i<string.length(); i++)
//		{
//			char oneChar = string.charAt(i);
//			if((oneChar >= 'u4e00' && oneChar <= 'u9fa5")
//					||(oneChar >= "uf900" && oneChar <="ufa2d"))
//				return true;
//			return false;
//
//		}
    }

    /**
     * 得到文件二进制流
     *
     * @param fileName
     * @return
     * @throws Exception
     */
    public static byte[] getFileContent(String fileName) throws Exception {
        File file = new File(fileName);
        if (file.exists()) {
            InputStream is = new FileInputStream(file);
            return getFileByte(is);
        }
        return null;
    }

    /**
     * 输入流转byte数组
     *
     * @param is
     * @return
     */
    public static byte[] getFileByte(InputStream is) {
        try {
            byte[] buffer = new byte[1024];
            int len = -1;
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            while ((len = is.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            byte[] data = outStream.toByteArray();
            outStream.close();
            is.close();
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 字符串不能空
     *
     * @param value 要验证的字符串
     * @return
     */
    public static boolean stringIsNotNull(String value) {
        return value != null && value.trim().length() > 0;
    }

    /**
     * 字符串为空
     *
     * @param value 要验证的字符串
     * @return
     */
    public static boolean stringIsNull(String value) {
        return value == null || value.trim().length() <= 0;
    }

    /**
     * @param xml
     * @return
     * @author ty
     * @createdate 2012-6-26 下午10:38:11
     * @Description: 字符串转InputStream
     */
    public static InputStream stringToInputStream(String xml) {
        ByteArrayInputStream stream = new ByteArrayInputStream(xml.getBytes());
        return stream;
    }

    /**
     * 获取文本内容的长度，中文算一个字符，英文算半个字符，包括标点符号
     *
     * @param str
     * @return
     */
    public static int getTextLengthes(String str) {
        int number = getTextLength(str);
        int length = number / 2;
        if (number % 2 != 0) {
            length += 1;
        }
        str = null;
        return length;
    }

    /**
     * 获取文本内容的长度(中文算两个字符，英文算一个字符)
     *
     * @param str
     * @return
     */
    public static int getTextLength(String str) {
        int length = 0;
        try {
            str = new String(str.getBytes("GBK"), "ISO8859_1");
            length = str.length();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return length;
    }

    /**
     * @param price 需要转换的价格
     * @autor bo.wang
     * @createdate 2012-11-28 上午10:14:47
     * @Description 酒店价格转换 1，100.1转换为101 2，100.000装换为100
     */
    public static String formatPrice(String price) {
        if (price.contains(".")) {
            String price2 = String.valueOf(price).substring(
                    price.lastIndexOf(".") + 1, price.length());
            if (Integer.valueOf(price2) > 0) {
                String price3 = String.valueOf(Integer.valueOf(price.substring(
                        0, price.lastIndexOf("."))) + 1);
                return price3;

            } else {
                String price4 = price.substring(0, price.lastIndexOf("."));
                return price4;
            }
        } else {
            return price;
        }
    }

    /**
     * @param input
     * @return
     * @author lk
     * @createdate 2014-9-30 上午11:28:14
     * @Description: (数字 、 字母及标点全部转为全角字符)
     */
    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    /**
     * 实现文本复制功能
     *
     * @param content 要复制的内容
     */
    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    public static void copy(String content, Context context) {
        // 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) context
                .getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content.trim());
    }

    public static String base64String(byte[] data) {
        char[] legalChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
                .toCharArray();
        int start = 0;

        int len = data.length;

        StringBuffer buf = new StringBuffer(data.length * 3 / 2);

        int end = len - 3;

        int i = start;

        int n = 0;

        while (i <= end) {

            int d = ((((int) data[i]) & 0x0ff) << 16)

                    | ((((int) data[i + 1]) & 0x0ff) << 8)

                    | (((int) data[i + 2]) & 0x0ff);

            buf.append(legalChars[(d >> 18) & 63]);

            buf.append(legalChars[(d >> 12) & 63]);

            buf.append(legalChars[(d >> 6) & 63]);

            buf.append(legalChars[d & 63]);

            i += 3;

            if (n++ >= 14) {

                n = 0;

                buf.append(" ");

            }

        }

        if (i == start + len - 2) {

            int d = ((((int) data[i]) & 0x0ff) << 16)

                    | ((((int) data[i + 1]) & 255) << 8);

            buf.append(legalChars[(d >> 18) & 63]);

            buf.append(legalChars[(d >> 12) & 63]);

            buf.append(legalChars[(d >> 6) & 63]);

            buf.append("=");

        } else if (i == start + len - 1) {

            int d = (((int) data[i]) & 0x0ff) << 16;

            buf.append(legalChars[(d >> 18) & 63]);

            buf.append(legalChars[(d >> 12) & 63]);

            buf.append("==");

        }

        return buf.toString();
    }

    /**
     * 关乎金额类的非空判断 为浮点数
     *
     * @param text
     * @return
     */
    public static String isnull(String text) {
        if ("null".equals(text) || text == null || "".equals(text) || "0".equals(text) || "0.0".equals(text) || "0.00".equals(text) || "0.000".equals(text)) {
            return "0";
        } else {
            DecimalFormat decimalFormat = new DecimalFormat("##0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.

            String p = decimalFormat.format(Float.parseFloat(text));//format 返回的是字符串

            return p;
        }
    }

    /**
     * 关乎数量类的非空判断 为整数
     *
     * @param text
     * @return
     */
    public static String isnum(String text) {
        String text1 = isnull(text);
        int num = (int) Float.parseFloat(text1);//只保留整数部分
        return num + "";
    }

    /**
     * 30分钟倒计时
     *
     * @param creatTime
     * @param currentTime
     * @return
     */
    public static String timeCount(String creatTime, long currentTime) {
        String str = "";
        if (!TextUtils.isEmpty(creatTime)) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                long creat = sdf.parse(creatTime).getTime();
                long time = currentTime - creat;
                if (time <= 1800000) {
                    time = 1800000 - time;
                    time = time / 1000;
                    if (time % 60 < 10) {
                        str = (time / 60) + ":0" + (time % 60);
                    } else {
                        str = (time / 60) + ":" + (time % 60);
                    }
                } else {
                    str = "";
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return str;
    }

    /**
     * 拼接字符串，按照不同的颜色
     *
     * @param str1
     * @param str2
     * @return
     */
    public static String appendStr(String str1, String str2) {
        return new StringBuilder().append("<font  color=\"black\">").append(str1).append("</font>")
                .append("<font  color=\"#FFA801\">").append(str2).append("</font>").toString();
    }

    /**
     * 模糊搜索结果，拼接字符串，高亮显示
     *
     * @param resultStr
     * @param keyWords
     * @return
     */
    public static String appendSearchStr(String resultStr, String keyWords) {
        if (!TextUtils.isEmpty(resultStr)) {
            if (resultStr.equals(keyWords)) {
                return new StringBuilder().append("<font  color=\"#FFA801\">").append(keyWords).append("</font>").toString();
            } else if (resultStr.contains(keyWords)) {
                String strStart = resultStr.substring(0, resultStr.indexOf(keyWords));
                String strEnd = resultStr.substring(resultStr.indexOf(keyWords), resultStr.length()).replaceFirst(keyWords, "");

                if (TextUtils.isEmpty(strStart) && !TextUtils.isEmpty(strEnd)) {
                    return new StringBuilder()
                            .append("<font  color=\"#FFA801\">").append(keyWords).append("</font>")
                            .append("<font  color=\"black\">").append(strEnd).append("</font>")
                            .toString();
                } else if (!TextUtils.isEmpty(strStart) && TextUtils.isEmpty(strEnd)) {
                    return new StringBuilder()
                            .append("<font  color=\"black\">").append(strStart).append("</font>")
                            .append("<font  color=\"#FFA801\">").append(keyWords).append("</font>")
                            .toString();
                } else if (!TextUtils.isEmpty(strStart) && !TextUtils.isEmpty(strEnd)) {
                    return new StringBuilder()
                            .append("<font  color=\"black\">").append(strStart).append("</font>")
                            .append("<font  color=\"#FFA801\">").append(keyWords).append("</font>")
                            .append("<font  color=\"black\">").append(strEnd).append("</font>")
                            .toString();
                } else {
                    return resultStr;
                }
            } else {
                return resultStr;
            }
        } else {
            return "";
        }
    }

    /**
     * 检查输入的金额是否符合格式(大于等于0 是数字格式)
     *
     * @param value
     * @return
     */
    public static boolean checkDouble(String value) {
        try {
            Double d = Double.valueOf(value);
            if (d < 0) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 检查输入的金额是否符合格式(大于0 是数字格式)
     *
     * @param value
     * @return
     */
    public static boolean checkDoubleOutZero(String value) {
        try {
            Double d = Double.valueOf(value);
            if (d <= 0) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    public static double multiplyDouble(String a, String b) {
        double result = 0;
        try {
            double c = Double.parseDouble(a);
            double d = Double.parseDouble(b);
            result = c * d;
        } catch (Exception e) {
            return 0;
        }
        return result;
    }


    public static boolean checkSymbol(String psd) {
        String speChat = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？ ]";
        Pattern pattern = Pattern.compile(speChat);
        Matcher matcher = pattern.matcher(psd);
        if (matcher.find()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param string
     * @return 验证是否为数值
     */
    public static boolean isNumeric(String string) {
        if (string != null && string.length() != 0) {
            Pattern pattern = Pattern.compile("([-|+]?[0-9]+\\.?[0-9]*)|([-|+]?[0-9]*\\.?[0-9]+)");
            return pattern.matcher(string).matches();
        }
        return false;
    }

    public static boolean checkPwd(TextView textView) {
        if (textView.getText().toString().length() < 6 || textView.getText().toString().length() > 20 || checkSymbol(textView.getText().toString())) {
            return true;
        } else {
            return false;
        }
    }

    public static void onClickCopy(String text, BaseActivity activity) {
        ClipboardManager cm = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        ClipData myClip = ClipData.newPlainText("text", text);//text是内容
        cm.setPrimaryClip(myClip);
//        cm.setText(v.getText());该方法已过时
        Toast.makeText(activity, "复制成功", Toast.LENGTH_LONG).show();
    }

    public static void onClickCopy(String text, BaseActivity activity, String alert) {
        ClipboardManager cm = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        ClipData myClip = ClipData.newPlainText("text", text);//text是内容
        cm.setPrimaryClip(myClip);
//        cm.setText(v.getText());该方法已过时
        Toast.makeText(activity, alert, Toast.LENGTH_LONG).show();
    }

    public static boolean isMobileNumber(String mobiles) {
        String telRegex = "^\\d{11}$";
        return !mobiles.matches(telRegex);
    }


    /**
     * @param str
     * @return 每4个加一个空格
     */
    public static String addBlankInMiddle(String str) {
//字符串长度
        int strlenth = str.length();
//需要加空格数量
        int blankcount = 0;
//判断字符串长度
        if (strlenth <= 4) {
            blankcount = 0;
        } else {
            blankcount = strlenth % 4 > 0 ? strlenth / 4 : str.length() / 4 - 1; //需要加空格数量
        }
//插入空格
        if (blankcount > 0) {
            for (int i = 0; i < blankcount; i++) {
                str = str.substring(0, (i + 1) * 4 + i) + " " + str.substring((i + 1) * 4 + i, strlenth + i);
            }
        } else {
//            System.out.println(“输入的字符串不多于4位，不需要添加空格”);
        }
//返回
        return str;
    }
}
