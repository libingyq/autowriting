package com.dinfo.fi.service.in;

import com.dinfo.fi.dao.IndustryDataDao;
import com.dinfo.fi.entity.IndustryData;
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
public class IndustryDataService {

    @Autowired
    private IndustryDataDao industryDataDao;


    public Response<List> getDataByTimeRange(String code, String startTime, String endTime) {
        List<IndustryData> query = industryDataDao.getJdbcTemplate().query("SELECT * FROM "+ industryDataDao.getTable()+" WHERE time BETWEEN '" + startTime + "' AND '" + endTime + "' AND `code`='" + code + "'", new RowMapper<IndustryData>() {
            @Nullable
            @Override
            public IndustryData mapRow(ResultSet resultSet, int i) throws SQLException {

                return changeResultSetToStockDataInfo(resultSet);
            }
        });
        if(CollectionUtils.isEmpty(query)){
            return Response.notOk("数据为null");
        }
        return Response.ok(query);

    }

    public Response getDataByTimeRangeAndCodeRange(String codeRange, String startTime, String endTime) {
        List<IndustryData> query = industryDataDao.getJdbcTemplate().query("SELECT * FROM "+ industryDataDao.getTable()+" WHERE time BETWEEN '" + startTime + "' AND '" + endTime + "' AND `code`in(" + codeRange + ")", new RowMapper<IndustryData>() {
            @Nullable
            @Override
            public IndustryData mapRow(ResultSet resultSet, int i) throws SQLException {


                return changeResultSetToStockDataInfo(resultSet);
            }
        });
        if(CollectionUtils.isEmpty(query)){
            return Response.notOk("数据为null");
        }
        return Response.ok(query);
    }

    private IndustryData changeResultSetToStockDataInfo(ResultSet resultSet) throws SQLException {
        IndustryData stockDataInfo = new IndustryData();
        stockDataInfo.setCode(resultSet.getString(1));
        stockDataInfo.setName(resultSet.getString(2));
        stockDataInfo.setTime(resultSet.getTimestamp(3));
        stockDataInfo.setChangePercent(resultSet.getDouble(4));
        stockDataInfo.setOpen(resultSet.getDouble(5));
        stockDataInfo.setHigh(resultSet.getDouble(6));
        stockDataInfo.setLow(resultSet.getDouble(7));
        stockDataInfo.setClose(resultSet.getDouble(8));
        stockDataInfo.setSettlement(resultSet.getDouble(9));
        stockDataInfo.setSumNum(resultSet.getLong(10));
        stockDataInfo.setSwing(resultSet.getDouble(11));
        stockDataInfo.setTurnOverRate(resultSet.getDouble(12));
        stockDataInfo.setInfos(resultSet.getString(13));
        stockDataInfo.setLeadUp(resultSet.getString(14));
        stockDataInfo.setLeadDown(resultSet.getString(15));
        return stockDataInfo;
    }
}
