package com.dinfo.fi.dto;

import com.dinfo.fi.enums.StockIndexConditionType;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * <p>Date:2018/10/19</p>
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
@Data
public class SentenceElement{

    private String object;

    private String objectName;

    private String funcName;

    private Integer funcTypeCode;

    private Double score = 0.0;

    private String result;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SentenceElement that = (SentenceElement) o;

        if (objectName != null ? !objectName.equals(that.objectName) : that.objectName != null) return false;
        if (funcName != null ? !funcName.equals(that.funcName) : that.funcName != null) return false;
        return result != null ? result.equals(that.result) : that.result == null;
    }

    @Override
    public int hashCode() {
        int result1 = 0;
        result1 = 31 * result1 + (objectName != null ? objectName.hashCode() : 0);
        result1 = 31 * result1 + (funcName != null ? funcName.hashCode() : 0);
        result1 = 31 * result1 + (result != null ? result.hashCode() : 0);
        return result1;
    }
}