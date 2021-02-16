package com.epam.esm.mapper;

import com.epam.esm.dto.UserDTO;
import com.epam.esm.model.User;

import java.util.List;

public interface UserMapper {
    User toEntity(UserDTO userDTO);

    UserDTO toDto(User user);

    List<UserDTO> toDto(List<User> users);
}
