package com.epam.esm.dao;

import java.util.Optional;

public interface BaseDao<T> {
    T add(T t);

    boolean remove(long id);

    Optional<T> findById(long id);
}