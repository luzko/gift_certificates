package com.epam.esm.utils.generator;

import com.epam.esm.dto.TagDTO;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class TagQueryGenerator {
    private static final String BASIC_TAG = "SELECT t FROM Tag t WHERE t.name IN(";
    private static final String COMMA = ",";
    private static final String QUOTATION = "'";
    private static final String BRACKET = ")";

    public String generate(Set<TagDTO> tagDTOs) {
        StringBuilder queryBuilder = new StringBuilder(BASIC_TAG);
        generateQueryTag(queryBuilder, tagDTOs);
        return queryBuilder.toString();
    }

    private void generateQueryTag(StringBuilder stringBuilder, Set<TagDTO> tagDTOs) {
        tagDTOs.forEach(tagDTO -> stringBuilder.append(QUOTATION).append(tagDTO.getName())
                .append(QUOTATION).append(COMMA));
        stringBuilder.setLength(stringBuilder.length() - 1);
        stringBuilder.append(BRACKET);
    }
}
