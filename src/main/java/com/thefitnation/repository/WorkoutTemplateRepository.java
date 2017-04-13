package com.thefitnation.repository;

import com.thefitnation.domain.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;

/**
 * Spring Data JPA repository for the WorkoutTemplate entity.
 */
@SuppressWarnings("unused")
public interface WorkoutTemplateRepository extends JpaRepository<WorkoutTemplate,Long> {

    @Query("select workoutTemplate " +
            "from WorkoutTemplate workoutTemplate " +
            "where workoutTemplate.userDemographic.user.login = :login")
    Page<WorkoutTemplate> findAllByCurrentLoggedInUser(@Param(value = "login") String login, Pageable pageable);

    @Query("select workoutTemplate " +
            "from WorkoutTemplate workoutTemplate " +
            "where workoutTemplate.userDemographic.user.login = :login " +
                "and workoutTemplate.id = :id")
    WorkoutTemplate findOne(@Param(value = "login") String login, @Param(value = "id") Long id);
}
