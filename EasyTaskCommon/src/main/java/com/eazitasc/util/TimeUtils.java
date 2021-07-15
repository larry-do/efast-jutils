package com.eazitasc.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

public class TimeUtils {
    public static String DDHHMM = "ddHHmm";
    public static String DD_MM_YYYY_HH_MM_SS = "dd/MM/yyyy HH:mm:ss";
    public static String DD_MM_YYYY = "dd/MM/yyyy";
    public static String MM_YYYY = "MM/yyyy";
    public static String HH_MM_SS = "HH:mm:ss";
    public static String DD_MM_YYYY_HH_MM = "dd/MM/yyyy HH:mm";
    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static String YYYYMMDD_HHMMSS = "yyyyMMdd_HHmmss";

    public static Date getCurrentSystemDate() {
        return new Date();
    }

    public static Date parseDate(String str, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(str);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static String formatDate(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public static Date addMonth(Date date, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, month);
        return calendar.getTime();
    }

    public static Date getEndOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    public static Date getStartOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getYesterday(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }

    public static long dateDiff(Date from, Date to) {
        return (to.getTime() - from.getTime()) / (24 * 60 * 60 * 1000);
    }

    public static String getTimespan(Date startDate, Date endDate, Locale locale) {
        Duration duration = Duration.between(startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(), endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        long second = duration.toMillis() / 1000;
        long minute = duration.toMinutes();
        long hour = duration.toHours();
        long day = duration.toDays();
        long week = duration.toDays() / 7;
        long year = duration.toDays() / 365;

        final ResourceBundle messages = ResourceBundle.getBundle("i18n/messages", locale);
        if (year > 0) return year + " " + (year > 1 ? messages.getString("years_ago") :  messages.getString("year_ago"));
        else if (week > 0) return week + " " + (week > 1 ?  messages.getString("weeks_ago") : messages.getString("week_ago"));
        else if (day > 0) return day + " " + (day > 1 ? messages.getString("days_ago") : messages.getString("day_ago"));
        else if (hour > 0) return hour + " " + (hour > 1 ? messages.getString("hours_ago") : messages.getString("hour_ago"));
        else if (minute > 0) return minute + " " + (minute > 1 ? messages.getString("minutes_ago") : messages.getString("minute_ago"));
        else if (second > 30) return second + " " + messages.getString("seconds_ago");
        else return messages.getString("just_now");
    }
}
