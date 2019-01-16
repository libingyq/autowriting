package com.dinfo.fi.controller;

import com.dinfo.fi.enums.FuncType;
import com.dinfo.fi.service.FuncService;
import com.dinfo.fi.utils.Response;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <p>Date:2018/12/13</p>
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
@Api(description = "模板Controller",basePath = "/web/func")
@RestController
@RequestMapping("/web/func")
public class FuncController {
    @Autowired
    private FuncService funcService;

    @RequestMapping(value = "/getAllFuncByType",method = RequestMethod.GET)
    Response<Map<FuncType, List<String>>> getAllFunc(){
        return funcService.getAllFunc();
    };
}
