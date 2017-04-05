package com.thefitnation.repository;

import com.thefitnation.domain.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the ExerciseInstanceSet entity.
 */
@SuppressWarnings("unused")
public interface ExerciseInstanceSetRepository extends JpaRepository<ExerciseInstanceSet,Long> {

    @Query("select exerciseInstanceSet " +
            "from ExerciseInstanceSet exerciseInstanceSet " +
            "where exerciseInstanceSet.exerciseInstance.workoutInstance.workoutTemplate.userDemographic.user.login = ?#{principal.username}")
    Page<ExerciseInstanceSet> findAllByCurrentLoggedInUser(Pageable pageable);
}
