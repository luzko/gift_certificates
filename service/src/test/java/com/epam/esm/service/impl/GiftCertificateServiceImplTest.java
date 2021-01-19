package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDAO;
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
import com.epam.esm.model.Tag;
import com.epam.esm.utils.PaginationUtil;
import com.epam.esm.utils.generator.CertificateQueryGenerator;
import com.epam.esm.utils.generator.TagQueryGenerator;
import com.epam.esm.validation.AppValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import javax.validation.Validation;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.time.ZonedDateTime.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {
    @Spy
    private final CertificateQueryGenerator certificateQueryGenerator = new CertificateQueryGenerator();
    @Spy
    private final TagQueryGenerator tagQueryGenerator = new TagQueryGenerator();
    @Spy
    private final GiftCertificateMapper giftCertificateMapper = new GiftCertificateMapperImpl(new ModelMapper());
    @Spy
    private final TagMapper tagMapper = new TagMapperImpl(new ModelMapper());
    @Spy
    private final AppValidator validator = new AppValidator(Validation.buildDefaultValidatorFactory().getValidator());
    @Spy
    private final PaginationUtil paginationUtil = new PaginationUtil();
    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateService;
    @Mock
    private GiftCertificateDAO giftCertificateDAO;
    @Mock
    private TagDAO tagDAO;

    @Test
    void addPositiveTest() {
        TagDTO inputTag = new TagDTO("#newtag");
        Tag tag = new Tag(1L, "#newtag");
        Set<Tag> expectedTagSet = new HashSet<>();
        expectedTagSet.add(tag);
        Set<TagDTO> inputTags = new HashSet<>();
        inputTags.add(inputTag);
        GiftCertificateDTO inputCertificate = new GiftCertificateDTO(
                "newCertificate", "newDescription", BigDecimal.valueOf(5), 10, inputTags
        );
        GiftCertificate certificate = new GiftCertificate(
                1L, "newCertificate", "newDescription", BigDecimal.valueOf(5),
                of(LocalDateTime.of(2020, Month.JANUARY, 10, 21, 0), ZoneId.systemDefault()),
                of(LocalDateTime.of(2020, Month.JANUARY, 14, 22, 4), ZoneId.systemDefault()),
                10, false, expectedTagSet
        );
        GiftCertificateDTO expectedCertificate = giftCertificateMapper.toDto(certificate);
        Mockito.when(giftCertificateDAO.add(Mockito.any(GiftCertificate.class))).thenReturn(certificate);
        Mockito.when(tagDAO.findByName(Mockito.anyString())).thenReturn(Collections.singletonList(tag));
        GiftCertificateDTO actualCertificate = giftCertificateService.add(inputCertificate);
        assertEquals(expectedCertificate, actualCertificate);
    }

    @Test
    void addNegativeTest() {
        TagDTO inputTag = new TagDTO("#newtag12345");
        Tag tag = new Tag(1L, "#newtag1234");
        Tag expectedTag = new Tag(1L, "#newtag123233");
        Set<TagDTO> inputTags = new HashSet<>();
        Set<Tag> expectedTags = new HashSet<>();
        inputTags.add(inputTag);
        expectedTags.add(expectedTag);
        GiftCertificateDTO inputCertificate = new GiftCertificateDTO(
                "newCertificate", "newDescription", BigDecimal.valueOf(5), 10, inputTags
        );
        GiftCertificate expected = new GiftCertificate(
                1L, "newCertificate", "newDescription", BigDecimal.valueOf(5),
                of(LocalDateTime.of(2020, Month.JANUARY, 10, 21, 0), ZoneId.systemDefault()),
                of(LocalDateTime.of(2020, Month.JANUARY, 14, 22, 4), ZoneId.systemDefault()),
                10, false, expectedTags
        );
        GiftCertificateDTO expectedCertificate = giftCertificateMapper.toDto(expected);
        List<Tag> tags = new ArrayList<>();
        tags.add(tag);
        Mockito.when(giftCertificateDAO.add(Mockito.any(GiftCertificate.class))).thenReturn(new GiftCertificate());
        Mockito.when(tagDAO.findByName(Mockito.anyString())).thenReturn(tags);
        GiftCertificateDTO actualCertificate = giftCertificateService.add(inputCertificate);
        assertNotEquals(expectedCertificate, actualCertificate);
    }

    @Test
    void addExceptionTest() {
        GiftCertificateDTO inputCertificate = new GiftCertificateDTO(
                "newCertificate", "newDescription", BigDecimal.valueOf(-5), -55, new HashSet<>()
        );
        assertThrows(GiftCertificateException.class, () -> giftCertificateService.add(inputCertificate));
    }

    @Test
    void updatePatchPositiveTest() {
        GiftCertificatePatchDTO inputCertificate = new GiftCertificatePatchDTO(
                "newCertificate111", "newDescription111", BigDecimal.valueOf(24), 16, new HashSet<>()
        );
        GiftCertificate certificate = new GiftCertificate(
                1L, "newCertificate", "newDescription", BigDecimal.valueOf(5),
                of(LocalDateTime.of(2020, Month.JANUARY, 10, 21, 0), ZoneId.systemDefault()),
                of(LocalDateTime.of(2020, Month.JANUARY, 14, 22, 4), ZoneId.systemDefault()),
                10, false, new HashSet<>()
        );
        GiftCertificateDTO expectedCertificate = giftCertificateMapper.toDto(certificate);
        Mockito.when(giftCertificateDAO.findById(Mockito.anyLong())).thenReturn(certificate);
        Mockito.when(giftCertificateDAO.update(Mockito.any(GiftCertificate.class))).thenReturn(certificate);
        Mockito.when(tagDAO.findByName(Mockito.anyString())).thenReturn(new ArrayList<>());
        GiftCertificateDTO actualCertificate = giftCertificateService.update(1L, inputCertificate);
        assertEquals(expectedCertificate, actualCertificate);
    }

    @Test
    void updatePatchNegativeTest() {
        GiftCertificatePatchDTO inputCertificate = new GiftCertificatePatchDTO(
                "newCertificate111", "newDescription111", BigDecimal.valueOf(24), 16, new HashSet<>()
        );
        GiftCertificate certificate1 = new GiftCertificate(
                1L, "newCertificate", "newDescription", BigDecimal.valueOf(5),
                of(LocalDateTime.of(2020, Month.JANUARY, 10, 21, 0), ZoneId.systemDefault()),
                of(LocalDateTime.of(2020, Month.JANUARY, 14, 22, 4), ZoneId.systemDefault()),
                10, false, new HashSet<>()
        );
        GiftCertificate certificate2 = new GiftCertificate(
                2L, "asdfasdf", "qwerqwer", BigDecimal.valueOf(6),
                of(LocalDateTime.of(2020, Month.APRIL, 1, 14, 14), ZoneId.systemDefault()),
                of(LocalDateTime.of(2020, Month.APRIL, 12, 15, 46), ZoneId.systemDefault()),
                11, false, new HashSet<>()
        );
        GiftCertificateDTO expectedCertificate = giftCertificateMapper.toDto(certificate1);
        Mockito.when(giftCertificateDAO.findById(Mockito.anyLong())).thenReturn(certificate2);
        Mockito.when(giftCertificateDAO.update(Mockito.any(GiftCertificate.class))).thenReturn(certificate2);
        GiftCertificateDTO actualCertificate = giftCertificateService.update(1L, inputCertificate);
        assertNotEquals(expectedCertificate, actualCertificate);
    }

    @Test
    void updatePutPositiveTest() {
        GiftCertificateDTO inputCertificate = new GiftCertificateDTO(
                "newasdfasdf", "newasdfasdfasdf", BigDecimal.valueOf(24), 16, new HashSet<>()
        );
        GiftCertificate certificate = new GiftCertificate(
                2L, "newCertificate", "newDescription", BigDecimal.valueOf(5),
                of(LocalDateTime.of(2021, Month.FEBRUARY, 11, 12, 0), ZoneId.systemDefault()),
                of(LocalDateTime.of(2021, Month.FEBRUARY, 12, 11, 4), ZoneId.systemDefault()),
                6, false, new HashSet<>()
        );
        GiftCertificateDTO expectedCertificate = giftCertificateMapper.toDto(certificate);
        Mockito.when(giftCertificateDAO.findById(Mockito.anyLong())).thenReturn(certificate);
        Mockito.when(giftCertificateDAO.update(Mockito.any(GiftCertificate.class))).thenReturn(certificate);
        Mockito.when(tagDAO.findByName(Mockito.anyString())).thenReturn(new ArrayList<>());
        GiftCertificateDTO actualCertificate = giftCertificateService.update(2L, inputCertificate);
        assertEquals(expectedCertificate, actualCertificate);
    }

    @Test
    void updatePutNegativeTest() {
        GiftCertificateDTO inputCertificate = new GiftCertificateDTO(
                "newCertificate", "newDescription", BigDecimal.valueOf(12), 6, new HashSet<>()
        );
        GiftCertificate certificate1 = new GiftCertificate(
                3L, "zxcvzxcvzxcv", "zxcvzxvzxcv", BigDecimal.valueOf(7),
                of(LocalDateTime.of(2020, Month.JANUARY, 10, 21, 0), ZoneId.systemDefault()),
                of(LocalDateTime.of(2020, Month.JANUARY, 14, 22, 4), ZoneId.systemDefault()),
                10, false, new HashSet<>()
        );
        GiftCertificate certificate2 = new GiftCertificate(
                4L, "asdfasdf", "qwerqwer", BigDecimal.valueOf(6),
                of(LocalDateTime.of(2021, Month.FEBRUARY, 2, 13, 14), ZoneId.systemDefault()),
                of(LocalDateTime.of(2021, Month.FEBRUARY, 13, 14, 46), ZoneId.systemDefault()),
                8, false, new HashSet<>()
        );
        GiftCertificateDTO expectedCertificate = giftCertificateMapper.toDto(certificate1);
        Mockito.when(giftCertificateDAO.findById(Mockito.anyLong())).thenReturn(certificate2);
        Mockito.when(giftCertificateDAO.update(Mockito.any(GiftCertificate.class))).thenReturn(certificate2);
        GiftCertificateDTO actualCertificate = giftCertificateService.update(3L, inputCertificate);
        assertNotEquals(expectedCertificate, actualCertificate);
    }

    @Test
    void updatePutExceptionTest() {
        GiftCertificateDTO inputCertificate = new GiftCertificateDTO(
                null, "newDescription", BigDecimal.valueOf(-5), -55, new HashSet<>()
        );
        assertThrows(GiftCertificateException.class, () -> giftCertificateService.add(inputCertificate));
    }

    @Test
    void removePositiveTest() {
        long inputId = 1;
        Mockito.doNothing().when(giftCertificateDAO).remove(Mockito.anyLong());
        giftCertificateService.remove(inputId);
        Mockito.verify(giftCertificateDAO).remove(inputId);
    }

    @Test
    void removeExceptionTest() {
        long inputId = 1;
        Mockito.doThrow(GiftCertificateException.class).when(giftCertificateDAO).remove(Mockito.anyLong());
        assertThrows(GiftCertificateException.class, () -> giftCertificateService.remove(inputId));
    }

    @Test
    void findByIdPositiveTest() {
        long inputId = 1;
        GiftCertificate certificate = new GiftCertificate(
                1L, "newCertificate", "newDescription", BigDecimal.valueOf(5),
                of(LocalDateTime.of(2020, Month.JANUARY, 10, 21, 0), ZoneId.systemDefault()),
                of(LocalDateTime.of(2020, Month.JANUARY, 14, 22, 4), ZoneId.systemDefault()),
                10, false, new HashSet<>()
        );
        GiftCertificateDTO expectedCertificate = giftCertificateMapper.toDto(certificate);
        Mockito.when(giftCertificateDAO.findById(Mockito.anyLong())).thenReturn(certificate);
        GiftCertificateDTO actualCertificate = giftCertificateService.findById(inputId);
        assertEquals(expectedCertificate, actualCertificate);

    }

    @Test
    void findByIdNegativeTest() {
        long inputId = 2;
        GiftCertificate certificate1 = new GiftCertificate(
                1L, "newCertificate1234", "newDescription1234", BigDecimal.valueOf(5),
                of(LocalDateTime.of(2020, Month.JANUARY, 10, 21, 0), ZoneId.systemDefault()),
                of(LocalDateTime.of(2020, Month.JANUARY, 14, 22, 4), ZoneId.systemDefault()),
                10, false, null
        );
        GiftCertificate certificate2 = new GiftCertificate(
                2L, "newCertificate", "newDescription", BigDecimal.valueOf(7),
                of(LocalDateTime.of(2020, Month.FEBRUARY, 11, 14, 0), ZoneId.systemDefault()),
                of(LocalDateTime.of(2020, Month.FEBRUARY, 15, 15, 4), ZoneId.systemDefault()),
                8, false, null
        );
        GiftCertificateDTO expectedCertificate = giftCertificateMapper.toDto(certificate1);
        Mockito.when(giftCertificateDAO.findById(Mockito.anyLong())).thenReturn(certificate2);
        GiftCertificateDTO actualCertificate = giftCertificateService.findById(inputId);
        assertNotEquals(expectedCertificate, actualCertificate);
    }

    @Test
    void findByIdExceptionTest() {
        long inputId = 1;
        Mockito.doThrow(GiftCertificateException.class).when(giftCertificateDAO).findById(Mockito.anyLong());
        assertThrows(GiftCertificateException.class, () -> giftCertificateService.findById(inputId));
    }

    @Test
    void findAllPositiveTest() {
        GiftCertificate certificate1 = new GiftCertificate(
                1L, "newCertificate1234", "newDescription1234", BigDecimal.valueOf(5),
                of(LocalDateTime.of(2020, Month.JANUARY, 10, 21, 0), ZoneId.systemDefault()),
                of(LocalDateTime.of(2020, Month.JANUARY, 14, 22, 4), ZoneId.systemDefault()),
                10, false, new HashSet<>()
        );
        GiftCertificate certificate2 = new GiftCertificate(
                2L, "asdfasdfasdf", "asdfasdfasdf", BigDecimal.valueOf(7),
                of(LocalDateTime.of(2020, Month.FEBRUARY, 11, 12, 0), ZoneId.systemDefault()),
                of(LocalDateTime.of(2020, Month.MARCH, 12, 11, 41), ZoneId.systemDefault()),
                11, false, new HashSet<>()
        );
        List<GiftCertificate> giftCertificates = Arrays.asList(certificate1, certificate2);
        GiftCertificateDTO expectedCertificate1 = giftCertificateMapper.toDto(certificate1);
        GiftCertificateDTO expectedCertificate2 = giftCertificateMapper.toDto(certificate2);
        List<GiftCertificateDTO> expectedGiftCertificates = Arrays.asList(expectedCertificate1, expectedCertificate2);
        Mockito.when(giftCertificateDAO.findAll(Mockito.anyList(), Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(giftCertificates);
        List<GiftCertificateDTO> actualGiftCertificates = giftCertificateService.findAll(new HashMap<>());
        assertEquals(expectedGiftCertificates, actualGiftCertificates);
    }

    @Test
    void findAllNegativeTest() {
        GiftCertificate certificate1 = new GiftCertificate(
                1L, "newCertificate34", "newDescription34", BigDecimal.valueOf(5),
                of(LocalDateTime.of(2020, Month.JANUARY, 10, 21, 0), ZoneId.systemDefault()),
                of(LocalDateTime.of(2020, Month.JANUARY, 14, 22, 4), ZoneId.systemDefault()),
                10, false, new HashSet<>()
        );
        GiftCertificate certificate2 = new GiftCertificate(
                2L, "zzzzzzzzz", "zzzzzzzzzzzzzzzzzzzzzzzz", BigDecimal.valueOf(7),
                of(LocalDateTime.of(2020, Month.FEBRUARY, 11, 12, 0), ZoneId.systemDefault()),
                of(LocalDateTime.of(2020, Month.MARCH, 12, 11, 41), ZoneId.systemDefault()),
                11, false, new HashSet<>()
        );
        List<GiftCertificate> giftCertificates = Collections.singletonList(certificate1);
        GiftCertificateDTO expectedCertificate1 = giftCertificateMapper.toDto(certificate1);
        GiftCertificateDTO expectedCertificate2 = giftCertificateMapper.toDto(certificate2);
        List<GiftCertificateDTO> expectedGiftCertificates = Arrays.asList(expectedCertificate1, expectedCertificate2);
        Mockito.when(giftCertificateDAO.findAll(Mockito.anyList(), Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(giftCertificates);
        List<GiftCertificateDTO> actualGiftCertificates = giftCertificateService.findAll(new HashMap<>());
        assertNotEquals(expectedGiftCertificates, actualGiftCertificates);
    }

    @Test
    void findAllExceptionTest() {
        Mockito.when(giftCertificateDAO.findAll(Mockito.anyList(), Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(new ArrayList<>());
        assertThrows(GiftCertificateException.class, () -> giftCertificateService.findAll(new HashMap<>()));
    }

    @Test
    void defineCountPositiveTest() {
        long expected = 5L;
        Mockito.when(giftCertificateDAO.defineCount(Mockito.anyList())).thenReturn(expected);
        long actual = giftCertificateService.defineCount(new HashMap<>());
        assertEquals(expected, actual);
    }

    @Test
    void defineCountNegativeTest() {
        long expected = 5L;
        Mockito.when(giftCertificateDAO.defineCount(Mockito.anyList())).thenReturn(0L);
        long actual = giftCertificateService.defineCount(new HashMap<>());
        assertNotEquals(expected, actual);
    }
}
