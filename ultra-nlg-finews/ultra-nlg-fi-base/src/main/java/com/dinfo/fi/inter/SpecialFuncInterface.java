package com.dinfo.fi.inter;

import com.dinfo.fi.entity.DataInfo;
import com.dinfo.fi.inter.base.FuncInterface;

import java.util.List;
import java.util.Map;

/**
 * <p>Date:2018/9/20</p>
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
public interface SpecialFuncInterface extends FuncInterface {

    String reduce(List<DataInfo> stockInfos);

    @Override
    default Map run(Map input) {
        input.put(this.getFuncName(),reduce(getData(input)));
        input.put(RESULT_FLG,this.getFuncName());
        input.put(FORMAT_KEY,this.formatTemplate());
        input.put(FUNC_TYPE_KEY,this.getFuncType());
        return input;
    }

    @Override
    default String getKey() {
        return ObjectFuncInterface.class.getName();
    }
}
