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
 * <p>Date:2018/10/18</p>
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
@ModelType(tableName = "template_semi")
public class SemiTemplate implements Serializable{

    @PrimaryKey(identityType = IdentityType.AUTO_INCREMENT)
    private int id;

    private int userId;

    private String templateName;

    private String template;

    private Timestamp createTime;

    private Timestamp updateTime;

}
