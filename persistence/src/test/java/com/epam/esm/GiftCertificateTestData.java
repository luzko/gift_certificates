package com.epam.esm;

import com.epam.esm.model.GiftCertificate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;

import static java.time.ZonedDateTime.of;

public class GiftCertificateTestData {
    public static final long CERTIFICATE_ID = 1;
    public static final int NOT_FOUND = 10;

    public static final GiftCertificate CERTIFICATE1 = new GiftCertificate(
            CERTIFICATE_ID, "spa", "any spa procedure", BigDecimal.valueOf(50.55),
            of(LocalDateTime.of(2020, Month.JANUARY, 10, 21, 0), ZoneId.systemDefault()),
            of(LocalDateTime.of(2020, Month.JANUARY, 14, 22, 4), ZoneId.systemDefault()),
            10
    );

    public static final GiftCertificate CERTIFICATE2 = new GiftCertificate(
            CERTIFICATE_ID + 1, "bowling", "bowling game for two", BigDecimal.valueOf(30.55),
            of(LocalDateTime.of(2020, Month.FEBRUARY, 10, 18, 16), ZoneId.systemDefault()),
            of(LocalDateTime.of(2020, Month.MARCH, 14, 16, 4), ZoneId.systemDefault()),
            14
    );

    public static final GiftCertificate CERTIFICATE3 = new GiftCertificate(
            CERTIFICATE_ID + 2, "football", "football match for friends", BigDecimal.valueOf(88.55),
            of(LocalDateTime.of(2020, Month.MARCH, 11, 17, 0), ZoneId.systemDefault()),
            of(LocalDateTime.of(2020, Month.JUNE, 14, 22, 11), ZoneId.systemDefault()),
            7
    );

    public static final GiftCertificate CERTIFICATE4 = new GiftCertificate(
            CERTIFICATE_ID + 3, "rent car", "discount rent car", BigDecimal.valueOf(24.55),
            of(LocalDateTime.of(2020, Month.JUNE, 3, 21, 47), ZoneId.systemDefault()),
            of(LocalDateTime.of(2020, Month.SEPTEMBER, 14, 22, 44), ZoneId.systemDefault()),
            24
    );

    public static final List<GiftCertificate> GIFT_CERTIFICATES_ALL = Arrays.asList(
            CERTIFICATE1, CERTIFICATE2, CERTIFICATE3, CERTIFICATE4
    );

    public static final List<GiftCertificate> GIFT_CERTIFICATES_NAME_ASC = Arrays.asList(
            CERTIFICATE2, CERTIFICATE3, CERTIFICATE4, CERTIFICATE1
    );

    public static final List<GiftCertificate> GIFT_CERTIFICATES_DATE_DESC = Arrays.asList(
            CERTIFICATE4, CERTIFICATE3, CERTIFICATE2, CERTIFICATE1
    );

    public static final List<GiftCertificate> GIFT_CERTIFICATES_NEGATIVE = Arrays.asList(
            CERTIFICATE2, CERTIFICATE3, CERTIFICATE4
    );
    public static final String BASIC_QUERY = "SELECT gc.gift_certificate_id, gc.gift_certificate_name, gc.description," +
            " gc.price, gc.duration, gc.create_date, gc.last_update_date FROM gift_certificate gc ";
    private static final String BY_NAME = "WHERE gc.gift_certificate_name LIKE CONCAT('%', ?, '%') ";
    public static final String FIND_BY_NAME = BASIC_QUERY + BY_NAME;
    private static final String ORDER_BY_NAME_ASC = "ORDER BY gc.gift_certificate_name ASC";
    public static final String FIND_BY_ORDER_BY_NAME_ASC = BASIC_QUERY + ORDER_BY_NAME_ASC;
    private static final String ORDER_BY_DATE_DESC = "ORDER BY gc.create_date DESC";
    public static final String FIND_BY_ORDER_BY_DATE_DESC = BASIC_QUERY + ORDER_BY_DATE_DESC;

    public static GiftCertificate defineNew() {
        return new GiftCertificate(
                null, "new certificate", "description for new certificate", BigDecimal.valueOf(11.11),
                CERTIFICATE1.getCreateDate(), CERTIFICATE3.getLastUpdateDate(), 15
        );
    }

    public static GiftCertificate defineUpdate() {
        return new GiftCertificate(
                CERTIFICATE1.getId(), "updated certificate name", null, null,
                null, CERTIFICATE1.getLastUpdateDate(), 22
        );
    }

    public static GiftCertificate findUpdated() {
        return new GiftCertificate(
                CERTIFICATE1.getId(), "updated certificate name", CERTIFICATE1.getDescription(), CERTIFICATE1.getPrice(),
                CERTIFICATE1.getCreateDate(), CERTIFICATE1.getLastUpdateDate(), 22
        );
    }
}
