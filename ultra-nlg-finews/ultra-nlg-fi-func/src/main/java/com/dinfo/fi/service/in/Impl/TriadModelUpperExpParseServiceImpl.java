package com.dinfo.fi.service.in.Impl;

import com.dinfo.fi.SpringUtil;
import com.dinfo.fi.enums.FuncType;
import com.dinfo.fi.exception.FuncException;
import com.dinfo.fi.func.TriadModel;
import com.dinfo.fi.func.object.GrabData;
import com.dinfo.fi.func.upper.UpperInter;
import com.dinfo.fi.inter.CulFuncInterface;
import com.dinfo.fi.inter.JudgeFuncInterface;
import com.dinfo.fi.inter.SpecialFuncInterface;
import com.dinfo.fi.inter.TimeFuncInterface;
import com.dinfo.fi.inter.base.FuncInterface;
import com.dinfo.fi.service.in.TriadModelExpParseService;
import com.dinfo.fi.spy.FuncLocator;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @auther rongzihao
 * @date 2018/10/18 17:33
 */
@Service
public class TriadModelUpperExpParseServiceImpl implements TriadModelExpParseService {


    @Override
    public List<TriadModel> parseTriadModelByExp(String sentenceExp) {
//        String sentenceExp = "<{早盘}+[{index:399106},{stock:603978}]+[{高开高走},{涨幅},{跌幅}]>";
        String sentenceExpReduce = delTag(sentenceExp);
        List<TriadModel> modelList = new ArrayList<>();
        FuncLocator funcLocator = new FuncLocator();
        Map<String, UpperInter> allUpperFuncByType = funcLocator.getAllUpperFuncByType();
        Map<FuncType, Map<String, FuncInterface>> allFuncByType = funcLocator.getAllFuncByType();

        for (UpperInter upperInter : allUpperFuncByType.values()) {
            sentenceExpReduce = upperInter.reduce(sentenceExpReduce);
        }


        try{
            String[] array = sentenceExpReduce.split("\\+");
            //时间概念
            String keyTime = array[0];
            TimeFuncInterface TF = (TimeFuncInterface)allFuncByType.get(FuncType.TimeFunc).get(keyTime);
            //对象概念
            String[] arrayObj = array[1].split(",");
            //趋势概念
            String[] arrayRate = array[2].split(",");
            Map<String, FuncInterface> culMap = allFuncByType.get(FuncType.CulFunc);
            Map<String, FuncInterface> judgeMap = allFuncByType.get(FuncType.JudgeFunc);
            Map<String, FuncInterface> specialMap = allFuncByType.get(FuncType.SpecialFunc);
            List<String> culNameSet = funcLocator.getAllFuncNameByType().get(FuncType.CulFunc);
            List<String> judgeNameSet = funcLocator.getAllFuncNameByType().get(FuncType.JudgeFunc);
            List<String> specialNameSet = funcLocator.getAllFuncNameByType().get(FuncType.SpecialFunc);
            for(int i=0;i< arrayObj.length;i++){
                for(int j=0;j<arrayRate.length;j++){
                    String oriCodeExp = arrayObj[i];
                    String rateKey = arrayRate[j];
                    //计算函数,判断函数
                    TriadModel model;
                    if(culNameSet.contains(rateKey)){
                        CulFuncInterface CU = (CulFuncInterface)culMap.get(rateKey);
                        model = new TriadModel(TF,SpringUtil.getBean(GrabData.class),CU,oriCodeExp);
                    }else if(specialNameSet.contains(rateKey)){
                        SpecialFuncInterface SP = (SpecialFuncInterface)specialMap.get(rateKey);
                        model = new TriadModel(TF,SpringUtil.getBean(GrabData.class),SP,oriCodeExp);
                    }
                    else{
                        JudgeFuncInterface JF = (JudgeFuncInterface)judgeMap.get(rateKey);
                        model = new TriadModel(TF,SpringUtil.getBean(GrabData.class),JF,oriCodeExp);
                    }
                    modelList.add(model);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new FuncException("模板解析失败");
        }
        return modelList;
    }


    /**
     * 删除特殊标签
     * @param str
     * @return
     */
    public static String delTag(String str) {
        String regEx_a = "<";
        String regEx_b = ">";
        String regEx_c = "\\{";
        String regEx_n = "}";
        String regEx_m = "\\[";
        String regEx_d = "]";

        Pattern p_script = Pattern.compile(regEx_a, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(str);
        str = m_script.replaceAll("");

        Pattern p_style = Pattern.compile(regEx_b, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(str);
        str = m_style.replaceAll("");

        Pattern p_html = Pattern.compile(regEx_c, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(str);
        str = m_html.replaceAll("");

        Pattern p_n = Pattern.compile(regEx_n, Pattern.CASE_INSENSITIVE);
        Matcher m_n = p_n.matcher(str);
        str = m_n.replaceAll("");

        Pattern p_m = Pattern.compile(regEx_m, Pattern.CASE_INSENSITIVE);
        Matcher m_m = p_m.matcher(str);
        str = m_m.replaceAll("");

        Pattern p_d = Pattern.compile(regEx_d, Pattern.CASE_INSENSITIVE);
        Matcher m_d = p_d.matcher(str);
        str = m_d.replaceAll("");

        return str.trim();
    }
}
