package com.dinfo.fi.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * <p>Date:2018/12/11</p>
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
public interface SectionData {

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    void fixData(String fixInfos) throws ParseException;

}
