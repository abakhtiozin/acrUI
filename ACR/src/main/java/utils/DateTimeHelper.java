package main.java.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by Andrii.Bakhtiozin on 11.03.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class DateTimeHelper {

    public static final String FORMAT_PATTERN = "dd.MM.yyyy";

    private static DateTimeFormatter getFormatPattern() {
        return DateTimeFormat.forPattern(FORMAT_PATTERN);
    }

    public static DateTime toJodaTime(String stringDate) {
        return getFormatPattern().parseDateTime(stringDate);
    }

    public static String currentDatePlusNDays(int days) {
        return new DateTime().plusDays(days).toString(FORMAT_PATTERN);
    }

    public static String currentDateMinusNYears(int years) {
        return new DateTime().minusYears(years).toString(FORMAT_PATTERN);
    }

    public static String currentDatePlusWeekAndDayOfTheWeek(int weeks, int days) {
        return new DateTime().plusWeeks(weeks).withDayOfWeek(days).toString(FORMAT_PATTERN);
    }

    public static String currentDatePlusNYears(int years) {
        return new DateTime().plusYears(years).toString(FORMAT_PATTERN);
    }

    public static String getDateOnly(DateTime dateTime) {
        return dateTime.toString(FORMAT_PATTERN);
    }

    public static String getTimeOnly(DateTime dateTime) {
        return dateTime.toString("HH:mm");
    }

    public static String getHours(String pattern, int hours) {
        return DateTimeFormat.forPattern(pattern).parseDateTime(hours + "").toString("HH:mm");
    }
}
