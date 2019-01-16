package com.dinfo.fi.anno;


import com.dinfo.fi.enums.FuncType;

import java.lang.annotation.*;

/**
 * <p>Date:2018/9/25</p>
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
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface FuncAttr {

    String name();

    FuncType type();

}
