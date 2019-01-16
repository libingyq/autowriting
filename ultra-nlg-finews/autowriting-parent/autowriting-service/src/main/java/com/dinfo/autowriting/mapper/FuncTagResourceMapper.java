package com.dinfo.autowriting.mapper;

import com.dinfo.autowriting.entity.FuncTagResource;
import org.apache.ibatis.annotations.*;

/**
 * @author yangxf
 */
@Mapper
public interface FuncTagResourceMapper {

    String TABLE = "func_tag_resource";

    @Insert("insert into " + TABLE + "(func_tag_id, func_tag_value, func_tag_len, source_id, genre_id) " +
            "values(#{funcTagId}, #{funcTagValue}, #{funcTagLen}, #{sourceId}, #{genreId})")
    @Options(useGeneratedKeys = true, keyColumn = "id")
    void add(FuncTagResource resource);

    @Update("update " + TABLE + " set func_tag_id=#{funcTagId}, func_tag_value=#{funcTagValue}, " +
            "func_tag_len=#{funcTagLen}, source_id=#{sourceId}, genre_id=#{genreId} " +
            "where id=#{id}")
    void modify(FuncTagResource resource);

    @Delete("delete from " + TABLE + " where id=#{id}")
    void removeById(Long id);

    @Delete("delete from " + TABLE + " where func_tag_id=#{id}")
    void removeByFuncTagId(Long id);

    @Select("select * from " + TABLE + " where id=#{id}")
    FuncTagResource findById(Long id);

    @Select("select * from " + TABLE + " where func_tag_id=#{id}")
    FuncTagResource findByFuncTagId(Long id);

}
