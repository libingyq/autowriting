package com.dinfo.fi.controller;

import com.dinfo.fi.dto.TemplateAllContent;
import com.dinfo.fi.service.in.IndexDataService;
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
@Api(description = "模板Controller",basePath = "/template")
@RestController
@RequestMapping("/template")
public class TemplateController {

    @Autowired
    private TemplateService templateService;
    /**
     * @return
     */
    @RequestMapping(value = "/getTemplateById",method = RequestMethod.GET)
    @ApiOperation(value="根据id查询模板", notes="")
    public Response getTemplateById(
            @ApiParam(value = "id")@RequestParam Integer id
                                               ){
        return templateService.queryTemplateAllContents(id);
    }


    @RequestMapping(value = "/getAllTemplate",method = RequestMethod.GET)
    @ApiOperation(value="查询全部template", notes="")
    public Response getAllTemplate(){
        return templateService.queryAllTemplate();
    }


    @RequestMapping(value = "/addTemplate",method = RequestMethod.POST)
    @ApiOperation(value="增加模板", notes="")
    public Response addTemplate(
            @RequestParam(value = "userId") Integer userId,
            @RequestParam(value = "templateName") String templateName,
            @RequestParam(value = "paragraphs") ArrayList<List<String>> paragraphs
            ){
        TemplateAllContent templateAllContent = new TemplateAllContent();
        templateAllContent.setParagraphs(paragraphs);
        templateAllContent.setUserId(userId);
        templateAllContent.setTemplateName(templateName);
        return templateService.addTemplate(templateAllContent);
    }

}

