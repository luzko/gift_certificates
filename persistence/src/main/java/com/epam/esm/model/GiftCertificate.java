package com.epam.esm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GiftCertificate {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private ZonedDateTime createDate;
    private ZonedDateTime lastUpdateDate;
    private Integer duration;

    public GiftCertificate(String name, String description, BigDecimal price, int duration) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
    }
}