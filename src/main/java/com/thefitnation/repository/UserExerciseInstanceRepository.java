package com.thefitnation.repository;

import com.thefitnation.domain.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;

/**
 * Spring Data JPA repository for the UserExerciseInstance entity.
 */
@SuppressWarnings("unused")
public interface UserExerciseInstanceRepository extends JpaRepository<UserExerciseInstance,Long> {

    @Query(
        "select userExerciseInstance " +
            "from UserExerciseInstance userExerciseInstance " +
            "where userExerciseInstance.userWorkoutInstance.userWorkoutTemplate.userDemographic.user.login = :login")
    Page<UserExerciseInstance> findAll(@Param(value = "login") String login, Pageable pageable);

    @Query("select userExerciseInstance " +
        "from UserExerciseInstance userExerciseInstance " +
        "where userExerciseInstance.userWorkoutInstance.userWorkoutTemplate.userDemographic.user.login = :login " +
        "and exerciseInstance.id = :id")
    UserExerciseInstance findOne(@Param(value = "login") String login, @Param(value = "id") Long id);

}

