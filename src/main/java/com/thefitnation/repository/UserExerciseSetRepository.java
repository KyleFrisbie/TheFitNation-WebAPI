package com.thefitnation.repository;

import com.thefitnation.domain.UserExerciseSet;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the UserExerciseSet entity.
 */
@SuppressWarnings("unused")
public interface UserExerciseSetRepository extends JpaRepository<UserExerciseSet,Long> {

}
