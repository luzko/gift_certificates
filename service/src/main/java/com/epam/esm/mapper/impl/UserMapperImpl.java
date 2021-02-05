package com.epam.esm.mapper.impl;

import com.epam.esm.dto.UserDTO;
import com.epam.esm.mapper.UserMapper;
import com.epam.esm.model.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapperImpl implements UserMapper {
    private final ModelMapper modelMapper;

    @Override
    public UserDTO toDto(User user) {
        return Objects.isNull(user) ? null : modelMapper.map(user, UserDTO.class);
    }

    @Override
    public List<UserDTO> toDto(List<User> users) {
        return users.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
