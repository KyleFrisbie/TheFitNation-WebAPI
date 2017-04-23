package com.thefitnation.repository;

import com.thefitnation.domain.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;

/**
 * Spring Data JPA repository for the UserExerciseInstanceSet entity.
 */
@SuppressWarnings("unused")
public interface UserExerciseInstanceSetRepository extends JpaRepository<UserExerciseInstanceSet,Long> {

    @Query("select userExerciseInstanceSet " +
        "from UserExerciseInstanceSet userExerciseInstanceSet " +
        "where userExerciseInstanceSet.userExerciseInstance.userWorkoutInstance.userWorkoutTemplate.userDemographic.user.login = :login")
    Page<UserExerciseInstanceSet> findAll(@Param(value = "login") String login, Pageable pageable);

    @Query("select userExerciseInstanceSet " +
        "from UserExerciseInstanceSet userExerciseInstanceSet " +
        "where userExerciseInstanceSet.userExerciseInstance.userWorkoutInstance.userWorkoutTemplate.userDemographic.user.login = :login " +
        "and userExerciseInstanceSet.id = :id")
    UserExerciseInstanceSet findOne(@Param(value = "login") String currentUserLogin, @Param(value = "id") Long id);

}
