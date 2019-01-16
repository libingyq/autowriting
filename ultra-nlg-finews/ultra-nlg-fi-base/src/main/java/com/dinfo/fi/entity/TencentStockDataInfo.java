package com.dinfo.fi.entity;

import com.dinfo.core.IdentityType;
import com.dinfo.core.ModelType;
import com.dinfo.core.PrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Builder;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * <p>Date:2018/10/10</p>
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
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ModelType(tableName = "share_ticker_tencent")
public class TencentStockDataInfo implements Serializable,DataInfo {
    /**
     * 股票代码
     */
    @PrimaryKey(identityType = IdentityType.CUSTOM_ID)
    private String code;
    /**
     * 股票名称
     */
    private String name;
    /**
     * 拉取时间
     */
    @PrimaryKey(identityType = IdentityType.CUSTOM_ID)
    private Timestamp time;
    /**
     * 涨跌幅 （%）
     */
    private Double changePercent;
    /**
     * 开盘价
     */
    private Double open;
    /**
     * 最高价
     */
    private Double high;
    /**
     * 最低价
     */
    private Double low;
    /**
     * 现价
     */
    private Double close;
    /**
     * 昨日收盘价
     */
    private Double settlement;
    /**
     * 成交量 （手）
     */
    private Long volume;
    /**
     * 换手率 （%）
     */
    private Double turnover;
    /**
     * 交易总额 (万)
     */
    private Double amount;



}
