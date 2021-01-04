package com.epam.esm.dao;

import com.epam.esm.entity.SqlRequest;
import com.epam.esm.model.Tag;

import java.util.List;

public interface TagDAO extends BaseDao<Tag> {
    void add(List<Tag> tags);

    List<Tag> findByName(SqlRequest sqlRequest);

    List<Tag> findByCertificateId(long id);

    List<Tag> findAll();
}