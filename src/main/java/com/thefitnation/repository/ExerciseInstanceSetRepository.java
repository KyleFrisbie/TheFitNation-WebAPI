package com.thefitnation.repository;

import com.thefitnation.domain.ExerciseInstanceSet;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ExerciseInstanceSet entity.
 */
@SuppressWarnings("unused")
public interface ExerciseInstanceSetRepository extends JpaRepository<ExerciseInstanceSet,Long> {

}
