package com.epam.esm.specification.impl;

import com.epam.esm.constant.FieldName;
import com.epam.esm.specification.SearchSpecification;
import lombok.AllArgsConstructor;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@AllArgsConstructor
public class GetCertificateByPartName implements SearchSpecification {
    private static final String PERCENT = "%";
    private final String name;

    @Override
    public <T> Predicate toPredicate(CriteriaBuilder criteriaBuilder, Root<T> root) {
        return criteriaBuilder.like(
                root.get(FieldName.NAME.getValue()), PERCENT.concat(name).concat(PERCENT)
        );
    }
}
