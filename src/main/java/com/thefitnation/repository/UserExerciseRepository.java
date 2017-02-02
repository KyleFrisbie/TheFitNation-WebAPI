package com.thefitnation.repository;

import com.thefitnation.domain.UserExercise;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the UserExercise entity.
 */
@SuppressWarnings("unused")
public interface UserExerciseRepository extends JpaRepository<UserExercise,Long> {

}
