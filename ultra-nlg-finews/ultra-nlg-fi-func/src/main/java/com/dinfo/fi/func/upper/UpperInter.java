package com.dinfo.fi.func.upper;

import com.dinfo.fi.anno.FuncAttr;
import com.dinfo.fi.enums.FuncType;

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
public interface UpperInter {

    String reduce(String oriTemp);

    default FuncType getFuncType(){
        FuncAttr annotation = this.getClass().getAnnotation(FuncAttr.class);
        return annotation.type();
    };

    default String getFuncName(){
        FuncAttr annotation = this.getClass().getAnnotation(FuncAttr.class);
        return annotation.name();
    }
}
