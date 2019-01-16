package com.dinfo.fi.service.in.Impl;

import com.dinfo.fi.dto.Sentence;
import com.dinfo.fi.dto.SentenceElement;
import com.dinfo.fi.enums.FuncType;
import com.dinfo.fi.enums.StockIndexConditionType;
import com.dinfo.fi.service.in.ConsistSentenceService;
import com.dinfo.fi.spy.FuncLocator;
import com.dinfo.fi.utils.Response;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.dinfo.fi.inter.base.FuncInterface.*;
import static com.dinfo.fi.inter.base.FuncInterface.FORMAT_KEY;
import static com.dinfo.fi.inter.base.FuncInterface.FUNC_TYPE_KEY;

/**
 * <p>Date:2018/10/19</p>
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
@Service
public class ConsistSentenceServiceImpl implements ConsistSentenceService {



    @Override
    public Sentence consistSentenceProxy(List<Map> maps) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");

        return consistSentenceProxy(maps,format);

    }

    @Override
    public String changeSentenceToString(Sentence sentence) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(sentence.getTimeDesc()).append("，");
        Map<String, List<SentenceElement>> listMap = sentence.getSentenceEle()
                .stream().sorted(Comparator.comparingInt(SentenceElement::getFuncTypeCode)).collect(Collectors.groupingBy(SentenceElement::getObject, Collectors.toList()));

        listMap.forEach((s, sentenceElements) -> {
            stringBuffer.append(sentenceElements.get(0).getObjectName()).append("(").append(s).append(")");

            List<SentenceElement> culResult = new ArrayList<>();

            try {
                List<SentenceElement> collect = sentenceElements.stream()
                        .filter(sentenceElement -> !sentenceElement.getFuncTypeCode().equals(FuncType.CulFunc.getId()))
                        .sorted(Comparator.comparingDouble(SentenceElement::getScore)).collect(Collectors.toList());

                if(collect.get(0).getScore() == 0.0){
                    culResult.add(collect.get(0));
                    culResult.add(collect.get(1));
                }else {
                    culResult.add(collect.get(0));
                }


            }catch (Exception e){

            }

            List<SentenceElement> collect = sentenceElements.stream()
                    .filter(sentenceElement ->
                            sentenceElement.getFuncTypeCode().equals(FuncType.CulFunc.getId())
                            || sentenceElement.getFuncTypeCode().equals(FuncType.SpecialFunc.getId())
                    )
                    .collect(Collectors.toList());
            culResult.addAll(collect);

            culResult.forEach(sentenceElement -> {

                stringBuffer.append(sentenceElement.getResult()).append("，");
            });
        });
        String substring = stringBuffer.substring(0, stringBuffer.length() - 1);

        return substring+"。";
    }

    @Override
    public Sentence consistSentenceProxy(List<Map> maps, SimpleDateFormat format) {


        maps.sort((Map o1, Map o2)-> {
                Double score = (Double) o1.get("JUDGE_SCORE");
                Double score1 = (Double) o2.get("JUDGE_SCORE");
                try {

                    return score.compareTo(score1);
                }catch (Exception e){
                    return 0;
                }
        });



        Map simpleMap = maps.get(0);

        Date date = (Date)simpleMap.get(DATA_KEY);
        String timeFuncName = simpleMap.get(TIME_FLG).toString();

        String timeDesc = format.format(date) + timeFuncName;

        Sentence oneSentence = new Sentence();
        oneSentence.setTimeDesc(timeDesc);

        ArrayList<SentenceElement> sentenceEles = new ArrayList<>();
        boolean hasTrend = false;
        for (Map map : maps) {




            SentenceElement sentenceEle = new SentenceElement();
            sentenceEle.setObjectName(map.get(DATA_NAME).toString());
            String thisFuncName = map.get(RESULT_FLG).toString();
            String code = map.get(CODE_KEY).toString();
            Double score = (Double)map.get(JUDGE_SCORE);

            String result = map.get(thisFuncName).toString();
            sentenceEle.setFuncName(thisFuncName);
            sentenceEle.setObject(code);
            sentenceEle.setScore(score);
            FuncType o = (FuncType)map.get(FUNC_TYPE_KEY);
            if(o.equals(FuncType.CulFunc)){
                double v = Double.parseDouble(result);
                if(v<0){
                    continue;
                }
                sentenceEle.setResult(map.get(FORMAT_KEY).toString().replace("{v}",String.valueOf(v)));
            }else if(o.equals(FuncType.SpecialFunc)){
                sentenceEle.setResult(result);
            }

            else if(o.equals(FuncType.JudgeFunc)){
                Boolean flg = Boolean.valueOf(result);

                if(score.equals(0.0)){
                    //说明此时涨停，跌停或者低走，高走
                    if(flg){
                        sentenceEle.setResult(String.format(map.get(FORMAT_KEY).toString(),thisFuncName));
                    }else {

                        continue;
                    }
                }else {
                    //排序后第一个不是0的就是我们要找的趋势
                    if(!hasTrend){
                        sentenceEle.setResult(String.format(map.get(FORMAT_KEY).toString(),thisFuncName));
                        hasTrend = true;
                    }else {
                        continue;
                    }
                }


            }
            sentenceEle.setFuncTypeCode(o.getId());
            sentenceEles.add(sentenceEle);

        }
        oneSentence.setSentenceEle(sentenceEles);

        return oneSentence;
    }
}
