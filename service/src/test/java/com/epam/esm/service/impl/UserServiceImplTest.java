package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDAO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.exception.UserException;
import com.epam.esm.mapper.UserMapper;
import com.epam.esm.mapper.impl.UserMapperImpl;
import com.epam.esm.model.Role;
import com.epam.esm.model.User;
import com.epam.esm.utils.PaginationUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Spy
    private final UserMapper userMapper = new UserMapperImpl(new ModelMapper());
    @Spy
    private final PaginationUtil paginationUtil = new PaginationUtil();
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserDAO userDAO;

    @Test
    void findByIdPositiveTest() {
        long inputId = 1;
        UserDTO expectedUser = new UserDTO(1L, "test@test.test", "password", Role.USER);
        User user = new User(1L, "test@test.test", "password", Role.USER);
        Mockito.when(userDAO.findById(Mockito.anyLong())).thenReturn(user);
        UserDTO actualUser = userService.findById(inputId);
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void findByIdNegativeTest() {
        long inputId = 1;
        UserDTO expectedUser = new UserDTO(1L, "test@test.test", "password", Role.USER);
        User user = new User(2L, "user@test.test", "ololo", Role.USER);
        Mockito.when(userDAO.findById(Mockito.anyLong())).thenReturn(user);
        UserDTO actualUser = userService.findById(inputId);
        assertNotEquals(expectedUser, actualUser);
    }

    @Test
    void findByIdExceptionTest() {
        long inputId = 1;
        Mockito.doThrow(UserException.class).when(userDAO).findById(Mockito.anyLong());
        assertThrows(UserException.class, () -> userService.findById(inputId));
    }

    @Test
    void findAllPositiveTest() {
        UserDTO userDTO1 = new UserDTO(1L, "test@test.test", "password", Role.USER);
        UserDTO userDTO2 = new UserDTO(2L, "user@test.test", "ololo", Role.USER);
        List<UserDTO> expectedUsers = Arrays.asList(userDTO1, userDTO2);
        User user1 = new User(1L, "test@test.test", "password", Role.USER);
        User user2 = new User(2L, "user@test.test", "ololo", Role.USER);
        List<User> users = Arrays.asList(user1, user2);
        Mockito.when(userDAO.findAll(Mockito.anyInt(), Mockito.anyInt())).thenReturn(users);
        List<UserDTO> actualUsers = userService.findAll(new HashMap<>());
        assertEquals(expectedUsers, actualUsers);
    }

    @Test
    void findAllNegativeTest() {
        UserDTO userDTO = new UserDTO(1L, "test@test.test", "password", Role.USER);
        List<UserDTO> expectedUsers = Collections.singletonList(userDTO);
        User user1 = new User(1L, "test@test.test", "password", Role.USER);
        User user2 = new User(2L, "user@test.test", "ololo", Role.USER);
        List<User> users = Arrays.asList(user1, user2);
        Mockito.when(userDAO.findAll(Mockito.anyInt(), Mockito.anyInt())).thenReturn(users);
        List<UserDTO> actualUsers = userService.findAll(new HashMap<>());
        assertNotEquals(expectedUsers, actualUsers);
    }

    @Test
    void findAllExceptionTest() {
        Mockito.when(userDAO.findAll(Mockito.anyInt(), Mockito.anyInt())).thenReturn(new ArrayList<>());
        assertThrows(UserException.class, () -> userService.findAll(new HashMap<>()));
    }

    @Test
    void defineCountPositiveTest() {
        long expected = 5L;
        Mockito.when(userDAO.defineCount()).thenReturn(expected);
        long actual = userService.defineCount();
        assertEquals(expected, actual);
    }

    @Test
    void defineCountNegativeTest() {
        long expected = 5L;
        Mockito.when(userDAO.defineCount()).thenReturn(0L);
        long actual = userService.defineCount();
        assertNotEquals(expected, actual);
    }
}
