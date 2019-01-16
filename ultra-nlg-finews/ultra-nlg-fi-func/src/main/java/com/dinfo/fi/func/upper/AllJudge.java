package com.dinfo.fi.func.upper;

import com.dinfo.fi.anno.FuncAttr;
import com.dinfo.fi.enums.FuncType;
import com.dinfo.fi.spy.FuncLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static com.dinfo.fi.common.CommonConst.Comma;

/**
 * <p>Date:2018/11/2</p>
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
@FuncAttr(name = "全部趋势",type = FuncType.UpperFunc)
public class AllJudge implements  UpperInter {



    @Override
    public String reduce(String oriTemp) {

        Map<FuncType, List<String>> allFuncNameByType = new FuncLocator().getAllFuncNameByType();
        List<String> strings = allFuncNameByType.get(FuncType.JudgeFunc);
        StringBuffer stringBuffer = new StringBuffer();
        for (String string : strings) {
            stringBuffer.append(string).append(Comma);
        }
        String substring = stringBuffer.substring(0, stringBuffer.length() - 1);
        String s = oriTemp.replaceAll(getFuncName(), substring);

        return s;
    }
}
