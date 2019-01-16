package com.dinfo.autowriting.mapper;

import com.dinfo.autowriting.entity.DocSourceInfo;
import org.apache.ibatis.annotations.*;

/**
 * @author yangxf
 */
@Mapper
public interface DocSourceInfoMapper {

    String TABLE = "t_doc_source_info";

    @Insert("insert into " + TABLE + "(source_id, source_name) values(#{sourceId}, #{sourceName})")
    @Options(useGeneratedKeys = true, keyColumn = "id")
    void add(DocSourceInfo info);

    @Update("update " + TABLE + " set source_id=#{sourceId}, source_name=#{sourceName} where id=#{id}")
    void modify(DocSourceInfo info);

    @Delete("delete from " + TABLE + " where id=#{id}")
    void removeById(Long id);

    @Delete("delete from " + TABLE + " where source_id=#{id}")
    void removeBySourceId(Long id);

    @Select("select * from " + TABLE + " where id=#{id}")
    DocSourceInfo findById(Long id);

    @Select("select * from " + TABLE + " where source_id=#{id}")
    DocSourceInfo findBySourceId(Long id);

}
