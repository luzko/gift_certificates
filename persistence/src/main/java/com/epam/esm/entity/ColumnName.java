package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ColumnName {
    //tag
    TAG("tag"),
    TAG_ID("tag_id"),
    TAG_NAME("tag_name"),

    //gift certificate
    GIFT_CERTIFICATE("gift_certificate"),
    GIFT_CERTIFICATE_ID("gift_certificate_id"),
    GIFT_CERTIFICATE_NAME("gift_certificate_name"),
    DESCRIPTION("description"),
    PRICE("price"),
    DURATION("duration"),
    CREATE_DATE("create_date"),
    LAST_UPDATE_DATE("last_update_date");

    private final String value;
}