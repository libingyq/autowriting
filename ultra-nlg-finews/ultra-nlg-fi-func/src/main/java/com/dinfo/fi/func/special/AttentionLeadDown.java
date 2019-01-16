package com.dinfo.fi.func.special;

import com.dinfo.fi.anno.FuncAttr;
import com.dinfo.fi.entity.ConceptData;
import com.dinfo.fi.entity.DataInfo;
import com.dinfo.fi.entity.IndustryData;
import com.dinfo.fi.enums.FuncType;
import com.dinfo.fi.inter.SpecialFuncInterface;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>Date:2018/11/2</p>
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
@FuncAttr(name = "关注领跌",type = FuncType.SpecialFunc)
public class AttentionLeadDown implements SpecialFuncInterface {


    @Override
    public String reduce(List<DataInfo> stockInfos) {

        DataInfo dataInfoEnd = stockInfos.get(stockInfos.size() - 1);
        String leadDown = "";
        if(dataInfoEnd instanceof ConceptData){
            ConceptData concept = (ConceptData) dataInfoEnd;
            leadDown = concept.getLeadDown();

        }else if(dataInfoEnd instanceof IndustryData) {
            IndustryData industry = (IndustryData) dataInfoEnd;
            leadDown = industry.getLeadDown();

        }else {
            return "";
        }
        //600071|凤凰光学|8.80|5.39
        String[] split = leadDown.split("\\|");
        String info =  "领跌股为"+split[1]+"("+split[0]+")"+"现价为"+split[2]+"元，跌幅"+(-Double.parseDouble(split[3]))+"%";
        return info;


    }



}
