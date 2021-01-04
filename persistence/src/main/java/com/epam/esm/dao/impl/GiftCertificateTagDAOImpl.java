package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateTagDAO;
import com.epam.esm.entity.SQLQuery;
import com.epam.esm.exception.ExceptionType;
import com.epam.esm.exception.GiftCertificateException;
import com.epam.esm.model.GiftCertificateTag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@Repository
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GiftCertificateTagDAOImpl implements GiftCertificateTagDAO {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void add(List<GiftCertificateTag> giftCertificateTags) {
        try {
            int[] batchArray = jdbcTemplate.batchUpdate(
                    SQLQuery.BATCH_ADD_CERTIFICATE_TAG.getValue(), new BatchPreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                            preparedStatement.setLong(1, giftCertificateTags.get(i).getGiftCertificateId());
                            preparedStatement.setLong(2, giftCertificateTags.get(i).getTagId());
                        }

                        @Override
                        public int getBatchSize() {
                            return giftCertificateTags.size();
                        }
                    }
            );
            if (Arrays.stream(batchArray).filter(x -> x != 1).count() != 0) {
                throw new GiftCertificateException(ExceptionType.CERTIFICATE_TAG_NOT_ADDED);
            }
        } catch (DataAccessException e) {
            throw new GiftCertificateException(ExceptionType.CERTIFICATE_TAG_NOT_ADDED);
        }
    }

    @Override
    public void remove(long certificateId) {
        try {
            jdbcTemplate.update(SQLQuery.REMOVE_CERTIFICATE_TAG.getValue(), certificateId);
        } catch (DataAccessException e) {
            throw new GiftCertificateException(ExceptionType.CERTIFICATE_TAG_NOT_DELETED);
        }
    }
}