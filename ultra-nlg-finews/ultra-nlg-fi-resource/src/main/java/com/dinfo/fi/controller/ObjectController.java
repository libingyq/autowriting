package com.dinfo.fi.controller;

import com.dinfo.fi.enums.DataType;
import com.dinfo.fi.service.in.ObjectService;
import com.dinfo.fi.utils.Response;
import com.dinfo.fi.utils.TwoTuple;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Date:2018/12/14</p>
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
@Api(description = "对象数据Controller",basePath = "/object")
@RestController
@RequestMapping("/object")
public class ObjectController {


    @Autowired
    private ObjectService objectService;

    @RequestMapping("getAllObjectMap")
    public Response<HashMap<DataType, List<TwoTuple>>> getAllObjectMap(){
        return objectService.getAllObjectSimpleMap();
    }

}
