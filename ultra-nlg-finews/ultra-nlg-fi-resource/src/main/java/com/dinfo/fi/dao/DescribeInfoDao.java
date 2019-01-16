package com.dinfo.fi.dao;

import com.dinfo.fi.dao.template.TransferDecorator;
import com.dinfo.fi.entity.DescribeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

/**
 * @auther rongzihao
 * @date 2018/10/31 13:42
 */
@Repository
public class DescribeInfoDao extends TransferDecorator<DescribeInfo> {

    @Autowired
    private DataSource dataSource;

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }
}
