package com.dinfo.autowriting.entity;

import com.dinfo.autowriting.core.id.GeneratedId;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author yangxf
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DocSourceInfo extends BaseEntity {
    private static final long serialVersionUID = -6010234580928325868L;
    
    @GeneratedId
    private Long sourceId;
    private String sourceName;
}
