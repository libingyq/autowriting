package com.dinfo.fi.service.in;

import com.dinfo.fi.dao.StockDataDao;
import com.dinfo.fi.entity.StockDataInfo;
import com.dinfo.fi.entity.TencentStockDataInfo;
import com.dinfo.fi.utils.Response;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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
@Service
public class StockDataService {

    @Autowired
    private StockDataDao stockDataDao;


    public Response<List> getDataByTimeRange(String code, String startTime, String endTime) {
        List<TencentStockDataInfo> query = stockDataDao.getJdbcTemplate().query("SELECT * FROM "+stockDataDao.getTable()+" WHERE time BETWEEN '" + startTime + "' AND '" + endTime + "' AND `code`='" + code + "'", new RowMapper<TencentStockDataInfo>() {
            @Nullable
            @Override
            public TencentStockDataInfo mapRow(ResultSet resultSet, int i) throws SQLException {

                return changeResultSetToStockDataInfo(resultSet);
            }
        });
        if(CollectionUtils.isEmpty(query)){
            return Response.notOk("数据为null");
        }
        return Response.ok(query);

    }

    public Response getDataByTimeRangeAndCodeRange(String codeRange, String startTime, String endTime) {
        List<TencentStockDataInfo> query = stockDataDao.getJdbcTemplate().query("SELECT * FROM "+stockDataDao.getTable()+" WHERE time BETWEEN '" + startTime + "' AND '" + endTime + "' AND `code`in(" + codeRange + ")", new RowMapper<TencentStockDataInfo>() {
            @Nullable
            @Override
            public TencentStockDataInfo mapRow(ResultSet resultSet, int i) throws SQLException {
                return changeResultSetToStockDataInfo(resultSet);
            }
        });
        if(CollectionUtils.isEmpty(query)){
            return Response.notOk("数据为null");
        }
        return Response.ok(query);
    }

    private TencentStockDataInfo changeResultSetToStockDataInfo(ResultSet resultSet) throws SQLException {
        TencentStockDataInfo stockDataInfo = new TencentStockDataInfo();
        stockDataInfo.setCode(resultSet.getString(1));
        stockDataInfo.setName(resultSet.getString(2));
        stockDataInfo.setTime(resultSet.getTimestamp(3));
        stockDataInfo.setChangePercent(resultSet.getDouble(4));
        stockDataInfo.setOpen(resultSet.getDouble(5));
        stockDataInfo.setHigh(resultSet.getDouble(6));
        stockDataInfo.setLow(resultSet.getDouble(7));
        stockDataInfo.setClose(resultSet.getDouble(8));
        stockDataInfo.setVolume(resultSet.getLong(9));
        stockDataInfo.setTurnover(resultSet.getDouble(10));
        stockDataInfo.setAmount(resultSet.getDouble(11));
        stockDataInfo.setSettlement(resultSet.getDouble(12));
        return stockDataInfo;
    }
}
