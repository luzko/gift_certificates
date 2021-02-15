package com.epam.esm.service;

import com.epam.esm.dto.UserDTO;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

public interface JwtService {
    String createToken(UserDTO userDTO);

    Authentication getAuthentication(String token);

    String resolveToken(HttpServletRequest request);

    boolean validateToken(String token);
}
