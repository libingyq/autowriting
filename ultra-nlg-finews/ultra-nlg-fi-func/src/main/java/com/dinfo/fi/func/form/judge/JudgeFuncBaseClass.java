package com.dinfo.fi.func.form.judge;

import com.alibaba.fastjson.JSON;
import com.dinfo.fi.anno.FuncAttr;
import com.dinfo.fi.dto.DTWCompareDto;
import com.dinfo.fi.entity.DataInfo;
import com.dinfo.fi.entity.DescribeInfo;
import com.dinfo.fi.enums.FuncType;
import com.dinfo.fi.inter.JudgeFuncInterface;
import com.dinfo.fi.service.out.DescriptionService;
import com.dinfo.fi.service.out.PyInterface;
import com.dinfo.fi.utils.Response;
import com.dinfo.fi.utils.TwoTuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * <p>Date:2018/10/31</p>
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
@Component
@FuncAttr(name = "",type = FuncType.JudgeFunc)
public class JudgeFuncBaseClass implements JudgeFuncInterface{

    @Autowired
    public PyInterface pyInterface;

    @Autowired
    public DescriptionService descriptionService;

    public TwoTuple<Boolean,Double> judge(List<DataInfo> dataInfos) {

        DTWCompareDto dtwCompare = getDTWCompare(dataInfos);
        long l = System.currentTimeMillis();
        String s = pyInterface.DTWCompareDemo(
                dtwCompare.getCode(),
                dtwCompare.getTableName(),
                dtwCompare.getStartTime(),
                dtwCompare.getEndTime(),
                dtwCompare.getTrend());
        long l1 = System.currentTimeMillis();
        System.out.println("匹配"+dtwCompare.getTrend()+"用时"+(l1-l));
        Boolean result = Boolean.valueOf(JSON.parseObject(s).get("status").toString());
        Double score = Double.valueOf(JSON.parseObject(s).get("average_score").toString());
        return new TwoTuple<>(result,score);
    }

    @Override
    public int getLevel() {
        return 1;
    }


    @Override
    public String formatTemplate() {

        String funcName = getFuncName();
        Response<List<DescribeInfo>> describeByTrend = descriptionService.getDescribeByTrend(funcName);
        List<DescribeInfo> data = describeByTrend.getData();
        List<String> collect = data.stream().map(describeInfo -> describeInfo.getDescribe()).collect(Collectors.toList());
        collect.add(funcName);
        Random random = new Random();
        int i = random.nextInt(collect.size());

        return collect.get(i);
    }
}
