package com.dinfo.fi.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static java.util.Calendar.*;

/**
 * <p>Date:2018/9/25</p>
 * <p>Module:</p>
 * <p>Description: </p>
 * <p>Remark: </p>
 *
 * @author wuxiangbo
 * @version 1.0
 * <p>------------------------------------------------------------</p>
 * <p> Change history</p>
 * <p> Serial number: date:modified person: modification reason:</p>
 */
public class TimeUtils {

    //延迟的时间
    private static int TIME_DIFFERENCE = 10;
    // 上午、下午开市和闭市的时间
    private static LocalTime morningBegin = LocalTime.of(9, 30,0);
    private static LocalTime morningEnd = LocalTime.of(11, 30,0);
    private static LocalTime afternoonBegin = LocalTime.of(13, 0,0);
    private static LocalTime afternoonEnd = LocalTime.of(15, 0,0);
    //下午闭市前的几分钟
    private static LocalTime lastTranscationMinute = afternoonEnd.minusMinutes(TIME_DIFFERENCE).withSecond(0);


    public static boolean isNotSameDay(Date datePre, Date dateAfter) {
        return !isSameDay(datePre, dateAfter);
    }

    public static boolean isSameDay(Date datePre, Date dateAfter) {
        if (datePre != null && dateAfter != null) {
            Calendar calPre = Calendar.getInstance();
            calPre.setTime(datePre);
            Calendar calAfter = Calendar.getInstance();
            calAfter.setTime(dateAfter);
            return isSameDay(calPre, calAfter);
        } else {
            throw new IllegalArgumentException("The date must not be null");
        }
    }

    public static boolean isSameDay(Calendar calPre, Calendar calAfter) {
        if (calPre != null && calAfter != null) {
            return calPre.get(ERA) == calAfter.get(ERA) && calPre.get(YEAR) == calAfter.get(YEAR) && calPre.get(DAY_OF_YEAR) == calAfter.get(DAY_OF_YEAR);
        } else {
            throw new IllegalArgumentException("The date must not be null");
        }
    }

    //把日期往后增加index天.整数往后推,负数往前移动
    public static String getDate(int index) {
        Date date = new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, index);//把日期往后增加一天.整数往后推,负数往前移动
        date = calendar.getTime(); //这个时间就是日期往后推一天的结果
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(date);
        return dateString;
    }

    /**
     * author: rzh <br/>
     * what: 文件命名 <br/>
     *
     * @return
     */
    public static String getFileNameNew() {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return fmt.format(new Date());
    }

    /**
     * 字符串转时间戳
     *
     * @return
     */
    public static String strToTimestamp(String time) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return String.valueOf(sdf.parse(time).getTime() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取时间字符串
     *
     * @return
     */
    public static String getTimestampStr() {
        Timestamp timestamp = new Timestamp((new Date()).getTime());
        return timestamp.toString();
    }

    public static LocalDateTime getLastTranscationDateTime() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return getLastTranscationDateTime(LocalDateTime.now().format(dateTimeFormatter),dateTimeFormatter);
    }
    public static LocalDateTime getLastTranscationDateTime(CharSequence text,DateTimeFormatter dateTimeFormatter) {
        LocalDateTime now = LocalDateTime.parse(text,dateTimeFormatter);
        DayOfWeek dayOfWeek = now.getDayOfWeek();

        if (dayOfWeek.equals(DayOfWeek.SUNDAY)) {
            return now.minusDays(2).withHour(lastTranscationMinute.getHour()).withMinute(lastTranscationMinute.getMinute()).withSecond(lastTranscationMinute.getSecond());
        } else if (dayOfWeek.equals(DayOfWeek.SATURDAY)) {
            return now.minusDays(1).withHour(lastTranscationMinute.getHour()).withMinute(lastTranscationMinute.getMinute()).withSecond(lastTranscationMinute.getSecond());
        }else{
            LocalTime nowTime = LocalTime.of(now.getHour(), now.getMinute(), now.getSecond());

            if (nowTime.isBefore(morningBegin)) {                                               //00：00~9:30
                return getLastTranscationDate(now).withHour(lastTranscationMinute.getHour()).withMinute(lastTranscationMinute.getMinute()).withSecond(lastTranscationMinute.getSecond());
            } else if (nowTime.isBefore(morningBegin.plusMinutes(TIME_DIFFERENCE))) {          //9:30~9：40
                return getLastTranscationDate(now).withHour(lastTranscationMinute.getHour()).withMinute(lastTranscationMinute.getMinute() + nowTime.getMinute()-morningBegin.getMinute());
            }else if(nowTime.isBefore(morningEnd)){                                            //9：40~11：30
                return now.minusMinutes(TIME_DIFFERENCE);
            }else if(nowTime.isBefore(afternoonBegin)){                                       //11：30~13：00
                return now.withMinute(morningEnd.minusMinutes(TIME_DIFFERENCE).getMinute()).withHour(morningEnd.getHour()).withSecond(morningEnd.getSecond());
            } else if (nowTime.isBefore(afternoonBegin.plusMinutes(TIME_DIFFERENCE))) {        //13：00~13：10
                return now.withHour(morningEnd.getHour()).withMinute(morningEnd.minusMinutes(TIME_DIFFERENCE).plusMinutes(nowTime.getMinute()).getMinute());
            }else if(nowTime.isBefore(afternoonEnd)){                   //13：10~15：00
                return now.minusMinutes(TIME_DIFFERENCE);
            } else{                 //15:00~24:00
                return now.withMinute(lastTranscationMinute.getMinute()).withHour(lastTranscationMinute.getHour()).withSecond(lastTranscationMinute.getSecond());
            }
        }
    }

    public static LocalDateTime getLastTranscationDate(LocalDateTime dateTime) {
        switch (dateTime.getDayOfWeek()) {
            case MONDAY:
                dateTime = dateTime.minusDays(3);
                break;
            case SUNDAY:
                dateTime = dateTime.minusDays(2);
                break;
            default:
                dateTime = dateTime.minusDays(1);
                break;
        }
        return dateTime;
    }
}
