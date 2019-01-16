package com.dinfo.fi.utils;


import org.junit.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtilsTest {



    @Test
    public void getLastTranscationDateTime() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println(TimeUtils.getLastTranscationDateTime("2018-12-15 10:00:00",dateTimeFormatter));
        System.out.println(TimeUtils.getLastTranscationDateTime("2018-12-16 10:00:00",dateTimeFormatter));
        System.out.println(TimeUtils.getLastTranscationDateTime("2018-12-17 10:00:00",dateTimeFormatter));
        System.out.println(TimeUtils.getLastTranscationDateTime("2018-12-17 09:05:00",dateTimeFormatter));
        System.out.println(TimeUtils.getLastTranscationDateTime("2018-12-17 07:05:00",dateTimeFormatter));
        System.out.println(TimeUtils.getLastTranscationDateTime("2018-12-17 11:35:00",dateTimeFormatter));
        System.out.println(TimeUtils.getLastTranscationDateTime("2018-12-17 13:05:00",dateTimeFormatter));
        System.out.println(TimeUtils.getLastTranscationDateTime("2018-12-17 15:15:00",dateTimeFormatter));
        System.out.println(TimeUtils.getLastTranscationDateTime());
    }
    @Test
    public void TestTimeTemp() {
        System.out.println(new Timestamp(System.currentTimeMillis() - 2 * 60 * 1000).toString());
    }
}
