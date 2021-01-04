package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SQLQuery {
    //tag
    BATCH_ADD_TAG("INSERT INTO tag(tag_name) VALUES(?)"),
    REMOVE_TAG_BY_ID("DELETE FROM tag WHERE tag_id=?"),
    FIND_TAG_BY_ID("SELECT tag_id, tag_name FROM tag WHERE tag_id = ?"),
    FIND_TAG_BY_NAME("SELECT tag_id, tag_name FROM tag WHERE tag_name = ?"),
    FIND_TAG_BY_CERTIFICATE_ID("SELECT tag_id, tag_name FROM tag JOIN gift_certificate_tag gct " +
            "ON tag.tag_id = gct.tag_id_fk WHERE gct.gift_certificate_id_fk = ?"),
    FIND_ALL_TAGS("SELECT tag_id, tag_name FROM tag"),

    //gift certificate tag
    BATCH_ADD_CERTIFICATE_TAG("INSERT INTO gift_certificate_tag(gift_certificate_id_fk, tag_id_fk) VALUES(?, ?)"),
    REMOVE_CERTIFICATE_TAG("DELETE FROM gift_certificate_tag WHERE gift_certificate_id_fk = ?"),

    //gift certificate
    REMOVE_CERTIFICATE_BY_ID("DELETE FROM gift_certificate WHERE gift_certificate_id = ?"),
    UPDATE_CERTIFICATE("UPDATE gift_certificate SET gift_certificate_name = case when " +
            ":gift_certificate_name is not null then :gift_certificate_name else gift_certificate_name end, " +
            "description = case when :description is not null then :description else description end, " +
            "price = case when :price is not null then :price else price end, duration = case when :duration is not" +
            " null then :duration else duration end, last_update_date = case when :last_update_date is not null then " +
            ":last_update_date end where gift_certificate_id=:gift_certificate_id"),
    FIND_CERTIFICATE_BY_ID("SELECT gift_certificate_id, gift_certificate_name, description, " +
            "price, duration, create_date, last_update_date FROM gift_certificate WHERE gift_certificate_id = ?");

    private final String value;
}