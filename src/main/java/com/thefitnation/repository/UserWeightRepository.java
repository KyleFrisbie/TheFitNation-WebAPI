package com.thefitnation.repository;

import com.thefitnation.domain.UserWeight;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the UserWeight entity.
 */
@SuppressWarnings("unused")
public interface UserWeightRepository extends JpaRepository<UserWeight,Long> {
    @Query("select distinct uw from UserWeight where uw.userId = :id")
    Page<UserWeight> findAllByUserId(Pageable pageable, @Param("id") Long id);
}
