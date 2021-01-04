package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WebExceptionResponse {
    private String errorCode;
    private String errorMessage;
}
