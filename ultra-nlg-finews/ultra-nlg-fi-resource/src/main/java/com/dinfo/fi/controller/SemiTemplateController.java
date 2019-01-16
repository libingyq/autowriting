package com.dinfo.fi.controller;

import com.dinfo.fi.dto.TemplateAllContent;
import com.dinfo.fi.service.in.SemiTemplateService;
import com.dinfo.fi.service.in.TemplateService;
import com.dinfo.fi.utils.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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
@Api(description = "模板Controller",basePath = "/semiTemplate")
@RestController
@RequestMapping("/semiTemplate")
public class SemiTemplateController {

    @Autowired
    private SemiTemplateService semiTemplateService;
    /**
     * @return
     */
    @RequestMapping(value = "/getAllSemiTemplate",method = RequestMethod.GET)
    @ApiOperation(value="根据id查询模板", notes="")
    public Response getAllTemplate(){
        return semiTemplateService.queryAllTemplate();
    }

    @RequestMapping(value = "/getTemplateById",method = RequestMethod.GET)
    @ApiOperation(value="根据id查询模板", notes="")
    public Response getTemplateById(
            @ApiParam(value = "id")@RequestParam Integer id
    ){
        return semiTemplateService.querySemiTempById(id);
    }



}

