package com.dinfo.fi.enums;

/**
 * <p>Date:2018/10/17</p>
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
public enum StockIndexConditionType {

    STOCK(0,"股票","stock"),
    CONCEPT(1,"概念","concept"),
    INDUSTRY(2,"行业","industry"),
    INDEX(3,"指数","index");
    private int code;
    private String name;
    private String label;

    StockIndexConditionType(int code, String name, String label) {
        this.code = code;
        this.name = name;
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
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


    public static StockIndexConditionType getEnumByLabel(String label){
        for (StockIndexConditionType stockIndexConditionType : StockIndexConditionType.values()) {
            if(stockIndexConditionType.getLabel().equals(label)){
                return stockIndexConditionType;
            }
        }
        return null;
    }

    public static StockIndexConditionType getEnumByName(String name){
        for (StockIndexConditionType stockIndexConditionType : StockIndexConditionType.values()) {
            if(stockIndexConditionType.getName().equals(name)){
                return stockIndexConditionType;
            }
        }
        return null;
    }
}
