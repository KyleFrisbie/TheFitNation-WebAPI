package com.thefitnation.repository;

import com.thefitnation.domain.ExerciseSet;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ExerciseSet entity.
 */
@SuppressWarnings("unused")
public interface ExerciseSetRepository extends JpaRepository<ExerciseSet,Long> {

}
