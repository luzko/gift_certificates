package com.epam.esm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    @EqualsAndHashCode.Exclude
    private Long id;
    private String name;

    public Tag(String name) {
        this.name = name;
    }
}
