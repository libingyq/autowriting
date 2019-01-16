package com.dinfo.fi.dto;

import com.dinfo.fi.enums.StockIndexConditionType;
import com.dinfo.fi.enums.StockQueryConditionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Builder;

import java.io.Serializable;

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
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class QueryInfoConditionDto implements Serializable{

    private String tip;

    private StockQueryConditionType queryType;

    private StockIndexConditionType chooseType;

    private String startTime;

    private String endTime;


    public String thisConditionKey(){
        return startTime+endTime+chooseType.getName()+tip;
    }



}
