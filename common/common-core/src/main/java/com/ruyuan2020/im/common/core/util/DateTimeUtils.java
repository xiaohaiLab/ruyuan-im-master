package com.ruyuan2020.im.common.core.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

/**
 * 时间工具类
 *
 * @author case
 */
public class DateTimeUtils {

    public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    /**
     * 格式化时间为字符串
     *
     * @param localDateTime 时间
     * @param format        格式
     * @return 格式化后的时间字符串
     */
    public static String formatDateTime(LocalDateTime localDateTime, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return formatter.format(localDateTime);
    }

    /**
     * 使用默认格式格式化时间为字符串
     *
     * @param localDateTime 时间
     * @return 格式化后的时间字符串
     */
    public static String formatDateTime(LocalDateTime localDateTime) {
        return formatDateTime(localDateTime, DEFAULT_DATETIME_FORMAT);
    }

    public static String formatDateTime(Date date, String format) {
        LocalDateTime localDateTime = date2LocalDateTime(date);
        return formatDateTime(localDateTime, format);
    }

    public static String formatDateTime(Date date) {
        LocalDateTime localDateTime = date2LocalDateTime(date);
        return formatDateTime(localDateTime);
    }

    public static String formatDate(LocalDate localDate, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return formatter.format(localDate);
    }

    public static String formatDate(LocalDate localDate) {
        return formatDate(localDate, DEFAULT_DATE_FORMAT);
    }

    public static String formatDate(Date date, String format) {
        LocalDate localDate = date2LocalDate(date);
        return formatDate(localDate, format);
    }

    public static String formatDate(Date date) {
        LocalDate localDate = date2LocalDate(date);
        return formatDate(localDate);
    }

    public static LocalDate date2LocalDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDate();
    }

    public static LocalDateTime date2LocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDateTime();
    }

    /**
     * 解析字符串为时间
     *
     * @param dateTimeStr 字符串时间
     * @param format      格式
     * @return 时间
     */
    public static LocalDateTime parseDateTime(String dateTimeStr, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(dateTimeStr, formatter);
    }

    /**
     * 使用默认格式解析字符串为时间
     *
     * @param dateTimeStr 字符串时间
     * @return 时间
     */
    public static LocalDateTime parseDateTime(String dateTimeStr) {
        return parseDateTime(dateTimeStr, DEFAULT_DATETIME_FORMAT);
    }

    /**
     * 使用默认格式解析字符串为时间
     *
     * @param dateTimeStr 字符串时间
     * @return 时间
     */
    public static LocalDate parseDate(String dateTimeStr) {
        return parseDate(dateTimeStr, DEFAULT_DATE_FORMAT);
    }

    /**
     * 解析字符串为时间
     *
     * @param dateTimeStr 字符串时间
     * @param format      格式
     * @return 时间
     */
    public static LocalDate parseDate(String dateTimeStr, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalDate.parse(dateTimeStr, formatter);
    }

    /**
     * 解析字符串为时间
     *
     * @param dateTimeStr 字符串时间
     * @param format      格式
     * @param locale      时区
     * @return 时间
     */
    public static LocalDate parseDate(String dateTimeStr, String format, Locale locale) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format, locale);
        return LocalDate.parse(dateTimeStr, formatter);
    }

    /**
     * 使用默认格式解析字符串为时间
     *
     * @param timestamp 字符串时间
     * @return 时间
     */
    public static LocalDateTime parseTimestamp(Integer timestamp) {
        long ts = (long) timestamp * 1000;
        Instant instant = Instant.ofEpochMilli(ts);
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    public static LocalDateTime parseTimestamp(Long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    /**
     * 获取当前时间
     *
     * @return 当前时间
     */
    public static LocalDateTime currentDateTime() {
        return LocalDateTime.now();
    }

    public static int getSecondTimestamp() {
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        return Integer.parseInt(timestamp);
    }

    public static String differenceString(final int differenceSecond) {
        int different = differenceSecond;
        int secondsInMilli = 1;
        int minutesInMilli = secondsInMilli * 60;
        int hoursInMilli = minutesInMilli * 60;
        int daysInMilli = hoursInMilli * 24;

//        int elapsedDays = different / daysInMilli;
//        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        return String.format("%dh%dm%ds%n",
                elapsedHours, elapsedMinutes, elapsedSeconds);
    }
}
