package com.dinfo.fi.func.form.cul;

import com.dinfo.fi.anno.FuncAttr;
import com.dinfo.fi.entity.DataInfo;
import com.dinfo.fi.enums.FuncType;
import com.dinfo.fi.exception.FuncException;
import com.dinfo.fi.inter.CulFuncInterface;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

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
@FuncAttr(name = "报点",type = FuncType.CulFunc)
public class Report implements CulFuncInterface {

    public static final String funcName = Report.class.getAnnotation(FuncAttr.class).name();
    @Override
    public double culculate(List<DataInfo> stockInfos) {
        DataInfo dataInfoEnd = stockInfos.get(stockInfos.size() - 1);
        BigDecimal b = new BigDecimal(dataInfoEnd.getClose());
        double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return f1;
    }


    @Override
    public String formatTemplate() {
        return "报{v}点";
    }
}
