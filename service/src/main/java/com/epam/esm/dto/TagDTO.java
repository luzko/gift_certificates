package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagDTO {
    private Long id;
    @Pattern(regexp = "^#\\w{2,20}$", message = "400003")
    private String name;

    public TagDTO(String name) {
        this.name = name;
    }
}