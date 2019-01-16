package com.dinfo.fi.func.object;

import com.dinfo.fi.anno.FuncAttr;
import com.dinfo.fi.dto.QueryInfoConditionDto;
import com.dinfo.fi.entity.DataInfo;
import com.dinfo.fi.enums.FuncType;
import com.dinfo.fi.exception.FuncException;
import com.dinfo.fi.inter.ObjectFuncInterface;
import com.dinfo.fi.service.out.StockDataService;
import com.dinfo.fi.service.out.StockInfoService;
import com.dinfo.fi.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>Date:2018/10/15</p>
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
@FuncAttr(name = "根据股票概念获得数据",type = FuncType.ObjectFunc)
public class GrabDataByShareType implements ObjectFuncInterface{

    @Autowired
    private StockDataService stockDataService;
    @Autowired
    private StockInfoService stockInfoService;

    @Override
    public List<DataInfo> queryData(QueryInfoConditionDto query) {
        String shareType = query.getTip();
        Response stockCodeByEalComponent = stockInfoService.getStockCodeByShareType(shareType);
        if(stockCodeByEalComponent.isNotSuccess()){
            throw new FuncException("获取股票代码失败，股盘为："+shareType);
        }
        List<String> data = (List<String>)stockCodeByEalComponent.getData();
        StringBuffer stringBuffer = new StringBuffer();
        for (String s : data) {
            stringBuffer.append(s).append(",");
        }
        String codes = stringBuffer.substring(0, stringBuffer.length() - 1);
        Response stockByCodeAndTimeRange = stockDataService.getStockByCodeRangeAndTimeRange(codes, query.getStartTime(), query.getEndTime());
        if(stockByCodeAndTimeRange.isNotSuccess()){
            throw new FuncException("获取数据失败，股票代码为："+codes);
        }
        return (List<DataInfo>) stockByCodeAndTimeRange.getData();
    }
}
