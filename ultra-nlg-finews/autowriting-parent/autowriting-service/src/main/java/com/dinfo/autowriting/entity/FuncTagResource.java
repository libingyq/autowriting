package com.dinfo.autowriting.entity;

import com.dinfo.autowriting.core.id.GeneratedId;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author yangxf
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FuncTagResource extends BaseEntity {
    private static final long serialVersionUID = 7878034075526103371L;

    @GeneratedId
    private Long funcTagId;
    private String funcTagValue;
    private Integer funcTagLen;
    private Long sourceId;
    private Long genreId;
    
}
