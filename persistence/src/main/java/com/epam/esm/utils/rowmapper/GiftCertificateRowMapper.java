package com.epam.esm.utils.rowmapper;

import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.enums.ColumnName;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
public class GiftCertificateRowMapper implements RowMapper<GiftCertificate> {
    @Override
    public GiftCertificate mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(resultSet.getLong(ColumnName.GIFT_CERTIFICATE_ID.getValue()));
        giftCertificate.setName(resultSet.getString(ColumnName.GIFT_CERTIFICATE_NAME.getValue()));
        giftCertificate.setDescription(resultSet.getString(ColumnName.DESCRIPTION.getValue()));
        giftCertificate.setPrice(resultSet.getBigDecimal(ColumnName.PRICE.getValue()));
        giftCertificate.setDuration(resultSet.getInt(ColumnName.DURATION.getValue()));
        giftCertificate.setCreateDate(ZonedDateTime.ofInstant(resultSet.getTimestamp(ColumnName.CREATE_DATE.getValue())
                .toInstant(), ZoneId.systemDefault()));
        giftCertificate.setLastUpdateDate(ZonedDateTime.ofInstant(resultSet.getTimestamp(ColumnName.LAST_UPDATE_DATE.
                getValue()).toInstant(), ZoneId.systemDefault()));
        return giftCertificate;
    }
}
