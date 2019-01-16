package com.dinfo.fi.service.in;

import com.dinfo.fi.dao.*;
import com.dinfo.fi.entity.ConceptDic;
import com.dinfo.fi.entity.IndexDic;
import com.dinfo.fi.entity.IndustryDic;
import com.dinfo.fi.entity.StockInfo;
import com.dinfo.fi.enums.DataType;
import com.dinfo.fi.utils.Response;
import com.dinfo.fi.utils.TwoTuple;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>Date:2018/12/14</p>
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
public class ObjectService {
    @Autowired
    private ConceptDicDao conceptDicDao;
    @Autowired
    private IndustryDicDao industryDicDao;
    @Autowired
    private StockInfoDao stockInfoDao;
    @Autowired
    private IndexDicDao indexDicDao;

    public Response<HashMap<DataType, List<TwoTuple>>> getAllObjectSimpleMap(){

        HashMap<DataType, List<TwoTuple>> map = Maps.newHashMap();

        List<StockInfo> stockInfos = stockInfoDao.queryList();
        List<IndexDic> indexDics = indexDicDao.queryList();
        List<ConceptDic> conceptDics = conceptDicDao.queryList();
        List<IndustryDic> industryDics = industryDicDao.queryList();
        List<TwoTuple> stock = stockInfos.stream().map(s -> new TwoTuple(s.getCode(), s.getName())).collect(Collectors.toList());
        List<TwoTuple> index = indexDics.stream().map(i -> new TwoTuple(i.getCode(), i.getName())).collect(Collectors.toList());
        List<TwoTuple> concept = conceptDics.stream().map(c -> new TwoTuple(c.getId(), c.getName())).collect(Collectors.toList());
        List<TwoTuple> industry = industryDics.stream().map(i -> new TwoTuple(i.getId(), i.getName())).collect(Collectors.toList());

        map.put(DataType.STOCK,stock);
        map.put(DataType.INDEX,index);
        map.put(DataType.CONCEPT,concept);
        map.put(DataType.INDUSTRY,industry);
        return Response.ok(map);

    }





}
