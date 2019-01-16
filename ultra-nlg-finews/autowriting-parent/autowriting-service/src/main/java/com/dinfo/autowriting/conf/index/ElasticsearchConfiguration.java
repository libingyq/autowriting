package com.dinfo.autowriting.conf.index;

import com.dinfo.autowriting.conf.AutoWritingCustomProperties;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static com.dinfo.autowriting.core.util.ObjectUtil.nonEmpty;


/**
 * @author yangxf
 */
@Slf4j
@Configuration
public class ElasticsearchConfiguration implements ResourceLoaderAware {

    private final ConcurrentMap<String, ClassMetadata> CLASS_MAP = new ConcurrentHashMap<>();

    @Autowired
    private AutoWritingCustomProperties customProperties;

    // @Bean
    public CommandLineRunner elasticsearchIndexInit(TransportClient transportClient) {
        return args -> createIfNotExists(transportClient);
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        try {
            ResourcePatternResolver resolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
            MetadataReaderFactory metaReader = new CachingMetadataReaderFactory(resourceLoader);
            String[] indexBasePackages = customProperties.getIndexBasePackages();
            for (String packageName : indexBasePackages) {
                Resource[] resourceArr = resolver.getResources("classpath*:" + packagePath(packageName) + "/**/*.class");
                for (Resource resource : resourceArr) {
                    MetadataReader reader = metaReader.getMetadataReader(resource);
                    ClassMetadata classMetadata = reader.getClassMetadata();
                    CLASS_MAP.putIfAbsent(classMetadata.getClassName(), classMetadata);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 当前版本jpa已修复不能创建index的bug
     */
    @Deprecated
    private void createIfNotExists(TransportClient client) throws Exception {
        IndicesAdminClient indices = client.admin().indices();

        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        for (Map.Entry<String, ClassMetadata> entry : CLASS_MAP.entrySet()) {
            String className = entry.getKey();
            Class<?> cls = loader.loadClass(className);
            Document document = cls.getAnnotation(Document.class);
            if (document == null) {
                continue;
            }

            String indexName = document.indexName(),
                    typeName = document.type();

            IndicesExistsRequest existsRequest = new IndicesExistsRequest(indexName);
            IndicesExistsResponse existsResponse = indices.exists(existsRequest).get();
            boolean exists = existsResponse.isExists();
            if (exists) {
                log.info("index {} exists, skip.", indexName);
                continue;
            }

            log.info("create index {}.", indexName);
            CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexName);
            CreateIndexResponse createIndexResponse = indices.create(createIndexRequest).get();
            if (createIndexResponse.isAcknowledged()) {
                log.info("create index {} success.", indexName);
            } else {
                throw new RuntimeException("create index " + indexName + " failure.");
            }

            XContentBuilder properties = XContentFactory.jsonBuilder()
                    .startObject()
                    .startObject("properties");

            Field[] declaredFields = cls.getDeclaredFields();
            for (Field field : declaredFields) {
                org.springframework.data.elasticsearch.annotations.Field fieldAnnotation =
                        field.getAnnotation(org.springframework.data.elasticsearch.annotations.Field.class);
                properties.startObject(field.getName());
                if (fieldAnnotation == null) {
                    properties.field("type", "keyword").endObject();
                    continue;
                }
                String analyzer = fieldAnnotation.analyzer();
                FieldType type = fieldAnnotation.type();
                boolean store = fieldAnnotation.store();
                properties.field("store", store);
                if (nonEmpty(analyzer)) {
                    properties.field("analyzer", analyzer)
                            .field("type", "text");
                } else if (type == FieldType.Auto) {
                    properties.field("type", "keyword");
                } else {
                    properties.field("type", type.name().toLowerCase());
                }
                properties.endObject();
            }

            properties.endObject().endObject();
            PutMappingRequest mappingRequest = Requests.putMappingRequest(indexName).type(typeName).source(properties);
            indices.putMapping(mappingRequest).get();
        }
    }

    private static String packagePath(String packageName) {
        return packageName.replace('.', '/');
    }

}
