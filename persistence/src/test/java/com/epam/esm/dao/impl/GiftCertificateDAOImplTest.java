package com.epam.esm.dao.impl;

import com.epam.esm.GiftCertificateTestData;
import com.epam.esm.configuration.PersistenceTestConfig;
import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.entity.SqlRequest;
import com.epam.esm.model.GiftCertificate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@ContextConfiguration(classes = PersistenceTestConfig.class)
@Sql(scripts = {"classpath:database/populateDB_H2.sql"}, config = @SqlConfig(encoding = "UTF-8"))
@ExtendWith(SpringExtension.class)
class GiftCertificateDAOImplTest {
    @Autowired
    private GiftCertificateDAO giftCertificateDAO;

    @Test
    void addPositiveTest() {
        GiftCertificate inputGiftCertificate = GiftCertificateTestData.defineNew();
        GiftCertificate actualGiftCertificate = giftCertificateDAO.add(inputGiftCertificate);
        assertEquals(5, actualGiftCertificate.getId());
    }

    @Test
    void addNegativeTest() {
        GiftCertificate inputGiftCertificate = GiftCertificateTestData.defineNew();
        GiftCertificate actualGiftCertificate = giftCertificateDAO.add(inputGiftCertificate);
        assertNotEquals(10, actualGiftCertificate.getId());
    }

    @Test
    void updatePositiveTest() {
        GiftCertificate inputGiftCertificate = GiftCertificateTestData.defineUpdate();
        Optional<GiftCertificate> actualGiftCertificate = giftCertificateDAO.update(inputGiftCertificate);
        if (actualGiftCertificate.isPresent()) {
            assertEquals(GiftCertificateTestData.findUpdated(), actualGiftCertificate.get());
        } else {
            fail();
        }
    }

    @Test
    void updateNegativeTest() {
        GiftCertificate inputGiftCertificate = GiftCertificateTestData.defineUpdate();
        Optional<GiftCertificate> actualGiftCertificate = giftCertificateDAO.update(inputGiftCertificate);
        if (actualGiftCertificate.isPresent()) {
            assertNotEquals(GiftCertificateTestData.CERTIFICATE3, actualGiftCertificate.get());
        } else {
            fail();
        }
    }

    @Test
    void removePositiveTest() {
        long inputId = GiftCertificateTestData.CERTIFICATE_ID;
        boolean actual = giftCertificateDAO.remove(inputId);
        assertTrue(actual);
    }

    @Test
    void removeNegativeTest() {
        long inputId = GiftCertificateTestData.NOT_FOUND;
        boolean actual = giftCertificateDAO.remove(inputId);
        assertFalse(actual);
    }

    @Test
    void findByIdPositiveTest() {
        long inputId = GiftCertificateTestData.CERTIFICATE_ID;
        Optional<GiftCertificate> actualGiftCertificate = giftCertificateDAO.findById(inputId);
        if (actualGiftCertificate.isPresent()) {
            assertEquals(GiftCertificateTestData.CERTIFICATE1, actualGiftCertificate.get());
        } else {
            fail();
        }
    }

    @Test
    void findByIdNegativeTest() {
        long inputId = GiftCertificateTestData.CERTIFICATE_ID;
        Optional<GiftCertificate> actualGiftCertificate = giftCertificateDAO.findById(inputId);
        if (actualGiftCertificate.isPresent()) {
            assertNotEquals(GiftCertificateTestData.CERTIFICATE2, actualGiftCertificate.get());
        } else {
            fail();
        }
    }

    @Test
    void findAllPositiveTest() {
        Object[] input = new Object[0];
        SqlRequest request = new SqlRequest(GiftCertificateTestData.BASIC_QUERY, input);
        List<GiftCertificate> actualGiftCertificates = giftCertificateDAO.findAll(request);
        assertEquals(GiftCertificateTestData.GIFT_CERTIFICATES_ALL, actualGiftCertificates);
    }

    @Test
    void findAllNegativeTest() {
        Object[] input = new Object[0];
        SqlRequest request = new SqlRequest(GiftCertificateTestData.BASIC_QUERY, input);
        List<GiftCertificate> actualGiftCertificates = giftCertificateDAO.findAll(request);
        assertNotEquals(GiftCertificateTestData.GIFT_CERTIFICATES_NEGATIVE, actualGiftCertificates);
    }

    @Test
    void findByNamePositiveTest() {
        Object[] input = {"rent car"};
        SqlRequest request = new SqlRequest(GiftCertificateTestData.FIND_BY_NAME, input);
        List<GiftCertificate> actualGiftCertificates = giftCertificateDAO.findAll(request);
        assertEquals(Collections.singletonList(GiftCertificateTestData.CERTIFICATE4), actualGiftCertificates);
    }

    @Test
    void findByNameNegativeTest() {
        Object[] input = {"rent car"};
        SqlRequest request = new SqlRequest(GiftCertificateTestData.FIND_BY_NAME, input);
        List<GiftCertificate> actualGiftCertificates = giftCertificateDAO.findAll(request);
        assertNotEquals(Collections.singletonList(GiftCertificateTestData.CERTIFICATE3), actualGiftCertificates);
    }

    @Test
    void findByNameSortASCPositiveTest() {
        Object[] input = new Object[0];
        SqlRequest request = new SqlRequest(GiftCertificateTestData.FIND_BY_ORDER_BY_NAME_ASC, input);
        List<GiftCertificate> actualGiftCertificates = giftCertificateDAO.findAll(request);
        assertEquals(GiftCertificateTestData.GIFT_CERTIFICATES_NAME_ASC, actualGiftCertificates);
    }

    @Test
    void findByNameSortASCNegativeTest() {
        Object[] input = new Object[0];
        SqlRequest request = new SqlRequest(GiftCertificateTestData.FIND_BY_ORDER_BY_NAME_ASC, input);
        List<GiftCertificate> actualGiftCertificates = giftCertificateDAO.findAll(request);
        assertNotEquals(GiftCertificateTestData.GIFT_CERTIFICATES_NEGATIVE, actualGiftCertificates);
    }

    @Test
    void findByDateSortDESCPositiveTest() {
        Object[] input = new Object[0];
        SqlRequest request = new SqlRequest(GiftCertificateTestData.FIND_BY_ORDER_BY_DATE_DESC, input);
        List<GiftCertificate> actualGiftCertificates = giftCertificateDAO.findAll(request);
        assertEquals(GiftCertificateTestData.GIFT_CERTIFICATES_DATE_DESC, actualGiftCertificates);
    }

    @Test
    void findByDateSortDESCNegativeTest() {
        Object[] input = new Object[0];
        SqlRequest request = new SqlRequest(GiftCertificateTestData.FIND_BY_ORDER_BY_DATE_DESC, input);
        List<GiftCertificate> actualGiftCertificates = giftCertificateDAO.findAll(request);
        assertNotEquals(GiftCertificateTestData.GIFT_CERTIFICATES_NEGATIVE, actualGiftCertificates);
    }
}