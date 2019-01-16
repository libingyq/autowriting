package com.dinfo.fi.entity;

import com.dinfo.core.IdentityType;
import com.dinfo.core.ModelType;
import com.dinfo.core.PrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Builder;

import java.sql.Timestamp;
import java.text.ParseException;

/**
 * <p>Date:2018/12/10</p>
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
@ModelType(tableName = "industry_data")
public class IndustryData implements DataInfo,SectionData{

    /**
     * 板块代码
     */
    @PrimaryKey(identityType = IdentityType.CUSTOM_ID)
    private String code;
    /**
     * 板块名称
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
     * 总量
     */
    private Long sumNum;
    /**
     * 振幅
     */
    private Double swing;
    /**
     * 换手率
     */
    private Double turnOverRate;

    /**
     * 涨家数|平家数|跌家数
     */
    private String infos;

    /**
     * 领涨股
     * 600807|*ST天业|4.24|4.43
     * id|名称|最新价|涨跌幅
     */
    private String leadUp;

    /**
     * 领跌股
     */
    private String leadDown;



    @Override
    public void fixData(String fixInfos) throws ParseException {
        String[] singleDetails = fixInfos.split(",");
        this.setTime(new Timestamp(format.parse(singleDetails[3]).getTime()));
        this.setClose(Double.parseDouble(singleDetails[4]));
        this.setChangePercent(Double.parseDouble(singleDetails[5]));
        this.setOpen(Double.parseDouble(singleDetails[7]));
        this.setHigh(Double.parseDouble(singleDetails[8]));
        this.setSumNum(Long.parseLong(singleDetails[9]));
        this.setSwing(Double.parseDouble(singleDetails[12]));
        this.setSettlement(Double.parseDouble(singleDetails[13]));
        this.setLow(Double.parseDouble(singleDetails[14]));
        this.setTurnOverRate(Double.parseDouble(singleDetails[23]));
    }
}
