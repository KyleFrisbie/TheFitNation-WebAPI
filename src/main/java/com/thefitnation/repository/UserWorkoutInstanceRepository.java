package com.thefitnation.repository;

import com.thefitnation.domain.UserWorkoutInstance;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the UserWorkoutInstance entity.
 */
@SuppressWarnings("unused")
public interface UserWorkoutInstanceRepository extends JpaRepository<UserWorkoutInstance,Long> {

}
