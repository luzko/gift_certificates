package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GiftCertificateDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    @NotBlank(message = "400106")
    @Size(min = 2, max = 40, message = "400107")
    private String name;
    @NotBlank(message = "400108")
    @Size(min = 10, max = 500, message = "400109")
    private String description;
    @NotNull(message = "400110")
    @Positive(message = "400111")
    @Max(value = 100000, message = "400112")
    private BigDecimal price;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = ZonedDateTimeSerializer.class)
    private ZonedDateTime createDate;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = ZonedDateTimeSerializer.class)
    private ZonedDateTime lastUpdateDate;
    @NotNull(message = "400113")
    @Positive(message = "400114")
    @Max(value = 1000, message = "400115")
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
