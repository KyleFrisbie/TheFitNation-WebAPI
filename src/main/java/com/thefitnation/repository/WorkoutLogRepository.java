package com.thefitnation.repository;

import com.thefitnation.domain.WorkoutLog;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the WorkoutLog entity.
 */
@SuppressWarnings("unused")
public interface WorkoutLogRepository extends JpaRepository<WorkoutLog,Long> {

    @Query("select distinct workoutLog from WorkoutLog workoutLog left join fetch workoutLog.workoutTemplates")
    List<WorkoutLog> findAllWithEagerRelationships();

    @Query("select workoutLog from WorkoutLog workoutLog left join fetch workoutLog.workoutTemplates where workoutLog.id =:id")
    WorkoutLog findOneWithEagerRelationships(@Param("id") Long id);

}
