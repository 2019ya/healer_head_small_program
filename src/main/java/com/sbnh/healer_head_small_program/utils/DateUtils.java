package com.sbnh.healer_head_small_program.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: weis
 * @create: 2021-06-15-11:44
 * @note: 时间工具类
 */
public class DateUtils {

    /**
     * 获取当前时间到24.00之间的毫秒
     */
    public static long get24TimeDifference() {
        LocalTime midnight = LocalTime.MIDNIGHT;
        LocalDate today = LocalDate.now();
        LocalDateTime todayMidnight = LocalDateTime.of(today, midnight);
        LocalDateTime localDateTime = todayMidnight.plusDays(1);
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        long time = System.currentTimeMillis();
        return instant.toEpochMilli() - time;
    }

    /**
     * 获取俩个时间相差（秒）
     *
     * @param oldLocalTime 大时间
     * @param nowLocalTime 小时间
     */
    public static Map<String, Long> getTimeDff(LocalDateTime oldLocalTime, LocalDateTime nowLocalTime) {
        long oldTime = getTimestampOfDateTime(oldLocalTime);
        long nowTime = getTimestampOfDateTime(nowLocalTime);
        long time = oldTime - nowTime;
        long day = time / (24 * 60 * 60 * 1000);
        long hour = (time / (60 * 60 * 1000) - day * 24);
        long minute = ((time / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long second = (time / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - minute * 60);
        HashMap<String, Long> map = new HashMap<>();
        map.put("day", day);
        map.put("hour", hour);
        map.put("minute", minute);
        map.put("second", second);
        return map;
    }

    /**
     * 时间戳转换成毫秒
     *
     * @param localDateTime
     * @return
     */
    public static long getTimestampOfDateTime(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return instant.toEpochMilli();
    }


    /**
     * long 转时间戳
     *
     * @param timestamp
     * @return
     */
    public static LocalDateTime getDateTimeOfTimestamp(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }


    /**
     * 将LocalDateTime转为自定义的时间格式的字符串
     *
     * @param localDateTime 时间戳
     * @param format 格式
     * @return
     */
    public static String getDateTimeAsString(LocalDateTime localDateTime, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return localDateTime.format(formatter);
    }

    /**
     * 将日期转换成 yyyy-MM-dd的字符串格式返回
     *
     * @param localDateTime 日期
     * @return 日期字符串
     */
    public static String getDateTimeAsString(LocalDateTime localDateTime) {
        return getDateTimeAsString(localDateTime, "yyyy-MM-dd");
    }

    /**
     * 将日期转换成 yyyy-MM-dd的字符串格式返回
     *
     * @return 日期字符串
     */
    public static String getDateTimeAsString() {
        return getDateTimeAsString(LocalDateTime.now(), "yyyy-MM-dd");
    }

    /**
     * 将某时间字符串转为自定义时间格式的LocalDateTime
     *
     * @param time 某时间字符串
     * @param format 自定义时间格式
     * @return
     */
    public static LocalDateTime parseStringToDateTime(String time, String format) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(time, df);
    }


    /**
     * 获取年月日时分秒
     */
    public static Map<String, Integer> getMinuteAndSecond(LocalDateTime localDateTime) {
        HashMap<String, Integer> map = new HashMap<>(8);
        map.put("year", localDateTime.getYear());
        map.put("month", localDateTime.getMonthValue());
        map.put("day", localDateTime.getDayOfMonth());
        map.put("hour", localDateTime.getHour());
        map.put("minute", localDateTime.getMinute());
        map.put("second", localDateTime.getSecond());
        return map;
    }

    /**
     * 获取时间拼接成的字符串 精确到分钟
     *
     * @param localDateTime 时间
     * @param minute 分钟数
     * @return 返回时间拼接 例如(20210101)
     */
    public static StringBuilder getCurrentTime(LocalDateTime localDateTime, int minute) {
        Map<String, Integer> minuteAndSecond = DateUtils.getMinuteAndSecond(localDateTime);
        StringBuilder str = new StringBuilder();
        str.append(minuteAndSecond.get("year")).append(minuteAndSecond.get("month")).append(minuteAndSecond.get("day"))
                .append(minuteAndSecond.get("hour")).append(minuteAndSecond.get("minute") + minute);
        return str;
    }

}
