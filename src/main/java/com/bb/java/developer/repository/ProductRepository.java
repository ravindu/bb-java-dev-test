package com.bb.java.developer.repository;

import com.bb.java.developer.model.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<EventEntity, String> {

    @Query("SELECT eventEntity FROM EventEntity eventEntity WHERE eventEntity.id= :eventId")
    Optional<EventEntity> findByEventId(@Param("eventId") String eventId);
}
