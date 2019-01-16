package com.dinfo.autowriting.index;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.List;

/**
 * 正文内容标签值信息
 *
 * @author yangxf
 */
@Data
@Document(indexName = "content_tag_value_index", type = "content_tag_value")
public class ContentTagValueIndex implements Serializable {
    private static final long serialVersionUID = 3637439778386398013L;
    
    @Id
    private String tagId;
    
    @Field(type = FieldType.keyword)
    private String tagName;
    
    @Field(type = FieldType.Integer)
    private Integer tagNameLen;

    /**
     * 变量标签名称在pre_pro_text中的起始位置，其他标签默认值-1
     */
    @Field(type = FieldType.Integer)
    private Integer nameStartPos;

    /**
     * 变量标签名称在pre_pro_text中的结束位置，其他标签默认值-1
     */
    @Field(type = FieldType.Integer)
    private Integer nameEndPos;

    @Field(analyzer = "ik_smart", type = FieldType.text)
    private String tagValue;
    
    @Field(type = FieldType.Integer)
    private Integer valueStartPos;
    
    @Field(type = FieldType.Integer)
    private Integer valueEndPos;

    /**
     * 0：常量；1：变量；2：未知
     */
    @Field(type = FieldType.Integer)
    private Integer tagType;

    /**
     * 公文唯一索引值
     */
    @Field(type = FieldType.keyword)
    private String docId;

    /**
     * 标签值中识别标题领域可替换信息列表
     */
    @Field(type = FieldType.keyword)
    private List<String> domainInValue;

    /**
     * 对应列表中下标元素在pre_pro_text中的起始位置
     */
    @Field(type = FieldType.Integer)
    private List<Integer> domainStartPos;

    /**
     * 对应列表中下标元素在pre_pro_text中的结束位置
     */
    @Field(type = FieldType.Integer)
    private List<Integer> domainEndPos;
}
