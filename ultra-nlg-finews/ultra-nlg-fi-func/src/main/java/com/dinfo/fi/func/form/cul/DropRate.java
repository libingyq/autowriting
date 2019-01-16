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
 *          其实不应该是这样算的，应该是（现价-前一日收盘价）/前一日收盘
 */

@Component
@FuncAttr(name = "跌幅",type = FuncType.CulFunc)
public class DropRate implements CulFuncInterface {

    public static final String funcName = DropRate.class.getAnnotation(FuncAttr.class).name();
//    @Override
//    public double culculate(List<DataInfo> stockInfos) {
//        DataInfo dataInfoStart = stockInfos.get(0);
//        DataInfo dataInfoEnd = stockInfos.get(stockInfos.size() - 1);
//        double v = (dataInfoEnd.getClose() - dataInfoStart.getOpen()) / dataInfoStart.getOpen();
//        BigDecimal b = new BigDecimal(-v * 100);
//        double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//        return f1;
//    }
    @Override
    public double culculate(List<DataInfo> stockInfos) {
        DataInfo dataInfoEnd = stockInfos.get(stockInfos.size() - 1);
        return -dataInfoEnd.getChangePercent();
    }




    @Override
    public String formatTemplate() {
        return "跌幅{v}%";
    }
}
