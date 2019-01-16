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
@ModelType(tableName = "shares_knowledge_base")
public class StockInfo implements Serializable {
    /**
     * id
     */
    @PrimaryKey(identityType = IdentityType.CUSTOM_ID)
    private String id;
    /**
     * 股票代码
     */
    private String code;
    /**
     * 股票名称
     */
    private String name;
    /**
     * 股票所属区域
     */
    private String area;
    /**
     * 股票类型（大盘、中盘、小盘）
     */
    private String shareType;
    /**
     * 股票板块，可能多个
     */
    private String industry;
    /**
     * 股票板块的id，可能多个
     */
    private String industryIds;
    /**
     * 股票概念，可能多个
     */
    private String concepts;
    /**
     * 股票概念id，可能多个
     */
    private String conceptsIds;
    /**
     * 股票所属市场
     */
    private String ealComponent;
}
