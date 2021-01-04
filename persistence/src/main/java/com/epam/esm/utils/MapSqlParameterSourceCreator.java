package com.epam.esm.utils;

import com.epam.esm.entity.ColumnName;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class MapSqlParameterSourceCreator {
    public MapSqlParameterSource create(Tag tag) {
        return new MapSqlParameterSource()
                .addValue(ColumnName.TAG_ID.getValue(), tag.getId())
                .addValue(ColumnName.TAG_NAME.getValue(), tag.getName());
    }

    public MapSqlParameterSource create(GiftCertificate giftCertificate) {
        Timestamp createDate = giftCertificate.getCreateDate() != null ?
                Timestamp.from(giftCertificate.getCreateDate().toInstant()) : null;
        Timestamp lastUpdateDate = giftCertificate.getLastUpdateDate() != null ?
                Timestamp.from(giftCertificate.getLastUpdateDate().toInstant()) : null;
        return new MapSqlParameterSource()
                .addValue(ColumnName.GIFT_CERTIFICATE_ID.getValue(), giftCertificate.getId())
                .addValue(ColumnName.GIFT_CERTIFICATE_NAME.getValue(), giftCertificate.getName())
                .addValue(ColumnName.DESCRIPTION.getValue(), giftCertificate.getDescription())
                .addValue(ColumnName.PRICE.getValue(), giftCertificate.getPrice())
                .addValue(ColumnName.DURATION.getValue(), giftCertificate.getDuration())
                .addValue(ColumnName.CREATE_DATE.getValue(), createDate)
                .addValue(ColumnName.LAST_UPDATE_DATE.getValue(), lastUpdateDate);
    }
}