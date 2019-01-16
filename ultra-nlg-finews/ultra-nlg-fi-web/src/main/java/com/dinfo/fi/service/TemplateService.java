package com.dinfo.fi.service;

import com.dinfo.fi.dto.TemplateAllContent;
import com.dinfo.fi.entity.Template;
import com.dinfo.fi.utils.Response;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

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
@RequestMapping(value = "/template")
public interface TemplateService {


    @RequestMapping(value = "/getTemplateById",method = RequestMethod.GET)
    Response<TemplateAllContent> getTemplateById(
            @RequestParam(value = "id") Integer id);


    @RequestMapping(value = "/getAllTemplate",method = RequestMethod.GET)
    Response<List<Template>> getAllTemplate();


//    @RequestMapping(value = "/addTemplate",method = RequestMethod.POST)
//    Response<Integer> addTemplate(
//            @RequestParam(value = "templateAllContent") TemplateAllContent templateAllContent
//    );


    @RequestMapping(value = "/addTemplate",method = RequestMethod.POST)
    Response addTemplate(@RequestParam(value = "userId") Integer userId, @RequestParam(value = "templateName") String templateName, @RequestParam(value = "paragraphs") ArrayList<List<String>> paragraphs);


}
