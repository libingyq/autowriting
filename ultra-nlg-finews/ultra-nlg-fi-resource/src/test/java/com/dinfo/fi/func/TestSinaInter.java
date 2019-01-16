package com.dinfo.fi.func;

import com.dinfo.fi.dao.StockInfoDao;
import com.dinfo.fi.entity.StockDataInfo;
import com.dinfo.fi.entity.StockInfo;
import com.dinfo.fi.utils.RestApiClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class TestSinaInter {

    @Autowired
    private StockInfoDao stockInfoDao;

    public static String ealSH = "沪市";
    public static String ealSZ = "深市";



    @Test
    public void testBatch(){
        List<StockInfo> stockInfos = stockInfoDao.queryList();
        String url = "http://hq.sinajs.cn/list=";
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

            if((i % 50 == 0 && i != 0) || i == stockInfos.size()-1){
                multiCode.add(stringBuffer.toString());
                stringBuffer = new StringBuffer();
            }
        }
        long start = System.currentTimeMillis();
        HashMap<String, StockDataInfo> stringMapHashMap = new HashMap<>();
        for (String s : multiCode) {
            String access = RestApiClient.getAccess(url + s, new HashMap<>());
            String[] split = access.split("\n");
            for (String s1 : split) {
                StockDataInfo stockDataInfo = changeToStockDataInfo(s1);
                stringMapHashMap.put(stockDataInfo.getName(),stockDataInfo);
            }
        }
        long end = System.currentTimeMillis();
        System.out.println(end-start);
        System.out.println("==");

    }

    @Test
    public void testSinaInter(){


        RestApiClient restApiClient = new RestApiClient();

        String url = "http://hq.sinajs.cn/list=";

        List<StockInfo> stockInfos = stockInfoDao.queryList();

        long start = System.currentTimeMillis();
        HashMap<String, Map> hashMap = new HashMap<>();
        for (StockInfo stockInfo : stockInfos) {
            boolean equals = stockInfo.getEalComponent().equals(ealSH);
            if(equals){
                String s = "sh" + stockInfo.getCode();
                String access = RestApiClient.getAccess(url + s, new HashMap<>());
                HashMap value = changeToMap(access);
                hashMap.put(s, value);
            }else{
                String s = "sz" + stockInfo.getCode();
                String access = RestApiClient.getAccess(url + s, new HashMap<>());
                HashMap value = changeToMap(access);
                hashMap.put(s, value);
            }
        }
        long end = System.currentTimeMillis();

        System.out.println(end - start);

        System.out.println(stockInfos);


    }

    private StockDataInfo changeToStockDataInfo(String access){

        try {
            String[] split = access.substring(access.indexOf("\""), access.lastIndexOf("\"")).split(",");

            String code = access.substring(13, access.indexOf("="));

            double currentPrice = Double.parseDouble(split[3]);
            double settlement = Double.parseDouble(split[2]);


            return StockDataInfo.builder().code(code).name(split[0]).open(Double.parseDouble(split[1]))
                    .close(currentPrice).high(Double.parseDouble(split[4]))
                    .low(Double.parseDouble(split[5])).volume(Long.parseLong(split[8]))
                    .settlement(settlement)
                    .time(new Timestamp(System.currentTimeMillis()))
                    .amount(Double.parseDouble(split[9]))
                    //现价减去昨日收盘价再除以昨日收盘价
                    .changePercent((currentPrice - settlement) / settlement)
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
