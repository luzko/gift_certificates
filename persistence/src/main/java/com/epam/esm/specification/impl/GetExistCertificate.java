package com.epam.esm.specification.impl;

import com.epam.esm.constant.FieldName;
import com.epam.esm.specification.SearchSpecification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class GetExistCertificate implements SearchSpecification {
    @Override
    public <T> Predicate toPredicate(CriteriaBuilder criteriaBuilder, Root<T> root) {
        return criteriaBuilder.isFalse(root.get(FieldName.IS_DELETED.getValue()));
    }
}
