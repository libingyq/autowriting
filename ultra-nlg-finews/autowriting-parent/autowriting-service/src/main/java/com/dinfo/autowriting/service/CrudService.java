package com.dinfo.autowriting.service;

/**
 * @author yangxf
 */
public interface CrudService<T, ID> {

    T create(T t);

    T update(T t);

    void delete(ID id);

    T retrieveById(ID id);
    
}
