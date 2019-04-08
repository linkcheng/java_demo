package com.xinyongfei.demo;

import lombok.extern.log4j.Log4j2;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@Log4j2
public class DateUtil {

    public static String getCurrentDate(String aFormat) {
        TimeZone timeZone = TimeZone.getTimeZone("GMT+8:00");
        SimpleDateFormat format = new SimpleDateFormat(aFormat);
        format.setTimeZone(timeZone);
        String tDate = format.format(new Date(System.currentTimeMillis()));

        return tDate;
    }

    public static String getCurrentDate() {
        return getCurrentDate("yyyyMMdd");
    }

    public static String getCurrentTime() {
        return getCurrentDate("HHmmss");
    }

    public static String getCurrentDateTime() {
        return getCurrentDate("yyyyMMddHHmmss");
    }

    public static String getSfyCurrentDateTime() {
        return getCurrentDate("yyyy-MM-dd HH:mm:ss");
    }

    public static String getCurrentDateTimeColon() {
        return getCurrentDate("yyyymmdd HH:mm:ss");
    }

    public static long countMonth2Now(String date) {
        try {
            Calendar lasCal = Calendar.getInstance();
            lasCal.setTime(new SimpleDateFormat("yyyyMMdd").parse(date));
            Calendar nowCal = Calendar.getInstance();
            int month = nowCal.get(Calendar.MONTH) - lasCal.get(Calendar.MONTH);
            int year = nowCal.get(Calendar.YEAR) - lasCal.get(Calendar.YEAR);
            return year * 12 + month;
        } catch (ParseException e) {
            //LogWriter.fatal("", e);
            throw new IllegalArgumentException("解析异常");
        }
    }

    public static long countMonths2Now(String date) {
        Calendar lasCal = Calendar.getInstance();
        int length = date.split("[-:]").length;
        try {
            switch (length){
                case 2:
                    lasCal.setTime(new SimpleDateFormat("yyyy-MM").parse(date));
                    break;
                case 3:
                    lasCal.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(date));
                    break;
                default:
                    lasCal.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date));
            }
        } catch (ParseException e) {
            //LogWriter.fatal("", e);
            throw new IllegalArgumentException("解析异常");
        }
        Calendar nowCal = Calendar.getInstance();
        long millis = nowCal.getTimeInMillis()-lasCal.getTimeInMillis();
        return millis/1000/60/60/24/30;
    }

    public static long countDate2Long(String date) {
        long d = 0;
        if (date.length() % 2 != 0) {
            date = "0" + date;
        }
        if (date.length() < 3) {
            String sec = date.substring(date.length() - 2, date.length());
            d = Integer.valueOf(sec);
            return d;
        }
        if (date.length() < 5) {
            String sec = date.substring(date.length() - 2, date.length());
            String div = date.substring(date.length() - 4, date.length() - 2);
            d = Integer.valueOf(sec) + Integer.valueOf(div) * 60;
            return d;
        }
        if (date.length() < 7) {
            String sec = date.substring(date.length() - 2, date.length());
            String div = date.substring(date.length() - 4, date.length() - 2);
            String hour = date.substring(date.length() - 6, date.length() - 4);
            d = Integer.valueOf(sec) + Integer.valueOf(div) * 60 + Integer.valueOf(hour) * 60 * 60;
            return d;
        }
        if (date.length() < 9) {
            String sec = date.substring(date.length() - 2, date.length());
            String div = date.substring(date.length() - 4, date.length() - 2);
            String hour = date.substring(date.length() - 6, date.length() - 4);
            String day = date.substring(date.length() - 8, date.length() - 6);
            d = Integer.valueOf(sec) + Integer.valueOf(div) * 60 + Integer.valueOf(hour) * 60 * 60 + Integer.valueOf(day) * 60 * 60 * 24;
            return d;
        }
        if (date.length() < 11) {
            String sec = date.substring(date.length() - 2, date.length());
            String div = date.substring(date.length() - 4, date.length() - 2);
            String hour = date.substring(date.length() - 6, date.length() - 4);
            String day = date.substring(date.length() - 8, date.length() - 6);
            String year = date.substring(0, date.length() - 8);
            d = Integer.valueOf(sec) + Integer.valueOf(div) * 60 + Integer.valueOf(hour) * 60 * 60
                    + Integer.valueOf(day) * 60 * 60 * 24 + Integer.valueOf(year) * 60 * 60 * 24 * 12;
            return d;
        }
        return d;
    }

    public static String getFirstDay() {
        return getMonthFirstDay(new Date(), "yyyy-MM-dd");
    }

    public static String getLastDay() {
        return getMonthLastDay(new Date(), "yyyy-MM-dd");
    }

    public static String getMonthFirstDay(Date date, String fmt) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return new SimpleDateFormat(fmt).format(cal.getTime());
    }


    public static String getMonthLastDay(Date date, String fmt) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return new SimpleDateFormat(fmt).format(cal.getTime());
    }

    /**
     * 取得两个时间段的时间间隔
     *
     * @param t1 时间1
     * @param t2 时间2
     * @return t2 与t1的间隔天数
     * @throws ParseException 如果输入的日期格式不是0000-00-00 格式抛出异常
     * @author color
     */
    public static int getBetweenDays(String t1, String t2) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        int betweenDays = 0;
        Date d1 = null;
        Date d2 = null;
        try {
            d1 = format.parse(t1);
            d2 = format.parse(t2);
        } catch (ParseException e) {
            //LogWriter.fatal("", e);
            return -1;
        }
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);
        // 保证第二个时间一定大于第一个时间
        if (c1.after(c2)) {
            //throw new CrcsException(Constants.CODE_ILLEGAL_PARAM, "出发日期不能在下单日期之前");
        }
        int betweenYears = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
        betweenDays = c2.get(Calendar.DAY_OF_YEAR)
                - c1.get(Calendar.DAY_OF_YEAR);
        for (int i = 0; i < betweenYears; i++) {
            c1.set(Calendar.YEAR, (c1.get(Calendar.YEAR) + 1));
            betweenDays += c1.getMaximum(Calendar.DAY_OF_YEAR);
        }
        return betweenDays;
    }

    /**
     * day2 是否在 day1 之后
     *
     * @param t1
     * @param t2
     * @return
     * @throws ParseException
     */
    public static boolean isD2AfterD1(String t1, String t2, String strFormat) {
        DateFormat format = new SimpleDateFormat(strFormat);
        Date d1 = null;
        Date d2 = null;
        try {
            d1 = format.parse(t1);
            d2 = format.parse(t2);
        } catch (ParseException e) {
            //LogWriter.fatal("", e);
            return false;
        }
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);
        if (c2.after(c1)) {
            return true;
        }
        return false;
    }

    public static boolean isD2AfterD1(String d1, String d2) {
        return d2.compareTo(d1) > 0;
    }


    public static String getDateAfterMonths(String strDate, int monthCount) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = format.parse(strDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.MONTH, monthCount);
            date = calendar.getTime();
            strDate = format.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return strDate;
    }

    public static String getDateAfterDays(String strDate, int days) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = format.parse(strDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DATE, days);
            date = calendar.getTime();
            strDate = format.format(date);
        } catch (ParseException e) {
            log.error("ParseException strDate["+strDate+"]", e);
        }

        return strDate;
    }

    /**
     * 获取指定小时后的日期
     *
     * @param strDate
     * @param hours
     * @return
     */
    public static String getDateAfterHours(String strDate, int hours) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = format.parse(strDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.HOUR_OF_DAY, hours);
            date = calendar.getTime();
            strDate = format.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return strDate;
    }

    /**
     * 获取指定分钟后的日期
     *
     * @param strDate
     * @param minutes
     * @return
     */
    public static String getDateAfterMinutes(String strDate, int minutes) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = format.parse(strDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.MINUTE, minutes);
            date = calendar.getTime();
            strDate = format.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return strDate;
    }

    public static String getDateBeforeDays(String strDate, int days) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = format.parse(strDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DATE, -days);
            date = calendar.getTime();
            strDate = format.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return strDate;
    }

    public static String getDateBeforeYears(String strDate, int years) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = format.parse(strDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.YEAR, -years);
            date = calendar.getTime();
            strDate = format.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return strDate;
    }public static String getDateBeforeMintues(String strDate, int minutes) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = format.parse(strDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.MINUTE, -minutes);
            date = calendar.getTime();
            strDate = format.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return strDate;
    }


    public static String getDateAfterMonthOther(String strDate, int monthCount) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        try {
            Date date = format.parse(strDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.MONTH, monthCount);
            date = calendar.getTime();
            strDate = format.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return strDate;
    }


    public static String getDateAfterDays(Date date, int Count) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, Count);
        date = calendar.getTime();
        strDate = format.format(date);
        return strDate;
    }

    public static String getDateAfterDays(String strDate, int count, String strFormat) {
        SimpleDateFormat format = new SimpleDateFormat(strFormat);
        Date date = null;
        try {
            date = format.parse(strDate);
        } catch (ParseException e) {
            //LogWriter.fatal("", e);
            return strDate;
        }

        String strDateNew;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, count);
        date = calendar.getTime();
        strDateNew = format.format(date);

        return strDateNew;
    }

    public static long getIntervalDayOfTwoTime(Date date1, Date date2) {
        return (date2.getTime() - date1.getTime()) / (1000 * 3600 * 24); // 毫秒 -> 天
    }

    public static boolean isToday(String strDate, String format) {
        String today = getCurrentDate(format);
        return strDate.substring(0, format.length()).equals(today);
    }

    /**
     * @param currentDay 格式必须是 yyyy-MM-dd HH:mm:ss
     * @param month      正数为当月往后数month个月 负数为往前数month个月
     * @return
     */
    public static String getMonthFromNow(String currentDay, int month) {
        return DateUtil.getDateAfterMonths(currentDay, month).substring(0, 7);
    }

    public static void main(String[] args) throws Exception {
//        System.out.println(DateUtil.getDateAfterDays(DateUtil.getSfyCurrentDateTime(), -1));
//        System.out.println(DateUtil.getDateAfterMonths(DateUtil.getSfyCurrentDateTime(), -6));
//        System.out.println(DateUtil.getDateBeforeMintues(DateUtil.getSfyCurrentDateTime(), 30));

        System.out.println(getSfyCurrentDateTime());
    }

}

