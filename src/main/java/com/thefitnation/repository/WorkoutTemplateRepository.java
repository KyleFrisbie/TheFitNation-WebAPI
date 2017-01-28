package com.thefitnation.repository;

import com.thefitnation.domain.WorkoutTemplate;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the WorkoutTemplate entity.
 */
@SuppressWarnings("unused")
public interface WorkoutTemplateRepository extends JpaRepository<WorkoutTemplate,Long> {

    @Query("select distinct workoutTemplate from WorkoutTemplate workoutTemplate left join fetch workoutTemplate.workoutInstances")
    List<WorkoutTemplate> findAllWithEagerRelationships();

    @Query("select workoutTemplate from WorkoutTemplate workoutTemplate left join fetch workoutTemplate.workoutInstances where workoutTemplate.id =:id")
    WorkoutTemplate findOneWithEagerRelationships(@Param("id") Long id);

}
