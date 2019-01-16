package com.dinfo.autowriting.mapper;

import com.dinfo.autowriting.entity.SrcDataInfo;
import org.apache.ibatis.annotations.*;

/**
 * @author yangxf
 */
@Mapper
public interface SrcDataInfoMapper {

    String TABLE = "src_data_info";

    @Insert("insert into " + TABLE + "(doc_id, title, text, pre_pro_text, struct_title, struct_text, source_id, genre_id) " +
            "values(#{docId}, #{title}, #{text}, #{preProText}, #{structTitle}, #{structText}, #{sourceId}, #{genreId})")
    @Options(useGeneratedKeys = true, keyColumn = "id")
    void add(SrcDataInfo info);

    @Update("update " + TABLE + " set doc_id=#{docId}, title=#{title}, text=#{text}, pre_pro_text=#{preProText}, " +
            "struct_title=#{structTitle}, struct_text=#{structText}, source_id=#{sourceId}, genre_id=#{genreId} " +
            "where id=#{id}")
    void modify(SrcDataInfo info);

    @Delete("delete from " + TABLE + " where id=#{id}")
    void removeById(Long id);
    
    @Delete("delete from " + TABLE + " where doc_id=#{id}")
    void removeByDocId(Long id);

    @Select("select * from " + TABLE + " where id=#{id}")
    SrcDataInfo findById(Long id);

    @Select("select * from " + TABLE + " where doc_id=#{id}")
    SrcDataInfo findByDocId(Long id);

}
