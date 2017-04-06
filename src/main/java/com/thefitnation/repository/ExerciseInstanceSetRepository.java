package com.thefitnation.repository;

import com.thefitnation.domain.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;

/**
 * Spring Data JPA repository for the ExerciseInstanceSet entity.
 */
@SuppressWarnings("unused")
public interface ExerciseInstanceSetRepository extends JpaRepository<ExerciseInstanceSet,Long> {

    @Query("select exerciseInstanceSet " +
            "from ExerciseInstanceSet exerciseInstanceSet " +
            "where exerciseInstanceSet.exerciseInstance.workoutInstance.workoutTemplate.userDemographic.user.login = :login")
    Page<ExerciseInstanceSet> findAll(@Param(value = "login") String login, Pageable pageable);
}
