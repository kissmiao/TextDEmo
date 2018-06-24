package com.commonadapter.common.util;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
    /**
     * date string to Date
     *
     * @param date eg:2016-10-17
     * @return
     */
    public static Date stringToDate(String date) {
        String[] split = date.split("-");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, Integer.valueOf(split[0]));
        calendar.set(Calendar.MONTH, Integer.valueOf(split[1]) - 1);
        calendar.set(Calendar.DAY_OF_MONTH, Integer.valueOf(split[2]));
        calendar.set(Calendar.HOUR, Integer.valueOf(0));
        calendar.set(Calendar.MINUTE, Integer.valueOf(0));
        calendar.set(Calendar.SECOND, Integer.valueOf(0));
        calendar.set(Calendar.MILLISECOND, Integer.valueOf(0));
        return calendar.getTime();
    }

    @SuppressLint("SimpleDateFormat")
    public static String transferLongToDate(int type, Long millSec) {
        millSec = millSec * 1000;
        String str = "";
        switch (type) {
            case 0:
                str = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINESE).format(millSec);
                break;
            case 1:
                str = new SimpleDateFormat("MM-dd HH:mm", Locale.CHINESE).format(millSec);
                break;
            case 2:
                str = new SimpleDateFormat("yyyy", Locale.CHINESE).format(millSec);
                break;
            case 3:
                str = new SimpleDateFormat("MM", Locale.CHINESE).format(millSec);
                break;
            case 4:
                str = new SimpleDateFormat("dd", Locale.CHINESE).format(millSec);
                break;
            case 5:
                str = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINESE).format(millSec);
                break;
            case 6:
                str = new SimpleDateFormat("MM-dd", Locale.CHINESE).format(millSec);
                break;
            default:
                break;
        }

        return str;
    }

    /**
     * @return eg:09-29
     */
    public static String formatTheDateToMM_dd(Date date, int type) {
        String str = "";
        switch (type) {
            case 0:
                str = new SimpleDateFormat("MM-dd", Locale.CHINESE).format(date);
                break;
            case 1:
                str = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINESE).format(date);
                break;
            case 2:
                str = new SimpleDateFormat("dd", Locale.CHINESE).format(date);
                break;
            case 3:
                str = new SimpleDateFormat("MM", Locale.CHINESE).format(date);
                break;
            case 4:
                str = new SimpleDateFormat("EEEE", Locale.CHINESE).format(date);
                break;
            case 5:
                str = new SimpleDateFormat("yyyy", Locale.CHINESE).format(date);
                break;
            case 6:
                str = new SimpleDateFormat("MM-dd HH:mm", Locale.CHINESE).format(date);
                break;

        }
        return str;
    }

    /**
     * @param amount :back or forward day's count
     * @return
     */
    public static Date getTheDayBeforeToDayOrAfter(Date date, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, amount);
        return calendar.getTime();
    }

    /**
     * @param date
     * @return 1 to 7 1:Sunday, 7:Saturday
     */
    public static int getTheDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 时间戳转换成字符窜
     *
     * @param time
     * @return
     */

    public static String getDateToString(long time) {
        Date d = new Date(time);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        return sf.format(d);
    }

    /**
     * 时间戳转换成字符窜 hh:mm
     *
     * @param time
     * @return
     */

    public static String getDateToStringhhmm(long time) {
        Date d = new Date(time * 1000);
        SimpleDateFormat sf = new SimpleDateFormat("HH:mm");
        return sf.format(d);
    }

    public static long toTimeStamp(Date date) {
        return date.getTime();
    }

    /**
     * yyyy-MM-dd 转时间搓
     *
     * @param
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static long stringToTime(long time) {
        Date dateString = new Date(time);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        String timeString = simpleDateFormat.format(dateString);
        Date date = null;
        try {
            date = simpleDateFormat.parse(timeString);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }

    public static boolean isExpectMoth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // calendar.add(Calendar.MONTH, 1);

        long end_time = (DateUtil.stringToTime(calendar.getTimeInMillis()));// 结束时间
        long current_time = (new Date()).getTime();
        boolean isExpectMoth = false;
        if (end_time > current_time) {
            isExpectMoth = true;
        }
        return isExpectMoth;

    }


    /**
     * 返回unix时间戳 (1970年至今的秒数)
     *
     * @return
     */

    public static long getUnixStamp() {

        return System.currentTimeMillis() / 1000;

    }

    /**
     * 得到昨天的日期
     *
     * @return
     */

    public static String getYestoryDate() {

        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.DATE, -1);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String yestoday = sdf.format(calendar.getTime());

        return yestoday;

    }

    /**
     * 得到今天的日期
     *
     * @return
     */

    public static String getTodayDate() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String date = sdf.format(new Date());

        return date;

    }

    /**
     * 时间戳转化为时间格式
     *
     * @param timeStamp
     * @return
     */

    public static String timeStampToStr(long timeStamp) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String date = sdf.format(timeStamp * 1000);

        return date;

    }

    /**
     * 得到日期   yyyy-MM-dd
     *
     * @param timeStamp 时间戳
     * @return
     */

    public static String formatDate(long timeStamp) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String date = sdf.format(timeStamp * 1000);

        return date;

    }

    /**
     * 得到时间  HH:mm:ss
     *
     * @param timeStamp 时间戳
     * @return
     */

    public static String getTime(long timeStamp) {

        String time = null;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String date = sdf.format(timeStamp * 1000);

        String[] split = date.split("\\s");

        if (split.length > 1) {

            time = split[1];

        }

        return time;

    }

    /**
     * 将一个时间戳转换成提示性时间字符串，如刚刚，1秒前
     *
     * @param timeStamp
     * @return
     */

    public static String convertTimeToFormat(long timeStamp) {

        long curTime = System.currentTimeMillis() / (long) 1000;

        long time = curTime - timeStamp;

        if (time < 60 && time >= 0) {

            return "刚刚";

        } else if (time >= 60 && time < 3600) {

            return time / 60 + "分钟前";

        } else if (time >= 3600 && time < 3600 * 24) {

            return time / 3600 + "小时前";

        } else if (time >= 3600 * 24 && time < 3600 * 24 * 30) {

            return time / 3600 / 24 + "天前";

        } else if (time >= 3600 * 24 * 30 && time < 3600 * 24 * 30 * 12) {

            return time / 3600 / 24 / 30 + "个月前";

        } else if (time >= 3600 * 24 * 30 * 12) {

            return time / 3600 / 24 / 30 / 12 + "年前";

        } else {

            return "刚刚";

        }

    }

    /**
     * 将一个时间戳转换成提示性时间字符串，(多少分钟)
     *
     * @param timeStamp
     * @return
     */

    public static String timeStampToFormat(long timeStamp) {

        long curTime = System.currentTimeMillis() / (long) 1000;

        long time = curTime - timeStamp;

        return time / 60 + "";

    }

    // 将字符串转为时间戳


    public static String getTime(String user_time) {
        String re_time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d;
        try {
            d = sdf.parse(user_time);
            long l = d.getTime();
            String str = String.valueOf(l);
            re_time = str.substring(0, 10);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return re_time;
    }

    /**
     * 判断时间是否是今天
     * @param date
     * @return
     */
    public static boolean isToday(Date date) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        if (fmt.format(date).toString().equals(fmt.format(new Date()).toString())) {//格式化为相同格式
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取昨天时间
     * @param date
     * @return
     */
    public static Date getUpDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        return date;
    }

    /**
     * 获取明天时间
     * @param date
     * @return
     */
    public static Date getNextDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        date = calendar.getTime();
        return date;
    }
}
