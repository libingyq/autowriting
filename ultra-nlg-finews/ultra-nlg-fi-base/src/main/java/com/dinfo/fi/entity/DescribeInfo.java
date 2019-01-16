package com.dinfo.fi.entity;

import com.dinfo.core.IdentityType;
import com.dinfo.core.ModelType;
import com.dinfo.core.PrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Builder;

import java.io.Serializable;

/**
 * @auther rongzihao
 * @date 2018/10/31 13:39
 */
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ModelType(tableName = "trend_describe")
public class DescribeInfo implements Serializable {

    @PrimaryKey(identityType = IdentityType.AUTO_INCREMENT)
    private String id;

    /**
     * 趋势
     */
    private String trend;

    /**
     * 表述
     */
    private String describe;
}
