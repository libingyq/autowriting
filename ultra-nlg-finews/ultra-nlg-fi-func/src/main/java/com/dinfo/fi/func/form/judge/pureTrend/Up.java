package com.dinfo.fi.func.form.judge.pureTrend;

import com.alibaba.fastjson.JSON;
import com.dinfo.fi.anno.FuncAttr;
import com.dinfo.fi.dto.DTWCompareDto;
import com.dinfo.fi.entity.DataInfo;
import com.dinfo.fi.enums.FuncType;
import com.dinfo.fi.func.form.judge.JudgeFuncBaseClass;
import com.dinfo.fi.inter.JudgeFuncInterface;
import com.dinfo.fi.service.out.PyInterface;
import com.dinfo.fi.utils.TwoTuple;
import org.springframework.beans.factory.annotation.Autowired;
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
@FuncAttr(name = "高走",type = FuncType.JudgeFunc)
public class Up extends JudgeFuncBaseClass {
    public static final String funcName = Up.class.getAnnotation(FuncAttr.class).name();

}
