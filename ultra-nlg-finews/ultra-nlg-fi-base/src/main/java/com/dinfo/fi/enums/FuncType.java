package com.dinfo.fi.enums;

import java.sql.Time;

/**
 * <p>Date:2018/9/20</p>
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
public enum FuncType {

    JudgeFunc(0,"判断函数"),
    CulFunc(1,"计算函数"),
    ObjectFunc(2,"对象函数"),
    TimeFunc(3,"时间函数"),
    UpperFunc(4,"上层函数"),
    SpecialFunc(5,"特殊函数");
    private int id;
    private String name;

    FuncType(int id, String name) {
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
}
