package com.dinfo.fi.func.form.judge;

import com.dinfo.fi.anno.FuncAttr;
import com.dinfo.fi.entity.DataInfo;
import com.dinfo.fi.enums.FuncType;
import com.dinfo.fi.inter.JudgeFuncInterface;
import com.dinfo.fi.utils.TwoTuple;
import org.springframework.stereotype.Component;

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
@FuncAttr(name = "涨停",type = FuncType.JudgeFunc)
public class LimitUp implements JudgeFuncInterface{

    public static final String funcName = LimitUp.class.getAnnotation(FuncAttr.class).name();

    @Override
    public TwoTuple<Boolean,Double> judge(List<DataInfo> dataInfos) {

        DataInfo end = dataInfos.get(dataInfos.size() - 1);
        double v = end.getChangePercent();

        return new TwoTuple<>(v > 10,0.0) ;
    }

    @Override
    public int getLevel() {
        return 2;
    }
}
