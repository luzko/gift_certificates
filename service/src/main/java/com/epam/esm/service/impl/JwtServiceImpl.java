package com.epam.esm.service.impl;

import com.epam.esm.constant.JwtParam;
import com.epam.esm.dao.UserDAO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.mapper.JwtUserMapper;
import com.epam.esm.mapper.UserMapper;
import com.epam.esm.model.JwtUser;
import com.epam.esm.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
    private final UserDAO userDAO;
    private final UserMapper userMapper;
    private final JwtUserMapper jwtUserMapper;
    @Value("${jwt.token.secret}")
    private String secret;
    @Getter
    @Value("${jwt.token.expired}")
    private long validity;

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String createToken(UserDTO userDTO) {
        Claims claims = Jwts.claims().setSubject(userDTO.getEmail());
        claims.put(JwtParam.ROLE.getValue(), userDTO.getRole());
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + validity))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDTO userDTO = userMapper.toDto(userDAO.findByEmail(getUsername(token)));
        JwtUser jwtUser = jwtUserMapper.toJwtUser(userDTO);
        return new UsernamePasswordAuthenticationToken(jwtUser, jwtUser.getPassword(), jwtUser.getAuthorities());
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(JwtParam.AUTHORIZATION.getValue());
        String token = null;
        if (bearerToken != null && bearerToken.startsWith(JwtParam.BEARER.getValue())) {
            token = bearerToken.substring(7);
        }
        return token;
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private String getUsername(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }
}
