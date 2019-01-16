package com.dinfo.fi.inter;

import com.dinfo.fi.dto.QueryInfoConditionDto;
import com.dinfo.fi.entity.DataInfo;
import com.dinfo.fi.enums.StockIndexConditionType;
import com.dinfo.fi.inter.base.FuncInterface;
import com.dinfo.fi.utils.TwoTuple;

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
public interface ObjectFuncInterface extends FuncInterface {

    List<DataInfo> queryData(QueryInfoConditionDto query);


    @Override
    default Map run(Map input) {
        TwoTuple<String, String> data = getData(input);
        List<DataInfo> parse = queryData(QueryInfoConditionDto.builder().chooseType((StockIndexConditionType)input.get(DATA_TYPE_KEY))
                .startTime(data.first).endTime(data.second)
                .tip((String)input.get(CODE_KEY)).build());
        input.put(getFuncName(), parse);
        input.put(ObjectFuncInterface.class.getName(), parse);
        input.put(DATA_NAME,parse.get(0).getName());
        return input;
    }

    @Override
    default String getKey() {
        return TimeFuncInterface.class.getName();
    }
}
