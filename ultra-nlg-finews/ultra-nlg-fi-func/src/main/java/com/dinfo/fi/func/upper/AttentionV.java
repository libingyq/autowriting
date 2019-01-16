package com.dinfo.fi.func.upper;

import com.dinfo.fi.anno.FuncAttr;
import com.dinfo.fi.enums.FuncType;
import com.dinfo.fi.func.form.judge.LimitDown;
import com.dinfo.fi.func.form.judge.OpenLow;
import com.dinfo.fi.func.form.judge.pureTrend.*;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.dinfo.fi.common.CommonConst.Comma;

/**
 * <p>Date:2018/12/13</p>
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
@FuncAttr(name = "关注V形",type = FuncType.UpperFunc)
public class AttentionV implements  UpperInter{



    @Override
    public String reduce(String oriTemp) {

        List<String> strings = Lists.newArrayList(BottomBack.funcName, BottomBackAndShake.funcName,
                ShakeAndBottomBack.funcName, ShakeAndUpDown.funcName,SlowBottomBack.funcName,
                SlowUpAndDown.funcName,TempBottomBack.funcName,TempUpAndDown.funcName,
                UpDown.funcName,UpDownAndShake.funcName);
        StringBuffer stringBuffer = new StringBuffer();
        for (String string : strings) {
            stringBuffer.append(string).append(Comma);
        }
        String substring = stringBuffer.substring(0, stringBuffer.length() - 1);
        String s = oriTemp.replaceAll(getFuncName(), substring);

        return s;
    }

}
