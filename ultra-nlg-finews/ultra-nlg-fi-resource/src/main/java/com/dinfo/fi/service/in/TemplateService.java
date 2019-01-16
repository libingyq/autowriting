package com.dinfo.fi.service.in;

import com.dinfo.fi.dao.IndexDataDao;
import com.dinfo.fi.dao.TemplateDao;
import com.dinfo.fi.dao.TemplateParagraphDao;
import com.dinfo.fi.dao.TemplateSentenceDao;
import com.dinfo.fi.dto.TemplateAllContent;
import com.dinfo.fi.entity.IndexDataInfo;
import com.dinfo.fi.entity.Template;
import com.dinfo.fi.entity.TemplateParagraph;
import com.dinfo.fi.entity.TemplateSentence;
import com.dinfo.fi.utils.Response;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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
@Service
public class TemplateService {

    @Autowired
    private TemplateDao templateDao;
    @Autowired
    private TemplateParagraphDao templateParagraphDao;
    @Autowired
    private TemplateSentenceDao templateSentenceDao;


    public Response<TemplateAllContent> queryAllTemplate(){
        return Response.ok(templateDao.queryList());
    }


    public Response<TemplateAllContent> queryTemplateAllContents(Integer id){
        Template entity = templateDao.getEntity(id);
        String paragraphIds = entity.getParagraphIds();
        ArrayList<List<String>> lists = new ArrayList<>();
        for (String s : paragraphIds.split(",")) {
            TemplateParagraph paragraph = templateParagraphDao.getEntity(s);
            List<String> strings = new ArrayList<>();
            for (String sentenceId : paragraph.getSentenceIds().split(",")) {
                TemplateSentence sentences = templateSentenceDao.getEntity(sentenceId);
                strings.add(sentences.getExp());
            }
            lists.add(strings);
        }
        return Response.ok(TemplateAllContent.builder().id(id).paragraphs(lists).templateName(entity.getTemplateName()).userId(entity.getUserId())
                .createTime(entity.getCreateTime()).updateTime(entity.getUpdateTime()).build());
    }
    @Transactional(rollbackFor = Exception.class)
    public Response<Integer> addTemplate(TemplateAllContent templateAllContent){

        List<List<String>> paragraphs = templateAllContent.getParagraphs();

        Integer userId = templateAllContent.getUserId();
        Timestamp nowTime = new Timestamp(System.currentTimeMillis());
        StringBuffer paraIds = new StringBuffer();
        for (List<String> paragraph : paragraphs) {
            StringBuffer sentenceIds = new StringBuffer();
            for (String s : paragraph) {
                TemplateSentence build = TemplateSentence.builder().exp(s).createTime(nowTime)
                        .updateTime(nowTime).userId(userId).build();
                TemplateSentence insert = templateSentenceDao.insert(build);
                sentenceIds.append(insert.getId()).append(",");
            }
            String sentenseIdStr = sentenceIds.substring(0, sentenceIds.length() - 1);
            TemplateParagraph build = TemplateParagraph.builder().createTime(nowTime).updateTime(nowTime)
                    .userId(userId).sentenceIds(sentenseIdStr).build();
            TemplateParagraph insert = templateParagraphDao.insert(build);
            paraIds.append(insert.getId()).append(",");
        }
        Template build = Template.builder().createTime(nowTime).updateTime(nowTime).templateName(templateAllContent.getTemplateName())
                .userId(userId).paragraphIds(paraIds.substring(0, paraIds.length() - 1)).build();
        templateDao.insert(build);
        return Response.ok(build.getId());

    }



}
