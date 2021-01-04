package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.exception.ExceptionType;
import com.epam.esm.exception.TagException;
import com.epam.esm.model.SqlRequest;
import com.epam.esm.model.Tag;
import com.epam.esm.model.enums.SQLQuery;
import com.epam.esm.utils.MapSqlParameterSourceCreator;
import com.epam.esm.utils.rowmapper.TagRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * The type Tag dao.
 */
@Repository
@RequiredArgsConstructor
public class TagDAOImpl implements TagDAO {
    private final MapSqlParameterSourceCreator parameterCreator;
    private final TagRowMapper rowMapper;
    private final JdbcTemplate jdbcTemplate;
    @Qualifier("insertTag")
    private final SimpleJdbcInsert insertTag;

    @Override
    public Tag add(Tag tag) {
        MapSqlParameterSource mapSqlParameterSource = parameterCreator.create(tag);
        try {
            Number newId = insertTag.executeAndReturnKey(mapSqlParameterSource);
            tag.setId(newId.longValue());
            return tag;
        } catch (DataAccessException e) {
            throw new TagException(ExceptionType.TAG_NOT_ADDED);
        }
    }

    @Override
    public void add(List<Tag> tags) {
        try {
            int[] rowsInsertedArray = jdbcTemplate.batchUpdate(
                    SQLQuery.BATCH_ADD_TAG.getValue(), new BatchPreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                            preparedStatement.setString(1, tags.get(i).getName());
                        }

                        @Override
                        public int getBatchSize() {
                            return tags.size();
                        }
                    }
            );
            if (Arrays.stream(rowsInsertedArray).filter(rowsInserted -> rowsInserted != 1).count() != 0) {
                throw new TagException(ExceptionType.TAG_NOT_ADDED);
            }
        } catch (DataAccessException e) {
            throw new TagException(ExceptionType.TAG_NOT_ADDED);
        }
    }

    @Override
    public boolean remove(long id) {
        try {
            return jdbcTemplate.update(SQLQuery.REMOVE_TAG_BY_ID.getValue(), id) != 0;
        } catch (DataAccessException e) {
            throw new TagException(ExceptionType.TAG_NOT_DELETED, String.valueOf(id));
        }
    }

    @Override
    public Optional<Tag> findById(long id) {
        try {
            List<Tag> tags = jdbcTemplate.query(SQLQuery.FIND_TAG_BY_ID.getValue(), rowMapper, id);
            return Optional.ofNullable(DataAccessUtils.singleResult(tags));
        } catch (DataAccessException e) {
            throw new TagException(ExceptionType.TAG_NOT_FOUND, String.valueOf(id));
        }
    }

    @Override
    public List<Tag> findByName(SqlRequest sqlRequest) {
        try {
            return jdbcTemplate.query(sqlRequest.getSqlQuery(), rowMapper, sqlRequest.getRequestParameters());
        } catch (DataAccessException e) {
            throw new TagException(ExceptionType.TAG_NOT_FOUND);
        }
    }

    @Override
    public List<Tag> findByCertificateId(long certificateId) {
        try {
            return jdbcTemplate.query(SQLQuery.FIND_TAG_BY_CERTIFICATE_ID.getValue(), rowMapper, certificateId);
        } catch (DataAccessException e) {
            throw new TagException(ExceptionType.TAGS_NOT_FOUND, String.valueOf(certificateId));
        }
    }

    @Override
    public List<Tag> findAll() {
        try {
            return jdbcTemplate.query(SQLQuery.FIND_ALL_TAGS.getValue(), rowMapper);
        } catch (DataAccessException e) {
            throw new TagException(ExceptionType.TAGS_NOT_FOUND);
        }
    }
}
