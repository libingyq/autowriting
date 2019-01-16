package com.dinfo.autowriting.index;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.List;

/**
 * @author yangxf
 */
@Data
@Document(indexName = "semantic_tag_index", type = "semantic_tag")
public class SemanticTagIndex implements Serializable {
    private static final long serialVersionUID = -2173415895644779309L;

    /**
     * 语义标签值id，非常量标签值基于语义计算的索引
     */
    @Id
    private String semTagValueId;

    /**
     * 语义标签值
     */
    @Field(analyzer = "ik_smart", type = FieldType.text)
    private String semTagValue;

    /**
     * 收集语义标签值过程中合并的领域信息集合
     */
    @Field(type = FieldType.keyword)
    private List<String> domainInfo;
}
