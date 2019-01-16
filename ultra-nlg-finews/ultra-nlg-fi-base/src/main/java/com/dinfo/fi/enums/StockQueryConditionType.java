package com.dinfo.fi.enums;

/**
 * <p>Date:2018/10/15</p>
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
public enum StockQueryConditionType {


    CODE(0,"股票代码"),
    NAME(1,"股票名"),
    AREA(2,"区域"),
    SHARE_TYPE(3,"股盘"),
    INDUSTRY(4,"行业"),
    CONCEPT(5,"概念"),
    EAL_COMPONENT(6,"所属市场");

    private int code;
    private String name;

    StockQueryConditionType(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
