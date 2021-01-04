package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GiftCertificateDTO {
    private Long id;
    @Size(min = 2, max = 40, message = "400106")
    private String name;
    @Size(min = 10, max = 500, message = "400107")
    private String description;
    @Positive(message = "400108")
    @Max(value = 100000, message = "400108")
    private BigDecimal price;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = ZonedDateTimeSerializer.class)
    private ZonedDateTime createDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = ZonedDateTimeSerializer.class)
    private ZonedDateTime lastUpdateDate;
    @Positive(message = "400109")
    @Max(value = 1000, message = "400109")
    private Integer duration;
    private List<TagDTO> tags;

    public GiftCertificateDTO(String name, String description, BigDecimal price, int duration, List<TagDTO> tags) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.tags = tags;
    }
}