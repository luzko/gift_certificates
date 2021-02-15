package com.epam.esm.mapper.impl;

import com.epam.esm.dto.UserDTO;
import com.epam.esm.model.JwtUser;
import com.epam.esm.mapper.JwtUserMapper;
import com.epam.esm.model.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class JwtUserMapperImpl implements JwtUserMapper {
    @Override
    public JwtUser toJwtUser(UserDTO userDTO) {
        return new JwtUser(
                userDTO.getId(),
                userDTO.getEmail(),
                userDTO.getPassword(),
                toGrantedAuthorities(userDTO.getRole())
        );
    }

    //@Override
    private List<GrantedAuthority> toGrantedAuthorities(Role userRole) {
        return Stream.of(userRole)
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());
    }
}
