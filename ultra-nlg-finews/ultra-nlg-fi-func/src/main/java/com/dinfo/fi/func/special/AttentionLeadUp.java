package com.dinfo.fi.func.special;

import com.dinfo.fi.anno.FuncAttr;
import com.dinfo.fi.entity.ConceptData;
import com.dinfo.fi.entity.DataInfo;
import com.dinfo.fi.entity.IndustryData;
import com.dinfo.fi.entity.StockDataInfo;
import com.dinfo.fi.enums.FuncType;
import com.dinfo.fi.enums.StockIndexConditionType;
import com.dinfo.fi.func.object.GrabConceptByCode;
import com.dinfo.fi.func.upper.UpperInter;
import com.dinfo.fi.inter.SpecialFuncInterface;
import com.dinfo.fi.service.in.TriadModelExpParseService;
import com.dinfo.fi.spy.FuncLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static com.dinfo.fi.common.CommonConst.Comma;
import static com.dinfo.fi.enums.DataType.CONCEPT;

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
@FuncAttr(name = "关注领涨",type = FuncType.SpecialFunc)
public class AttentionLeadUp implements SpecialFuncInterface {


    @Override
    public String reduce(List<DataInfo> stockInfos) {

        DataInfo dataInfoEnd = stockInfos.get(stockInfos.size() - 1);
        String leadUp = "";
        if(dataInfoEnd instanceof ConceptData){
            ConceptData concept = (ConceptData) dataInfoEnd;
            leadUp = concept.getLeadUp();

        }else if(dataInfoEnd instanceof IndustryData) {
            IndustryData industry = (IndustryData) dataInfoEnd;
            leadUp = industry.getLeadUp();

        }else {
            return "";
        }
        //600071|凤凰光学|8.80|5.39
        String[] split = leadUp.split("\\|");
        String info =  "领涨股为"+split[1]+"("+split[0]+")"+"现价为"+split[2]+"元，涨幅"+split[3]+"%";
        return info;


    }



}
