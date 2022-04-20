package com.mall.common.base.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author DongJunTao
 * @Description 时间工具类
 * @Date 2021/4/24 11:43
 * @Version 1.0
 */
public class DateUtil {

    /**
     * 根据时间字符串和转换格式 获取Date类型值
     * @param format
     * @param dateStr
     * @return
     * @throws ParseException
     */
    public static Date getDateByDateString(String dateStr, String format) throws ParseException {
        SimpleDateFormat ft = new SimpleDateFormat (format);
        return ft.parse(dateStr);
    }
}
