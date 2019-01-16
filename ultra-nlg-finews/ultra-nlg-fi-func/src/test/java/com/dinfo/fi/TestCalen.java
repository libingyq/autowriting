package com.dinfo.fi;

import org.junit.Test;

import java.util.Calendar;

/**
 * <p>Date:2018/10/18</p>
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
public class TestCalen {

    @Test
    public void testString(){
        String format = String.format("报%.2f点", 2031.1223);
        System.out.println(format);
    }


    public static void main(String[] args) {
        Calendar instance = Calendar.getInstance();
        int i = instance.get(Calendar.HOUR_OF_DAY);
        int i1 = instance.get(Calendar.MINUTE);

        i = 13;
        i1 = 0;

        if(i < 13 || i > 15){
            System.out.println("不在午市时间");
        }

        if(i < 9 || (i == 9 && i1 <30) || i > 11 || (i == 11 && i1 > 30)){
            System.out.println("不在早市时间");
        }
    }
}
