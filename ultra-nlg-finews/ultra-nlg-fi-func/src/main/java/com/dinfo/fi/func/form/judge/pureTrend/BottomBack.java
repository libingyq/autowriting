package com.dinfo.fi.func.form.judge.pureTrend;

import com.dinfo.fi.anno.FuncAttr;
import com.dinfo.fi.enums.FuncType;
import com.dinfo.fi.func.form.judge.JudgeFuncBaseClass;
import com.dinfo.fi.func.form.judge.LimitDown;
import org.springframework.stereotype.Component;

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
@FuncAttr(name = "触底反弹",type = FuncType.JudgeFunc)
public class BottomBack extends JudgeFuncBaseClass {
    public static final String funcName = BottomBack.class.getAnnotation(FuncAttr.class).name();

}
