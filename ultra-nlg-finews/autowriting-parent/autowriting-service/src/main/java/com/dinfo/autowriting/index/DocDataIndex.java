package com.dinfo.autowriting.index;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.List;

/**
 * 公文数据基本信息索引
 *
 * @author yangxf
 */
@Data
@Document(indexName = "doc_data_index", type = "doc_data")
public class DocDataIndex implements Serializable {
    private static final long serialVersionUID = -500468800267029961L;

    @Id
    private String docId;
    
    @Field(analyzer = "ik_smart", type = FieldType.text)
    private String title;

    /**
     * 公文原文
     */
    private String text;

    /**
     * 预处理后正文内容
     */
    private String preProText;

    /**
     * 发文机关
     */
    @Field(analyzer = "ik_smart", type = FieldType.text)
    private List<String> agency;

    /**
     * 存放功能标签值
     */
    @Field(type = FieldType.keyword)
    private String funcTag;
    
    /**
     * 功能标签值长度
     */
    @Field(type = FieldType.Integer)
    private Integer funcTagLen;

    /**
     * 体裁
     */
    @Field(type = FieldType.keyword)
    private String genre;

    /**
     * 标题中的时间信息
     */
    @Field(type = FieldType.Date)
    private String time;

    /**
     * 领域信息
     */
    @Field(analyzer = "ik_smart", type = FieldType.text)
    private String domainInfo;

}
