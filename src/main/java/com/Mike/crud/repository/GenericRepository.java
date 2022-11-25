package com.Mike.crud.repository;

import java.sql.SQLException;
import java.util.List;

public interface GenericRepository <T, Id> {
    T getById (Id id);
    List<T> getAll();
    T save (T t);
    T update (T t);
    void deleteById(Id id);
}

