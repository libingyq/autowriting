package com.dinfo.fi.controller;

import com.dinfo.fi.service.in.DescribeService;
import com.dinfo.fi.utils.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther rongzihao
 * @date 2018/10/31 11:32
 */
@Api(description = "趋势表述Controller",basePath = "/describe")
@RestController
@RequestMapping("/describe")
public class DescribeController {

    @Autowired
    DescribeService describeService;


    @RequestMapping(value = "/getDescribeByTrend",method = RequestMethod.GET)
    @ApiOperation(value="根据趋势获取表述", notes="")
    public Response getDescribeByTrend(@ApiParam(value = "趋势")@RequestParam String trend){
        return describeService.getDescribeByTrend(trend);
    }
}
