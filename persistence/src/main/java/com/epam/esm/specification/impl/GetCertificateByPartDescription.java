package com.epam.esm.specification.impl;

import com.epam.esm.constant.FieldName;
import com.epam.esm.specification.SearchSpecification;
import lombok.AllArgsConstructor;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@AllArgsConstructor
public class GetCertificateByPartDescription implements SearchSpecification {
    private static final String PERCENT = "%";
    private final String description;

    @Override
    public <T> Predicate toPredicate(CriteriaBuilder criteriaBuilder, Root<T> root) {
        return criteriaBuilder.like(
                root.get(FieldName.DESCRIPTION.getValue()), PERCENT.concat(description).concat(PERCENT)
        );
    }
}
