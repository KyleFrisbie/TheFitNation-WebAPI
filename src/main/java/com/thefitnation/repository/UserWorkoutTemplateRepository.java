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

    @Query("select userWorkoutTemplate " +
        "from UserWorkoutTemplate userWorkoutTemplate " +
        "where userWorkoutTemplate.userDemographic.user.login = :login")
    Page<UserWorkoutTemplate> findAll(@Param(value = "login") String login, Pageable pageable);

    @Query("select userWorkoutTemplate " +
        "from UserWorkoutTemplate userWorkoutTemplate " +
        "where userWorkoutTemplate.userDemographic.user.login = :login " +
        "and userWorkoutTemplate.id = :id")
    UserWorkoutTemplate findOne(@Param(value = "login") String login, @Param(value = "id") Long id);

}
