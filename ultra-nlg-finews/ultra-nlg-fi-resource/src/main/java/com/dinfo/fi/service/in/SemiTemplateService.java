package com.dinfo.fi.service.in;

import com.dinfo.fi.dao.SemiTemplateDao;
import com.dinfo.fi.dao.TemplateDao;
import com.dinfo.fi.dao.TemplateParagraphDao;
import com.dinfo.fi.dao.TemplateSentenceDao;
import com.dinfo.fi.dto.TemplateAllContent;
import com.dinfo.fi.entity.SemiTemplate;
import com.dinfo.fi.entity.Template;
import com.dinfo.fi.entity.TemplateParagraph;
import com.dinfo.fi.entity.TemplateSentence;
import com.dinfo.fi.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class SemiTemplateService {

    @Autowired
    private SemiTemplateDao templateDao;



    public Response<SemiTemplate> queryAllTemplate(){
        return Response.ok(templateDao.queryList());
    }

    public Response<SemiTemplate> querySemiTempById(Integer id){
        return Response.ok(templateDao.queryById(id));
    }





}
