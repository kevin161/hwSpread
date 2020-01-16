
package com.leisurely.spread.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DateUtil {


    public static String currentime() {
        Date dt = new Date();
        Long ctuenttime = dt.getTime();
        return ctuenttime + "";
    }

    public static long currentimeLong() {
        Date dt = new Date();
        Long ctuenttime = dt.getTime();
        return ctuenttime;
    }

    public static String longToDate(String longStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long date = Long.parseLong(longStr);
        Date dt = new Date(date);

        return sdf.format(dt);
    }

    //获取当前日期
    public static String getLocationYmd() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }

    //获取当前日期
    public static String getLocationYm() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        return sdf.format(new Date());
    }

    //获取当前日期
    public static String getLocationweekday() {
        return getWeekDate(new Date().getTime());
    }


    /**
     * 秒的时间戳
     *
     * @param longStr
     * @return
     */
    public static String longToDate2(String longStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long date = Long.parseLong(longStr) * 1000;
        Date dt = new Date(date);
        return sdf.format(dt);
    }

    public static String getMonthDate(String longStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        long date = Long.parseLong(longStr);
        Date dt = new Date(date);
        return sdf.format(dt);
    }

    /**
     * 秒的时间戳
     *
     * @param longStr
     * @return
     */
    public static String getMonthDate2(String longStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        long date = Long.parseLong(longStr) * 1000;
        Date dt = new Date(date);
        return sdf.format(dt);
    }

    public static String longToDate(Long lg) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dt = new Date(lg);
        return sdf.format(dt);
    }

    /**
     * 秒的时间戳
     *
     * @param longStr
     * @return
     */
    public static String getMini(String longStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        long date = Long.parseLong(longStr) ;
        Date dt = new Date(date);
        return sdf.format(dt);
    }

    public static String getCurrendYearMonthDayHourMin(String longStr) {
        SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        if ("".equals(longStr) || longStr == null) {
            return "";
        } else {
            long date = Long.parseLong(longStr);
            Date dt = new Date(date);
            return myFmt.format(dt);
        }
    }

    /**
     * 秒的时间戳 上面的是毫秒的
     *
     * @param longStr
     * @return
     */
    public static String toymdhm(String longStr) {
        SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        if ("".equals(longStr) || longStr == null) {
            return "";
        } else {
            long date = Long.parseLong(longStr) * 1000;
            Date dt = new Date(date);
            return myFmt.format(dt);
        }
    }


    public static String getCurrendYearMonthDay() {
        SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd");
        Date dt = new Date();
        return myFmt.format(dt);
    }

    public static String getCurrendYearMonth() {
        SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM");
        Date dt = new Date();
        return myFmt.format(dt);
    }

    /**
     * -----------------------------获取本周周一的日期getWeekdays()
     */
    private static final int FIRST_DAY = Calendar.MONDAY;

    public static String getWeekdays() {
        Calendar calendar = Calendar.getInstance();
        setToFirstDay(calendar);
        return printDay(calendar);
    }

    private static void setToFirstDay(Calendar calendar) {
        while (calendar.get(Calendar.DAY_OF_WEEK) != FIRST_DAY) {
            calendar.add(Calendar.DATE, -1);
        }
    }

    private static String printDay(Calendar calendar) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(calendar.getTime());
    }

    public static Matcher getYearMonthDayByString(String data) {

        Pattern p = Pattern.compile("(\\d{4})-(\\d{1,2})-(\\d{1,2})");
        Matcher m = p.matcher("x20xxx" + data.trim() + "xxx19852x");
        if (m.find()) {
            System.out.println("日期:" + m.group());
            System.out.println("年:" + m.group(1));
            System.out.println("月:" + m.group(2));
            System.out.println("日:" + m.group(3));
        }
        return m;
    }

    public static Date getDateByString(String date) {
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
        Date d = null;
        try {
            d = sim.parse(date.trim());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }

    /**
     * 根据日期（年月日）字符串 转成毫秒值
     *
     * @param date 2015-12-12
     * @return
     */
    public static long getDateIntByString(String date) {
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
        Date d = null;
        try {
            d = sim.parse(date.trim());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d.getTime();
    }

    /**
     * 根据日期（年月日时分秒）字符串 转成毫秒值
     *
     * @param date 2015-12-12-11：50；30
     * @return
     */
    public static long getLongtime(String date) {

        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date d = null;
        try {
            d = sim.parse(date.trim());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d.getTime();
    }

    public static String getDataTime(long longStr) {
        SimpleDateFormat myFmt = new SimpleDateFormat("MM-dd HH:mm:ss");
        if (longStr == 0) {
            return "";
        } else {
            Date dt = new Date(longStr);
            return myFmt.format(dt);
        }
    }

    public static String getdata(long longStr) {
        SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (longStr == 0) {
            return "";
        } else {
            Date dt = new Date(longStr);
            return myFmt.format(dt);
        }
    }

    public static String getdatatime(long longStr) {
        SimpleDateFormat myFmt = new SimpleDateFormat("HH:mm:ss");
        if (longStr == 0) {
            return "";
        } else {
            Date dt = new Date(longStr);
            return myFmt.format(dt);
        }
    }


    /**
     * 根据日期（年月日时分）字符串 转成毫秒值
     *
     * @param = "2015-04-14 13:02";
     * @return
     */
    public static Long getDateto(String date) {
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date d = null;
        try {
            d = sim.parse(date.trim());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d.getTime();
    }

    /**
     * 根据日期（年月日时分）字符串 转成毫秒值
     *
     * @param
     * @return
     */
    public static Long getTimeto(String date) {
        SimpleDateFormat sim = new SimpleDateFormat("HH:mm");
        Date d = null;
        try {
            d = sim.parse(date.trim());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d.getTime();
    }

    /**
     * @param
     * @return
     */
    public static String getTimetoString(String date) {
        SimpleDateFormat sim = new SimpleDateFormat("HH:mm");
        String result = "";
        try {
            Date d = new Date(getLongtime(date));
            result = sim.format(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 获取指定日期是星期几
     * <p>
     * 根据日期或者毫秒值 转化成 星期
     *
     * @param ("2015-12-12")
     * @return
     */

    public static String getWeekOfDate(String longStr) {
        Long date = getDateIntByString(longStr);
        Date dt = new Date(date);
        String[] weekOfDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar calendar = Calendar.getInstance();
        if (dt != null) {
            calendar.setTime(dt);
        }
        int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekOfDays[w];

    }

    public static String getWeekDate(Long longStr) {
        Date dt = new Date(longStr);
        String[] weekOfDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar calendar = Calendar.getInstance();
        if (dt != null) {
            calendar.setTime(dt);
        }
        int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekOfDays[w];

    }


    public static SimpleDateFormat getFormatyMd() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf;
    }

    public static SimpleDateFormat getFormatyM() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        return sdf;
    }

    //获取当月第一天
    public static String getfirstDay(String ym) {
        SimpleDateFormat sim = getFormatyM();
        Date d = null;
        try {
            d = sim.parse(ym.trim());
        } catch (ParseException e) {
            e.printStackTrace();
            d = new Date();
        }

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(d.getTime());
        cal.add(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        String first = getFormatyMd().format(cal.getTime());
        return first;
    }


    //获取最后一天
    public static String getlastDay(String ym) {
        SimpleDateFormat sim = getFormatyM();
        Date d = null;
        try {
            d = sim.parse(ym.trim());
        } catch (ParseException e) {
            e.printStackTrace();
            d = new Date();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(d.getTime());
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        String last = getFormatyMd().format(cal.getTime());
        return last;
    }

}
