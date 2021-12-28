package com.itdfq.upload.utils;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author GocChin
 * @Date 2021/12/28 21:43
 * @Blog: itdfq.com
 * @QQ: 909256107
 * @Description:
 */
public class DateUtils {

    /**
     * 将指定字符串转换成日期
     *
     * @param date
     * @param datePattern
     * @return
     */
    public static Date getFormatDate(String date, String datePattern) {
        SimpleDateFormat sd = new SimpleDateFormat(datePattern);
        return sd.parse(date, new ParsePosition(0));
    }


    /**
     * 将指定日期对象转换成格式化字符串
     *
     * @param date
     * @param datePattern
     * @return
     */
    public static String getFormattedString(Date date, String datePattern) {
        SimpleDateFormat sd = new SimpleDateFormat(datePattern);

        return sd.format(date);
    }

    public static final String DATE_FORMAT_1 = "yyyyMMdd";
    public static final String DATE_FORMAT_2 = "yyyy-MM-dd";
    public static final String DATE_FORMAT_3 = "yyyy/MM/dd";
    public static final String DATE_FORMAT_4 = "yyyy年MM月dd日";
    public static final String DATE_FORMAT_5 = "yyyy-MM-dd HH";
    public static final String DATE_FORMAT_6 = "yyyyMMddHHmmss";
    public static final String DATE_FORMAT_7 = "yyyyMMddHHmmssSSS";
    public static final String DATE_FORMAT_8 = "yyyy年MM月dd日HH时mm分";
    public static final String DATE_FORMAT_9 = "yyyy-MM-dd HH:mm:ss";

}
