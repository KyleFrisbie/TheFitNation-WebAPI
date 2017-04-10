package com.thefitnation.repository;

import com.thefitnation.domain.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;

/**
 * Spring Data JPA repository for the WorkoutInstance entity.
 */
@SuppressWarnings("unused")
public interface WorkoutInstanceRepository extends JpaRepository<WorkoutInstance,Long> {

    @Query("select workoutInstance " +
            "from WorkoutInstance workoutInstance " +
            "where workoutInstance.workoutTemplate.userDemographic.user.login = :login")
    Page<WorkoutInstance> findAll(@Param(value = "login") String login, Pageable pageable);

    @Query("select workoutInstance " +
            "from WorkoutInstance workoutInstance " +
            "where workoutInstance.workoutTemplate.userDemographic.user.login = :login " +
                "and workoutInstance.id = :id ")
    WorkoutInstance findOne(@Param(value = "login") String login, @Param(value = "id") Long id);
}
