package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.GiftCertificateTagDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.GiftCertificatePatchDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.GiftCertificateException;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.mapper.impl.GiftCertificateMapperImpl;
import com.epam.esm.mapper.impl.TagMapperImpl;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.GiftCertificateTag;
import com.epam.esm.model.SqlRequest;
import com.epam.esm.model.Tag;
import com.epam.esm.utils.SqlRequestGenerator;
import com.epam.esm.validation.AppValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.util.LinkedCaseInsensitiveMap;

import javax.validation.Validation;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static java.time.ZonedDateTime.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {
    @Spy
    private final SqlRequestGenerator sqlRequestGenerator = new SqlRequestGenerator();
    @Spy
    private final GiftCertificateMapper giftCertificateMapper = new GiftCertificateMapperImpl(new ModelMapper());
    @Spy
    private final TagMapper tagMapper = new TagMapperImpl(new ModelMapper());
    @Spy
    private final AppValidator validator = new AppValidator(Validation.buildDefaultValidatorFactory().getValidator());
    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateService;
    @Mock
    private GiftCertificateDAO giftCertificateDAO;
    @Mock
    private GiftCertificateTagDAO giftCertificateTagDAO;
    @Mock
    private TagDAO tagDAO;

    @Test
    void addPositiveTest() {
        TagDTO inputTag = new TagDTO("#newtag");
        Tag tag = new Tag(1L, "#newtag");
        TagDTO expectedTag = new TagDTO(1L, "#newtag");
        List<TagDTO> inputTags = Collections.singletonList(inputTag);
        GiftCertificateDTO inputCertificate = new GiftCertificateDTO(
                "newCertificate", "newDescription", BigDecimal.valueOf(5), 10, inputTags
        );
        GiftCertificate certificate = new GiftCertificate(
                1L, "newCertificate", "newDescription", BigDecimal.valueOf(5),
                of(LocalDateTime.of(2020, Month.JANUARY, 10, 21, 0), ZoneId.systemDefault()),
                of(LocalDateTime.of(2020, Month.JANUARY, 14, 22, 4), ZoneId.systemDefault()),
                10
        );
        GiftCertificateDTO expectedCertificate = giftCertificateMapper.toDto(certificate,
                Collections.singletonList(expectedTag));
        GiftCertificateTag giftCertificateTag = new GiftCertificateTag(1L, 1L);
        Mockito.when(giftCertificateDAO.add(Mockito.any(GiftCertificate.class))).thenReturn(certificate);
        Mockito.when(tagDAO.findByName(Mockito.any(SqlRequest.class))).thenReturn(Collections.singletonList(tag));
        Mockito.doNothing().when(giftCertificateTagDAO).add(Collections.singletonList(giftCertificateTag));
        Mockito.when(tagDAO.findByCertificateId(1L)).thenReturn(Collections.singletonList(tag));
        GiftCertificateDTO actualCertificate = giftCertificateService.add(inputCertificate);
        assertEquals(expectedCertificate, actualCertificate);
    }

    @Test
    void addNegativeTest() {
        TagDTO inputTag = new TagDTO("#newtag12345");
        Tag tag = new Tag(1L, "#newtag1234");
        TagDTO expectedTag = new TagDTO(1L, "#newtag123");
        List<TagDTO> inputTags = Collections.singletonList(inputTag);
        GiftCertificateDTO inputCertificate = new GiftCertificateDTO(
                "newCertificate", "newDescription", BigDecimal.valueOf(5), 10, inputTags
        );
        GiftCertificate certificate = new GiftCertificate(
                1L, "newCertificate", "newDescription", BigDecimal.valueOf(5),
                of(LocalDateTime.of(2020, Month.JANUARY, 10, 21, 0), ZoneId.systemDefault()),
                of(LocalDateTime.of(2020, Month.JANUARY, 14, 22, 4), ZoneId.systemDefault()),
                10
        );
        GiftCertificateDTO expectedCertificate = giftCertificateMapper.toDto(certificate,
                Collections.singletonList(expectedTag));
        List<Tag> tags = new ArrayList<>();
        tags.add(tag);
        GiftCertificateTag giftCertificateTag = new GiftCertificateTag(1L, 1L);
        Mockito.when(giftCertificateDAO.add(Mockito.any(GiftCertificate.class))).thenReturn(certificate);
        Mockito.when(tagDAO.findByName(Mockito.any(SqlRequest.class))).thenReturn(tags);
        Mockito.doNothing().when(giftCertificateTagDAO).add(Arrays.asList(giftCertificateTag, giftCertificateTag));
        Mockito.when(tagDAO.findByCertificateId(1L)).thenReturn(tags);
        GiftCertificateDTO actualCertificate = giftCertificateService.add(inputCertificate);
        assertNotEquals(expectedCertificate, actualCertificate);
    }

    @Test
    void addExceptionTest() {
        GiftCertificateDTO inputCertificate = new GiftCertificateDTO(
                "newCertificate", "newDescription", BigDecimal.valueOf(-5), -55, new ArrayList<>()
        );
        assertThrows(GiftCertificateException.class, () -> giftCertificateService.add(inputCertificate));
    }

    @Test
    void updatePositiveTest() {
        GiftCertificatePatchDTO inputCertificate = new GiftCertificatePatchDTO(
                "newCertificate111", "newDescription111", BigDecimal.valueOf(24), 16, new ArrayList<>()
        );
        GiftCertificate certificate = new GiftCertificate(
                1L, "newCertificate", "newDescription", BigDecimal.valueOf(5),
                of(LocalDateTime.of(2020, Month.JANUARY, 10, 21, 0), ZoneId.systemDefault()),
                of(LocalDateTime.of(2020, Month.JANUARY, 14, 22, 4), ZoneId.systemDefault()),
                10
        );
        GiftCertificateDTO expectedCertificate = giftCertificateMapper.toDto(certificate, new ArrayList<>());
        Mockito.when(giftCertificateDAO.update(Mockito.any(GiftCertificate.class))).thenReturn(Optional.of(certificate));
        Mockito.when(tagDAO.findByName(Mockito.any(SqlRequest.class))).thenReturn(new ArrayList<>());
        Mockito.doNothing().when(giftCertificateTagDAO).add(new ArrayList<>());
        Mockito.when(tagDAO.findByCertificateId(1L)).thenReturn(new ArrayList<>());
        GiftCertificateDTO actualCertificate = giftCertificateService.update(1L, inputCertificate);
        assertEquals(expectedCertificate, actualCertificate);
    }

    @Test
    void updateNegativeTest() {
        GiftCertificatePatchDTO inputCertificate = new GiftCertificatePatchDTO(
                "newCertificate111", "newDescription111", BigDecimal.valueOf(24), 16, new ArrayList<>()
        );
        GiftCertificate certificate1 = new GiftCertificate(
                1L, "newCertificate", "newDescription", BigDecimal.valueOf(5),
                of(LocalDateTime.of(2020, Month.JANUARY, 10, 21, 0), ZoneId.systemDefault()),
                of(LocalDateTime.of(2020, Month.JANUARY, 14, 22, 4), ZoneId.systemDefault()),
                10
        );
        GiftCertificate certificate2 = new GiftCertificate(
                2L, "asdfasdf", "qwerqwer", BigDecimal.valueOf(6),
                of(LocalDateTime.of(2020, Month.APRIL, 1, 14, 14), ZoneId.systemDefault()),
                of(LocalDateTime.of(2020, Month.APRIL, 12, 15, 46), ZoneId.systemDefault()),
                11
        );
        GiftCertificateDTO expectedCertificate = giftCertificateMapper.toDto(certificate1, new ArrayList<>());
        Mockito.when(giftCertificateDAO.update(Mockito.any(GiftCertificate.class))).thenReturn(Optional.of(certificate2));
        Mockito.when(tagDAO.findByName(Mockito.any(SqlRequest.class))).thenReturn(new ArrayList<>());
        Mockito.doNothing().when(giftCertificateTagDAO).add(new ArrayList<>());
        Mockito.when(tagDAO.findByCertificateId(Mockito.anyLong())).thenReturn(new ArrayList<>());
        GiftCertificateDTO actualCertificate = giftCertificateService.update(1L, inputCertificate);
        assertNotEquals(expectedCertificate, actualCertificate);
    }

    @Test
    void updateExceptionTest() {
        GiftCertificateDTO inputCertificate = new GiftCertificateDTO(
                null, "newDescription", BigDecimal.valueOf(-5), -55, new ArrayList<>()
        );
        assertThrows(GiftCertificateException.class, () -> giftCertificateService.add(inputCertificate));
    }

    @Test
    void removePositiveTest() {
        long inputId = 1;
        Mockito.when(giftCertificateDAO.remove(Mockito.anyLong())).thenReturn(true);
        giftCertificateService.remove(inputId);
        Mockito.verify(giftCertificateDAO).remove(inputId);
    }

    @Test
    void removeExceptionTest() {
        long inputId = 1;
        Mockito.when(giftCertificateDAO.remove(Mockito.anyLong())).thenReturn(false);
        assertThrows(GiftCertificateException.class, () -> giftCertificateService.remove(inputId));
    }

    @Test
    void findByIdPositiveTest() {
        long inputId = 1;
        GiftCertificate certificate = new GiftCertificate(
                1L, "newCertificate", "newDescription", BigDecimal.valueOf(5),
                of(LocalDateTime.of(2020, Month.JANUARY, 10, 21, 0), ZoneId.systemDefault()),
                of(LocalDateTime.of(2020, Month.JANUARY, 14, 22, 4), ZoneId.systemDefault()),
                10
        );
        GiftCertificateDTO expectedCertificate = giftCertificateMapper.toDto(certificate, new ArrayList<>());
        Mockito.when(giftCertificateDAO.findById(Mockito.anyLong())).thenReturn(Optional.of(certificate));
        GiftCertificateDTO actualCertificate = giftCertificateService.findById(inputId);
        assertEquals(expectedCertificate, actualCertificate);

    }

    @Test
    void findByIdNegativeTest() {
        long inputId = 1;
        GiftCertificate certificate = new GiftCertificate(
                1L, "newCertificate1234", "newDescription1234", BigDecimal.valueOf(5),
                of(LocalDateTime.of(2020, Month.JANUARY, 10, 21, 0), ZoneId.systemDefault()),
                of(LocalDateTime.of(2020, Month.JANUARY, 14, 22, 4), ZoneId.systemDefault()),
                10
        );
        GiftCertificateDTO expectedCertificate = giftCertificateMapper.toDto(certificate, null);
        Mockito.when(giftCertificateDAO.findById(Mockito.anyLong())).thenReturn(Optional.of(certificate));
        GiftCertificateDTO actualCertificate = giftCertificateService.findById(inputId);
        assertNotEquals(expectedCertificate, actualCertificate);
    }

    @Test
    void findByIdExceptionTest() {
        long inputId = 1;
        Mockito.when(giftCertificateDAO.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        assertThrows(GiftCertificateException.class, () -> giftCertificateService.findById(inputId));
    }

    @Test
    void findAllPositiveTest() {
        GiftCertificate certificate1 = new GiftCertificate(
                1L, "newCertificate1234", "newDescription1234", BigDecimal.valueOf(5),
                of(LocalDateTime.of(2020, Month.JANUARY, 10, 21, 0), ZoneId.systemDefault()),
                of(LocalDateTime.of(2020, Month.JANUARY, 14, 22, 4), ZoneId.systemDefault()),
                10
        );
        GiftCertificate certificate2 = new GiftCertificate(
                2L, "asdfasdfasdf", "asdfasdfasdf", BigDecimal.valueOf(7),
                of(LocalDateTime.of(2020, Month.FEBRUARY, 11, 12, 0), ZoneId.systemDefault()),
                of(LocalDateTime.of(2020, Month.MARCH, 12, 11, 41), ZoneId.systemDefault()),
                11
        );
        List<GiftCertificate> giftCertificates = Arrays.asList(certificate1, certificate2);
        GiftCertificateDTO expectedCertificate1 = giftCertificateMapper.toDto(certificate1, new ArrayList<>());
        GiftCertificateDTO expectedCertificate2 = giftCertificateMapper.toDto(certificate2, new ArrayList<>());
        List<GiftCertificateDTO> expectedGiftCertificates = Arrays.asList(expectedCertificate1, expectedCertificate2);
        Mockito.when(giftCertificateDAO.findAll(Mockito.any())).thenReturn(giftCertificates);
        List<GiftCertificateDTO> actualGiftCertificates = giftCertificateService.findAll(new LinkedCaseInsensitiveMap<>());
        assertEquals(expectedGiftCertificates, actualGiftCertificates);
    }

    @Test
    void findAllNegativeTest() {
        GiftCertificate certificate1 = new GiftCertificate(
                1L, "newCertificate34", "newDescription34", BigDecimal.valueOf(5),
                of(LocalDateTime.of(2020, Month.JANUARY, 10, 21, 0), ZoneId.systemDefault()),
                of(LocalDateTime.of(2020, Month.JANUARY, 14, 22, 4), ZoneId.systemDefault()),
                10
        );
        GiftCertificate certificate2 = new GiftCertificate(
                2L, "zzzzzzzzz", "zzzzzzzzzzzzzzzzzzzzzzzz", BigDecimal.valueOf(7),
                of(LocalDateTime.of(2020, Month.FEBRUARY, 11, 12, 0), ZoneId.systemDefault()),
                of(LocalDateTime.of(2020, Month.MARCH, 12, 11, 41), ZoneId.systemDefault()),
                11
        );
        List<GiftCertificate> giftCertificates = Collections.singletonList(certificate1);
        GiftCertificateDTO expectedCertificate1 = giftCertificateMapper.toDto(certificate1, new ArrayList<>());
        GiftCertificateDTO expectedCertificate2 = giftCertificateMapper.toDto(certificate2, new ArrayList<>());
        List<GiftCertificateDTO> expectedGiftCertificates = Arrays.asList(expectedCertificate1, expectedCertificate2);
        Mockito.when(giftCertificateDAO.findAll(Mockito.any())).thenReturn(giftCertificates);
        List<GiftCertificateDTO> actualGiftCertificates = giftCertificateService.findAll(new HashMap<>());
        assertNotEquals(expectedGiftCertificates, actualGiftCertificates);
    }

    @Test
    void findAllExceptionTest() {
        Mockito.when(giftCertificateDAO.findAll(Mockito.any())).thenReturn(new ArrayList<>());
        assertThrows(GiftCertificateException.class, () -> giftCertificateService.findAll(new HashMap<>()));
    }
}
