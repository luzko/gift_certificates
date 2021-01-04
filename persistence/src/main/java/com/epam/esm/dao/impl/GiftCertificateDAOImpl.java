package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.exception.ExceptionType;
import com.epam.esm.exception.GiftCertificateException;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.SqlRequest;
import com.epam.esm.model.enums.SQLQuery;
import com.epam.esm.utils.MapSqlParameterSourceCreator;
import com.epam.esm.utils.rowmapper.GiftCertificateRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The type Gift certificate dao.
 */
@Repository
@RequiredArgsConstructor
public class GiftCertificateDAOImpl implements GiftCertificateDAO {
    private final MapSqlParameterSourceCreator parameterCreator;
    private final GiftCertificateRowMapper rowMapper;
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Qualifier("insertCertificate")
    private final SimpleJdbcInsert insertCertificate;

    @Override
    public GiftCertificate add(GiftCertificate giftCertificate) {
        MapSqlParameterSource mapSqlParameterSource = parameterCreator.create(giftCertificate);
        try {
            Number newId = insertCertificate.executeAndReturnKey(mapSqlParameterSource);
            giftCertificate.setId(newId.longValue());
            return giftCertificate;
        } catch (DataAccessException e) {
            throw new GiftCertificateException(ExceptionType.CERTIFICATE_NOT_ADDED);
        }
    }

    @Override
    public Optional<GiftCertificate> update(GiftCertificate giftCertificate) {
        MapSqlParameterSource mapSqlParameterSource = parameterCreator.create(giftCertificate);
        try {
            if (namedParameterJdbcTemplate.update(SQLQuery.UPDATE_CERTIFICATE.getValue(), mapSqlParameterSource) == 0) {
                throw new GiftCertificateException(ExceptionType.CERTIFICATE_NOT_UPDATED);
            }
            return findById(giftCertificate.getId());
        } catch (DataAccessException e) {
            throw new GiftCertificateException(ExceptionType.CERTIFICATE_NOT_UPDATED);
        }
    }

    @Override
    public boolean remove(long id) {
        try {
            return jdbcTemplate.update(SQLQuery.REMOVE_CERTIFICATE_BY_ID.getValue(), id) != 0;
        } catch (DataAccessException e) {
            throw new GiftCertificateException(ExceptionType.CERTIFICATE_NOT_DELETED, String.valueOf(id));
        }
    }

    @Override
    public Optional<GiftCertificate> findById(long id) {
        try {
            List<GiftCertificate> giftCertificates = jdbcTemplate.query(SQLQuery.FIND_CERTIFICATE_BY_ID.getValue(),
                    rowMapper, id);
            return Optional.ofNullable(DataAccessUtils.singleResult(giftCertificates));
        } catch (DataAccessException e) {
            throw new GiftCertificateException(ExceptionType.CERTIFICATE_NOT_FOUND, String.valueOf(id));
        }
    }

    @Override
    public List<GiftCertificate> findAll(SqlRequest sqlRequest) {
        try {
            return jdbcTemplate.query(sqlRequest.getSqlQuery(), rowMapper, sqlRequest.getRequestParameters());
        } catch (DataAccessException e) {
            throw new GiftCertificateException(ExceptionType.CERTIFICATES_NOT_FOUND);
        }
    }
}
