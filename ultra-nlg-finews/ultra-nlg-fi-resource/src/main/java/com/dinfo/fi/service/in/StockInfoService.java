package com.dinfo.fi.service.in;

import com.dinfo.core.query.CompareType;
import com.dinfo.core.query.Query;
import com.dinfo.fi.dao.StockInfoDao;
import com.dinfo.fi.entity.StockInfo;
import com.dinfo.fi.utils.Response;
import com.dinfo.rdbms.crud.RdmsQuery;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>Date:2018/10/10</p>
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
public class StockInfoService{
    @Autowired
    private StockInfoDao stockInfoDao;

    public Response<StockInfo> getStockInfoByCode(String code){
        Query query = new RdmsQuery();
        query.from(StockInfo.class).where("code", CompareType.EQ,code);
        List<StockInfo> stockInfos = stockInfoDao.queryListByConditions(query);
        if(CollectionUtils.isEmpty(stockInfos)){
            return Response.notOk("该code没有对应的数据");
        }
        return Response.ok(stockInfos.get(0));
    }

    public Response<StockInfo> getStockInfoByName(String name){
        Query query = new RdmsQuery();
        query.from(StockInfo.class).where("name", CompareType.EQ,name);
        List<StockInfo> stockInfos = stockInfoDao.queryListByConditions(query);
        if(CollectionUtils.isEmpty(stockInfos)){
            return Response.notOk("该name没有对应的数据");
        }
        return Response.ok(stockInfos.get(0));
    }


    public Response<List> getStockInfoByArea(String area){
        Query query = new RdmsQuery();
        query.from(StockInfo.class).where("area", CompareType.EQ,area);
        List<StockInfo> stockInfos = stockInfoDao.queryListByConditions(query);
        if(CollectionUtils.isEmpty(stockInfos)){
            return Response.notOk("该area没有对应的数据");
        }
        return Response.ok(stockInfos);
    }

    public Response<List> getStockInfoByEalComponent(String ealComponent){
        Query query = new RdmsQuery();
        query.from(StockInfo.class).where("eal_component", CompareType.EQ,ealComponent);
        List<StockInfo> stockInfos = stockInfoDao.queryListByConditions(query);
        if(CollectionUtils.isEmpty(stockInfos)){
            return Response.notOk("该eal_component没有对应的数据");
        }
        return Response.ok(stockInfos);
    }


    public Response<List> getStockInfoByShareType(String shareType){
        Query query = new RdmsQuery();
        query.from(StockInfo.class).where("share_type", CompareType.EQ,shareType);
        List<StockInfo> stockInfos = stockInfoDao.queryListByConditions(query);
        if(CollectionUtils.isEmpty(stockInfos)){
            return Response.notOk("该share_type没有对应的数据");
        }
        return Response.ok(stockInfos);
    }


    public Response<List> getStockCodeByIndustry(String industry) {
        Response<List> stockInfoByIndustry = getStockInfoByIndustry(industry);
        if(stockInfoByIndustry.isNotSuccess()){
            return stockInfoByIndustry;
        }
        List<StockInfo> data = stockInfoByIndustry.getData();
        List<String> collect = data.stream().map(stockInfo -> stockInfo.getCode()).collect(Collectors.toList());
        return Response.ok(collect);

    }


    public Response<List> getStockInfoByIndustry(String industry) {
        List<StockInfo> code = stockInfoDao.getJdbcTemplate().query("select * from shares_knowledge_base where locate('" + industry + "',industry) ", new RowMapper<StockInfo>() {
            @Nullable
            @Override
            public StockInfo mapRow(ResultSet resultSet, int i) throws SQLException {

                return changeResultSetToStockInfo(resultSet);
            }
        }).stream().filter(stockInfo ->
            Arrays.asList(stockInfo.getIndustry().split("、")).contains(industry)
        ).collect(Collectors.toList());
        return Response.ok(code);

    }

    private StockInfo changeResultSetToStockInfo(ResultSet resultSet) throws SQLException {
        StockInfo stockInfo = new StockInfo();
        stockInfo.setId(resultSet.getString(1));
        stockInfo.setCode(resultSet.getString(2));
        stockInfo.setName(resultSet.getString(3));
        stockInfo.setArea(resultSet.getString(4));
        stockInfo.setShareType(resultSet.getString(5));
        stockInfo.setIndustry(resultSet.getString(6));
        stockInfo.setConcepts(resultSet.getString(7));
        stockInfo.setEalComponent(resultSet.getString(8));
        return stockInfo;
    }


    public Response<List> getStockCodeByConcept(String concept) {
        Response<List> stockInfoByConcept = getStockInfoByConcept(concept);
        if(stockInfoByConcept.isNotSuccess()){
            return stockInfoByConcept;
        }
        List<StockInfo> data = stockInfoByConcept.getData();
        List<String> collect = data.stream().map(stockInfo -> stockInfo.getCode()).collect(Collectors.toList());
        return Response.ok(collect);

    }

    public Response<List> getStockInfoByConcept(String concept) {
        List<StockInfo> code = stockInfoDao.getJdbcTemplate().query("select * from shares_knowledge_base where locate('" + concept + "',concepts) ", new RowMapper<StockInfo>() {
            @Nullable
            @Override
            public StockInfo mapRow(ResultSet resultSet, int i) throws SQLException {

                return changeResultSetToStockInfo(resultSet);
            }
        }).stream().filter(stockInfo ->
                Arrays.asList(stockInfo.getConcepts().substring(1,stockInfo.getConcepts().length()-1).split(", ")).contains(concept)
        ).collect(Collectors.toList());
        return Response.ok(code);

    }



    public Response<String> getStockCodeByName(String name){
        Query query = new RdmsQuery();
        query.from(StockInfo.class).where("name", CompareType.EQ,name);
        List<StockInfo> stockInfos = stockInfoDao.queryListByConditions(query);
        if(CollectionUtils.isEmpty(stockInfos)){
            return Response.notOk("该name没有对应的数据");
        }
        return Response.ok(stockInfos.get(0).getCode());
    }


    public Response<List> getStockCodeByArea(String area){
        Query query = new RdmsQuery();
        query.from(StockInfo.class).where("area", CompareType.EQ,area);
        List<StockInfo> stockInfos = stockInfoDao.queryListByConditions(query);
        if(CollectionUtils.isEmpty(stockInfos)){
            return Response.notOk("该area没有对应的数据");
        }
        List<String> collect = stockInfos.stream().map(stockInfo -> stockInfo.getCode()).collect(Collectors.toList());
        return Response.ok(collect);
    }

    public Response<List> getStockCodeByEalComponent(String ealComponent){
        Query query = new RdmsQuery();
        query.from(StockInfo.class).where("eal_component", CompareType.EQ,ealComponent);
        List<StockInfo> stockInfos = stockInfoDao.queryListByConditions(query);
        if(CollectionUtils.isEmpty(stockInfos)){
            return Response.notOk("该eal_component没有对应的数据");
        }
        List<String> collect = stockInfos.stream().map(stockInfo -> stockInfo.getCode()).collect(Collectors.toList());
        return Response.ok(collect);
    }


    public Response<List> getStockCodeByShareType(String shareType){
        Query query = new RdmsQuery();
        query.from(StockInfo.class).where("share_type", CompareType.EQ,shareType);
        List<StockInfo> stockInfos = stockInfoDao.queryListByConditions(query);
        if(CollectionUtils.isEmpty(stockInfos)){
            return Response.notOk("该share_type没有对应的数据");
        }
        List<String> collect = stockInfos.stream().map(stockInfo -> stockInfo.getCode()).collect(Collectors.toList());
        return Response.ok(collect);
    }
}
