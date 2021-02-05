package com.epam.esm.utils.generator;

import com.epam.esm.dto.GiftCertificateOrderDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderCertificateQueryGenerator {
    private static final String BASIC_ORDER = "SELECT gc FROM GiftCertificate gc WHERE gc.isDeleted = FALSE AND gc.id IN(";
    private static final String COMMA = ",";
    private static final String QUOTATION = "'";
    private static final String BRACKET = ")";

    public String generate(List<GiftCertificateOrderDTO> certificates) {
        StringBuilder queryBuilder = new StringBuilder(BASIC_ORDER);
        generateQueryOrder(queryBuilder, certificates);
        return queryBuilder.toString();
    }

    private void generateQueryOrder(StringBuilder stringBuilder, List<GiftCertificateOrderDTO> certificates) {
        certificates.forEach(certificate -> stringBuilder.append(QUOTATION).append(certificate.getId())
                .append(QUOTATION).append(COMMA));
        stringBuilder.setLength(stringBuilder.length() - 1);
        stringBuilder.append(BRACKET);
    }
}
