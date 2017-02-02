package com.thefitnation.repository;

import com.thefitnation.domain.WorkoutInstance;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the WorkoutInstance entity.
 */
@SuppressWarnings("unused")
public interface WorkoutInstanceRepository extends JpaRepository<WorkoutInstance,Long> {

    @Query("select distinct workoutInstance from WorkoutInstance workoutInstance left join fetch workoutInstance.exercises left join fetch workoutInstance.muscles")
    List<WorkoutInstance> findAllWithEagerRelationships();

    @Query("select workoutInstance from WorkoutInstance workoutInstance left join fetch workoutInstance.exercises left join fetch workoutInstance.muscles where workoutInstance.id =:id")
    WorkoutInstance findOneWithEagerRelationships(@Param("id") Long id);

}
