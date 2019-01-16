package com.dinfo.autowriting.entity;

import com.dinfo.autowriting.core.id.GeneratedId;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author yangxf
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SrcDataInfo extends BaseEntity {
    private static final long serialVersionUID = 3309136294846625092L;
    
    @GeneratedId
    private Long docId;
    private String title;
    private String text;
    private String preProText;
    private String structTitle;
    private String structText;
    private Long sourceId;
    private Long genreId;
    
}
