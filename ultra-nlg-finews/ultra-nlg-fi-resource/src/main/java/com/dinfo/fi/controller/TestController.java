package com.dinfo.fi.controller;

import com.dinfo.fi.dao.GrabLogDao;
import com.dinfo.fi.dao.StockInfoDao;
import com.dinfo.fi.entity.GrabLog;
import com.dinfo.fi.service.out.PyInterface;
import com.dinfo.fi.utils.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

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
@Api(description = "测试用Controller",basePath = "/test")
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private StockInfoDao stockInfoDao;



    @Autowired
    private PyInterface pyInterface;

    @Autowired
    private GrabLogDao grabLogDao;
    /**
     * @return
     */
    @RequestMapping(value = "/testPyInter",method = RequestMethod.GET)
    @ApiOperation(value="测试python接口调用(指数)", notes="")
    public Response  testPyInter(){
        return Response.ok(pyInterface.updateIndeces());
    }


//    /**
//     * @return
//     */
//    @RequestMapping(value = "/testPyInterMore",method = RequestMethod.GET)
//    @ApiOperation(value="测试python接口调用（股票数据）", notes="")
//    public Response  testPyInterMore(){
//        return Response.ok(pyInterface.updatetickers());
//    }
    /**
     * @return
     */
    @RequestMapping(value = "/testDataSource",method = RequestMethod.GET)
    @ApiOperation(value="测试数据源", notes="")
    public Response testDataSource(){
        return Response.ok(stockInfoDao.getCount());
    }


    @RequestMapping(value = "/testLog",method = RequestMethod.GET)
    @ApiOperation(value="测试日志", notes="")
    public Response testLog(){


        GrabLog index = grabLogDao.insert(GrabLog.builder().grabTime(new Timestamp(System.currentTimeMillis()))
                .dataType("index").status("200").content("{status:200}").retry(0).build());
        return Response.ok(index);
    }





}

