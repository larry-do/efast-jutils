package com.kwicktask.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.ResourceBundle;

public class TimeUtils {
    public static final long MILLISECONDS_PER_DAY = 86400000L;

    public static String DDHHMM = "ddHHmm";
    public static String DD_MM_YYYY_HH_MM_SS = "dd/MM/yyyy HH:mm:ss";
    public static String DD_MM_YYYY = "dd/MM/yyyy";
    public static String MM_YYYY = "MM/yyyy";
    public static String HH_MM_SS = "HH:mm:ss";
    public static String DD_MM_YYYY_HH_MM = "dd/MM/yyyy HH:mm";
    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static String YYYYMMDD_HHMMSS = "yyyyMMdd_HHmmss";

    public static Date getCurrentSysDate() {
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

    public static Date addTime(Date date, int field, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(field, amount);
        return calendar.getTime();
    }

    public static Date addHour(Date date, int hour) {
        return addTime(date, Calendar.HOUR, hour);
    }

    public static Date addDay(Date date, int day) {
        return addTime(date, Calendar.DATE, day);
    }

    public static Date addMonth(Date date, int month) {
        return addTime(date, Calendar.MONTH, month);
    }

    public static Date addYear(Date date, int year) {
        return addTime(date, Calendar.YEAR, year);
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

    public static Date setTime(Date date, int field, int amount) {
        Calendar c = Calendar.getInstance();
        c.setLenient(false);
        c.setTime(date);
        c.set(field, amount);
        return c.getTime();
    }

    public static Date setYear(Date date, int year) {
        return setTime(date, Calendar.YEAR, year);
    }

    public static Date setMonth(Date date, int month) {
        return setTime(date, Calendar.MONTH, month);
    }

    public static Date setDay(Date date, int day) {
        return setTime(date, Calendar.DATE, day);
    }

    public static Date setHour(Date date, int hour) {
        return setTime(date, Calendar.HOUR, hour);
    }

    public static Date setMinute(Date date, int minute) {
        return setTime(date, Calendar.MINUTE, minute);
    }

    public static Date setSecond(Date date, int second) {
        return setTime(date, Calendar.SECOND, second);
    }

    public static Date setMillisecond(Date date, int mSecond) {
        return setTime(date, Calendar.MILLISECOND, mSecond);
    }

    public static int getYear(Date date) {
        return getCalendar(date).get(Calendar.YEAR);
    }

    public static Calendar getCalendar(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setMinimalDaysInFirstWeek(7);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date);
        return calendar;
    }

    public static Date getYesterday(Date date) {
        return addDay(date, -1);
    }

    public static Date getTomorrow(Date date) {
        return addDay(date, 1);
    }

    public static long getDateDiff(Date from, Date to) {
        return (to.getTime() - from.getTime()) / MILLISECONDS_PER_DAY;
    }

    public static boolean isSameDay(Date date, Date comparedDate) {
        return getDateDiff(date, comparedDate) == 0;
    }

    public static boolean isLeapYear(int year) {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }

    public static int getNoOfDaysInMonth(int year, int month) {
        while(month > 12) {
            month -= 12;
            ++year;
        }
        while(month < 1) {
            month += 12;
            --year;
        }
        switch(month) {
            case 2:
                return isLeapYear(year) ? 29 : 28;
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            default:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
        }
    }

    public static int getNoOfDaysInMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return getNoOfDaysInMonth(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1);
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
        if (year > 0) return year + " " + (year > 1 ? messages.getString("years_ago") : messages.getString("year_ago"));
        else if (week > 0) return week + " " + (week > 1 ? messages.getString("weeks_ago") : messages.getString("week_ago"));
        else if (day > 0) return day + " " + (day > 1 ? messages.getString("days_ago") : messages.getString("day_ago"));
        else if (hour > 0) return hour + " " + (hour > 1 ? messages.getString("hours_ago") : messages.getString("hour_ago"));
        else if (minute > 0) return minute + " " + (minute > 1 ? messages.getString("minutes_ago") : messages.getString("minute_ago"));
        else if (second > 30) return second + " " + messages.getString("seconds_ago");
        else return messages.getString("just_now");
    }
}
