package com.dinfo.fi.controller;

import com.dinfo.fi.service.PyInterface;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>Date:2017/12/28</p>
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
@Api(description = "app-service测试Controller",basePath = "/app/test")
@RestController
@RequestMapping("/app/test")
public class AppTestController {



    @Autowired
    PyInterface pyInterface;



    @RequestMapping(value = "/testPyInter",method = RequestMethod.GET)
    @ApiOperation(value="测试py接口", notes="")
    public String  testPyInter(){
        return pyInterface.testPyInter();
    }





}
