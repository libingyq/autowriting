package com.dinfo.fi.service;

import com.dinfo.fi.dto.TemplateAllContent;
import com.dinfo.fi.entity.Template;
import com.dinfo.fi.enums.DataType;
import com.dinfo.fi.utils.Response;
import com.dinfo.fi.utils.TwoTuple;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Date:2018/10/22</p>
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
@FeignClient(value = "ultra-nlg-fi-resource")
@RequestMapping(value = "/object")
public interface ObjectService {


    @RequestMapping("getAllObjectMap")
    Response<HashMap<DataType,List<TwoTuple<String,String>>>> getAllObjectMap();


}
