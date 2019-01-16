package com.dinfo.fi.service.in;

import com.dinfo.fi.dao.ConceptDicDao;
import com.dinfo.fi.dao.IndexDataDao;
import com.dinfo.fi.entity.ConceptDic;
import com.dinfo.fi.entity.IndexDataInfo;
import com.dinfo.fi.utils.Response;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
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
public class ConceptDicService {

    @Autowired
    private ConceptDicDao indexDataDao;


    public Response<List> getConceptsByCode(String code) {
        List<ConceptDic> query = indexDataDao.getJdbcTemplate().query("SELECT * FROM concept_dic WHERE  find_in_set('"+code+"', contains_code)", new RowMapper<ConceptDic>() {
            @Nullable
            @Override
            public ConceptDic mapRow(ResultSet resultSet, int i) throws SQLException {
                ConceptDic conceptDic = new ConceptDic();
                conceptDic.setId(resultSet.getString(1));
                conceptDic.setName(resultSet.getString(2));
                conceptDic.setContainsCode(resultSet.getString(3));
                return conceptDic;
            }
        });
        if(CollectionUtils.isEmpty(query)){
            return Response.notOk("数据为null");
        }
        return Response.ok(query);

    }


}
