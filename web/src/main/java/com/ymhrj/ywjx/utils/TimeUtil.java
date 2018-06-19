package com.ymhrj.ywjx.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 时间辅助工具类.
 * <p>Description: </p>
 * <p>Create Time: 2017/2/21 0021 15:57 </p>
 * @author 910204
 */
public class TimeUtil {

    /**
     * Description:
     * Author: zys(910204)
     * Date: 16/11/09
     *
     * @param String formate
     *
     * @return String
     */
    public static String date(String formate) {
        if ("".equals(formate)) {
            formate = "yyyy-MM-dd HH:mm:ss";
        }

        SimpleDateFormat sdf = new SimpleDateFormat(formate);
        Date             dt  = new Date();

        return sdf.format(dt);
    }

    /**
     * Description:
     * Author: zys(910204)
     * Date: 16/11/09
     *
     * @return int
     */
    public static int endOfToday() {
        Calendar todayEnd = Calendar.getInstance();

        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);

        return (int) (todayEnd.getTime().getTime() / 1000);
    }

    /**
     * Description:
     * Author: zys(910204)
     * Date: 16/11/09
     *
     * @return int
     */
    public static int startOfToday() {
        Calendar todayStart = Calendar.getInstance();

        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);

        return (int) (todayStart.getTime().getTime() / 1000);
    }

    /**
     * Description:
     * Author: zys(910204)
     * Date: 16/11/09
     *
     * @return int
     */
    public static int str2Ts() {
        return str2Ts("", "");
    }

    /**
     * Description:
     * Author: zys(910204)
     * Date: 16/11/09
     *
     * @param String dateStr
     * @param String formate
     *
     * @return int
     */
    public static int str2Ts(String dateStr, String formate) {
        Date date  = null;
        long times = 0;

        if (dateStr.equals("")) {
            date = new Date();
        } else {
            if (formate.equals("")) {
                formate = "yyyy-MM-dd HH:mm:ss";
            }

            SimpleDateFormat sdf = new SimpleDateFormat(formate);

            try {
                date = sdf.parse(dateStr);
            } catch (Exception e) {
                return 0;
            }
        }

        times = date.getTime();

        return (int) (times / 1000);
    }

    /**
     * Description:
     * Author: zys(910204)
     * Date: 16/11/09
     *
     * @param long timestamp
     *
     * @return String
     */
    public static String ts2Str(long timestamp) {
        return ts2Str(timestamp, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * Description:
     * Author: zys(910204)
     * Date: 16/11/09
     *
     * @param long timestamp
     * @param String formate
     *
     * @return String
     */
    public static String ts2Str(long timestamp, String formate) {
        SimpleDateFormat sdf = new SimpleDateFormat(formate);

        timestamp = (String.valueOf(timestamp).length() > 10)
                    ? timestamp
                    : timestamp * 1000;

        return sdf.format(new Date(timestamp));
    }

    /**
     * 计算所给时间戳与当前时刻相差的时间.
     * <p>Description:输出格式为“X秒前”“X分钟前”“X小时前”，非当天的则直接显示时间戳日期. </p>
     * <p>Create Time: 2017/2/21 0021 15:59 </p>
     * @author 910204
     * @param timestamp long
     * @return String
     */
    public static String timeDiff(long timestamp){
        // 毫秒进制
        int msCons = 1000;
        SimpleDateFormat dateSdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat datetimeSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        long nh = msCons * 60 * 60;//一小时的毫秒数
        long nm = msCons * 60;//一分钟的毫秒数

        String diffStr;
        Date nowDate = new Date();
        Date preDate = new Date(timestamp * msCons);

        String nowDateTimeStr = dateSdf.format(nowDate);
        String preDateTimeStr = dateSdf.format(preDate);
        if (preDateTimeStr.equals(nowDateTimeStr)) {// 同一天
            long now = nowDate.getTime();
            long diff = now - timestamp * msCons;
            long hour = diff / nh;//计算差多少小时
            long min = diff / nm;//计算差多少分钟
            long sec = diff / msCons;//计算差多少秒//输出结果
            if (hour > 0) {
                diffStr = String.valueOf(hour) + "小时前";
            } else if (min > 0) {
                diffStr = String.valueOf(min) + "分钟前";
            } else {
                diffStr = String.valueOf(sec) + "秒前";
            }
        } else {
            diffStr = datetimeSdf.format(preDate);
        }
        return diffStr;
    }

    /**
     * 向前推算日期.
     * <p>Description: </p>
     * <p>Create Time: 2017/3/24 0024 10:23 </p>
     * @author 910204
     * @param days 天数
     * @param months 月数
     * @param years 年数
     * @param formatStr 格式
     * @return 返回按formatStr格式化的时间字符串
     */
    public static String forwardDate(int days, int months, int years, String formatStr) {
        SimpleDateFormat dateSdf = new SimpleDateFormat(formatStr);
        Date curDate = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(curDate);
        calendar.add(Calendar.DATE, 0 - days);
        calendar.add(Calendar.MONTH, 0 - months);
        calendar.add(Calendar.YEAR, 0 - years);
        curDate = calendar.getTime();
        return dateSdf.format(curDate);
    }

    /**
     * 向前推算日期.
     * <p>Description: </p>
     * <p>Create Time: 2017/3/24 0024 10:23 </p>
     * @author 910204
     * @param days 天数
     * @param months 月数
     * @param formatStr 格式
     * @return 返回按formatStr格式化的时间字符串
     */
    public static String forwardDate(int days, int months, String formatStr) {
        return forwardDate(days, months, 0, formatStr);
    }

    /**
     * 向前推算日期.
     * <p>Description: </p>
     * <p>Create Time: 2017/3/24 0024 10:22 </p>
     * @author 910204
     * @param days 天数
     * @param formatStr 格式
     * @return 返回按formatStr格式化的时间字符串
     */
    public static String forwardDate(int days, String formatStr) {
        return forwardDate(days, 0, formatStr);
    }
}
