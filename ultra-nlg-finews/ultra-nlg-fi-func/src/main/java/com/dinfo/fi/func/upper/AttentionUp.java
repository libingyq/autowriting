package com.dinfo.fi.func.upper;

import com.dinfo.fi.anno.FuncAttr;
import com.dinfo.fi.enums.FuncType;
import com.dinfo.fi.func.form.judge.LimitUp;
import com.dinfo.fi.func.form.judge.OpenHigh;
import com.dinfo.fi.func.form.judge.pureTrend.ShakeAndUp;
import com.dinfo.fi.func.form.judge.pureTrend.ShakeUp;
import com.dinfo.fi.func.form.judge.pureTrend.Up;
import com.dinfo.fi.func.form.judge.pureTrend.UpAndShake;
import com.dinfo.fi.spy.FuncLocator;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

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
@FuncAttr(name = "关注上涨",type = FuncType.UpperFunc)
public class AttentionUp implements  UpperInter{



    @Override
    public String reduce(String oriTemp) {

        List<String> strings = Lists.newArrayList(LimitUp.funcName, OpenHigh.funcName,
                Up.funcName, UpAndShake.funcName, ShakeAndUp.funcName, ShakeUp.funcName);
        StringBuffer stringBuffer = new StringBuffer();
        for (String string : strings) {
            stringBuffer.append(string).append(Comma);
        }
        String substring = stringBuffer.substring(0, stringBuffer.length() - 1);
        String s = oriTemp.replaceAll(getFuncName(), substring);

        return s;
    }

}
