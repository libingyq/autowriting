package com.dinfo.fi.inter;

import com.dinfo.fi.inter.base.FuncInterface;
import com.dinfo.fi.utils.TwoTuple;

import java.util.Date;
import java.util.Map;

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
public interface TimeFuncInterface extends FuncInterface {


    String START_SUFFIX = "  09:30:00";
    String MORNING_END_SUFFIX = "  11:30:00";
    String NOON_START_SUFFIX = "  13:00:00";
    String END_SUFFIX = " 15:00:00";

    TwoTuple<String,String> parseTimeDesc(Date baseDate);

    @Override
    default Map run(Map input) {
        TwoTuple<String, String> value = parseTimeDesc(getData(input));
        input.put(this.getFuncName(), value);
        input.put(TIME_FLG,this.getFuncName());
        input.put(TimeFuncInterface.class.getName(), value);
        return input;
    }

    @Override
    default String getKey() {
        return DATA_KEY;
    }
}
