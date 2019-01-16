package com.dinfo.fi.inter.base;


import com.dinfo.fi.anno.FuncAttr;
import com.dinfo.fi.enums.FuncType;

import java.util.Map;

/**
 * <p>Date:2018/9/20</p>
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
public interface FuncInterface {

    String DATA_KEY = "DATA_KEY";
    String DATA_NAME = "DATA_NAME";
    String CODE_KEY = "CODE_KEY";
    String DATA_TYPE_KEY = "DATA_TYPE_KEY";
    String FUNC_TYPE_KEY = "FUNC_TYPE_KEY";
    String FORMAT_KEY = "FORMAT_KEY";

    String TIME_FLG = "TIME_FLG";
    String RESULT_FLG = "RESULT_FLG";
    String JUDGE_SCORE = "JUDGE_SCORE";


    String getKey();

    Map run(Map input);

    default String formatTemplate(){
        FuncAttr annotation = this.getClass().getAnnotation(FuncAttr.class);
        return annotation.name();
    };

    default FuncType getFuncType(){
        FuncAttr annotation = this.getClass().getAnnotation(FuncAttr.class);
        return annotation.type();
    };

    default String getFuncName(){
        FuncAttr annotation = this.getClass().getAnnotation(FuncAttr.class);
        return annotation.name();
    }


    default <T>T getData(Map input){
        return (T)input.get(getKey());
    };





}
