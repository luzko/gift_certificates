package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenDTO {
    private String userName;
    private String token;
    private long validity;
}
