package com.dinfo.fi.controller;

import com.dinfo.fi.dto.TemplateAllContent;
import com.dinfo.fi.service.FuncService;
import com.dinfo.fi.service.TemplateService;
import com.dinfo.fi.utils.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
@Api(description = "模板Controller",basePath = "/web/template")
@RestController
@RequestMapping("/web/template/")
public class TemplateController {
    @Autowired
    private TemplateService templateService;
    @Autowired
    private FuncService funcService;

    @RequestMapping(value = "/buildNewsByObjsAndSemiTem",method = RequestMethod.POST)
    @ApiOperation(value="根据半成品模板和对象列生成", notes="")
    public Response buildNewsByObjsAndSemiTem(
            HttpServletRequest request,
            @ApiParam(value = "objs")@RequestParam String objs
    ){
        String template = request.getSession().getAttribute("template").toString();
        String name = request.getSession().getAttribute("name").toString();
        String[] split = template.split("\\+");
        String times = split[0];
        String funcs = split[2];
        if(times.contains("&")){
            times = times.substring(1,times.length()-1);
        }
        if(funcs.contains("&")){
            funcs = funcs.substring(1,funcs.length()-1);
        }
        String[] objEles = objs.split(",");

        TemplateAllContent templateAllContent = new TemplateAllContent();

        templateAllContent.setTemplateName(name);

        ArrayList<List<String>> paras = new ArrayList<>();
        Map<String, List<String>> collect = Arrays.stream(objEles).collect(Collectors.groupingBy(s -> s.split(":")[0]));
        for (Map.Entry<String, List<String>> stringListEntry : collect.entrySet()) {
            HashSet<String> strings = new HashSet<>();
            String[] timeEles = times.split("&");
            String[] funcEles = funcs.split("&");
            List<String> value = stringListEntry.getValue();


            for (String timeEle : timeEles) {


                for (String s : value) {
                    for (String funcEle : funcEles) {
                        strings.add(timeEle+"+"+s+"+"+funcEle);
                    }
                    if(s.startsWith("概念") || s.startsWith("行业")){
                        strings.add(timeEle+"+"+s+"+"+"关注领涨");
                        strings.add(timeEle+"+"+s+"+"+"关注领跌");
                    }
                }

            }
            paras.add(new ArrayList<>(strings));

        }

        templateAllContent.setParagraphs(paras);

        Response contentByTemplateAllContent = funcService.createContentByTemplateAllContent(templateAllContent);

        String data = (String)contentByTemplateAllContent.getData();
        String[] splitPara = data.split("\n");
        ArrayList<String> strings = new ArrayList<>();
        for (String s : splitPara) {
            if(!StringUtils.isBlank(s)){
                strings.add(s.substring(s.indexOf("日")+1));
            }
        }


        return Response.ok(strings);
    }




    @RequestMapping(value = "/getTemplateById",method = RequestMethod.GET)
    @ApiOperation(value="根据id查询模板", notes="")
    public Response getTemplateById(
            @ApiParam(value = "id")@RequestParam Integer id
    ){
        return templateService.getTemplateById(id);
    }


    @RequestMapping(value = "/getAllTemplate",method = RequestMethod.GET)
    @ApiOperation(value="查询全部template", notes="")
    public Response getAllTemplate(){
        return templateService.getAllTemplate();
    }





    @RequestMapping(value = "/addTemplate",method = RequestMethod.POST)
    @ApiOperation(value="增加模板", notes="")
    public Response addTemplate(String templateName,@RequestParam String paragraphs){

        Integer userId = 1;
        ArrayList<List<String>> lists = new ArrayList<>();
        String[] paras = paragraphs.split("#");
        for (String para : paras) {
            ArrayList<String> par = new ArrayList<>();
            String[] sentence = para.split("-");
            for (String s : sentence) {
                if(!StringUtils.isBlank(s)){
                    par.add(s);
                }
            }
            lists.add(par);
        }

        return templateService.addTemplate(userId,templateName,lists);
    }

}
