package com.thefitnation.repository;

import com.thefitnation.domain.UserExerciseInstanceSet;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the UserExerciseInstanceSet entity.
 */
@SuppressWarnings("unused")
public interface UserExerciseInstanceSetRepository extends JpaRepository<UserExerciseInstanceSet,Long> {

}
