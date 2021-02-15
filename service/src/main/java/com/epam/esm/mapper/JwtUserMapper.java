package com.epam.esm.mapper;

import com.epam.esm.dto.UserDTO;
import com.epam.esm.model.JwtUser;

public interface JwtUserMapper {
    JwtUser toJwtUser(UserDTO userDTO);

    //List<GrantedAuthority> toGrantedAuthorities(Role role);
}
