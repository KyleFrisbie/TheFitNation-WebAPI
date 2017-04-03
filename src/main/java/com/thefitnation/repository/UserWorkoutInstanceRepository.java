package com.thefitnation.repository;

import com.thefitnation.domain.*;
import com.thefitnation.service.dto.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;

/**
 * Spring Data JPA repository for the UserWorkoutInstance entity.
 */
@SuppressWarnings("unused")
public interface UserWorkoutInstanceRepository extends JpaRepository<UserWorkoutInstance,Long> {

    String FIND_ALL_BY_CURR_USER = "select userWorkoutInstance " +
        "from UserWorkoutInstance userWorkoutInstance " +
        "join userWorkoutInstance.userWorkoutTemplate.userDemographic.user u " +
        "where u.login = :currUser";

    @Query(value = FIND_ALL_BY_CURR_USER)
    Page<UserWorkoutInstanceDTO> findAllByCurrentUser(@Param("currUser") String currUser, Pageable pageable);
}
