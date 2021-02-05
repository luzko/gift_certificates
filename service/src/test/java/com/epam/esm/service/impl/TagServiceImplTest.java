package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.TagException;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.mapper.impl.TagMapperImpl;
import com.epam.esm.model.Tag;
import com.epam.esm.utils.PaginationUtil;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {
    @Spy
    private final TagMapper tagMapper = new TagMapperImpl(new ModelMapper());
    @Spy
    private final AppValidator validator = new AppValidator(Validation.buildDefaultValidatorFactory().getValidator());
    @Spy
    private final PaginationUtil paginationUtil = new PaginationUtil();
    @InjectMocks
    private TagServiceImpl tagService;
    @Mock
    private TagDAO tagDAO;

    @Test
    void addPositiveTest() {
        TagDTO inputTag = new TagDTO("#newtag");
        TagDTO expectedTag = new TagDTO(1L, "#newtag");
        Tag tag = new Tag(1L, "#newtag");
        Mockito.when(tagDAO.add(Mockito.any(Tag.class))).thenReturn(tag);
        TagDTO actualTag = tagService.add(inputTag);
        assertEquals(expectedTag, actualTag);
    }

    @Test
    void addNegativeTest() {
        TagDTO inputTag = new TagDTO("#newtag24");
        TagDTO expectedTag = new TagDTO(1L, "#newtag24");
        Tag tag = new Tag(1L, "#tag");
        Mockito.when(tagDAO.add(Mockito.any(Tag.class))).thenReturn(tag);
        TagDTO actualTag = tagService.add(inputTag);
        assertNotEquals(expectedTag, actualTag);
    }

    @Test
    void addExceptionTest() {
        TagDTO inputTag = new TagDTO("#");
        assertThrows(TagException.class, () -> tagService.add(inputTag));
    }

    @Test
    void removePositiveTest() {
        long inputId = 1;
        Mockito.doNothing().when(tagDAO).remove(Mockito.anyLong());
        tagService.remove(inputId);
        Mockito.verify(tagDAO).remove(inputId);
    }

    @Test
    void removeExceptionTest() {
        long inputId = 1;
        Mockito.doThrow(TagException.class).when(tagDAO).remove(Mockito.anyLong());
        assertThrows(TagException.class, () -> tagService.remove(inputId));
    }

    @Test
    void findByIdPositiveTest() {
        long inputId = 1;
        TagDTO expectedTag = new TagDTO(1L, "#newtag24");
        Tag tag = new Tag(1L, "#newtag24");
        Mockito.when(tagDAO.findById(Mockito.anyLong())).thenReturn(tag);
        TagDTO actualTag = tagService.findById(inputId);
        assertEquals(expectedTag, actualTag);
    }

    @Test
    void findByIdNegativeTest() {
        long inputId = 1;
        TagDTO expectedTag = new TagDTO(1L, "#olololo");
        Tag tag = new Tag(2L, "#newtag");
        Mockito.when(tagDAO.findById(Mockito.anyLong())).thenReturn(tag);
        TagDTO actualTag = tagService.findById(inputId);
        assertNotEquals(expectedTag, actualTag);
    }

    @Test
    void findByIdExceptionTest() {
        long inputId = 1;
        Mockito.doThrow(TagException.class).when(tagDAO).findById(Mockito.anyLong());
        assertThrows(TagException.class, () -> tagService.findById(inputId));
    }

    @Test
    void findMostPopularPositiveTest() {
        TagDTO expectedTag = new TagDTO(1L, "#most");
        Tag tag = new Tag(1L, "#most");
        Mockito.when(tagDAO.findMostPopular()).thenReturn(tag);
        TagDTO actualTag = tagService.findMostPopular();
        assertEquals(expectedTag, actualTag);
    }

    @Test
    void findMostPopularNegativeTest() {
        TagDTO expectedTag = new TagDTO(1L, "#new");
        Tag tag = new Tag(2L, "#old");
        Mockito.when(tagDAO.findMostPopular()).thenReturn(tag);
        TagDTO actualTag = tagService.findMostPopular();
        assertNotEquals(expectedTag, actualTag);
    }

    @Test
    void findAllPositiveTest() {
        TagDTO tagDTO1 = new TagDTO(1L, "#olololo");
        TagDTO tagDTO2 = new TagDTO(2L, "#newtag24");
        List<TagDTO> expectedTags = Arrays.asList(tagDTO1, tagDTO2);
        Tag tag1 = new Tag(1L, "#olololo");
        Tag tag2 = new Tag(2L, "#newtag24");
        List<Tag> tags = Arrays.asList(tag1, tag2);
        Mockito.when(tagDAO.findAll(Mockito.anyInt(), Mockito.anyInt())).thenReturn(tags);
        List<TagDTO> actualTags = tagService.findAll(new HashMap<>());
        assertEquals(expectedTags, actualTags);
    }

    @Test
    void findAllNegativeTest() {
        TagDTO tagDTO = new TagDTO(1L, "#newtag");
        List<TagDTO> expectedTags = Collections.singletonList(tagDTO);
        Tag tag1 = new Tag(1L, "#olololo");
        Tag tag2 = new Tag(2L, "#newtag24");
        List<Tag> tags = Arrays.asList(tag1, tag2);
        Mockito.when(tagDAO.findAll(Mockito.anyInt(), Mockito.anyInt())).thenReturn(tags);
        List<TagDTO> actualTags = tagService.findAll(new HashMap<>());
        assertNotEquals(expectedTags, actualTags);
    }

    @Test
    void findAllExceptionTest() {
        Mockito.when(tagDAO.findAll(Mockito.anyInt(), Mockito.anyInt())).thenReturn(new ArrayList<>());
        assertThrows(TagException.class, () -> tagService.findAll(new HashMap<>()));
    }

    @Test
    void defineCountPositiveTest() {
        long expected = 5L;
        Mockito.when(tagDAO.defineCount()).thenReturn(expected);
        long actual = tagService.defineCount();
        assertEquals(expected, actual);
    }

    @Test
    void defineCountNegativeTest() {
        long expected = 5L;
        Mockito.when(tagDAO.defineCount()).thenReturn(0L);
        long actual = tagService.defineCount();
        assertNotEquals(expected, actual);
    }
}
