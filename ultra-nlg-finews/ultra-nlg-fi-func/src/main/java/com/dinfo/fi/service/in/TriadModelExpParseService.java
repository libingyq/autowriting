package com.dinfo.fi.service.in;

import com.dinfo.fi.func.TriadModel;

import java.util.List;

/**
 * <p>Date:2018/10/18</p>
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
public interface TriadModelExpParseService {

    /**
     * 解析单个句子成为多元组
     * @param sentenceExp 类似
     *                    <{早盘}+[{index:399106},{stock:603978}]+[{高开高走},{涨幅},{跌幅}]>
     *                    的单条三元组模板
     * @return
     */
    List<TriadModel> parseTriadModelByExp(String sentenceExp);

}
