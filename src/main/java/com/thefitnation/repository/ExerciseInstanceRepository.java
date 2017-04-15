package com.thefitnation.repository;

import com.thefitnation.domain.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;

/**
 * Spring Data JPA repository for the ExerciseInstance entity.
 */
@SuppressWarnings("unused")
public interface ExerciseInstanceRepository extends JpaRepository<ExerciseInstance,Long> {

    @Query("select exerciseInstance " +
            "from ExerciseInstance exerciseInstance " +
            "where exerciseInstance.workoutInstance.workoutTemplate.userDemographic.user.login = :login")
    Page<ExerciseInstance> findAll(@Param(value = "login") String login, Pageable pageable);

    @Query("select exerciseInstance " +
            "from ExerciseInstance exerciseInstance " +
            "where exerciseInstance.workoutInstance.workoutTemplate.userDemographic.user.login = :login " +
                "and exerciseInstance.id = :id")
    ExerciseInstance findOne(@Param(value = "login") String login, @Param(value = "id") Long id);
}
