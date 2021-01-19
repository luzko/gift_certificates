package com.epam.esm.specification.impl;

import com.epam.esm.constant.FieldName;
import com.epam.esm.specification.SearchSpecification;
import lombok.AllArgsConstructor;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@AllArgsConstructor
public class GetCertificateByTagName implements SearchSpecification {
    private final String tagName;

    @Override
    public <T> Predicate toPredicate(CriteriaBuilder criteriaBuilder, Root<T> root) {
        return criteriaBuilder.like(
                root.join(FieldName.TAGS.getValue()).get(FieldName.NAME.getValue()), tagName
        );
    }
}
