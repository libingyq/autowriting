package com.dinfo.autowriting.mapper;

import com.dinfo.autowriting.entity.VarTagResource;
import org.apache.ibatis.annotations.*;

/**
 * @author yangxf
 */
@Mapper
public interface VarTagResourceMapper {

    String TABLE = "var_tag_resource";

    @Insert("insert into " + TABLE + "(var_tag_id, var_tag_name, var_tag_len, source_id, genre_id) " +
            "values(#{varTagId}, #{varTagName}, #{varTagLen}, #{sourceId}, #{genreId})")
    @Options(useGeneratedKeys = true, keyColumn = "id")
    void add(VarTagResource resource);

    @Update("update " + TABLE + " set var_tag_id=#{varTagId}, var_tag_name=#{varTagName}, " +
            "var_tag_len=#{varTagLen}, source_id=#{sourceId}, genre_id=#{genreId} " +
            "where id=#{id}")
    void modify(VarTagResource resource);

    @Delete("delete from " + TABLE + " where id=#{id}")
    void removeById(Long id);

    @Delete("delete from " + TABLE + " where var_tag_id=#{id}")
    void removeByVarTagId(Long id);

    @Select("select * from " + TABLE + " where id=#{id}")
    VarTagResource findById(Long id);

    @Select("select * from " + TABLE + " where var_tag_id=#{id}")
    VarTagResource findByVarTagId(Long id);

}
