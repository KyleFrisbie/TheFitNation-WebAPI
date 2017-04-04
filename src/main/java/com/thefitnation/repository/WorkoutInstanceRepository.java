package com.thefitnation.repository;

import com.thefitnation.domain.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the WorkoutInstance entity.
 */
@SuppressWarnings("unused")
public interface WorkoutInstanceRepository extends JpaRepository<WorkoutInstance,Long> {

    @Query("select workoutInstanceRepository " +
            "from WorkoutInstance workoutInstanceRepository " +
            "where workoutInstanceRepository.workoutTemplate.userDemographic.user.login = ?#{principal.username}")
    Page<WorkoutInstance> findAllByCurrentLoggedInUser(Pageable pageable);

}
