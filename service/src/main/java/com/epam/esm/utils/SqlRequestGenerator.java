package com.epam.esm.utils;

import com.epam.esm.entity.SqlRequest;
import com.epam.esm.model.Tag;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class SqlRequestGenerator {
    private static final String TAG = "tag";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String SORT_DATE = "sortDate";
    private static final String SORT_NAME = "sortName";
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
    private static final String COMMA = ", ";
    private static final String QUESTION = "? ,";
    private static final String BRACKET = ")";

    private String tagParam;
    private String nameParam;
    private String descriptionParam;
    private String sortDate;
    private String sortName;
    private List<String> queryParameters;

    public SqlRequest generateForCertificate(Map<String, String> parameters) {
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
        String sortDateParam = parameters.get(SORT_DATE);
        this.sortDate = ASC.equals(sortDateParam) || DESC.equals(sortDateParam) ? sortDateParam.toUpperCase() : null;
        String sortNameParam = parameters.get(SORT_NAME);
        this.sortName = ASC.equals(sortNameParam) || DESC.equals(sortNameParam) ? sortNameParam.toUpperCase() : null;
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
        if (sortDate != null) {
            stringBuilder.append(ORDER_BY)
                    .append(ORDER_BY_DATE)
                    .append(sortDate);
        }
        if (sortName != null) {
            if (stringBuilder.indexOf(ORDER_BY) == -1) {
                stringBuilder.append(ORDER_BY);
            } else {
                stringBuilder.append(COMMA);
            }
            stringBuilder.append(ORDER_BY_NAME)
                    .append(sortName);
        }
    }

    private void appendCondition(StringBuilder stringBuilder) {
        if (stringBuilder.indexOf(WHERE) == -1) {
            stringBuilder.append(WHERE);
        } else {
            stringBuilder.append(AND);
        }
    }
}
