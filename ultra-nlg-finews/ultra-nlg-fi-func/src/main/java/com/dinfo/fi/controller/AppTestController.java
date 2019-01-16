package com.dinfo.fi.controller;

import com.dinfo.fi.SpringUtil;
import com.dinfo.fi.common.CommonConst;
import com.dinfo.fi.dto.Sentence;
import com.dinfo.fi.dto.SentenceElement;
import com.dinfo.fi.dto.TemplateAllContent;
import com.dinfo.fi.entity.StockInfo;
import com.dinfo.fi.enums.FuncType;
import com.dinfo.fi.func.TriadModel;
import com.dinfo.fi.func.form.cul.UpRate;
import com.dinfo.fi.func.form.judge.JudgeFuncBaseClass;
import com.dinfo.fi.func.object.GrabData;
import com.dinfo.fi.func.time.MorningSession;
import com.dinfo.fi.func.upper.UpperInter;
import com.dinfo.fi.inter.base.FuncInterface;
import com.dinfo.fi.service.in.ConsistSentenceService;
import com.dinfo.fi.service.in.TriadModelExpParseService;
import com.dinfo.fi.service.out.*;
import com.dinfo.fi.spy.FuncLocator;
import com.dinfo.fi.utils.Response;
import com.dinfo.fi.utils.TwoTuple;
import com.google.common.collect.Maps;
import com.netflix.discovery.converters.Auto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.dinfo.fi.inter.base.FuncInterface.*;

/**
 * <p>Date:2017/12/28</p>
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
@Api(description = "测试用Controller",basePath = "/app/test")
@RestController
@RequestMapping("/app/test")
public class AppTestController {

    @Autowired
    private PyInterface pyInterface;
    @Autowired
    private StockDataService stockDataService;
    @Autowired
    private StockInfoService stockInfoService;
    @Autowired
    private IndexDataService indexDataService;
    @Autowired
    private ConsistSentenceService consistSentenceService;
    @Autowired
    private TriadModelExpParseService triadModelExpParseService;
    @Autowired
    private TemplateService templateService;
    /**
     * @return
     */
    @RequestMapping(value = "/testPyInter",method = RequestMethod.GET)
    @ApiOperation(value="测试python接口调用(指数)", notes="")
    public Response  testPyInter(){
        return Response.ok(pyInterface.updateIndeces());
    }

    @RequestMapping(value = "/testFunc",method = RequestMethod.GET)
    @ApiOperation(value="2018-10-17 " +
            "<{早盘}+[{index:399001}]+[{低开低走},{跌幅}]>+" +
            "<{早盘}+[{stock:603977}]+[{报点},{跌幅}]>", notes="")
    public Response testFunc(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        Calendar instance = Calendar.getInstance();
        instance.set(2018,9,17);
        Date time = instance.getTime();
        System.out.println(format.format(time));



        List<TriadModel> triadModels = triadModelExpParseService.parseTriadModelByExp("<{早盘}+[{index:399001}]+[{低开低走},{跌幅}]>");
        //因为时间都是一致的

        List<Map> collect = triadModels.stream().map(triadModel -> triadModel.run(time)).collect(Collectors.toList());

        Sentence sentence = consistSentenceService.consistSentenceProxy(collect);

        String s = consistSentenceService.changeSentenceToString(sentence);


        return Response.ok(s);
    }




    @RequestMapping(value = "/createContentByTransferTem",method = RequestMethod.GET)
    @ApiOperation(value="根据输入的三元组和时间生成内容，eg:<{早盘}+[{index:399106}]+[{高开高走},{涨幅},{跌幅}]>", notes="")
    public Response createContentByTransferTem(
            @RequestParam @ApiParam(value = "yyyy年MM月dd日") String baseTime,
            @RequestParam @ApiParam(value = "模板") String tem
    ) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        Date parse = format.parse(baseTime);
        System.out.println(baseTime);

        FuncLocator funcLocator = new FuncLocator();
        Map<String, UpperInter> allUpperFuncByType = funcLocator.getAllUpperFuncByType();
        for (UpperInter upperInter : allUpperFuncByType.values()) {
            tem = upperInter.reduce(tem);
        }

        List<TriadModel> triadModels = triadModelExpParseService.parseTriadModelByExp(tem);

        List<Map> collect = triadModels.stream().map(triadModel -> triadModel.run(parse)).collect(Collectors.toList());

        Sentence sentence = consistSentenceService.consistSentenceProxy(collect);

        StringBuffer stringBuffer = new StringBuffer();

        SentenceElement obj = sentence.getSentenceEle().get(0);
        stringBuffer.append("快讯:").append(obj.getObjectName())
                .append(obj.getFuncName());

        String s = consistSentenceService.changeSentenceToString(sentence);

        return Response.ok(new TwoTuple<>(stringBuffer,s));
    }



    @RequestMapping(value = "/createContentByObject",method = RequestMethod.GET)
    @ApiOperation(value="传入股票名称或代码，时间函数是今天截止发稿，默认全函数", notes="")
    public Response createContentByObject(
            @RequestParam @ApiParam(value = "对象函数") String object
    ) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        Date parse = new Date();
        Calendar instance = Calendar.getInstance();

        int hour = instance.get(Calendar.HOUR_OF_DAY);
        int min = instance.get(Calendar.MINUTE);

        FuncLocator funcLocator = new FuncLocator();
        Map<FuncType, List<String>> allFuncNameByType = funcLocator.getAllFuncNameByType();
        List<String> judge = allFuncNameByType.get(FuncType.JudgeFunc);
        List<String> cul = allFuncNameByType.get(FuncType.CulFunc);
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[");
        for (String s : judge) {
            stringBuffer.append(s).append(",");
        }
        for (String s : cul) {
            stringBuffer.append(s).append(",");
        }
        String s = stringBuffer.toString();
        s = s.substring(0, s.length() - 1);

        if(hour < 9){
            return Response.notOk("未开盘");
        }

        if(hour >= 9 && hour < 13){
            String contentByTuple = getContentByTuple("<{截止发稿}+[{" + object + "}]+"+s+"]>", parse);
            return Response.ok(contentByTuple);
        }else if(hour >= 13 && hour < 15){
            String contentByTupleMS = getContentByTuple("<{早盘}+[{" + object + "}]+"+s+"]>", parse);
            String contentByTupleUN = getContentByTuple("<{截止发稿}+[{" + object + "}]+"+s+"]>", parse);
            contentByTupleUN = contentByTupleUN.substring(contentByTupleUN.indexOf("日")+1);
            return Response.ok(contentByTupleMS + contentByTupleUN);

        }else if(hour >=15){
            String contentByTupleMS = getContentByTuple("<{早盘}+[{" + object + "}]+"+s+"]>", parse);
            String contentByTupleNO = getContentByTuple("<{午盘}+[{" + object + "}]+"+s+"]>", parse);
            contentByTupleNO = contentByTupleNO.substring(contentByTupleNO.indexOf("日")+1);
            String replace = s.replace("低开,", "");
            replace = replace.replace("高开,","");
            String contentByTupleUN = getContentByTuple("<{当天}+[{" + object + "}]+"+replace+"]>", parse);
            contentByTupleUN = contentByTupleUN.substring(contentByTupleUN.indexOf("日")+1);
            return Response.ok(contentByTupleMS+contentByTupleNO+contentByTupleUN);

        }


        return Response.notOk("未开盘");
    }



    @RequestMapping(value = "/createContentByObjectAndTime",method = RequestMethod.GET)
    @ApiOperation(value="传入股票代码，时间函数是今天截止发稿，默认全函数", notes="")
    public Response createContentByObjectAndTime(
            @RequestParam @ApiParam(value = "yyyy年MM月dd日") String baseTime,
            @RequestParam @ApiParam(value = "对象函数") String object
    ) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        Date parse = format.parse(baseTime);
        Calendar instance = Calendar.getInstance();

        int hour = instance.get(Calendar.HOUR_OF_DAY);
        int min = instance.get(Calendar.MINUTE);

        FuncLocator funcLocator = new FuncLocator();
        Map<FuncType, List<String>> allFuncNameByType = funcLocator.getAllFuncNameByType();
        List<String> judge = allFuncNameByType.get(FuncType.JudgeFunc);
        List<String> cul = allFuncNameByType.get(FuncType.CulFunc);
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[");
        for (String s : judge) {
            stringBuffer.append(s).append(",");
        }
        for (String s : cul) {
            stringBuffer.append(s).append(",");
        }
        String s = stringBuffer.toString();
        s = s.substring(0, s.length() - 1);

        if(hour < 9){
            return Response.notOk("未开盘");
        }
        if(hour >= 9 && hour < 13){
            String tem = "<{截止发稿}+[{" + object + "}]+" + s + "]>";
            TwoTuple<String, String> contentAndTitleByTuple = getContentAndTitleByTuple(tem, parse);
            return Response.ok(contentAndTitleByTuple);
        }else if(hour >= 13 && hour < 15){
            String contentByTupleMS = getContentByTuple("<{早盘}+[{" + object + "}]+"+s+"]>", parse);
            String replace = s.replace("低开,", "");
            replace = replace.replace("高开,","");
            String tem = "<{截止发稿}+[{" + object + "}]+" + replace + "]>";
            TwoTuple<String, String> contentAndTitleByTuple = getContentAndTitleByTuple(tem, parse);
            String content = contentAndTitleByTuple.second.substring(contentAndTitleByTuple.second.indexOf("日")+1);
            return Response.ok(new TwoTuple<>(contentAndTitleByTuple.first,contentByTupleMS + content));

        }else if(hour >=15){
            String contentByTupleMS = getContentByTuple("<{早盘}+[{" + object + "}]+[全部计算]>", parse);
            String replace = s.replace("低开,", "");
            replace = replace.replace("高开,","");
            String contentByTupleNO = getContentByTuple("<{午盘}+[{" + object + "}]+[全部计算]>", parse);
            contentByTupleNO = contentByTupleNO.substring(contentByTupleNO.indexOf("日")+1);
            String tem = "<{当天}+[{" + object + "}]+[全部计算,全部判断]>";
            TwoTuple<String, String> contentAndTitleByTuple = getContentAndTitleByTuple(tem, parse);
            String content = contentAndTitleByTuple.second.substring(contentAndTitleByTuple.second.indexOf("日")+1);
            return Response.ok(new TwoTuple<>(contentAndTitleByTuple.first,contentByTupleMS+contentByTupleNO+content));

        }


        return Response.notOk("未开盘");
    }

    @RequestMapping(value = "/DIVTemp",method = RequestMethod.GET)
    @ApiOperation(value="传入股票代码，时间函数是今天截止发稿，默认全函数", notes="")
    public Response DIVTemp(
            @RequestParam @ApiParam(value = "yyyy年MM月dd日") String baseTime,
            @RequestParam @ApiParam(value = "对象函数") String object
    ) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        Date parse = format.parse(baseTime);
        Calendar instance = Calendar.getInstance();

        int hour = instance.get(Calendar.HOUR_OF_DAY);
        int min = instance.get(Calendar.MINUTE);

        FuncLocator funcLocator = new FuncLocator();
        Map<FuncType, List<String>> allFuncNameByType = funcLocator.getAllFuncNameByType();
        List<String> judge = allFuncNameByType.get(FuncType.JudgeFunc);
        List<String> cul = allFuncNameByType.get(FuncType.CulFunc);

        if(hour >= 9 && hour < 13){
            List<TriadModel> triadModels = triadModelExpParseService.parseTriadModelByExp("");
        }else if(hour >= 13 && hour < 15){

        }else if(hour >=15){

        }


        return Response.notOk("未开盘");
    }

    private String getAllPureTrend(){
        Map<String, JudgeFuncBaseClass> beansOfType = SpringUtil.getApplicationContext().getBeansOfType(JudgeFuncBaseClass.class);
        HashMap<String, JudgeFuncBaseClass> map = Maps.newHashMap();
        StringBuffer stringBuffer = new StringBuffer();
        beansOfType.forEach((k,v)->{
            if(StringUtils.isNotBlank(v.getFuncName())){
                stringBuffer.append(v.getFuncName()).append(",");
            }
        });
        return stringBuffer.toString();
    }

    private String getContentByTuple(String tem,Date parse){


        Sentence sentence = getSentenceByTuple(tem, parse);

        String content = consistSentenceService.changeSentenceToString(sentence);
        return content;
    }

    private TwoTuple<String,String> getContentAndTitleByTuple(String tem,Date parse){

        Sentence sentence = getSentenceByTuple(tem, parse);

        String content = consistSentenceService.changeSentenceToString(sentence);

        SentenceElement sentenceElement = sentence.getSentenceEle().get(0);
        
        String title = "快讯："+sentenceElement.getObjectName()+sentenceElement.getFuncName();

        return new TwoTuple<>(title,content);
    }

    private Sentence getSentenceByTuple(String tem,Date parse){

        Map<String, UpperInter> allUpperFuncByType = new FuncLocator().getAllUpperFuncByType();
        for (UpperInter upperInter : allUpperFuncByType.values()) {
            tem = upperInter.reduce(tem);
        }

        List<TriadModel> triadModels = triadModelExpParseService.parseTriadModelByExp(tem);

        List<Map> collect = triadModels.stream().map(triadModel -> triadModel.run(parse)).collect(Collectors.toList());

        Sentence sentence = consistSentenceService.consistSentenceProxy(collect);

        return sentence;
    }



    @RequestMapping(value = "/createContentByTemplate",method = RequestMethod.GET)
    @ApiOperation(value="根据template的id生成内容", notes="")
    public Response createContentByTemplate(
            @RequestParam @ApiParam(value = "yyyy年MM月dd日") String baseTime,
            @RequestParam @ApiParam(value = "模板id") Integer id
                                            ) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        Date parse = format.parse(baseTime);
        System.out.println(baseTime);

        Response<TemplateAllContent> templateById = templateService.getTemplateById(id);

        if(templateById.isNotSuccess()){
            return Response.notOk("没有该模板");
        }

        TemplateAllContent data = templateById.getData();

        StringBuffer content = new StringBuffer();

        for (List<String> strings : data.getParagraphs()) {

            content.append("  ");

            for (String string : strings) {

                List<TriadModel> triadModels = triadModelExpParseService.parseTriadModelByExp(string);

                List<Map> collect = triadModels.stream().map(triadModel -> triadModel.run(parse)).collect(Collectors.toList());

                Sentence sentence = consistSentenceService.consistSentenceProxy(collect);

                String s = consistSentenceService.changeSentenceToString(sentence);

                content.append(s);
            }

            content.append("\n");
        }



        return Response.ok(content.toString());
    }











    @RequestMapping(value = "/testFuncWithDataAndCode",method = RequestMethod.GET)
    @ApiOperation(value="2018-10-17 早盘+399001(深证成指)+[低开高走+涨幅]", notes="")
    public Response testFuncWithDataAndCode(@RequestParam String codeExp,
                                            @RequestParam String baseTime){
        TriadModel triadModel = new TriadModel(SpringUtil.getBean(MorningSession.class),
                SpringUtil.getBean(GrabData.class), SpringUtil.getBean(UpRate.class), codeExp);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Map run = null;
        try {
            run = triadModel.run(format.parse(baseTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Response.ok(run);
    }

    /**
     * @return
     */
    @RequestMapping(value = "/testDTW",method = RequestMethod.GET)
    @ApiOperation(value="测试python接口调用(DTW)", notes="")
    public Response  testDTW(){
        return Response.ok(
                pyInterface.DTWCompareDemo("BK0642","concept_data",
                                "2018-12-12 09:30:19",
                                "2018-12-19 10:25:05",
                                "震荡"));
    }


    /**
     * @return
     */
    @RequestMapping(value = "/testPyInterMore",method = RequestMethod.GET)
    @ApiOperation(value="测试python接口调用（股票数据）", notes="")
    public Response  testPyInterMore(){
        return Response.ok(pyInterface.updatetickers());
    }



     /**
     * @return
     */
    @RequestMapping(value = "/testReadFunc",method = RequestMethod.GET)
    @ApiOperation(value="查看有哪些函数", notes="")
    public Response  testReadFunc(){
        FuncLocator funcLocator = new FuncLocator();
        Map<String, FuncInterface> map = funcLocator.getMap();
        return Response.ok(map);
    }


    /**
     * @return
     */
    @RequestMapping(value = "/testReadFuncByType",method = RequestMethod.GET)
    @ApiOperation(value="查看有哪些函数,根据类别", notes="")
    public Response  testReadFuncByType(){
        FuncLocator funcLocator = new FuncLocator();
        Map<FuncType, Map<String, FuncInterface>> allFuncByType = funcLocator.getAllFuncByType();


        return Response.ok(allFuncByType);
    }






}
