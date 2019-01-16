package com.dinfo.fi.service.in;

import com.dinfo.fi.dto.Sentence;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

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
public interface ConsistSentenceService {

    Sentence consistSentenceProxy(List<Map> maps);

    String changeSentenceToString(Sentence sentence);

    Sentence consistSentenceProxy(List<Map> maps, SimpleDateFormat format);
}
