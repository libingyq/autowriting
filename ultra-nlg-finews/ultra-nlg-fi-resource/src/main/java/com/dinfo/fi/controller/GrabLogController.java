package com.dinfo.fi.controller;

import com.dinfo.fi.dto.GrabStatusDto;
import com.dinfo.fi.enums.ShareType;
import com.dinfo.fi.service.in.GrabLogService;
import com.dinfo.fi.utils.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author zhangjinsheng
 */
@Api(description = "爬取日志Controller",basePath = "/grabLog")
@RestController
@RequestMapping(path = "/grabLog")
public class GrabLogController {
    @Autowired
    GrabLogService grabLogService;

    @RequestMapping(path = "/getGrabStatus",method = RequestMethod.GET)
    @ApiOperation(value = "获取爬取日志的最近信息")
    public Response<Map<ShareType,GrabStatusDto>> getGrabStatus(){
        return grabLogService.getGrabStatus();
    }

}
