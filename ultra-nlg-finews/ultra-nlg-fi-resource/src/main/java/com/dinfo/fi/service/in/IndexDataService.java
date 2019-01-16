package com.dinfo.fi.service.in;

import com.dinfo.fi.dao.IndexDataDao;
import com.dinfo.fi.entity.IndexDataInfo;
import com.dinfo.fi.entity.StockDataInfo;
import com.dinfo.fi.utils.Response;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
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
public class IndexDataService {

    @Autowired
    private IndexDataDao indexDataDao;


    public Response<List> getDataByTimeRange(String code, String startTime, String endTime) {
        List<IndexDataInfo> query = indexDataDao.getJdbcTemplate().query("SELECT * FROM "+indexDataDao.getTable()+" WHERE time BETWEEN '" + startTime + "' AND '" + endTime + "' AND `code`='" + code + "'", new RowMapper<IndexDataInfo>() {
            @Nullable
            @Override
            public IndexDataInfo mapRow(ResultSet resultSet, int i) throws SQLException {

                return changeResultSetToStockDataInfo(resultSet);
            }
        });
        if(CollectionUtils.isEmpty(query)){
            return Response.notOk("数据为null");
        }
        return Response.ok(query);

    }

    public Response getDataByTimeRangeAndCodeRange(String codeRange, String startTime, String endTime) {
        List<IndexDataInfo> query = indexDataDao.getJdbcTemplate().query("SELECT * FROM "+indexDataDao.getTable()+" WHERE time BETWEEN '" + startTime + "' AND '" + endTime + "' AND `code`in(" + codeRange + ")", new RowMapper<IndexDataInfo>() {
            @Nullable
            @Override
            public IndexDataInfo mapRow(ResultSet resultSet, int i) throws SQLException {


                return changeResultSetToStockDataInfo(resultSet);
            }
        });
        if(CollectionUtils.isEmpty(query)){
            return Response.notOk("数据为null");
        }
        return Response.ok(query);
    }

    private IndexDataInfo changeResultSetToStockDataInfo(ResultSet resultSet) throws SQLException {
        IndexDataInfo stockDataInfo = new IndexDataInfo();
        stockDataInfo.setCode(resultSet.getString(1));
        stockDataInfo.setName(resultSet.getString(2));
        stockDataInfo.setTime(resultSet.getTimestamp(3));
        stockDataInfo.setChangePercent(resultSet.getDouble(4));
        stockDataInfo.setOpen(resultSet.getDouble(5));
        stockDataInfo.setHigh(resultSet.getDouble(6));
        stockDataInfo.setLow(resultSet.getDouble(7));
        stockDataInfo.setClose(resultSet.getDouble(8));
        stockDataInfo.setVolume(resultSet.getLong(9));
        stockDataInfo.setAmount(resultSet.getDouble(10));
        stockDataInfo.setSettlement(resultSet.getDouble(11));
        return stockDataInfo;
    }
}
