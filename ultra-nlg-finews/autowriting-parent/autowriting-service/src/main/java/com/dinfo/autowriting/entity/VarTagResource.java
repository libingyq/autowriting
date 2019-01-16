package com.dinfo.autowriting.entity;

import com.dinfo.autowriting.core.id.GeneratedId;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author yangxf
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class VarTagResource extends BaseEntity {
    private static final long serialVersionUID = 2201618853974870409L;
    
    @GeneratedId
    private Long varTagId;
    private String varTagName;
    private Integer varTagLen;
    private Long sourceId;
    private Long genreId;
}
