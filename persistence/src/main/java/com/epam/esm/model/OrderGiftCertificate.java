package com.epam.esm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "order_certificates")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderGiftCertificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_certificate_id")
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gift_certificate_id_fk", nullable = false)
    private GiftCertificate giftCertificate;
    @Column(name = "count")
    private int count;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id_fk", nullable = false)
    private Order order;

    public OrderGiftCertificate(GiftCertificate giftCertificate, int count) {
        this.giftCertificate = giftCertificate;
        this.count = count;
    }
}
