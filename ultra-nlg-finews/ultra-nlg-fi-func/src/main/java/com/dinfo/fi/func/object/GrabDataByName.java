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
@FuncAttr(name = "根据股票代码获得数据",type = FuncType.ObjectFunc)
public class GrabDataByName implements ObjectFuncInterface{

    @Autowired
    private StockDataService stockDataService;
    @Autowired
    private StockInfoService stockInfoService;

    @Override
    public List<DataInfo> queryData(QueryInfoConditionDto query) {
        String name = query.getTip();
        Response stockInfoByName = stockInfoService.getStockCodeByName(name);
        if(stockInfoByName.isNotSuccess()){
            throw new FuncException("获取股票代码失败，股票名称为："+name);
        }
        String data = (String)stockInfoByName.getData();
        Response stockByCodeAndTimeRange = stockDataService.getStockByCodeAndTimeRange(data, query.getStartTime(), query.getEndTime());
        if(stockByCodeAndTimeRange.isNotSuccess()){
            throw new FuncException("获取数据失败，股票代码为："+data);
        }
        return (List<DataInfo>) stockByCodeAndTimeRange.getData();
    }
}
