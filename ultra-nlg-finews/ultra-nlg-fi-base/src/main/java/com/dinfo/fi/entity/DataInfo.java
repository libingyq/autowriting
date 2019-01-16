package com.dinfo.fi.entity;

import java.sql.Timestamp;

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
public interface DataInfo {


    String getCode();

    String getName();

    Timestamp getTime();

    Double getChangePercent();
    /**
     * 开盘价
     */
    Double getOpen();
    /**
     * 最高价
     */
    Double getHigh();
    /**
     * 最低价
     */
    Double getLow();
    /**
     * 收盘价
     */
    Double getClose();

    /**
     * 上一日收盘价
     * @return
     */
    Double getSettlement();



}
