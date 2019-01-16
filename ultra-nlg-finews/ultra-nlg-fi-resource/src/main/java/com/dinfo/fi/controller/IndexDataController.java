package com.dinfo.fi.controller;

import com.dinfo.fi.service.in.IndexDataService;
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
@Api(description = "指数数据Controller",basePath = "/indexData")
@RestController
@RequestMapping("/indexData")
public class IndexDataController {

    @Autowired
    private IndexDataService indexDataService;
    /**
     * @return
     */
    @RequestMapping(value = "/getIndexByCodeAndTimeRange",method = RequestMethod.GET)
    @ApiOperation(value="根据指数代码和时间区间获取数据区间", notes="")
    public Response getIndexByCodeAndTimeRange(
            @ApiParam(value = "股票代码")@RequestParam String code,
            @ApiParam(value = "数据开始时间")@RequestParam String startTime,
            @ApiParam(value = "数据结束时间")@RequestParam String endTime
                                               ){
        return indexDataService.getDataByTimeRange(code,startTime,endTime);
    }


    /**
     * @return
     */
    @RequestMapping(value = "/getIndexByCodeRangeAndTimeRange",method = RequestMethod.GET)
    @ApiOperation(value="根据股票代码和时间区间获取数据区间", notes="")
    public Response getIndexByCodeRangeAndTimeRange(
            @ApiParam(value = "多个股票代码，以,分隔")@RequestParam String codeRange,
            @ApiParam(value = "数据开始时间")@RequestParam String startTime,
            @ApiParam(value = "数据结束时间")@RequestParam String endTime
    ){
        return indexDataService.getDataByTimeRangeAndCodeRange(codeRange,startTime,endTime);
    }






}

