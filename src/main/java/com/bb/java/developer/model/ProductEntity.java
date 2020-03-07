package com.bb.java.developer.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "product_tbl")
public class ProductEntity extends BaseEntity {

    @Id
    @Column(name = "product_id")
    @EqualsAndHashCode.Exclude
    private long id;

    @Column(name = "product_name")
    private String name;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "sale_amount")
    private double saleAmount;

    @Column(name = "created_date")
    private OffsetDateTime createdDate;

    @Column(name = "updated_date")
    private OffsetDateTime updatedDate;

    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name = "fk_event_id", nullable = false)
    private EventEntity eventEntity;


}
