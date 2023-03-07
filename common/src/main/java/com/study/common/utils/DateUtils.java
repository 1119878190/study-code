package com.study.common.utils;



import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期操作工具类
 *
 * @author fangzhongwei
 */
public class DateUtils {

    private static final long MILLIS_PER_SECOND = 1000L;
    private static final long MILLIS_PER_MINUTE = 60 * MILLIS_PER_SECOND;
    private static final long MILLIS_PER_HOUR = 60 * MILLIS_PER_MINUTE;
    private static final long MILLIS_PER_DAY = 24 * MILLIS_PER_HOUR;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static Date add(Date date, int field, int value) {
        if (null == date) {
            throw new IllegalArgumentException("The date must not be null.");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(field, value);
        return calendar.getTime();
    }

    public static Date addWeek(Date date, int value) {
        return add(date, Calendar.WEEK_OF_MONTH, value);
    }

    public static Date addYear(Date date, int value) {
        return add(date, Calendar.YEAR, value);
    }

    public static Date addMonth(Date date, int value) {
        return add(date, Calendar.MONTH, value);
    }

    public static Date addDay(Date date, int value) {
        return add(date, Calendar.DAY_OF_MONTH, value);
    }

    public static Date addHour(Date date, int value) {
        return add(date, Calendar.HOUR_OF_DAY, value);
    }

    public static Date addMinute(Date date, int value) {
        return add(date, Calendar.MINUTE, value);
    }

    public static Date addSecond(Date date, int value) {
        return add(date, Calendar.SECOND, value);
    }

    public static Date addMillisecond(Date date, int value) {
        return add(date, Calendar.MILLISECOND, value);
    }

    public static Date set(Date date, int field, int value) {
        if (null == date) {
            throw new IllegalArgumentException("The date must not be null.");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(field, value);
        return calendar.getTime();
    }

    public static Date setYear(Date date, int value) {
        return set(date, Calendar.YEAR, value);
    }

    public static Date setMonth(Date date, int value) {
        return set(date, Calendar.MONTH, value);
    }

    public static Date setDay(Date date, int value) {
        return set(date, Calendar.DAY_OF_MONTH, value);
    }

    public static Date setHour(Date date, int value) {
        return set(date, Calendar.HOUR_OF_DAY, value);
    }

    public static Date setMinute(Date date, int value) {
        return set(date, Calendar.MINUTE, value);
    }

    public static Date setSecond(Date date, int value) {
        return set(date, Calendar.SECOND, value);
    }

    public static Date setMillisecond(Date date, int value) {
        return set(date, Calendar.MILLISECOND, value);
    }

    public static Calendar toCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static Date now() {
        return Calendar.getInstance().getTime();
    }

    public static String getDate() {
        return DateFormatUtils.formatDate(Calendar.getInstance().getTime());
    }

    public static String getDatetime() {
        return DateFormatUtils.formatDatetime(Calendar.getInstance().getTime());
    }

    public static boolean isSameDay(Date date1, Date date2) {
        if (null == date1 || null == date2) {
            throw new IllegalArgumentException("The date must not be null.");
        }
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);
        return isSameDay(calendar1, calendar2);
    }

    public static boolean isSameDay(Calendar calendar1, Calendar calendar2) {
        if (null == calendar1 || null == calendar2) {
            throw new IllegalArgumentException("The date must not be null.");
        }
        return (calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR))
            && (calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH))
            && (calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH));
    }

    public static Date addTwo(Date date1, Date date2) {
        if (null == date1 || null == date2) {
            throw new IllegalArgumentException("The date must not be null.");
        }
        return new Date(date1.getTime() + date2.getTime());
    }

    public static int minusYear(Date date1, Date date2) {
        if (null == date1 || null == date2) {
            throw new IllegalArgumentException("The date must not be null.");
        }
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);
        return calendar1.get(Calendar.YEAR) - calendar2.get(Calendar.YEAR);
    }

    public static int minusMonth(Date date1, Date date2) {
        if (null == date1 || null == date2) {
            throw new IllegalArgumentException("The date must not be null.");
        }
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);
        return minusYear(date1, date2) * 12 + calendar1.get(Calendar.MONTH) - calendar2.get(Calendar.MONTH);
    }

    public static int minusDay(Date date1, Date date2) {
        return (int)(minusMillisecond(date1, date2) / MILLIS_PER_DAY);
    }

    public static int minusHour(Date date1, Date date2) {
        return (int)(minusMillisecond(date1, date2) / MILLIS_PER_HOUR);
    }

    public static int minusMinute(Date date1, Date date2) {
        return (int)(minusMillisecond(date1, date2) / MILLIS_PER_MINUTE);
    }

    public static long minusSecond(Date date1, Date date2) {
        return minusMillisecond(date1, date2) / MILLIS_PER_SECOND;
    }

    public static long minusMillisecond(Date date1, Date date2) {
        if (null == date1 || null == date2) {
            throw new IllegalArgumentException("The date must not be null.");
        }
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);
        return calendar1.getTimeInMillis() - calendar2.getTimeInMillis();
    }

    //一天的开始  00:00:00
    public static Date startOfDay(Date date){
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
        LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
        return Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }
    //一个月的开始
    public static Date startOfMonth(Date date){
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
        LocalDateTime first = localDateTime.with(TemporalAdjusters.firstDayOfMonth());
        first = first.with(LocalTime.MIN);
        return Date.from(first.atZone(ZoneId.systemDefault()).toInstant());
    }
    //一年的开始
    public static Date startOfYear(Date date){
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
        LocalDateTime first = localDateTime.with(TemporalAdjusters.firstDayOfYear());
        first = first.with(LocalTime.MIN);
        return Date.from(first.atZone(ZoneId.systemDefault()).toInstant());
    }
    //某年某月当月的天数
    public static  long dayCountOfMonthInDate(Date date){
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
        LocalDateTime first = localDateTime.with(TemporalAdjusters.firstDayOfMonth());
        LocalDateTime last = localDateTime.with(TemporalAdjusters.lastDayOfMonth());
        return first.until(last, ChronoUnit.DAYS)+1;
    }

    //日期天数差
    public static  long daysDistance(Date startTime,Date endTime) {
        LocalDateTime start = LocalDateTime.ofInstant(Instant.ofEpochMilli(startTime.getTime()), ZoneId.systemDefault());
        LocalDateTime end = LocalDateTime.ofInstant(Instant.ofEpochMilli(endTime.getTime()), ZoneId.systemDefault());
        return start.until(end, ChronoUnit.DAYS)+1;
    }

    //日期月数差
    public static  long monthsDistance(Date startTime, Date endTime) {
        LocalDateTime start = LocalDateTime.ofInstant(Instant.ofEpochMilli(startTime.getTime()), ZoneId.systemDefault());
        LocalDateTime end = LocalDateTime.ofInstant(Instant.ofEpochMilli(endTime.getTime()), ZoneId.systemDefault());
        return start.until(end, ChronoUnit.MONTHS)+1;
    }
    //日期年数差
    public static  long yearsDistance(Date startTime, Date endTime) {
        LocalDateTime start = LocalDateTime.ofInstant(Instant.ofEpochMilli(startTime.getTime()), ZoneId.systemDefault());
        LocalDateTime end = LocalDateTime.ofInstant(Instant.ofEpochMilli(endTime.getTime()), ZoneId.systemDefault());
        return start.until(end, ChronoUnit.YEARS)+1;
    }
}
