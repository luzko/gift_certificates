package com.epam.esm.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public interface SearchSpecification extends Specification {
    <T> Predicate toPredicate(CriteriaBuilder criteriaBuilder, Root<T> root);
}
