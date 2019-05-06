package com.kdyadav.androidutilities;

import android.util.Log;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;


public class DateTimeUtil {

    private static final String API_APP_INSTALL_UPDATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String API_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    private static final String BOOK_MY_SHOW_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String API_DATE_FORMAT = "yyyy-MM-dd";
    private static final String WEATHER_DATE_FORMAT = "MMMM d, yyyy";
    private static final String SUBSCRIPTION_DATE_FORMAT = "dd MMM yyyy";
    private static final String WEATHER_WEEK_OF_DAY = "EEE";
    private static final String LIVE_TV_DATE_FORMAT = "hh:mmaa dd MMM yy";
    private static final String CONTEST_DATE_FORMAT = "d MMM yyyy";
    private static final String CONTEST_TIME_FORMAT = "hh:mm aa";
    private static final String NEWS_ROUNDUP_DATE_FORMAT = "EEEE, MMMM dd";
    private static final String TIME_FORMAT = "hh:mm aa";
    public static final String NEWS_BULLETIN_DATE_TIME_FORMAT = "EEE, MM/dd/yyyy - HH:mm";
    public static final String NEWS_BULLETIN_DATE_DISPLAY_FORMAT = "dd-MM-yyyy";
    public static final String NEWS_BULLETIN_DATE_FORMAT = "EEE, MM/dd/yyyy";
    public static final String NEWS_BULLETIN_DATE_REQUEST_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String MONTH_FORMAT = "MMM";
    public static final String DATE_FORMAT = "dd";
    private static final String NEWS_ROUNDUP_UPDATED_DATE_FORMAT = "EEE dd MMM";

    public static int getCurrentTimeOfDay() {
        Calendar calender = Calendar.getInstance();
        return calender.get(Calendar.HOUR_OF_DAY);
    }

    public static String getCurrentDate() {
        return new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(Calendar
                .getInstance().getTime());
    }

    public static long getCurrentDateInMilliSeconds() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int date = cal.get(Calendar.DATE);
        cal.clear();
        cal.set(year, month, date);
        return cal.getTimeInMillis();
    }

    /**
     * Return date in specified format.
     *
     * @param milliSeconds Date in milliseconds
     * @return String representing date in specified format
     */
    public static String getDate(long milliSeconds) {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(SUBSCRIPTION_DATE_FORMAT, Locale.ENGLISH);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    public static String getFormattedWeatherDate(String weatherDate) {
        SimpleDateFormat currentFormat = new SimpleDateFormat(API_DATE_FORMAT, Locale.ENGLISH);
        SimpleDateFormat requiredFormat = new SimpleDateFormat(WEATHER_DATE_FORMAT,
                Locale.ENGLISH);
        return getFormattedDate(weatherDate, currentFormat, requiredFormat);
    }

    public static String getFormattedWeekName(String weatherDate) {
        SimpleDateFormat currentFormat = new SimpleDateFormat(API_DATE_FORMAT, Locale.ENGLISH);
        SimpleDateFormat requiredFormat = new SimpleDateFormat(WEATHER_WEEK_OF_DAY,
                Locale.ENGLISH);
        return getFormattedDate(weatherDate, currentFormat, requiredFormat);
    }

    public static String getFormattedDailyRashiPhalDate(String endDate) {
        SimpleDateFormat currentFormat = new SimpleDateFormat(API_DATE_FORMAT, Locale.ENGLISH);
        String dateMonth = getRashiphalFormatDate(endDate, currentFormat, false, false);
        String year = getRashiphalFormatDate(endDate, currentFormat, true, false);
        return dateMonth + ", " + year;
    }

    public static String getFormattedWeeklyRashiPhalDate(String endDate) {
        SimpleDateFormat currentFormat = new SimpleDateFormat(API_DATE_FORMAT, Locale.ENGLISH);
        String endDateMonth = getRashiphalFormatDate(endDate, currentFormat, false, false);
        String startDateMopnth = getRashiphalFormatDate(subtractDays(endDate), currentFormat, false, false);
        return startDateMopnth + " - " + endDateMonth + ", " + getRashiphalFormatDate(endDate, currentFormat, true, false);
    }

    public static String getFormattedMonthlyRashiPhalDate(String endDate) {
        SimpleDateFormat currentFormat = new SimpleDateFormat(API_DATE_FORMAT, Locale.ENGLISH);
        String startDateMonth = getRashiphalFormatDate(endDate, currentFormat, false, true) + " 01";
        String endDateMonth = getRashiphalFormatDate(checkforLastDate(endDate, currentFormat), currentFormat, false, false);
        return startDateMonth + " - " + endDateMonth + ", " + getRashiphalFormatDate(endDate, currentFormat, true, false);
    }

    private static String checkforLastDate(String endDate, SimpleDateFormat dateFormat) {
        Date convertedDate = null;
        try {
            convertedDate = dateFormat.parse(endDate);
        } catch (Exception e) {
//do nothing
        }
        Calendar c = Calendar.getInstance();
        c.setTime(convertedDate);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        return dateFormat.format(c.getTime());
    }

    public static String getFormattedYearlyRashiPhalDate(String weatherDate) {
        SimpleDateFormat currentFormat = new SimpleDateFormat(API_DATE_FORMAT, Locale.ENGLISH);
        String monthDate = "Jan 01 - Dec 31";
        String year = getRashiphalFormatDate(weatherDate, currentFormat, true, false);
        return monthDate + ", " + year;
    }

    public static String getFormattedLiveTvDate(String date) {
        SimpleDateFormat currentFormat = new SimpleDateFormat(API_DATE_TIME_FORMAT, Locale.ENGLISH);
        SimpleDateFormat requiredFormat = new SimpleDateFormat(LIVE_TV_DATE_FORMAT, Locale.ENGLISH);
        return getFormattedDate(date, currentFormat, requiredFormat);
    }

    public static String getFormattedContestDate(String date) {
        SimpleDateFormat currentFormat = new SimpleDateFormat(API_DATE_TIME_FORMAT, Locale.ENGLISH);
        SimpleDateFormat requiredFormat = new SimpleDateFormat(CONTEST_DATE_FORMAT, Locale.ENGLISH);
        return getFormattedDate(date, currentFormat, requiredFormat);
    }

    public static String getFormattedContestTime(String date) {
        SimpleDateFormat currentFormat = new SimpleDateFormat(API_DATE_TIME_FORMAT, Locale.ENGLISH);
        SimpleDateFormat requiredFormat = new SimpleDateFormat(CONTEST_TIME_FORMAT, Locale.ENGLISH);
        return getFormattedDate(date, currentFormat, requiredFormat);
    }

    public static String getFormattedNewsRoundupDate(String date) {
        SimpleDateFormat currentFormat = new SimpleDateFormat(API_DATE_FORMAT, Locale.ENGLISH);
        SimpleDateFormat requiredFormat = new SimpleDateFormat(NEWS_ROUNDUP_DATE_FORMAT,
                Locale.ENGLISH);
        return getFormattedDate(date, currentFormat, requiredFormat);
    }

    public static String getFormattedNewsBriefDate(String date) {
        SimpleDateFormat currentFormat = new SimpleDateFormat(API_DATE_FORMAT, Locale.ENGLISH);
        SimpleDateFormat requiredFormat = new SimpleDateFormat(NEWS_ROUNDUP_UPDATED_DATE_FORMAT,
                Locale.ENGLISH);
        return getFormattedDate(date, currentFormat, requiredFormat);
    }

    public static String getFormattedNewsRoundupDateInEnglish(String date) {
        SimpleDateFormat currentFormat = new SimpleDateFormat(API_DATE_FORMAT, Locale.ENGLISH);
        SimpleDateFormat requiredFormat = new SimpleDateFormat(NEWS_ROUNDUP_DATE_FORMAT,
                new Locale("en", "US"));
        return getFormattedDate(date, currentFormat, requiredFormat);
    }

    public static String getTime(String date) {
        SimpleDateFormat currentFormat = new SimpleDateFormat(API_DATE_TIME_FORMAT, Locale.ENGLISH);
        SimpleDateFormat requiredFormat = new SimpleDateFormat(TIME_FORMAT,
                new Locale("hi", "IN"));
        return getFormattedDate(date, currentFormat, requiredFormat);
    }

    public static boolean dateIsInRange(String startDate, String endDate) {
        SimpleDateFormat format = new SimpleDateFormat(API_DATE_TIME_FORMAT, Locale.ENGLISH);
        Date start = getFormattedDate(startDate, format);
        Date end = getFormattedDate(endDate, format);
        Date currentDate = getCurrentTime();
        return start != null && end != null
                && start.compareTo(currentDate) < 0
                && currentDate.compareTo(end) < 0;
    }

    public static boolean dateIsInRange(int startDate, int endDate, long serverTime) {
        Date start = new Date(startDate * 1000L);
        Date end = new Date(endDate * 1000L);
        //Date currentDate = getCurrentTime();
        Date currentDate = new Date(getServerTime(serverTime));
        return start.compareTo(currentDate) < 0
                && currentDate.compareTo(end) < 0;
    }

    public static boolean cutoffTimePassed(String dateText) {
        SimpleDateFormat format = new SimpleDateFormat(API_DATE_TIME_FORMAT, Locale.ENGLISH);
        Date date = getFormattedDate(dateText, format);
        Date currentDate = getCurrentTime();
        return date != null && date.compareTo(currentDate) < 0;
    }

    public static boolean cutoffTimePassed(String cutOffTime, int currentTimeStamp) {
        SimpleDateFormat format = new SimpleDateFormat(BOOK_MY_SHOW_DATE_FORMAT, Locale.ENGLISH);
        Date cutOffDate = getFormattedDate(cutOffTime, format);
        return cutOffDate != null && currentTimeStamp > (int) (cutOffDate.getTime() / 1000);
    }

    public static String getLocationTrackerDate(long time) {
        SimpleDateFormat requiredFormat = new SimpleDateFormat(BOOK_MY_SHOW_DATE_FORMAT, Locale.ENGLISH);
        Date date = new Date(time);
        return getFormattedDate(date, requiredFormat);
    }

    public static String getCurrentDateInApiFormat() {
        Date currentDate = getCurrentTime();
        SimpleDateFormat format = new SimpleDateFormat(API_DATE_TIME_FORMAT, Locale.ENGLISH);
        return getFormattedDate(currentDate, format);
    }

    public static String getInstalledDateAPIFormatFromDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(API_APP_INSTALL_UPDATE_FORMAT, Locale.ENGLISH);
        return getFormattedDate(date, format);
    }

    public static String getTimeFromMiliMSeconds(long noOMilifSeconds) {
        SimpleDateFormat formatter = new SimpleDateFormat(TIME_FORMAT, Locale.ENGLISH);
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date start = new Date(noOMilifSeconds);
        return formatter.format(start);
    }

    public static String getTimeFromSeconds(long noOfSeconds) {
        SimpleDateFormat formatter = new SimpleDateFormat(TIME_FORMAT, Locale.ENGLISH);
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date start = new Date(noOfSeconds * 1000L);
        return formatter.format(start);
    }

    private static Date getFormattedDate(String date, SimpleDateFormat format) {
        try {
            return format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date getDateFromString(String date, String dateFormat) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
            return format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    public static String getStringFromDate(Date date, String dateFormat) {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
        return getFormattedDate(date, format);
    }

    public static String getFormattedDateString(String date, String currentDateFormat,
                                                String requiredDateFormat) {
        try {
            SimpleDateFormat currentFormat = new SimpleDateFormat(currentDateFormat, Locale.ENGLISH);
            SimpleDateFormat requiredFormat = new SimpleDateFormat(requiredDateFormat, Locale.ENGLISH);
            return getFormattedDate(currentFormat.parse(date), requiredFormat);
        } catch (ParseException e) {
        }
        return date;
    }

    private static String getRashiphalFormatDate(String date, SimpleDateFormat format, boolean isYear, boolean isMonth) {
        String month = "", dd = "", year = "";
        try {
            Date d = format.parse(date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(d);
            month = getMonthForInt(cal.get(Calendar.MONTH));
            dd = checkDigit(cal.get(Calendar.DATE));
            year = checkDigit(cal.get(Calendar.YEAR));

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (isYear) {
            return year;
        } else if (isMonth) {
            return month;
        } else {
            return month + " " + dd;
        }
    }

    private static String getMonthForInt(int num) {
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 11) {
            month = months[num];
        }
        return month.substring(0, 3);
    }

    private static String checkDigit(int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }

    private static String getFormattedDate(String date, SimpleDateFormat currentFormat,
                                           SimpleDateFormat requiredFormat) {
        try {
            return getFormattedDate(currentFormat.parse(date), requiredFormat);
        } catch (ParseException e) {
        }
        return date;
    }

    private static String getFormattedDate(Date date, SimpleDateFormat requiredFormat) {
        return requiredFormat.format(date);
    }

    private static Date getCurrentTime() {
        return Calendar.getInstance().getTime();
    }

    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;


    public static String getTimeAgo(String timeDate) {
        long time = Long.parseLong(timeDate);
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }

        long now = System.currentTimeMillis();
        if (time > now || time <= 0) {
            return null;
        }

        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "Just Now";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "Before 1 Minute";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " Minute Before";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "1 Hour Before";
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " HOur Before";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "Yesterday";
        } else {
            return diff / DAY_MILLIS + " Day Before";
        }
    }

    /**
     * subtract days to date in java
     *
     * @param date
     * @return
     */
    private static String subtractDays(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(API_DATE_FORMAT, Locale.ENGLISH);
        Date myDate = null;
        try {
            myDate = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(myDate);
        cal.add(Calendar.DATE, -6);

        SimpleDateFormat timeFormat = new SimpleDateFormat(API_DATE_FORMAT, Locale.ENGLISH);
        return timeFormat.format(cal.getTime());
    }

    public static String getTimerFromMilliSeconds(long milliseconds) {
        String finalTimerString = "";
        String secondsString = "";

        // Convert total duration into time
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
        // Add hours if there
        if (hours > 0) {
            finalTimerString = hours + ":";
        }

        // Prepending 0 to seconds if it is one digit
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }

        finalTimerString = finalTimerString + minutes + ":" + secondsString;

        // return timer string
        return finalTimerString;
    }

    public static long nextTriggerTimeInMilliseconds() {
        try {
            int triggerTimes[] = {9, 15, 19};                         // 9AM, 3PM, 7PM
            Calendar instance = Calendar.getInstance();
            long currentMilliSecs = instance.getTimeInMillis();
            resetTimeToZero(instance);                               //time is reset to 00 :00 :00
            for (int specifiedTime : triggerTimes) {
                long specifiedTimeInMills = instance.getTimeInMillis()
                        + TimeUnit.HOURS.toMillis(specifiedTime);   //get milliseconds of 9AM/3PM/7PM of today's date
                if (specifiedTimeInMills > currentMilliSecs) {
                    return specifiedTimeInMills;
                }
            }
            instance.add(Calendar.DAY_OF_YEAR, 1);          //if nothing matches increment the date, set to first time (9AM)
            return instance.getTimeInMillis() + TimeUnit.HOURS.toMillis(triggerTimes[0]);
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e("Alarm Manager", "nextTriggerTimeInMilliseconds: " + e.getMessage());
            }
        }
        return 0;
    }

    private static void resetTimeToZero(Calendar instance) {
        logState("instance_before", instance.getTime().toString());
        instance.set(Calendar.HOUR_OF_DAY, 0);
        instance.set(Calendar.MINUTE, 0);
        instance.set(Calendar.SECOND, 0);
        logState("instance_after", instance.getTime().toString());
    }

    private static void logState(String id, String value) {
    }

    private static long getServerTime(long serverTime) {
        return serverTime * 1000L;
    }

    public static Integer getTimeInt(String time) {
        int add;
        if (time.contains("am") || time.contains("AM"))
            add = 0;
        else
            add = 1200;

        String timeString = time.replace("am", "")
                .replace("AM", "")
                .replace("pm", "")
                .replace("PM", "")
                .replace(":", "")
                .replace(" ", "");

        return Integer.parseInt(timeString.substring(0, timeString.length() - 2)) + add;
    }

    public static String getFormattedNewsBriefDateDisplay(String date) {
        SimpleDateFormat currentFormat = new SimpleDateFormat(API_DATE_FORMAT, Locale.ENGLISH);
        SimpleDateFormat requiredFormat = new SimpleDateFormat(NEWS_ROUNDUP_UPDATED_DATE_FORMAT,
                Locale.ENGLISH);
        return getFormattedDate(date, currentFormat, requiredFormat);
    }

    public static String getFirstDay(Date d) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date dddd = calendar.getTime();
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy");
        return sdf1.format(dddd);
    }

    public static String getLastDay(Date d) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date dddd = calendar.getTime();
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy");
        return sdf1.format(dddd);
    }

    public static String getMonth(Date d) {
        /*Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);*/
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        Date dddd = calendar.getTime();
        SimpleDateFormat sdf1 = new SimpleDateFormat("MMMM");
        return sdf1.format(d);
    }


    public static String getYear(Date d) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        //calendar.set(Calendar.YEAR, 1);
        Date dddd = calendar.getTime();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy");
        return sdf1.format(d);
    }

}
