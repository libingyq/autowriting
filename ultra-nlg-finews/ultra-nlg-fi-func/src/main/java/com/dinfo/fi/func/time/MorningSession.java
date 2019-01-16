package com.dinfo.fi.func.time;

import com.dinfo.fi.anno.FuncAttr;
import com.dinfo.fi.enums.FuncType;
import com.dinfo.fi.inter.TimeFuncInterface;
import com.dinfo.fi.utils.TwoTuple;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
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
@Component
@FuncAttr(name = "早盘",type = FuncType.TimeFunc)
public class MorningSession implements TimeFuncInterface{

    @Override
    public TwoTuple<String, String> parseTimeDesc(Date baseDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String base = format.format(baseDate);

        return new TwoTuple<>(base+START_SUFFIX,base+MORNING_END_SUFFIX);
    }


}
