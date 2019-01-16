package com.dinfo.fi.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dinfo.fi.dao.GrabLogDao;
import com.dinfo.fi.entity.GrabLog;
import com.dinfo.fi.service.out.PyInterface;
import com.dinfo.fi.task.eastmoney.IndustryAndConceptGraber;
import com.dinfo.fi.task.tencent.TencentStockGraber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;


@FunctionalInterface
interface GrabDataFunc {
    boolean grabData(int retry);


}



@Slf4j
@Component
public class ScheduledTask {

    @Autowired
    private PyInterface pyInterface;
    @Autowired
    private GrabLogDao grabLogDao;
    @Autowired
    private TencentStockGraber tencentStockGraber;
    @Autowired
    private IndustryAndConceptGraber industryAndConceptGraber;

    String index = "index";

    String stock = "stock";

    String concept = "concept";

    String industry = "industry";

    String SUC_CODE = "200";

    int retryTime = 3;
    /**
     * 抓取指数信息
     */
    private GrabDataFunc grabIndecesDataFunc = (int retry) -> {
        String s = pyInterface.updateIndeces();
        JSONObject jsonObject = JSON.parseObject(s);
        log.info(s);
        String status = jsonObject.getString("status");
        grabLogDao.insert(GrabLog.builder()
                .content(s).status(status).retry(retry)
                .dataType(index).grabTime(new Timestamp(System.currentTimeMillis())).build());

        return status.equals(SUC_CODE);
    };

    /**
     * 抓取股票信息
     */
    private GrabDataFunc grabStockDataFunc = (int retryTime) -> {
        boolean result = false;
        try {
            result = tencentStockGraber.mulThreadGrab();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        grabLogDao.insert(GrabLog.builder()
                .content("tencent接口调用").status(String.valueOf(result)).retry(retryTime)
                .dataType(stock).grabTime(new Timestamp(System.currentTimeMillis())).build());
        return result;
    };

    /**
     * 抓取行业信息
     */
    private GrabDataFunc grabIndustryDataFunc = (int retryTime) ->{
        boolean result = false;
        try {
            result = industryAndConceptGraber.grabIndustryData();
        }  catch (ParseException e) {
            e.printStackTrace();
        }
        grabLogDao.insert(GrabLog.builder()
                .content("eastMoney接口调用").status(String.valueOf(result)).retry(retryTime)
                .dataType(industry).grabTime(new Timestamp(System.currentTimeMillis())).build());
        return result;
    };

    /**
     * 抓取概念信息
     */
    private GrabDataFunc grabConceptDataFunc = (int retryTime) ->{
        boolean result = false;
        try {
            result = industryAndConceptGraber.grabConceptData();
        }  catch (ParseException e) {
            e.printStackTrace();
        }
        grabLogDao.insert(GrabLog.builder()
                .content("eastMoney接口调用").status(String.valueOf(result)).retry(retryTime)
                .dataType(concept).grabTime(new Timestamp(System.currentTimeMillis())).build());
        return result;
    };



    @Scheduled(cron = "0 0 12 * * ?")
    public void cleanTencentData(){
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.MONTH, -1);
        Date time = instance.getTime();
        log.info("清除一个月前数据。。。");
        tencentStockGraber.deleteBeforeTimeDot(time);
    }


    @Scheduled(cron = "0 */5 9-12 * * 1-5")    //周一到周五，早上九点到十一点半,每隔5分钟调用一次
    public void grapIndustryMorning() {
        checkTimeAndExec(grabIndustryDataFunc);
    }


    @Scheduled(cron = "0 */5 13-15 * * 1-5")    //周一到周五，下午一点到三点,每隔5分钟调用一次
    public void grapIndustryAfternoon() {
        checkTimeAndExec(grabIndustryDataFunc);
    }


    @Scheduled(cron = "0 */5 9-12 * * 1-5")    //周一到周五，早上九点到十一点半,每隔5分钟调用一次
    public void grapConceptMorning() {
        checkTimeAndExec(grabConceptDataFunc);
    }


    @Scheduled(cron = "0 */5 13-15 * * 1-5")    //周一到周五，下午一点到三点,每隔5分钟调用一次
    public void grapConceptAfternoon() {
        checkTimeAndExec(grabConceptDataFunc);
    }



 
    @Scheduled(cron = "0 */5 9-12 * * 1-5")    //周一到周五，早上九点到十一点半,每隔5分钟调用一次
    public void grapIndiceMorning() {
        checkTimeAndExec(grabIndecesDataFunc);
    }


    @Scheduled(cron = "0 */5 13-15 * * 1-5")    //周一到周五，下午一点到三点,每隔5分钟调用一次
    public void grapIndiceAfternoon() {
        checkTimeAndExec(grabIndecesDataFunc);
    }


    @Scheduled(cron = "0 */5 9-12 * * 1-5")    //周一到周五，早上九点到十一点（包括十一点）,每隔5分钟调用一次
    public void grapTickersMorning() {
        checkTimeAndExec(grabStockDataFunc);
    }

    @Scheduled(cron = "0 */5 13-15 * * 1-5")    //周一到周五，下午一点到三点（包括三点）,每隔5分钟调用一次
    public void grapTickersAfternoon() {
        checkTimeAndExec(grabStockDataFunc);
    }


    public boolean isGrabTime(){
        Calendar instance = Calendar.getInstance();
        int hour = instance.get(Calendar.HOUR_OF_DAY);
        int min = instance.get(Calendar.MINUTE);

        boolean isMorning = true;
        boolean isNoon = true;
        if(hour < 13 || hour > 15|| (hour == 15 && min > 0)){
            isNoon = false;
        }
        if(hour < 9 || (hour == 9 && min <30) || hour > 11 || (hour == 11 && min > 30)){
            isMorning = false;
        }
        return isMorning || isNoon;
    }

    private void checkTimeAndExec(GrabDataFunc func){
        if(!isGrabTime()){
            log.info("不在有效时间");
            return;
        }
        try {
            for (int i = 0; i < retryTime; i++) {
                boolean b = func.grabData(i);
                if(b){
                    break;
                }
            }
        }catch (Exception e){
            String message = e.getMessage();
            log.info("定时任务出错："+message);
        }
    }


}

