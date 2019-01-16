package com.dinfo.autowriting.entity;

import com.dinfo.autowriting.core.id.GeneratedId;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author yangxf
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DocGenreInfo extends BaseEntity {
    private static final long serialVersionUID = -5272426952680248697L;
    
    @GeneratedId
    private Long genreId;
    private String genreInfo;
}
