package com.thefitnation.repository;

import com.thefitnation.domain.UserExerciseInstance;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the UserExerciseInstance entity.
 */
@SuppressWarnings("unused")
public interface UserExerciseInstanceRepository extends JpaRepository<UserExerciseInstance,Long> {

}
