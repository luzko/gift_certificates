package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GiftCertificatePatchDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    @Size(min = 2, max = 40, message = "400107")
    private String name;
    @Size(min = 10, max = 500, message = "400109")
    private String description;
    @Positive(message = "400111")
    @Max(value = 100000, message = "400112")
    private BigDecimal price;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private ZonedDateTime createDate;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private ZonedDateTime lastUpdateDate;
    @Positive(message = "400114")
    @Max(value = 1000, message = "400115")
    private Integer duration;
    private Set<TagDTO> tags;

    public GiftCertificatePatchDTO(String name, String description, BigDecimal price, int duration, Set<TagDTO> tags) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.tags = tags;
    }
}
