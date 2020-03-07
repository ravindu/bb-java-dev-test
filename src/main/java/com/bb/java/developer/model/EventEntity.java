package com.bb.java.developer.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "event_tbl")
public class EventEntity extends BaseEntity {

    @Id
    @Column(name = "event_id")
    private String id;

    @Column(name = "time_stamp")
    private OffsetDateTime timestamp;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "eventEntity", cascade = CascadeType.ALL)
    private Collection<ProductEntity> productEntities;

    @Column(name = "created_date")
    private OffsetDateTime createdDate;

    @Column(name = "updated_date")
    private OffsetDateTime updatedDate;

    public void addEntityIdToPaymentEntity(EventEntity eventEntity) {
        Collection<ProductEntity> productEntity= eventEntity.getProductEntities();
        if(!CollectionUtils.isEmpty(productEntity)){
            productEntity.stream().forEach(x->{x.setEventEntity(eventEntity);});
        }
    }

    @Override
    public String toString() {
        return "EventEntity{" +
                "id=" + id +
                ", timestamp=" + timestamp+
                ", createdDate='" + createdDate + '\'' +
                ", updatedDate='" + updatedDate + '\'' +
                '}';
    }

}
