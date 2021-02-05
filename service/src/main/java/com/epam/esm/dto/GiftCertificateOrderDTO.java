package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GiftCertificateOrderDTO {
    @NotNull(message = "400116")
    @Positive(message = "400117")
    private Long id;
    @NotNull(message = "400118")
    @Positive(message = "400119")
    @Max(value = 20, message = "400120")
    private Integer count;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String name;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String description;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private BigDecimal price;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = ZonedDateTimeSerializer.class)
    private ZonedDateTime createDate;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = ZonedDateTimeSerializer.class)
    private ZonedDateTime lastUpdateDate;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer duration;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Set<TagDTO> tags;

    public GiftCertificateOrderDTO(Long id, Integer count) {
        this.id = id;
        this.count = count;
    }
}
