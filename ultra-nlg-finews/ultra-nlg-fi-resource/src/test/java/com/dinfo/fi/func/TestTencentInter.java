package com.dinfo.fi.func;

import com.dinfo.fi.dao.StockInfoDao;
import com.dinfo.fi.dao.TencentStockDataDao;
import com.dinfo.fi.entity.StockDataInfo;
import com.dinfo.fi.entity.StockInfo;
import com.dinfo.fi.entity.TencentStockDataInfo;
import com.dinfo.fi.utils.RestApiClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;

/**
 * <p>Date:2018/10/29</p>
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
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestTencentInter {

    @Autowired
    private StockInfoDao stockInfoDao;

    @Autowired
    private TencentStockDataDao tencentStockDataDao;

    public static String ealSH = "沪市";
    public static String ealSZ = "深市";

    @Test
    public void testClean(){
        Boolean execute = tencentStockDataDao.getJdbcTemplate().execute("DELETE FROM " + tencentStockDataDao.getTable() + " WHERE time < '2018-10-31 11:14:02'",
                (PreparedStatement preparedStatement) -> preparedStatement.execute());
        System.out.println(execute);
    }

    @Test
    public void testMulThreadGet() throws ExecutionException, InterruptedException {
        List<String> mulCode = getMulCode();
        String url = "http://qt.gtimg.cn/q=";
        ConcurrentLinkedQueue<String> concurrentLinkedQueue = new ConcurrentLinkedQueue();
        concurrentLinkedQueue.addAll(mulCode);
        long start = System.currentTimeMillis();
        ConcurrentHashMap<String, TencentStockDataInfo> concurrentHashMap = new ConcurrentHashMap<>();
        ConcurrentTestUtil.concurrentTest(mulCode.size(), mulCode.size(),
                new Callable<String>() {
                    @Override
                    public String call() throws Exception {
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
                    }
                },
                new RequestHandler<String>() {
                    @Override
                    public void handle(String result) {
                        if(!result.equals("ok")){
                            System.out.println(result);
                        }
                    }
                }, 6000);

        long end = System.currentTimeMillis();
        System.out.println(end-start);
        System.out.println(concurrentHashMap.size());

        tencentStockDataDao.batchInsert(new ArrayList<>(concurrentHashMap.values()));
        long insertFinish = System.currentTimeMillis();
        System.out.println(insertFinish-start);
        System.out.println("end");
    }


    private List<String> getMulCode(){
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
        return multiCode;
    }


    @Test
    public void testBatch(){

        String url = "http://qt.gtimg.cn/q=";

        List<String> multiCode = getMulCode();

        long start = System.currentTimeMillis();
        HashMap<String, TencentStockDataInfo> stringMapHashMap = new HashMap<>();
        for (String s : multiCode) {
            String access = RestApiClient.getAccess(url + s, new HashMap<>());
            String[] split = access.split("\n");
            for (String s1 : split) {
                TencentStockDataInfo stockDataInfo = changeToStockDataInfo(s1);
                stringMapHashMap.put(stockDataInfo.getName(),stockDataInfo);
            }
        }
        long end = System.currentTimeMillis();
        System.out.println(end-start);


        long l = System.currentTimeMillis();
        List<TencentStockDataInfo> stockDataInfo_copies = tencentStockDataDao.batchInsert(new ArrayList<>(stringMapHashMap.values()));
        long l1 = System.currentTimeMillis();

        System.out.println(l1-l);

        System.out.println("==");

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



    private HashMap changeToMap(String access){

        try {
            String[] split = access.substring(access.indexOf("\""), access.lastIndexOf("\"")).split(",");

            String code = access.substring(13, access.indexOf("="));

            HashMap<String, String> stockMap = new HashMap<>();
            stockMap.put("code",code);
            stockMap.put("股票名称",split[0]);
            stockMap.put("今日开盘价",split[1]);
            stockMap.put("昨日收盘价",split[2]);
            stockMap.put("当前价格",split[3]);
            stockMap.put("今日最高价",split[4]);
            stockMap.put("今日最低价",split[5]);
            stockMap.put("成交的股票数",split[8]);
            stockMap.put("成交金额",split[9]);
            return stockMap;
        }catch (Exception e){
            System.out.println(access);
        }
        return null;
    }

}
