package com.example.authmodule.util;

import java.util.Date;

/**
 * @author Hongyu Zhang
 * @date 2022/9/18 17:41
 */
public class DateUtil {

    private static Integer HOURS_SECONDS = 3600;
    public static Date getHoursInForCurrentDate(Date date, Integer hours){
        return new Date(date.getTime() + (long) hours * HOURS_SECONDS*1000);
    }
}
