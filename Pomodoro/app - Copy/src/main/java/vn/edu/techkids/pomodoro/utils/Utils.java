package vn.edu.techkids.pomodoro.utils;

import android.content.res.Resources;
import android.view.View;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utils {


    /**
     * Concert time in seconds to string time
     * @param timeInSeconds
     * @return
     */
    public static String seconds2time(int timeInSeconds) {
        int minutes = timeInSeconds / 60;
        int seconds = timeInSeconds - minutes * 60;
        return formatNumber(minutes).concat(":").concat(formatNumber(seconds));
    }

    /**
     * Format number to 2 characters number
     * @param number
     * @return
     */
    public static String formatNumber(int number) {
        if (number > 9) {
            return String.valueOf(number);
        } else {
            return "0".concat(String.valueOf(number));
        }
    }

    /**
     * Convert dp to px
     * @param resources
     * @param dp
     * @return
     */
    public static float dp2px(Resources resources, float dp) {
        final float scale = resources.getDisplayMetrics().density;
        return  dp * scale + 0.5f;
    }

    /**
     * Convert sp to px
     * @param resources
     * @param sp
     * @return
     */
    public static float sp2px(Resources resources, float sp){
        final float scale = resources.getDisplayMetrics().scaledDensity;
        return sp * scale;
    }

    /**
     * Get current time from system
     * @return
     */
    public static long getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTimeInMillis();
    }

    /**
     * Get today string
     * @return
     */
    public static String getTodayString() {
        String result = null;
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = (calendar.get(Calendar.MONTH) + 1);
        int year = calendar.get(Calendar.YEAR);
        result = getDatetimeString(year, month, day);
        return result;
    }

    /**
     * Get first day of week
     * @return
     */
    public static String getFirstDayOfWeek() {
        String result = null;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = (calendar.get(Calendar.MONTH) + 1);
        int year = calendar.get(Calendar.YEAR);
        result = getDatetimeString(year, month, day);
        return result;
    }

    /**
     * Get last day of week
     * @return
     */
    public static String getLastDayOfWeek() {
        String result = null;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getMaximum(Calendar.DAY_OF_WEEK));
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = (calendar.get(Calendar.MONTH) + 1);
        int year = calendar.get(Calendar.YEAR);
        result = getDatetimeString(year, month, day);
        return result;
    }

    /**
     * Get first day of month
     * @return
     */
    public static String getFirstDayOfMonth() {
        String result = null;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = (calendar.get(Calendar.MONTH) + 1);
        int year = calendar.get(Calendar.YEAR);
        result = getDatetimeString(year, month, day);
        return result;
    }

    /**
     * Get last day of month
     * @return
     */
    public static String getLastDayOfMonth() {
        String result = null;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getMaximum(Calendar.DAY_OF_MONTH));
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = (calendar.get(Calendar.MONTH) + 1);
        int year = calendar.get(Calendar.YEAR);
        result = getDatetimeString(year, month, day);
        return result;
    }

    /**
     * Get first day of year
     * @return
     */
    public static String getFirstDayOfYear() {
        String result = null;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        calendar.set(Calendar.MONTH, 1 - 1);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = (calendar.get(Calendar.MONTH) + 1);
        int year = calendar.get(Calendar.YEAR);
        result = getDatetimeString(year, month, day);
        return result;
    }

    /**
     * Get last day of year
     * @return
     */
    public static String getLastDayOfYear() {
        String result = null;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, 12 - 1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getMaximum(Calendar.DAY_OF_MONTH));
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = (calendar.get(Calendar.MONTH) + 1);
        int year = calendar.get(Calendar.YEAR);
        result = getDatetimeString(year, month, day);
        return result;
    }

    /**
     * Get date string from millis
     * @param millis
     * @return
     */
    public static String getDateFromMillis(long millis) {
        String result = null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = (calendar.get(Calendar.MONTH) + 1);
        int year = calendar.get(Calendar.YEAR);
        result = getDatetimeString(year, month, day);
        return result;
    }

    /**
     * Get time in millis from Date string
     * @param dateString
     * @return
     */
    public static long getMillisFromDate(String dateString) {
        long result = 0;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = simpleDateFormat.parse(dateString);
            return date.getTime();
        } catch (ParseException e) {
            Logger.e("getMillisFromDate", "ParseException = " + e.toString());
        }
        return result;
    }

    /**
     * Get day form date
     * @param date
     * @return
     */
    public static int getDayFromString(String date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(getMillisFromDate(date));
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Get month form date
     * @param date
     * @return
     */
    public static int getMonthFromString(String date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(getMillisFromDate(date));
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * Get year form date
     * @param date
     * @return
     */
    public static int getYearFromString(String date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(getMillisFromDate(date));
        return calendar.get(Calendar.YEAR);
    }

    /**
     * Get current day
     * @return
     */
    public static int getCurrentDay() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Get current month
     * @return
     */
    public static int getCurrentMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * Get current year
     * @return
     */
    public static int getCurrentYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    /**
     * Get current hour
     * @return
     */
    public static int getCurrentHour() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * Get current minute
     * @return
     */
    public static int getCurrentMinute() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MINUTE);
    }

    /**
     * Convert numbers to datetime String
    * */
    public static String getDatetimeString(int year, int month, int day, int hour, int minute) {
        return String.format("%04d-%02d-%02d %02d:%02d", year, month, day, hour, minute);
    }

    /**
     * Convert numbers to datetime String
     * */
    public static String getDatetimeString(int year, int month, int day) {
        return getDatetimeString(year, month, day, 0, 0);
    }

}