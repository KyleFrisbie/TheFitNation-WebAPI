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

    @Query("select workoutInstanceRepository " +
            "from WorkoutInstance workoutInstanceRepository " +
            "where workoutInstanceRepository.workoutTemplate.userDemographic.user.login = :login")
    Page<WorkoutInstance> findAllByCurrentLoggedInUser(@Param(value = "login") String login, Pageable pageable);
}
