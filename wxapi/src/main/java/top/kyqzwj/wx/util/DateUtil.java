package top.kyqzwj.wx.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Description:
 * Copyright: © 2019 CSNT. All rights reserved.
 * Company:CSNT
 *
 * @author kyq
 * @version 1.0
 * @Date 2020/9/8 19:56
 */
public class DateUtil {

    /**
     * 精确地3位毫秒
     */
    public static final String format_yyyyMMddHHmmssSSS = "yyyyMMddHHmmssSSS";
    /**
     * 精确到天
     */
    public static final String format_yyyy_MM_dd = "yyyy-MM-dd";
    /**
     * 精确到天
     */
    public static final String format_yyyyMMdd = "yyyyMMdd";
    /**
     * 精确地3位毫秒
     */
    public static final String format_yyyy_MM_dd_HHmmssSSS = "yyyy-MM-dd HH:mm:ss.SSS";
    /**
     * 精确地0位毫秒
     */
    public static final String format_yyyy_MM_dd_HHmmss = "yyyy-MM-dd HH:mm:ss";
    /**
     * 精确地0位毫秒
     */
    public static final String format_yyyy_MM_ddTHHmmss = "yyyy-MM-dd'T'HH:mm:ss";
    /**
     * yyyy/MM/dd的时间路径
     */
    public static final String format_yyyy_MM_dd_path = "yyyy/MM/dd";

    /**
     * 格式化日期对象
     *
     * @param date   日期对象
     * @param format 格式化字符串
     * @return
     */
    public static String formatDate(Date date, String format) {
        String result = "";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        if (date == null) {
            return null;
        } else {
            result = sdf.format(date);
        }
        return result;
    }

    /**
     * 格式化日期对象
     *
     * @param date   日期对象
     * @param format 格式化字符串
     * @return
     */
    public static Date parseDate(String date, String format) {
        Date result = null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        if (date != null && !("".equals(date))) {
            try {
                result = sdf.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
