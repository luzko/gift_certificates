package com.epam.esm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "gift_certificates")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GiftCertificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gift_certificate_id")
    private Long id;
    @Column(name = "gift_certificate_name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "create_date", updatable = false)
    private ZonedDateTime createDate;
    @Column(name = "last_update_date")
    private ZonedDateTime lastUpdateDate;
    @Column(name = "duration")
    private Integer duration;
    @Column(name = "is_deleted")
    private boolean isDeleted;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "gift_certificate_tags",
            joinColumns = @JoinColumn(name = "gift_certificate_id_fk"),
            inverseJoinColumns = @JoinColumn(name = "tag_id_fk")
    )
    private Set<Tag> tags = new HashSet<>();
}
