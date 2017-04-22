package com.thefitnation.repository;

import com.thefitnation.domain.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;

/**
 * Spring Data JPA repository for the UserWorkoutInstance entity.
 */
@SuppressWarnings("unused")
public interface UserWorkoutInstanceRepository extends JpaRepository<UserWorkoutInstance,Long> {

    @Query("select userWorkoutInstance " +
        "from UserWorkoutInstance userWorkoutInstance " +
        "where userWorkoutInstance.userWorkoutTemplate.userDemographic.user.login = :login")
    Page<UserWorkoutInstance> findAll(@Param(value = "login") String login, Pageable pageable);

    @Query("select userWorkoutInstance " +
        "from UserWorkoutInstance userWorkoutInstance " +
        "where userWorkoutInstance.userWorkoutTemplate.userDemographic.user.login = :login " +
        "and userWorkoutInstance.id = :id")
    UserWorkoutInstance findOne(@Param(value = "login") String login, @Param(value = "id") Long id);
}
