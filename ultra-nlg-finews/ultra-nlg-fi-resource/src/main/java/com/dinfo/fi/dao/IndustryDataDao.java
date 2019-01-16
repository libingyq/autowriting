package com.dinfo.fi.dao;

import com.dinfo.fi.dao.template.TransferDecorator;
import com.dinfo.fi.entity.IndustryData;
import com.dinfo.fi.entity.IndustryDic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

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
@Repository
public class IndustryDataDao extends TransferDecorator<IndustryData> {
    @Autowired
    private DataSource dataSource;

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }
}
