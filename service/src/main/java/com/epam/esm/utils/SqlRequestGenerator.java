package com.epam.esm.utils;

import com.epam.esm.model.SqlRequest;
import com.epam.esm.model.Tag;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedCaseInsensitiveMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class SqlRequestGenerator {
    private static final String TAG = "tag";
    private static final String NAME = "name";
    private static final String DATE = "date";
    private static final String DESCRIPTION = "description";
    private static final String SORT = "sort";
    private static final String SORT_ORDER = "sortOrder";
    private static final String BASIC_CERTIFICATE = "SELECT gc.gift_certificate_id, gc.gift_certificate_name, " +
            "gc.description, gc.price, gc.duration, gc.create_date, gc.last_update_date FROM gift_certificate gc ";
    private static final String BASIC_TAG = "SELECT tag_id, tag_name FROM tag WHERE tag_name IN(";
    private static final String JOIN_TAG = "JOIN gift_certificate_tag gct ON gc.gift_certificate_id = " +
            "gct.gift_certificate_id_fk JOIN tag t ON gct.tag_id_fk = t.tag_id WHERE t.tag_name = ? ";
    private static final String WHERE = "WHERE ";
    private static final String AND = "AND ";
    private static final String BY_NAME = "gc.gift_certificate_name LIKE CONCAT('%', ?, '%') ";
    private static final String BY_DESCRIPTION = "gc.description LIKE CONCAT('%', ?, '%') ";
    private static final String ORDER_BY = "ORDER BY ";
    private static final String ORDER_BY_NAME = "gc.gift_certificate_name ";
    private static final String ORDER_BY_DATE = "gc.create_date ";
    private static final String ASC = "ASC";
    private static final String DESC = "DESC";
    private static final String COMMA = ",";
    private static final String QUESTION = "? ,";
    private static final String BRACKET = ")";

    private String tagParam;
    private String nameParam;
    private String descriptionParam;
    private String sortParam;
    private String sortOrderParam;
    private List<String> queryParameters;

    public SqlRequest generateForCertificate(Map<String, String> params) {
        Map<String, String> parameters = new LinkedCaseInsensitiveMap<>();
        parameters.putAll(params);
        initializeParameters(parameters);
        StringBuilder queryBuilder = new StringBuilder(BASIC_CERTIFICATE);
        buildQueryCertificate(queryBuilder);
        return new SqlRequest(queryBuilder.toString(), queryParameters.toArray());
    }

    public SqlRequest generateForTag(List<Tag> tags) {
        this.queryParameters = new ArrayList<>();
        StringBuilder queryBuilder = new StringBuilder(BASIC_TAG);
        buildQueryTag(queryBuilder, tags);
        return new SqlRequest(queryBuilder.toString(), queryParameters.toArray());
    }

    private void initializeParameters(Map<String, String> parameters) {
        this.tagParam = parameters.get(TAG);
        this.nameParam = parameters.get(NAME);
        this.descriptionParam = parameters.get(DESCRIPTION);
        this.sortParam = parameters.get(SORT);
        String order = parameters.get(SORT_ORDER);
        this.sortOrderParam = ASC.equalsIgnoreCase(order) || DESC.equalsIgnoreCase(order) ? order.toUpperCase() : "";
        this.queryParameters = new ArrayList<>();
    }

    private void buildQueryCertificate(StringBuilder stringBuilder) {
        appendTag(stringBuilder);
        appendSearch(stringBuilder);
        appendSort(stringBuilder);
    }

    private void buildQueryTag(StringBuilder stringBuilder, List<Tag> tags) {
        for (Tag tag : tags) {
            stringBuilder.append(QUESTION);
            queryParameters.add(tag.getName());
        }
        stringBuilder.setLength(stringBuilder.length() - 1);
        stringBuilder.append(BRACKET);
    }

    private void appendTag(StringBuilder stringBuilder) {
        if (tagParam != null) {
            stringBuilder.append(JOIN_TAG);
            queryParameters.add(tagParam);
        }
    }

    private void appendSearch(StringBuilder stringBuilder) {
        if (nameParam != null) {
            appendCondition(stringBuilder);
            stringBuilder.append(BY_NAME);
            queryParameters.add(nameParam);
        }
        if (descriptionParam != null) {
            appendCondition(stringBuilder);
            stringBuilder.append(BY_DESCRIPTION);
            queryParameters.add(descriptionParam);
        }
    }

    private void appendSort(StringBuilder stringBuilder) {
        if (sortParam != null) {
            List<String> sortParams = Arrays.asList(sortParam.toLowerCase().split(COMMA));
            if (sortParams.contains(DATE)) {
                stringBuilder.append(ORDER_BY).append(ORDER_BY_DATE).append(sortOrderParam);
            }
            if (sortParams.contains(NAME)) {
                stringBuilder.append(stringBuilder.indexOf(ORDER_BY) == -1 ? ORDER_BY : COMMA)
                        .append(ORDER_BY_NAME).append(sortOrderParam);
            }
        }
    }

    private void appendCondition(StringBuilder stringBuilder) {
        stringBuilder.append(stringBuilder.indexOf(WHERE) == -1 ? WHERE : AND);
    }
}
