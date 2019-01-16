package com.dinfo.fi.func.time;

import com.dinfo.fi.anno.FuncAttr;
import com.dinfo.fi.enums.FuncType;
import com.dinfo.fi.inter.TimeFuncInterface;
import com.dinfo.fi.utils.TwoTuple;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>Date:2018/10/17</p>
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
@FuncAttr(name = "截止发稿",type = FuncType.TimeFunc)
public class UntilNow implements TimeFuncInterface {
    @Override
    public TwoTuple<String, String> parseTimeDesc(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String base = format.format(date);
        Date now = new Date();
        String untilNow = format.format(now);
        return new TwoTuple<>(base.split(" ")[0] + START_SUFFIX,untilNow);
    }
}
