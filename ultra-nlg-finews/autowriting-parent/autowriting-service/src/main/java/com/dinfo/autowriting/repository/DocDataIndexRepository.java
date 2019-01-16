package com.dinfo.autowriting.repository;

import com.dinfo.autowriting.index.DocDataIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author yangxf
 */
@Repository
public interface DocDataIndexRepository extends ElasticsearchRepository<DocDataIndex, String> {
    

}