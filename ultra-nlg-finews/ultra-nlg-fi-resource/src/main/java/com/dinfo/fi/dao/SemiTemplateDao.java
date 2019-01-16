package com.dinfo.fi.dao;

import com.dinfo.fi.dao.template.TransferDecorator;
import com.dinfo.fi.entity.SemiTemplate;
import com.dinfo.fi.entity.Template;
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
public class SemiTemplateDao extends TransferDecorator<SemiTemplate> {
    @Autowired
    private DataSource dataSource;

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }
}
