package com.dinfo.autowriting.mapper;

import com.dinfo.autowriting.entity.DocGenreInfo;
import org.apache.ibatis.annotations.*;

/**
 * @author yangxf
 */
@Mapper
public interface DocGenreInfoMapper {

    String TABLE = "t_doc_genre_info";

    @Insert("insert into " + TABLE + "(genre_id, genre_info) values(#{genreId}, #{genreInfo})")
    @Options(useGeneratedKeys = true, keyColumn = "id")
    void add(DocGenreInfo info);

    @Update("update " + TABLE + " set genre_id=#{genreId}, genre_info=#{genreInfo} where id=#{id}")
    void modify(DocGenreInfo info);

    @Delete("delete from " + TABLE + " where id=#{id}")
    void removeById(Long id);

    @Delete("delete from " + TABLE + " where genre_id=#{id}")
    void removeByGenreId(Long id);

    @Select("select * from " + TABLE + " where id=#{id}")
    DocGenreInfo findById(Long id);

    @Select("select * from " + TABLE + " where genre_id=#{id}")
    DocGenreInfo findByGenreId(Long id);

}
