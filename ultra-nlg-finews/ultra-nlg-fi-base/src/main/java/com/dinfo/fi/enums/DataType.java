package com.dinfo.fi.enums;

/**
 * <p>Date:2018/12/14</p>
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
public enum  DataType {


    STOCK(0,"股票"),INDEX(1,"指数"),CONCEPT(2,"概念"),INDUSTRY(3,"行业")    ;
    private int id;
    private String name;

    DataType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static DataType getEnumByName(String label){
        for (DataType dataType : DataType.values()) {
            if(dataType.getName().equals(label)){
                return dataType;
            }
        }
        return null;
    }
}
