package com.dinfo.fi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Builder;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * <p>Date:2018/10/22</p>
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
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TemplateAllContent implements Serializable{

    private Integer id;

    private Integer userId;

    private String templateName;

    private Timestamp createTime;

    private Timestamp updateTime;

    private List<List<String>> paragraphs;

}
