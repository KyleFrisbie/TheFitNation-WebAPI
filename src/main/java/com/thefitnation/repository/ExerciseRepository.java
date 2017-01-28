package com.thefitnation.repository;

import com.thefitnation.domain.Exercise;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Exercise entity.
 */
@SuppressWarnings("unused")
public interface ExerciseRepository extends JpaRepository<Exercise,Long> {

    @Query("select distinct exercise from Exercise exercise left join fetch exercise.muscles")
    List<Exercise> findAllWithEagerRelationships();

    @Query("select exercise from Exercise exercise left join fetch exercise.muscles where exercise.id =:id")
    Exercise findOneWithEagerRelationships(@Param("id") Long id);

}
