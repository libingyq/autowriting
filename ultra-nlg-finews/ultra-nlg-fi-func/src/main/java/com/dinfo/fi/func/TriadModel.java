package com.dinfo.fi.func;

import com.dinfo.fi.enums.FuncType;
import com.dinfo.fi.enums.StockIndexConditionType;
import com.dinfo.fi.inter.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.dinfo.fi.inter.base.FuncInterface.*;

/**
 * <p>Date:2018/10/17</p>
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
@Data
@Slf4j
@ToString
public class TriadModel implements Serializable{

    private TimeFuncInterface timeFuncInterface;

    private ObjectFuncInterface objectFuncInterface;

    private CulFuncInterface culFuncInterface;

    private JudgeFuncInterface judgeFuncInterface;

    private SpecialFuncInterface specialFuncInterface;

    private String oriCodeExp;

//    private boolean isJudge;

    private FuncType funcType;

    private Double score;

    public TriadModel(TimeFuncInterface timeFuncInterface, ObjectFuncInterface objectFuncInterface, CulFuncInterface culFuncInterface, String oriCodeExp) {
        this.timeFuncInterface = timeFuncInterface;
        this.objectFuncInterface = objectFuncInterface;
        this.culFuncInterface = culFuncInterface;
        this.oriCodeExp = oriCodeExp;
        this.funcType = FuncType.CulFunc;
    }

    public TriadModel(TimeFuncInterface timeFuncInterface, ObjectFuncInterface objectFuncInterface, JudgeFuncInterface judgeFuncInterface, String oriCodeExp) {
        this.timeFuncInterface = timeFuncInterface;
        this.objectFuncInterface = objectFuncInterface;
        this.judgeFuncInterface = judgeFuncInterface;
        this.oriCodeExp = oriCodeExp;
        this.funcType = FuncType.JudgeFunc;
    }

    public TriadModel(TimeFuncInterface timeFuncInterface, ObjectFuncInterface objectFuncInterface, SpecialFuncInterface specialFuncInterface, String oriCodeExp) {
        this.timeFuncInterface = timeFuncInterface;
        this.objectFuncInterface = objectFuncInterface;
        this.specialFuncInterface = specialFuncInterface;
        this.oriCodeExp = oriCodeExp;
        this.funcType = FuncType.SpecialFunc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        TriadModel that = (TriadModel) o;

        return oriCodeExp != null ? oriCodeExp.equals(that.oriCodeExp) : that.oriCodeExp == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (oriCodeExp != null ? oriCodeExp.hashCode() : 0);
        return result;
    }

    public Map run(Date baseTime){
        HashMap<String, Object> input = new HashMap<>();
        input.put(DATA_KEY,baseTime);
        String[] split = oriCodeExp.split(":");
        StockIndexConditionType enumByLabel = StockIndexConditionType.getEnumByName(split[0]);
        input.put(CODE_KEY,split[1]);
        input.put(DATA_TYPE_KEY,enumByLabel);
        timeFuncInterface.run(input);
        objectFuncInterface.run(input);
        if(funcType.equals(FuncType.JudgeFunc)){
            judgeFuncInterface.run(input);
            score = (Double) input.get(JUDGE_SCORE);
        }else if(funcType.equals(FuncType.SpecialFunc)){
            specialFuncInterface.run(input);
        }
        else {
            try {
                culFuncInterface.run(input);
            }catch (Exception e){
                input.put(culFuncInterface.getFuncName(),0);
            }
        }
        return input;
    }

    public boolean isJudge() {
        return this.funcType.equals(FuncType.JudgeFunc);
    }


}
