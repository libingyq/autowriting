package com.dinfo.fi.controller;

import com.dinfo.fi.enums.DataType;
import com.dinfo.fi.enums.FuncType;
import com.dinfo.fi.service.FuncService;
import com.dinfo.fi.service.ObjectService;
import com.dinfo.fi.utils.Response;
import com.dinfo.fi.utils.TwoTuple;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

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
@Api(description = "资源Controller",basePath = "/web/res")
@RestController
@RequestMapping("/web/res")
public class ResController {
    @Autowired
    private ObjectService objectService;

    private static HashMap<DataType,List<TwoTuple<String,String>>> cacheMap;

    @RequestMapping(value = "/searchObj",method = {RequestMethod.POST,RequestMethod.GET})
    Response<List<String>> searchObj(String search,String type){

        if(cacheMap == null){
            Response<HashMap<DataType, List<TwoTuple<String, String>>>> allObjectMap = objectService.getAllObjectMap();
            cacheMap = allObjectMap.getData();
        }
        DataType enumByName = DataType.getEnumByName(type);
        List<TwoTuple<String, String>> twoTuples = cacheMap.get(enumByName);
        List<TwoTuple<String, String>> filterList = twoTuples.stream().filter(f -> f.second.contains(search) | f.first.contains(search)).collect(Collectors.toList());


        return Response.ok(filterList);
    };
}
