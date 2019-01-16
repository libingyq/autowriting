package com.dinfo.fi.spy;

/**
 * <p>Date:2018/9/27</p>
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
import com.dinfo.fi.SpringUtil;
import com.dinfo.fi.enums.FuncType;
import com.dinfo.fi.func.form.judge.JudgeFuncBaseClass;
import com.dinfo.fi.func.upper.UpperInter;
import com.dinfo.fi.inter.base.FuncInterface;
import com.google.common.collect.Collections2;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ApplicationObjectSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FuncLocator extends ApplicationObjectSupport implements ApplicationContextAware {
    /**
     * 用于保存接口实现类名及对应的类
     */
    private Map<String, FuncInterface> map;

    public Map<String, FuncInterface> getMap() {
        ApplicationContext applicationContext = SpringUtil.getApplicationContext();
        Map<String, FuncInterface> map = applicationContext.getBeansOfType(FuncInterface.class);
        HashMap<String, FuncInterface> result = Maps.newHashMap();
        map.forEach((k,v)->{
            if(StringUtils.isNotBlank(v.getFuncName())){
                result.put(k,v);
            }
        });
        return result;
    }

    public Map<FuncType,Map<String,FuncInterface>> getAllFuncByType(){
        Map<String, FuncInterface> map = getMap();
        HashMap<FuncType, Map<String, FuncInterface>> result = new HashMap<>();
        for (Map.Entry<String, FuncInterface> stringFuncInterfaceEntry : map.entrySet()) {
            FuncInterface value = stringFuncInterfaceEntry.getValue();
            FuncType funcType = null;
            try {
                funcType = value.getFuncType();
            }catch (Exception e){
                continue;
            }
            Map<String, FuncInterface> partFunc = result.get(funcType);
            if(partFunc == null){
                partFunc = new HashMap<>();
            }
            partFunc.put(value.getFuncName(),value);
            result.put(funcType,partFunc);
        }
        return result;
    }


    public Map<String, UpperInter> getAllUpperFuncByType(){
        ApplicationContext applicationContext = SpringUtil.getApplicationContext();
        Map<String, UpperInter> map = applicationContext.getBeansOfType(UpperInter.class);
        HashMap<String, UpperInter> resultMap = new HashMap<>();
        for (UpperInter upperInter : map.values()) {
            resultMap.put(upperInter.getFuncName(),upperInter);
        }
        return resultMap;
    }


    public Map<FuncType,List<String>> getAllFuncNameByType(){
        Map<String, FuncInterface> map = getMap();
        HashMap<FuncType, List<String>> result = new HashMap<>();
        map.forEach((k,v)->{
                FuncType funcType = v.getFuncType();
                List<String> strings = result.get(funcType);
                if(CollectionUtils.isEmpty(strings)) {
                    strings = new ArrayList<>();
                }
                strings.add(v.getFuncName());
                result.put(funcType,strings);

        });
        List<UpperInter> allFuncGroup = getAllFuncGroup();
        List<String> collect = allFuncGroup.stream().map(u -> u.getFuncName()).collect(Collectors.toList());
        result.put(FuncType.UpperFunc,collect);
        return result;
    }


    public List<UpperInter> getAllFuncGroup(){
        ApplicationContext applicationContext = SpringUtil.getApplicationContext();
        Map<String, UpperInter> map = applicationContext.getBeansOfType(UpperInter.class);

        return new ArrayList<>(map.values());
    }
}
