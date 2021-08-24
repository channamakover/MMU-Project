package com.hit.dao;

import java.io.IOException;

public interface IDao<ID extends java.io.Serializable,T> {
    void save(T entity) throws IOException;
    void delete(T entity) throws java.lang.IllegalArgumentException, IOException;
    T find(ID id) throws java.lang.IllegalArgumentException, IOException;
}
