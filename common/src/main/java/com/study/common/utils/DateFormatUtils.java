package com.study.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

/**
 * 日期时间格式和字符串转换工具类
 *
 * @author fangzhongwei
 */
public class DateFormatUtils {

    private static final ThreadLocal<Map<String, SimpleDateFormat>> DATE_LOCAL =
        new ThreadLocal<Map<String, SimpleDateFormat>>();
    private static final String DEFAULT_DATETIMEFORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String DEFAULT_DATEFORMAT = "yyyy-MM-dd";
    private static final String UTC_DATETIMEFORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    private static SimpleDateFormat getDateFormat(String format) {
        SimpleDateFormat sdf = null;
        Map<String, SimpleDateFormat> sdfMap = DATE_LOCAL.get();
        if (null == sdfMap) {
            sdf = new SimpleDateFormat(format);
            if (UTC_DATETIMEFORMAT.equals(format)) {
                sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            }
            sdfMap = new HashMap<String, SimpleDateFormat>();
            sdfMap.put(format, sdf);
            DATE_LOCAL.set(sdfMap);
        } else {
            sdf = sdfMap.get(format);
            if (null == sdf) {
                sdf = new SimpleDateFormat(format);
                if (UTC_DATETIMEFORMAT.equals(format)) {
                    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
                }
                sdfMap.put(format, sdf);
            }
        }

        return sdf;
    }

    public static String format(String format, Date date) {
        return getDateFormat(format).format(date);
    }

    public static Date parse(String format, String date) throws ParseException {
        return getDateFormat(format).parse(date);
    }

    public static String formatDatetime(Date date) {
        return getDateFormat(DEFAULT_DATETIMEFORMAT).format(date);
    }

    public static Date parseDatetime(String date) throws ParseException {
        return getDateFormat(DEFAULT_DATETIMEFORMAT).parse(date);
    }

    public static String formatDate(Date date) {
        return getDateFormat(DEFAULT_DATEFORMAT).format(date);
    }

    public static Date parseDate(String date) throws ParseException {
        return getDateFormat(DEFAULT_DATEFORMAT).parse(date);
    }

    public static String formatToUTC(Date date) {
        return getDateFormat(UTC_DATETIMEFORMAT).format(date);
    }

    public static Date parseUTCDatetime(String date) throws ParseException {
        return getDateFormat(UTC_DATETIMEFORMAT).parse(date);
    }

}
