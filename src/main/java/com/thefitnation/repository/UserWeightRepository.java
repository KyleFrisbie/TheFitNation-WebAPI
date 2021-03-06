package com.thefitnation.repository;

import com.thefitnation.domain.UserWeight;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Spring Data JPA repository for the UserWeight entity.
 */
@SuppressWarnings("unused")
public interface UserWeightRepository extends JpaRepository<UserWeight, Long> {
    @Query("select userWeight from UserWeight userWeight " +
        "join userWeight.userDemographic.user u where u.id = :id")
    Page<UserWeight> findAllByUserId(Pageable pageable, @Param("id") Long id);
    @Query("select userWeight from UserWeight userWeight " +
        "join userWeight.userDemographic.user u where u.id = :userId and userWeight.id = :userWeightId")
    Optional<UserWeight> findOneByUserId(@Param("userWeightId") Long userWeightId, @Param("userId") Long userId);
}
