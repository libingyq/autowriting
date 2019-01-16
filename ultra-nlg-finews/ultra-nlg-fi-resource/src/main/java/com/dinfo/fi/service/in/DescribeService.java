package com.dinfo.fi.service.in;

import com.dinfo.core.query.CompareType;
import com.dinfo.core.query.Query;
import com.dinfo.fi.dao.DescribeInfoDao;
import com.dinfo.fi.entity.DescribeInfo;
import com.dinfo.fi.utils.Response;
import com.dinfo.rdbms.crud.RdmsQuery;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class DescribeService {

    @Autowired
    private DescribeInfoDao DescribeInfo;

    public Response<DescribeInfo> getDescribeByTrend(String trend){
        Query query = new RdmsQuery();
        query.from(DescribeInfo.class).where("trend", CompareType.EQ,trend);
        List<DescribeInfo> describeInfo = DescribeInfo.queryListByConditions(query);
        if(CollectionUtils.isEmpty(describeInfo)){
            return Response.notOk("该趋势没有对应的数据");
        }
        return Response.ok(describeInfo);
    }
}
