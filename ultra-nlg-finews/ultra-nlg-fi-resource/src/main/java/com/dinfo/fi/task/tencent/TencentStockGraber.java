package com.dinfo.fi.task.tencent;

import com.dinfo.fi.dao.StockInfoDao;
import com.dinfo.fi.dao.TencentStockDataDao;
import com.dinfo.fi.entity.IndexDataInfo;
import com.dinfo.fi.entity.StockInfo;
import com.dinfo.fi.entity.TencentStockDataInfo;
import com.dinfo.fi.utils.RestApiClient;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;

/**
 * <p>Date:2018/10/31</p>
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
public class TencentStockGraber {

    @Autowired
    private TencentStockDataDao tencentStockDataDao;
    @Autowired
    private StockInfoDao stockInfoDao;

    public static String ealSH = "沪市";
    public static String ealSZ = "深市";

    private static List<String> queryCodeList;


    public boolean deleteBeforeTimeDot(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(date);
        return tencentStockDataDao.getJdbcTemplate().execute("DELETE FROM " + tencentStockDataDao.getTable() + " WHERE time < '" + format + "'",
                (PreparedStatement preparedStatement) -> preparedStatement.execute());
    }


    public boolean mulThreadGrab() throws ExecutionException, InterruptedException {
        List<String> mulCode = getMulCode();
        Long count = stockInfoDao.getCount();
        String url = "http://qt.gtimg.cn/q=";
        ConcurrentLinkedQueue<String> concurrentLinkedQueue = new ConcurrentLinkedQueue();
        concurrentLinkedQueue.addAll(mulCode);
        long start = System.currentTimeMillis();
        ConcurrentHashMap<String, TencentStockDataInfo> concurrentHashMap = new ConcurrentHashMap<>();
        ConcurrentUtil.concurrent(mulCode.size(), mulCode.size(),
                () -> {
                        String poll = concurrentLinkedQueue.poll();
                        try {
                            String access = RestApiClient.getAccess(url + poll, new HashMap<>());
                            String[] split = access.split("\n");
                            for (String s1 : split) {
                                TencentStockDataInfo stockDataInfo = changeToStockDataInfo(s1);
                                concurrentHashMap.put(stockDataInfo.getName(),stockDataInfo);
                            }
                        }catch (Exception e){
                            return "err:"+poll;
                        }
                        return "ok";
                },
                (String result) -> {
                        if(!result.equals("ok")){
                            System.out.println(result);
                    }
                }, 6000);

        long end = System.currentTimeMillis();
        System.out.println(end-start);
        if(count != concurrentHashMap.values().size()){
            return false;
        }
        tencentStockDataDao.batchInsert(new ArrayList<>(concurrentHashMap.values()));
        return true;
    }





    private TencentStockDataInfo changeToStockDataInfo(String access){
        try {

            String[] split = access.substring(access.indexOf("\""), access.lastIndexOf("\"")).split("~");

            return TencentStockDataInfo.builder().code(split[2]).name(split[1]).open(Double.parseDouble(split[5]))
                    .close(Double.parseDouble(split[3])).high(Double.parseDouble(split[33]))
                    .low(Double.parseDouble(split[34])).volume(Long.parseLong(split[36]))
                    .settlement(Double.parseDouble(split[4]))
                    .turnover(Double.parseDouble(split[38]))
                    .time(new Timestamp(System.currentTimeMillis()))
                    .amount(Double.parseDouble(split[37]))
                    //现价减去昨日收盘价再除以昨日收盘价
                    .changePercent(Double.parseDouble(split[32]))
                    .build();

        }catch (Exception e){
            System.out.println(access);
        }
        return null;
    }


    private List<String> getMulCode(){

        if(CollectionUtils.isEmpty(queryCodeList)){
            List<StockInfo> stockInfos = stockInfoDao.queryList();
            ArrayList<String> multiCode = new ArrayList<>();


            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < stockInfos.size(); i++) {

                StockInfo stockInfo = stockInfos.get(i);
                boolean equals = stockInfo.getEalComponent().equals(ealSH);
                if(equals){
                    String s = "sh" + stockInfo.getCode();
                    stringBuffer.append(s).append(",");
                }else{
                    String s = "sz" + stockInfo.getCode();
                    stringBuffer.append(s).append(",");
                }

                if((i % 300 == 0 && i != 0) || i == stockInfos.size()-1){
                    multiCode.add(stringBuffer.toString());
                    stringBuffer = new StringBuffer();
                }
            }
            queryCodeList = multiCode;
        }
        return queryCodeList;


    }

}
