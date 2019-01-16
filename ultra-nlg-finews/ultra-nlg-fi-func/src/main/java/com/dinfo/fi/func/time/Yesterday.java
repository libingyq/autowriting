package com.dinfo.fi.func.time;

import com.dinfo.fi.anno.FuncAttr;
import com.dinfo.fi.enums.FuncType;
import com.dinfo.fi.inter.TimeFuncInterface;
import com.dinfo.fi.utils.TwoTuple;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <p>Date:2018/10/16</p>
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
@Component
@FuncAttr(name = "上一日",type = FuncType.TimeFunc)
public class Yesterday implements TimeFuncInterface {
    @Override
    public TwoTuple<String, String> parseTimeDesc(Date baseDate) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(baseDate);
        instance.add(Calendar.DATE, - 1);
        Date oneWeekBefore = instance.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        return new TwoTuple<>(format.format(oneWeekBefore)+START_SUFFIX,format.format(oneWeekBefore)+END_SUFFIX);

    }
}
