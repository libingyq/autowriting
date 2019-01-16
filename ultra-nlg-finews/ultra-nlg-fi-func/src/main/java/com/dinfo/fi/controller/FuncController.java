package com.dinfo.fi.controller;

import com.dinfo.fi.dto.Sentence;
import com.dinfo.fi.dto.SentenceElement;
import com.dinfo.fi.dto.TemplateAllContent;
import com.dinfo.fi.enums.FuncType;
import com.dinfo.fi.func.TriadModel;
import com.dinfo.fi.func.upper.UpperInter;
import com.dinfo.fi.service.in.ConsistSentenceService;
import com.dinfo.fi.service.in.TriadModelExpParseService;
import com.dinfo.fi.service.out.*;
import com.dinfo.fi.spy.FuncLocator;
import com.dinfo.fi.utils.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
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
@Api(description = "函数controller",basePath = "/app/func")
@RestController
@RequestMapping("/app/func")
public class FuncController {


    @Autowired
    private ConsistSentenceService consistSentenceService;
    @Autowired
    private TriadModelExpParseService triadModelExpParseService;
    @Autowired
    private TemplateService templateService;


    @RequestMapping(value = "/getAllFunc",method = RequestMethod.GET)
    @ApiOperation(value="获取全部的function", notes="")
    private Response<Map<FuncType, List<String>>> getAllFunc(){
        FuncLocator funcLocator = new FuncLocator();
        Map<FuncType, List<String>> allFuncNameByType = funcLocator.getAllFuncNameByType();
        return Response.ok(allFuncNameByType);
    }


    @RequestMapping(value = "/createContentByTemplate",method = RequestMethod.GET)
    @ApiOperation(value="根据template的id生成内容", notes="")
    public Response createContentByTemplate(
            @RequestParam @ApiParam(value = "模板id") Integer id
    ) throws ParseException {

        Response<TemplateAllContent> templateById = templateService.getTemplateById(id);

        if(templateById.isNotSuccess()){
            return Response.notOk("没有该模板");
        }

        TemplateAllContent data = templateById.getData();

        return this.createContentByTemplateAllContent(data);
    }




    @RequestMapping(value = "/createContentByTemplateAllContent",method = RequestMethod.POST)
    @ApiOperation(value="根据template生成内容", notes="")
    public Response createContentByTemplateAllContent(
            @RequestBody @ApiParam(value = "模板") TemplateAllContent templateAllContent
    ) throws ParseException {
        Date parse = new Date();


        StringBuffer content = new StringBuffer("   ");

        for (List<String> strings : templateAllContent.getParagraphs()) {

            HashMap<String, List<SentenceElement>> sentenceByTimeMap = new HashMap<>();

            for (String string : strings) {

                List<TriadModel> triadModels = triadModelExpParseService.parseTriadModelByExp(string);

                List<Map> collect = triadModels.stream().map(triadModel -> triadModel.run(parse)).collect(Collectors.toList());

                Sentence sentence = consistSentenceService.consistSentenceProxy(collect);

                List<SentenceElement> sentenceEle = sentence.getSentenceEle();

                String timeDesc = sentence.getTimeDesc();

                List<SentenceElement> sentenceElements = sentenceByTimeMap.get(timeDesc);

                if(CollectionUtils.isEmpty(sentenceElements)){
                    sentenceElements = new ArrayList<>();
                }

                sentenceElements.addAll(sentenceEle);

                sentenceByTimeMap.put(timeDesc,sentenceElements);

            }

            for (Map.Entry<String, List<SentenceElement>> stringListEntry : sentenceByTimeMap.entrySet()) {
                String key = stringListEntry.getKey();
                List<SentenceElement> value = stringListEntry.getValue();
                HashSet<SentenceElement> sentenceElements = new HashSet<>();
                sentenceElements.addAll(value);
                Sentence sentence = new Sentence(key, new ArrayList<>(sentenceElements));
                String s = consistSentenceService.changeSentenceToString(sentence);
                content.append(s);
            }


            content.append("\n   ");
        }

        return Response.ok(content.toString());
    }


}
