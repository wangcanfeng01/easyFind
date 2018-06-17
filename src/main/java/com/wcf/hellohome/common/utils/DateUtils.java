package com.wcf.hellohome.common.utils;


import lombok.extern.log4j.Log4j2;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * @author WCF
 * @time 2018/4/8
 * @why 日期工具类
 **/
@Log4j2
public class DateUtils {
    /**
     * 日期格式
     */
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * @param unixTime
     * @return java.lang.String
     * @note 根据10位的时间戳获取时间字符串
     * @author WCF
     * @time 2018/6/10 20:24
     * @since v1.0
     **/
    public static String getTimeByUnixTime(Integer unixTime) {
        LocalDateTime time = LocalDateTime.ofEpochSecond(unixTime, 0, ZoneOffset.ofHours(8));
        return formatter.format(time);
    }

    /**
     * @param
     * @return int
     * @note 获取当前的UnixTime
     * @author WCF
     * @time 2018/6/10 20:25
     * @since v1.0
     **/
    public static int getCurrentUnixTime() {
        return (int) (System.currentTimeMillis() / 1000L);
    }

    /**
     * @param time
     * @return int
     * @note localDateTime转换成UnixTime
     * @author WCF
     * @time 2018/6/10 20:25
     * @since v1.0
     **/
    public static int toUnixTime(LocalDateTime time) {
        long epochSecond = time.toInstant(ZoneOffset.ofHours(8)).getEpochSecond();
        return (int) (epochSecond);
    }


    /**
     * @param
     * @return java.lang.String
     * @note 获取当前时间
     * @author WCF
     * @time 2018/6/10 20:26
     * @since v1.0
     **/
    public static String now() {
        LocalDateTime time = LocalDateTime.now();
        return formatter.format(time);
    }

    /**
     * @param
     * @return java.lang.String
     * @note 今天凌晨的时间
     * @author WCF
     * @time 2018/6/10 20:26
     * @since v1.0
     **/
    public static String todayZero() {
        LocalDateTime time = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
        return formatter.format(time);
    }

    /**
     * @param
     * @return java.lang.String
     * @note 获取明天凌晨时间字符串
     * @author WCF
     * @time 2018/6/10 20:27
     * @since v1.0
     **/
    public static String tomorrow() {
        LocalDateTime time = LocalDateTime.now().plusDays(1).truncatedTo(ChronoUnit.DAYS);
        return formatter.format(time);
    }

    /**
     * @param n
     * @return java.lang.String
     * @note 获取n秒后的时间
     * @author WCF
     * @time 2018/6/10 20:28
     * @since v1.0
     **/
    public static String getTimeAfterNSeconds(int n) {
        LocalDateTime time = LocalDateTime.now().plusSeconds(n);
        return formatter.format(time);
    }

    /**
     * @param time
     * @return java.time.LocalDateTime
     * @note 将字符串转成localDate
     * @author WCF
     * @time 2018/6/10 20:31
     * @since v1.0
     **/
    public static LocalDateTime getTime(String time) {
        LocalDateTime localDateTime = null;
        try {
            localDateTime = LocalDateTime.parse(time, formatter);
        } catch (Exception e) {
            log.error("The time string's formatter is not right");
        }
        return localDateTime;
    }

}
