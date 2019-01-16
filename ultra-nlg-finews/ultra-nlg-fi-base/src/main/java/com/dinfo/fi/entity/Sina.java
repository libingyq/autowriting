package com.dinfo.fi.entity;

import lombok.Data;

import java.util.Map;

/**
 * @auther rongzihao
 * @date 2018/10/23 14:43
 */
@Data
public class Sina {

    private String title;

    private String classification;

    private String text;

    private String url;

    /**
     *采集时间
     */
    private String collectionTime;

    /**
     * 发布时间
     */
    private String releaseTime;

    //任务信息
    private Map<String , Map<String,String>> map ;
}
