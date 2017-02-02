package com.thefitnation.repository;

import com.thefitnation.domain.Gym;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Gym entity.
 */
@SuppressWarnings("unused")
public interface GymRepository extends JpaRepository<Gym,Long> {

    @Query("select distinct gym from Gym gym left join fetch gym.userDemographics")
    List<Gym> findAllWithEagerRelationships();

    @Query("select gym from Gym gym left join fetch gym.userDemographics where gym.id =:id")
    Gym findOneWithEagerRelationships(@Param("id") Long id);

}
