package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GiftCertificatePatchDTO {
    @Size(min = 2, max = 40, message = "400107")
    private String name;
    @Size(min = 10, max = 500, message = "400109")
    private String description;
    @Positive(message = "400111")
    @Max(value = 100000, message = "400112")
    private BigDecimal price;
    @Positive(message = "400114")
    @Max(value = 1000, message = "400115")
    private Integer duration;
    private List<TagDTO> tags;

    public GiftCertificatePatchDTO(String name, String description, BigDecimal price, int duration, List<TagDTO> tags) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.tags = tags;
    }
}
