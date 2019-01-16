package com.dinfo.autowriting.entity;

import java.io.Serializable;

/**
 * @author yangxf
 */
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 7399205496560977953L;
    
    private Long id;

    public Long getId() {
        return id;
    }

    public BaseEntity setId(Long id) {
        this.id = id;
        return this;
    }
}
