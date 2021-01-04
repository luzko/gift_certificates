package com.epam.esm.service;

public interface BaseService<T> {
    T add(T t);

    void remove(long id);

    T findById(long id);
}