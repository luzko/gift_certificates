package com.epam.esm.specification.impl;

import com.epam.esm.specification.SortSpecification;
import lombok.AllArgsConstructor;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

@AllArgsConstructor
public class SortCertificateASC implements SortSpecification {
    private final String field;

    @Override
    public <T> Order toOrder(CriteriaBuilder criteriaBuilder, Root<T> root) {
        return criteriaBuilder.asc(root.get(field));
    }
}
