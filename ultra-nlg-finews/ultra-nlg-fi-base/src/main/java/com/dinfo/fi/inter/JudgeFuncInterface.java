package com.dinfo.fi.inter;

import com.dinfo.core.ModelType;
import com.dinfo.fi.dto.DTWCompareDto;
import com.dinfo.fi.entity.DataInfo;
import com.dinfo.fi.entity.StockInfo;
import com.dinfo.fi.inter.base.FuncInterface;
import com.dinfo.fi.utils.TwoTuple;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
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
public interface JudgeFuncInterface extends FuncInterface {


    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    TwoTuple<Boolean,Double> judge(List<DataInfo> dataInfos);

    int getLevel();

    default Map run(Map input) {
        TwoTuple<Boolean, Double> judge = judge(getData(input));
        input.put(this.getFuncName(), judge.first);
        input.put(RESULT_FLG,this.getFuncName());
        input.put(FORMAT_KEY,this.formatTemplate());
        input.put(FUNC_TYPE_KEY,this.getFuncType());
        input.put(JUDGE_SCORE,judge.second);
        return input;
    }


    @Override
    default String getKey() {
        return ObjectFuncInterface.class.getName();
    }


    default DTWCompareDto getDTWCompare(List<DataInfo> dataInfos){
        DataInfo fir = dataInfos.get(0);
        DataInfo last = dataInfos.get(dataInfos.size()-1);

        String tableName = fir.getClass().getDeclaredAnnotation(ModelType.class).tableName();

        return DTWCompareDto.builder().code(fir.getCode()).startTime(sdf.format(fir.getTime()))
                .endTime(sdf.format(last.getTime())).tableName(tableName).trend(this.getFuncName()).build();
    }
}
