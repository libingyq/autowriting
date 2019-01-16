package com.dinfo.autowriting.service;

import com.dinfo.autowriting.core.id.IdGenerator;
import com.dinfo.autowriting.index.DocDataIndex;
import com.dinfo.autowriting.repository.DocDataIndexRepository;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

import static com.dinfo.autowriting.core.util.ObjectUtil.isEmpty;

/**
 * @author yangxf
 */
@Service
public class DocDataIndexService implements CrudService<DocDataIndex, String> {

    @Autowired
    private DocDataIndexRepository repository;

    @Resource
    private IdGenerator idGenerator;

    @Override
    public DocDataIndex create(DocDataIndex docDataIndex) {
        if (docDataIndex == null) {
            return null;
        }
        String docId = docDataIndex.getDocId();
        if (isEmpty(docId)) {
            docDataIndex.setDocId(String.valueOf(idGenerator.nextId()));
        }
        return repository.save(docDataIndex);
    }

    @Override
    public DocDataIndex update(DocDataIndex docDataIndex) {
        return null;
    }

    @Override
    public void delete(String s) {
        repository.deleteById(s);
    }

    @Override
    public DocDataIndex retrieveById(String s) {
        return repository.findById(s).orElse(null);
    }

    public List<DocDataIndex> query(String field, String content) {
        try {
            QueryBuilder builder = QueryBuilders.termQuery(field, content);
            Pageable pageable = PageRequest.of(0, 10);
            Page<DocDataIndex> page = repository.search(builder, pageable);
            return page.getContent();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

}
