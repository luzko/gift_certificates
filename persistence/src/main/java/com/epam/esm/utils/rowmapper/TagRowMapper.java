package com.epam.esm.utils.rowmapper;

import com.epam.esm.model.Tag;
import com.epam.esm.model.enums.ColumnName;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TagRowMapper implements RowMapper<Tag> {
    @Override
    public Tag mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Tag tag = new Tag();
        tag.setId(resultSet.getLong(ColumnName.TAG_ID.getValue()));
        tag.setName(resultSet.getString(ColumnName.TAG_NAME.getValue()));
        return tag;
    }
}
