package com.thefitnation.repository;

import com.thefitnation.domain.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;

/**
 * Spring Data JPA repository for the UserWorkoutTemplate entity.
 */
@SuppressWarnings("unused")
public interface UserWorkoutTemplateRepository extends JpaRepository<UserWorkoutTemplate,Long> {

    @Query("select workoutTemplate " +
        "from WorkoutTemplate workoutTemplate " +
        "where workoutTemplate.userDemographic.user.login = :login")
    Page<UserWorkoutTemplate> findAll(@Param(value = "login") String login, Pageable pageable);

    @Query("select workoutTemplate " +
        "from WorkoutTemplate workoutTemplate " +
        "where workoutTemplate.userDemographic.user.login = :login " +
        "and workoutTemplate.id = :id")
    UserWorkoutTemplate findOne(@Param(value = "login") String login, @Param(value = "id") Long id);

}
