package com.dinfo.fi.func;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.quartz.CronExpression;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * <p>Date:2018/10/15</p>
 * <p>Module:</p>
 * <p>Description: </p>
 * <p>Remark: </p>
 *
 * @author wuxiangbo
 * @version 1.0
 *          <p>------------------------------------------------------------</p>
 *          <p> Change history</p>
 *          <p> Serial number: date:modified person: modification reason:</p>
 */
@Slf4j
public class TestLam {

    public static void main(String[] args) {
        String s = "[价值成长, 集成电路, 芯片概念, 半导体]";
        boolean b = Arrays.asList(s.substring(1, s.length()).split(", ")).contains("集成电路");
        System.out.println(b);
    }


    @Test
    public void testCron(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date curTime = new Date();
        System.out.println(curTime);

        CronExpression expression;
        try
        {
            expression = new CronExpression("0 */5 9-12 * * ?");
//            0 0 1 ? * L
            for (int i = 0; i < 100; i++) {
                Date newDate = expression.getNextValidTimeAfter(curTime);
                curTime = newDate;
                System.out.println(simpleDateFormat.format(newDate));
            }

        } catch (ParseException e) {
            log.error("fail to parse cron express", e);
        } catch (Exception e) {
            log.error("fail to update rule nextTime", e);
        }
    }
}
