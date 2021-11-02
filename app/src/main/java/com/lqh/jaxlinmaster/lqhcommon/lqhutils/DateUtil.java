package com.lqh.jaxlinmaster.lqhcommon.lqhutils;

import android.annotation.SuppressLint;

import com.lqh.jaxlinmaster.lqhcommon.lqhutils.constant.TimeConstants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
/**
 * 日期工具类
 *
 * @author lqh
 */
public class DateUtil {

    /**
     * 日期类型 *
     */
    public static final String yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";
    public static final String yyyyMMddHHmm = "yyyy-MM-dd HH:mm";
    public static final String yyyyMMdd = "yyyy-MM-dd";
    public static final String yyyyMM = "yyyy-MM";
    public static final String yyyy_MM_dd = "yyyy.MM.dd";
    public static final String MM_DD = "MM.dd";
    public static final String HH_mm_ss = "HH:mm:ss";
    public static final String HH_mm = "HH:mm";

    public static final String CHNIESE_yyyyMMddHHmmss = "yyyy年M月d日 HH:mm:ss";
    public static final String CHNIESE_yyyyMMddHHmm = "yyyy年MM月dd日 HH:mm";
    public static final String CHNIESE_yyyyMMdd  = "yyyy年MM月dd日";
    public static final String CHNIESE_yyyyMM = "yyyy年MM月";
    public static final String  CHNIESE_MMdd = "MM月dd日";
    public static final String CHNIESEdd = "dd日";
    public static final String CHNIESEMM = "MM月";

    public static final String SKEW_yyyyMMddHHmmss = "yyyy/MM/dd HH:mm:ss";
    public static final String SKEW_yyyyMMddHHmm = "yyyy/MM/dd HH:mm";
    public static final String SKEW_yyyyMMdd = "yyyy/MM/dd";
    public static final String SKEW_yyyyMM = "yyyy/MM";
    public static final String SKEW_MMdd = "MM/dd";
    private static final String[] CHINESE_ZODIAC =
            {"猴", "鸡", "狗", "猪", "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊"};
    private static final String[] ZODIAC       = {
            "水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座",
            "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "魔羯座"
    };
    private static final int[]    ZODIAC_FLAGS = {20, 19, 21, 21, 21, 22, 23, 23, 23, 24, 23, 22};
    private DateUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 将Date类型转换为日期字符串
     *
     * @param date Date对象
     * @param pattern 需要的日期格式
     * @return 按照需求格式的日期字符串
     */
    public static String dateToString(Date date, String pattern)
    {
        return new SimpleDateFormat(pattern, Locale.CHINA).format(date);
    }

    /**
     * 将日期字符串转换为Date类型
     *
     * @param dateStr 日期字符串
     * @param pattern 日期字符串格式
     * @return Date对象
     */
    public static Date stringToDate(String dateStr, String pattern) {
        try {
            return new SimpleDateFormat(pattern, Locale.CHINA).parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 将long类型转换为日期字符串
     *
     * @param ms   毫秒
     * @param pattern 需要的日期格式
     * @return 按照需求格式的日期字符串
     */
    public static String millisToString(long ms, String pattern) {

            SimpleDateFormat df = new SimpleDateFormat(pattern, Locale.CHINA);
            return df.format(new Date(ms));
    }

    /**
     * 将long类型转换为日期字符串
     *
     * @param dateStr   毫秒
     * @param pattern 需要的日期格式
     * @return 按照需求格式的日期字符串
     */
    public static long stringToMillis(String dateStr, String pattern) {

        SimpleDateFormat df = new SimpleDateFormat(pattern, Locale.CHINA);
        try {
            return df.parse(dateStr).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
    /**
     * Date to the milliseconds. date转换为时间
     *
     * @param date The date.
     * @return the milliseconds
     */
    public static long dateToMillis( Date date) {
        return date.getTime();
    }


    /**
     * Milliseconds to the date.时间转换为date
     *
     * @param millis The milliseconds.
     * @return the date
     */
    public static Date millisToDate(final long millis) {
        return new Date(millis);
    }


    /**
     * 得到年
     *
     * @param date Date对象
     * @return 年
     */
    public static int getYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.YEAR);
    }
    /**
     * 得到月 返回的是中国的月份
     *
     * @param date Date对象
     * @return 月
     */
    public static int getMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MONTH) + 1;

    }
    /**
     * 这一天在是这个月的第多少天,也就是几号
     *
     * @param date Date对象
     * @return 日
     */
    public static int getDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**获得周几。1-7表示周日到周六,
     * @param time
     * @return
     */
    public static int getWeekInt(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * Return whether it is leap year.判断是否是闰年
     *
     * @param millis The milliseconds.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isLeapYear(final long millis) {
        return isLeapYear(millisToDate(millis));
    }

    /**
     * Return whether it is leap year.判断是否是闰年
     *
     * @param date The date.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isLeapYear(final Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        return isLeapYear(year);
    }
    /**
     * Return whether it is leap year.判断是否是闰年
     *
     * @param year The year.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isLeapYear(final int year) {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }

    /**
     * Return whether it is today.判断是否今天
     *
     * @param millis The milliseconds.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isToday(final long millis) {
        long wee = getZeroToday();
        return millis >= wee && millis < wee + TimeConstants.DAY;
    }

    private static long getZeroToday() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    /**中国的获取星期几
     * @param time
     * @return
     */
    public static String getChineseWeek(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        int week = cal.get(Calendar.DAY_OF_WEEK)-1;
            if(week==0){
            week=7;
            }
        switch (week) {
            case 1:
                return "星期一";
            case 2:
                return "星期二";
            case 3:
                return "星期三";
            case 4:
                return "星期四";
            case 5:
                return "星期五";
            case 6:
                return "星期六";
            case 7:
                return "星期日";
            default:
                return "";
        }
    }

    /*获取美国的星期几*/
    public static String getUsWeek(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        int i = cal.get(Calendar.DAY_OF_WEEK);
        switch (i) {
            case 1:
                return "星期日";
            case 2:
                return "星期一";
            case 3:
                return "星期二";
            case 4:
                return "星期三";
            case 5:
                return "星期四";
            case 6:
                return "星期五";
            case 7:
                return "星期六";
            default:
                return "";
        }
    }
    /**
     * Return the Chinese zodiac.获取生肖
     *
     * @param date The date.
     * @return the Chinese zodiac
     */
    public static String getChineseZodiac(final Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return CHINESE_ZODIAC[cal.get(Calendar.YEAR) % 12];
    }
    /**
     * Return the zodiac.获取星座
     *
     * @param date The date.
     * @return the zodiac
     */
    public static String getZodiac(final Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return getZodiac(month, day);
    }
    /**
     * Return the zodiac.
     *
     * @param month The month.
     * @param day   The day.
     * @return the zodiac
     */
    public static String getZodiac(final int month, final int day) {
        return ZODIAC[day >= ZODIAC_FLAGS[month - 1]
                ? month - 1
                : (month + 10) % 12];
    }


    /**
     * Time span in unit to milliseconds.以 unit 为单位的时间长度与毫秒时间戳互转
     *
     * @param timeSpan The time span.某个时间
     * @param unit     The unit of time span.这个时间的单位
     *                 <ul>
     *                 <li>{@link TimeConstants#MSEC}</li>
     *                 <li>{@link TimeConstants#SEC }</li>
     *                 <li>{@link TimeConstants#MIN }</li>
     *                 <li>{@link TimeConstants#HOUR}</li>
     *                 <li>{@link TimeConstants#DAY }</li>
     *                 </ul>
     * @return milliseconds 时间段转换为毫秒时间戳
     */
    public static long timeSpan2Millis(final long timeSpan, @TimeConstants.Unit final int unit) {
        return timeSpan * unit;
    }

    /**
     * Milliseconds to time span in unit.以 unit 为单位的时间长度与毫秒时间戳互转
     *
     * @param millis The milliseconds.
     * @param unit   The unit of time span.
     *               <ul>
     *               <li>{@link TimeConstants#MSEC}</li>
     *               <li>{@link TimeConstants#SEC }</li>
     *               <li>{@link TimeConstants#MIN }</li>
     *               <li>{@link TimeConstants#HOUR}</li>
     *               <li>{@link TimeConstants#DAY }</li>
     *               </ul>
     * @return time span in unit
     */
    public static long millis2TimeSpan(final long millis, @TimeConstants.Unit final int unit) {
        return millis / unit;
    }

    /**
     * Milliseconds to fit time span.毫秒时间戳转合适时间长度
     *
     * @param millis    The milliseconds.
     *                  <p>millis &lt;= 0, return null</p>
     * @param precision The precision of time span.
     *                  <ul>
     *                  <li>precision = 0, return null</li>
     *                  <li>precision = 1, return 天</li>
     *                  <li>precision = 2, return 天, 小时</li>
     *                  <li>precision = 3, return 天, 小时, 分钟</li>
     *                  <li>precision = 4, return 天, 小时, 分钟, 秒</li>
     *                  <li>precision &gt;= 5，return 天, 小时, 分钟, 秒, 毫秒</li>
     *                  </ul>
     * @return fit time span
     */
    @SuppressLint("DefaultLocale")
    public static String millis2FitTimeSpan(long millis, int precision) {
        if (millis <= 0 || precision <= 0) return null;
        StringBuilder sb = new StringBuilder();
        String[] units = {"天", "小时", "分钟", "秒", "毫秒"};
        int[] unitLen = {86400000, 3600000, 60000, 1000, 1};
        precision = Math.min(precision, 5);
        for (int i = 0; i < precision; i++) {
            if (millis >= unitLen[i]) {
                long mode = millis / unitLen[i];
                millis -= mode * unitLen[i];
                sb.append(mode).append(units[i]);
            }
        }
        return sb.toString();
    }



    /**
     * Return the friendly time span by now. 获取友好型与当前时间的差
     *
     * @param millis The milliseconds.
     * @return the friendly time span by now
     * <ul>
     * <li>如果小于 1 秒钟内，显示刚刚</li>
     * <li>如果在 1 分钟内，显示 XXX秒前</li>
     * <li>如果在 1 小时内，显示 XXX分钟前</li>
     * <li>如果在 1 小时外的今天内，显示今天15:32</li>
     * <li>如果是昨天的，显示昨天15:32</li>
     * <li>其余显示，2016-10-15</li>
     * <li>时间不合法的情况下显示输入的时间
     * </ul>
     * *  %tF	年-月-日	2018-05-13
         %tD	月/日/年	05/13/18
         %tc	全部时间日期	星期日 五月 13 15:44:21 CST 2018
         %tr	时分秒 PM	03:44:21 下午
            %tT	时分秒	15:44:21
          %tR	时分	15:44
     */
    public static String getFriendTimeNow(final long millis) {
        long now = System.currentTimeMillis();
        long span = now - millis;
        if (span < 0)
            return DateUtil.millisToString(millis, DateUtil.yyyyMMddHHmm);
        if (span < TimeConstants.SEC) {
            return "刚刚";
        } else if (span < TimeConstants.MIN) {
            return String.format(Locale.CHINA, "%d秒前", span / TimeConstants.SEC);
        } else if (span < TimeConstants.HOUR) {
            return String.format(Locale.CHINA, "%d分钟前", span / TimeConstants.MIN);
        }
        // 获取当天 00:00
        long wee = getZeroToday();
        if (millis >= wee) {

            return String.format("今天%s", DateUtil.millisToString(millis, DateUtil.HH_mm));
        } else if (millis >= wee - TimeConstants.DAY) {
            return String.format("昨天%s", DateUtil.millisToString(millis, DateUtil.HH_mm));
        } else {
            return DateUtil.millisToString(millis, DateUtil.yyyyMMddHHmm);
        }
    }
}