package com.dinfo.fi.service;

import com.dinfo.fi.dto.TemplateAllContent;
import com.dinfo.fi.enums.FuncType;
import com.dinfo.fi.utils.Response;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
@FeignClient(value = "ultra-nlg-fi-func")
@RequestMapping(value = "/app/func")
public interface FuncService {

    @RequestMapping(value = "/getAllFunc",method = RequestMethod.GET)
    Response<Map<FuncType, List<String>>> getAllFunc();


    @RequestMapping(value = "/createContentByTemplate",method = RequestMethod.GET)
    Response createContentByTemplate(
            @RequestParam(value = "id") Integer id
    );

    @RequestMapping(value = "/createContentByTemplateAllContent",method = RequestMethod.POST,consumes = "application/json")
    Response createContentByTemplateAllContent(
            @RequestBody TemplateAllContent templateAllContent
    );

}
