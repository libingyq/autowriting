package com.dinfo.fi.func.object;

import com.dinfo.fi.SpringUtil;
import com.dinfo.fi.anno.FuncAttr;
import com.dinfo.fi.common.DataCache;
import com.dinfo.fi.dto.QueryInfoConditionDto;
import com.dinfo.fi.entity.DataInfo;
import com.dinfo.fi.enums.StockIndexConditionType;
import com.dinfo.fi.enums.FuncType;
import com.dinfo.fi.func.time.MorningSession;
import com.dinfo.fi.inter.ObjectFuncInterface;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Date:2018/10/15</p>
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
@FuncAttr(name = "解析数据函数总入口",type = FuncType.ObjectFunc)
public class GrabData implements ObjectFuncInterface{
    @Override
    public List<DataInfo> queryData(QueryInfoConditionDto query) {
        StockIndexConditionType chooseType = query.getChooseType();

        List<DataInfo> cache = DataCache.getInstance().get(query.thisConditionKey());
        if(!CollectionUtils.isEmpty(cache)){
            return cache;
        }

        List<DataInfo> dataInfos = new ArrayList<>();
        switch (chooseType){
            case INDEX:
                dataInfos = SpringUtil.getApplicationContext().getBean(GrabIndexByCode.class).queryData(query);
                break;
            case STOCK:
                dataInfos = SpringUtil.getApplicationContext().getBean(GrabDataByCode.class).queryData(query);
                break;
            case CONCEPT:
                dataInfos = SpringUtil.getApplicationContext().getBean(GrabConceptByCode.class).queryData(query);
                break;
            case INDUSTRY:
                dataInfos = SpringUtil.getApplicationContext().getBean(GrabIndustryByCode.class).queryData(query);
                break;
        }
        DataCache.getInstance().set(query.thisConditionKey(),dataInfos);
        return dataInfos;
    }


    //    @Override
//    public List<DataInfo> queryData(QueryInfoConditionDto query) {
//        StockQueryConditionType queryType = query.getQueryType();
//        List<DataInfo> data = new ArrayList<>();
//        switch (queryType) {
//            case CODE:
//                data = new GrapDataByCode().queryData(query);
//                break;
//            case CONCEPT:
//                data = new GrapDataByConcept().queryData(query);
//                break;
//            case NAME:
//                data = new GrapDataByName().queryData(query);
//                break;
//            case EAL_COMPONENT:
//                data = new GrapDataByEalComponent().queryData(query);
//                break;
//            case AREA:
//                data = new GrapDataByArea().queryData(query);
//                break;
//            case SHARE_TYPE:
//                data = new GrapDataByShareType().queryData(query);
//                break;
//            case INDUSTRY:
//                data = new GrapDataByIndustry().queryData(query);
//                break;
//        }
//
//        return data;
//    }



}
